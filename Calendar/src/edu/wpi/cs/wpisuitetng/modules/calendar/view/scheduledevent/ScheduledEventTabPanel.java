package edu.wpi.cs.wpisuitetng.modules.calendar.view.scheduledevent;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class ScheduledEventTabPanel extends JPanel {

	
	public ScheduledEventTabPanel()
	{
		this.setLayout(new MigLayout("fill,insets 0"));
		ScheduleEventSetup wtm = new ScheduleEventSetup();
		this.add(wtm, "grow,push");
		
	}
	
	public void closeEventPanel() {
		this.getParent().remove(this);
	}
}
