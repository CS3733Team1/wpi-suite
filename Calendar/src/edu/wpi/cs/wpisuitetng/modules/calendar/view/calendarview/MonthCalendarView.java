/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.ListIterator;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.EventListModel;

public class MonthCalendarView extends JPanel implements ICalendarView, ListDataListener, AncestorListener {
	public static final String[] weekNames = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
	public static final String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	private ArrayList<JLabel> dayLabel = new ArrayList<JLabel>();
	private ArrayList<DatePanel> panelList = new ArrayList<DatePanel>();
	private ArrayList<JLabel> nameLabelList = new ArrayList<JLabel>();
	private ArrayList<DatePanel> nameList = new ArrayList<DatePanel>();
	
	private int[][] listOfDaysCalendar = new int[7][6];
	private Date[][] listOfDates = new Date[7][6];
	private DatePanel2[][] dateArray = new DatePanel2[7][6];
	
	private HashMap<Date, DatePanel2> paneltracker;

	private Calendar mycal;
	private int currentMonth;
	private int currentYear;
	
	private boolean isDay = false;
	
	private Date d1 = new Date(113,10,13);
	private Date d2 = new Date(113,10,15);
	
	
	/**
	 * Create the frame.
	 */
	public MonthCalendarView() {
		
		paneltracker = new HashMap<Date, DatePanel2>();
		
		Date today = new Date();
		
		currentYear = today.getYear() + 1900;
		currentMonth = today.getMonth();
		
		mycal = new GregorianCalendar(currentYear, currentMonth, 1);
		this.setBackground(Color.white);
		
		this.setLayout(new MigLayout("fill, insets 0", 
				"[14%]0[14%]0[14%]0[14%]0[14%]0[14%]0[14%]0", 
				"[14%]0[14%]0[14%]0[14%]0[14%]0[14%]0[14%]0"));
		
		addDayLabels();
		addDaysToCalendar(mycal);

		EventListModel.getEventListModel().addListDataListener(this);
		
		this.addAncestorListener(this);
		
	}
	

	/**
	 * Adds Labels for the Days of the Week
	 */

