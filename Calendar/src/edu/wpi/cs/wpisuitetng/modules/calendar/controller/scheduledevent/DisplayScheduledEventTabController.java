package edu.wpi.cs.wpisuitetng.modules.calendar.controller.scheduledevent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.event.EventTabPanel;

public class DisplayScheduledEventTabController implements ActionListener {
		private CalendarPanel calendarPanel;

		public DisplayScheduledEventTabController(CalendarPanel calendarPanel){
			this.calendarPanel = calendarPanel;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			EventTabPanel scheduledEventPanel = new EventTabPanel();
			ImageIcon miniScheduledEventIcon = new ImageIcon();
			try {
				miniScheduledEventIcon = new ImageIcon(ImageIO.read(getClass().getResource("/images/schedule_icon.png")));
			} catch (IOException exception) {}
			calendarPanel.addTab("Add Scheduled Event", miniScheduledEventIcon , scheduledEventPanel);
			calendarPanel.setSelectedComponent(scheduledEventPanel);
		}
	}
