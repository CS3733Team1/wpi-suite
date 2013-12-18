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

import edu.wpi.cs.wpisuitetng.modules.calendar.model.filter.Filter;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.filter.FilterListModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class AddFilterController{
	private FilterListModel model;

	public AddFilterController(){
		this.model = FilterListModel.getFilterListModel();
	}

	public void addFilter(Filter filter) {
		System.out.println("Adding filter: name = " + filter.getName() + "; uid = " + filter.getUniqueID());

		// Create a Put Request
		final Request request = Network.getInstance().makeRequest("calendar/filter", HttpMethod.PUT);

		// Put the new message in the body of the request
		request.setBody(filter.toJSON()); 

		// Add an observer to process the response
		request.addObserver(new AddFilterObserver(this));

		// Send the request
		request.send();
	}

	public void addFilterToModel(Filter filter){
		System.out.println("	Added filter: name = " + filter.getName() + "; uid = " + filter.getUniqueID());
		model.addFilter(filter);
	}
}
