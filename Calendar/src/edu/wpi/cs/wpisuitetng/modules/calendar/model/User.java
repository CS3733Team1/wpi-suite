package edu.wpi.cs.wpisuitetng.modules.calendar.model;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;


public class User extends AbstractModel {
	
	private String userName;
	private String name;
	
	public User(){}
	
	public User(String userName, String name) {
		this.userName = userName;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public String getUserName() {
		return userName;
	}

	@Override
	public String toJSON() {
		//name, dueDate, description, category
		String str = new Gson().toJson(this, User.class);
		return str;
	}


	/**
	 * Parses a single JSON string into an Event
	 * @param input A string containing a single serialized Event
	 * @return The Event represented by input
	 */

	public static User fromJSON(String input) {
		final Gson parser = new Gson();
		return parser.fromJson(input, User.class);
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Boolean identify(Object o) {
		// TODO Auto-generated method stub
		return null;
	}
}
