/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Tart of War
 * 
 * 
 * I, TARTICUS
 ******************************************************************************/
package Backend;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.HashSet;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.UnauthorizedException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.calendar.mock.MockData;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.CommitmentEntityManager;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.EventEntityManager;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.Role;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

public class EventBackendTest {

	static MockData db;
	static User USER;
	static Session SESSION;
	static String SSID;
	static EventEntityManager manager;
	static Project project;
	

	@Before
	public void setUp() throws Exception {
		USER = new User("admin", "admin", "password", 7);
		USER.setRole(Role.ADMIN);
		project = new Project("Project", "7");
		SSID = "SSID123";
		SESSION = new Session(USER, project, SSID);
		db = new MockData(new HashSet<Object>());
		db.save(USER);
		manager = EventEntityManager.getEventEntityManager(db);
	}

	/**
	 * Stores a new requirement and ensures the correct data was stored
	
	 * @throws WPISuiteException */
	@Test
	public void testMakeEntity() throws WPISuiteException {
		Event com1 = manager.makeEntity(SESSION, new Event().toJSON());
		assertNotNull(com1.getUniqueID());
		assertEquals(USER.getUsername(),com1.getOwnerName());
		assertEquals(USER.getIdNum(),com1.getOwnerID());
	}
	
	//@throws WPISuiteException
	@Test 
	public void testGetAll() throws WPISuiteException
	{
		testMakeEntity();
		Event [] coms = manager.getAll(SESSION);
		assertTrue(1 <= coms.length);
	}
	@Test
	public void testGetSpecific() throws WPISuiteException
	{
		testMakeEntity();
		Event [] coms = manager.getAll(SESSION);
		for (Event com : coms)
		{
			System.out.println(com.getUniqueID());
		}
		assertTrue(1 <= coms.length);
		Event com1 = coms[0];
		System.out.println("Com1 id" + com1.getUniqueID());
		Event com2 = manager.getEntity(SESSION,String.format("%d", com1.getUniqueID()))[0];
		assertEquals(com1.getUniqueID(),com2.getUniqueID());
	}
	
	@Test
	public void testDeleteSpecific() throws WPISuiteException
	{
		testMakeEntity();
		Event [] coms = manager.getAll(SESSION);
		int length = coms.length;
		Event com1 = coms[0];
		System.out.println("Com1 id" + com1.getUniqueID());
		manager.deleteEntity(SESSION, String.format("%d",com1.getUniqueID()));
		assertTrue(manager.getAll(SESSION).length == length -1);
	}
	@Test
	public void testDeleteAll() throws WPISuiteException
	{
		manager.deleteAll(SESSION);
		assertTrue(manager.getAll(SESSION).length == 0);
	}
	@Test
	public void testUpdate() throws WPISuiteException
	{

		testMakeEntity();
		Event [] coms = manager.getAll(SESSION);
		int length = coms.length;
		Event com1 = coms[0];
		long bobsuid = com1.getUniqueID();
		com1.setName("BOB THE BUILDER");
		manager.update(SESSION,com1.toJSON());
		Event com2 = manager.getEntity(SESSION,String.format("%d",bobsuid))[0];
		assertTrue(com1.getName().equals(com2.getName()));
		
		
	}
	
}
