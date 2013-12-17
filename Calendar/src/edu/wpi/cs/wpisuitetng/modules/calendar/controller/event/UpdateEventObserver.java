package edu.wpi.cs.wpisuitetng.modules.calendar.controller.event;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.event.Event;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

public class UpdateEventObserver implements RequestObserver{

	private final UpdateEventController controller;

	/**
	 * Constructs the observer given an UpdateCommitmentController
	 * @param controller The controller used to update commitments
	 */
	public UpdateEventObserver(UpdateEventController controller) {
		this.controller = controller;
	}

	@Override
	public void responseSuccess(IRequest iReq) {
		System.out.println("	The request to update the event was successful.");

		// Parse the message out of the response body
		final Event event = Event.fromJSON(iReq.getResponse().getBody());

		// Pass the messages back to the controller
		controller.updateEventInModel(event);
	}

	/*
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseError(IRequest iReq) {
		System.err.println("	" + iReq.getResponse().getStatusMessage());
		System.err.println("	Failed to update the event on the server.");
	}

	/*
	 * Put an error message in the PostBoardPanel if the request fails.
	 * 
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(edu.wpi.cs.wpisuitetng.network.models.IRequest, java.lang.Exception)
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		System.err.println("	" + exception);
		System.err.println("	The request failed to connect to the server.");
	}
}
