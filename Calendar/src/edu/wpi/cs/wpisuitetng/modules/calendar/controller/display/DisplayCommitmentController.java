package edu.wpi.cs.wpisuitetng.modules.calendar.controller.display;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.CalendarModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.CommitmentPanel;

public class DisplayCommitmentController implements ActionListener{
	CalendarModel model;
	CalendarView view;
	
	public DisplayCommitmentController(CalendarView view, CalendarModel model){
		this.model = model;
		this.view = view;
	}
	
	public void actionPerformed(ActionEvent e) {
		view.getCalendarPanel().add(new CommitmentPanel(view, model));
	}
	

}
