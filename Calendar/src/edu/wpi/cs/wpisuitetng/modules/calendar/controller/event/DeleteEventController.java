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
		System.err.println("Del: "+ selected.get(0).getUniqueID());
		
		for (int i =0; i< selected.size();i++) {
//			Event event=EventMouseListener.getSelected();
			event = new Event(selected.get(i));
			if(event!=null){
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
