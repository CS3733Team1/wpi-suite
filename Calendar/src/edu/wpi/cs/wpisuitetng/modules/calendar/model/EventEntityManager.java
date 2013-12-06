/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.model;

import java.util.List;
import java.util.UUID;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.BadRequestException;
import edu.wpi.cs.wpisuitetng.exceptions.ConflictException;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.EntityManager;
import edu.wpi.cs.wpisuitetng.modules.Model;

public class EventEntityManager implements EntityManager<Event> {
	/** The database */
	final Data db;
	public static EventEntityManager EManager;
	
	/**
	 * Constructs the entity manager. This constructor is called by
	 * {@link edu.wpi.cs.wpisuitetng.ManagerLayer#ManagerLayer()}. To make sure
	 * this happens, be sure to place add this entity manager to the map in
	 * the ManagerLayer file.
	 * 
	 * @param db a reference to the persistent database
	 */
	public static EventEntityManager getEventEntityManager(Data db)
	{
		EManager = (EManager == null) ? new EventEntityManager(db) : EManager;
		return EManager;
	}
	
	private EventEntityManager(Data db) {
		this.db = db;
	}

	/*
	 * Saves a Event when it is received from a client
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#makeEntity(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public Event makeEntity(Session s, String content)
			throws BadRequestException, ConflictException, WPISuiteException {
		System.out.println("Trying to make an event");
			
		// Parse the message from JSON
		final Event newMessage = Event.fromJSON(content);
		
		newMessage.setOwnerName(s.getUsername());
		newMessage.setOwnerID(s.getUser().getIdNum());
		//until we find a id that is unique assume another event might alreayd have it
		boolean found=true;
		long id=0;
		while (found)
		{
			id = UUID.randomUUID().getMostSignificantBits();
			for (Event e : this.getAll(s) )
			{
				if (e.getUniqueID()==id)
				{
					found=true;
				}
			}
			found=false;
		}
		newMessage.setUniqueID(id);
		System.out.printf("Server: Creating new event entity with id = %s and owner = %s\n",newMessage.getUniqueID(),newMessage.getOwner());
		
		// Save the message in the database if possible, otherwise throw an exception
		// We want the message to be associated with the project the user logged in to
		if (!db.save(newMessage, s.getProject())) {
			throw new WPISuiteException();
		}

		// Return the newly created message (this gets passed back to the client)
		return newMessage;
	}

	/*
	 * Individual messages cannot be retrieved. This message always throws an exception.
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getEntity(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public Event[] getEntity(Session s, String id)
			throws NotFoundException, WPISuiteException {
		// Throw an exception if an ID was specified, as this module does not support
		// retrieving specific Events.
		throw new WPISuiteException();
	}

	/* 
	 * Returns all of the messages that have been stored.
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getAll(edu.wpi.cs.wpisuitetng.Session)
	 */
	@Override
	public Event[] getAll(Session s) throws WPISuiteException {
		// Ask the database to retrieve all objects of the type Event.
		// Passing a dummy Event lets the db know what type of object to retrieve
		// Passing the project makes it only get messages from that project
		List<Model> messages = db.retrieveAll(new Event(), s.getProject());

		// Return the list of messages as an array
		return messages.toArray(new Event[0]);
	}

	/*
	 * Message cannot be updated. This method always throws an exception.
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#update(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public Event update(Session s, String content)
			throws WPISuiteException {

		// This module does not allow Events to be modified, so throw an exception
		throw new WPISuiteException();
	}

	/*
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#save(edu.wpi.cs.wpisuitetng.Session, edu.wpi.cs.wpisuitetng.modules.Model)
	 */
	@Override
	public void save(Session s, Event model)
			throws WPISuiteException {

		// Save the given defect in the database
		db.save(model);
	}

	public void deleteEvent(Event model){
		db.delete(model);
	}
	
	/*
	 * Events totally can be deleted
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteEntity(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public boolean deleteEntity(Session s, String id) throws WPISuiteException {
				System.out.println("Trying to delete an event with id = " + id);
				try {
				Event todelete = (Event) db.retrieve(Event.class, "UniqueID", Long.parseLong(id), s.getProject()).get(0);
				this.deleteEvent(todelete);
				}
				catch (Exception e) {
					e.printStackTrace();
					return false;
				}
				
				return true;
	}

	/*
	 * Messages cannot be deleted
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteAll(edu.wpi.cs.wpisuitetng.Session)
	 */
	@Override
	public void deleteAll(Session s) throws WPISuiteException {

		// This module does not allow Events to be deleted, so throw an exception
		throw new WPISuiteException();
	}

	/*
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#Count()
	 */
	@Override
	public int Count() throws WPISuiteException {
		// Return the number of Events currently in the database
		return db.retrieveAll(new Event()).size();
	}

	@Override
	public String advancedGet(Session s, String[] args)
			throws WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String advancedPut(Session s, String[] args, String content)
			throws WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String advancedPost(Session s, String string, String content)
			throws WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}
}
