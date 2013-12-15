package edu.wpi.cs.wpisuitetng.modules.calendar.controller.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JEditorPane;

import edu.wpi.cs.wpisuitetng.modules.calendar.controller.commitment.UpdateCommitmentObserver;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.CommitmentListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.EventListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.commitment.CommitmentTabPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.event.EventTabPanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class UpdateEventController implements ActionListener
{
	EventListModel model;
	EventTabPanel view;
	Event oldEvent;
	Event eventToUpdate;
	
	public UpdateEventController(EventTabPanel view, Event oldEvent) {
		this.model = EventListModel.getEventListModel();
		this.view = view;
		this.oldEvent = oldEvent;
	}
	
	public UpdateEventController(Event updatedEvent)
	{
		System.out.println(updatedEvent);
		this.model = EventListModel.getEventListModel();
		this.eventToUpdate = updatedEvent;
		updateEventInServer();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		eventToUpdate = view.getFilledEvent();
		eventToUpdate.setUniqueID(oldEvent.getUniqueID());
		eventToUpdate.setOwnerID(oldEvent.getOwnerID());
		eventToUpdate.setOwnerName(oldEvent.getOwnerName());
		
		updateEventInServer();
	}
	
	public void updateEventInServer()
	{
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

	public void updateEventInModel(Event event) 
	{
		if (oldEvent != null)
		{
			System.out.println("	Update event: name = " + event.getName() + "; uid = " + event.getUniqueID());
			model.updateEvent(oldEvent, event);
			
			if (view != null)
			{
				view.closeEventPanel();
			}
		}		
	}
}
