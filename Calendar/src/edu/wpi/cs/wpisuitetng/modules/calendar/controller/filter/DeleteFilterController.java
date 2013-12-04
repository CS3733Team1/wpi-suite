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
	FilterListModel model;

	public DeleteFilterController(){
		model = FilterListModel.getFilterListModel();
	}

	/**
	 * Handles the pressing of the Remove Commitment button
	 */

	public void deleteFilters(List<Filter> list) {
		for (Filter filt: list) {
			if(!model.isDefault(filt)) {
				model.removeFilter(filt);
				// Send a request to the core to save this message 
				final Request request = Network.getInstance().makeRequest("calendar/filter/"+filt.getUniqueID(), HttpMethod.GET); // PUT == create
				request.addHeader("X-HTTP-Method-Override", "DELETE");
				//request.setBody(cat.toJSON()); // put the new message in the body of the request
				request.addObserver(new DeleteFilterObserver(this)); // add an observer to process the response
				request.send(); // send the request
			}
		}
	}

	public void removeFilterToModel(Filter filt){
		System.out.println("Deleting Filter");
		model.removeFilter(filt);
	}
}
