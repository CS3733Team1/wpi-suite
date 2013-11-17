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

import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.CommitmentListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.commitment.CommitmentTabPanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class AddCommitmentController implements ActionListener{
	CommitmentListModel model;
	CommitmentTabPanel view;
	
	public AddCommitmentController(CommitmentTabPanel view){
		this.model = CommitmentListModel.getCommitmentListModel();
		this.view = view;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		Commitment commit = view.getDisplayCommitment();
		
	
		System.out.println("Adding commitment...");
		
		// Send a request to the core to save this message
		final Request request = Network.getInstance().makeRequest("calendar/commitment", HttpMethod.PUT); // PUT == create
		request.setBody(commit.toJSON()); // put the new message in the body of the request
		request.addObserver(new AddCommitmentObserver(this)); // add an observer to process the response
		request.send(); // send the request
		view.killPanel();
		
	}
	
	public void addCommitmentToModel(Commitment commit){
		System.out.println("Commitment added.");
		model.addCommitment(commit);
	}

}
