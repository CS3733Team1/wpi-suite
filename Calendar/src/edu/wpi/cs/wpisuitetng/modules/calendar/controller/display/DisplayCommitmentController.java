package edu.wpi.cs.wpisuitetng.modules.calendar.controller.display;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		CommitmentPanel pan = new CommitmentPanel(view, model);
		view.getCalendarPanel().addTab("Add Commitment",pan);
		view.getCalendarPanel().setSelectedComponent(pan);
	}
}
