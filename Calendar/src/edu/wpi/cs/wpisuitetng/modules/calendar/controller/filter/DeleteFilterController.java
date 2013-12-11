/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.controller.filter;

import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.Filter;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.FilterListModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class DeleteFilterController{
	private FilterListModel model;

	public DeleteFilterController(){
		model = FilterListModel.getFilterListModel();
	}

	/**
	 * Handles the pressing of the Remove Filter button
	 */
	public void deleteFilters(List<Filter> filterList) {
		for (Filter filter: filterList) {
			if(!model.isDefault(filter)) {
				System.out.println("Deleting filter: name = " + filter.getName() + "; uid = " + filter.getUniqueID());

				// Create a Delete Request
				final Request request = Network.getInstance().makeRequest("calendar/filter/" + filter.getUniqueID(), HttpMethod.DELETE);

				// Add an observer to process the response
				request.addObserver(new DeleteFilterObserver());
				
				// Send the request
				request.send();

				// We must remove the filter without knowing the result of the server's response because
				// of a bug in Java in which you cannot set the body of a HTTP.DELETE request.
				model.removeFilter(filter);
			}
		}
	}
}
