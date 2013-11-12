package edu.wpi.cs.wpisuitetng.modules.calendar.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.CalendarModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.EventEntityManager;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.CommitmentPanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class AddCommitmentController implements ActionListener{
	CalendarModel model;
	CommitmentPanel view;
	
	public AddCommitmentController(CommitmentPanel view, CalendarModel model){
		this.model = model;
		this.view = view;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		Commitment commit = view.getDisplayCommitment();
		
		//set name encoding to ASCII so it can't have the delete character
		if(commit.getName().substring(0, EventEntityManager.DELETESYMBOL.length()).equals(EventEntityManager.DELETESYMBOL))
		{
			System.err.println("Commitment names cannot begin with " + EventEntityManager.DELETESYMBOL);
			//return;
		}
		
		//don't need this because server works now
		//addCommitmentToModel(commit);
		System.out.println("Shots Fired 2");
		// Send a request to the core to save this message
		final Request request = Network.getInstance().makeRequest("calendar/commitment", HttpMethod.PUT); // PUT == create
		request.setBody(commit.toJSON()); // put the new message in the body of the request
		request.addObserver(new AddCommitmentObserver(this)); // add an observer to process the response
		request.send(); // send the request
		
		
	}
	
	public void addCommitmentToModel(Commitment commit){
		System.out.println("Shots  ADded Fired");
		model.addCommitmentFromCalendar(commit);
	}

}
