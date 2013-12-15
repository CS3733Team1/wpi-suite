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
	
	@Override
	public Dimension getPreferredScrollableViewportSize() {
		return this.getPreferredSize();
	}

	@Override
	public int getScrollableBlockIncrement(Rectangle visibleRect,
			int orientation, int direction) {
		return 32;
	}

	@Override
	public int getScrollableUnitIncrement(Rectangle visibleRect,
			int orientation, int direction) {
		return 32;
	}
}
