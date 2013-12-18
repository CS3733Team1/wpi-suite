package edu.wpi.cs.wpisuitetng.modules.calendar.view.scheduledevent;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

import net.miginfocom.swing.MigLayout;

public class Leader extends JPanel{

	private int start;
	private int end;
	private int day;
	private Color col;

	private Point previoiusLocation;
	private Point startLocation;
	private int previousSizeY;
	private int previousSizeX;
	private int startLocationY;
	private int startLocationX;
	private int dragLocationX;
	private int dragLocationY;
	private int screenX;
	private int screenY;
	private int myX;
	private int myY;
	private boolean isDragged;
	private boolean isAdding;
	private boolean isPressed;
	
	private float percentage;


	private List<List<Hour>> hourList;
	private List<List<HourPanel>> hourPanelList;

	public Leader()
	{
		hourList = new ArrayList<List<Hour>>();
		hourPanelList = new ArrayList<List<HourPanel>>();

		addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				HourPanel hr = (HourPanel) Leader.this.getComponentAt(e.getPoint());
				startLocation= hr.getLocation();

				Point p = hr.getLocation();
				
				hourList.get(p.x).get(p.y).setState(!(hourList.get(p.x).get(p.y).getState()));
				hourPanelList.get(p.x).get(p.y).setState(!(hourPanelList.get(p.x).get(p.y).getState()));
				
				LeaderPanel leadPanel = (LeaderPanel) Leader.this.getParent();
		        leadPanel.updatePanels();
			}

			@Override
			public void mousePressed(MouseEvent e) {
				previousSizeX = 0;
				previousSizeY = 0;
				startLocationX = e.getXOnScreen();
				startLocationY = e.getYOnScreen();
				HourPanel hr = (HourPanel) Leader.this.getComponentAt(e.getPoint());
				startLocation= hr.getLocation();
				if(!(hourList.get(startLocation.x).get(startLocation.y).isSelected())){
					isAdding = true;

				}else{
					isAdding = false;
				}

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				int deltaX = e.getXOnScreen() - screenX;
				int deltaY = e.getYOnScreen() - screenY;
				if(isDragged == true){
				}
				isPressed = false;
				isDragged = false;

			}

			@Override
			public void mouseEntered(MouseEvent e) { }

			@Override
			public void mouseExited(MouseEvent e) { }

		});
		addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {

				int dragLocationX = e.getXOnScreen();
				int dragLocationY = e.getYOnScreen();
				
				Point p = (Point) Leader.this.getComponentAt(e.getPoint()).getLocation();


				int minX = Math.min(p.x, startLocation.x);
				int maxX = Math.max(p.x, startLocation.x);
				int minY = Math.min(p.y, startLocation.y);
				int maxY = Math.max(p.y, startLocation.y);

				
				if(p.x != startLocation.x && p.y != startLocation.y){
					for(int i = minX; i < maxX; i++){
						for(int j = minY; j < maxY; j++){
							if(isAdding){
								hourList.get(i).get(j).setState(true);
								hourPanelList.get(i).get(j).setState(true);
							}
								
							else{
								hourList.get(i).get(j).setState(false);
								hourPanelList.get(i).get(j).setState(false);
							}
						}
					}
				}
				
				if(p.x != startLocation.x && p.y == startLocation.y){
					for(int i = minX; i < maxX; i++){
							if(isAdding){
								hourList.get(i).get(p.y).setState(true);
								hourPanelList.get(i).get(p.y).setState(true);
							}else{
								hourList.get(i).get(p.y).setState(false);
								hourPanelList.get(i).get(p.y).setState(false);
							}
					}
				}
				
				if(p.x == startLocation.x && p.y != startLocation.y){
					for(int i = minY; i < maxY; i++){
							if(isAdding){
								hourList.get(p.x).get(i).setState(true);
								hourPanelList.get(p.x).get(i).setState(true);
							}else{
								hourList.get(p.x).get(i).setState(false);
								hourPanelList.get(p.x).get(i).setState(false);
							}
					}
				}
				
				previousSizeX = Math.abs(maxX-minX);
				previousSizeY = Math.abs(maxY-minY);
				
				LeaderPanel leadPanel = (LeaderPanel) Leader.this.getParent();
		        leadPanel.updatePanels();


			}
			private void updatePanels(Point mostRecent, Point previous)
			{
				int minX = Math.min(mostRecent.x, previous.x);
				int maxX = Math.max(mostRecent.x, previous.x);
				int minY = Math.min(mostRecent.y, previous.y);
				int maxY = Math.max(mostRecent.y, previous.y);

				for(int i = minX; i < maxX; i++){
					for(int j = maxY; j < maxY; j++){
						if(isAdding)
							hourList.get(i).get(j).setState(true);
						else
							hourList.get(i).get(j).setState(false);
						
						
						
					}
				}



			}

			@Override
			public void mouseMoved(MouseEvent e) { }

		});
	}



	public void setTimeFrame(int day, int start, int end) {
		this.day = day;
		this.start = start;
		this.end = end;

		StringBuilder row = new StringBuilder();
		for(int i = 0; i < end-start; i++)
		{
			percentage = (float)((1.0)/(end-start))*100;
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
	public List<List<HourPanel>> getListPanel()
	{
		return hourPanelList;
	}
	public List<List<Hour>> getList()
	{
		return hourList;
	}
	
	private void addHours() {
		for(int i = 0; i < day; i++){
			ArrayList<HourPanel> hourPanel = new ArrayList<HourPanel>();
			ArrayList<Hour> hour = new ArrayList<Hour>();
			hourList.add(hour);
			hourPanelList.add(hourPanel);
			for(int j = 0; j < end-start; j++)
			{	
				HourPanel testHour = new HourPanel();
				testHour.setState(false);
				testHour.configCol(col);
				testHour.setHour(i);
				testHour.setIndex(i, j);
				Hour hr = testHour.config();
				
				hourList.get(i).add(hr);
				hourPanelList.get(i).add(testHour);
				this.add(testHour, "cell"+i+" "+j+", grow, push");
			}
		}

	}

	public void setColor(Color col) {
		this.col = col;		
		for(int i = 0; i < day; i++){
			for(int j = 0; j < end-start; j++)
			{	
				hourPanelList.get(i).get(j).configCol(col);
			}
		}
	}
	
	public void reSize(int width){
		this.setSize(width, this.getHeight());
		this.setPreferredSize(new Dimension(width, this.getHeight()));
		this.setMinimumSize(new Dimension(0,this.getHeight()));
		
		for(int i = 0; i < day; i++){
			for(int j = 0; j < end-start; j++)
				hourPanelList.get(i).get(j).reSize(new Integer((int) (width * (percentage/100))));
		}
		this.repaint();
	}
}
