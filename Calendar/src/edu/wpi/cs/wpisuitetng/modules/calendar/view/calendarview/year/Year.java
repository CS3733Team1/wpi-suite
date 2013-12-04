package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.year;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JPanel;
import javax.swing.Scrollable;

public class Year extends JPanel implements Scrollable {

	public Year() {
		this.setBackground(Color.WHITE);
	}
	
	@Override
	public boolean getScrollableTracksViewportHeight() {
		return false;
	}
	
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
