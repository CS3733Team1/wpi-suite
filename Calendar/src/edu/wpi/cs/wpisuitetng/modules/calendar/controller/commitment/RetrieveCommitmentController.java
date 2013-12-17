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

import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.commitment.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.commitment.CommitmentListModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This controller coordinates retrieving all of the messages
 * from the server. This controller is called when the user
 * clicks the refresh button.
 *
 */
public class RetrieveCommitmentController implements AncestorListener, ActionListener {
	private CommitmentListModel model;

	public RetrieveCommitmentController() {
		this.model = CommitmentListModel.getCommitmentListModel();
	}

	public void retrieveMessages(){
		System.out.println("Retrieving commitments from server...");

		// Create a Get Request
		final Request request = Network.getInstance().makeRequest("calendar/commitment", HttpMethod.GET);

		// Add an observer to process the response
		request.addObserver(new RetrieveCommitmentObserver(this));

		// Send the request
		request.send();
	}

	/**
	 * Add the given messages to the local model (they were received from the core).
	 * This method is called by the GetMessagesRequestObserver
	 * 
	 * @param messages an array of messages received from the server
	 */
	public void receivedMessages(Commitment[] commitments) {		
		// Make sure the response was not null
		if (commitments != null) {
			model.setCommitments(commitments);
			System.out.println("	Retrieved " + commitments.length + " commitments from the server.");
		}
	}

	/**
	 * Whenever the Calendar Module Tab is viewed it will refetch Commitments from the server.
	 * This also runs on first launch.
	 */
	@Override
	public void ancestorAdded(AncestorEvent e) {
		this.retrieveMessages();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.retrieveMessages();
	}

	// Unused
	@Override
	public void ancestorMoved(AncestorEvent e) {}
	@Override
	public void ancestorRemoved(AncestorEvent e) {}
}
