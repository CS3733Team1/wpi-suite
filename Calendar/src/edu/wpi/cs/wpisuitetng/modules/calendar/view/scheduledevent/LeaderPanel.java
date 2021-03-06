package edu.wpi.cs.wpisuitetng.modules.calendar.view.scheduledevent;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.CalendarUtils;

public class LeaderPanel extends JPanel {
	private String user;
	private Leader lead;
	private TimePanel tp;
	private Color col;
	private List<List<HourPanel>> hourList;
	
	public LeaderPanel()
	{
		hourList = new ArrayList<List<HourPanel>>();
		lead = new Leader();
		this.setLayout(new MigLayout("fill,insets 0", "0[10%]0[90%]0","0[]0"));
		this.setSize(this.getWidth(), this.getHeight());
		this.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
		this.setMinimumSize(new Dimension(0,this.getHeight()));
		this.setBorder(new MatteBorder(0, 0, 1, 1, CalendarUtils.lineColor));
	}

	public void updateFollower(List<List<Hour>> list, String user) {
		int start = list.get(0).get(0).getHour();
		int end = list.get(0).get(list.get(0).size()-1).getHour();
		int days = list.size();
		
		System.err.println("start "+start+" end"+end+" days"+days);
		
		lead.setColor(CalendarUtils.thatBlue);
		lead.updateTimeFrame(days,start,end,list,user);
		tp =new TimePanel(start, end);
		
		this.add(tp, "cell 0 0 ,grow, push, wmin 0");
		this.add(lead, "cell 1 0 ,grow, push, wmin 0");
	}
	
	
	public void setTimeFrame(int day, int start, int end) {
		lead.setColor(col);
		lead.setTimeFrame(day,start,end);
		tp = new TimePanel(start,end);
		
		this.add(tp, "cell 0 0 ,grow, push, wmin 0");
		this.add(lead, "cell 1 0 ,grow, push, wmin 0");
	}

	public void updatePanels() {
		lead.getList();
		ScheduleEventMain wtm = (ScheduleEventMain) this.getParent();
		wtm.updatePanel(lead.getList(), getUser());
		System.err.println("Lead transfer to follower" + lead.getList());
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
	
	public void reSize(int width){
		this.setSize(width, this.getHeight());
		this.setPreferredSize(new Dimension(width, this.getHeight()));
		this.setMinimumSize(new Dimension(0,this.getHeight()));
		tp.reSize(new Integer((int) (width * .10)));
		lead.reSize(new Integer((int) (width * .90)));
		
		this.repaint();
	}

	

	
	
	
	
	
	
	
}
