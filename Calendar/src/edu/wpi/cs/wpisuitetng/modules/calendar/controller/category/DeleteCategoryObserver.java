/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.controller.category;

import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

public class DeleteCategoryObserver implements RequestObserver {
	/*
	 * Parse the message that was received from the server then pass them to
	 * the controller.
	 * 
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		// The response will not include a body as this is HTTP.DELETE
		// Do nothing
		System.out.println("	The request to delete a category was successful.");
	}

	@Override
	public void responseError(IRequest iReq) {
		System.err.println("	" + iReq.getResponse().getStatusMessage());
		System.err.println("	The request to remove a category failed.");
	}

	@Override
	public void fail(IRequest iReq, Exception exception) {
		System.err.println("	" + exception);
		System.err.println("	The request failed to connect to the server.");
	}
}
