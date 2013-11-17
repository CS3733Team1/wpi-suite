package edu.wpi.cs.wpisuitetng.modules.calendar.model;

import java.util.ArrayList;
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
	}//end createFilter
	
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

	@Override
	public Object getElementAt(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setFilters(Filter[] fil) {
		// TODO Auto-generated method stub
		
	}
}
