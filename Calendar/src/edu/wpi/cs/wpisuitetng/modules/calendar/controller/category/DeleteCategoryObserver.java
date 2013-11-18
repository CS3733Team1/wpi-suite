package edu.wpi.cs.wpisuitetng.modules.calendar.controller.category;

import edu.wpi.cs.wpisuitetng.modules.calendar.controller.commitment.DeleteCommitmentController;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

public class DeleteCategoryObserver implements RequestObserver{

	private final DeleteCategoryController controller;

	public DeleteCategoryObserver(DeleteCategoryController controller) {
		this.controller = controller;
	}

	/*
	 * Parse the message that was received from the server then pass them to
	 * the controller.
	 * 
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		// Get the response to the given request
		final ResponseModel response = iReq.getResponse();
		//Response Will not include a body.... this is HTTP.delete
		System.out.println("Request to delete category was succesful");
		
		/*
		// Parse the message out of the response body
		final Category cat = Category.fromJSON(response.getBody());

		// Pass the messages back to the controller
		controller.removeCategoryToModel(cat); */
	}

	@Override
	public void responseError(IRequest iReq) {
		System.out.println(iReq.getResponse().getStatusMessage());
		System.err.println("The request to remove a category failed.");
	}

	@Override
	public void fail(IRequest iReq, Exception exception) {
		System.out.println(exception);
		System.err.println("The request failed to connect to server.");
	}
}