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

import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * This observer is called when a response is received from a request
 * to the server to add a message.
 * 
 * @author John Breen
 *
 */
public class DeleteCommitmentObserver implements RequestObserver {

	private final DeleteCommitmentController controller;

	public DeleteCommitmentObserver(DeleteCommitmentController controller) {
		this.controller = controller;
	}

	/*
	 * Parse the message that was received from the server then pass them to
	 * the controller.
	 * 
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		// Get the response to the given request
		System.out.println("The request to remove a commitment succeded");

	}

	@Override
	public void responseError(IRequest iReq) {
		System.out.println(iReq.getResponse().getStatusMessage());
		System.err.println("The request to remove a commitment failed.");
	}

	@Override
	public void fail(IRequest iReq, Exception exception) {
		System.out.println(exception);
		System.err.println("The request failed to connect to server.");
	}
}
