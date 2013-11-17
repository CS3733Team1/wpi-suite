package edu.wpi.cs.wpisuitetng.modules.calendar.controller.category;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.CategoryListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarPanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class DeleteCategoryController implements ActionListener{
	CategoryListModel model;
	
	public DeleteCategoryController(CalendarPanel calendarPanel){
		this.model = CategoryListModel.getCategoryListModel();
	}
	
	/**
	 * Handles the pressing of the Remove Commitment button
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		Category cat = new Category();
		System.out.println("Attempt Delete 2");

		cat.markForDeletion();
		
		// Send a request to the core to save this message 
		final Request request = Network.getInstance().makeRequest("calendar/commitment", HttpMethod.PUT); // PUT == create
		request.setBody(cat.toJSON()); // put the new message in the body of the request
		request.addObserver(new DeleteCategoryObserver(this)); // add an observer to process the response
		request.send(); // send the request

		
	}
	
	public void removeCategoryToModel(Category cat){
		System.out.println("Deleting");
		model.removeCategory(cat);
	}
}
