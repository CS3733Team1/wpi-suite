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

import edu.wpi.cs.wpisuitetng.modules.calendar.model.commitment.Commitment;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/**
 * This observer is called when a response is received from a request
 * to the server to update a commitment.
 */

public class UpdateCommitmentObserver implements RequestObserver {
	private final UpdateCommitmentController controller;

	/**
	 * Constructs the observer given an UpdateCommitmentController
	 * @param controller The controller used to update commitments
	 */
	public UpdateCommitmentObserver(UpdateCommitmentController controller) {
		this.controller = controller;
	}

	@Override
	public void responseSuccess(IRequest iReq) {
		System.out.println("	The request to update the commitment was succesful.");

		// Parse the message out of the response body
		final Commitment commitment = Commitment.fromJSON(iReq.getResponse().getBody());

		// Pass the messages back to the controller
		controller.updateCommitmentInModel(commitment);
	}

	/*
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseError(IRequest iReq) {
		System.err.println("	" + iReq.getResponse().getStatusMessage());
		System.err.println("	Failed to update the commitment on the server.");
	}

	/*
	 * Put an error message in the PostBoardPanel if the request fails.
	 * 
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(edu.wpi.cs.wpisuitetng.network.models.IRequest, java.lang.Exception)
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		System.err.println("	" + exception);
		System.err.println("	The request failed to connect to the server.");
	}
}