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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.ListIterator;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.EventListModel;

public class MonthCalendarView extends JPanel implements ICalendarView, ListDataListener {
	public static final String[] weekNames = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
	public static final String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	private ArrayList<JLabel> dayLabel = new ArrayList<JLabel>();
	private ArrayList<DatePanel> panelList = new ArrayList<DatePanel>();
	private ArrayList<JLabel> nameLabelList = new ArrayList<JLabel>();
	private ArrayList<DatePanel> nameList = new ArrayList<DatePanel>();
	
	private HashMap<Integer, DatePanel> paneltracker;

	private Calendar mycal;
	private int currentMonth;
	private int currentYear;

	
//	JPanel contentPane = new JPanel();
	
	/**
	 * Create the frame.
	 */
	public MonthCalendarView() {
		paneltracker = new HashMap<Integer, DatePanel>();
		
		Date today = new Date();
		
		currentYear = today.getYear() + 1900;
		currentMonth = today.getMonth();

		mycal = new GregorianCalendar(currentYear, currentMonth, 1);
		this.setBackground(Color.white);
		
		this.setLayout(new MigLayout("fill", 
				"[14%][14%][14%][14%][14%][14%][14%]", 
				"[14%][14%][14%][14%][14%][14%][14%]"));
		
		
		addDayLabels();
		addDays(mycal);
		this.setVisible(true);
		
		SetDate();
		EventListModel.getEventListModel().addListDataListener(this);
	}
	
	public void makeGridPanels(Calendar c)
	{
		ArrayList<Integer> listDays = makeListOfDays(c);
		int temp =7;
//		nameList = new ArrayList<JPanel>();
		for(int i = 1; i < 7; i++)
		{	
			for(int j = 0; j <7; j++)
			{
				JLabel dayLabel = addDay(listDays.get(temp-7),Color.black);
				StringBuilder sb = new StringBuilder();
				sb.append("cell ");
				sb.append(j);
				sb.append(" ");
				sb.append(i);
				sb.append(",grow, push");
				
				nameList.add(new DatePanel());
				nameList.get(temp).setBackground(Color.white);
				nameList.get(temp).add(dayLabel);
				
				this.add(nameList.get(temp), sb.toString());
				
				temp++;
			}
		}
	}
	
	
	

	

