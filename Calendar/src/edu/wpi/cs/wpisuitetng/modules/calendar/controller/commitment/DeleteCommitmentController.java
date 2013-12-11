/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.controller.commitment;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.CommitmentListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarPanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class DeleteCommitmentController implements ActionListener {
	private CommitmentListModel model;
	private CalendarPanel calendarPanel;

	public DeleteCommitmentController(CalendarPanel calendarPanel) {
		this.model = CommitmentListModel.getCommitmentListModel();
		this.calendarPanel = calendarPanel;
	}

	/**
	 * Handles the pressing of the Remove Commitment button
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		List<Commitment> commitmentList = calendarPanel.getCalendarTabPanel().getSelectedCommitmentList();
		for (Commitment commitment: commitmentList) {
			System.out.println("Deleting commitment: name = " + commitment.getName() + "; uid = " + commitment.getUniqueID());

			// Create a Delete Request
			final Request request = Network.getInstance().makeRequest("calendar/commitment/" + commitment.getUniqueID(), HttpMethod.DELETE);

			// Add an observer to process the response
			request.addObserver(new DeleteCommitmentObserver());

			// Send the request
			request.send();

			// We must remove the commitment without knowing the result of the server's response because
			// of a bug in Java in which you cannot set the body of a HTTP.DELETE request.
			model.removeCommitment(commitment);
		}
	}
}
