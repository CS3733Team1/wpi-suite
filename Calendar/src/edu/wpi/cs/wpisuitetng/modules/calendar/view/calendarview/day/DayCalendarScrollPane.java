package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class DayCalendarScrollPane extends JScrollPane{
	private DayCalendarLayerPane daylayer;
	
	public DayCalendarScrollPane(DayCalendarLayerPane day){
		super(day);
		
		daylayer = day;
		
		this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
	}
	
}
