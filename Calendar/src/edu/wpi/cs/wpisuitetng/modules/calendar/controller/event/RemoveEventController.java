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
import edu.wpi.cs.wpisuitetng.modules.calendar.model.EventEntityManager;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.MainModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.MainView;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class RemoveEventController implements ActionListener{
	MainModel model;
	MainView view;
	
	public RemoveEventController(MainView view, MainModel model){
		this.model = model;
		this.view = view;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		System.out.println("Attempt Delete 2");
		
		int[] index = view.getCalendarPanel().getSelectedEventList().getSelectedIndices();
		
		
		for (int x = index.length-1; x >= 0; x--){
			System.out.println("index: " + index[x]);

			Event eve = (Event) model.getEventModel().getElement(index[x]);
			//remove this later
			eve.setName(EventEntityManager.DELETESYMBOL+eve.getName());
			model.removeEvent(eve);

			// Send a request to the core to save this message
			final Request request = Network.getInstance().makeRequest("calendar/commitment", HttpMethod.DELETE); // PUT == create
			request.setBody(eve.toJSON()); // put the new message in the body of the request
			request.addObserver(new RemoveEventObserver(this)); // add an observer to process the response
			request.send(); // send the request
		}
		
		view.getCalendarPanel().refreshSelectedPanel();
		
	}
	
	public void removeEventToModel(Event eve){
		System.out.println("Deleting");
		model.removeEvent(eve);
	}

}
