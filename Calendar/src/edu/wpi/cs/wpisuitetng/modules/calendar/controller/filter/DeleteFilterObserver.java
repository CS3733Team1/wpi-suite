package edu.wpi.cs.wpisuitetng.modules.calendar.controller.filter;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.Filter;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

public class DeleteFilterObserver implements RequestObserver{
	private final DeleteFilterController controller;

	public DeleteFilterObserver(DeleteFilterController controller) {
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

		// Parse the message out of the response body
		final Filter filt = Filter.fromJSON(response.getBody());

		// Pass the messages back to the controller
		controller.removeFilterToModel(filt);
	}

	@Override
	public void responseError(IRequest iReq) {
		System.out.println(iReq.getResponse().getStatusMessage());
		System.err.println("The request to remove a filter failed.");
	}

	@Override
	public void fail(IRequest iReq, Exception exception) {
		System.out.println(exception);
		System.err.println("The request failed to connect to server.");
	}
}
