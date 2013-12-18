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
import edu.wpi.cs.wpisuitetng.modules.calendar.view.MainView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.commitment.CommitmentTabPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.scheduledevent.ScheduleEventMain;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * An instance of this class may be used to edit commitment
 * @author TeamTART
 *
 */
public class UpdateScheduledEventController implements ActionListener {

	/**
	 * Constructor used by the commitment list or edit commitment panel to update commitment
	 * @param view The view, which needs to be closed after edit is entered
	 * @param commitmentToUpdate commitment to update
	 */

	private ScheduleEventMain schedMain;

	/**
	 * This constructor is used by views for updating dragged commitments
	 * @param scheduleEvent commitment being updated
	 */
	public UpdateScheduledEventController(ScheduleEventMain scheduleEvent) {
		this.schedMain = scheduleEvent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ScheduledEvent newScheduledEvent = schedMain.getFilledScheduledEvent();

		// Create a Post Request
		final Request request = Network.getInstance().makeRequest("calendar/scheduledevent", HttpMethod.POST);

		// Put the new message in the body of the request
		request.setBody(newScheduledEvent.toJSON()); 

		// Add an observer to process the response
		request.addObserver(new UpdateScheduledEventObserver(this));

		// Send the request
		request.send();
	}

	/**
	 * Updates the model with the new commitment recieved from the server
	 * @param newCommitment commitment to be added to the model
	 */
	public void updateScheduledEventInModel(ScheduledEvent scheduledEvent) {
		MainView.getCurrentCalendarPanel().remove(MainView.getCurrentCalendarPanel().getSelectedComponent());
	}

}
