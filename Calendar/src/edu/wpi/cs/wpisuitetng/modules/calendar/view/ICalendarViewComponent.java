package edu.wpi.cs.wpisuitetng.modules.calendar.view;

public interface ICalendarViewComponent {
	public void next();
	public void previous();
	public void today();
	public String getTitle();
}
