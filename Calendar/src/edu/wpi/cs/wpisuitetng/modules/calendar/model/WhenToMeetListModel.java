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
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractListModel;

public class WhenToMeetListModel extends AbstractListModel<WhenToMeet> { 

	/**
	 * This is a model for WhenToMeets. It contains all of the WhenToMeets to be
	 * displayed on the calendar. It extends AbstractListModel so that it can provide
	 * the model data to the JList component in the BoardPanel. This class is a singleton.
	 * 
	 */

	private static WhenToMeetListModel WhenToMeetModel;

	/** The list of WhenToMeets on the calendar */
	private List<WhenToMeet> WhenToMeets;

	/**
	 * Constructs a new calendar with no WhenToMeets.
	 */
	private WhenToMeetListModel() {
		//Ask for a thread safe arrayList
		WhenToMeets = Collections.synchronizedList(new ArrayList<WhenToMeet>());
	}

	public static WhenToMeetListModel getWhenToMeetListModel() {
		if( WhenToMeetModel == null)
			WhenToMeetModel = new WhenToMeetListModel();
		return WhenToMeetModel;
	}

	/**
	 * Adds the given WhenToMeet to the calendar
	 * 
	 * @param newMessage
	 *            the new WhenToMeet to add
	 */
	public void addWhenToMeet(WhenToMeet newWhenToMeet) {
		// Add the WhenToMeet
		WhenToMeets.add(newWhenToMeet);
		// Notify the model that it has changed so the GUI will be udpated
		this.fireIntervalAdded(this, 0, 0);
	}

	/**
	 * Adds the given array of WhenToMeets to the calendar
	 * 
	 * @param WhenToMeets
	 *            the array of WhenToMeets to add
	 */
	public void addWhenToMeets(WhenToMeet[] WhenToMeets) {
		for (int i = 0; i < WhenToMeets.length; i++) {
			this.WhenToMeets.add(WhenToMeets[i]);
		}
		//Collections.sort(this.WhenToMeets);
		
		this.fireIntervalAdded(this, 0, Math.max(getSize() - 1, 0));
	}
	
	public synchronized void setWhenToMeets(WhenToMeet[] WhenToMeets) {
		this.emptyModel();
		this.WhenToMeets.addAll(Arrays.asList(WhenToMeets));
		//Collections.sort(this.WhenToMeets);
		
		this.fireIntervalAdded(this, 0, Math.max(getSize() - 1, 0));
	}

	/**
	 * Removes all WhenToMeets from this model
	 * 
	 * NOTE: One cannot simply construct a new instance of the model, because
	 * other classes in this module have references to it. Hence, we manually
	 * remove each WhenToMeet from the model.
	 */
	public synchronized void emptyModel() {
		int oldSize = getSize();
		Iterator<WhenToMeet> iterator = WhenToMeets.iterator();
		while (iterator.hasNext()) {
			iterator.next();
			iterator.remove();
		}
		this.fireIntervalRemoved(this, 0, Math.max(oldSize - 1, 0));
	}

	/**
	 * Returns the WhenToMeet at the given index. This method is called internally
	 * by the JList in BoardPanel. Note this method returns elements in reverse
	 * order, so newest WhenToMeets are returned first.
	 * 
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	@Override
	public WhenToMeet getElementAt(int index) {
		return WhenToMeets.get(index);
	}
	
	public void removeWhenToMeet(int index) {
		WhenToMeets.remove(index);
		this.fireIntervalRemoved(this, 0, 0);
	}

	public void removeWhenToMeet(WhenToMeet WhenToMeet) {
		WhenToMeets.remove(WhenToMeet);
		this.fireIntervalRemoved(this, 0, 0);
	}

	/**
	 * Returns the number of WhenToMeets in the model. Also used internally by the
	 * JList in BoardPanel.
	 * 
	 * @see javax.swing.ListModel#getSize()
	 */
	@Override
	public int getSize() {
		return WhenToMeets.size();
	}

	//** Note: avoid coping the WhenToMeets in the list here.
	// use the copy constructor provided in WhenToMeet() to avoid creating copies with dissimilar UniuqeID's
	public synchronized List<WhenToMeet> getList(){
		return new ArrayList<WhenToMeet>(WhenToMeets);
	}
	
	public synchronized void update() {
		this.fireIntervalAdded(this, 0, this.getSize() > 0 ? this.getSize() -1 : 0);
	}

	public void updateWhenToMeet(WhenToMeet oldWhenToMeet, WhenToMeet newWhenToMeet) 
	{
		removeWhenToMeet(oldWhenToMeet);
		addWhenToMeet(newWhenToMeet);
		this.fireIntervalAdded(this, 0, 0);
	}
	
}
