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
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.ListIterator;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.EventModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.MainModel;

public class MonthCalendarView extends JPanel implements ICalendarView {
	public static final String[] weekNames = {"Saturday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
	public static final String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	private ArrayList<JLabel> dayLabel = new ArrayList<JLabel>();
	private ArrayList<DatePanel> panelList = new ArrayList<DatePanel>();
	private ArrayList<JLabel> nameLabelList = new ArrayList<JLabel>();
	private ArrayList<JPanel> nameList = new ArrayList<JPanel>();
	private ArrayList<GridBagConstraints> nameGridBagList = new ArrayList<GridBagConstraints>();
	private ArrayList<GridBagConstraints> gridBagList = new ArrayList<GridBagConstraints>();
	private JPanel panel = new JPanel();

	private Calendar mycal;
	private int currentMonth;
	private int currentYear;

	/**
	 * Create the frame.
	 */
	public MonthCalendarView() {
		int gridWidth = 110;
		int gridHeight = 100;
		this.add(panel);
		System.out.println("Size: " + panel.getSize());
		GridBagLayout gbl_panel = new GridBagLayout();
		
		gbl_panel.columnWidths = new int[]{gridWidth, gridWidth, gridWidth, gridWidth, gridWidth, gridWidth, gridWidth};
		gbl_panel.rowHeights = new int[]{30, gridHeight, gridHeight, gridHeight, gridHeight, gridHeight, gridHeight};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		panel.setLayout(gbl_panel);

		Date today = new Date();
		currentMonth = today.getMonth();
		currentYear = today.getYear() + 1900;
		//TODO: Update with current month and year
		mycal = new GregorianCalendar(currentYear, currentMonth, 1);
		addDayLabels();
		addDays(mycal);
		panel.setVisible(true);
		
		SetDate();

	}

	public void addDayLabels()
	{
		nameLabelList = new ArrayList<JLabel>();
		nameList = new ArrayList<JPanel>();
		nameGridBagList = new ArrayList<GridBagConstraints>();
		for(int i = 0; i < weekNames.length; i++)
		{
			nameList.add(new JPanel());
			nameList.get(i).setBackground(new Color(138, 173, 209));
			nameGridBagList.add(new GridBagConstraints());

			nameGridBagList.get(i).insets = new Insets(0, 0, 0, 0);
			nameGridBagList.get(i).fill = GridBagConstraints.BOTH;
			nameGridBagList.get(i).weightx = 0.0;
			nameGridBagList.get(i).weighty = 0.0;
			nameGridBagList.get(i).gridx = i;
			nameGridBagList.get(i).gridy = 0;
			panel.add(nameList.get(i),nameGridBagList.get(i));

			nameLabelList.add(new JLabel(weekNames[i]));
			nameList.get(i).add(nameLabelList.get(i));
		}
	}

	public void addDay(int x, int y, int day, int gridIndex, Color c)
	{
		if(day > 0)
		{
			panelList.get(gridIndex).setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.black));
			gridBagList.add(new GridBagConstraints());
			gridBagList.get(gridIndex).insets = new Insets(0, 0, 0, 0);
			gridBagList.get(gridIndex).fill = GridBagConstraints.BOTH;
			gridBagList.get(gridIndex).gridx = x;
			gridBagList.get(gridIndex).gridy = y;
			gridBagList.get(gridIndex).weightx = 0.0;
			gridBagList.get(gridIndex).weighty = 0.0;
			panel.add(panelList.get(gridIndex), gridBagList.get(gridIndex));
			StringBuilder sb = new StringBuilder();
			sb = sb.append("");
			sb = sb.append(day);
			dayLabel.add(new JLabel(sb.toString()));
			dayLabel.get(gridIndex).setForeground(c);
			panelList.get(gridIndex).add(dayLabel.get(gridIndex));
		}
		else
		{
			panelList.add(new DatePanel());
			panelList.get(gridIndex).setBackground(Color.white);
			panelList.get(gridIndex).setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.black));
			gridBagList.add(new GridBagConstraints());
			gridBagList.get(gridIndex).insets = new Insets(0, 0, 0, 0);
			gridBagList.get(gridIndex).fill = GridBagConstraints.BOTH;
			gridBagList.get(gridIndex).gridx = x;
			gridBagList.get(gridIndex).gridy = y;
			gridBagList.get(gridIndex).weightx = 0.0;
			gridBagList.get(gridIndex).weighty = 0.0;
			panel.add(panelList.get(gridIndex), gridBagList.get(gridIndex));
		}
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
				panelList.add(new DatePanel());
				panelList.get(i).setBackground(Color.white);
				addDay(x,y,listOfDays.get(i),i, Color.gray);
			}
			else if(listOfDays.get(i) == 1 && !firstDaySet){
				panelList.add(new DatePanel());
				panelList.get(i).setBackground(Color.white);
				addDay(x,y,listOfDays.get(i),i, Color.black);
				firstDaySet = true;
			}
			else if(listOfDays.get(i) == daysInMonth && !lastDaySet){
				panelList.add(new DatePanel());
				panelList.get(i).setBackground(Color.white);
				addDay(x,y,listOfDays.get(i),i, Color.black);
				lastDaySet = true;
			}
			else if(listOfDays.get(i) == todayDate && firstDaySet && isMonth && !lastDaySet){
				panelList.add(new DatePanel());
				panelList.get(i).setBackground(new Color(236,252,144));
				addDay(x,y,listOfDays.get(i),i, Color.black);
			}
			else if(listOfDays.get(i) == todayDate && firstDaySet && !isMonth && !lastDaySet){
				panelList.add(new DatePanel());
				panelList.get(i).setBackground(Color.white);
				addDay(x,y,listOfDays.get(i),i, Color.black);
			}
			else if(listOfDays.get(i) > 0 && listOfDays.get(i) != todayDate && firstDaySet && !lastDaySet){
				panelList.add(new DatePanel());
				panelList.get(i).setBackground(Color.white);
				addDay(x,y,listOfDays.get(i),i, Color.black);
			}
			else{
				panelList.add(new DatePanel());
				panelList.get(i).setBackground(Color.white);
				addDay(x,y,listOfDays.get(i),i, Color.gray);
			}
		}
	}
	public void addEvent(Calendar cal, Event e)
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
				panelList.add(new DatePanel());
				panelList.get(i).setBackground(Color.white);
				addDay(x,y,listOfDays.get(i),i, Color.gray);
			}
			else if(listOfDays.get(i) == 1 && !firstDaySet){
				panelList.add(new DatePanel());
				panelList.get(i).setBackground(Color.white);
				addDay(x,y,listOfDays.get(i),i, Color.black);
				firstDaySet = true;
			}
			else if(listOfDays.get(i) == daysInMonth && !lastDaySet){
				panelList.add(new DatePanel());
				panelList.get(i).setBackground(Color.white);
				addDay(x,y,listOfDays.get(i),i, Color.black);
				lastDaySet = true;
			}
			else if(listOfDays.get(i) == todayDate && firstDaySet && isMonth && !lastDaySet){
				panelList.add(new DatePanel());
				panelList.get(i).setBackground(new Color(236,252,144));
				addDay(x,y,listOfDays.get(i),i, Color.black);
			}
			else if(listOfDays.get(i) == todayDate && firstDaySet && !isMonth && !lastDaySet){
				panelList.add(new DatePanel());
				panelList.get(i).setBackground(Color.white);
				addDay(x,y,listOfDays.get(i),i, Color.black);
			}
			else if(listOfDays.get(i) > 0 && listOfDays.get(i) != todayDate && firstDaySet && !lastDaySet){
				panelList.add(new DatePanel());
				panelList.get(i).setBackground(Color.white);
				addDay(x,y,listOfDays.get(i),i, Color.black);
			}
			else{
				panelList.add(new DatePanel());
				panelList.get(i).setBackground(Color.white);
				addDay(x,y,listOfDays.get(i),i, Color.gray);
			}
		}
	}
	
	public JPanel getDayPanel(Date d)
	{
		int month = d.getMonth();
		int day = d.getDay();
		int year = d.getYear();
		
		
		
		
		
		return null;
	}
	
	


	public void updateDays(int daysInMonth, int dayOfWeekFirstWeek, int numWeeksMonth)
	{
		dayLabel.clear();
		dayOfWeekFirstWeek = dayOfWeekFirstWeek-1;
		boolean firstDaySet = false;
		int day = 1;
		int gridIndex = 0;
		for(int i = 1; i < 6; i++)
		{
			for(int j = 0; j < 7; j++)
			{

				if(j == dayOfWeekFirstWeek && !firstDaySet)
				{
					addDay(j, i,day,gridIndex, Color.black);
				}
				else if(day < daysInMonth && firstDaySet)
				{
					day++;
					addDay(j, i,day,gridIndex, Color.black);	
				}
				else
				{

				}
				gridIndex++;
			}
		}
	}

	@Override
	public void next() {
		removeListeners();
		
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

		this.remove(panel);

		dayLabel = new ArrayList<JLabel>();
		panelList = new ArrayList<DatePanel>();
		gridBagList = new ArrayList<GridBagConstraints>();
		panel = new JPanel();

		int gridSize = 100;

		add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{gridSize, gridSize, gridSize, gridSize, gridSize, gridSize, gridSize};
		gbl_panel.rowHeights = new int[]{30, gridSize, gridSize, gridSize, gridSize, gridSize, gridSize};
		gbl_panel.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
		gbl_panel.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
		panel.setLayout(gbl_panel);

		addDayLabels();
		addDays(next);
		
		SetDate();
		updatePanels();
	}

	@Override
	public void previous() {
		removeListeners();
		
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

		this.remove(panel);

		dayLabel = new ArrayList<JLabel>();
		panelList = new ArrayList<DatePanel>();
		gridBagList = new ArrayList<GridBagConstraints>();
		panel = new JPanel();

		int gridSize = 100;

		add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{gridSize, gridSize, gridSize, gridSize, gridSize, gridSize, gridSize};
		gbl_panel.rowHeights = new int[]{30, gridSize, gridSize, gridSize, gridSize, gridSize, gridSize};
		gbl_panel.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
		gbl_panel.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
		panel.setLayout(gbl_panel);

		addDayLabels();
		addDays(next);

		SetDate();
		updatePanels();
	}

	public void addEvent(Event e)
	{
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

			this.remove(panel);

			dayLabel = new ArrayList<JLabel>();
			panelList = new ArrayList<DatePanel>();
			gridBagList = new ArrayList<GridBagConstraints>();
			panel = new JPanel();

			int gridSize = 100;

			add(panel, BorderLayout.NORTH);
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[]{gridSize, gridSize, gridSize, gridSize, gridSize, gridSize, gridSize};
			gbl_panel.rowHeights = new int[]{30, gridSize, gridSize, gridSize, gridSize, gridSize, gridSize};
			gbl_panel.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
			gbl_panel.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
			panel.setLayout(gbl_panel);


			addDayLabels();
			addDays(next);
		}
	}

	@Override
	public void today() {
		removeListeners();
		
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

			this.remove(panel);

			dayLabel = new ArrayList<JLabel>();
			panelList = new ArrayList<DatePanel>();
			gridBagList = new ArrayList<GridBagConstraints>();
			panel = new JPanel();

			int gridSize = 100;

			add(panel, BorderLayout.NORTH);
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[]{gridSize, gridSize, gridSize, gridSize, gridSize, gridSize, gridSize};
			gbl_panel.rowHeights = new int[]{30, gridSize, gridSize, gridSize, gridSize, gridSize, gridSize};
			gbl_panel.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
			gbl_panel.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
			panel.setLayout(gbl_panel);

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
		
		if (currentMonth == 0){
			setMonth = 12;
		}
		else{
			setMonth = currentMonth-1;
		}
		
		for (int x = 0; x < panelList.size(); x ++){
			day = Integer.valueOf(dayLabel.get(x).getText());
			if (day == 1){
				if (setMonth == 12){
					setMonth = 1;
					setYear++;
				}
				else{
					setMonth++;
				}
			}
			panelList.get(x).setDate(new Date(setYear, setMonth, day));
			EventModel.getEventModel().addListDataListener(panelList.get(x));
		}
	}
	
	public void removeListeners(){
		for (int x = 0;x < panelList.size(); x++){
			EventModel.getEventModel().removeListDataListener(panelList.get(x));
		}
	}

	public void updatePanels(){
		for (int x = 0;x < panelList.size(); x++){
			panelList.get(x).updatePanel();
		}
	}
	
	@Override
	public String getTitle() {
		return monthNames[currentMonth] + ", " + currentYear;
	}

	
}
