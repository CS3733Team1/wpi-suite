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
import java.awt.Font;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
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
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.ICalendarView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.CalendarUtils;

public class MonthCalendarView extends JPanel implements ICalendarView, AncestorListener, ComponentListener, ListDataListener {
	// A List holding all of the Day Panels as to be able to modify the contents [No need to recreate a new day on view changes]
	private ArrayList<DayPanel> days;

	// A List holding all of the Day Panels containing Events or commitments as to be able to easily which ones need to be
	// changed on a resize
	private List<DayPanel> daysWithEvComs;

	// A List holding the MultiDayEvents currently displayed in the view
	private List<Event> multiDayEvents;

	// A List holding the Events currently displayed in the view
	private List<Event> events;

	// A List holding the Commitments currently displayed in the view
	private List<Commitment> commitments;

	private List<MultiDayEventPanel> multiDayEventPanels;
	private List<EventPanel> eventPanels;
	private List<CommitmentPanel> commitmentPanels;

	// A Calendar to keep track of the month the user is currently viewing
	private Calendar currentMonth;

	// A Calendar to keep track of the first visible day in the view
	private Calendar firstDayInView;

	// A Calendar to keep track of the last visible day in the view
	private Calendar lastDayInView;

	private HashMap<String, Integer> indexInDaysOfCal;

	// Handles on the events and commitments models
	private FilteredEventsListModel filteredEventsModel;
	private FilteredCommitmentsListModel filteredCommitmentsModel;

	private boolean isDisplayAbrrWeekDayNames;
	private List<JLabel> weekDays;

	private EvComMouseListener mouseListener;

	public MonthCalendarView() {
		this.filteredEventsModel = FilteredEventsListModel.getFilteredEventsListModel();
		this.filteredCommitmentsModel = FilteredCommitmentsListModel.getFilteredCommitmentsListModel();
		
		mouseListener = new EvComMouseListener(this);

		multiDayEvents = new ArrayList<Event>();
		events = new ArrayList<Event>();
		commitments = new ArrayList<Commitment>();
		indexInDaysOfCal = new HashMap<String, Integer>();

		multiDayEventPanels = new ArrayList<MultiDayEventPanel>();
		eventPanels = new ArrayList<EventPanel>();
		commitmentPanels = new ArrayList<CommitmentPanel>();

		this.setLayout(new MigLayout("fill, gap 0 0, wrap 7",
				"[14.2857%][14.2857%][14.2857%][14.2857%][14.2857%][14.2857%][14.2857%]",
				"[9%][13%][13%][13%][13%][13%][13%]"));
		this.setBackground(Color.WHITE);
		this.setBorder(new MatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));

		this.currentMonth = createCalendar();

		this.daysWithEvComs = new ArrayList<DayPanel>();
		this.days = new ArrayList<DayPanel>();

		for(int i = 0; i < 7*6; i++) days.add(new DayPanel(i));

		this.isDisplayAbrrWeekDayNames = false;
		weekDays = new ArrayList<JLabel>();

		for(String weekDay: CalendarUtils.weekNamesAbbr) {
			JPanel titlePanel = new JPanel(new MigLayout("fill, insets 0", "[center]"));
			titlePanel.setBackground(Color.WHITE);
			JLabel weekDayLabel = new JLabel(weekDay, JLabel.RIGHT);
			weekDayLabel.setForeground(CalendarUtils.titleNameColor);
			weekDayLabel.setFont(new Font(weekDayLabel.getFont().getName(), Font.BOLD, 14));
			weekDayLabel.setBorder(new MatteBorder(0, 0, 5, 0, Color.GRAY));
			weekDays.add(weekDayLabel);
			titlePanel.add(weekDayLabel, "grow");
			this.add(titlePanel, "grow");
		}

		weekDays.get(0).setForeground(CalendarUtils.timeColor);
		weekDays.get(CalendarUtils.weekNamesAbbr.length - 1).setForeground(CalendarUtils.timeColor);

