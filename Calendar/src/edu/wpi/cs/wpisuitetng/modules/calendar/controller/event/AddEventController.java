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
import java.util.Date;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.EventEntityManager;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.MainModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.event.AddEventTabPanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class AddEventController implements ActionListener{
	MainModel model;
	AddEventTabPanel view;
	
	public AddEventController(AddEventTabPanel view, MainModel model){
		this.model = model;
		this.view = view;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

		Event eve = new Event("Hello", new Date(), new Date());
		
		if(eve.getName().length() > EventEntityManager.DELETESYMBOL.length() && eve.getName().substring(0, EventEntityManager.DELETESYMBOL.length()).equals(EventEntityManager.DELETESYMBOL))
		{
			System.err.println("Commitment names cannot begin with " + EventEntityManager.DELETESYMBOL);
			return;
		}
		
		System.out.println("Adding event...");
		
		// Send a request to the core to save this message
		final Request request = Network.getInstance().makeRequest("calendar/event", HttpMethod.PUT); // PUT == create
		request.setBody(eve.toJSON()); // put the new message in the body of the request
		request.addObserver(new AddEventObserver(this)); // add an observer to process the response
		request.send(); // send the request
		view.killPanel();
	}
	
	public void addEventToModel(Event eve){
		System.out.println("Event added.");
		model.addEventFromCalendar(eve);
	}

}
