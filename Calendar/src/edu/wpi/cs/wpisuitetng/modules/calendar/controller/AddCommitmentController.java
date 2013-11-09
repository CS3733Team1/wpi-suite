package edu.wpi.cs.wpisuitetng.modules.calendar.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.CalendarModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.DateTime;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarView;
import edu.wpi.cs.wpisuitetng.modules.postboard.controller.AddMessageRequestObserver;
import edu.wpi.cs.wpisuitetng.modules.postboard.model.PostBoardMessage;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class AddCommitmentController implements ActionListener{
	CalendarModel model;
	CalendarView view;
	
	public AddCommitmentController(CalendarView view, CalendarModel model){
		this.model = model;
		this.view = view;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		
		// Send a request to the core to save this message
		final Request request = Network.getInstance().makeRequest("postboard/postboardmessage", HttpMethod.PUT); // PUT == create
		request.setBody(new Commitment("Hello", new DateTime()).toJSON()); // put the new message in the body of the request
		request.addObserver(new AddCommitmentObserver(this)); // add an observer to process the response
		request.send(); // send the request

	}
	
	public void addCommitmentToModel(){
		
	}

}
