/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day;

import java.awt.Color;
import java.text.DateFormat;
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
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.FilteredCommitmentsListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.DatePanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.ICalendarView;

public class DayView extends JPanel implements ListDataListener {
	public static final String[] weekNames = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
	public static final String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

	private ArrayList<DatePanel> nameList;
	private ArrayList<JPanel> hourlist;
	private HashMap<Date, DatePanel> paneltracker;

	private Calendar mycal;
	private int currentMonth;
	private int currentYear;
	private int currentDate;

	private ArrayList<Event> eventlist = new ArrayList();

	/**
	 * DayView Constructor, which setups the initial dayview view
	 */
	public DayView (){
		nameList = new ArrayList<DatePanel>();
		paneltracker = new HashMap<Date, DatePanel>();
		hourlist = new ArrayList<JPanel>();

		Date today = new Date();

		currentYear = today.getYear() + 1900;
		currentMonth = today.getMonth();
		currentDate = today.getDate();

		mycal = new GregorianCalendar(currentYear, currentMonth, currentDate);
		this.setBackground(Color.white);

		this.setLayout(new MigLayout("fill", 
				"[20%][80%]", 
				"[4%]1[4%]1[4%]1[4%]1[4%]1[4%]1[4%]1[4%]1[4%]1[4%]1[4%]1[4%]1[4%]1[4%]1[4%]1[4%]1[4%]1[4%]1[4%]1[4%]1[4%]1[4%]1[4%]1[4%]1[4%]1"));

		this.setVisible(true);

		fillDayView();
		//DisplayCommitments();
		
		FilteredCommitmentsListModel.getFilteredCommitmentsListModel().addListDataListener(this);
	}

