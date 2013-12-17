package edu.wpi.cs.wpisuitetng.modules.calendar.view.scheduledevent;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.calendar.controller.scheduledevent.UpdateEventController;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.CalendarUtils;
import net.miginfocom.swing.MigLayout;

public class ScheduledPanel extends JPanel {

	private ScheduleEventMain scheduleEvent;
	private JLabel eventTitle;
	private ScheduleEventTitlePanel weekNames; 
	private ScheduleEventMainScrollPane scroll;
	private JButton addEventButton;
	public ScheduledPanel()
	{
		this.setLayout(new MigLayout("fill, insets 0 10 0 10", "10[100%]10", "0[30]0[30]0[80%]0[10%]0"));
		
	}
	
	public void setup(String title, ScheduleEventMain scheduleEvent, ArrayList<String> days)
	{
		this.scheduleEvent =  scheduleEvent;
		eventTitle = new JLabel(title, JLabel.CENTER);
		eventTitle.setForeground(CalendarUtils.titleNameColor);
		eventTitle.setFont(new Font(eventTitle.getFont().getFontName(), Font.BOLD, 16));
		weekNames = new ScheduleEventTitlePanel(days);
		scroll = new ScheduleEventMainScrollPane(scheduleEvent);
		
		addEventButton = new JButton("Update Event");
		addEventButton.setActionCommand("addevent");
		addEventButton.addActionListener(new UpdateEventController(this));

		this.add(eventTitle, "grow, push, alignx center,wrap");
		this.add(weekNames, "grow, push,wrap");
		this.add(scroll,"grow, push" );
		this.add(addEventButton, "cell 0 3,alignx left");
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
