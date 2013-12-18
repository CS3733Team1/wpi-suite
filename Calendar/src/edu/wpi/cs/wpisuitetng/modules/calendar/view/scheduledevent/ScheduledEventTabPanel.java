package edu.wpi.cs.wpisuitetng.modules.calendar.view.scheduledevent;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.scheduledevent.ScheduledEvent;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.scheduledevent.ScheduledEventListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.CalendarUtils;

public class ScheduledEventTabPanel extends JPanel implements ActionListener {

	private ScheduleEventSetup setup;
	
	public ScheduledEventTabPanel() {
		System.out.println("PLEASE WORK");
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
		event.setTime(ScheduledEventListModel.getScheduledEventListModel().getUser().getName(), schedule.getHourList().size(), schedule.getHourList().get(0).get(0).getHour(),  schedule.getHourList().get(schedule.getHourList().size()-1).get(0).getHour(), CalendarUtils.thatBlue);
		event.updatePanel(schedule.getHourList(), ScheduledEventListModel.getScheduledEventListModel().getUser().getName());
		panel.update(schedule.getHourList(),schedule.getTitle(), ScheduledEventListModel.getScheduledEventListModel().getUser().getName(), event, schedule.getDays());
		this.add(panel, "grow, push");
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
