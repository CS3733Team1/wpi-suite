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


//BUGBUG why is this here?
//import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

public class Commitment extends DeletableAbstractModel implements Comparable<Commitment>{
	// Required parameters
	private String name;
	private Date dueDate;

	// Optional parameters
	private String description;
	private Category category;
	
	public Commitment(){}
	
	public Commitment(String name, Date dueDate) {
		this.name = name;
		this.dueDate = dueDate;
	}

	public Commitment(String name, Date dueDate, String description) {
		this(name, dueDate);
		this.description = description;
	}

	public Commitment(String name, Date dueDate, Category category) {
		this(name, dueDate);
		this.category = category;
	}

	public Commitment(String name, Date dueDate, String description,
			Category category) {
		this(name, dueDate);
		this.description = description;
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
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

	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		this.markForDeletion();
	}

	@Override
	public String toJSON() {
		//name, dueDate, description, category
		String str = new Gson().toJson(this, Commitment.class);
		return str;
	}
	
	public static Commitment[] fromJSONArray(String input)
	{
		final Gson parser = new Gson();
		return parser.fromJson(input, Commitment[].class);
	}
	
	public static Commitment fromJSON(String input)
	{
		final Gson parser = new Gson();
		return parser.fromJson(input, Commitment.class);
	}
	
	
	
	@Override
	public String toString()
	{
		String str = "Name: " + this.name + " Due Date: " + this.dueDate.toString();
		if(this.category != null)
			str += " Category: " + this.category;
		if(this.description != null)
			str += " Description: " + this.description;
		str += "\n";
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
	 *  @return Returns 1 if this commitment begins after the given commitment
	 *          Returns 0 if the commitments begin at the same time.
	 *          Returns -1 if this commitment begins before the given commitment
	 */
	@Override
	public int compareTo(Commitment commitment) 
	{
		if (this.dueDate.after(commitment.getDueDate()) == true)
		{
			return 1;
		}//end if
		else if (this.dueDate.before(commitment.getDueDate()) == true)
		{
			return -1;
		}//end else if
		else 
		{
			return 0;
		}//end else
	}//end compareTo
}//end Commitment
