package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview;

import java.util.EventListener;
import java.util.EventObject;


public interface TimeChangedEventListener extends EventListener {
	public void TimeChangedEventOccurred(TimeChangedEvent evt);
}
