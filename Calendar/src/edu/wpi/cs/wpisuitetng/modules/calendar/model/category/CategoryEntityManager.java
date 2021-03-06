/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.model.category;

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


/**
 * This class handles database entries for Categories. It runs server-side. It is a singleton.
 */
public class CategoryEntityManager implements EntityManager<Category> {
	/** The database */
	private final Data db;
	private static CategoryEntityManager CManager;

	/**
	 * Constructs the entity manager. This constructor is called by
	 * {@link edu.wpi.cs.wpisuitetng.ManagerLayer#ManagerLayer()}. To make sure
	 * this happens, be sure to place add this entity manager to the map in
	 * the ManagerLayer file.
	 * 
	 * @param db a reference to the persistent database
	 */
	public static CategoryEntityManager getCategoryEntityManager(Data db) {
		CManager = (CManager == null) ? new CategoryEntityManager(db) : CManager;
		return CManager;
	}

	private CategoryEntityManager(Data db) {
		this.db = db;
	}

	/**
	 * Saves a Category when it is received from a client
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#makeEntity(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public Category makeEntity(Session s, String content)
			throws BadRequestException, ConflictException, WPISuiteException {
		// Parse the message from JSON
		final Category newMessage = Category.fromJSON(content);

		newMessage.setOwnerName(s.getUser().getName());
		newMessage.setOwnerID(s.getUser().getIdNum());

		// Until we find a id that is unique assume another category might already have it
		boolean unique;
		long id = 0;
		do {
			unique = true;
			id = UUID.randomUUID().getMostSignificantBits();
			for(Category c : this.getAll(s)) if (c.getUniqueID() == id) unique = false;
		} while(!unique);

		newMessage.setUniqueID(id);
		System.out.printf("Server: Creating new category with id = %s and owner = %s\n", newMessage.getUniqueID(), newMessage.getOwnerName());

		// Save the message in the database if possible, otherwise throw an exception
		// We want the message to be associated with the project the user logged in to
		if (!db.save(newMessage, s.getProject())) {
			throw new WPISuiteException();
		}

		// Return the newly created message (this gets passed back to the client)
		return newMessage;
	}

	/**
	 * Individual messages cannot be retrieved. This message always throws an exception.
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getEntity(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public Category[] getEntity(Session s, String id)
			throws NotFoundException, WPISuiteException {
		System.out.printf("Server: Retrieving category with id = %s\n", id);
		List<Model> list = db.retrieve(Category.class,"UniqueID", Long.parseLong(id), s.getProject());
		System.out.println("List size = " + list.size());
		return list.toArray(new Category[list.size()]);
	}

	/**
	 * Returns all of the messages that have been stored.
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getAll(edu.wpi.cs.wpisuitetng.Session)
	 */
	@Override
	public Category[] getAll(Session s) throws WPISuiteException {
		// Ask the database to retrieve all objects of the type Category.
		// Passing a dummy Category lets the db know what type of object to retrieve
		// Passing the project makes it only get messages from that project
		System.out.println("Server: Retrieving all categories");
		
		List<Model> messages = db.retrieveAll(new Category(), s.getProject());

		// Return the list of messages as an array
		return messages.toArray(new Category[0]);
	}

	/**
	 * Message cannot be updated. This method always throws an exception.
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#update(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public Category update(Session s, String content)
			throws WPISuiteException {
		// This module does not allow Categories to be modified, so throw an exception
		throw new WPISuiteException();
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#save(edu.wpi.cs.wpisuitetng.Session, edu.wpi.cs.wpisuitetng.modules.Model)
	 */
	@Override
	public void save(Session s, Category model)
			throws WPISuiteException {
		// Save the given defect in the database
		db.save(model);
	}

	public void deleteCategory(Category model){
		db.delete(model);
	}

	/*
	 * Messages cannot be deleted
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteEntity(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public boolean deleteEntity(Session s, String id) throws WPISuiteException {
		System.out.printf("Server: Deleting category with id = %s\n", id);
		try {
			Category todelete= (Category) db.retrieve(Category.class, "UniqueID", Long.parseLong(id), s.getProject()).get(0);
			deleteCategory(todelete);
			return true;
		} catch (Exception e) {
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
		// This module does not allow Categories to be deleted, so throw an exception
		db.deleteAll(new Category());
	}

	/*
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#Count()
	 */
	@Override
	public int count() throws WPISuiteException {
		// Return the number of Categories currently in the database
		return db.retrieveAll(new Category()).size();
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
