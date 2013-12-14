package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day2;

import java.awt.Color;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.MatteBorder;

public class DayMultiScrollPane extends JScrollPane{
	
	private MultidayEventView mday;
	
	public DayMultiScrollPane(MultidayEventView hold){
		mday = hold;
		
		this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.setBorder(new MatteBorder(0, 0, 0, 0, Color.LIGHT_GRAY));
	}

}
