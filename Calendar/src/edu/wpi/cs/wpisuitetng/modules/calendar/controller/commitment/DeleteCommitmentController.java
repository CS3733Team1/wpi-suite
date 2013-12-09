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
		
		List<Commitment> list = calendarPanel.getCalendarTabPanel().getSelectedCommitmentList();
		
		System.out.println("Called Delete Commitments...");
		
		for (Commitment commit:list) {
			commit.markForDeletion();
			model.removeCommitment(commit);
			// Send a request to the core to save this message 
			final Request request = Network.getInstance().makeRequest("calendar/commitment/" + commit.getUniqueID(), HttpMethod.GET); // PUT == create
			request.addHeader("X-HTTP-Method-Override", "DELETE");
			request.addObserver(new DeleteCommitmentObserver(this)); // add an observer to process the response
			request.send(); // send the request
		}
		calendarPanel.getCalendarTabPanel().resetSelection();
	}
	
	public void removeCommitmentFromModel(Commitment commit){
		System.out.println("Deleting Commitment");
		model.removeCommitment(commit);
	}
}
