package edu.wpi.cs.wpisuitetng.modules.calendar.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractListModel;

/**
 * This is a model for the filter list. It contains all of the filters the user has created.
 *  It extends AbstractListModel so that it can provide
 * the model data to the JList component in the CalendarPanel.
 * 
 * @author Nicholas Otero
 * 
 */

public class FilterListModel extends AbstractListModel<Object>
{
	private static FilterListModel filterListModel;
	
	/** The list of filter */
	private List<Filter> filters;
	
	/** The active Event filter */
	private Filter activeEventFilter;
	
	/** The active Category filter */
	private Filter activeCommitmentFilter;
	
	
	private FilterListModel() 
	{
		this.filters = new ArrayList<Filter>();
	}
	
	static public FilterListModel getFilterListModel() 
	{
		if (filterListModel == null)
			filterListModel = new FilterListModel();
		return filterListModel;
	}
	
	public void createFilter(String name, ArrayList<Category> categories)
	{
		this.filters.add(new Filter(name, categories));
		
		this.fireIntervalAdded(this, 0, 0);
	}//end createFilter
	
	public void addFilter(Filter toAdd)
	{
		this.filters.add(toAdd);
		
		this.fireIntervalAdded(this, 0, 0);
	}//end addFilter
	
	public void setActiveEventFilter(int filterIndex)
	{
		this.activeEventFilter = filters.get(filterIndex);
	}//end setActiveEventFilter
	
	public void setActiveCommitmentFilter(int filterIndex)
	{
		this.activeCommitmentFilter = filters.get(filterIndex);
	}//end setActiveEventFilter
	
	public ArrayList<Event> applyEventFilter()
	{
		List<Event> eventList = EventListModel.getEventListModel().getList();
		Event[] eventArray = eventList.toArray(new Event[eventList.size()]);
		
		return this.activeEventFilter.apply(eventArray);
	}//end applyFilter
	
	public ArrayList<Event> applyFilter()
	{
		List<Event> eventList = EventListModel.getEventListModel().getList();
		Event[] eventArray = eventList.toArray(new Event[eventList.size()]);
		
		return this.activeEventFilter.apply(eventArray);
	}//end applyFilter
	
	/**
	 * Removes all filters from this model
	 * 
	 * NOTE: One cannot simply construct a new instance of the model, because
	 * other classes in this module have references to it. Hence, we manually
	 * remove each filter from the model.
	 */
	public void emptyModel() 
	{
		int oldSize = getSize();
		Iterator<Filter> iterator = filters.iterator();
		while (iterator.hasNext()) 
		{
			iterator.next();
			iterator.remove();
		}
		this.fireIntervalRemoved(this, 0, Math.max(oldSize - 1, 0));
	}

	/**
	 * Returns the filter at the given index. Note this method returns elements in reverse
	 * order, so newest filters are returned first.
	 * 
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	@Override
	public Object getElementAt(int index) 
	{
		return filters.get(filters.size() - 1 - index);
	}
	
	public Object getElement(int index){
		return filters.get(filters.size() - 1 - index);
	}

	public void removeFilter(int index) 
	{
		filters.remove(index);
		this.fireIntervalAdded(this, 0, 0);
	}

	public void removeFilter(Filter filter) 
	{
		filters.remove(filter);
		this.fireIntervalAdded(this, 0, 0);
	}

	/**
	 * Returns the number of filters in the model.
	 * 
	 * @see javax.swing.ListModel#getSize()
	 */
	@Override
	public int getSize() {
		return filters.size();
	}

	static public List<Filter> getList(){
		List<Filter> rtnFilterList = new ArrayList<Filter>();
		rtnFilterList.addAll(getFilterListModel().filters);
		return rtnFilterList;
	}

	public void setFilters(Filter[] fil) {
		// TODO Auto-generated method stub
		
	}
}
