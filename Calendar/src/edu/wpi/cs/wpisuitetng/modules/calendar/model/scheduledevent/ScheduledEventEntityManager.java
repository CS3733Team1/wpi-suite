/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.model.scheduledevent;

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

public class ScheduledEventEntityManager implements EntityManager<ScheduledEvent> {
	/** The database */
	final Data db;
	public static ScheduledEventEntityManager EManager;

	/**
	 * Constructs the entity manager. This constructor is called by
	 * {@link edu.wpi.cs.wpisuitetng.ManagerLayer#ManagerLayer()}. To make sure
	 * this happens, be sure to place add this entity manager to the map in
	 * the ManagerLayer file.
	 * 
	 * @param db a reference to the persistent database
	 */
	public static ScheduledEventEntityManager getScheduledEventEntityManager(Data db) {
		EManager = (EManager == null) ? new ScheduledEventEntityManager(db) : EManager;
		return EManager;
	}

	private ScheduledEventEntityManager(Data db) {
		this.db = db;
	}

	/*
	 * Saves a ScheduledEvent when it is received from a client
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#makeEntity(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public ScheduledEvent makeEntity(Session s, String content)
			throws BadRequestException, ConflictException, WPISuiteException {
		// Parse the message from JSON
		final ScheduledEvent newMessage = ScheduledEvent.fromJSON(content);

		newMessage.setOwnerName(s.getUsername());
		newMessage.setOwnerID(s.getUser().getIdNum());

		// Until we find a id that is unique assume another ScheduledEvent might already have it
		boolean unique;
		long id = 0;
		do {
			unique = true;
			id = UUID.randomUUID().getMostSignificantBits();
			for(ScheduledEvent e : this.getAll(s)) if (e.getUniqueID() == id) unique = false;
		} while(!unique);

		newMessage.setUniqueID(id);
		System.out.printf("Server: Creating new ScheduledEvent with id = %s and owner = %s\n", newMessage.getUniqueID(), newMessage.getOwnerName());

		// Save the message in the database if possible, otherwise throw an exception
		// We want the message to be associated with the project the user logged in to
		if (!db.save(newMessage, s.getProject())) {
			throw new WPISuiteException();
		}

		// Return the newly created message (this gets passed back to the client)
		return newMessage;
	}

	/*
	 * ScheduledEvents can be retrieved
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getEntity(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public ScheduledEvent[] getEntity(Session s, String id)
			throws NotFoundException, WPISuiteException {
		// Throw an exception if an ID was specified, as this module does not support
		// retrieving specific ScheduledEvents.
		System.out.printf("Server: Retrieving ScheduledEvent with id = %s\n", id);
		List<Model> list = db.retrieve(ScheduledEvent.class,"UniqueID", Long.parseLong(id), s.getProject());
		System.out.println("List size = " + list.size());
		return list.toArray(new ScheduledEvent[list.size()]);
	}

	/* 
	 * Returns all of the messages that have been stored.
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getAll(edu.wpi.cs.wpisuitetng.Session)
	 */
	@Override
	public ScheduledEvent[] getAll(Session s) throws WPISuiteException {
		// Ask the database to retrieve all objects of the type ScheduledEvent.
		// Passing a dummy ScheduledEvent lets the db know what type of object to retrieve
		// Passing the project makes it only get messages from that project
		System.out.println("Server: Retrieving all ScheduledEvents");
		
		List<Model> messages = db.retrieveAll(new ScheduledEvent(), s.getProject());

		// Return the list of messages as an array
		return messages.toArray(new ScheduledEvent[0]);
	}

	/*
	 * Message cannot be updated. This method always throws an exception.
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#update(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public ScheduledEvent update(Session s, String content)
			throws WPISuiteException {

		ScheduledEvent updatedScheduledEvent = ScheduledEvent.fromJSON(content);
		/*
		 * Because of the disconnected objects problem in db4o, we can't just save Commitments.
		 * We have to get the original defect from db4o, copy properties from updatedCommitment,
		 * then save the original Commitment again.
		 */
		List<Model> oldScheduledEvents = db.retrieve(ScheduledEvent.class, "UniqueID", updatedScheduledEvent.getUniqueID(), s.getProject());
		//System.out.println(oldCommitments.toString());
		if(oldScheduledEvents.size() < 1 || oldScheduledEvents.get(0) == null) {
			throw new BadRequestException("ScheduledEvent with ID does not exist.");
		}

		ScheduledEvent existingScheduledEvent = (ScheduledEvent)oldScheduledEvents.get(0);		

		// copy values to old commitment and fill in our changeset appropriately
		existingScheduledEvent.copyFrom(updatedScheduledEvent);

		if(!db.save(existingScheduledEvent, s.getProject())) {
			throw new WPISuiteException();
		}

		return existingScheduledEvent;
	}

	/*
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#save(edu.wpi.cs.wpisuitetng.Session, edu.wpi.cs.wpisuitetng.modules.Model)
	 */
	@Override
	public void save(Session s, ScheduledEvent model)
			throws WPISuiteException {

		// Save the given defect in the database
		db.save(model);
	}

	public void deleteScheduledEvent(ScheduledEvent model){
		db.delete(model);
	}

	/*
	 * ScheduledEvents totally can be deleted
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteEntity(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public boolean deleteEntity(Session s, String id) throws WPISuiteException {
		System.out.printf("Server: Deleting ScheduledEvent with id = %s\n", id);
		try {
			ScheduledEvent todelete = (ScheduledEvent) db.retrieve(ScheduledEvent.class, "UniqueID", Long.parseLong(id), s.getProject()).get(0);
			this.deleteScheduledEvent(todelete);
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

		// This module does not allow ScheduledEvents to be deleted, so throw an exception
		db.deleteAll(new ScheduledEvent());
	}

	/*
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#Count()
	 */
	@Override
	public int count() throws WPISuiteException {
		// Return the number of ScheduledEvents currently in the database
		return db.retrieveAll(new ScheduledEvent()).size();
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
