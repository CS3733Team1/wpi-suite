/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.model.commitment;

import java.text.DateFormat;
import java.util.Date;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.DeletableAbstractModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.ISchedulable;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.category.Category;

/**
 * Class for holding Commitment data. A Commitment must have:<br>
 * -A name<br>
 * -A Date<br>
 * <br>
 * A Commitment can have:<br>
 * -A description<br>
 * -A Category<br>
 * -A completion State<br>
 * <br>
 * Privately, a Commitment has a uniqueID to help distinguish it in the database.
 */
public class Commitment extends DeletableAbstractModel implements ISchedulable {

	public static enum State {
		NEW ("New"),
		IN_PROGRESS ("In Progress"),
		COMPLETE ("Complete");

		private String display;

		private State(String display) {
			this.display = display;
		}

		public String toString() {
			return display;
		}
	}

	// Required parameters
	private String name;
	private Date dueDate;

	// Optional parameters
	private String description;
	private Category category;
	private State progress;

	//------------ATTENTION---------
	//Should Category have an empty constructor?
	//It has required fields that should constitute
	//the arguments of the most basic constructor.
	public Commitment(){}

	//Commitment copy constructor
	public Commitment(Commitment other) {
		//Commitment(String name, Date dueDate, boolean Teamhuh, String description,
		//Category category, String progress)
		this(other.name,other.dueDate,other.isTeam,other.description,other.category);
		this.progress=other.progress;
		this.uniqueID=other.uniqueID;
		this.ownerID=other.ownerID;
		this.ownerName=other.ownerName;
	}
	/**
	 * Constructs a Commitment object
	 * @param name name of Commitment
	 * @param dueDate due date of Commitment
	 */
	public Commitment(String name, Date dueDate, boolean Teamhuh) {
		this.name = name;
		this.dueDate = dueDate;
		this.progress = State.NEW; //Default
		this.isTeam = Teamhuh;

	}

	/**
	 * Constructs a Commitment object
	 * @param name name of Commitment
	 * @param dueDate due date of Commitment
	 * @param description description of Commitment
	 */
	public Commitment(String name, Date dueDate, boolean Teamhuh, String description) {
		this(name, dueDate, Teamhuh);
		this.description = description;
		this.progress = State.NEW; //Default
	}

	/**
	 * Constructs a Commitment
	 * @param name name of Commitment
	 * @param dueDate due date of Commitment
	 * @param category Category of Commitment
	 */
	public Commitment(String name, Date dueDate, boolean Teamhuh, Category category) {
		this(name, dueDate, Teamhuh);
		this.category = category.cloneFake();
		this.progress = State.NEW; //Default
	}

	/**
	 * Constructs a Commitment
	 * @param name name of Commitment
	 * @param dueDate due date of Commitment
	 * @param description description of Commitment
	 * @param category Category of Commitment
	 */
	public Commitment(String name, Date dueDate, boolean Teamhuh, String description,
			Category category) {
		this(name, dueDate, Teamhuh);
		this.description = description;
		this.category = category.cloneFake();
		this.progress = State.NEW; //Default
	}

	/**
	 * Constructs a Commitment
	 * @param name name of Commitment
	 * @param dueDate due date of Commitment
	 * @param description description of Commitment
	 * @param progress progress of Commitment
	 */
	public Commitment(String name, Date dueDate, boolean Teamhuh, String description, 
			String progress) {
		this(name, dueDate, Teamhuh);
		this.description = description;
		this.progress=getStateFromString(progress);
	}

	/**
	 * Constructs a Commitment
	 * @param name name of Commitment
	 * @param dueDate due date of Commitment
	 * @param category category of Commitment
	 * @param progress progress of Commitment
	 */
	public Commitment(String name, Date dueDate, boolean Teamhuh, Category category, 
			String progress) {
		this(name, dueDate, Teamhuh);
		this.category = category.cloneFake();
		this.progress = getStateFromString(progress);
	}

