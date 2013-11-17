package edu.wpi.cs.wpisuitetng.modules.calendar.controller.filter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.Filter;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.FilterListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarPanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class DeleteFilterController implements ActionListener{
	FilterListModel model;
	
	public DeleteFilterController(CalendarPanel calendarPanel){
		model = FilterListModel.getFilterListModel();
	}
	
	/**
	 * Handles the pressing of the Remove Commitment button
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		//TODO: make this work once the view exists
		System.out.println("Attempt Delete 2");
		Filter filt;

		//System.out.println("controller: marking for deletion");
		//filt.markForDeletion();

		// Send a request to the core to save this message 
		final Request request = Network.getInstance().makeRequest("calendar/commitment", HttpMethod.PUT); // PUT == create
		//request.setBody(filt.toJSON()); // put the new message in the body of the request
		//System.out.println("controller: sending this for deletion: " + commit.toJSON());
		request.addObserver(new DeleteFilterObserver(this)); // add an observer to process the response
		request.send(); // send the request

	}
	
	public void removeFilterToModel(Filter filt){
		System.out.println("Deleting");
		model.removeFilter(filt);
	}
}
