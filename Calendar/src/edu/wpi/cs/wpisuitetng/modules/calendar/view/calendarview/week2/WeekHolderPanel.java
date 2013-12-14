package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.week2;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day2.DayArea;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day2.HourLabels;

public class WeekHolderPanel extends JPanel{
	public static final String[] weekNames = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
	public static final String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	
	private ArrayList<DayArea> day;
	private HourLabels hour;
	private Date currentDay;

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
	
	public Date getDayViewDate(){
		return day.get(0).getDayViewDate();
	}
     
	public String getTitle() {
		return monthNames[currentDay.getMonth()];
	}

	public void next() {
		currentDay.setDate(currentDay.getDate()+7);
		for (int x = 0; x < day.size(); x++){
			DayArea changeday = day.get(x);
			changeday.viewDate(new Date(currentDay.getYear(), currentDay.getMonth(), currentDay.getDate()+x));
		}
	}

	public void previous() {
		currentDay.setDate(currentDay.getDate()-7);
		for (int x = 0; x < day.size(); x++){
			DayArea changeday = day.get(x);
			changeday.viewDate(new Date(currentDay.getYear(), currentDay.getMonth(), currentDay.getDate()+x));
		}
	}

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
