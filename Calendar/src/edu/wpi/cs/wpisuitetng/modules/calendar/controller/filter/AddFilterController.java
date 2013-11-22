package edu.wpi.cs.wpisuitetng.modules.calendar.controller.filter;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.Filter;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.FilterListModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class AddFilterController{
	FilterListModel model;
	
	public AddFilterController(){
		this.model = FilterListModel.getFilterListModel();
	}
	
	public void addFilter(Filter filt) {
	
		System.out.println("Adding filter...");
		
		// Send a request to the core to save this message
		final Request request = Network.getInstance().makeRequest("calendar/filter", HttpMethod.PUT); // PUT == create
		request.setBody(filt.toJSON()); // put the new message in the body of the request
		request.addObserver(new AddFilterObserver(this)); // add an observer to process the response
		request.send(); // send the request
		
	}
	
	public void addFilterToModel(Filter filt){
		System.out.println("Filter added.");
		model.addFilter(filt);
	}
}
