/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.week;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.FilteredCommitmentsListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.DatePanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.CalendarUtils;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.DateUtils;

/**
 * This is the bottom layer of the week view hierarchy. 
 */

public class WeekView extends JPanel{
	public static final String[] weekNames = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
	public static final String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

	private ArrayList<DatePanel> nameList;
	private ArrayList<JPanel> hourlist;
	private HashMap<Date, DatePanel> paneltracker;

	private int numDays;
	
	private Calendar mycal;
	private int currentMonth;
	private int currentYear;
	private int currentDate;
	private boolean todayWithinWeek;
	private int todayIndex;
	private int todayDate;

	public WeekView(){
		todayDate = 0;
		todayIndex = 0;
		todayWithinWeek = false;
		nameList = new ArrayList<DatePanel>();
		paneltracker = new HashMap<Date, DatePanel>();
		hourlist = new ArrayList<JPanel>();

		Date today = new Date();

		int correctday = today.getDay();
		currentDate = today.getDate() - correctday;
		currentYear = today.getYear() + 1900;
		currentMonth = today.getMonth();

		mycal = new GregorianCalendar(currentYear, currentMonth, currentDate);
		this.setBackground(Color.white);

		this.setLayout(new MigLayout("fill", 
				"0[8.75%]3[12.667%]3[12.667%]3[12.667%]3[12.667%]3[12.667%]3[12.667%]3[12.667%]0", 
				"0[4%]0[4%]0[4%]0[4%]0[4%]0[4%]0[4%]0[4%]0[4%]0[4%]0[4%]0[4%]0[4%]0[4%]0[4%]0[4%]0[4%]0[4%]0[4%]0[4%]0[4%]0[4%]0[4%]0[4%]0[4%]0"));

		this.setVisible(true);

		fillDayView();
	}

	public void fillDayView(){
		todayWithinWeek = false;
		JPanel time = new JPanel();

		for (int currenthour=0; currenthour < 24; currenthour++){
			JPanel hour = new JPanel();

			StringBuilder hourbuilder = new StringBuilder();
			hourbuilder.append("cell ");
			hourbuilder.append("0");
			hourbuilder.append(" ");
			hourbuilder.append((new Integer(currenthour+1)).toString());
			hourbuilder.append(",grow, push");
			
			JLabel label = new JLabel(DateUtils.timeToString(currenthour,0));
			label.setForeground(CalendarUtils.timeColor);

			hour.add(label);
			hour.setBackground(Color.white);
			this.add(hour, hourbuilder.toString());
			hourlist.add(hour);
		}

		for (int currentday=1; currentday<8;currentday++){
			for (int currenthour=0; currenthour < 24; currenthour++){

				DatePanel thesecond = new DatePanel();

				StringBuilder datebuilder = new StringBuilder();
				datebuilder.append("cell ");
				datebuilder.append((new Integer(currentday)).toString());
				datebuilder.append(" ");
				datebuilder.append((new Integer(currenthour+1)).toString());
				datebuilder.append(",grow, push");

				Date hue = new Date(currentYear-1900, currentMonth, currentDate + (currentday - 1), currenthour, 0);
				thesecond.setDate(hue);
				
				Calendar cal = Calendar.getInstance();
				
				if(currentday == 1 || currentday ==7)
					thesecond.setBackground(CalendarUtils.weekendColor);
				else 
					thesecond.setBackground(Color.WHITE);	
				
				if(cal.get(Calendar.MONTH) == currentMonth &&
						cal.get(Calendar.YEAR) == currentYear && 
						cal.get(Calendar.DATE) == currentDate + (currentday - 1)){
							todayWithinWeek = true;
							todayIndex = currentday;
							thesecond.setBackground(CalendarUtils.selectionColor);
							thesecond.setBorder(new MatteBorder(0, 0, 1, 0, CalendarUtils.thatBlue));
				}
				
				thesecond.setBorder(new MatteBorder(0, 0, 1, 0, Color.gray));
				this.add(thesecond, datebuilder.toString());
				paneltracker.put(hue, thesecond);
				nameList.add(thesecond);
			}
		}
	}
	public boolean isToday()
	{
		return todayWithinWeek;
	}
	
