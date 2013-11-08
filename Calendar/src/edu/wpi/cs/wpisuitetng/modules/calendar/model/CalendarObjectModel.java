package edu.wpi.cs.wpisuitetng.modules.calendar.model;

import java.util.ArrayList;
import java.util.List;

public class CalendarObjectModel {
	private String title;
	private List<Commitment> commitments;

	public CalendarObjectModel(String title) {
		this.title = title;

		commitments = new ArrayList<Commitment>();
		commitments.add(new Commitment("Meeting 1"));
		commitments.add(new Commitment("Meeting 2"));
		commitments.add(new Commitment("Meeting 3"));
		commitments.add(new Commitment("Meeting 4"));
		commitments.add(new Commitment("Meeting 5"));
	}

	public String getTitle() {
		return title;
	}

	public List<Commitment> getCommitments() {
		return commitments;
	}
}
