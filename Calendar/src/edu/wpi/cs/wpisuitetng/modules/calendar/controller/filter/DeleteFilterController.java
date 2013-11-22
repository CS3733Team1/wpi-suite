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
			// Send a request to the core to save this message 
			final Request request = Network.getInstance().makeRequest("calendar/filter/"+filt.getUniqueID(), HttpMethod.GET); // PUT == create
			request.addHeader("X-HTTP-Method-Override", "DELETE");
			//request.setBody(cat.toJSON()); // put the new message in the body of the request
			request.addObserver(new DeleteFilterObserver(this)); // add an observer to process the response
			request.send(); // send the request
			model.removeFilter(filt);
		}
	}
	
	public void removeFilterToModel(Filter filt){
		System.out.println("Deleting Filter");
		model.removeFilter(filt);
	}
}
