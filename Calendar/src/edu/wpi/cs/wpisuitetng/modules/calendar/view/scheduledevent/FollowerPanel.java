package edu.wpi.cs.wpisuitetng.modules.calendar.view.scheduledevent;

import java.awt.Color;
import java.util.List;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class FollowerPanel extends JPanel{

	private Follower fp;
	private Color col;
	public FollowerPanel()
	{
		this.setLayout(new MigLayout("fill,insets 0", "0[5%]0[95%]0","0[5%]0[95%]"));
		
	}

	public void setTimeFrame(int day, int start, int end) {
		
		TimePanel tp = new TimePanel(start,end);
		fp = new Follower();
		fp.setTimeFrame(day,start,end);
		this.add(tp, "cell 0 1 ,grow, push");
		this.add(fp, "cell 1 1, grow, push");
	}

	public void updateFollower(List<List<Hour>> list, String user) {
		fp.updatePanel(list,user);
	}

	public void setColor(Color col) {
		this.col = col;
		fp.setColor(col);
	}
}
