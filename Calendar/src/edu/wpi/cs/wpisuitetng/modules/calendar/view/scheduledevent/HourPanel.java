package edu.wpi.cs.wpisuitetng.modules.calendar.view.scheduledevent;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.ColorUIResource;

import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.CalendarUtils;
import net.miginfocom.swing.MigLayout;

public class HourPanel extends JPanel {

	private Color configColor;
	private boolean state;
	private int setHour;
	private int dragLocationX;
	private int dragLocationY;
	private int screenX;
	private int screenY;
	private int myX;
	private int myY;
	private boolean isDragged;
	private boolean isPressed;
	private Point location;
	private int count;

	private ArrayList<String> users;
	public HourPanel()
	{
		UIManager.put("ToolTip.background", new ColorUIResource(255, 255, 255));
		users = new ArrayList<String>();
		count = 0;
		this.setLayout(new MigLayout("fill"));
		state = false;
		this.setBackground(Color.white);
		this.setBorder(new MatteBorder(1, 1, 0, 0, CalendarUtils.lineColor));
	}
	public void setHour(int hour)
	{
		setHour = hour;
	}
	public int getHour()
	{
		return setHour;
	}
	public void configCol(Color col)
	{
		configColor = col;
	}
	public void setColor(Color col)
	{
		this.setBackground(col);
	}
	public ArrayList<String> userList()
	{
		return users;
	}
	public Color getColor()
	{
		return this.getBackground();
	}
	public void setState(boolean set)
	{
		state = set;
		if(set) {
			setColor(configColor);
			this.setBorder(new MatteBorder(1, 1, 0, 0, Color.white));
		}
		else{
			setColor(Color.white);
			this.setBorder(new MatteBorder(1, 1, 0, 0, CalendarUtils.lineColor));
		}

	}
	public boolean getState()
	{
		return state;
	}


	public void updateCount(String user)
	{
		System.out.println("user"+user);
		boolean update = false;
		if(users.size() == 0)
		{
			count++;
			users.add(user);
		}else{

			for(int i = 0; i < users.size(); i++){
				if(users.get(i).equals(user)){
					update = true;
				}
			}
			if(update == false){
				count++;
				users.add(user);
			}
		}

		configColorCount(count);
	}



	private void configColorCount(int count) {
		
		Color highDensity = configColor;
		Color mediumHighDensity = CalendarUtils.blend(highDensity, Color.white, (float) 0.8);
		Color mediumDensity  =  CalendarUtils.blend(highDensity, Color.white, (float) 0.6);
		Color mediumLowDensity = CalendarUtils.blend(highDensity, Color.white, (float) 0.4);
		Color lowDensity = CalendarUtils.blend(highDensity, Color.white, (float) 0.2);
		
		switch(count){
			case 0:
				setColor(Color.white);
				this.setBorder(new MatteBorder(1, 1, 0, 0, CalendarUtils.lineColor));
				break;
			case 1:
				setColor(lowDensity);
				this.setBorder(new MatteBorder(1, 1, 0, 0, Color.white));
				break;
			case 2:
				setColor(mediumLowDensity);
				this.setBorder(new MatteBorder(1, 1, 0, 0, Color.white));
				break;
			case 3:
				setColor(mediumDensity);
				this.setBorder(new MatteBorder(1, 1, 0, 0, Color.white));
				break;
			case 4:
				setColor(mediumHighDensity);
				this.setBorder(new MatteBorder(1, 1, 0, 0, Color.white));
				break;
			case 5:
				setColor(highDensity);
				this.setBorder(new MatteBorder(1, 1, 0, 0, Color.white));
				break;
			default:
				setColor(highDensity);
				this.setBorder(new MatteBorder(1, 1, 0, 0, Color.white));
				break;
		}
	}
	public int getCount()
	{
		return count;
	}
	
	public void reduceCount(String user)
	{
		for(int i = 0; i < users.size(); i++){
			if(users.get(i).equals(user)){
				count--;
				users.remove(i);
			}
		}
		configColorCount(count);
	}

	public void removeSelected()
	{
		state = false;
	}
	public boolean switchState()
	{
		if(state == true) setColor(new Color(255,20,147));
		else setColor(Color.white);
		return !state;
	}

	public boolean isSelected()
	{
		return state;
	}

	public void setIndex(int x, int y)
	{
		location = new Point(x, y);
	}
	public Point getLocation()
	{
		return location;
	}
	public void resetCount() {
		count = 0;

	}
	public Hour config() {
		Hour hr = new Hour();
		hr.setIndex(location.x, location.y);
		hr.setState(this.getState());
		hr.setColor(this.getColor());
		hr.setCount(this.getCount());
		hr.addUsers(users);
		return hr;
	}
	
	public void reSize(int width){
		this.setSize(width, this.getHeight());
		this.setPreferredSize(new Dimension(width, this.getHeight()));
		this.setMinimumSize(new Dimension(0,this.getHeight()));
		
		this.repaint();
	}

}
