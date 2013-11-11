package edu.wpi.cs.wpisuitetng.modules.calendar.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.CalendarModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.EventEntityManager;
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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		System.out.println("Attempt Delete 2");
		
		int[] index = view.getCalendarPanel().getSelectedPanel().getSelectedIndices();
		
		if(index.length == 0)
		{
			System.out.println("YA DUN GOOFED");
			return;
		}
		
		for (int x = index.length-1; x >= 0; x--){
			System.out.println("index: " + index[x]);

			Commitment commit = (Commitment) model.getcommitModel().getElement(index[x]);
			//remove this later
			
			commit.setName(EventEntityManager.DELETESYMBOL+commit.getName());
			model.removeCommitment(commit);
			// Send a request to the core to save this message 
			final Request request = Network.getInstance().makeRequest("calendar/commitment", HttpMethod.PUT); // PUT == create
			request.setBody(commit.toJSON()); // put the new message in the body of the request
			request.addObserver(new RemoveCommitmentObserver(this)); // add an observer to process the response
			request.send(); // send the request
		}
		
		view.getCalendarPanel().RefreshSelectedPanel();
		
	}
	
	public void removeCommitmentToModel(Commitment commit){
		System.out.println("Deleting");
		model.removeCommitment(commit);
	}

}
