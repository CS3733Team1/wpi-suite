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

import edu.wpi.cs.wpisuitetng.modules.calendar.model.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.CategoryListModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class AddCategoryController {
	CategoryListModel model;

	public AddCategoryController(){
		this.model = CategoryListModel.getCategoryListModel();
	}

	public void addCategory(Category category) {
		System.out.println("Adding category: name = " + category.getName() + "; uid = " + category.getUniqueID());

		// Create a Put Request
		final Request request = Network.getInstance().makeRequest("calendar/category", HttpMethod.PUT);

		// Put the new message in the body of the request
		request.setBody(category.toJSON()); 

		// Add an observer to process the response
		request.addObserver(new AddCategoryObserver(this));

		// Send the request
		request.send();
	}

	public void addCategoryToModel(Category category){
		System.out.println("	Added category: name = " + category.getName() + "; uid = " + category.getUniqueID());
		model.addCategory(category);
	}
}
