package edu.wpi.cs.wpisuitetng.modules.calendar.view.scheduledevent;

import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class FollowerPanel extends JPanel{

	private Follower fp;
	private Color col;
	private TimePanel tp;
	
	public FollowerPanel()
	{
		this.setLayout(new MigLayout("fill,insets 0", "0[5%]0[95%]0","0[]0"));
		
	}

	public void setTimeFrame(int day, int start, int end) {
		
		tp = new TimePanel(start,end);
		fp = new Follower();
		fp.setTimeFrame(day,start,end);
		this.add(tp, "cell 0 0 ,grow, push");
		this.add(fp, "cell 1 0, grow, push");
	}

	public void updateFollower(List<List<Hour>> list, String user) {
		fp.updatePanel(list,user);
	}

	public void setColor(Color col) {
		this.col = col;
		fp.setColor(col);
	}
	
	public void reSize(int width){
		this.setSize(width, this.getHeight());
		this.setPreferredSize(new Dimension(width, this.getHeight()));
		this.setMinimumSize(new Dimension(0,this.getHeight()));
		tp.reSize(new Integer((int) (width * .05)));
		fp.reSize(new Integer((int) (width * .50)));
		
		this.repaint();
	}
}
