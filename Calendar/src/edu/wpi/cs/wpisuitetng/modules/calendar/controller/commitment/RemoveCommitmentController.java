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
import edu.wpi.cs.wpisuitetng.modules.calendar.view.MainView;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class RemoveCommitmentController implements ActionListener{
	MainModel model;
	MainView view;
	
	public RemoveCommitmentController(MainView view, MainModel model){
		this.model = model;
		this.view = view;
	}
	
	/**
	 * Handles the pressing of the Remove Commitment button
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		System.out.println("Attempt Delete 2");
		
		int[] index = view.getCalendarPanel().getSelectedCommitmentsInList().getSelectedIndices();
		
		if(index.length == 0)
		{
			System.out.println("YA DUN GOOFED");
			return;
		}
		
		for (int x = index.length-1; x >= 0; x--){
			System.out.println("index: " + index[x]);

			Commitment commit = (Commitment) model.getCommitmentModel().getElement(index[x]);
			//remove this later
			
			//System.out.println("controller: marking for deletion");
			commit.markForDeletion();
			
			//System.out.println("delete: " + commit.isMarkedForDeletion());
			
			model.removeCommitment(commit);
			// Send a request to the core to save this message 
			final Request request = Network.getInstance().makeRequest("calendar/commitment", HttpMethod.PUT); // PUT == create
			request.setBody(commit.toJSON()); // put the new message in the body of the request
			//System.out.println("controller: sending this for deletion: " + commit.toJSON());
			request.addObserver(new RemoveCommitmentObserver(this)); // add an observer to process the response
			request.send(); // send the request
		}
		
		view.getCalendarPanel().refreshSelectedPanel();
		
	}
	
	public void removeCommitmentToModel(Commitment commit){
		System.out.println("Deleting");
		model.removeCommitment(commit);
	}

}
