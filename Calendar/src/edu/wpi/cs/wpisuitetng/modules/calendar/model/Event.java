/*******************************************************************************
 * Copyright (c) 2013 WPI-Suitex
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.model;

import java.util.Date;

import com.google.gson.Gson;

/**
 * Data class for Events
 * An event must have a name, a start Date, and an end Date. It can contain a custom
 * description and category.
 */
public class Event extends DeletableAbstractModel {
	// Required parameters
	private String name;
	private Date startDate;
	private Date endDate;
	

	// Optional parameters
	private String description;
	private Category category;
	

	/*
	 * TO DO:
	 * 
	 * Participants [Add from existing project members]
	 * Recurring events
	 * 
	 * Optional: All day events [Uses just start time's Date] Placed in a
	 * separate area above hours]
	 */

	public Event(){}
	
	/**
	 * A copy constructor to preserve UniqueID's when copying events.
	 * @param other event to copy
	 * @return a copy of other
	 */
	public Event(Event other)
	{
		super();
		System.out.println("Event Copy Constructor");
		this.UniqueID=other.getUniqueID();
		this.OwnerID=other.getOwnerID();
		this.OwnerName=other.getOwnerName();
		this.name = other.name;
		this.startDate = other.startDate;
		this.endDate = other.endDate;
		this.description=other.description;
		this.category=other.category;
	}
	
	public Event(String name, Date startDate, Date endDate) {
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.isTeam = false;
	}
	
	public Event(String name, Date startDate, Date endDate, boolean isTeam) {
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.isTeam = isTeam;
	}
	
	public Event(String name, Date startDate, Date endDate, String description) {
		this(name, startDate, endDate);
		this.description = description;
		this.isTeam = false;
	}
	
	public Event(String name, Date startDate, Date endDate, String description, boolean isTeam) {
		this(name, startDate, endDate, isTeam);
		this.description = description;
	}
	
	public Event(String name, Date startDate, Date endDate, Category category) {
		this(name, startDate, endDate);
		this.category = category.cloneFake();
		this.isTeam = false;
	}
	
	public Event(String name, Date startDate, Date endDate, Category category, boolean isTeam) {
		this(name, startDate, endDate, isTeam);
		this.category = category.cloneFake();
	}
	
	public Event(String name, Date startDate, Date endDate, String description, Category category) {
		this(name, startDate, endDate);
		this.description = description;
		this.category = category.cloneFake();
		this.isTeam = false;
	}
	
	public Event(String name, Date startDate, Date endDate, String description, Category category,
				boolean isTeam) {
		this(name, startDate, endDate, isTeam);
		this.description = description;
		this.category = category.cloneFake();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	public boolean getTeam()
	{
		return isTeam;
	}
	
	public void setTeam(boolean isTeam)
	{
		this.isTeam = isTeam;
	}
	
	@Override
	public String toJSON() {
		//name, dueDate, description, category
		String str = new Gson().toJson(this, Event.class);
		System.err.println(str);
		return str;
	}
	
	/**
	 * Parses a string of an arbitrary number of JSON-serialized Events into an array of Events
	 * @param input A string of JSON Events
	 * @return An array of Events
	 */
	
	public static Event[] fromJSONArray(String input)
	{
		final Gson parser = new Gson();
		System.err.println(input);
		return parser.fromJson(input, Event[].class);
	}
	
	/**
	 * Parses a single JSON string into an Event
	 * @param input A string containing a single serialized Event
	 * @return The Event represented by input
	 */
	
	public static Event fromJSON(String input)
	{
		final Gson parser = new Gson();
		return parser.fromJson(input, Event.class);
	}
	
	
	@Override
	/**
	 * @return Returns a human-readable String describing this Event
	 */
	public String toString()
	{
		//TODO: Remember to change this when participants, recurrence etc. gets added
		String str = "UniqueId = " + this.UniqueID;
				str += "Name: " + this.name + " Start Date: " + this.startDate.toString()
				+ " End Date: " + this.endDate.toString();
		if(this.category != null)
			str += " Category: " + this.category.toJSON();
		if(this.description != null)
			str += " Description: " + this.description;
		str += "\n";
		return str;
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete() {
		this.markForDeletion();
		
	}

	@Override
	public Boolean identify(Object o) {
		// TODO Auto-generated method stub
		return null;
	}
}