	/**
	 * Fills In Dayview with various panels and labels
	 */
	public void fillDayView(){
		JPanel event = new JPanel();

		StringBuilder eventbuilder = new StringBuilder();
		eventbuilder.append("cell ");
		eventbuilder.append("1");
		eventbuilder.append(" ");
		eventbuilder.append("0");
		eventbuilder.append(",grow, push");

		event.add(new JLabel(" "));
		event.setBackground(Color.WHITE);
		this.add(event, eventbuilder.toString());

		JPanel time = new JPanel();

		StringBuilder timebuilder = new StringBuilder();
		timebuilder.append("cell ");
		timebuilder.append("0");
		timebuilder.append(" ");
		timebuilder.append("0");
		timebuilder.append(",grow, push");

		time.add(new JLabel(" "));
		time.setBackground(Color.WHITE);
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

			DatePanel thesecond = new DatePanel();

			StringBuilder datebuilder = new StringBuilder();
			datebuilder.append("cell ");
			datebuilder.append("1");
			datebuilder.append(" ");
			datebuilder.append((new Integer(currenthour+1)).toString());
			datebuilder.append(",grow, push");

			Date hue = new Date(currentYear-1900, currentMonth, currentDate, currenthour, 0);
			thesecond.setDate(hue);
			thesecond.setBackground(Color.WHITE);
			thesecond.setBorder(new MatteBorder(0, 0, 1, 0, Color.gray));
			this.add(thesecond, datebuilder.toString());
			paneltracker.put(hue, thesecond);
			nameList.add(thesecond);
		}
	}
	
	/**
	 * Displays Commitments on DayView 
	 */
	public void DisplayCommitments(){
		List<List<Commitment>> foundyou = bananaSplit(CommitmentsOnCalendar());
		
		for (int x = 0; x < 24; x++){
			if (foundyou.get(x).size() > 0){
				JPanel hour = hourlist.get(x);
				hour.setBackground(Color.RED);
				StringBuilder bob = new StringBuilder();
				bob.append("<html>");
				int i=1;
				for (Commitment commit: foundyou.get(x)){
					bob.append("<p style='width:175px'>");
					if(i != 1)
					{
					bob.append("<br>");
					}
					bob.append("Commitment ");
					bob.append(new Integer(i).toString()+":");
					i++;
					bob.append("<br>");
					bob.append("<b>Name:</b> ");
					bob.append(commit.getName());
					bob.append("<br><b>Due Date:</b> ");
					bob.append(DateFormat.getInstance().format(commit.getDueDate()));
					if(commit.getCategory()!=null){
						bob.append("<br><b>Category: </b>");
						bob.append(commit.getCategory().getName());
					}
					if(commit.getDescription().length()>0){
						bob.append("<br><b>Description:</b> ");
						bob.append(commit.getDescription());
					}
					bob.append("</p>");
				}
				bob.append("</html>");
				hour.setToolTipText(bob.toString());
			}
		}
	}
	
	/**
	 * Rebuilds the hour panels, so that they are normalized
	 */
	public void rebuildHours(){
		for (int x = 0; x < hourlist.size(); x++){
			this.remove(hourlist.get(x));
		}
		
		hourlist = new ArrayList<JPanel>();
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
	}
	
	/**
	 * Splits The List Into Hourly Blocks
	 * @param thoseitems the list being split
	 * @return split list of hours
	 */
	public List<List<Commitment>> bananaSplit(List<Commitment> thoseitems){
		List<List<Commitment>> dalist = new LinkedList<List<Commitment>>();
		
		for (int x = 0; x < 24; x++){
			List<Commitment> hourlist = new LinkedList<Commitment>();
			for (Commitment commit: thoseitems){
				if (commit.getDueDate().getHours() == x){
					hourlist.add(commit);
				}
			}
			dalist.add(hourlist);
		}
		return dalist;
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
	
	/**
	 * Gets the Title for the current Day
	 * @return String title of the day in Month Date, Year
	 */
	public String getTitle() {
		return monthNames[currentMonth] + " "+ currentDate+ ", " + currentYear;
	}

	/**
	 * Clears the events from the currentview
	 */
	public void ClearEvents(){
		for (int x = 0;x < nameList.size(); x++){
			nameList.get(x).removeEventPanel();
		}
	}

	/**
	 * Moves the Calendar Ahead by 1 Day
	 */
	public void next() {
		currentDate++;
		Date today = new Date(currentYear-1900, currentMonth, currentDate);
		currentDate = today.getDate();
		currentMonth = today.getMonth();
		currentYear = today.getYear() + 1900;

		this.removeAll();
		nameList = new ArrayList<DatePanel>();
		paneltracker = new HashMap<Date, DatePanel>();
		hourlist = new ArrayList<JPanel>();
		fillDayView();
		DisplayCommitments();
	}

	/**
	 * Moves the Calendar Back by 1 Day
	 */
	public void previous() {
		currentDate--;
		Date today = new Date(currentYear-1900, currentMonth, currentDate);
		currentDate = today.getDate();
		currentMonth = today.getMonth();
		currentYear = today.getYear() + 1900;

		this.removeAll();
		nameList = new ArrayList<DatePanel>();
		paneltracker = new HashMap<Date, DatePanel>();
		hourlist = new ArrayList<JPanel>();
		fillDayView();
		DisplayCommitments();
	}

	/**
	 * Moves the Calendar to Current Day
	 */
	public void today() {
		Date today = new Date();
		if(!(currentDate == today.getDate() && currentMonth == today.getMonth() && currentYear == today.getYear() + 1900)) {
			currentDate = today.getDate();
			currentMonth = today.getMonth();
			currentYear = today.getYear() + 1900;

			this.removeAll();
			nameList = new ArrayList<DatePanel>();
			paneltracker = new HashMap<Date, DatePanel>();
			hourlist = new ArrayList<JPanel>();
			fillDayView();
			DisplayCommitments();
		}
	}
	
	/**
	 * ViewDate changes the date of Calendar to current date
	 * @param day The Date to change Calendar to
	 */
	public void viewDate(Date day) {
		if(!(currentDate == day.getDate() && currentMonth == day.getMonth() && currentYear == day.getYear() + 1900)) {
			currentDate = day.getDate();
			currentMonth = day.getMonth();
			currentYear = day.getYear() + 1900;

			this.removeAll();
			nameList = new ArrayList<DatePanel>();
			paneltracker = new HashMap<Date, DatePanel>();
			hourlist = new ArrayList<JPanel>();
			fillDayView();
			DisplayCommitments();
		}
	}

	/**
	 * Getter For Date of Calendar
	 * @return current Date
	 */
	public Date getDate()
	{
		return new Date(currentYear-1900,currentMonth,currentDate);
	}
	
	/**
	 * Getter Method for Hashmap of Dates to Panels
	 * @return The Hashmap containing the current dates supported in view
	 */
	public HashMap<Date,DatePanel> getMap()
	{
		return paneltracker;
	}

	@Override
	public void intervalAdded(ListDataEvent e) {
		rebuildHours();
		DisplayCommitments();
		this.updateUI();
	}

	@Override
	public void intervalRemoved(ListDataEvent e) {
		rebuildHours();
		DisplayCommitments();
		this.updateUI();
	}

	@Override
	public void contentsChanged(ListDataEvent e) {
		rebuildHours();
		DisplayCommitments();
		this.updateUI();
	}
}
