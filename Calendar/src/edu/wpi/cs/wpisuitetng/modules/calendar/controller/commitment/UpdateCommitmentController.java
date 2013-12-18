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

/**
 * An instance of this class may be used to edit commitment
 * @author TeamTART
 *
 */
public class UpdateCommitmentController implements ActionListener {
	private CommitmentListModel model;
	private CommitmentTabPanel view;
	private Commitment commitmentToUpdate;
	
	/**
	 * Constructor used by the commitment list or edit commitment panel to update commitment
	 * @param view The view, which needs to be closed after edit is entered
	 * @param commitmentToUpdate commitment to update
	 */
	public UpdateCommitmentController(CommitmentTabPanel view, Commitment commitmentToUpdate) {
		this.model = CommitmentListModel.getCommitmentListModel();
		this.view = view;
		this.commitmentToUpdate = commitmentToUpdate;
	}
	
	/**
	 * This constructor is used by views for updating dragged commitments
	 * @param updatedCommitment commitment being updated
	 */
	public UpdateCommitmentController(Commitment updatedCommitment) {
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
	
	/**
	 * Makes a request to the server to update a commitment based on the unique idea of the commitment
	 */
	public void updateCommitmentInServer() {
		// Create a Post Request
		final Request request = Network.getInstance().makeRequest("calendar/commitment", HttpMethod.POST);
		
		// Put the new message in the body of the request
		request.setBody(commitmentToUpdate.toJSON()); 
		
		// Add an observer to process the response
		request.addObserver(new UpdateCommitmentObserver(this));
		
		// Send the request
		request.send();
	}

	/**
	 * Updates the model with the new commitment recieved from the server
	 * @param newCommitment commitment to be added to the model
	 */
	public void updateCommitmentInModel(Commitment newCommitment) {

		if (commitmentToUpdate != null) {
			model.updateCommitment(commitmentToUpdate, newCommitment);
			
			if (view != null) {
				view.closeCommitmentPanel();
			}
		}
	}

}