	public void addDayLabels()
	{
		for(int i = 0; i < 7; i++)
		{
			JPanel panel = new JPanel();
			panel.setBackground(new Color(138,173,209));
			panel.add(new JLabel(weekNames[i]));
			this.add(panel, "cell "+i+" 0, grow, hmin 70, hmax 40");
		}
	}
	
	
	/*
	 * Creates the list of days to be displayed in the month view
	 * returns ArrayList<Integer>
	 */
	public void makeListOfDays(Calendar c)
	{	
		int numberOfDaysInPreviousMonth;
		int nextMonth = c.get(Calendar.MONTH);
		int nextYear = c.get(Calendar.YEAR);
		int daysInMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH); 
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		
		if (c.get(Calendar.MONTH) == 0){
			nextMonth = 11;
			nextYear = c.get(Calendar.YEAR) -1;
		}
		else{
			nextMonth--;
		}
		Calendar previous = new GregorianCalendar(nextYear, nextMonth, 1);
		numberOfDaysInPreviousMonth = previous.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		addPreviousMonth(numberOfDaysInPreviousMonth,dayOfWeek,c);
		addCurrentMonth(dayOfWeek,daysInMonth,c);
		addNextMonth(daysInMonth+(dayOfWeek-1),c);
			
	}

	
	
	private void addNextMonth(int days, Calendar c) {
		int day = 1;
		int nextMonth =c.get(Calendar.MONTH);
		int nextYear = c.get(Calendar.YEAR);
		
		if (c.get(Calendar.MONTH) == 11){
			nextMonth = 0;
			nextYear = c.get(Calendar.YEAR) +1;
		}
		else{
			nextMonth++;
			nextYear = c.get(Calendar.YEAR);
		}
		
		for(int i = days; i < 42; i++)
		{
			int x = i%7;
			int y = (i/7);
			
			listOfDates[x][y]= new Date(nextYear-1900,nextMonth,day);
//			System.out.println(listOfDates[x][y]);
			listOfDaysCalendar[x][y] = day;
			day++;	
		}
		
	}


	private void addPreviousMonth(int daysInMonth, int dayOfWeek, Calendar c) {
		int day = 0;
		int nextMonth =c.get(Calendar.MONTH);
		int nextYear = c.get(Calendar.YEAR);
		if (c.get(Calendar.MONTH) == 0){
			nextMonth = 11;
			nextYear = (c.get(Calendar.YEAR) - 1);
		}
		else{
			nextMonth--;
			nextYear = c.get(Calendar.YEAR);
		}
		
		for(int i = dayOfWeek-2; i>= 0; i--)
		{
			listOfDaysCalendar[i][0] = daysInMonth-day;
			listOfDates[i][0]= new Date(nextYear-1900,nextMonth,daysInMonth-day);
			day++;
		}
		
	}
	private void addCurrentMonth(int dayOfWeek, int daysInMonth, Calendar c)
	{
		int day = 1;
		int nextYear = c.get(Calendar.YEAR);
		int nextMonth = c.get(Calendar.MONTH);
		for(int i = dayOfWeek-1; i < daysInMonth+dayOfWeek; i++)
		{
			int x = i%7;
			int y = (i/7);	
			listOfDates[x][y]= new Date(nextYear-1900,nextMonth,day);
			System.out.println(listOfDates[x][y]);
			listOfDaysCalendar[x][y] = day;
			day++;
		}
	}
	

	/*
	 * takes in a calendar and determines if it is the current month
	 * returns boolean
	 * 
	 */
	public boolean isCurrentMonth(Calendar c)
	{
		Calendar currentMonth = Calendar.getInstance();
		int todayMonth = currentMonth.get(Calendar.MONTH);
		int todayYear = currentMonth.get(Calendar.YEAR);
		int givenMonth = c.get(Calendar.MONTH);
		int givenYear = c.get(Calendar.YEAR);
		
		if(todayMonth == givenMonth && todayYear == givenYear)
			return true;
		else 
			return false;
	}
	
	public DatePanel2 constructDay (Calendar cal,int x, int y, int day, Color text, Color background)
	{
		DatePanel2 dayPanel = new DatePanel2(constructDate(cal,day));
		dayPanel.setColors(text,background);
		dayPanel.setDay(day);
		this.add(dayPanel, "cell "+x+" "+(y+1)+", grow, hmin 27");
		return dayPanel;
	}
	
	private Date constructDate(Calendar cal, int day)
	{
		int setYear = cal.get(Calendar.YEAR)-1900;
		int setMonth = cal.get(Calendar.MONTH);
		return new Date(setYear, setMonth, day);
		
	}
	
	public Calendar nextMonth(Calendar cal)
	{
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		if (month == 11){
			month = 0;
			year++;
		}
		else{
			month++;
		}
		Calendar next = new GregorianCalendar(year, month, 1);
		
		return next;
	}
	
	public Calendar previousMonth(Calendar cal)
	{
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		if (month == 0){
			month = 11;
			year--;
		}
		else{
			month--;
		}
		Calendar next = new GregorianCalendar(year, month, 1);
		return next;
	}
	
	
	
	public void addDaysToCalendar(Calendar cal)
	{
		makeListOfDays(cal);
		int index = 0;
		for(int i = 0; i < 6; i++){
			for(int j = 0; j< 7; j++){
				if(isToday(cal,listOfDates[j][i])){
					dateArray[j][i] = new DatePanel2(constructDate(cal,listOfDaysCalendar[j][i]));
					dateArray[j][i] = constructDay(cal,j, i, listOfDaysCalendar[j][i],Color.black,new Color(236,252,144));
					setDate(cal,listOfDaysCalendar[j][i], dateArray[j][i]);
				}
				else if(isPreviousMonth(cal, index) ||isNextMonth(cal, index)){
					if(isPreviousMonth(cal, index)){
						dateArray[j][i] = new DatePanel2(constructDate(previousMonth(cal),listOfDaysCalendar[j][i]));
						dateArray[j][i] = constructDay(previousMonth(cal),j, i, listOfDaysCalendar[j][i],Color.gray, Color.white);
						setDatePrevious(cal,listOfDaysCalendar[j][i],dateArray[j][i]);
					}
					else{
						dateArray[j][i] = new DatePanel2(constructDate(nextMonth(cal),listOfDaysCalendar[j][i]));
						dateArray[j][i] = constructDay(nextMonth(cal),j, i, listOfDaysCalendar[j][i],Color.gray, Color.white);
						setDateNext(cal,listOfDaysCalendar[j][i], dateArray[j][i]);
					}
				}
				else{
					dateArray[j][i] = new DatePanel2(constructDate(cal,listOfDaysCalendar[j][i]));
					dateArray[j][i]=constructDay(cal,j, i, listOfDaysCalendar[j][i],Color.black, Color.white);
					setDate(cal,listOfDaysCalendar[j][i], dateArray[j][i]);
				}
				index++;
			}
		}
		isDay = true;

	}

	private void setDate(Calendar cal, int day, DatePanel2 dayPanel) {
		int setYear = cal.get(Calendar.YEAR)-1900;
		int setMonth = cal.get(Calendar.MONTH);
		Date today = new Date(setYear, setMonth, day);
		dayPanel.setDate(today);
	}


	private void setDateNext(Calendar cal, int day, DatePanel2 dayPanel) {
		int setYear = cal.get(Calendar.YEAR)-1900;
		int setMonth = cal.get(Calendar.MONTH);
		if (setMonth == 11){
			setMonth = 0;
			setYear++;
		}
		else{
			setMonth++;
		}
		Date today = new Date(setYear, setMonth, day);
		dayPanel.setDate(today);
	}


	private void setDatePrevious(Calendar cal, int day, DatePanel2 dayPanel) {
		int setYear = cal.get(Calendar.YEAR)-1900;
		int setMonth = cal.get(Calendar.MONTH);
		if (setMonth == 11){
			setMonth = 0;
			setYear--;
		}
		else{
			setMonth--;
		}
		Date today = new Date(setYear, setMonth, day);
		dayPanel.setDate(today);
	}


	private boolean isToday(Calendar cal, Date day) {
		Calendar conversion = Calendar.getInstance();
		conversion.setTime(day);
		Calendar currentMonth = Calendar.getInstance();
		int todayDay = currentMonth.get(Calendar.DATE);
		int todayMonth = currentMonth.get(Calendar.MONTH);
		int todayYear = currentMonth.get(Calendar.YEAR);
		int givenMonth = conversion.get(Calendar.MONTH);
		int givenYear = conversion.get(Calendar.YEAR);
		int givenDay = conversion.get(Calendar.DATE);

		if(todayMonth == givenMonth && todayYear == givenYear && givenDay == todayDay){
			return true;
		}else{
			return false;
		}
	}

	private boolean isPreviousMonth(Calendar cal, int index) {
		if(index < cal.get(Calendar.DAY_OF_WEEK)-1)
			return true;
		else
			return false;
	}

	private boolean isNextMonth(Calendar cal, int index) {
		int totalDays = ((cal.get(Calendar.DAY_OF_WEEK))-1)+cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		if(index > totalDays-1) 
			return true;
		else
			return false;
	}
	
	@Override
	public void next() {
		Calendar current = Calendar.getInstance();
		int month = current.get(Calendar.MONTH);
		if (currentMonth == 11){
			currentMonth = 0;
			currentYear++;
		}
		else{
			currentMonth++;
		}
		Calendar next = new GregorianCalendar(currentYear, currentMonth, 1);
		mycal = next;
		
		this.removeAll();
		addDayLabels();
		addDaysToCalendar(next);
		updatePanels();
	}

	@Override
	public void previous() {
		
		Calendar current = Calendar.getInstance();
		int month = current.get(Calendar.MONTH);
		if (currentMonth == 0){
			currentMonth = 11;
			currentYear--;
		}
		else{
			currentMonth--;
		}
		this.removeAll();
		
		Calendar next = new GregorianCalendar(currentYear, currentMonth, 1);
		mycal = next;
		
		addDayLabels();
		addDaysToCalendar(next);
		updatePanels();
	}


	@Override
	public void today() {
		Date today = new Date();
		if(!(currentMonth == today.getMonth() && currentYear == today.getYear()+1900)) {
			currentMonth = today.getMonth();
			currentYear = today.getYear() + 1900;

			Calendar next = new GregorianCalendar(currentYear, currentMonth, 1);
			mycal = next;
			
			this.removeAll();

			addDayLabels();
			addDaysToCalendar(next);
			updatePanels();
		}
	}
	
	
	public void removeEvents(){
		for (int x = 0;x < nameList.size(); x++){
			nameList.get(x).removeEventPanel();
		}
	}

	public void updatePanels(){
		Date key;
		
		removeEvents();
		ListIterator<Event> event = EventListModel.getEventListModel().getList().listIterator();
		
		while(event.hasNext()){
			Event eve = event.next();
			Date evedate = eve.getStartDate();
			key = new Date(evedate.getYear(),evedate.getMonth(), evedate.getDate());
			
			if (paneltracker.containsKey(key)){
				
				paneltracker.get(key).addEvent(eve);
				
			}
		}
	}
	
	
	
	
	
	
	public boolean isArrayEmpty()
	{
		boolean isEmpty = false;
		for(int i = 0; i < 6; i++)
		{
			for(int j = 0; j < 7; j++)
			{
				if(dateArray[j][i] == null)
				{
					isEmpty = true;
				}
		    }
		}
		if(isEmpty == true){
			return true;
		}else{
			return false;
		}
	}
	

	@Override
	public void repaint()
	{
		super.repaint();
	}
	
	@Override 
	public void paint(Graphics g)
	{
		super.paint(g);
//		addMultiDayEvent(d1, d2,g);
	}

	@Override
	public String getTitle() {
		return monthNames[currentMonth] + ", " + currentYear;
	}

	@Override
	public void intervalAdded(ListDataEvent e) {
		Calendar next = new GregorianCalendar(currentYear, currentMonth, 1);
		mycal = next;
		
		this.removeAll();

		addDayLabels();
		addDaysToCalendar(next);
		updatePanels();
	}

	@Override
	public void intervalRemoved(ListDataEvent e) {
		Calendar next = new GregorianCalendar(currentYear, currentMonth, 1);
		mycal = next;
		
		this.removeAll();

		addDayLabels();
		addDaysToCalendar(next);
		updatePanels();
	}

	@Override
	public void contentsChanged(ListDataEvent e) {}


	@Override
	public void ancestorAdded(AncestorEvent arg0) {
		Calendar next = new GregorianCalendar(currentYear, currentMonth, 1);
		mycal = next;
		
		this.removeAll();

		addDayLabels();
		addDaysToCalendar(next);
		updatePanels();
	}


	@Override
	public void ancestorMoved(AncestorEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void ancestorRemoved(AncestorEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
