package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview;

import java.util.EventObject;

public class TimeChangedEvent extends EventObject {
	public TimeChangedEvent(Object source) {
		super(source);
	}
}