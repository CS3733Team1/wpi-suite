/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractListModel;

/**
 * This is a model for the filter list. It contains all of the filters the user has created.
 *  It extends AbstractListModel so that it can provide
 * the model data to the JList component in the CalendarPanel.
 */

public class FilterListModel extends AbstractListModel<Filter>
{
	private static FilterListModel filterListModel;
	
	/** The list of filter */
	private List<Filter> filters;
	
	/** The active filter */
	private Filter activeFilter;
	
	
	private FilterListModel() {
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
	
	public void setFilters(Filter[] filters) {
		this.emptyModel();
		for (int i = 0; i < filters.length; i++) {
			this.filters.add(filters[i]);
		}
		this.fireIntervalAdded(this, 0, Math.max(getSize() - 1, 0));
	}
	
	public void setActiveFilter(Filter filter) {
		for(Filter f: this.filters) {
			f.setSelected(false);
		}
		filter.setSelected(true);
		this.activeFilter = filter;
		System.out.println("Active filter: " + filter.getName());
	}
	
	public ArrayList<Event> applyFilterToEvent() {
		List<Event> eventList = EventListModel.getEventListModel().getList();
		Event[] eventArray = eventList.toArray(new Event[eventList.size()]);
		
		return this.activeFilter.apply(eventArray);
	}//end applyFilter
	
	public ArrayList<Event> applyFilter() {
		List<Event> eventList = EventListModel.getEventListModel().getList();
		Event[] eventArray = eventList.toArray(new Event[eventList.size()]);
		
		return this.activeFilter.apply(eventArray);
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
	public Filter getElementAt(int index) 
	{
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
}
