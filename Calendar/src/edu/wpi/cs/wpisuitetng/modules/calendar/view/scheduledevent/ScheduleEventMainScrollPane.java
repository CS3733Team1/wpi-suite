package edu.wpi.cs.wpisuitetng.modules.calendar.view.scheduledevent;

import java.awt.Color;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.MatteBorder;

public class ScheduleEventMainScrollPane extends JScrollPane  {

	private ScheduleEventMain schedule;
	private int scrollSpeed = 15;
	
	public ScheduleEventMainScrollPane(ScheduleEventMain scheduleEvent)
	{
		super(scheduleEvent);
		
//		this.add(scheduleEvent);
		this.schedule = scheduleEvent;
		
		this.setWheelScrollingEnabled(true);
		this.getVerticalScrollBar().setUnitIncrement(scrollSpeed);
		
		this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.setBorder(new MatteBorder(0, 0, 0, 0, Color.LIGHT_GRAY));
	}

	
}
