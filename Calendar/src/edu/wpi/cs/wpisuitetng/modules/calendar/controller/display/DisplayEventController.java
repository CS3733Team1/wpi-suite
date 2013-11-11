package edu.wpi.cs.wpisuitetng.modules.calendar.controller.display;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.CalendarModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.EventPanel;

/**
 * This Controller is based largely off of DisplayCommitmentController 
 * in the calendar->controller.display
 * @version $Revision: 1.0 $
 * @author rbansal
 */

public class DisplayEventController implements ActionListener{
	CalendarModel model;
	CalendarView view;
	
	public DisplayEventController(CalendarView view, CalendarModel model){
		this.model = model;
		this.view = view;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		EventPanel pan = new EventPanel(view, model);
		view.getCalendarPanel().addTab("Add Event",pan);
		view.getCalendarPanel().setSelectedComponent(pan);
	}
	

}
