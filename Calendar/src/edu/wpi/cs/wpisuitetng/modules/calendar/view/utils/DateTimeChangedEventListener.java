package edu.wpi.cs.wpisuitetng.modules.calendar.view.utils;

import java.util.EventListener;

import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.TimeChangedEvent;

public interface DateTimeChangedEventListener extends EventListener {
	public void DateTimeChangedEventOccurred(DateTimeChangedEvent evt);
}
