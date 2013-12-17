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

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.category.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.event.Event;

public class EventTest {

	Date Date1;
	Date Date2;
	Date Date3;
	Date Date4;
	Category cat1;
	Category cat2;
	Event Event1;
	Event Event2;
	Event Event3;
	Event Event4;
	
	@Before
	public void setUp() {
		Date1 = new Date(113, 11, 11);
		Date2 = new Date(93, 6, 5);
		Date3 = new Date(100, 1, 1, 10, 15);
		Date4 = new Date(100, 1, 1, 11, 30);
		cat1 = new Category("school", Color.blue);
		cat2 = new Category("Work", Color.red);
		Event1 = new Event("Meeting1",Date2,Date1);
		Event2 = new Event("Birthday",Date1,Date1, true, "Kyles Birthday");
		Event3 = new Event("Class",Date2,Date2, true, cat1);
		Event4 = new Event("Meeting2",Date3,Date4,true, "CS Meeting",cat1);
	}
	
	@Test
	public void testEventname() {
		assertEquals("Meeting1",Event1.getName());
		Event1.setName("Soft Eng");
		assertEquals("Soft Eng",Event1.getName());
	}
	
	@Test
	public void testEventStartDate() {
		assertEquals(Date1,Event2.getStartDate());
		Event2.setStartDate(Date4);
		assertEquals(Date4,Event2.getStartDate());
	}
	
	@Test
	public void testEventEndDate() {
		assertEquals(Date2,Event3.getEndDate());
		Event3.setEndDate(Date3);
		assertEquals(Date3,Event3.getEndDate());
	}
		
	@Test
	public void testEventDescription() {
		assertEquals("Kyles Birthday",Event2.getDescription());
		Event2.setDescription("Alecs Birthday");
		assertEquals("Alecs Birthday", Event2.getDescription());
	}
	
	@Test
	public void testEventCategory() {
		assertEquals(cat1,Event3.getCategory());
		Event3.setCategory(cat2);
		assertEquals(cat2,Event3.getCategory());
	}
	
	@Test
	public void testEventJSONConversion()
	{
		assertEquals(Event4, Event4);
		Event anEvent = Event.fromJSON(Event4.toJSON());
		assertEquals(anEvent, anEvent);
		assertEquals("Event converted toJSON and back to object should be equal to original object", Event4, anEvent);
	}
}