	public int getDate(int index)
	{
		Calendar cal = new GregorianCalendar(currentYear, currentMonth, 1);
		
		if(cal.getActualMaximum(Calendar.DAY_OF_MONTH) >= currentDate + (index -1))
			return currentDate + (index - 1);
		else
			return (currentDate + (index -1))- cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		
	}
	
	public int getIndex()
	{
		return todayIndex;
	}

	/**
	 * Finds All Commitments Belonging to Calendar
	 * @return List of Found Commitments
	 */
	public List<Commitment> CommitmentsOnCalendar(){
		List<Commitment> notevenclose = new LinkedList<Commitment>();
		for (Commitment commit: FilteredCommitmentsListModel.getFilteredCommitmentsListModel().getList()){
			Date commitdate = commit.getDueDate();
			Date teemo = new Date(commitdate.getYear(),commitdate.getMonth(),commitdate.getDate(),commitdate.getHours(),0);
			if (paneltracker.containsKey(teemo)){
				//System.out.println("I'm Invisible"); //never underestimate the power of the scout's code
				notevenclose.add(commit);
			}
		}
		
		return notevenclose;
	}
	
	public String getTitle() {
		Date datdate = new Date(currentYear-1900, currentMonth, currentDate+6);
		int nextyear = datdate.getYear() + 1900;
		int nextmonth = datdate.getMonth();
		int nextdate = datdate.getDate();

		return monthNames[nextmonth] + " " + nextyear;
	}

	public void next() {
		currentDate = currentDate + 7;
		Date today = new Date(currentYear-1900, currentMonth, currentDate);
		currentDate = today.getDate();
		currentMonth = today.getMonth();
		currentYear = today.getYear() + 1900;

		this.removeAll();
		nameList = new ArrayList<DatePanel>();
		paneltracker = new HashMap<Date, DatePanel>();
		hourlist = new ArrayList<JPanel>();
		fillDayView();
	}

	public void previous() {
		currentDate = currentDate - 7;
		Date today = new Date(currentYear-1900, currentMonth, currentDate);
		currentDate = today.getDate();
		currentMonth = today.getMonth();
		currentYear = today.getYear() + 1900;

		this.removeAll();
		nameList = new ArrayList<DatePanel>();
		paneltracker = new HashMap<Date, DatePanel>();
		hourlist = new ArrayList<JPanel>();
		fillDayView();
	}

	public void today() {
		Date today = new Date();
		int correctday = today.getDay();
		if(!(currentDate == today.getDate() - correctday && currentMonth == today.getMonth() && currentYear == today.getYear() + 1900)) {

			currentDate = today.getDate() - correctday;
			currentMonth = today.getMonth();
			currentYear = today.getYear() + 1900;

			this.removeAll();
			nameList = new ArrayList<DatePanel>();
			paneltracker = new HashMap<Date, DatePanel>();
			hourlist = new ArrayList<JPanel>();
			fillDayView();
		}
	}

	public HashMap<Date, DatePanel> getMap(){
		return paneltracker;
	}

	public Date getStart(){
		return new Date(currentYear-1900, currentMonth, currentDate);
	}

	public void viewDate(Date date) {
		int correctday = date.getDay();
		if(!(currentDate == date.getDate() - correctday && currentMonth == date.getMonth() && currentYear == date.getYear() + 1900)) {

			currentDate = date.getDate() - correctday;
			currentMonth = date.getMonth();
			currentYear = date.getYear() + 1900;

			this.removeAll();
			nameList = new ArrayList<DatePanel>();
			paneltracker = new HashMap<Date, DatePanel>();
			hourlist = new ArrayList<JPanel>();
			fillDayView();
		}
	}

}
