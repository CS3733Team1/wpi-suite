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

/**
 * An instance of this class may be used to edit commitment
 * @author TeamTART
 *
 */
public class UpdateCommitmentController implements ActionListener {
	CommitmentListModel model;
	CommitmentTabPanel view;
	Commitment oldCommitment;
	
	public UpdateCommitmentController(CommitmentTabPanel view, Commitment oldCommitment) {
		this.model = CommitmentListModel.getCommitmentListModel();
		this.view = view;
		this.oldCommitment = oldCommitment;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Commitment updatedCommitment = view.getFilledCommitment();
		updatedCommitment.setUniqueID(oldCommitment.getUniqueID());
	
		System.out.println("Updating commitment: name = " + updatedCommitment.toString());//oldCommitment.getName() + "; uid = " + oldCommitment.getUniqueID());

		// Create a Post Request
		final Request request = Network.getInstance().makeRequest("calendar/commitment", HttpMethod.POST);
		
		// Put the new message in the body of the request
		request.setBody(updatedCommitment.toJSON()); 
		
		// Add an observer to process the response
		request.addObserver(new UpdateCommitmentObserver(this));
		
		// Send the request
		request.send();
	}

	public void updateCommitmentInModel(Commitment newCommitment) {
		System.out.println("	Update commitment: name = " + newCommitment.getName() + "; uid = " + newCommitment.getUniqueID());
		model.updateCommitment(oldCommitment, newCommitment);
		view.closeCommitmentPanel();
	}

}
