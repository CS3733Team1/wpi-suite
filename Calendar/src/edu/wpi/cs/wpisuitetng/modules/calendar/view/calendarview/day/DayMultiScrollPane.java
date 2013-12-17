package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day;

import java.awt.Color;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.MatteBorder;

public class DayMultiScrollPane extends JScrollPane{
	
	private MultidayEventView mday;
	private int scrollSpeed = 15;
	
	/**
	 * Creates a new ScrollPane for holding the multiday events
	 * @param hold the multidayeventview being held by the scrollpane
	 */
	public DayMultiScrollPane(MultidayEventView hold){
		super(hold);
		mday = hold;
		
		this.setWheelScrollingEnabled(true);
		this.getVerticalScrollBar().setUnitIncrement(scrollSpeed);
		
		this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.setBorder(new MatteBorder(0, 0, 0, 0, Color.LIGHT_GRAY));
	}

}
