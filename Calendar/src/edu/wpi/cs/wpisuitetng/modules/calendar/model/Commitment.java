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

import java.util.Date;

import com.google.gson.Gson;

/**
 * Model for holding commitment data
 */
public class Commitment extends DeletableAbstractModel implements Comparable<Commitment>{
	// Required parameters
	private String name;
	private Date dueDate;

	// Optional parameters
	private String description;
	private Category category;
	private int progress;
	
	//------------ATTENTION---------
	//Should Category have an empty constructor?
	//It has required fields that should constitute
	//the arguments of the most basic constructor.
	public Commitment(){}
	
	/**
	 * Constructs a Commitment object
	 * @param name name of Commitment
	 * @param dueDate due date of Commitment
	 */
	public Commitment(String name, Date dueDate) {
		this.name = name;
		this.dueDate = dueDate;
		this.progress = 0; //Default
	}

	/**
	 * Constructs a Commitment object
	 * @param name name of Commitment
	 * @param dueDate due date of Commitment
	 * @param description description of Commitment
	 */
	public Commitment(String name, Date dueDate, String description) {
		this(name, dueDate);
		this.description = description;
		this.progress = 0; //Default
	}

	/**
	 * Constructs a Commitment
	 * @param name name of Commitment
	 * @param dueDate due date of Commitment
	 * @param category Category of Commitment
	 */
	public Commitment(String name, Date dueDate, Category category) {
		this(name, dueDate);
		this.category = category.cloneFake();
		this.progress = 0; //Default
	}

	/**
	 * Constructs a Commitment
	 * @param name name of Commitment
	 * @param dueDate due date of Commitment
	 * @param description description of Commitment
	 * @param category Category of Commitment
	 */
	public Commitment(String name, Date dueDate, String description,
			Category category) {
		this(name, dueDate);
		this.description = description;
		this.category = category.cloneFake();
		this.progress = 0; //Default
	}
	
	/**
	 * Constructs a Commitment
	 * @param name name of Commitment
	 * @param dueDate due date of Commitment
	 * @param progress progress of Commitment
	 */
	public Commitment(String name, Date dueDate, int progress) {
		this(name, dueDate);
		this.progress = progress;
	}
	
	/**
	 * Constructs a Commitment
	 * @param name name of Commitment
	 * @param dueDate due date of Commitment
	 * @param description description of Commitment
	 * @param progress progress of Commitment
	 */
	public Commitment(String name, Date dueDate, String description, 
			int progress) {
		this(name, dueDate);
		this.description = description;
		this.progress = progress;
	}
	
	/**
	 * Constructs a Commitment
	 * @param name name of Commitment
	 * @param dueDate due date of Commitment
	 * @param category category of Commitment
	 * @param progress progress of Commitment
	 */
	public Commitment(String name, Date dueDate, Category category, 
			int progress) {
		this(name, dueDate);
		this.category = category.cloneFake();
		this.progress = progress;
	}
	
	/**
	 * Constructs a Commitment
	 * @param name name of Commitment
	 * @param dueDate due date of Commitment
	 * @param description description of Commitment
	 * @param category category of Commitment
	 * @param progress progress of Commitment
	 */
	public Commitment(String name, Date dueDate, String description,
			Category category, int progress) {
		this(name, dueDate);
		this.description = description;
		this.category = category.cloneFake();
		this.progress = progress;
	}

	/**
	 * @return the name of the Commitment as a String
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name a String holding the new name for the Commitment
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return a Date object indicating when the Commitment is due
	 */
	public Date getDueDate() {
		return dueDate;
	}

	/**
	 * @param dueDate the date when the Commitment is due, as a Date object
	 */
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	/**
	 * @return the commitment's description as a String
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description a String to set the Commitment's description to
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the Category object representing the Commitment's Category
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * @param category the Category object to set the Commitment's Category to
	 */
	public void setCategory(Category category) {
		this.category = category;
	}
	
	/**
	 * @return the Category object representing the Commitment's Category
	 */
	public int getProgress() {
		return progress;
	}

	/**
	 * @param category the Category object to set the Commitment's Category to
	 */
	public void setProgress(int progress) {
		this.progress = progress;
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
	}

	/**
	 * @return a json String representation of the Commitment
	 */
	@Override
	public String toJSON() {
		//name, dueDate, description, category
		String str = new Gson().toJson(this, Commitment.class);
		return str;
	}
	
	/**
	 * Generates an array of Commitments from a json String
	 * @param input json String representing an array of Commitments
	 * @return Commitments built from json String
	 */
	public static Commitment[] fromJSONArray(String input)
	{
		final Gson parser = new Gson();
		return parser.fromJson(input, Commitment[].class);
	}
	
	/**
	 * Generates a Commitment from a json String
	 * @param input json String representing a Commitment
	 * @return Commitment built from json String
	 */
	public static Commitment fromJSON(String input)
	{
		final Gson parser = new Gson();
		return parser.fromJson(input, Commitment.class);
	}
	
	@Override
	public String toString()
	{
		String str = "<html><b>Name:</b>  " + getName() +
				"<br><b>Due Date:</b>  " + getDueDate().toString();
		if(this.category != null)
			str += "<br><b>Category:</b> " + getCategory().getName();
		if(this.description != null)
			str += "<br><b>Description:</b> " + getDescription();
		str += String.format("<br><b>Progress:</b> %d%%</html>", getProgress());
				
		return str;
	}

	@Override
	public Boolean identify(Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	/** compareTo
	 * 
	 *  This function compares a commitment to this commitment and 
	 *  the return value decides if this commitment begins before, after,
	 *  or at the same time.
	 *  
	 *  @return Returns -1 if this commitment begins after the given commitment
	 *          Returns 0 if the commitments begin at the same time.
	 *          Returns 1 if this commitment begins before the given commitment
	 */
	@Override
	public int compareTo(Commitment commitment) 
	{
		if (this.dueDate.after(commitment.getDueDate()) == true)
		{
			return -1;
		}//end if
		else if (this.dueDate.before(commitment.getDueDate()) == true)
		{
			return 1;
		}//end else if
		else 
		{
			return 0;
		}//end else
	}//end compareTo
}//end Commitment
