

package edu.wpi.cs.wpisuitetng.modules.calendar.controller.scheduledevent;

import java.awt.Color;
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
		System.out.println("We Did It Ladies and Gents");
		
		ScheduledEventTabPanel test =  (ScheduledEventTabPanel) setup.getParent();
		ArrayList<String> days = setup.getDayList();
		int startIndex = setup.getStartIndex();
		int endIndex = setup.getStartIndex();
		String title = setup.getTitle();
		System.out.println("StartIndex:"+startIndex+" EndIndex"+endIndex);
		System.out.println("Did I get HERE");
		ScheduleEventMain schedule = new ScheduleEventMain();
		ScheduledPanel schedulePanel = new ScheduledPanel();
		System.out.println("OR HERE");
		schedule.setTime(days.size(), 8, 14, CalendarUtils.thatBlue);
		schedulePanel.setup(title, schedule, days);
		System.out.println("Maybe HERE");
		test.removeAll();
		System.out.println("Probably not HERE");
		test.add(schedulePanel, "grow, push");
		System.out.println("No way I could be HERE");
		test.repaint();
		test.validate();
		
		
	}
	

}
