package edu.wpi.cs.wpisuitetng.modules.calendar.controller.category;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.CategoryListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarPanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class AddCategoryController implements ActionListener{
	CategoryListModel model;
	
	public AddCategoryController(CalendarPanel calendarPanel){
		this.model = CategoryListModel.getCategoryListModel();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
	
		Category cat = new Category("A NAME", Color.RED);
		System.out.println("Adding category...");
		
		// Send a request to the core to save this message
		final Request request = Network.getInstance().makeRequest("calendar/category", HttpMethod.PUT); // PUT == create
		request.setBody(cat.toJSON()); // put the new message in the body of the request
		request.addObserver(new AddCategoryObserver(this)); // add an observer to process the response
		request.send(); // send the request
	}
	
	public void addCategoryToModel(Category cat){
		System.out.println("Category added.");
		model.addCategory(cat);
	}
}
