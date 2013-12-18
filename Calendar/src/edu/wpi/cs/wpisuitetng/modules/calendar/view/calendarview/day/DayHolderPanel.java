package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day;

import java.awt.Dimension;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;

import net.miginfocom.swing.MigLayout;

public class DayHolderPanel extends JPanel{
	private DayArea day;
	private HourLabels hour;

	public DayHolderPanel(){
		this.setLayout(new MigLayout("inset 0, fill", 
				"[10%][90%]"));

		hour = new HourLabels();
		day = new DayArea();
		
		int height = 1440;
		int width = 1000;
		
		this.setSize(width, height);
		this.setPreferredSize(new Dimension(width, height));
		this.setMinimumSize(new Dimension(0,1440));
		
		this.add(hour);
		this.add(day);
	}
	
	/**
	 * Resize method to deal with changing parent panel width
	 * @param width new width to adapt to
	 */
	public void reSize(int width){
		//The height must always be 1440, because 1 pixel per minute
		this.setSize(width, 1440);
		this.setPreferredSize(new Dimension(width, 1440));
		this.setMinimumSize(new Dimension(0,1440));
		
		//Give the daypanel 90% of the screen
		day.reSize(new Integer((int) (width * .9)));
		//give the hour panel remaining 10%
		hour.reSize(new Integer((int) (width * .1)));
		
		this.repaint();
	}
	
	public List<Event> getMultiDayEvents(){
		return day.getMultiDayEvents();
	}
	
	public Date getDayViewDate(){
		return day.getDayViewDate();
	}
    
	/**
	 * 
	 * @return
	 */
	public String getTitle() {
		return day.getTitle();
	}

	/**
	 * request that the day move ahead by 1
	 */
	public void next() {
		day.next();
	}

	/**
	 * request that the day move back by 1
	 */
	public void previous() {
		day.previous();
	}

	/**
	 * request that day displays today
	 */
	public void today() {
		day.today();
	}

	/**
	 * Passes request to view a specific date down to the day
	 * @param date the date being requested
	 */
	public void viewDate(Calendar date) {
		day.viewDate(date);
	}
}
