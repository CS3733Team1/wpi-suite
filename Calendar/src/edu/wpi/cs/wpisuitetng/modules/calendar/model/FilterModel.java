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

public class FilterModel extends AbstractListModel<Object>
{
	private static FilterModel filterModel;
	
	/** The list of filter */
	private List<Filter> filters;
	
	/** The active Event filter */
	private Filter activeEventFilter;
	
	/** The active Category filter */
	private Filter activeCommitmentFilter;
	
	
	private FilterModel() 
	{
		this.filters = new ArrayList<Filter>();
	}
	
	static public FilterModel getFilterModel() 
	{
		if (filterModel == null)
			filterModel = new FilterModel();
		return filterModel;
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
		List<Event> eventList = EventModel.getEventModel().getList();
		Event[] eventArray = eventList.toArray(new Event[eventList.size()]);
		
		return this.activeEventFilter.apply(eventArray);
	}//end applyFilter
	
	public ArrayList<Event> applyFilter()
	{
		List<Event> eventList = EventModel.getEventModel().getList();
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
	
}//end class
	

	/**
	 * Constructs a new calendar with no commitments.
	 */
	