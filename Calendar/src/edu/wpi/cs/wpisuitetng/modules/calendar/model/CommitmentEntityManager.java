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

public class CommitmentEntityManager implements EntityManager<Commitment> {
	/** The database */
	final Data db;
	public static CommitmentEntityManager CManager;

	
	/**
	 * Constructs the entity manager. This constructor is called by
	 * {@link edu.wpi.cs.wpisuitetng.ManagerLayer#ManagerLayer()}. To make sure
	 * this happens, be sure to place add this entity manager to the map in
	 * the ManagerLayer file.
	 * 
	 * @param db a reference to the persistent database
	 */
	public static CommitmentEntityManager getCommitmentEntityManager(Data db)
	{
		CManager = (CManager == null) ? new CommitmentEntityManager(db) : CManager;
		return CManager;
			
	}
	
	private CommitmentEntityManager(Data db) {
		this.db = db;
	}

	/*
	 * Saves a Commitment when it is received from a client
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#makeEntity(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public Commitment makeEntity(Session s, String content)
			throws BadRequestException, ConflictException, WPISuiteException {

		// Parse the message from JSON
		final Commitment newMessage = Commitment.fromJSON(content);
		
		//System.out.println("EM: marked for delete:" + newMessage.isMarkedForDeletion());
		
		newMessage.setOwnerName(s.getUsername());
		newMessage.setOwnerID(s.getUser().getIdNum());
		//until we find a id that is unique assume another event might alreayd have it
		boolean found=true;
		long id=0;
		while (found)
		{
			id = UUID.randomUUID().getMostSignificantBits();
			for (Commitment e : this.getAll(s) )
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
	 * Individual commitments can be retrieved. 
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getEntity(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public Commitment[] getEntity(Session s, String id)
			throws NotFoundException, WPISuiteException {
		return (Commitment []) (db.retrieve(this.getClass(),"UniqueID", id, s.getProject()).toArray());
	}

	/* 
	 * Returns all of the messages that have been stored.
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getAll(edu.wpi.cs.wpisuitetng.Session)
	 */
	@Override
	public Commitment[] getAll(Session s) throws WPISuiteException {
		// Ask the database to retrieve all objects of the type Commitment.
		// Passing a dummy Commitment lets the db know what type of object to retrieve
		// Passing the project makes it only get messages from that project
		List<Model> messages = db.retrieveAll(new Commitment(), s.getProject());

		// Return the list of messages as an array
		return messages.toArray(new Commitment[0]);
	}

	/*
	 * Commitment cannot be updated. This method always throws an exception.
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#update(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public Commitment update(Session s, String content)
			throws WPISuiteException {

		Commitment updatedCommitment = Commitment.fromJSON(content);
		/*
		 * Because of the disconnected objects problem in db4o, we can't just save Commitments.
		 * We have to get the original defect from db4o, copy properties from updatedCommitment,
		 * then save the original Commitment again.
		 */
		List<Model> oldCommitments = db.retrieve(Commitment.class, "id", updatedCommitment.getID(), s.getProject());
		//System.out.println(oldCommitments.toString());
		if(oldCommitments.size() < 1 || oldCommitments.get(0) == null) {
			throw new BadRequestException("Commitment with ID does not exist.");
		}
				
		Commitment existingCommitment = (Commitment)oldCommitments.get(0);		

		// copy values to old commitment and fill in our changeset appropriately
		existingCommitment.copyFrom(updatedCommitment);
		
		if(!db.save(existingCommitment, s.getProject())) {
			throw new WPISuiteException();
		}
		
		return existingCommitment;
	}

	/*
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#save(edu.wpi.cs.wpisuitetng.Session, edu.wpi.cs.wpisuitetng.modules.Model)
	 */
	@Override
	public void save(Session s, Commitment model)
			throws WPISuiteException {
		db.save(model, s.getProject());
	}

	public void deleteCommitment(Commitment model){
		//System.out.println("made it to entitymanager delete");
		db.delete(model);
	}
	
	/*
	 * Messages cannot be deleted
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteEntity(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public boolean deleteEntity(Session s, String id) throws WPISuiteException {
		System.out.println("Commitment entiy manager delete entity id = " + id);
		try
		{
			Commitment todelete= (Commitment) db.retrieve(Commitment.class, "UniqueID", Long.parseLong(id), s.getProject()).get(0);
			deleteCommitment(todelete);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/*
	 * Commitments can be deleted
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteAll(edu.wpi.cs.wpisuitetng.Session)
	 */
	@Override
	public void deleteAll(Session s) throws WPISuiteException {

		db.deleteAll(new Commitment());
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
