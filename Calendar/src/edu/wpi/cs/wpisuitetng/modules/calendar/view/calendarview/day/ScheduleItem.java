package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.ISchedulable;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.CalendarUtils;

/**
 * A panel to hold and correctly display events and commitments on week and day view
 */
public class ScheduleItem extends JPanel{
	private ISchedulable display;
	private int StartX, StartY, width, height, division, location;
	private boolean changed = false;
	
	/**
	 * Constructor for Creating a ScheduleItem
	 * @param item The Item that is being displayed
	 * @param sx starting x position of item in chart
	 * @param sy starting y position of item in chart 
	 * @param w width of item in chart
	 * @param h height of item in chart
	 * @param div how many people this item is sharing its spot with
	 * @param spot the position in line of this item
	 */
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
	
	/**
	 * Updates the Event Bounds while keeping track of updates
	 * @param x starting x position
	 * @param y starting y position
	 * @param w width of object
	 * @param h height of object
	 */
	public void updateSize(int x, int y, int w, int h){
		//Keep track of whether data was changed if it wasn't no need to update the item with database
		changed = false;
		if (StartX != x || StartY != y || width != w || height != h){
			changed = true;
		}
		StartX = x;
		StartY = y;
		width = w;
		height = h;
		this.setBounds(StartX, StartY, width, Math.min(Math.abs(height), (1440 - StartY)));
	}
	
	/**
	 * Overloaded for only changing x and y
	 * @param x starting x position
	 * @param y starting y position
	 */
	public void updateSize(int x, int y){
		//Keep track of whether data was changed if it wasn't no need to update the item with database
		changed = false;
		if (StartX != x || StartY != y){
			changed = true;
		}
		StartX = x;
		StartY = y;
		this.setBounds(StartX, StartY, width, Math.min(Math.abs(height), (1440 - StartY)));
	}
	
	/**
	 * Calculates whether data inside was changed
	 * @return returns whether data was changed
	 */
	public boolean isChanged(){
		return changed;
	}
	
	/**
	 * Repaints item, but recalculates bounds
	 */
	public void repaint(){
		//Check for the parents width and calculates its spot and division
		if (this.getParent() != null){
			 StartX = location*(this.getParent().getWidth()/division);
             width = this.getParent().getWidth()/division;
		}
	
		//Sets new bounds
		this.setBounds(StartX, StartY, width, Math.min(Math.abs(height), (1440 - StartY)));
		
		super.repaint();
	}
	
	/**
	 * Returns the schedulable item being displayed
	 * @return item being displayed
	 */
	public ISchedulable getDisplayItem(){
		return display;
	}
	
	/**
	 * recalculates the start time of the item and changes it based on startY
	 * @return the new updated start time of item
	 */
	public Date getStartTime(){
		Date start = display.getStartDate();
		start.setHours(0);
		start.setMinutes(StartY);
		return start;
	}
	
	/**
	 * recalculates the end time of the item based on StartY and length
	 * @return new end time of the item
	 */
	public Date getEndTime(){
		Date end = display.getEndDate();
		end.setHours(0);
		end.setMinutes(StartY+height);
		return end;
	}
    
   
}
