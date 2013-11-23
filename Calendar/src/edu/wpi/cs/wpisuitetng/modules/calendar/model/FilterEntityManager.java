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

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.BadRequestException;
import edu.wpi.cs.wpisuitetng.exceptions.ConflictException;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.EntityManager;
import edu.wpi.cs.wpisuitetng.modules.Model;

public class FilterEntityManager implements EntityManager<Filter> {
	/** The database */
	final Data db;
	public static FilterEntityManager FManager;

	
	/**
	 * Constructs the entity manager. This constructor is called by
	 * {@link edu.wpi.cs.wpisuitetng.ManagerLayer#ManagerLayer()}. To make sure
	 * this happens, be sure to place add this entity manager to the map in
	 * the ManagerLayer file.
	 * 
	 * @param db a reference to the persistent database
	 */
	public static FilterEntityManager getFilterEntityManager(Data db) {
		FManager = (FManager == null) ? new FilterEntityManager(db) : FManager;
		return FManager;
			
	}
	
	private FilterEntityManager(Data db) {
		this.db = db;
	}

	/*
	 * Saves a Filter when it is received from a client
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#makeEntity(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public Filter makeEntity(Session s, String content)
			throws BadRequestException, ConflictException, WPISuiteException {
		
		// Parse the message from JSON
		final Filter newMessage = Filter.fromJSON(content);
		
		if (newMessage.isMarkedForDeletion())
		{
			newMessage.unmarkForDeletion();
			deleteFilter(newMessage);
			return newMessage;
		}
		
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
	public Filter[] getEntity(Session s, String id)
			throws NotFoundException, WPISuiteException {
		return (Filter []) (db.retrieve(this.getClass(),"UniqueID", id, s.getProject()).toArray());
	}

	/* 
	 * Returns all of the messages that have been stored.
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getAll(edu.wpi.cs.wpisuitetng.Session)
	 */
	@Override
	public Filter[] getAll(Session s) throws WPISuiteException {
		// Ask the database to retrieve all objects of the type Commitment.
		// Passing a dummy Commitment lets the db know what type of object to retrieve
		// Passing the project makes it only get messages from that project
		List<Model> messages = db.retrieveAll(new Filter(), s.getProject());

		// Return the list of messages as an array
		return messages.toArray(new Filter[0]);
	}

	/*
	 * Message cannot be updated. This method always throws an exception.
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#update(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public Filter update(Session s, String content)
			throws WPISuiteException {

		// This module does not allow Commitments to be modified, so throw an exception
		throw new WPISuiteException();
	}

	/*
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#save(edu.wpi.cs.wpisuitetng.Session, edu.wpi.cs.wpisuitetng.modules.Model)
	 */
	@Override
	public void save(Session s, Filter model)
			throws WPISuiteException {
		
		if (model.isMarkedForDeletion())
		{
			deleteFilter(model);
			return;
		}
		// Save the given defect in the database
		db.save(model);
	}

	public void deleteFilter(Filter model){
		db.delete(model);
	}
	
	/*
	 * Messages cannot be deleted
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteEntity(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public boolean deleteEntity(Session s, String id) throws WPISuiteException {
		System.out.println("Filter entiy manager delete entity id = " + id);
		try
		{
			Filter todelete = (Filter) db.retrieve(Filter.class, "UniqueID", Integer.parseInt(id), s.getProject()).get(0);
			deleteFilter(todelete);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/*
	 * Messages cannot be deleted
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteAll(edu.wpi.cs.wpisuitetng.Session)
	 */
	@Override
	public void deleteAll(Session s) throws WPISuiteException {
		db.deleteAll(new Filter());
	}

	/*
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#Count()
	 */
	@Override
	public int Count() throws WPISuiteException {
		// Return the number of Commitments currently in the database
		return db.retrieveAll(new Commitment()).size();
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
