/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.model.scheduledevent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractListModel;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.User;

public class ScheduledEventListModel extends AbstractListModel<ScheduledEvent> { 

	/**
	 * This is a model for scheduledEventModel. It contains all of the scheduledEventModel to be
	 * displayed on the calendar. It extends AbstractListModel so that it can provide
	 * the model data to the JList component in the BoardPanel. This class is a singleton.
	 * 
	 */

	private User currentUser;
	private static ScheduledEventListModel scheduledEventModel;

	/** The list of scheduledEventModel on the calendar */
	private List<ScheduledEvent> scheduledEvents;

	/**
	 * Constructs a new calendar with no scheduledEventModel.
	 */
	private ScheduledEventListModel() {
		//Ask for a thread safe arrayList
		scheduledEvents = Collections.synchronizedList(new ArrayList<ScheduledEvent>());
	}

	public static ScheduledEventListModel getScheduledEventListModel() {
		if( scheduledEventModel == null)
			scheduledEventModel = new ScheduledEventListModel();
		return scheduledEventModel;
	}

	/**
	 * Adds the given ScheduledEvent to the calendar
	 * 
	 * @param newMessage
	 *            the new ScheduledEvent to add
	 */
	public void addScheduledEvent(ScheduledEvent newScheduledEvent) {
		// Add the ScheduledEvent
		scheduledEvents.add(newScheduledEvent);
		// Notify the model that it has changed so the GUI will be udpated
		this.fireIntervalAdded(this, 0, 0);
	}

	/**
	 * Adds the given array of scheduledEventModel to the calendar
	 * 
	 * @param scheduledEventModel
	 *            the array of scheduledEventModel to add
	 */
	public void addScheduledEventModel(ScheduledEvent[] scheduledEventModel) {
		for (int i = 0; i < scheduledEventModel.length; i++) {
			this.scheduledEvents.add(scheduledEventModel[i]);
		}
		
		this.fireIntervalAdded(this, 0, Math.max(getSize() - 1, 0));
	}
	
	public synchronized void setScheduledEvents(ScheduledEvent[] scheduledEvents) {
		this.scheduledEvents.clear();
		this.scheduledEvents.addAll(Arrays.asList(scheduledEvents));
		this.fireIntervalAdded(this, 0, Math.max(getSize() - 1, 0));
	}

	/**
	 * Returns the ScheduledEvent at the given index. This method is called internally
	 * by the JList in BoardPanel. Note this method returns elements in reverse
	 * order, so newest scheduledEventModel are returned first.
	 * 
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	@Override
	public ScheduledEvent getElementAt(int index) {
		return scheduledEvents.get(index);
	}
	
	public void removeScheduledEvent(int index) {
		scheduledEvents.remove(index);
		this.fireIntervalRemoved(this, 0, 0);
	}

	public void removeScheduledEvent(ScheduledEvent ScheduledEvent) {
		scheduledEvents.remove(ScheduledEvent);
		this.fireIntervalRemoved(this, 0, 0);
	}
	
	public void setUser(User user) {
		this.currentUser = user;
		this.fireIntervalAdded(this, 0, 0);
	}
	
	public User getUser() {return this.currentUser;}

	/**
	 * Returns the number of scheduledEventModel in the model. Also used internally by the
	 * JList in BoardPanel.
	 * 
	 * @see javax.swing.ListModel#getSize()
	 */
	@Override
	public int getSize() {
		return scheduledEvents.size();
	}

	//** Note: avoid coping the scheduledEventModel in the list here.
	// use the copy constructor provided in ScheduledEvent() to avoid creating copies with dissimilar UniuqeID's
	public synchronized List<ScheduledEvent> getList(){
		return new ArrayList<ScheduledEvent>(scheduledEvents);
	}
	
	public synchronized void update() {
		this.fireIntervalAdded(this, 0, this.getSize() > 0 ? this.getSize() -1 : 0);
	}

	public void updateScheduledEvent(ScheduledEvent oldScheduledEvent, ScheduledEvent newScheduledEvent) 
	{
		removeScheduledEvent(oldScheduledEvent);
		addScheduledEvent(newScheduledEvent);
		this.fireIntervalAdded(this, 0, 0);
	}
}
