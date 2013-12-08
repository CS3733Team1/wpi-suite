package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.year;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JPanel;
import javax.swing.Scrollable;

public class MonthsContainerPanel extends JPanel implements Scrollable {
	@Override
	public boolean getScrollableTracksViewportHeight() {
		return false;
	}
	
	/*
	 * Used to disable horizontal scrolling.
	 * @see javax.swing.Scrollable#getScrollableTracksViewportWidth()
	 */
	@Override
	public boolean getScrollableTracksViewportWidth() {
		return true;
	}
	
	// Unused
	@Override
	public Dimension getPreferredScrollableViewportSize() {return null;}
	@Override
	public int getScrollableBlockIncrement(Rectangle r, int i, int j) {return 0;}
	@Override
	public int getScrollableUnitIncrement(Rectangle r, int i, int j) {return 0;}
}
