package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;

import org.jdesktop.swingx.JXMonthView;
import org.jdesktop.swingx.calendar.DateSelectionModel.SelectionMode;

import edu.wpi.cs.wpisuitetng.modules.calendar.view.ErrorPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.ViewMode;

public class DatePickerPanel extends JPanel {
	private JXMonthView calendarMonthView;
	public static final Color START_END_DAY = new Color(47, 150, 9);
	public static final Color SELECTION = new Color(236,252,144);
	public static final Color UNSELECTABLE = Color.red;

	/**
	 * Create the panel.
	 */
	public DatePickerPanel() {
		
		
		buildLayout();
		

	}
	
	private void buildLayout(){
		
		this.setLayout(new BorderLayout());
		calendarMonthView = new JXMonthView();
		calendarMonthView.setPreferredColumnCount(4);
		calendarMonthView.setPreferredRowCount(3);
		calendarMonthView.setSelectionBackground(SELECTION);
		calendarMonthView.setFlaggedDayForeground(START_END_DAY);
		calendarMonthView.setSelectionMode(SelectionMode.MULTIPLE_INTERVAL_SELECTION);
		this.setAlignmentX(CENTER_ALIGNMENT);
	}
	
}
