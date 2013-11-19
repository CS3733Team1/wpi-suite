package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.week;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day.DayCalendarLayerPane;

public class WeekCalendarScrollPane extends JScrollPane{
	private WeekCalendarLayerPane weeklayer;

	public WeekCalendarScrollPane(WeekCalendarLayerPane day){
		super(day);

		weeklayer = day;

		this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

	}
}
