/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Chris Casola
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.CalendarModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This controller coordinates retrieving all of the messages
 * from the server. This controller is called when the user
 * clicks the refresh button.
 * 
 * @author Chris Casola
 *
 */
public class RetrieveEventController implements ActionListener, KeyListener, MouseListener {
	CalendarModel model;

	public RetrieveEventController(CalendarModel model) {
		this.model = model;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		grabMessages();
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		grabMessages();
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		grabMessages();
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		grabMessages();
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		grabMessages();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		return;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		return;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		return;
	}

	@Override
	public void mouseExited(MouseEvent e) {
		return;
	}
	
	public void grabMessages(){
		System.out.println("Reloading");
		// Send a request to the core to save this message
		
		final Request request = Network.getInstance().makeRequest("calendar/event", HttpMethod.GET); // GET == read
		request.addObserver(new RetrieveEventObserver(this)); // add an observer to process the response
		request.send(); // send the request
	}
	
	/**
	 * Add the given messages to the local model (they were received from the core).
	 * This method is called by the GetMessagesRequestObserver
	 * 
	 * @param messages an array of messages received from the server
	 */
	public void receivedMessages(Event[] event) {
		// Empty the local model to eliminate duplications
		model.getEventModel().emptyModel();
		
		// Make sure the response was not null
		if (event != null) {
			
			// add the messages to the local model
			model.getEventModel().addEvents(event);
		}
	}


}
