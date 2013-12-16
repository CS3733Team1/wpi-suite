package edu.wpi.cs.wpisuitetng.modules.calendar.view.scheduledevent;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class LeaderPanel extends JPanel {
	private String user;
	private Leader lead;
	private Color col;
	private List<List<HourPanel>> hourList;
	
	public LeaderPanel()
	{
		hourList = new ArrayList<List<HourPanel>>();
		this.setLayout(new MigLayout("fill,insets 0", "0[5%]0[95%]0","0[5%]0[95%]"));
		
	}

	public void setTimeFrame(int day, int start, int end) {
		lead = new Leader();
		lead.setColor(col);
		lead.setTimeFrame(day,start,end);
		TimePanel tp = new TimePanel(start,end);
		
		this.add(tp, "cell 0 1 ,grow, push");
		this.add(lead, "cell 1 1 ,grow, push");
	}

	public void updatePanels() {
		lead.getList();
		ScheduleEventMain wtm = (ScheduleEventMain) this.getParent();
		wtm.updatePanel(lead.getList(), getUser());
	}

	public void addUser(String user) {
		this.user = user;
		
	}
	public String getUser()
	{
		return user;
	}

	public void setColor(Color col) {
		this.col = col;
		lead.setColor(col);
		
	}
	
	
	
	
}
