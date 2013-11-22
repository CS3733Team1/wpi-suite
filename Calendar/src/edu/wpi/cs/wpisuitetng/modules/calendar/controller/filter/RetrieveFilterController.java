/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.controller.filter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.Filter;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.FilterListModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class RetrieveFilterController implements AncestorListener, ActionListener {
	FilterListModel model;

	public RetrieveFilterController() {
		this.model = FilterListModel.getFilterListModel();
	}

	public void retrieveMessages(){
		System.out.println("Retrieving filters from server...");
		// Send a request to the core to save this message
		final Request request = Network.getInstance().makeRequest("calendar/filter", HttpMethod.GET); // GET == read
		request.addObserver(new RetrieveFilterObserver(this)); // add an observer to process the response
		request.send(); // send the request
	}
	
	/**
	 * Add the given messages to the local model (they were received from the core).
	 * This method is called by the GetMessagesRequestObserver
	 * 
	 * @param messages an array of messages received from the server
	 */
	public void receivedMessages(Filter[] fil) {		
		// Make sure the response was not null
		if (fil != null) {
			// set the messages to the local model
			model.setFilters(fil);
		}
	}

	/**
	 * Whenever the Calendar Module Tab is viewed it will refetch Filters from the server.
	 * This also runs on first launch.
	 */
	@Override
	public void ancestorAdded(AncestorEvent e) {
		this.retrieveMessages();
	}

	@Override
	public void ancestorMoved(AncestorEvent e) {}

	@Override
	public void ancestorRemoved(AncestorEvent e) {}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.retrieveMessages();
	}
}