	public void addDayLabels()
	{
		nameLabelList = new ArrayList<JLabel>();
		nameList = new ArrayList<DatePanel>();
		for(int i = 0; i < weekNames.length; i++)
		{	
			StringBuilder sb = new StringBuilder();
			sb.append("cell ");
			sb.append(i);
			sb.append(" ");
			sb.append(0);
			sb.append(",grow, push");
			
			nameList.add(new DatePanel());
			nameList.get(i).setBackground(new Color(138,173,209));
			nameLabelList.add(new JLabel(weekNames[i]));
			nameList.get(i).add(nameLabelList.get(i));
			
			
			this.add(nameList.get(i), sb.toString());
		}
	}
	
	
	public JLabel addDay( int day, Color text)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("");
		sb.append(day);
		JLabel dayLabel = new JLabel(sb.toString());
		dayLabel.setForeground(text);
		return dayLabel;
	}

	public ArrayList<Integer> makeListOfDays(Calendar c)
	{
		ArrayList<Integer> listOfDays = new ArrayList<Integer>();
		int numberOfDaysInPreviousMonth;
		int nextMonth = c.get(Calendar.MONTH);
		int nextYear = c.get(Calendar.YEAR);
		int daysInMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH); 
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		c.set(Calendar.DAY_OF_MONTH, daysInMonth);

		if (c.get(Calendar.MONTH) == 0){
			nextMonth = 11;
			nextYear = c.get(Calendar.YEAR) -1;
		}
		else{
			nextMonth--;
		}
		Calendar previous = new GregorianCalendar(nextYear, nextMonth, 1);
		numberOfDaysInPreviousMonth = previous.getActualMaximum(Calendar.DAY_OF_MONTH);
		int previousDays = 	dayOfWeek-1;
		int day = 1;
		int nextMonthDays = 1;
		for(int i =0; i < 42; i++)
		{
			if(previousDays > 0)
			{
				previousDays--;
				listOfDays.add(numberOfDaysInPreviousMonth-previousDays);
			}
			else if(daysInMonth > 0 && previousDays == 0)
			{
				listOfDays.add(day);
				daysInMonth--;
				day++;
			}
			else
			{
				listOfDays.add(nextMonthDays);
				nextMonthDays++;
			}
			
			System.out.println("day:"+day);
		}
		
		return listOfDays;
	}

	public boolean isCurrentMonth(Calendar c)
	{
		Calendar currentMonth = Calendar.getInstance();
		int todayMonth = currentMonth.get(Calendar.MONTH);
		int todayYear = currentMonth.get(Calendar.YEAR);
		int givenMonth = c.get(Calendar.MONTH);
		int givenYear = c.get(Calendar.YEAR);

		if(todayMonth == givenMonth && todayYear == givenYear)
		{
			System.out.println("TRUE");
			return true;
		}
		else
		{
			System.out.println("False");
			return false;
		}
	}
	public void addDay(int x, int y, int day, int gridIndex, Color text, Color background)
	{
		JLabel dayLabel = addDay(day,text);
		StringBuilder sb = new StringBuilder();
		sb.append("cell ");
		sb.append(x);
		sb.append(" ");
		sb.append(y);
		sb.append(",grow, push");
		
		nameList.add(new DatePanel());
		nameList.get(gridIndex+7).setBackground(background);
		nameList.get(gridIndex+7).setBorder(new MatteBorder(0, 0, 1, 0, Color.gray));
		nameList.get(gridIndex+7).add(dayLabel);
		
		this.add(nameList.get(gridIndex+7), sb.toString());
	}

	public void addDays(Calendar cal)
	{
		int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		ArrayList<Integer> listOfDays = makeListOfDays(cal);
		Calendar currentMonth = Calendar.getInstance();
		int todayDate = currentMonth.get(Calendar.DAY_OF_MONTH);

		boolean isMonth = isCurrentMonth(cal);

		System.out.println("days In Month: " + daysInMonth);

		dayOfWeek = dayOfWeek-1;
		boolean firstDaySet = false;
		boolean lastDaySet = false;
		for(int i = 0; i < 42; i++){
			int x = i%7;
			int y = (i/7)+1;
			if(listOfDays.get(i) != 1 && !firstDaySet)
			{
				addDay(x,y,listOfDays.get(i),i, Color.gray, Color.white);
			}
			else if(listOfDays.get(i) == 1 && !firstDaySet){
				addDay(x,y,listOfDays.get(i),i, Color.black, Color.white);
				firstDaySet = true;
			}
			else if(listOfDays.get(i) == daysInMonth && !lastDaySet){
				addDay(x,y,listOfDays.get(i),i, Color.black, Color.white);
				lastDaySet = true;
			}
			else if(listOfDays.get(i) == todayDate && firstDaySet && isMonth && !lastDaySet){
				addDay(x,y,listOfDays.get(i),i, Color.black,new Color(236,252,144));
			}
			else if(listOfDays.get(i) == todayDate && firstDaySet && !isMonth && !lastDaySet){
				addDay(x,y,listOfDays.get(i),i, Color.black, Color.white);
			}
			else if(listOfDays.get(i) > 0 && listOfDays.get(i) != todayDate && firstDaySet && !lastDaySet){
				addDay(x,y,listOfDays.get(i),i, Color.black, Color.white);
			}
			else{
				addDay(x,y,listOfDays.get(i),i, Color.gray, Color.white);
			}
		}
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
		
		dayLabel = new ArrayList<JLabel>();
		panelList = new ArrayList<DatePanel>();
		nameLabelList = new ArrayList<JLabel>();
		nameList = new ArrayList<DatePanel>();
		
		this.removeAll();

		addDayLabels();
		addDays(next);
		
		SetDate();
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
		Calendar next = new GregorianCalendar(currentYear, currentMonth, 1);
		mycal = next;


		int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH); // 28
		int dayOfWeek = mycal.get(Calendar.DAY_OF_WEEK);
		mycal.set(Calendar.DAY_OF_MONTH, daysInMonth);
		int numWeeksMonth = mycal.get(Calendar.WEEK_OF_MONTH);

		dayLabel = new ArrayList<JLabel>();
		panelList = new ArrayList<DatePanel>();
		nameLabelList = new ArrayList<JLabel>();
		nameList = new ArrayList<DatePanel>();
		
		this.removeAll();

		addDayLabels();
		addDays(next);

		SetDate();
		updatePanels();
	}


	@Override
	public void today() {
		
		Date today = new Date();
		if(currentMonth != today.getMonth()) {
			currentMonth = today.getMonth();
			currentYear = today.getYear() + 1900;

			Calendar next = new GregorianCalendar(currentYear, currentMonth, 1);
			mycal = next;

			int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH); // 28
			int dayOfWeek = mycal.get(Calendar.DAY_OF_WEEK);
			mycal.set(Calendar.DAY_OF_MONTH, daysInMonth);
			int numWeeksMonth = mycal.get(Calendar.WEEK_OF_MONTH);

			dayLabel = new ArrayList<JLabel>();
			panelList = new ArrayList<DatePanel>();
			nameLabelList = new ArrayList<JLabel>();
			nameList = new ArrayList<DatePanel>();
			
			this.removeAll();

			addDayLabels();
			addDays(next);
			SetDate();
			updatePanels();
		}
	}
	
	public void SetDate(){
		int setMonth;
		int day =0;
		int setYear = currentYear - 1900;
		
		ArrayList<Integer> listOfDays = makeListOfDays(mycal);
		
		System.out.println(listOfDays.size());
		
		if (currentMonth == 0){
			setMonth = 12;
		}
		else{
			setMonth = currentMonth-1;
		}
		
		for (int x = 0; x < nameList.size()-7; x ++){
			day = listOfDays.get(x);
			if (day == 1){
				if (setMonth == 12){
					setMonth = 1;
					setYear++;
				}
				else{
					setMonth++;
				}
			}
			nameList.get(x+7).setDate(new Date(setYear, setMonth, day));
			System.out.println("value: " + (setYear * setMonth + day));
			paneltracker.put(setYear * setMonth + day, nameList.get(x+7));
		}
	}
	
	public void removeEvents(){
		for (int x = 0;x < nameList.size(); x++){
			nameList.get(x).removeEventPanel();
		}
	}

	public void updatePanels(){
		int key;
		
		removeEvents();
		ListIterator<Event> event = EventListModel.getEventListModel().getList().listIterator();
		
		while(event.hasNext()){
			Event eve = event.next();
			Date evedate = eve.getStartDate();
			key = evedate.getYear() * evedate.getMonth() + evedate.getDate();
			
			System.out.println(key);
			if (paneltracker.containsKey(key)){
				
				paneltracker.get(key).addEventPanel(eve);
			}
		}
	}
	
	@Override
	public String getTitle() {
		return monthNames[currentMonth] + ", " + currentYear;
	}

	@Override
	public void intervalAdded(ListDataEvent e) {
		updatePanels();
	}

	@Override
	public void intervalRemoved(ListDataEvent e) {
		updatePanels();
	}

	@Override
	public void contentsChanged(ListDataEvent e) {
		updatePanels();
	}

	
}
