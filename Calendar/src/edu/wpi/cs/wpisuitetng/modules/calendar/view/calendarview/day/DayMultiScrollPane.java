package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.MatteBorder;

/**
 * A scroll pane to hold the multiday events
 */
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
		this.setBorder(new MatteBorder(0 ,0 ,0 ,0, this.getBackground()));
		this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	}

}
