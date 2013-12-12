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
	public void deleteCategories(List<Category> categoryList) {
		for (Category category: categoryList) {
			if(!model.isDefault(category)) {
				System.out.println("Deleting category: name = " + category.getName() + "; uid = " + category.getUniqueID());

				// Create a Delete Request
				final Request request = Network.getInstance().makeRequest("calendar/category/" + category.getUniqueID(), HttpMethod.DELETE);

				// Add an observer to process the response
				request.addObserver(new DeleteCategoryObserver());
				
				// Send the request
				request.send();

				// We must remove the category without knowing the result of the server's response because
				// of a bug in Java in which you cannot set the body of a HTTP.DELETE request.
				model.removeCategory(category);
			}
		}
	}
}
