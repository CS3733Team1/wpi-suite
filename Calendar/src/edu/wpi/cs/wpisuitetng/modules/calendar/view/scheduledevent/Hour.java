package edu.wpi.cs.wpisuitetng.modules.calendar.view.scheduledevent;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.util.ArrayList;



public class Hour {

	private boolean state;
	private int hour;
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
	private Color configColor;

	private ArrayList<String> users;
	public Hour()
	{
		users = new ArrayList<String>();
		count = 0;
		state = false;
	}
	public void setCount(int count)
	{
		this.count = count;
	}
	
	public boolean containsName(String user)
	{
		boolean hasName = false;
		for(int i=0; i < users.size(); i++)
		{
			if(users.get(i).equals(user)){
				hasName= true;
			}
		}
		return hasName;
	}
	
	
	public ArrayList<String> userList()
	{
		return users;
	}
	public void setHour(int hour)
	{
		this.hour = hour;
	}
	public int getHour()
	{
		return hour;
	}
	public void  addUsers(ArrayList<String> user)
	{
		this.users = users;
	}

	public void setColor(Color col)
	{
		configColor = col;
	}
	public void setState(boolean set)
	{
		state = set;
		if(set) setColor(new Color(255,20,147));
		else setColor(Color.white);

	}
	public boolean getState()
	{
		return state;
	}


	public void updateCount(String user)
	{

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
		if(count == 0){
			setColor(Color.white);
		}
		if(count == 1){
			setColor(new Color(255,20,147));
		}if(count == 2)
			setColor(Color.BLUE);

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
	public String toString()
	{
		return " "+hour;
	}
	
	public boolean switchState()
	{
		if(state == true) setColor(new Color(255,20,147));
		else setColor(Color.white);
		return !state;
	}
	public int getCount()
	{
		return count;
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

}
