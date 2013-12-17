package edu.wpi.cs.wpisuitetng.modules.calendar.view.utils;

import java.util.EventListener;

public interface DateTimeChangedEventListener extends EventListener {
	public void dateTimeChangedEventOccurred(DateTimeChangedEvent evt);
}
