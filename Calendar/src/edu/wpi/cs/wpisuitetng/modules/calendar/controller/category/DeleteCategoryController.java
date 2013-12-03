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

import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.CategoryListModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class DeleteCategoryController {
	private CategoryListModel model;

	public DeleteCategoryController(){
		this.model = CategoryListModel.getCategoryListModel();
	}

	/**
	 * Handles the pressing of the Remove Category button
	 */
	public void deleteCategories(List<Category> list) {
		
		for (Category cat: list) {
			// Send a request to the core to save this message 
			final Request request = Network.getInstance().makeRequest("calendar/category/"+cat.getUniqueID(), HttpMethod.GET); // PUT == create
			request.addHeader("X-HTTP-Method-Override", "DELETE");
			//request.setBody(cat.toJSON()); // put the new message in the body of the request
			request.addObserver(new DeleteCategoryObserver(this)); // add an observer to process the response
			request.send(); // send the request
			model.removeCategory(cat);
		}
	}

	
	// WHAT is the point of this method if it is removed above???
	public void removeCategoryToModel(Category cat){
		System.out.println("Deleting Category");
		model.removeCategory(cat);
	}
}
