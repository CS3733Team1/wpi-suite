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

import javax.swing.JEditorPane;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.CommitmentListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.commitment.CommitmentTabPanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class UpdateCommitmentController implements ActionListener
{
	CommitmentListModel model;
	CommitmentTabPanel view;
	Commitment oldCommitment;
	JEditorPane commitmentDisplay;
	
	public UpdateCommitmentController(CommitmentTabPanel view, Commitment oldCommitment)
	{
		this.model = CommitmentListModel.getCommitmentListModel();
		this.view = view;
		this.oldCommitment = oldCommitment;
		this.commitmentDisplay = view.getCommitmentView();
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		Commitment updatedCommitment = view.getFilledCommitment();
		updatedCommitment.setID(oldCommitment.getID());
	
		System.out.println("Updating commitment...");
		
		// Send a request to the core to save this message
		System.out.println("Sending Request");
		Request request = Network.getInstance().makeRequest("calendar/commitment", HttpMethod.POST); // POST == update
		request.setBody(updatedCommitment.toJSON()); // put the new requirement in the body of the request
		request.addObserver(new UpdateCommitmentObserver(this)); // add an observer to process the response
		request.send(); 
		view.killCommitmentPanel();
	}

	public void updateCommitmentInModel(Commitment newCommitment) 
	{
		model.updateCommitment(oldCommitment, newCommitment);
		commitmentDisplay.setText(newCommitment.toString());
	}

}
