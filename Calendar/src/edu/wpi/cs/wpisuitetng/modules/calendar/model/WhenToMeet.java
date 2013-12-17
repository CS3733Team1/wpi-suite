package edu.wpi.cs.wpisuitetng.modules.calendar.model;

import java.util.ArrayList;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.calendar.view.scheduledevent.Hour;

public class WhenToMeet extends DeletableAbstractModel {

	private final ArrayList< ArrayList<Hour> > hours;// = new ArrayList<ArrayList<Hour>>();
	private String name;
	private boolean [] daysoccuring;
	
	public WhenToMeet()
	{
		
	}
	public WhenToMeet(ArrayList< ArrayList< Hour > > thehours, String thename, boolean [] days)
	{
		this.name = thename;
		daysoccuring = days;
		this.hours = thehours;
	}
	public WhenToMeet(WhenToMeet other)
	{
		super(other);
		this.name = other.name;
		this.daysoccuring = other.daysoccuring;
		this.hours = other.hours;
		
	}
	@Override
	public String toJSON() {
		//name, dueDate, description, category
		String str = new Gson().toJson(this, WhenToMeet.class);
		return str;
	}

	/**
	 * Parses a string of an arbitrary number of JSON-serialized Events into an array of Events
	 * @param input A string of JSON Events
	 * @return An array of Events
	 */

	public static WhenToMeet[] fromJSONArray(String input) {
		final Gson parser = new Gson();
		return parser.fromJson(input, WhenToMeet[].class);
	}

	/**
	 * Parses a single JSON string into an Event
	 * @param input A string containing a single serialized Event
	 * @return The Event represented by input
	 */

	public static WhenToMeet fromJSON(String input) {
		final Gson parser = new Gson();
		return parser.fromJson(input, WhenToMeet.class);
	}

}
