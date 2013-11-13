package edu.wpi.cs.wpisuitetng.modules.calendar.model;

import java.util.List;

public class CalendarObjectModel {
	private String title;
	private List<Commitment> commitments;
	private List<Event> events;

	public CalendarObjectModel(String title) {
		this.title = title;

	}

	public String getTitle() {
		return title;
	}

}
