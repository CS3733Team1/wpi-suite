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
	
	public void addCategory(Category cat) {
		System.out.println("Adding category...");
		
		// Send a request to the core to save this message
		final Request request = Network.getInstance().makeRequest("calendar/category", HttpMethod.PUT); // PUT == create
		request.setBody(cat.toJSON()); // put the new message in the body of the request
		request.addObserver(new AddCategoryObserver(this)); // add an observer to process the response
		request.send(); // send the request
	}
	
	public void addCategoryToModel(Category cat){
		System.out.println("Category added.");
		model.addCategory(cat);
	}
}
