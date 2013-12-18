/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.controller;

import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.User;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.scheduledevent.ScheduledEventListModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class RetrieveUserController implements AncestorListener {
	public RetrieveUserController() {
		
	}

	public void retrieveMessages(){
		System.out.println("Retrieving User Info from server...");

		// Create a Get Request
		final Request request = Network.getInstance().makeRequest("calendar/user", HttpMethod.PUT);

		// Add an observer to process the response
		request.addObserver(new RetrieveUserObserver(this));
		
		request.setBody("");

		// Send the request
		request.send();
	}

	/**
	 * Add the given messages to the local model (they were received from the core).
	 * This method is called by the GetMessagesRequestObserver
	 * 
	 * @param messages an array of messages received from the server
	 */
	public void receivedMessages(User user) {		
		// Make sure the response was not null
		System.out.println("You are: " + user.getName() + " user: " + user.getUserName());
		ScheduledEventListModel.getScheduledEventListModel().setUser(user);
	}

	/**
	 * Whenever the Calendar Module Tab is viewed it will refetch Filters from the server.
	 * This also runs on first launch.
	 */
	@Override
	public void ancestorAdded(AncestorEvent e) {
		this.retrieveMessages();
	}

	// Unused
	@Override
	public void ancestorMoved(AncestorEvent e) {}
	@Override
	public void ancestorRemoved(AncestorEvent e) {}
}
