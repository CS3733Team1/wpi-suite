package edu.wpi.cs.wpisuitetng.modules.calendar.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.CalendarModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarView;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class RemoveCommitmentController implements ActionListener{
	CalendarModel model;
	CalendarView view;
	
	public RemoveCommitmentController(CalendarView view, CalendarModel model){
		this.model = model;
		this.view = view;
	}
	
	public void actionPerformed(ActionEvent e) {
		
		System.out.println("Attempt Delete 2");
		
		int[] index = view.getCalendarPanel().getSelectedPanel().getSelectedIndices();
		
		System.out.println("index: " + index[0]);
		
		Commitment commit = (Commitment) model.getcommitModel().getElement(index[0]);
		model.removeCommitment(commit);
		
		// Send a request to the core to save this message
		final Request request = Network.getInstance().makeRequest("calendar/commitment", HttpMethod.DELETE); // PUT == create
		request.setBody(commit.toJSON()); // put the new message in the body of the request
		request.addObserver(new RemoveCommitmentObserver(this)); // add an observer to process the response
		request.send(); // send the request
		
		
	}
	
	public void removeCommitmentToModel(Commitment commit){
		System.out.println("Deleting");
		model.removeCommitment(commit);
	}

}
