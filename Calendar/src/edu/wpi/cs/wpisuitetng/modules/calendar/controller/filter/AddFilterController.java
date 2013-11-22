package edu.wpi.cs.wpisuitetng.modules.calendar.controller.filter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Filter;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.FilterListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.filter.FilterPanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class AddFilterController implements ActionListener{
	FilterListModel model;
	
	public AddFilterController(FilterPanel filterPanel){
		model = FilterListModel.getFilterListModel();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		Filter filt = new Filter("A FILTER" , new ArrayList<Category>());
	
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
