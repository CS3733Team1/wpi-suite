package edu.wpi.cs.wpisuitetng.modules.calendar.view.scheduledevent;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class ScheduleEventMain extends JPanel{

	private LeaderPanel leaderPanel;
	private LeaderPanel leaderPanel2;
	private FollowerPanel followerPanel;
	private String user;
	private List<List<Hour>> hourList;
	
	public ScheduleEventMain()
	{
		this.setLayout(new MigLayout("fill,insets 0", "0[50%]0[50%]0","0[]0" ));
		leaderPanel = new LeaderPanel(); 
		followerPanel = new FollowerPanel();
		hourList =  new ArrayList<List<Hour>>();
	}
	
	public void setupPanel()
	{
		this.setLayout(new MigLayout("fill,insets 0", "0[100%]0","0[]0" ));
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
	
	public void setTime(int day, int start, int end, Color col)
	{
		leaderPanel.setTimeFrame(day, start, end);
		leaderPanel.setColor(col);
		leaderPanel.addUser("Bob");
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
	
	
}
