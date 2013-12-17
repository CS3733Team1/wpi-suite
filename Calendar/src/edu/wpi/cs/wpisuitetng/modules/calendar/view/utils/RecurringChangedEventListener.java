package edu.wpi.cs.wpisuitetng.modules.calendar.view.utils;

import java.util.EventListener;

public interface RecurringChangedEventListener extends EventListener {
	public void recurringChangedEventOccurred(RecurringChangedEvent evt);
}
