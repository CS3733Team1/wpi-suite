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
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.ICalendarView;

/**
 * This is the bottom layer of the week view hierarchy. 
 */

public class WeekView extends JPanel implements ICalendarView {
	public static final String[] weekNames = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
	public static final String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

	private ArrayList<DatePanel> nameList;
	private ArrayList<JPanel> hourlist;
	private HashMap<Date, DatePanel> paneltracker;

	private Calendar mycal;
	private int currentMonth;
	private int currentYear;
	private int currentDate;

	public WeekView(){
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

		this.setLayout(new MigLayout("fill, debug", 
				"[9%]2[13%]3[13%]3[13%]3[13%]3[13%]3[13%]3[13%]3",
				"[4%]4[4%]4[4%]4[4%]4[4%]4[4%]4[4%]4[4%]4[4%]4[4%]4[4%]4[4%]4[4%]4[4%]4[4%]4[4%]4[4%]4[4%]4[4%]4[4%]4[4%]4[4%]4[4%]4[4%]4[4%]4"));

		this.setVisible(true);

		fillDayView();
	}

	public void fillDayView(){

		JPanel time = new JPanel();

		StringBuilder timebuilder = new StringBuilder();
		timebuilder.append("cell ");
		timebuilder.append("0");
		timebuilder.append(" ");
		timebuilder.append("0");
		timebuilder.append(",grow, push");

		time.add(new JLabel(" "));
		time.setBackground(new Color(255,255,255));
		this.add(time, timebuilder.toString());

		for (int currenthour=0; currenthour < 24; currenthour++){
			JPanel hour = new JPanel();

			StringBuilder hourbuilder = new StringBuilder();
			hourbuilder.append("cell ");
			hourbuilder.append("0");
			hourbuilder.append(" ");
			hourbuilder.append((new Integer(currenthour+1)).toString());
			hourbuilder.append(",grow, push");

			hour.add(new JLabel(currenthour+":00"));
			hour.setBackground(new Color(236,252,144));
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
				thesecond.setBackground(Color.WHITE);
				thesecond.setBorder(new MatteBorder(0, 0, 1, 0, Color.gray));
				this.add(thesecond, datebuilder.toString());
				paneltracker.put(hue, thesecond);
				nameList.add(thesecond);
			}
		}

		for (int day = 1; day < 8; day++){
			JPanel event = new JPanel();

			StringBuilder eventbuilder = new StringBuilder();
			eventbuilder.append("cell ");
			eventbuilder.append((new Integer(day)).toString());
			eventbuilder.append(" ");
			eventbuilder.append("0");
			eventbuilder.append(",grow, push");
			event.add(new JLabel(" "));
			event.setBackground(new Color(255,255,255));
			this.add(event, eventbuilder.toString());

		}
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
				System.out.println("I'm Invisible"); //never underestimate the power of the scout's code
				notevenclose.add(commit);
			}
		}
		
		return notevenclose;
	}
	
	@Override
	public String getTitle() {
		Date datdate = new Date(currentYear-1900, currentMonth, currentDate+6);
		int nextyear = datdate.getYear() + 1900;
		int nextmonth = datdate.getMonth();
		int nextdate = datdate.getDate();

		return monthNames[currentMonth] + " "+ currentDate + ", " + currentYear + " - " + monthNames[nextmonth] + " "+ nextdate + ", " + nextyear;
	}

	@Override
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

	@Override
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

	@Override
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

}
