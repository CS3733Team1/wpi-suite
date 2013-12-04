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
import java.util.Arrays;
import java.util.List;

import javax.swing.AbstractListModel;

/**
 * This is a model for the filter list. It contains all of the filters the user has created.
 *  It extends AbstractListModel so that it can provide
 * the model data to the JList component in the CalendarPanel.
 */

public class FilterListModel extends AbstractListModel<Filter> {
	private static FilterListModel filterListModel;

	List<FilterChangedListener> filterChangedListeners = new ArrayList<FilterChangedListener>();

	private final Filter[] defaultFilters = {new Filter("None")};

	/** The list of filter */
	private List<Filter> filters;

	/** The active filter */
	private Filter activeFilter;

	private FilterListModel() {
		this.filters = new ArrayList<Filter>();
	}

	static public FilterListModel getFilterListModel() {
		if (filterListModel == null)
			filterListModel = new FilterListModel();
		return filterListModel;
	}

	public void createFilter(String name, ArrayList<Category> categories) {
		this.filters.add(new Filter(name, categories));
		this.fireIntervalAdded(this, 0, 0);
	}

	public void addFilter(Filter toAdd) {
		this.filters.add(toAdd);
		this.fireIntervalAdded(this, 0, 0);
	}

	// Used to see if a filter is a default or not
	public boolean isDefault(Filter filter) {
		return Arrays.asList(defaultFilters).contains(filter);
	}

	public void setFilters(Filter[] filters) {
		this.emptyModel();

		//DEFAULT FILTERS
		for(Filter filter: defaultFilters) this.filters.add(filter);

		for (int i = 0; i < filters.length; i++) {
			this.filters.add(filters[i]);
		}
		this.fireIntervalAdded(this, 0, Math.max(getSize() - 1, 0));
	}

	public void setActiveFilter(Filter filter) {
		if(!filter.equals(activeFilter)) {
			for(Filter f: this.filters) {
				f.setSelected(false);
			}
			filter.setSelected(true);
			this.activeFilter = filter;
			this.fireFilterChanged();
		}
	}

	/**
	 * Removes all filters from this model
	 * 
	 * NOTE: One cannot simply construct a new instance of the model, because
	 * other classes in this module have references to it. Hence, we manually
	 * remove each filter from the model.
	 */
	public void emptyModel() {
		int oldSize = getSize();
		for(Filter filter: filters) filters.remove(filter);

		this.fireIntervalRemoved(this, 0, Math.max(oldSize - 1, 0));
	}

	/**
	 * Returns the filter at the given index. Note this method returns elements in reverse
	 * order, so newest filters are returned first.
	 * 
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	@Override
	public Filter getElementAt(int index) {
		return filters.get(index);
	}

	public void removeFilter(Filter filter) {
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

	public List<Event> applyEventFilter(List<Event> eventList) {
		if(activeFilter.getName().equals("None")) return eventList;
		else return activeFilter.applyEventFilter(eventList);
	}

	public List<Commitment> applyCommitmentFilter(List<Commitment> commitmentList) {
		if(activeFilter.getName().equals("None")) return commitmentList;
		else return activeFilter.applyCommitmentFilter(commitmentList);
	}

	public void fireFilterChanged() {
		for(FilterChangedListener l: filterChangedListeners)
			l.filterChanged();
	}

	public void addFilterChangedListener(FilterChangedListener l) {
		filterChangedListeners.add(l);
	}
}
