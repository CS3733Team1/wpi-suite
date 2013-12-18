package edu.wpi.cs.wpisuitetng.modules.calendar.view.scheduledevent;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.scheduledevent.AddScheduledEventController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.scheduledevent.UpdateScheduledEventController;
//import edu.wpi.cs.wpisuitetng.modules.calendar.controller.scheduledevent.UpdateScheduledEventController;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.CalendarUtils;

public class ScheduledPanel extends JPanel {

	private ScheduleEventMain scheduleEvent;
	private JLabel eventTitle;
	private ScheduleEventTitlePanel weekNames; 
	private ScheduleEventMainScrollPane scroll;
	private JButton addEventButton;
	private String user;
	private String title;
	private ArrayList<String> days;
	
	
	public ScheduledPanel()
	{
		this.setLayout(new MigLayout("fill, insets 0 10 0 10", "10[100%]10", "0[30]0[30]0[80%]0[10%]0"));
	}
	
	
	public void update(List<List<Hour>> hourList,String title, String user,ScheduleEventMain scheduleEvent, ArrayList<String> days)
	{
		
		this.title = title;
		this.scheduleEvent =  scheduleEvent;
		scheduleEvent.addUser(user);
		eventTitle = new JLabel(title, JLabel.CENTER);
		eventTitle.setForeground(CalendarUtils.titleNameColor);
		eventTitle.setFont(new Font(eventTitle.getFont().getFontName(), Font.BOLD, 14));
		weekNames = new ScheduleEventTitlePanel(days);
		scheduleEvent.update(hourList, user);
		scroll = new ScheduleEventMainScrollPane(scheduleEvent);
		
		
		//CHANGE BUTTON NAME
		
		addEventButton = new JButton("Update Event");
		addEventButton.setActionCommand("updateevent");
		addEventButton.addActionListener(new UpdateScheduledEventController(scheduleEvent));

		this.add(eventTitle, "grow, push, alignx center,wrap");
		this.add(weekNames, "grow, push,wrap");
		this.add(scheduleEvent, "grow, push");
//		this.add(scroll,"grow, push" );
		this.add(addEventButton, "cell 0 3,alignx left");
	}
	
	

	public void setup(String title, String user, ScheduleEventMain scheduleEvent, ArrayList<String> days)
	{
		this.title = title;
		this.scheduleEvent =  scheduleEvent;
		eventTitle = new JLabel(title, JLabel.CENTER);
		eventTitle.setForeground(CalendarUtils.titleNameColor);
		eventTitle.setFont(new Font(eventTitle.getFont().getFontName(), Font.BOLD, 14));
		weekNames = new ScheduleEventTitlePanel(days);
		scroll = new ScheduleEventMainScrollPane(scheduleEvent);
		
		addEventButton = new JButton("Add Event");
		addEventButton.setActionCommand("addevent");
		addEventButton.addActionListener(new AddScheduledEventController(scheduleEvent));

		this.add(eventTitle, "grow, push, alignx center,wrap");
		this.add(weekNames, "grow, push,wrap");
		this.add(scroll,"grow, push" );
		this.add(addEventButton, "cell 0 3,alignx left");
	}
	public List<List<Hour>> updateSchedule()
	{
		return scheduleEvent.updateHourList();
	}
	
	public String getTitle()
	{
		return title;
	}
	
	
	
	@Override
    public void repaint()
    {
		if (scheduleEvent != null){
			scheduleEvent.reSize(this.getWidth() - (scroll.getVerticalScrollBar().getWidth()));
			//John had *3 for (weekSCroll) check this
		}
        super.repaint();
    }
}
