/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.controller.commitment;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.EventEntityManager;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.MainModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.commitment.AddCommitmentTabPanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class AddCommitmentController implements ActionListener{
	MainModel model;
	AddCommitmentTabPanel view;
	
	public AddCommitmentController(AddCommitmentTabPanel view, MainModel model){
		this.model = model;
		this.view = view;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		Commitment commit = view.getDisplayCommitment();
		
		//set name encoding to ASCII so it can't have the delete character
		if(commit.getName().length() > EventEntityManager.DELETESYMBOL.length() && commit.getName().substring(0, EventEntityManager.DELETESYMBOL.length()).equals(EventEntityManager.DELETESYMBOL))
		{
			System.err.println("Commitment names cannot begin with " + EventEntityManager.DELETESYMBOL);
			return;
		}
		
		//don't need this because server works now
		//addCommitmentToModel(commit);
		System.out.println("Shots Fired 2");
		// Send a request to the core to save this message
		final Request request = Network.getInstance().makeRequest("calendar/commitment", HttpMethod.PUT); // PUT == create
		request.setBody(commit.toJSON()); // put the new message in the body of the request
		request.addObserver(new AddCommitmentObserver(this)); // add an observer to process the response
		request.send(); // send the request
		view.killPanel();
		
	}
	
	public void addCommitmentToModel(Commitment commit){
		System.out.println("Shots  ADded Fired");
		model.addCommitmentFromCalendar(commit);
	}

}
