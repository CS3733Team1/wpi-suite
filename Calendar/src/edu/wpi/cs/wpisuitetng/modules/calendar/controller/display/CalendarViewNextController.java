package edu.wpi.cs.wpisuitetng.modules.calendar.controller.display;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarTabPanel;

public class CalendarViewNextController implements ActionListener{
	CalendarTabPanel view;

	public CalendarViewNextController(CalendarTabPanel view){
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		view.changeMonthView();
	}

}
