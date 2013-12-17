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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.category.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.category.CategoryListModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class RetrieveCategoryController implements AncestorListener, ActionListener{
	private CategoryListModel model;

	public RetrieveCategoryController() {
		this.model = CategoryListModel.getCategoryListModel();
	}

	public void retrieveMessages() {
		System.out.println("Retrieving categories from server...");

		// Create a Get Request
		final Request request = Network.getInstance().makeRequest("calendar/category", HttpMethod.GET);

		// Add an observer to process the response
		request.addObserver(new RetrieveCategoryObserver(this));

		// Send the request
		request.send();
	}

	/**
	 * Add the given messages to the local model (they were received from the core).
	 * This method is called by the GetMessagesRequestObserver
	 * 
	 * @param messages an array of messages received from the server
	 */
	public void receivedMessages(Category[] categories) {		
		// Make sure the response was not null
		if (categories != null) {
			model.setCategories(categories);
			System.out.println("	Retrieved " + categories.length + " categories from the server.");
		}
	}

	/**
	 * Whenever the Calendar Module Tab is viewed it will refetch Categories from the server.
	 * This also runs on first launch.
	 */
	@Override
	public void ancestorAdded(AncestorEvent e) {
		this.retrieveMessages();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.retrieveMessages();
	}

	// Unused
	@Override
	public void ancestorMoved(AncestorEvent e) {}
	@Override
	public void ancestorRemoved(AncestorEvent e) {}
}