		// Add all the day labels to the month
		for(DayPanel dayPanel: days) {
			this.add(dayPanel, "grow, hmin 27");
		}

		// Fill the newly created month with its respective dates
		this.updateDates();

		// Add ListDataListeners so the YearCalendarView can be notified of data changes.
		this.filteredEventsModel.addListDataListener(this);
		this.filteredCommitmentsModel.addListDataListener(this);

		this.addAncestorListener(this);
		this.addComponentListener(this);
	}

	/*
	 * Changes the week day names to abbreviations if the panel is too small
	 */
	private void updateWeekDayNames() {
		if(this.getWidth() <= 900 && !isDisplayAbrrWeekDayNames) {
			isDisplayAbrrWeekDayNames = true;
			for(int i = 0; i < weekDays.size(); i++)
				weekDays.get(i).setText(CalendarUtils.weekNamesAbbr[i]);
		} else if(this.getWidth() > 900 && isDisplayAbrrWeekDayNames) {
			isDisplayAbrrWeekDayNames = false;
			for(int i = 0; i < weekDays.size(); i++)
				weekDays.get(i).setText(CalendarUtils.weekNames[i]);
		}
	}

	/*
	 * Fills the Month with its respective dates
	 */
	private void updateDates() {
		indexInDaysOfCal.clear();

		this.currentMonth.set(Calendar.DATE, 1);
		Calendar month = cloneCalendar(this.currentMonth);
		int savedMonth = month.get(Calendar.MONTH);

		if(month.get(Calendar.DAY_OF_WEEK) == 1) month.add(Calendar.DATE, -7);
		else month.add(Calendar.DATE, -currentMonth.get(Calendar.DAY_OF_WEEK)+1);

		firstDayInView = cloneCalendar(month);

		for(int i = 0; i < days.size(); i++) {
			DayPanel dayPanel = days.get(i);
			indexInDaysOfCal.put(month.getTime().toString(), i);
			dayPanel.setDate(cloneCalendar(month),  month.get(Calendar.MONTH) == savedMonth);
			dayPanel.setIsToday(false);
			dayPanel.updateColors();

			month.add(Calendar.DATE, 1);
		}

		month.add(Calendar.DATE, -1);
		lastDayInView = cloneCalendar(month);

		// Set the current day/month to be highlighted
		Calendar today = createCalendar();

		if(isDateInView(today)) {
			if(today.get(Calendar.YEAR) == currentMonth.get(Calendar.YEAR) &&
					today.get(Calendar.MONTH) == currentMonth.get(Calendar.MONTH)) {
				for(JLabel weekDayLabel: weekDays) weekDayLabel.setBorder(new MatteBorder(0, 0, 5, 0, CalendarUtils.selectionColor));
				weekDays.get(today.get(Calendar.DAY_OF_WEEK)-1).setBorder(new MatteBorder(0, 0, 5, 0, CalendarUtils.thatBlue));
			}

			int index = getIndexofDay(today);
			days.get(index).setIsToday(true);
			days.get(index).updateColors();
		} else for(JLabel weekDayLabel: weekDays) weekDayLabel.setBorder(new MatteBorder(0, 0, 5, 0, Color.LIGHT_GRAY));
	}

	// Loads events and commitments
	private void loadEvComs() {
		multiDayEvents.clear();
		events.clear();
		commitments.clear();

		for(Event event: filteredEventsModel.getList()) {
			Date start = event.getStartDate();
			Calendar startCal = createCalendarFromDate(start);

			Date end = event.getEndDate();
			Calendar endCal = createCalendarFromDate(end);

			// If this event is not a multiday event
			if(!event.isMultiDay()) {
				if(isDateInView(startCal)) events.add(event);
			} else { // Multiday event
				Calendar iterCal = cloneCalendar(startCal);

				if(!isDateInView(iterCal))
					if (iterCal.compareTo(firstDayInView) < 0) iterCal = cloneCalendar(firstDayInView);

				if(iterCal.compareTo(endCal) <= 0 && iterCal.compareTo(lastDayInView) <= 0) multiDayEvents.add(event);
			}
		}

		for(Commitment commitment: filteredCommitmentsModel.getList()) {
			Date due = commitment.getDueDate();
			Calendar dueCal = createCalendarFromDate(due);
			if(isDateInView(dueCal)) commitments.add(commitment);
		}

		createEvComPanels();
	}

	// Creates the EvComPanels
	public void createEvComPanels() {
		for(int i = 0; i < daysWithEvComs.size(); i++) {
			daysWithEvComs.get(i).clearEvComs();
		}

		multiDayEventPanels.clear();
		eventPanels.clear();
		commitmentPanels.clear();

		for(int i = 0; i < events.size(); i++) {
			Event event = events.get(i);
			Date start = event.getStartDate();
			Calendar startCal = createCalendarFromDate(start);
			int index = getIndexofDay(startCal);
			days.get(index).incrementNumEvComs();
			if(!daysWithEvComs.contains(days.get(index))) daysWithEvComs.add(days.get(index));
		}

		for(int i = 0; i < multiDayEvents.size(); i++) {
			Event event = multiDayEvents.get(i);
			Date start = event.getStartDate();
			Calendar startCal = createCalendarFromDate(start);

			Date end = event.getEndDate();
			Calendar endCal = createCalendarFromDate(end);
			Calendar iterCal = cloneCalendar(startCal);

			if(!isDateInView(iterCal))
				if (iterCal.compareTo(firstDayInView) < 0) iterCal = cloneCalendar(firstDayInView);

			while(iterCal.compareTo(endCal) <= 0 && iterCal.compareTo(lastDayInView) <= 0) {
				int index = getIndexofDay(iterCal);
				days.get(index).setHasMultiDay(true);
				days.get(index).incrementNumEvComs();
				if(!daysWithEvComs.contains(days.get(index))) daysWithEvComs.add(days.get(index));
				iterCal.add(Calendar.DATE, 1);
			}
		}

		for(int i = 0; i < commitments.size(); i++) {
			Commitment commitment = commitments.get(i);
			Date due = commitment.getDueDate();
			Calendar dueCal = createCalendarFromDate(due);
			int index = getIndexofDay(dueCal);
			days.get(index).incrementNumEvComs();
			if(!daysWithEvComs.contains(days.get(index))) daysWithEvComs.add(days.get(index));
		}

		for(DayPanel dayPanel: daysWithEvComs) dayPanel.initEvComPanels();

		List<List<MultiDayEventPanel>> multiDays = new ArrayList<List<MultiDayEventPanel>>();

		for(int i = 0; i < multiDayEvents.size(); i++) {
			Event multiDayEvent = multiDayEvents.get(i);
			List<MultiDayEventPanel> multiDay = new ArrayList<MultiDayEventPanel>();

			Date start = multiDayEvent.getStartDate();
			Calendar startCal = createCalendarFromDate(start);

			Date end = multiDayEvent.getEndDate();
			Calendar endCal = createCalendarFromDate(end);

			Calendar iterCal = cloneCalendar(startCal);

			if(!isDateInView(iterCal))
				if (iterCal.compareTo(firstDayInView) < 0) iterCal = cloneCalendar(firstDayInView);

			boolean isFirstPanel = true;
			int indexOfMultiday = 0;
			while(iterCal.compareTo(endCal) <= 0 && iterCal.compareTo(lastDayInView) <= 0) {
				int textType = 0;
				int index = getIndexofDay(iterCal);

				//if(isAllDay) { // if is all day - not implemented
				//	if(isFirstPanel) textType = 2;
				//	else if((index+1)%7 == 1) textType = 2;
				//} else {

				// If this MultiDayEventPanel is on the first day show the start time
				if(startCal.compareTo(iterCal) == 0) textType = 1;

				// Else if its not the start day but it's the first panel visible, only show the name
				else if(isFirstPanel) {
					if(endCal.compareTo(iterCal) == 0) textType = 4; //Show name and end date
					else textType = 2;
				}

				// Else If this EventPanel is on the last day show the end time
				else if(endCal.compareTo(iterCal) == 0) textType = 3;
				else if((index+1)%7 == 1) textType = 2;

				//}

				MultiDayEventPanel multiDayEventPanel = new MultiDayEventPanel(multiDayEvent, textType);
				multiDay.add(multiDayEventPanel);

				if(isFirstPanel) {
					multiDayEventPanels.add(multiDayEventPanel);
					indexOfMultiday = days.get(index).addEvComPanel(multiDayEventPanel);
					isFirstPanel = false;
				} else days.get(index).addEvComPanelAt(multiDayEventPanel, indexOfMultiday);

				iterCal.add(Calendar.DATE, 1);
			}

			multiDays.add(multiDay);
		}

		mouseListener.setMultiDayLists(multiDays);

		for(int i = 0; i < events.size(); i++) {
			Event event = events.get(i);
			Date start = event.getStartDate();
			Calendar startCal = createCalendarFromDate(start);

			int index = getIndexofDay(startCal);
			EventPanel eventPanel = new EventPanel(event);
			eventPanel.setBackground(days.get(index).getBackground());
			eventPanels.add(eventPanel);
			days.get(index).addEvComPanel(eventPanel);
		}

		for(int i = 0; i < commitments.size(); i++) {
			Commitment commitment = commitments.get(i);
			Date due = commitment.getDueDate();
			Calendar dueCal = createCalendarFromDate(due);

			int index = getIndexofDay(dueCal);
			CommitmentPanel commitmentPanel = new CommitmentPanel(commitment);
			commitmentPanel.setBackground(days.get(index).getBackground());
			commitmentPanels.add(commitmentPanel);
			days.get(index).addEvComPanel(commitmentPanel);
		}

		for(DayPanel dayPanel: daysWithEvComs) dayPanel.addFillerPanels();
	}

	// Called on a resize and on init
	// Calculates which evComms to display
	public void updateEvComs() {
		int total = 0;
		total += days.get(0).getContainerPanelHeight();
		total += days.get(7).getContainerPanelHeight();
		total += days.get(14).getContainerPanelHeight();
		total += days.get(21).getContainerPanelHeight();
		total += days.get(28).getContainerPanelHeight();
		total += days.get(35).getContainerPanelHeight();

		int totalEvComsDisplayable = (int)(total / (new JLabel("I have Height!")).getPreferredSize().getHeight());
		int numDisplayableEvComs = totalEvComsDisplayable / 6;
		int width = this.getWidth()/7;

		for(int i = 0; i < daysWithEvComs.size(); i++) daysWithEvComs.get(i).setNumDisplayableEvComs(numDisplayableEvComs);

		List<Event> hiddenMultiDayEvents = new ArrayList<Event>();
		List<DayPanel> daysWithHiddenMultiDayEvents = new ArrayList<DayPanel>();

		for(int i = 0; i < daysWithEvComs.size(); i++) {
			if(daysWithEvComs.get(i).hasMultiDay() && daysWithEvComs.get(i).hasHiddinMultiDayEvent()) {
				if(!daysWithHiddenMultiDayEvents.contains(daysWithEvComs.get(i))) daysWithHiddenMultiDayEvents.add(daysWithEvComs.get(i));
				Event multidayEvent = daysWithEvComs.get(i).getHiddenMultiDayEvent().getEvent();
				if(!hiddenMultiDayEvents.contains(multidayEvent)) hiddenMultiDayEvents.add(multidayEvent);
			}
		}

		for(Event multidayEvent: hiddenMultiDayEvents) {
			Date start = multidayEvent.getStartDate();
			Calendar startCal = createCalendarFromDate(start);

			Date end = multidayEvent.getEndDate();
			Calendar endCal = createCalendarFromDate(end);

			Calendar iterCal = cloneCalendar(startCal);

			if(!isDateInView(iterCal))
				if (iterCal.compareTo(firstDayInView) < 0) iterCal = cloneCalendar(firstDayInView);

			while(iterCal.compareTo(endCal) <= 0 && iterCal.compareTo(lastDayInView) <= 0) {
				int index = getIndexofDay(iterCal);
				if(!daysWithHiddenMultiDayEvents.contains(days.get(index))) days.get(index).decrementNumDisplayableEvComs();
				iterCal.add(Calendar.DATE, 1);
			}
		}

		for(int i = 0; i < daysWithEvComs.size(); i++) daysWithEvComs.get(i).updateEveComs(width);

		this.invalidate();
		this.updateUI();
	}

	public ArrayList<DayPanel> getDayPanels() {return days;}

	private int getIndexofDay(Calendar indexCal) {
		return indexInDaysOfCal.get(indexCal.getTime().toString());
	}

	private boolean isDateInView(Calendar startDate) {
		return startDate.compareTo(firstDayInView) >= 0 && startDate.compareTo(lastDayInView) <= 0;
	}

	private Calendar cloneCalendar(Calendar toBeCloned) {
		return (Calendar)toBeCloned.clone();
	}

	private Calendar createCalendarFromDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(date.getYear()+1900, date.getMonth(), date.getDate(), 0, 0, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar;
	}

	private Calendar createCalendar() {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar;
	}

	public int getMonth() {
		return currentMonth.get(Calendar.MONTH);
	}

	public List<Event> getSelectedEvents() {
		List<Event> selectedEvents = new ArrayList<Event>();

		for(EventPanel eventPanel: eventPanels) {
			if(eventPanel.isSelected())
				if(!selectedEvents.contains(eventPanel.getEvent())) selectedEvents.add(eventPanel.getEvent());
		}

		for(MultiDayEventPanel multiDayEventPanel: multiDayEventPanels) {
			if(multiDayEventPanel.isSelected())
				if(!selectedEvents.contains(multiDayEventPanel.getEvent())) selectedEvents.add(multiDayEventPanel.getEvent());
		}

		return selectedEvents;
	}

	public List<Commitment> getSelectedCommitments() {
		List<Commitment> selectedCommitments = new ArrayList<Commitment>();

		for(CommitmentPanel commitmentPanel: commitmentPanels) {
			if(commitmentPanel.isSelected())
				if(!selectedCommitments.contains(commitmentPanel.getCommitment())) selectedCommitments.add(commitmentPanel.getCommitment());
		}

		return selectedCommitments;
	}

	@Override
	public String getTitle() {
		return CalendarUtils.monthNames[currentMonth.get(Calendar.MONTH)] + " " + currentMonth.get(Calendar.YEAR);
	}

	@Override
	public void next() {
		currentMonth.add(Calendar.MONTH, 1);
		this.updateDates();
		this.loadEvComs();
		this.updateEvComs();
	}

	@Override
	public void previous() {
		currentMonth.add(Calendar.MONTH, -1);
		this.updateDates();
		this.loadEvComs();
		this.updateEvComs();
	}

	@Override
	public void today() {
		if(this.currentMonth.get(Calendar.MONTH) != createCalendar().get(Calendar.MONTH)) {
			this.currentMonth = createCalendar();
			this.updateDates();
			this.loadEvComs();
			this.updateEvComs();
		}
	}

	@Override
	public void contentsChanged(ListDataEvent e) {
		this.loadEvComs();
		this.updateEvComs();
	}

	@Override
	public void intervalAdded(ListDataEvent e) {
		this.loadEvComs();
		this.updateEvComs();
	}

	@Override
	public void intervalRemoved(ListDataEvent e) {
		this.loadEvComs();
		this.updateEvComs();
	}

	public void ancestorAdded(AncestorEvent e) {
		this.updateWeekDayNames();
	}

	@Override
	public void componentResized(ComponentEvent e) {
		this.updateWeekDayNames();
		this.updateEvComs();
	}

	@Override
	public void viewDate(Calendar date) {
		if(this.currentMonth.get(Calendar.MONTH) != date.get(Calendar.MONTH)) {
			this.currentMonth = date;
			this.updateDates();
			this.loadEvComs();
			this.updateEvComs();
		}
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
