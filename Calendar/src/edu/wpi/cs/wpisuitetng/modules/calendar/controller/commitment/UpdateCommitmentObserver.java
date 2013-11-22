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
 * to the server to update a commitment.
 */

public class UpdateCommitmentObserver implements RequestObserver
{
	private final UpdateCommitmentController controller;
	
	/**
	 * Constructs the observer given an UpdateCommitmentController
	 * @param controller The controller used to update commitments
	 */
	public UpdateCommitmentObserver(UpdateCommitmentController controller) 
	{
		this.controller = controller;
	}
	
	@Override
	public void responseSuccess(IRequest iReq) 
	{
		// Get the response to the given request
		final ResponseModel response = iReq.getResponse();
		
		// Parse the requirement out of the response body
		final Commitment commitment = Commitment.fromJSON(response.getBody());	
		// Pass the messages back to the controller
		controller.updateCommitmentModel(commitment);
	}
	
	@Override
	public void responseError(IRequest iReq) 
	{
		System.err.println("The request to update a commitment failed.");
		System.err.println("Response: " + iReq.getResponse().getStatusMessage());
	}

	@Override
	public void fail(IRequest iReq, Exception exception) 
	{
		System.err.println("The request to update a commitment failed.");
	}

}
