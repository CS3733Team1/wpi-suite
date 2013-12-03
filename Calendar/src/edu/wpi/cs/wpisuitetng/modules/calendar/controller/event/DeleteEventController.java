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
import java.util.ArrayList;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.EventListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.EventMouseListener;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class DeleteEventController implements ActionListener {
	EventListModel model;
	CalendarPanel calendarPanel;
	
	public DeleteEventController(CalendarPanel calendarPanel){
		this.model = EventListModel.getEventListModel();
		this.calendarPanel = calendarPanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//calendarPanel.getCalendarTabPanel().getSelectedEventList()
		Event event=null;
		ArrayList<Event> selected = EventMouseListener.getSelected();
		for (int i =0; i< selected.size();i++) {
//			Event event=EventMouseListener.getSelected();
			event = new Event(selected.get(i));
			if(event!=null){
				System.out.println("Events in the model");
				for(Event thisevent : EventListModel.getEventListModel().getList())
				{
					System.out.println("Event with id " + thisevent.getUniqueID() + " and name " + thisevent.getName());
				}
				model.removeEvent(event);

				// Send a request to the core to save this message 
				System.out.println("Trying to delete Event with id = " + event.getUniqueID());
				final Request request = Network.getInstance().makeRequest("calendar/event/"+event.getUniqueID(), HttpMethod.GET); 
				request.addHeader("X-HTTP-Method-Override", "DELETE");
				request.addObserver(new DeleteEventObserver(this)); // add an observer to process the response
				request.send(); // send the request
			}
			
		}
		
		calendarPanel.refreshSelectedPanel();
	}
	
	public void removeEventToModel(Event eve){
		System.out.println("Deleting Event");
		model.removeEvent(eve);
	}
}
