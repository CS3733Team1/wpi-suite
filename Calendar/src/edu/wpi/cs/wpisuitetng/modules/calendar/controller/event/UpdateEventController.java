package edu.wpi.cs.wpisuitetng.modules.calendar.controller.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.event.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.event.EventListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.event.EventTabPanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class UpdateEventController implements ActionListener {
	private EventListModel model;
	private EventTabPanel view;
	private Event oldEvent;
	private Event eventToUpdate;
	
	public UpdateEventController(EventTabPanel view, Event oldEvent) {
		this.model = EventListModel.getEventListModel();
		this.view = view;
		this.oldEvent = oldEvent;
	}
	
	public UpdateEventController(Event updatedEvent) {
		this.model = EventListModel.getEventListModel();
		this.eventToUpdate = updatedEvent;
		updateEventInServer();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		eventToUpdate = view.getFilledEvent();
		eventToUpdate.setUniqueID(oldEvent.getUniqueID());
		eventToUpdate.setOwnerID(oldEvent.getOwnerID());
		eventToUpdate.setOwnerName(oldEvent.getOwnerName());
		
		updateEventInServer();
	}
	
	public void updateEventInServer() {
		System.out.println("Updating event: name = " + eventToUpdate.getName() + "; uid = " + eventToUpdate.getUniqueID());

		// Create a Post Request
		final Request request = Network.getInstance().makeRequest("calendar/event", HttpMethod.POST);
		
		// Put the new message in the body of the request
		request.setBody(eventToUpdate.toJSON()); 
		
		// Add an observer to process the response
		request.addObserver(new UpdateEventObserver(this));
		
		// Send the request
		request.send();
	}

	public void updateEventInModel(Event event) {
		if (oldEvent != null) {
			model.updateEvent(oldEvent, event);
			
			if (view != null) {
				view.closeEventPanel();
			}
		}		
	}
}
