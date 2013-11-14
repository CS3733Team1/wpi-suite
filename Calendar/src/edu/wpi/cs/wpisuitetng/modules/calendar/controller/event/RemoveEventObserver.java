/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.controller.event;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * This observer is called when a response is received from a request
 * to the server to add a message.
 * 
 * @author Chris Casola
 *
 */
public class RemoveEventObserver implements RequestObserver {
	
	private final RemoveEventController controller;
	
	public RemoveEventObserver(RemoveEventController controller) {
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
		final ResponseModel response = iReq.getResponse();
		
		// Parse the message out of the response body
		final Event eve = Event.fromJSON(response.getBody());
		
		// Pass the messages back to the controller
		controller.removeEventToModel(eve);
	}

	@Override
	public void responseError(IRequest iReq) {
		System.out.println(iReq.getResponse().getStatusMessage());
		System.err.println("The request to remove an event failed.");
	}

	@Override
	public void fail(IRequest iReq, Exception exception) {
		System.out.println(exception);
		System.err.println("The request failed to connect to server.");
	}
}
