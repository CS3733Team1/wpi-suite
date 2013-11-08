package edu.wpi.cs.wpisuitetng.modules.calendar.model;

import java.util.ArrayList;
import java.util.List;

public class CalendarObjectModel {
	private String title;
	private List<Commitment> commitments;
	private List<Event> events;

	public CalendarObjectModel(String title) {
		this.title = title;

		commitments = new ArrayList<Commitment>();
		commitments.add(new Commitment("A commitment: " + title, null));
		events = new ArrayList<Event>();
	}

	public String getTitle() {
		return title;
	}

	public List<Commitment> getCommitments() {
		return commitments;
	}
}
