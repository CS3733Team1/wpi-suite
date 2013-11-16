package edu.wpi.cs.wpisuitetng.modules.calendar.controller.category;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.calendar.controller.commitment.RemoveCommitmentObserver;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.CategoryListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.MainModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.MainView;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class RemoveCategoryController implements ActionListener{
	CategoryListModel model;
	
	public RemoveCategoryController(){
		this.model = CategoryListModel.getCategoryModel();
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
		request.addObserver(new RemoveCategoryObserver(this)); // add an observer to process the response
		request.send(); // send the request

		
	}
	
	public void removeCategoryToModel(Category cat){
		System.out.println("Deleting");
		model.removeCategory(cat);
	}
}
