package edu.wpi.cs.wpisuitetng.modules.calendar.model;


public class Event {
	// Required parameters
	private String name;
	private DateTime startDateTime;
	private DateTime endDateTime;

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

	public Event(String name, DateTime startDateTime, DateTime endDateTime) {
		this.name = name;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
	}
	
	public Event(String name, DateTime startDateTime, DateTime endDateTime, String description) {
		this(name, startDateTime, endDateTime);
		this.description = description;
	}
	
	public Event(String name, DateTime startDateTime, DateTime endDateTime, Category category) {
		this(name, startDateTime, endDateTime);
		this.category = category;
	}
	
	public Event(String name, DateTime startDateTime, DateTime endDateTime, String description, Category category) {
		this(name, startDateTime, endDateTime);
		this.description = description;
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DateTime getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(DateTime startDateTime) {
		this.startDateTime = startDateTime;
	}
	
	public DateTime getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(DateTime endDateTime) {
		this.endDateTime = endDateTime;
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
}
