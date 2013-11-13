package edu.wpi.cs.wpisuitetng.modules.calendar.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.CalendarModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.EventEntityManager;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarView;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class RemoveEventController implements ActionListener{
	CalendarModel model;
	CalendarView view;
	
	public RemoveEventController(CalendarView view, CalendarModel model){
		this.model = model;
		this.view = view;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		System.out.println("Attempt Delete 2");
		
		int[] index = view.getCalendarPanel().getSelectedEventList().getSelectedIndices();
		
		
		for (int x = index.length-1; x >= 0; x--){
			System.out.println("index: " + index[x]);

			Event eve = (Event) model.getcommitModel().getElement(index[x]);
			//remove this later
			eve.setName(EventEntityManager.DELETESYMBOL+eve.getName());
			model.removeEvent(eve);

			// Send a request to the core to save this message
			final Request request = Network.getInstance().makeRequest("calendar/commitment", HttpMethod.DELETE); // PUT == create
			request.setBody(eve.toJSON()); // put the new message in the body of the request
			request.addObserver(new RemoveEventObserver(this)); // add an observer to process the response
			request.send(); // send the request
		}
		
		view.getCalendarPanel().RefreshSelectedPanel();
		
	}
	
	public void removeEventToModel(Event eve){
		System.out.println("Deleting");
		model.removeEvent(eve);
	}

}
