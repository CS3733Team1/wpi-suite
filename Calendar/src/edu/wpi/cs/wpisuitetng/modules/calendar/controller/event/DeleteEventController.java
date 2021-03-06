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
import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.event.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.event.EventListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarPanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class DeleteEventController implements ActionListener {
	private CalendarPanel calendarPanel;

	public DeleteEventController(CalendarPanel calendarPanel) {
		this.calendarPanel = calendarPanel;
	}
	
	/**
	 * Handles the pressing of the Remove Commitment button
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		List<Event> eventList = calendarPanel.getCalendarTabPanel().getSelectedEventList();
		deleteEventList(eventList);
	}
	
	public static void deleteEventList(List<Event> eventList){
		EventListModel model = EventListModel.getEventListModel();
		
		for (Event event: eventList) {
			System.out.println("Deleting event: name = " + event.getName() + "; uid = " + event.getUniqueID());

			// Create a Delete Request
			final Request request = Network.getInstance().makeRequest("calendar/event/" + event.getUniqueID(), HttpMethod.DELETE);

			// Add an observer to process the response
			request.addObserver(new DeleteEventObserver());

			// Send the request
			request.send();

			// We must remove the commitment without knowing the result of the server's response because
			// of a bug in Java in which you cannot set the body of a HTTP.DELETE request.
			model.removeEvent(event);
		}
	}
}
