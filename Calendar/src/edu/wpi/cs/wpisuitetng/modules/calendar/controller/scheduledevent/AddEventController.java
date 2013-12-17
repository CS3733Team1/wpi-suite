

package edu.wpi.cs.wpisuitetng.modules.calendar.controller.scheduledevent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import edu.wpi.cs.wpisuitetng.modules.calendar.view.scheduledevent.ScheduleEventMain;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.scheduledevent.ScheduleEventSetup;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.scheduledevent.ScheduledEventTabPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.scheduledevent.ScheduledPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.CalendarUtils;

public class AddEventController implements ActionListener {

	
	private ScheduleEventSetup setup;
	
	public AddEventController(ScheduleEventSetup setup) {
		this.setup = setup;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ScheduledEventTabPanel test =  (ScheduledEventTabPanel) setup.getParent();
		ArrayList<String> days = setup.getDayList();
		String title = setup.getTitle();
		ScheduleEventMain schedule = new ScheduleEventMain();
		ScheduledPanel schedulePanel = new ScheduledPanel();
		schedule.setTime(days.size(), 8, 14, CalendarUtils.thatBlue);
		schedulePanel.setup(title, schedule, days);
		test.removeAll();
		test.add(schedulePanel, "grow, push");
		test.repaint();
		test.validate();
	}
}
