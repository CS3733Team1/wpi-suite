package edu.wpi.cs.wpisuitetng.modules.calendar.controller.display;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarTabPanel;

public class CalendarViewNowController implements ActionListener{
	CalendarTabPanel view;

	public CalendarViewNowController(CalendarTabPanel view){
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(view.getUpdateView() != null) {
			view.getUpdateView().today();
			view.setCalendarViewTitle(view.getUpdateView().getTitle());
			view.refreshCalendarView();
		}
	}
}
