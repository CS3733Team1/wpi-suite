package edu.wpi.cs.wpisuitetng.modules.calendar.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.CalendarModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarView;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class AddEventController implements ActionListener{
	CalendarModel model;
	CalendarView view;
	
	public AddEventController(CalendarView view, CalendarModel model){
		this.model = model;
		this.view = view;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		Event eve = new Event("Hello", new Date(), new Date());
		
		// Send a request to the core to save this message
		final Request request = Network.getInstance().makeRequest("calendar/event", HttpMethod.PUT); // PUT == create
		request.setBody(eve.toJSON()); // put the new message in the body of the request
		request.addObserver(new AddEventObserver(this)); // add an observer to process the response
		request.send(); // send the request
		
		
	}
	
	public void addEventToModel(Event eve){
		System.out.println("Shots  ADded Fired");
		model.addEventFromCalendar(eve);
	}

}
