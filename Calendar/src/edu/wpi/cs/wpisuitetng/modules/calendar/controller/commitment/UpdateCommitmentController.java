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

/**
 * An instance of this class may be used to edit commitment
 * @author TeamTART
 *
 */
public class UpdateCommitmentController implements ActionListener {
	CommitmentListModel model;
	CommitmentTabPanel view;
	Commitment commitmentToUpdate;
	
	public UpdateCommitmentController(CommitmentTabPanel view, Commitment commitmentToUpdate) {
		this.model = CommitmentListModel.getCommitmentListModel();
		this.view = view;
		this.commitmentToUpdate = commitmentToUpdate;
	}
	
	public UpdateCommitmentController(Commitment updatedCommitment)
	{
		this.model = CommitmentListModel.getCommitmentListModel();
		this.commitmentToUpdate = updatedCommitment;
		updateCommitmentInServer();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Commitment newCommitment = view.getFilledCommitment();
		newCommitment.setUniqueID(commitmentToUpdate.getUniqueID());
		newCommitment.setOwnerID(commitmentToUpdate.getOwnerID());
		newCommitment.setOwnerName(commitmentToUpdate.getOwnerName());
		commitmentToUpdate = newCommitment;
		
		updateCommitmentInServer();
	}
	
	public void updateCommitmentInServer()
	{
		System.out.println("Updating commitment: name = " + commitmentToUpdate.getName() + "; uid = " + commitmentToUpdate.getUniqueID());

		// Create a Post Request
		final Request request = Network.getInstance().makeRequest("calendar/commitment", HttpMethod.POST);
		
		// Put the new message in the body of the request
		request.setBody(commitmentToUpdate.toJSON()); 
		
		// Add an observer to process the response
		request.addObserver(new UpdateCommitmentObserver(this));
		
		// Send the request
		request.send();
	}

	public void updateCommitmentInModel(Commitment newCommitment) {

		System.out.println("	Update commitment: name = " + newCommitment.getName() + "; uid = " + newCommitment.getUniqueID());
		model.updateCommitment(commitmentToUpdate, newCommitment);

		if (commitmentToUpdate != null)
		{
			System.out.println("	Update commitment: name = " + newCommitment.getName() + "; uid = " + newCommitment.getUniqueID());
			model.updateCommitment(commitmentToUpdate, newCommitment);
			
			if (view != null)
			{
				view.closeCommitmentPanel();
			}
		}
	}

}
