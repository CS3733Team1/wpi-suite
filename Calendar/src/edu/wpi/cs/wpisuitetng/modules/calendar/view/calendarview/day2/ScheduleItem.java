package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day2;

import java.awt.Dimension;
import java.util.Date;

import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.ISchedulable;

public class ScheduleItem extends JPanel{
	private ISchedulable display;
	private int StartX, StartY, width, height, division, location;
	private boolean changed = false;
	
	public ScheduleItem(ISchedulable item, int sx, int sy, int w, int h, int div, int spot){
		display = item;
		StartX = sx;
		StartY = sy;
		width = w;
		height = h;
		division = div;
		location = spot;
		
		this.setMinimumSize(new Dimension(0,0));
	}
	
	public void updateSize(int x, int y, int w, int h){
		changed = false;
		if (StartX != x || StartY != y || width != w || height != h){
			changed = true;
		}
		StartX = x;
		StartY = y;
		width = w;
		height = h;
		this.setBounds(StartX, StartY, width, height);
	}
	
	public void updateSize(int x, int y){
		changed = false;
		if (StartX != x || StartY != y){
			changed = true;
		}
		StartX = x;
		StartY = y;
		this.setBounds(StartX, StartY, width, height);
	}
	
	public boolean isChanged(){
		return changed;
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
