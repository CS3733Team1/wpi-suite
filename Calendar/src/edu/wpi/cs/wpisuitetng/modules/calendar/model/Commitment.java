package edu.wpi.cs.wpisuitetng.modules.calendar.model;


public class Commitment {
	// Required parameters
	private String name;
	private DateTime dueDate;

	// Optional parameters
	private String description;
	private Category category;

	public Commitment(String name, DateTime dueDate) {
		this.name = name;
		this.dueDate = dueDate;
	}

	public Commitment(String name, DateTime dueDate, String description) {
		this(name, dueDate);
		this.description = description;
	}

	public Commitment(String name, DateTime dueDate, Category category) {
		this(name, dueDate);
		this.category = category;
	}

	public Commitment(String name, DateTime dueDate, String description,
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

	public DateTime getDueDate() {
		return dueDate;
	}

	public void setDueDate(DateTime dueDate) {
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
}
