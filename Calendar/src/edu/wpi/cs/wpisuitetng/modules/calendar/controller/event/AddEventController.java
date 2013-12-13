/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.controller.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.EventListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.event.EventTabPanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class AddEventController implements ActionListener {
	private EventListModel model;
	private EventTabPanel view;

	public AddEventController(EventTabPanel view) {
		this.model = EventListModel.getEventListModel();
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//get occurrences 
		int numOfEvents = view.numOfEvents();
		
		// Retrieve the event
		Event event;
		
		for(int i = 0; i < numOfEvents; ++i)
		{
			if(numOfEvents == 0)
				event = view.getFilledEvent();
			else
				event = view.getFilledEvents()[i];
			System.out.println("Adding event: name = " + event.getName() + "; uid = " + event.getUniqueID());

			// Create a Put Request
			final Request request = Network.getInstance().makeRequest("calendar/event", HttpMethod.PUT);
			
			// Put the new message in the body of the request
			request.setBody(event.toJSON()); 
			
			// Add an observer to process the response
			request.addObserver(new AddEventObserver(this));
			
			// Send the request
			request.send();
		}
		
	}
	
	public void addEventToModel(Event event) {
		System.out.println("	Added event: name = " + event.getName() + "; uid = " + event.getUniqueID());
		model.addEvent(event);
		view.closeEventPanel();
	}
}
