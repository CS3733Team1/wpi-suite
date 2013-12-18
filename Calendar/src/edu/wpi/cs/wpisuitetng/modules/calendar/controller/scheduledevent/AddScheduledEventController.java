/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.controller.scheduledevent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.commitment.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.commitment.CommitmentListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.scheduledevent.ScheduledEvent;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.scheduledevent.ScheduledEventListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.MainView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.commitment.CommitmentTabPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.scheduledevent.ScheduleEventMain;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class AddScheduledEventController implements ActionListener{
	private ScheduledEventListModel model;
	private ScheduleEventMain schedMain;

	public AddScheduledEventController(ScheduleEventMain scheduleEvent){
		this.model = ScheduledEventListModel.getScheduledEventListModel();
		this.schedMain = scheduleEvent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Retrieve the ScheduledEvent
		ScheduledEvent scheduledEvent = schedMain.getFilledScheduledEvent();
		
		System.out.println("Adding Scheduled Event: name = " + scheduledEvent.getTitle() + "; uid = " + scheduledEvent.getUniqueID());

		// Create a Put Request
		final Request request = Network.getInstance().makeRequest("calendar/scheduledevent", HttpMethod.PUT);

		// Put the new message in the body of the request
		request.setBody(scheduledEvent.toJSON()); 

		// Add an observer to process the response
		request.addObserver(new AddScheduledEventObserver(this));

		// Send the request
		request.send();
	}

	public void addScheduledEventToModel(ScheduledEvent scheduledEvent){
		System.out.println("	Added commitment: name = " + scheduledEvent.getTitle() + "; uid = " + scheduledEvent.getUniqueID());
		model.addScheduledEvent(scheduledEvent);
		MainView.getCurrentCalendarPanel().remove(MainView.getCurrentCalendarPanel().getSelectedComponent());
	}
}
