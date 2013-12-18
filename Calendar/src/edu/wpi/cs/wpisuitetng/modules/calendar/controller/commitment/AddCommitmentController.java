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

import edu.wpi.cs.wpisuitetng.modules.calendar.model.commitment.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.commitment.CommitmentListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.commitment.CommitmentTabPanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class AddCommitmentController implements ActionListener{
	private CommitmentListModel model;
	private CommitmentTabPanel view;

	public AddCommitmentController(CommitmentTabPanel view){
		this.model = CommitmentListModel.getCommitmentListModel();
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Retrieve the commitment
		Commitment commitment = view.getFilledCommitment();

		System.out.println("Adding commitment: name = " + commitment.getName() + "; uid = " + commitment.getUniqueID());

		// Create a Put Request
		final Request request = Network.getInstance().makeRequest("calendar/commitment", HttpMethod.PUT);

		// Put the new message in the body of the request
		request.setBody(commitment.toJSON()); 

		// Add an observer to process the response
		request.addObserver(new AddCommitmentObserver(this));

		// Send the request
		request.send();
	}

	public void addCommitmentToModel(Commitment commitment){
		System.out.println("	Added commitment: name = " + commitment.getName() + "; uid = " + commitment.getUniqueID());
		model.addCommitment(commitment);
		view.closeCommitmentPanel();
	}
}
