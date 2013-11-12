package edu.wpi.cs.wpisuitetng.modules.calendar.controller.display;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.CalendarModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarTabPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.EventPanel;

public class DisplayMonthViewController implements ActionListener{
	CalendarTabPanel view;

	public DisplayMonthViewController(CalendarTabPanel view){
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		view.changeMonthView();
	}


}
