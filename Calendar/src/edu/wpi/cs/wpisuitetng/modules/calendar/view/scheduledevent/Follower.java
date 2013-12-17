package edu.wpi.cs.wpisuitetng.modules.calendar.view.scheduledevent;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class Follower extends JPanel{
	private int start;
	private int end;
	private int day;
	private Color col;

	private List<List<HourPanel>> hourList;


	public Follower()
	{
		hourList = new ArrayList<List<HourPanel>>();
		addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				HourPanel hr = (HourPanel) Follower.this.getComponentAt(e.getPoint());
				Point p = e.getLocationOnScreen();
				Graphics g = getGraphics();
				g.setColor(Color.BLACK);
				g.fillRect(p.x, p.y, 100, 100);
				System.out.println("added");
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) { }

			@Override
			public void mouseExited(MouseEvent e) { }

		});
		addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {




			}
			@Override
			public void mouseMoved(MouseEvent e) {

			}

		});
	}




	public void setTimeFrame(int day, int start, int end) {
		this.day = day;
		this.start = start;
		this.end = end;

		StringBuilder row = new StringBuilder();
		for(int i = 0; i < end-start; i++)
		{
			float percentage = (float)(1.0/(end-start))*100;
			row.append("0[");
			row.append(percentage);
			row.append("%]0");
		}
		StringBuilder col = new StringBuilder();
		for(int i = 0; i < day; i++)
		{
			col.append("0[]0");
		}

		this.setLayout(new MigLayout("fill,insets 0,gapy 0", 
				col.toString(),
				row.toString()));
		addHours();
	}

	private void addHours() {
		for(int i = 0; i < day; i++){
			ArrayList<HourPanel> hour = new ArrayList<HourPanel>();
			hourList.add(hour);
			for(int j = 0; j < end-start; j++)
			{	
				HourPanel testHour = new HourPanel();
				testHour.resetCount();
				testHour.setHour(i);
				testHour.setIndex(i, j);
				testHour.configCol(col);
				hourList.get(i).add(testHour);
				this.add(testHour, "cell"+i+" "+j+", grow, push");

			}
		}
	}
	public void updatePanel(List<List<Hour>> list, String user)
	{
		for(int i = 0; i < day; i++){
			for(int j = 0; j < end-start; j++)
			{	StringBuilder sb = new StringBuilder();
				if(list.get(i).get(j).getState())
					hourList.get(i).get(j).updateCount(user);
				if(!list.get(i).get(j).getState())
					hourList.get(i).get(j).reduceCount(user);
				sb.append("<html><p style='width:100'><b>Available: </b><br>");
				for(int z = 0; z < hourList.get(i).get(j).userList().size(); z++)
				{
					sb.append(hourList.get(i).get(j).userList().get(z)+"<br>");
					
					
				}
				sb.append("</p><br>");
				hourList.get(i).get(j).setToolTipText(sb.toString());
			}
		}
	}

	public void setColor(Color col) {
		this.col = col;
		for(int i = 0; i < day; i++){
			for(int j = 0; j < end-start; j++)
			{	
				hourList.get(i).get(j).configCol(col);

			}
		}

	}
}
