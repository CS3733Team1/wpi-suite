package edu.wpi.cs.wpisuitetng.modules.calendar.model.scheduledevent;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.DeletableAbstractModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.event.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.scheduledevent.Hour;

public class ScheduledEvent extends DeletableAbstractModel {

	private List< List<Hour> > hours;// = new ArrayList<ArrayList<Hour>>();
	private  ArrayList<String> dayOfWeek;
	private String title;
	
	public ScheduledEvent()
	{
		hours=null;
		dayOfWeek=null;
		title = null;
	}
	public ScheduledEvent(List<List<Hour>> thehours, ArrayList<String> dayOfWeek, String title)
	{
		this.dayOfWeek = dayOfWeek;
		this.hours = thehours;
		this.title= title;
	}
	@Override
	public String toJSON() {
		//name, dueDate, description, category
		String str = new Gson().toJson(this, ScheduledEvent.class);
		return str;
	}

	/**
	 * Parses a string of an arbitrary number of JSON-serialized Events into an array of Events
	 * @param input A string of JSON Events
	 * @return An array of Events
	 */

	public static ScheduledEvent[] fromJSONArray(String input) {
		final Gson parser = new Gson();
		return parser.fromJson(input, ScheduledEvent[].class);
	}

	/**
	 * Parses a single JSON string into an Event
	 * @param input A string containing a single serialized Event
	 * @return The Event represented by input
	 */

	public static ScheduledEvent fromJSON(String input) {
		final Gson parser = new Gson();
		return parser.fromJson(input, ScheduledEvent.class);
	}
	
	public void copyFrom(ScheduledEvent e) {
		this.hours = e.hours;
		this.dayOfWeek = e.dayOfWeek;
		this.title = e.title;
		this.uniqueID = e.uniqueID;
		this.ownerID = e.ownerID;

		this.ownerName = e.ownerName;
	}
	public List<List<Hour>> getHourList() {
		return hours;
	}
	public String getTitle() {
		return title;
	}
	public ArrayList<String> getDays() {
		return dayOfWeek;
	}
}
