package edu.wpi.cs.wpisuitetng.modules.calendar.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class FilteredEventsListModel extends AbstractListModel<Event> implements ListDataListener, FilterChangedListener {

	private static FilteredEventsListModel filteredEventsListModel;

	/** The list of filtered events on the calendar */
	private List<Event> filteredEvents;

	private FilteredEventsListModel() {
		filteredEvents=new ArrayList<Event>();
		//filteredEvents = Collections.synchronizedList(new ArrayList<Event>());
		EventListModel.getEventListModel().addListDataListener(this);
		FilterListModel.getFilterListModel().addListDataListener(this);
		filterEvents();
	}

	static public synchronized FilteredEventsListModel getFilteredEventsListModel() {
		if (filteredEventsListModel == null)
			filteredEventsListModel = new FilteredEventsListModel();
		//filteredEventsListModel.filterEvents();
		return filteredEventsListModel;
	}

	private void filterEvents() {
		filteredEvents.clear();
		List<Event> eventList = EventListModel.getEventListModel().getList();
		
		for(Event e: FilterListModel.getFilterListModel().applyEventFilter(eventList)) filteredEvents.add(new Event(e));
		
		this.fireIntervalAdded(this, 0, Math.max(filteredEvents.size() - 1, 0));
	}

	@Override
	public Event getElementAt(int index) {
		return filteredEvents.get(index);
	}

	@Override
	public int getSize() {
		return filteredEvents.size();
	}

	// Used to update the filtered contents when there are changes to the EventListModel

	@Override
	public void contentsChanged(ListDataEvent e) {
		filterEvents();
	}

	@Override
	public void intervalAdded(ListDataEvent e) {
		filterEvents();
	}

	@Override
	public void intervalRemoved(ListDataEvent e) {
		filterEvents();
	}

	@Override
	public void filterChanged() {
		filterEvents();
	}

	public synchronized List<Event> getList() {
		filterEvents();
		return filteredEvents;
	}
}
