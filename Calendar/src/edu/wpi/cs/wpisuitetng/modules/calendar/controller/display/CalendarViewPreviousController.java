package edu.wpi.cs.wpisuitetng.modules.calendar.controller.display;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarTabPanel;

public class CalendarViewPreviousController implements ActionListener{
	CalendarTabPanel view;

	public CalendarViewPreviousController(CalendarTabPanel view){
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(view.getUpdateView() != null) {
			view.getUpdateView().previous();
			view.setCalendarViewTitle(view.getUpdateView().getTitle());
			view.refreshCalendarView();
		}
	}
}
