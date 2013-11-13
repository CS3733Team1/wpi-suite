package edu.wpi.cs.wpisuitetng.modules.calendar.model;

import java.util.Date;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

public abstract class Schedulable extends AbstractModel{
	private String name;
	private Date dueDate;

	// Optional parameters
	private String description;
	private Category category;
	
	
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
	
	public int compareTo(Schedulable s){
		return 0;
	}
}