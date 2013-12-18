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

public class WhenToMeetEntityManager implements EntityManager<WhenToMeet> {
	/** The database */
	final Data db;
	public static WhenToMeetEntityManager EManager;

	/**
	 * Constructs the entity manager. This constructor is called by
	 * {@link edu.wpi.cs.wpisuitetng.ManagerLayer#ManagerLayer()}. To make sure
	 * this happens, be sure to place add this entity manager to the map in
	 * the ManagerLayer file.
	 * 
	 * @param db a reference to the persistent database
	 */
	public static WhenToMeetEntityManager getWhenToMeetEntityManager(Data db) {
		EManager = (EManager == null) ? new WhenToMeetEntityManager(db) : EManager;
		return EManager;
	}

	private WhenToMeetEntityManager(Data db) {
		this.db = db;
	}

	/*
	 * Saves a WhenToMeet when it is received from a client
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#makeEntity(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public WhenToMeet makeEntity(Session s, String content)
			throws BadRequestException, ConflictException, WPISuiteException {
		// Parse the message from JSON
		final WhenToMeet newMessage = WhenToMeet.fromJSON(content);

		newMessage.setOwnerName(s.getUsername());
		newMessage.setOwnerID(s.getUser().getIdNum());

		// Until we find a id that is unique assume another WhenToMeet might already have it
		boolean unique;
		long id = 0;
		do {
			unique = true;
			id = UUID.randomUUID().getMostSignificantBits();
			for(WhenToMeet e : this.getAll(s)) if (e.getUniqueID() == id) unique = false;
		} while(!unique);

		newMessage.setUniqueID(id);
		System.out.printf("Server: Creating new WhenToMeet with id = %s and owner = %s\n", newMessage.getUniqueID(), newMessage.getOwnerName());

		// Save the message in the database if possible, otherwise throw an exception
		// We want the message to be associated with the project the user logged in to
		if (!db.save(newMessage, s.getProject())) {
			throw new WPISuiteException();
		}

		// Return the newly created message (this gets passed back to the client)
		return newMessage;
	}

	/*
	 * WhenToMeets can be retrieved
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getEntity(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public WhenToMeet[] getEntity(Session s, String id)
			throws NotFoundException, WPISuiteException {
		// Throw an exception if an ID was specified, as this module does not support
		// retrieving specific WhenToMeets.
		System.out.printf("Server: Retrieving WhenToMeet with id = %s\n", id);
		List<Model> list = db.retrieve(WhenToMeet.class,"UniqueID", Long.parseLong(id), s.getProject());
		System.out.println("List size = " + list.size());
		return list.toArray(new WhenToMeet[list.size()]);
	}

	/* 
	 * Returns all of the messages that have been stored.
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getAll(edu.wpi.cs.wpisuitetng.Session)
	 */
	@Override
	public WhenToMeet[] getAll(Session s) throws WPISuiteException {
		// Ask the database to retrieve all objects of the type WhenToMeet.
		// Passing a dummy WhenToMeet lets the db know what type of object to retrieve
		// Passing the project makes it only get messages from that project
		System.out.println("Server: Retrieving all WhenToMeets");
		
		List<Model> messages = db.retrieveAll(new WhenToMeet(), s.getProject());

		// Return the list of messages as an array
		return messages.toArray(new WhenToMeet[0]);
	}

	/*
	 * Message cannot be updated. This method always throws an exception.
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#update(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public WhenToMeet update(Session s, String content)
			throws WPISuiteException {

		WhenToMeet updatedWhenToMeet = WhenToMeet.fromJSON(content);
		/*
		 * Because of the disconnected objects problem in db4o, we can't just save Commitments.
		 * We have to get the original defect from db4o, copy properties from updatedCommitment,
		 * then save the original Commitment again.
		 */
		List<Model> oldWhenToMeets = db.retrieve(WhenToMeet.class, "UniqueID", updatedWhenToMeet.getUniqueID(), s.getProject());
		//System.out.println(oldCommitments.toString());
		if(oldWhenToMeets.size() < 1 || oldWhenToMeets.get(0) == null) {
			throw new BadRequestException("WhenToMeet with ID does not exist.");
		}

		WhenToMeet existingWhenToMeet = (WhenToMeet)oldWhenToMeets.get(0);		

		// copy values to old commitment and fill in our changeset appropriately
		existingWhenToMeet = new WhenToMeet(updatedWhenToMeet);

		if(!db.save(existingWhenToMeet, s.getProject())) {
			throw new WPISuiteException();
		}

		return existingWhenToMeet;
	}

	/*
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#save(edu.wpi.cs.wpisuitetng.Session, edu.wpi.cs.wpisuitetng.modules.Model)
	 */
	@Override
	public void save(Session s, WhenToMeet model)
			throws WPISuiteException {

		// Save the given defect in the database
		db.save(model);
	}

	public void deleteWhenToMeet(WhenToMeet model){
		db.delete(model);
	}

	/*
	 * WhenToMeets totally can be deleted
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteEntity(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public boolean deleteEntity(Session s, String id) throws WPISuiteException {
		System.out.printf("Server: Deleting WhenToMeet with id = %s\n", id);
		try {
			WhenToMeet todelete = (WhenToMeet) db.retrieve(WhenToMeet.class, "UniqueID", Long.parseLong(id), s.getProject()).get(0);
			this.deleteWhenToMeet(todelete);
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

		// This module does not allow WhenToMeets to be deleted, so throw an exception
		db.deleteAll(new WhenToMeet());
	}

	/*
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#Count()
	 */
	@Override
	public int count() throws WPISuiteException {
		// Return the number of WhenToMeets currently in the database
		return db.retrieveAll(new WhenToMeet()).size();
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
