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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.jdesktop.swingx.JXMonthView;

/**
 * Panel to hold the date picking calendar and the buttons to scroll
 * which month it is displaying
 */
public class DatePickerPanel extends JPanel {
	private JXMonthView calendarMonthView;
	
	private JButton nextMonth;
	private JButton prevMonth;
	private JButton today;

	/**
	 * Panel constructor
	 * @param singleSelection selects whether panel will be set to single or
	 * multiple interval selection, true selects single interval
	 */
	public DatePickerPanel(boolean singleSelection) {
		buildLayout(singleSelection);
	}
	
	private void buildLayout(boolean singleSelection){
		
		this.setLayout(new BorderLayout());
		JPanel buttonPanel = new JPanel();
		
		setupButtons();
		
		buttonPanel.add(prevMonth);
		buttonPanel.add(today);
		buttonPanel.add(nextMonth);
		calendarMonthView = new DatePicker(singleSelection);
		this.setAlignmentX(CENTER_ALIGNMENT);
		this.add(buttonPanel,BorderLayout.NORTH);
		this.add(calendarMonthView,BorderLayout.CENTER);
	}
	
	private void setupButtons(){
		nextMonth = new JButton(">");
		nextMonth.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		today = new JButton ("Today");
		today.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		prevMonth = new JButton("<");
		prevMonth.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		nextMonth.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e){
				nextMonth();
			}
		});
		today.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e){
				today();
			}
		});
		prevMonth.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e){
				prevMonth();
			}
		});
	}
	
	private void nextMonth(){
		Calendar cal = calendarMonthView.getCalendar();
		cal.add(Calendar.MONTH, 1);
		calendarMonthView.setFirstDisplayedDay(cal.getTime());
	}
	
	private void today(){
		Calendar cal = Calendar.getInstance();
		calendarMonthView.setFirstDisplayedDay(cal.getTime());
	}
	
	private void prevMonth(){
		Calendar cal = calendarMonthView.getCalendar();
		cal.add(Calendar.MONTH, -1);
		calendarMonthView.setFirstDisplayedDay(cal.getTime());
	}
	
	/**
	 * @return all the dates the user has selected
	 */
	@SuppressWarnings("deprecation")
	public ArrayList<Date> getAllDates(){
		ArrayList<Date> dates = new ArrayList<Date>();
		Date firstSel,lastSel,testDate;
		firstSel=calendarMonthView.getFirstSelectionDate();
		lastSel=calendarMonthView.getLastSelectionDate();
		testDate=firstSel;
		while(testDate.compareTo(lastSel)<=0){
			if(calendarMonthView.isSelected(testDate)){
				dates.add(new Date(testDate.getYear(),testDate.getMonth(),testDate.getDate()));
			}
			testDate.setDate(testDate.getDate()+1);
		}
		return dates;
	}
	
	/**
	 * @return the first date the user has selected
	 */
	public Date getStartDate(){
		return calendarMonthView.getFirstSelectionDate();
	}
	
	/**
	 * @return the last date the user has selected
	 */
	public Date getEndDate(){
		return calendarMonthView.getLastSelectionDate();
	}
}
