package edu.wpi.cs.wpisuitetng.modules.calendar.view.scheduledevent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.scheduledevent.ScheduledEvent;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.scheduledevent.ScheduledEventListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.CalendarUtils;
import net.miginfocom.swing.MigLayout;

public class ScheduledEventTabPanel extends JPanel implements ActionListener {

	private ScheduleEventSetup setup;
	
	public ScheduledEventTabPanel() {
		this.setLayout(new MigLayout("fill,insets 0"));
		setup = new ScheduleEventSetup();
		setup.addEventListener(this);
		this.add(setup, "grow,push");
	}
	
	
	public ScheduledEventTabPanel(ScheduledEvent schedule)
	{
		this.setLayout(new MigLayout("fill,insets 0"));
		ScheduledPanel panel = new ScheduledPanel();
		
		
		ScheduleEventMain event = new ScheduleEventMain();
		event.updatePanel(schedule.getHourList(), ScheduledEventListModel.getScheduledEventListModel().getUser().getName());
		panel.update(schedule.getHourList(),schedule.getTitle(), ScheduledEventListModel.getScheduledEventListModel().getUser().getName(), event, schedule.getDays());
	}

	public void closeEventPanel() {
		this.getParent().remove(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ArrayList<String> days = setup.getDayList();
		int startIndex = setup.getStartIndex();
		int endIndex = setup.getEndIndex();
		String title = setup.getTitle();
		System.out.println("title"+title);
		String name = ScheduledEventListModel.getScheduledEventListModel().getUser().getName();
		ScheduleEventMain schedule = new ScheduleEventMain();
		schedule.addTitle(title);
		schedule.addWeekList(days);
		schedule.addUser(name);
		ScheduledPanel schedulePanel = new ScheduledPanel();
		schedule.setTime(name,days.size(), startIndex, endIndex, CalendarUtils.thatBlue);
		schedulePanel.setup(title, name, schedule, days);
		this.removeAll();
		this.add(schedulePanel, "grow, push");
		this.invalidate();
		this.repaint();
	}
}
