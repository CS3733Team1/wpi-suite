/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.month;

import java.awt.Color;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.FilteredCommitmentsListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.FilteredEventsListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.CalendarUtils;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.ICalendarView;

public class MonthCalendarView extends JPanel implements ICalendarView, AncestorListener, ComponentListener, ListDataListener {
	// A List holding all of the Day Panels as to be able to modify the contents [No need to recreate a new day on view changes]
	private List<DayPanel> days;

	// A List holding all of the Day Panels containing Events or commitments as to be able to easily which ones need to be
	// changed on a resize
	private List<DayPanel> daysWithEvComs;

	// A Calendar to keep track of the month the user is currently viewing
	private Calendar currentMonth;

	// Handles on the events and commitments models
	private FilteredEventsListModel filteredEventsModel;
	private FilteredCommitmentsListModel filteredCommitmentsModel;

	private boolean isDisplayAbrrWeekDayNames;
	private List<JLabel> weekDays;

	public MonthCalendarView() {
		this.filteredEventsModel = FilteredEventsListModel.getFilteredEventsListModel();
		this.filteredCommitmentsModel = FilteredCommitmentsListModel.getFilteredCommitmentsListModel();

		// Add ListDataListeners so the YearCalendarView can be notified of data changes.
		this.filteredEventsModel.addListDataListener(this);
		this.filteredCommitmentsModel.addListDataListener(this);

		this.setLayout(new MigLayout("fill, gap 0 0, wrap 7",
				"[14.2857%][14.2857%][14.2857%][14.2857%][14.2857%][14.2857%][14.2857%]",
				"[9%][13%][13%][13%][13%][13%][13%]"));
		this.setBackground(Color.WHITE);
		this.setBorder(new MatteBorder(1, 1, 1, 1, Color.GRAY));

		this.currentMonth = Calendar.getInstance();

		this.daysWithEvComs = new ArrayList<DayPanel>();
		this.days = new ArrayList<DayPanel>();

		for(int i = 0; i < 7*6; i++) days.add(new DayPanel(i));

		this.isDisplayAbrrWeekDayNames = false;
		weekDays = new ArrayList<JLabel>();

		for(String weekDay: CalendarUtils.weekNames) {
			JPanel titlePanel = new JPanel(new MigLayout("fill, insets 0", "[center]"));
			titlePanel.setBackground(Color.WHITE);
			JLabel weekDayLabel = new JLabel(weekDay, JLabel.CENTER);
			weekDays.add(weekDayLabel);
			titlePanel.add(weekDayLabel, "grow");
			this.add(titlePanel, "grow");
		}

		// Add all the day labels to the month
		for(DayPanel dayPanel: days) this.add(dayPanel, "grow, hmin 27");

		// Fill the newly created month with its respective dates as well as events or commitments
		this.updateDates();

		this.addAncestorListener(this);
		this.addComponentListener(this);
	}

	/*
	 * Changes the week day names to abbreviations if the panel is too small
	 */
	private void updateLayout() {
		if(this.getWidth() <= 550 && !isDisplayAbrrWeekDayNames) {
			isDisplayAbrrWeekDayNames = true;
			for(int i = 0; i < weekDays.size(); i++)
				weekDays.get(i).setText(CalendarUtils.weekNamesAbbr[i]);
		} else if(this.getWidth() > 550 && isDisplayAbrrWeekDayNames) {
			isDisplayAbrrWeekDayNames = false;
			for(int i = 0; i < weekDays.size(); i++)
				weekDays.get(i).setText(CalendarUtils.weekNames[i]);
		}
	}

