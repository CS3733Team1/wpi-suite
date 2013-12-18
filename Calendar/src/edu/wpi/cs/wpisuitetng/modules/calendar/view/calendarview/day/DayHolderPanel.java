package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day;

import java.awt.Dimension;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollBar;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.event.Event;
import net.miginfocom.swing.MigLayout;

/**
 * Holds the two panels responsible for displaying the information of the current day.
 * One panel holds the hour labels, the other holds panels representing events and commitments.
 */
public class DayHolderPanel extends JPanel{
	private DayArea day, previous, next;
	private HourLabels prevhour, hour, nexthour;
	private int height = 3*1440;

	public DayHolderPanel(){
		this.setLayout(new MigLayout("inset 0, fill", 
				"[10%][90%]"));

		prevhour = new HourLabels();
		hour = new HourLabels();
		nexthour = new HourLabels();
		
		day = new DayArea();
		Date time = day.getDayViewDate();
		previous = new DayArea(new Date(time.getYear(), time.getMonth(), time.getDate()-1));
		next = new DayArea(new Date(time.getYear(), time.getMonth(), time.getDate() + 1));
		
		int height = 3*1440;
		int width = 1000;
		
		this.setSize(width, height);
		this.setPreferredSize(new Dimension(width, height));
		this.setMinimumSize(new Dimension(0,height));
		
		this.add(prevhour);
		this.add(previous, "wrap");
		this.add(hour);
		this.add(day, "wrap");
		this.add(nexthour);
		this.add(next);
	}
	
	/**
	 * Resize method to deal with changing parent panel width
	 * @param width new width to adapt to
	 */
	public void reSize(int width){
		//The height must always be 1440, because 1 pixel per minute
		this.setSize(width, height);
		this.setPreferredSize(new Dimension(width, height));
		this.setMinimumSize(new Dimension(0,height));
		
		//Give the daypanel 90% of the screen
		day.reSize(new Integer((int) (width * .9)));
		//give the hour panel remaining 10%
		hour.reSize(new Integer((int) (width * .1)));
		
		//Give the daypanel 90% of the screen
		previous.reSize(new Integer((int) (width * .9)));
		//give the hour panel remaining 10%
		prevhour.reSize(new Integer((int) (width * .1)));

		//Give the daypanel 90% of the screen
		next.reSize(new Integer((int) (width * .9)));
		//give the hour panel remaining 10%
		nexthour.reSize(new Integer((int) (width * .1)));
		
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
		previous.next();
		day.next();
		next.next();
	}

	/**
	 * request that the day move back by 1
	 */
	public void previous() {
		previous.previous();
		day.previous();
		next.previous();
	}

	/**
	 * request that day displays today
	 */
	public void today() {
		day.today();
		previous.today();
		next.today();
		next.next();
		previous.previous();
	}

	/**
	 * Passes request to view a specific date down to the day
	 * @param date the date being requested
	 */
	public void viewDate(Calendar date) {
		day.viewDate(date);
		Date current = date.getTime();
		next.viewDate(new Date(current.getYear(), current.getMonth(), current.getDate()+1));
		previous.viewDate(new Date(current.getYear(), current.getMonth(), current.getDate()-1));
	}

}
