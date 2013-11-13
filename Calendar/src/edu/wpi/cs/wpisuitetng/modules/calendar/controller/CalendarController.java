package edu.wpi.cs.wpisuitetng.modules.calendar.controller;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.CalendarModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarView;

public class CalendarController {
	
	CalendarModel model;
	CalendarView view;
	
	public CalendarController(CalendarModel model, CalendarView view) {
		this.model = model;
		this.view = view;
	}

}
