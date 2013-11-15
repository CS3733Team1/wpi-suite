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
import java.util.Calendar;
import java.util.Date;
import java.util.ListIterator;
import java.util.SortedSet;

import javax.swing.JPanel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import org.jdesktop.swingx.JXMonthView;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.EventModel;

public class YearCalendarView extends JPanel implements ICalendarView, ListDataListener{

	public static final Color START_END_DAY = new Color(47, 150, 9);

	private JXMonthView yearView;

	/**
	 * Constructor for the iteration calendar.
	 * @param parent IterationPanel
	 * @param vm ViewMode
	 * @param displayIteration Iteration
	 */
	public YearCalendarView() {
		yearView = new JXMonthView();
		yearView.setPreferredColumnCount(4);
		yearView.setPreferredRowCount(3);
		yearView.setFlaggedDayForeground(START_END_DAY);
		yearView.setAlignmentX(CENTER_ALIGNMENT);
		
		
		this.today();
		
		this.add(yearView);
		
		EventModel.getEventModel().addListDataListener(this);
		UpdateYearView();
		
		yearView.setFlaggedDayForeground(Color.RED);
	}

	@Override
	public String getTitle() {
		return "Year of " + yearView.getCalendar().get(Calendar.YEAR);
	}

	@Override
	public void next() {
		Calendar cal = yearView.getCalendar();
		cal.add(Calendar.YEAR, +1);
		yearView.setFirstDisplayedDay(cal.getTime());
	}

	@Override
	public void previous() {
		Calendar cal = yearView.getCalendar();
		cal.add(Calendar.YEAR, -1);
		yearView.setFirstDisplayedDay(cal.getTime());
	}

	@Override
	public void today() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		yearView.setFirstDisplayedDay(cal.getTime());
	}

	public void HighlightDate(Date m){
		System.out.println("Eve no Jikan");
		
		SortedSet<Date> dateset = yearView.getFlaggedDates();
		dateset.add(m);
		
		
		
		yearView.setFlaggedDates(dateset.toArray(new Date[dateset.size()]));
	}
	
	public void UpdateYearView(){
		ListIterator<Event> event = EventModel.getEventModel().getList().listIterator();
		while(event.hasNext()){
			Event eve = event.next();
			Date evedate = eve.getStartDate();
			if (evedate.getYear() == yearView.getCalendar().getTime().getYear()){
				HighlightDate(evedate);
			}
		}
	}
	
	@Override
	public void intervalAdded(ListDataEvent e) {
		// TODO Auto-generated method stub
		UpdateYearView();
	}

	@Override
	public void intervalRemoved(ListDataEvent e) {
		// TODO Auto-generated method stub
		UpdateYearView();
	}

	@Override
	public void contentsChanged(ListDataEvent e) {
		// TODO Auto-generated method stub
		UpdateYearView();
	}
}
