package edu.wpi.cs.wpisuitetng.modules.calendar.view.scheduledevent;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.CalendarUtils;
import net.miginfocom.swing.MigLayout;

public class DayOfWeek extends JPanel {

	private boolean state;
	private int setDay;
	private int dragLocationX;
	private int dragLocationY;
	private int screenX;
	private int screenY;
	private int myX;
	private int myY;
	private boolean isDragged;
	private boolean isPressed;
	private Color configColor;
	private JPanel dayTop;
	private JPanel dayBottom;
	private JLabel dayLabel;
	private String [] dayOfWeek = {"Sun", "Mon", "Tues", "Wed", "Thur", "Fri", "Sat"};

	public DayOfWeek()
	{
		this.setLayout(new MigLayout("fill,insets 0", "0[]0","0[5%]0[95%]0" ));
		dayTop = new JPanel(new MigLayout("insets 0"));
		dayBottom = new JPanel(new MigLayout("insets 0"));
		state = false;
		this.setBackground(Color.white);
		this.setBorder(new MatteBorder(1, 0, 0, 1, Color.gray));
		
		
	}
	public void setDay(int day)
	{
		dayLabel = new JLabel(""+dayOfWeek[day], JLabel.CENTER);
		dayLabel.setFont(new Font(dayLabel.getName(),Font.BOLD,14));
		dayLabel.setForeground(Color.gray);
		dayTop.add( dayLabel, "push, aligny center, alignx center" );
		this.add(dayTop,"cell 0 0, grow,push, wrap");
		this.add(dayBottom,"grow, push");
		setDay = day;
		if(day == 0 || day == 6){
			configColor = CalendarUtils.weekendColor;
			dayTop.setBackground(configColor);
			dayBottom.setBackground(configColor);
		}else{
			configColor = Color.white;
			dayTop.setBackground(configColor);
			dayBottom.setBackground(configColor);
		}
		
		
	}
	public String getDayString()
	{
		return dayOfWeek[getDay()];
	}
	
	
	public int getDay()
	{
		return setDay;
	}

	public void setColor(Color col)
	{
		dayTop.setBackground(col);
		dayBottom.setBackground(col);
	}
	public void setState(boolean set)
	{
		state = set;
		if(set){
			setColor(CalendarUtils.thatBlue);
			dayLabel.setForeground(Color.white);
			this.setBorder(new MatteBorder(1, 0, 0, 1, Color.white));
		}
		else{ 
			setColor(configColor);
			dayLabel.setForeground(Color.gray);
			this.setBorder(new MatteBorder(0, 0, 0, 1, CalendarUtils.lineColor));
		}
		
	}
	public boolean getState()
	{
		return state;
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

}
