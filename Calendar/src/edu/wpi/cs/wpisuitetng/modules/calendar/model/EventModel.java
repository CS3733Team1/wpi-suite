

/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Chris Casola
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.model;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractListModel;
@SuppressWarnings("serial")
public class EventModel extends AbstractListModel<Object> { 

/**
 * This is a model for events. It contains all of the events to be
 * displayed on the calendar. It extends AbstractListModel so that it can provide
 * the model data to the JList component in the BoardPanel.
 * 
 * @author Thomas DeSilva, Zach Estep
 * 
 */
	
	private static EventModel model;

	/** The list of events on the calendar */
	private List<Event> event;

	/**
	 * Constructs a new calendar with no events.
	 */
	private EventModel() {
		event = new ArrayList<Event>();
	}
	
	public static EventModel getEventModel()
	{
		if( model == null)
			model = new EventModel();
		return model;
	}

	/**
	 * Adds the given event to the calendar
	 * 
	 * @param newMessage
	 *            the new event to add
	 */
	public void addEvent(Event newEvent) {
		// Add the event
		event.add(newEvent);

		// Notify the model that it has changed so the GUI will be udpated
		this.fireIntervalAdded(this, 0, 0);
	}

	/**
	 * Adds the given array of events to the calendar
	 * 
	 * @param events
	 *            the array of events to add
	 */
	public void addEvents(Event[] events) {
		for (int i = 0; i < events.length; i++) {
			this.event.add(events[i]);
		}
		this.fireIntervalAdded(this, 0, Math.max(getSize() - 1, 0));
	}

	/**
	 * Removes all events from this model
	 * 
	 * NOTE: One cannot simply construct a new instance of the model, because
	 * other classes in this module have references to it. Hence, we manually
	 * remove each event from the model.
	 */
	public void emptyModel() {
		int oldSize = getSize();
		Iterator<Event> iterator = event.iterator();
		while (iterator.hasNext()) {
			iterator.next();
			iterator.remove();
		}
		this.fireIntervalRemoved(this, 0, Math.max(oldSize - 1, 0));
	}

	/*
	 * Returns the event at the given index. This method is called internally
	 * by the JList in BoardPanel. Note this method returns elements in reverse
	 * order, so newest events are returned first.
	 * 
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	@Override
	public Object getElementAt(int index) {
		return event.get(event.size() - 1 - index).toString();
	}
	
	
	
	public void removeEvent(int index)
	{
		event.remove(index);
	}
	
	public void removeEvent(Event givenEvent)
	{
		event.remove(givenEvent);
	}
	

	/*
	 * Returns the number of events in the model. Also used internally by the
	 * JList in BoardPanel.
	 * 
	 * @see javax.swing.ListModel#getSize()
	 */
	@Override
	public int getSize() {
		return event.size();
	}
	
	public List<Event> getList(){
		List<Event> temp = new ArrayList<Event>();
		temp.addAll(getEventModel().event);
		return temp;
	}
}
