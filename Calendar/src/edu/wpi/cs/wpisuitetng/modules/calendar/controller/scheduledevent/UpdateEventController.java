package edu.wpi.cs.wpisuitetng.modules.calendar.controller.scheduledevent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.calendar.view.scheduledevent.ScheduledEventTabPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.scheduledevent.ScheduledPanel;

public class UpdateEventController implements ActionListener {

	private ScheduledPanel scheduledPanel;
	public UpdateEventController(ScheduledPanel scheduledPanel) {
		// TODO Auto-generated constructor stub
		this.scheduledPanel = scheduledPanel;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

		
		ScheduledEventTabPanel schedule= (ScheduledEventTabPanel) scheduledPanel.getParent();
		schedule.closeEventPanel();
	}

}