	/*
	 * Fills the Month with its respective dates as well as events or commitments
	 */
	private void updateDates() {
		Calendar month = (Calendar)currentMonth.clone();
		month.set(Calendar.DATE, 1);

		int savedMonth = month.get(Calendar.MONTH);

		if(month.get(Calendar.DAY_OF_WEEK) == 1) month.add(Calendar.DATE, -7);
		else month.add(Calendar.DATE, -currentMonth.get(Calendar.DAY_OF_WEEK)+1);

		for(DayPanel dayPanel: days) {
			dayPanel.setDate(month,  month.get(Calendar.MONTH) == savedMonth);
			dayPanel.setIsToday(false);
			

			month.add(Calendar.DATE, 1);
		}

		// Set the current day to be highlighted yellow
		Calendar today = Calendar.getInstance();
		if(today.get(Calendar.YEAR) == currentMonth.get(Calendar.YEAR) &&
				today.get(Calendar.MONTH) == currentMonth.get(Calendar.MONTH)) {
			days.get(getIndexofDay(today)).setIsToday(true);
		}

		updateEvComs();
	}

	private void updateEvComs() {
		for(DayPanel dayPanel: daysWithEvComs) dayPanel.clearEvComs();
		
		daysWithEvComs = new ArrayList<DayPanel>();

		for(Event event: filteredEventsModel.getList()) {
			Date start = event.getStartDate();
			Calendar startCal = Calendar.getInstance();
			startCal.set(start.getYear()+1900, start.getMonth(), start.getDate());
			if(startCal.get(Calendar.YEAR) == currentMonth.get(Calendar.YEAR) &&
					startCal.get(Calendar.MONTH) == currentMonth.get(Calendar.MONTH)) {
				int index = getIndexofDay(startCal);
				days.get(index).addEvent(event);
				daysWithEvComs.add(days.get(index));
			}
		}

		for(Commitment commitment: filteredCommitmentsModel.getList()) {
			Date due = commitment.getDueDate();
			Calendar dueCal = Calendar.getInstance();
			dueCal.set(due.getYear()+1900, due.getMonth(), due.getDate());
			if(dueCal.get(Calendar.YEAR) == currentMonth.get(Calendar.YEAR) &&
					dueCal.get(Calendar.MONTH) == currentMonth.get(Calendar.MONTH)) {
				int index = getIndexofDay(dueCal);
				days.get(index).addCommitment(commitment);
				daysWithEvComs.add(days.get(index));
			}
		}

		for(DayPanel dayPanel: daysWithEvComs) {
			dayPanel.updateEveComs();
		}
		
		this.repaint();
		this.updateUI();
	}

	private int getIndexofDay(Calendar cal) {
		int index = cal.get(Calendar.DATE) - 1;
		cal.set(Calendar.DATE, 1);
		if(cal.get(Calendar.DAY_OF_WEEK) == 1) index += 7;
		else index += cal.get(Calendar.DAY_OF_WEEK) - 1;
		return index;
	}

	@Override
	public String getTitle() {
		return CalendarUtils.monthNames[currentMonth.get(Calendar.MONTH)] + ", " + currentMonth.get(Calendar.YEAR);
	}

	@Override
	public void next() {
		currentMonth.add(Calendar.MONTH, 1);
		this.updateDates();
	}

	@Override
	public void previous() {
		currentMonth.add(Calendar.MONTH, -1);
		this.updateDates();
	}

	@Override
	public void today() {
		if(this.currentMonth.get(Calendar.MONTH) != Calendar.getInstance().get(Calendar.MONTH)) {
			this.currentMonth = Calendar.getInstance();
			this.updateDates();
		}
	}

	@Override
	public void contentsChanged(ListDataEvent e) {
		this.updateEvComs();
	}

	@Override
	public void intervalAdded(ListDataEvent e) {
		this.updateEvComs();
	}

	@Override
	public void intervalRemoved(ListDataEvent e) {
		this.updateEvComs();
	}

	public void ancestorAdded(AncestorEvent e) {
		this.updateLayout();
	}

	@Override
	public void componentResized(ComponentEvent e) {
		this.updateLayout();
		this.updateEvComs();
	}

	// Unused
	@Override
	public void ancestorMoved(AncestorEvent e) {}
	@Override
	public void ancestorRemoved(AncestorEvent e) {}
	@Override
	public void componentHidden(ComponentEvent e) {}
	@Override
	public void componentMoved(ComponentEvent e) {}
	@Override
	public void componentShown(ComponentEvent e) {}
}