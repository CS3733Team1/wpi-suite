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

import edu.wpi.cs.wpisuitetng.modules.calendar.controller.commitment.AddCommitmentController;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

public class AddCategoryObserver implements RequestObserver{
	private final AddCategoryController controller;
	
	public AddCategoryObserver(AddCategoryController controller) {
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
		final Category cat = Category.fromJSON(response.getBody());
		
		// Pass the messages back to the controller
		controller.addCategoryToModel(cat);
	}

	@Override
	public void responseError(IRequest iReq) {
		System.err.println("The request to add a category failed.");
		System.err.println("Response: " + iReq.getResponse().getStatusMessage());
	}

	@Override
	public void fail(IRequest iReq, Exception exception) {
		System.err.println("The request to add a category failed.");
	}
}
