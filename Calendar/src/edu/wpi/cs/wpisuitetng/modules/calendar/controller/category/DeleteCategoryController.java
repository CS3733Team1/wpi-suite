package edu.wpi.cs.wpisuitetng.modules.calendar.controller.category;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.CategoryListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.category.CategoryTabPanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class DeleteCategoryController implements ActionListener{
	private CategoryListModel model;
	private CalendarPanel calendarPanel;

	public DeleteCategoryController(CalendarPanel calendarPanel){
		this.model = CategoryListModel.getCategoryListModel();
		this.calendarPanel = calendarPanel;
	}

	/**
	 * Handles the pressing of the Remove Category button
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		for (Category cat: ((CategoryTabPanel)calendarPanel.getSelectedComponent()).getSelectedCategories()) {
			// Send a request to the core to save this message 
			final Request request = Network.getInstance().makeRequest("calendar/category/"+cat.getUniqueID(), HttpMethod.GET); // PUT == create
			request.addHeader("X-HTTP-Method-Override", "DELETE");
			//request.setBody(cat.toJSON()); // put the new message in the body of the request
			request.addObserver(new DeleteCategoryObserver(this)); // add an observer to process the response
			request.send(); // send the request
			model.removeCategory(cat);
		}
	}

	
	// WHAT is the point of this method if it is removed above???
	public void removeCategoryToModel(Category cat){
		System.out.println("Deleting Category");
		model.removeCategory(cat);
	}
}
