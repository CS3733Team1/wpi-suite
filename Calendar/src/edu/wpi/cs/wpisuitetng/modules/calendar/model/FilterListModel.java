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
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractListModel;

/**
 * This is a model for the Filter list. It contains all of the Filters the user has created.
 *  It extends AbstractListModel so that it can provide
 * the model data to the JList component in the CalendarPanel. This class is a singleton.
 */

public class FilterListModel extends AbstractListModel<Filter> {
	private static FilterListModel filterListModel;

	List<FilterChangedListener> filterChangedListeners = new ArrayList<FilterChangedListener>();

	private final Filter[] defaultFilters = {new Filter("Unfiltered")};

	/** The list of Filter */
	private List<Filter> filters;

	/** The active Filter */
	private Filter activeFilter;

	private FilterListModel() {
		this.filters = Collections.synchronizedList(new ArrayList<Filter>());
	}

	static public FilterListModel getFilterListModel() {
		if (filterListModel == null)
			filterListModel = new FilterListModel();
		return filterListModel;
	}

	
	/**
	 * Constructs a new Filter and adds it to the model's List of Filters.
	 * @param name A name for the new Filter
	 * @param categories a list of Categories to whitelist
	 */
	public void createFilter(String name, ArrayList<Category> categories) {
		this.filters.add(new Filter(name, categories));
		this.fireIntervalAdded(this, 0, 0);
	}

	/**
	 * Adds an existing Filter to the model's List.
	 * @param toAdd Filter to be added.
	 */
	
	public void addFilter(Filter toAdd) {
		this.filters.add(toAdd);
		this.fireIntervalAdded(this, 0, 0);
	}

	/**
	 * @return <b>true</b> if the given Filter is the default Filter, <b>false</b> otherwise
	 */
	public boolean isDefault(Filter filter) {
		return Arrays.asList(defaultFilters).contains(filter);
	}

	/**
	 * @return <b>true</b> if the given String matches the name of an existing Filter, <b>false</b> otherwise
	 */
	public boolean isReserved(String filterName) {
		for(Filter filter: filters) if(filter.getName().equals(filterName)) return true;
		return false;
	}

	/**
	 * Clears the model's list of Filters and replaces it with the given array of Filters.
	 * @param filters an array of Filters to put in 
	 */
	
	public void setFilters(Filter[] filters) {
		this.emptyModel();

		//DEFAULT FILTERS
		for(Filter filter: defaultFilters) this.filters.add(filter);

		for (int i = 0; i < filters.length; i++) {
			this.filters.add(filters[i]);
			if(filters[i].getSelected()) this.activeFilter = filters[i];
		}

		if(this.activeFilter == null) {
			defaultFilters[0].setSelected(true);
			this.activeFilter = defaultFilters[0];
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
			this.fireContentsChanged(this, 0, Math.max(0, filters.size()-1));
		}
	}

	/**
	 * Removes all filters from this model
	 * 
	 * NOTE: One cannot simply construct a new instance of the model, because
	 * other classes in this module have references to it. Hence, we manually
	 * remove each Filter from the model.
	 */
	public void emptyModel() {
		int oldSize = getSize();
		while(filters.size() != 0) filters.remove(0);

		this.fireIntervalRemoved(this, 0, Math.max(oldSize - 1, 0));
	}

	/**
	 * Returns the Filter at the given index. Note this method returns elements in reverse
	 * order, so newest Filters are returned first.
	 * 
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	@Override
	public Filter getElementAt(int index) {
		return filters.get(index);
	}

	public void removeFilter(Filter filter) {
		filters.remove(filter);
		if(filter.getSelected()) {
			defaultFilters[0].setSelected(true);
			this.activeFilter = defaultFilters[0];
			this.fireFilterChanged();
		}
		this.fireIntervalAdded(this, 0, 0);
	}

	/**
	 * Returns the number of Filters in the model.
	 * 
	 * @see javax.swing.ListModel#getSize()
	 */
	@Override
	public int getSize() {
		return filters.size();
	}

	static public List<Filter> getList(){
		return getFilterListModel().filters;
	}

	
	/**
	 * Applies the current activeFilter to the given List of Events
	 * @param eventList A List of Events to filter
	 * @return The filtered List of Events
	 */
	public List<Event> applyEventFilter(List<Event> eventList) {
		if(activeFilter == null || activeFilter.getName().equals("Unfiltered")) return eventList;
		else return activeFilter.applyEventFilter(eventList);
	}

	/**
	 * Applies the current activeFilter to the given List of Commitments
	 * @param eventList A List of Commitments to filter
	 * @return The filtered List of Commitments
	 */
	public List<Commitment> applyCommitmentFilter(List<Commitment> commitmentList) {
		if(activeFilter == null || activeFilter.getName().equals("Unfiltered")) return commitmentList;
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
