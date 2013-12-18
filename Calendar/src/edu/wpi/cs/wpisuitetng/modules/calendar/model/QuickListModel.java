package edu.wpi.cs.wpisuitetng.modules.calendar.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.commitment.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.event.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.filter.FilteredCommitmentsListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.filter.FilteredEventsListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarTabPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.ICalendarView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day.DayCalendar;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.month.MonthCalendarView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.week.WeekCalendar;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.year.YearCalendarView;

public class QuickListModel extends AbstractListModel<ISchedulable> implements ListDataListener {

	private static QuickListModel quickListModel;

	/** The list of evComs on the calendar */
	private List<ISchedulable> quickListItems;

	// Stores whether or not Events or Commitments or both show up in the quicklist
	// 0 Nothing, 1 Events, 2 Commitments, 3 Both
	private int state;

	private QuickListModel() {
		quickListItems = new ArrayList<ISchedulable>();
		state = 3;
		FilteredEventsListModel.getFilteredEventsListModel().addListDataListener(this);
		FilteredCommitmentsListModel.getFilteredCommitmentsListModel().addListDataListener(this);
	}

	static public synchronized QuickListModel getQuickListModel() {
		if (quickListModel == null)
			quickListModel = new QuickListModel();
		return quickListModel;
	}
	
	public void setState(int state) {
		this.state = state;
		this.updateList();
	}

	/**
	 * Calculates which commitments in a list are within the user's current view.
	 * @return inView An ArrayList of commitments in the user's current view.
	 */
	private List<Commitment> commitmentsInView() {
		ArrayList<Commitment> inView = new ArrayList<Commitment>();

		ICalendarView currentView = CalendarTabPanel.getCalendarView();
		if(currentView instanceof DayCalendar) {
			for(Commitment c: FilteredCommitmentsListModel.getFilteredCommitmentsListModel().getList()) {
				if(c.getDueDate().getDate() == ((DayCalendar)currentView).getDate().getDate() && 
						c.getDueDate().getMonth() == ((DayCalendar)currentView).getDate().getMonth() &&
						c.getDueDate().getYear() == ((DayCalendar)currentView).getDate().getYear()) {
					inView.add(c);
				}
			}
			return inView;
		} else if (currentView instanceof WeekCalendar) {
			for(Commitment c: FilteredCommitmentsListModel.getFilteredCommitmentsListModel().getList()) {
				if(c.getDueDate().getDate() - c.getDueDate().getDay() == ((WeekCalendar)currentView).getWeekStart().getDate()) {
					inView.add(c);
				}
			}
			return inView;
		} else if (currentView instanceof MonthCalendarView) {
			return ((MonthCalendarView)currentView).getCommitments();
		} else if (currentView instanceof YearCalendarView) {
			int year = ((YearCalendarView) currentView).getYear();
			for(Commitment c: FilteredCommitmentsListModel.getFilteredCommitmentsListModel().getList()) {
				if(c.getDueDate().getYear() == year) {
					inView.add(c);
				}
			}
			return inView;
		}
		else return inView;
	}

	/**
	 * Calculates which events in a list are within the user's current view.
	 * @return inView An ArrayList of events in the user's current view.
	 */
	private List<Event> eventsInView() {
		ArrayList<Event> inView = new ArrayList<Event>();

		ICalendarView currentView = CalendarTabPanel.getCalendarView();
		if(currentView instanceof DayCalendar) {
			for(Event e: FilteredEventsListModel.getFilteredEventsListModel().getList()) {
				if(e.getStartDate().getDate() == ((DayCalendar)currentView).getDate().getDate()) {
					inView.add(e);
				}
			}
			return inView;
		} else if (currentView instanceof WeekCalendar) {
			for(Event e: FilteredEventsListModel.getFilteredEventsListModel().getList()) {
				if(e.getStartDate().getDate() - e.getStartDate().getDay() == ((WeekCalendar)currentView).getWeekStart().getDate()) {
					inView.add(e);
				}
			}
			return inView;
		} else if (currentView instanceof MonthCalendarView) {
			return ((MonthCalendarView)currentView).getEvents();
		} else if (currentView instanceof YearCalendarView) {
			int year = ((YearCalendarView) currentView).getYear();
			for(Event e: FilteredEventsListModel.getFilteredEventsListModel().getList()) {
				if(e.getStartDate().getYear() == year) {
					inView.add(e);
				}
			}
			return inView;
		}
		else return inView;
	}
	
	@Override
	public ISchedulable getElementAt(int index) {
		return quickListItems.get(index);
	}

	@Override
	public int getSize() {
		return quickListItems.size();
	}

	public void updateList() {
		quickListItems.clear();
		switch(state) {
		case 1:
			quickListItems.addAll(eventsInView());
			break;
		case 2:
			quickListItems.addAll(commitmentsInView());
			break;
		case 3:
			quickListItems.addAll(eventsInView());
			quickListItems.addAll(commitmentsInView());
		}
		//Sort the List!
		Collections.sort(quickListItems);
		this.fireIntervalAdded(this, 0, quickListItems.size() - 1);
	}
	
	@Override
	public void contentsChanged(ListDataEvent e) {
		updateList();
	}

	@Override
	public void intervalAdded(ListDataEvent e) {
		updateList();
	}

	@Override
	public void intervalRemoved(ListDataEvent e) {
		updateList();
	}
}
