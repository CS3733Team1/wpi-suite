package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.week;

import java.awt.Color;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.MatteBorder;

/**
 * Scroll pane that holds the week's multiday events
 */
public class WeekMultiScrollPane extends JScrollPane{

private MultidayEventWeekView mday;
	
	public WeekMultiScrollPane(MultidayEventWeekView hold){
		super(hold);
		mday = hold;
		
		this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.setBorder(new MatteBorder(0, 0, 0, 0, Color.LIGHT_GRAY));
	}
}
