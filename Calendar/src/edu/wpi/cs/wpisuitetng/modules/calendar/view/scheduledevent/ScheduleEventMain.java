package edu.wpi.cs.wpisuitetng.modules.calendar.view.scheduledevent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.scheduledevent.ScheduledEvent;
import net.miginfocom.swing.MigLayout;

public class ScheduleEventMain extends JPanel{

	private LeaderPanel leaderPanel;
	private LeaderPanel leaderPanel2;
	private FollowerPanel followerPanel;
	private String user;
	private List<List<Hour>> hourList;
	private ArrayList<String> dayOfWeek;
	private String title;
	
	public ScheduleEventMain()
	{
		this.setLayout(new MigLayout("fill,insets 0", "0[50%]0[50%]0","0[]0" ));
		leaderPanel = new LeaderPanel(); 
		followerPanel = new FollowerPanel();
		hourList =  new ArrayList<List<Hour>>();
	}
	
	
	public void update(List<List<Hour>> hourList, String user)
	{
		leaderPanel.updateFollower(hourList, user);
	}
	
	public void addUser(String user)
	{
		this.user = user;
		System.out.println("user"+user);
	}
	
	
	public void constructHourTest(int day, int start, int end)
	{
		for(int i = 0; i < day; i++){
			ArrayList<Hour> hour = new ArrayList<Hour>();
			hourList.add(hour);
			for(int j = 0; j < end-start; j++)
			{	
				Hour testHour = new Hour();
				testHour.setState(true);
				testHour.setHour(i);
				testHour.setIndex(i, j);
				hourList.get(i).add(testHour);
			}
		}
	}
	
	
	
	public void setTime(String user,int day, int start, int end, Color col)
	{
		leaderPanel.setTimeFrame(day, start, end);
		leaderPanel.setColor(col);
		leaderPanel.addUser(user);
		followerPanel.setTimeFrame(day, start, end);
		followerPanel.setColor(col);
		constructHourTest(day, start, end);
		updatePanel(hourList,"John");
		updatePanel(hourList,"Joe");
		this.add(leaderPanel,"grow, push");
		this.add(followerPanel,"grow, push");
	}
	
	public void updatePanel(List<List<Hour>> list, String user) {
		followerPanel.updateFollower(list, user);
	}
	
	public List<List<Hour>> updateHourList()
	{
		return followerPanel.updateHourList();
	}
	
	
	
	public ScheduledEvent getFilledScheduledEvent()
	{
		return new ScheduledEvent(updateHourList(),dayOfWeek,title);
		
	}
	
	
	public void reSize(int width){
		this.setSize(width, this.getHeight());
		this.setPreferredSize(new Dimension(width, this.getHeight()));
		this.setMinimumSize(new Dimension(0,this.getHeight()));
		followerPanel.reSize(new Integer((int) (width * .50)));
		leaderPanel.reSize(new Integer((int) (width * .50)));
		
		this.repaint();
	}


	public void addWeekList(ArrayList<String> days) {
		this.dayOfWeek = days;
		
	}
	
	public void addTitle(String title)
	{
		System.out.println("title"+title);
		System.out.println(this);
		this.title = title;
	}
	
	public String getTitle() {return this.title;}
	
}