	/**
	 * Constructs a Commitment
	 * @param name name of Commitment
	 * @param dueDate due date of Commitment
	 * @param description description of Commitment
	 * @param category category of Commitment
	 * @param progress progress of Commitment
	 */
	public Commitment(String name, Date dueDate, boolean Teamhuh, String description,
			Category category, String progress) {
		this(name, dueDate, Teamhuh);
		this.description = description;
		this.category = category.cloneFake();
		this.progress = getStateFromString(progress);
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
	public String getProgress() {
		return progress.toString();
	}

	private State getProgressState(){
		return progress;
	}

	/**
	 * @param category the Category object to set the Commitment's Category to
	 */
	public void setProgress(State progress) {
		this.progress = progress;
	}

	private State getStateFromString(String stateString){
		switch(stateString){
		case "New": 
			return State.NEW;
		case "In Progress": 
			return State.IN_PROGRESS;
		case "Complete": 
			return State.COMPLETE;
		}
		return null;
	}

	/**
	 * @return a JSON String representation of this Commitment
	 */
	@Override
	public String toJSON() {
		//name, dueDate, description, category
		String str = new Gson().toJson(this, Commitment.class);
		return str;
	}

	/**
	 * Generates an array of Commitments from a JSON String
	 * @param input JSON String containing an arbitrary number of serialized Commitments
	 * @return An array of Commitments
	 */
	public static Commitment[] fromJSONArray(String input) {
		final Gson parser = new Gson();
		return parser.fromJson(input, Commitment[].class);
	}

	/**
	 * Generates a single Commitment from a JSON String
	 * @param input JSON String representing one Commitment
	 * @return Commitment built from the input
	 */
	public static Commitment fromJSON(String input) {
		final Gson parser = new Gson();
		return parser.fromJson(input, Commitment.class);
	}
	/**
	 * @return A human-readable string representation of this class
	 */
	@Override
	public String toString() {
		
		String str = "<html><b>Name:</b>  " + getName() +
				"<br><b>Due Date:</b>  " + DateFormat.getInstance().format(getStartDate());
		if(this.category != null)
			str += "<br><b>Category:</b> " + getCategory().getName();

		str += String.format("<br><b>Progress:</b> %s", progress.toString());

		str += String.format("<br><b>Calendar:</b> %s", (this.isTeam) ? "Team" : "Personal");

		if(this.description != null)
			str += "<br><b>Description:</b> " + getDescription();

		str += "</html>";

		return str;
	}

	/** 
	 *  This function compares another Commitment to this Commitment and 
	 *  decides if this Commitment begins before, after, or at the same time.
	 *  
	 *  @return Returns <b>-1</b> if this Commitment begins after the input Commitment<br>
	 *          Returns <b>0</b> if both Commitments begin at the same time.<br>
	 *          Returns <b>1</b> if this Commitment begins before the input Commitment
	 */
	@Override
	public int compareTo(ISchedulable other) {
		if (this.dueDate.after(other.getStartDate())) return -1;
		else if (this.dueDate.before(other.getStartDate())) return 1;
		else return 0;
	}

	/**
	 * Copies all of the fields from the input Commitment into this Commitment.
	 * @param toCopyFrom The Commitment to copy
	 */
	public void copyFrom(Commitment toCopyFrom) {
		this.category = toCopyFrom.getCategory();
		this.description = toCopyFrom.getDescription();
		this.dueDate = toCopyFrom.getDueDate();
		this.name = toCopyFrom.getName();
		this.isTeam = toCopyFrom.getisTeam();
		this.progress = toCopyFrom.getProgressState();
		this.uniqueID = toCopyFrom.getUniqueID();
		this.ownerID = toCopyFrom.getOwnerID();
		this.ownerName = toCopyFrom.getOwnerName();
		
	}

	@Override
	public Date getStartDate() {
		return this.dueDate;
	}

	@Override
	public Date getEndDate() {
		Date temp = (Date)this.dueDate.clone();
		temp.setMinutes(temp.getMinutes() + 30);
		return temp;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (o == this)
			return true;
		if (!(o instanceof Commitment))
			return false;

		Commitment commitmentOther = (Commitment)o;
		return this.getUniqueID() == commitmentOther.getUniqueID();
	}
	
	@Override
	public int hashCode() {
		return (int)this.getUniqueID();
	}
}
