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

import edu.wpi.cs.wpisuitetng.modules.calendar.model.category.Category;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

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
		System.out.println("	The request to add the category was succesful.");

		// Parse the message out of the response body
		final Category category = Category.fromJSON(iReq.getResponse().getBody());

		// Pass the messages back to the controller
		controller.addCategoryToModel(category);
	}

	/*
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseError(IRequest iReq) {
		System.err.println("	" + iReq.getResponse().getStatusMessage());
		System.err.println("	Failed to add the category to the server.");
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
