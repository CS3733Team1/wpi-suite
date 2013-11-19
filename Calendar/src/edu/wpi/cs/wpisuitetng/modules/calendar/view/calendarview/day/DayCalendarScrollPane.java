package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day;

import javax.swing.JScrollPane;

public class DayCalendarScrollPane extends JScrollPane{
	private DayCalendarLayerPane daylayer;
	
	public DayCalendarScrollPane(DayCalendarLayerPane day){
		super(day);
		
		daylayer = day;
	}
}
