package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.week;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.ListIterator;

import javax.swing.JPanel;

import java.util.List;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day.DayArea;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day.HourLabels;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.event.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.filter.FilteredEventsListModel;

/**
 * Panel that holds the panels responsible for displaying the week's single day events and commitments.
 * Holds a panel with hour labels, and an ArrayList of DayArea panels for each day of the week.
 */
public class WeekHolderPanel extends JPanel{
	public static final String[] weekNames = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
	public static final String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	
	private ArrayList<DayArea> day;
	private HourLabels hour;
	private Date currentDay;

	/**
	 * Creates a panel which holds the hours and the 7 days of a week
	 */
	public WeekHolderPanel(){
		this.setLayout(new MigLayout("inset 0, fill", 
				"[9%][13%][13%][13%][13%][13%][13%][13%]"));

		hour = new HourLabels();
		day = new ArrayList<DayArea>();
		
		int height = 1440;
		int width = 1000;
		
		this.setSize(width, height);
		this.setPreferredSize(new Dimension(width, height));
		this.setMinimumSize(new Dimension(0,1440));
		
		this.add(hour);
		createWeek();
	}
	
	/**
	 * Creates the days and sets each one with the correct date based on NOW
	 */
	public void createWeek(){
		Date today = new Date();
		int correctday = today.getDay();
		int currentDate = today.getDate() - correctday;
		currentDay = new Date(today.getYear(), today.getMonth(), currentDate);
		for (int x = 0; x < 7; x++){
			day.add(new DayArea(new Date(currentDay.getYear(), currentDay.getMonth(), currentDay.getDate()+x)));
			day.get(x).setPreferredSize(new Dimension((int) (this.getWidth() * .13), 1440));
			day.get(x).setSize(day.get(x).getPreferredSize());
			this.add(day.get(x));
		}
	}
	
	/**
	 * Resizes the width of this view to reflect parents calculated size
	 * @param width size parent wants view to assume
	 */
	public void reSize(int width){
		this.setSize(width, 1440);
		this.setPreferredSize(new Dimension(width, 1440));
		this.setMinimumSize(new Dimension(0,1440));
		
		for (int x = 0; x < day.size(); x++){
			day.get(x).reSize(new Integer((int) (width * .13)));
		}
		hour.reSize(new Integer((int) (width * .09)));
		
		this.repaint();
	}
	
	/**
	 * Calculates the list of multidayevents that belong to the week
	 * @return list of 7 lists, which contain the multiday events for each day of the week
	 */
	public List<List<Event>> getMultiDayEvents(){
		Date key;
		List<Event> mevents = new LinkedList<Event>();
		
		ListIterator<Event> event = FilteredEventsListModel.getFilteredEventsListModel().getList().listIterator();
		
		Date enddate = new Date(currentDay.getYear(), currentDay.getMonth(), currentDay.getDate()+7);
		while(event.hasNext()){
			Event eve = new Event(event.next());
			Date evedate = eve.getStartDate();
			key = new Date(evedate.getYear(),evedate.getMonth(),evedate.getDate(),evedate.getHours(),0);
			if ((key.after(currentDay) || key.equals(currentDay)) && key.before(enddate) ){
				if(eve.getStartDate().getDate() == eve.getEndDate().getDate()
				&& eve.getStartDate().getMonth() == eve.getEndDate().getMonth()
				&& eve.getStartDate().getYear() == eve.getEndDate().getYear())
				{
					//eventlist.add(eve);
				}
				else
				{
					mevents.add(eve);
				}
			}
			else if(currentDay.after(eve.getStartDate()) && currentDay.before(eve.getEndDate()))
			{
				mevents.add(eve);
			}
		}
		
		
		Date eveenddate = new Date();
		ArrayList<Integer> weekdays = new ArrayList<Integer>();
		List<List<Event>> multilistlist = new LinkedList<List<Event>>();
		Date iter = (Date) currentDay.clone();
		for(int i = 0; i < 7; i++)
		{
			weekdays.add(i, iter.getDate());
			iter.setDate(iter.getDate() + 1);
			multilistlist.add(new LinkedList<Event>());
		}
		
		for(Event e: mevents)
		{
			if(currentDay.before(e.getStartDate()))
				iter = (Date) e.getStartDate().clone();
			else
				iter = (Date) currentDay.clone();
			eveenddate.setDate(e.getEndDate().getDate() + 1);
			while(!(iter.getDate() == eveenddate.getDate() || iter.getDate() == enddate.getDate()))
			{
				//System.out.println("Iter " + iter.getDate());
				if(weekdays.contains(iter.getDate()))
				{
					multilistlist.get(weekdays.indexOf(iter.getDate())).add(e);
					break;
					//System.out.println("Event " + e.getName() + " added to index " + weekdays.indexOf(iter.getDate()));
				}
				iter.setDate(iter.getDate() + 1); //setDate knows where month boundaries are
			}
		}
		
		return multilistlist;
	}
	
	public Date getDayViewDate(){
		return currentDay;
	}
     
	public String getTitle() {
		return monthNames[currentDay.getMonth()];
	}

	/**
	 * Moves the week ahead by one week
	 */
	public void next() {
		currentDay.setDate(currentDay.getDate()+7);
		for (int x = 0; x < day.size(); x++){
			DayArea changeday = day.get(x);
			changeday.viewDate(new Date(currentDay.getYear(), currentDay.getMonth(), currentDay.getDate()+x));
		}
	}

	/**
	 * Moves the week back by a week
	 */
	public void previous() {
		currentDay.setDate(currentDay.getDate()-7);
		for (int x = 0; x < day.size(); x++){
			DayArea changeday = day.get(x);
			changeday.viewDate(new Date(currentDay.getYear(), currentDay.getMonth(), currentDay.getDate()+x));
		}
	}

	/**
	 * changes the week to NOW's week
	 */
	public void today() {
		Date today = new Date();
		int correctday = today.getDay();
		int currentDate = today.getDate() - correctday;
		currentDay = new Date(today.getYear(), today.getMonth(), currentDate);
		for (int x = 0; x < day.size(); x++){
			DayArea changeday = day.get(x);
			changeday.viewDate(new Date(currentDay.getYear(), currentDay.getMonth(), currentDay.getDate()+x));
		}
	}
	
	public Date getDate(int day){
		return this.day.get(day-1).getDayViewDate();
	}
	
	public Calendar getCalendarDate(){
		Calendar c = Calendar.getInstance();
		c.setTime(this.currentDay);
		return c;
	}

	/**
	 * Changes the view of the week to Specific Date
	 * @param date Date the week will display
	 */
	public void viewDate(Calendar date) {
		Date today = date.getTime();
		int correctday = today.getDay();
		int currentDate = today.getDate() - correctday;
		currentDay = new Date(today.getYear(), today.getMonth(), currentDate);
		for (int x = 0; x < day.size(); x++){
			DayArea changeday = day.get(x);
			changeday.viewDate(new Date(currentDay.getYear(), currentDay.getMonth(), currentDay.getDate()+x));
		}
	}
}
