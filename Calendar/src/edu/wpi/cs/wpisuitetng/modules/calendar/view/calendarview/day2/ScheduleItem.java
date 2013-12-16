package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day2;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.ISchedulable;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.CalendarUtils;

public class ScheduleItem extends JPanel{
	private ISchedulable display;
	private int StartX, StartY, width, height, division, location;
	
	
	public ScheduleItem(ISchedulable item, int sx, int sy, int w, int h, int div, int spot){
		display = item;
		StartX = sx;
		StartY = sy;
		width = w;
		height = h;
		division = div;
		location = spot;
		
		this.setBorder(BorderFactory.createLineBorder(CalendarUtils.darken(display.getCategory().getColor()),2,true));
		this.setMinimumSize(new Dimension(0,0));
	}
	
	public void updateSize(int x, int y, int w, int h){
		StartX = x;
		StartY = y;
		width = w;
		height = h;
		this.setBounds(StartX, StartY, width, height);
	}
	
	public void repaint(){
		if (this.getParent() != null){
			 StartX = location*(this.getParent().getWidth()/division);
             width = this.getParent().getWidth()/division;
             System.err.println(this.getParent().getWidth());
		}
		
		this.setBounds(StartX, StartY, width, height);
		
		super.repaint();
	}
	
	public ISchedulable getDisplayItem(){
		return display;
	}
	
	public Date getStartTime(){
		Date start = display.getStartDate();
		start.setHours(0);
		start.setMinutes(StartY);
		return start;
	}
	
	public Date getEndTime(){
		Date end = display.getEndDate();
		end.setHours(0);
		end.setMinutes(StartY+height);
		return end;
	}
    
   
}
