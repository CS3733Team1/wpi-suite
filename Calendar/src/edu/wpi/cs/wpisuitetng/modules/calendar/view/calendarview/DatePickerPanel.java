package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import org.jdesktop.swingx.JXMonthView;

import edu.wpi.cs.wpisuitetng.modules.calendar.view.ErrorPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.ViewMode;

public class DatePickerPanel extends JPanel {
	private JXMonthView calendarMonthView;

	/**
	 * Create the panel.
	 */
	public DatePickerPanel() {
		
		
		buildLayout();
		

	}
	
	private void buildLayout(){
		
		this.setLayout(new BorderLayout());
		calendarMonthView = new JXMonthView();
	}
	
}
