package edu.wpi.cs.wpisuitetng.modules.calendar.model;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

import java.util.Date;


//BUGBUG why is this here?
//import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

public class Commitment extends AbstractModel {
	// Required parameters
	private String name;
	private Date dueDate;

	// Optional parameters
	private String description;
	private Category category;

	public Commitment()
	{
		//lulululululululul/EntityManager
	}
	
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

	public void save() {
		// TODO Auto-generated method stub
		
	}

	public void delete() {
		// TODO Auto-generated method stub
		
	}

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

}
