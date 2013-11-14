package edu.wpi.cs.wpisuitetng.modules.calendar.controller.calendarview;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarTabPanel;

public class DisplayDayViewController implements ActionListener {
	CalendarTabPanel view;

	public DisplayDayViewController(CalendarTabPanel view){
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		view.displayDayView();
	}
}
