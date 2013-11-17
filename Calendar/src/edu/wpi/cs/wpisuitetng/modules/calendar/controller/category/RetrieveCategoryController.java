package edu.wpi.cs.wpisuitetng.modules.calendar.controller.category;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.CategoryListModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class RetrieveCategoryController implements AncestorListener, ActionListener{
	CategoryListModel model;

	public RetrieveCategoryController() {
		this.model = CategoryListModel.getCategoryListModel();
	}

	public void retrieveMessages() {
		System.out.println("Retrieving categories from server...");
		// Send a request to the core to save this message
		final Request request = Network.getInstance().makeRequest("calendar/category", HttpMethod.GET); // GET == read
		request.addObserver(new RetrieveCategoryObserver(this)); // add an observer to process the response
		request.send(); // send the request
	}
	
	/**
	 * Add the given messages to the local model (they were received from the core).
	 * This method is called by the GetMessagesRequestObserver
	 * 
	 * @param messages an array of messages received from the server
	 */
	public void receivedMessages(Category[] cat) {		
		// Make sure the response was not null
		if (cat != null) {
			// set the messages to the local model
			model.setCategories(cat);
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
	public void ancestorMoved(AncestorEvent e) {}

	@Override
	public void ancestorRemoved(AncestorEvent e) {}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.retrieveMessages();
	}
}
