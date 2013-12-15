package edu.wpi.cs.wpisuitetng.modules.calendar.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarTabPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.ICalendarView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day.DayCalendarPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.month.MonthCalendarView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.week.WeekCalendarPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.year.YearCalendarView;

public class QuickListModel extends AbstractListModel<DeletableAbstractModel> implements ListDataListener, FilterChangedListener {

	private static QuickListModel filteredEventsListModel;

	/** The list of filtered events on the calendar */
	private List<DeletableAbstractModel> filteredEvents;

	private QuickListModel() {
		filteredEvents=new ArrayList<DeletableAbstractModel>();
		//filteredEvents = Collections.synchronizedList(new ArrayList<DeletableAbstractModel>());
		EventListModel.getEventListModel().addListDataListener(this);
		CommitmentListModel.getCommitmentListModel().addListDataListener(this);
		FilterListModel.getFilterListModel().addListDataListener(this);
		filterEvents();
	}

	static public synchronized QuickListModel getFilteredEventsListModel() {
		if (filteredEventsListModel == null)
			filteredEventsListModel = new QuickListModel();
		//filteredEventsListModel.filterEvents();
		return filteredEventsListModel;
	}

	private void filterEvents() {
		filteredEvents.clear();
		List<Commitment> commitmentList = CommitmentListModel.getCommitmentListModel().getList();
		List<Event> eventList = EventListModel.getEventListModel().getList();
		
		filteredEvents.addAll(FilterListModel.getFilterListModel().applyEventFilter(eventList));
		filteredEvents.addAll(FilterListModel.getFilterListModel().applyCommitmentFilter(commitmentList));
		
		this.fireIntervalAdded(this, 0, Math.max(filteredEvents.size() - 1, 0));
	}
	
	/**
	 * Calculates which commitments in a list are within the user's current view.
	 * @param list A List of commitments.
	 * @return inView An ArrayList of commitments in the user's current view.
	 */
	private ArrayList<Commitment> commitmentsInView(List<Commitment> list){
		ArrayList<Commitment> inView = new ArrayList<Commitment>();

		ICalendarView currentView = CalendarTabPanel.getCalendarView();
		Iterator<Commitment> iterator = list.iterator();
		Commitment temp = new Commitment();
		if (currentView instanceof DayCalendarPanel)
		{
			while(iterator.hasNext())
			{
				temp = iterator.next();
				if(temp.getDueDate() == ((DayCalendarPanel) currentView).getDate())
					inView.add(temp);
			}
			return inView;
		}
		else if (currentView instanceof WeekCalendarPanel)
		{
			while(iterator.hasNext()) {
				temp = iterator.next();
				Date tempDate = temp.getDueDate();
				// set tempDate to the Sunday of the week that temp is part of
				tempDate.setDate(tempDate.getDate() - tempDate.getDay());
				if(temp.getDueDate() == ((WeekCalendarPanel) currentView).getWeekStart()) {
					inView.add(temp);
				}
			}
			return inView;
		}
		else if (currentView instanceof MonthCalendarView)
		{
			int month = ((MonthCalendarView) currentView).getMonth();
			int year = ((MonthCalendarView) currentView).getYear();
			while(iterator.hasNext()) {
				temp = iterator.next();
				if(temp.getDueDate().getMonth() == month
						&& temp.getDueDate().getYear() == year) {
					inView.add(temp);
				}
			}
			return inView;
		}
		else if (currentView instanceof YearCalendarView)
		{
			int year = ((YearCalendarView) currentView).getYear();
			while(iterator.hasNext()) {
				temp = iterator.next();
				if(temp.getDueDate().getYear() == year) {
					inView.add(temp);
				}
			}
			return inView;
		}
		else return inView;
	}
	
	/**
	 * Calculates which events in a list are within the user's current view.
	 * @param list A List of events.
	 * @return inView An ArrayList of events in the user's current view.
	 */
	private ArrayList<Event> eventsInView(List<Event> list){
		ArrayList<Event> inView = new ArrayList<Event>();

		ICalendarView currentView = CalendarTabPanel.getCalendarView();
		Iterator<Event> iterator = list.iterator();
		Event temp = new Event();
		if (currentView instanceof DayCalendarPanel)
		{
			Date today = ((DayCalendarPanel) currentView).getDate();
			int startComparison;
			int endComparison;
			while(iterator.hasNext())
			{
				temp = iterator.next();
				startComparison = temp.getStartDate().compareTo(today);
				endComparison = temp.getEndDate().compareTo(today);
				if(((startComparison == 0) || (endComparison == 0))
						|| (startComparison < 0 && endComparison > 0))
					inView.add(temp);
			}
			return inView;
		}
		else if (currentView instanceof WeekCalendarPanel)
		{ 
			int startComparison;
			int endComparison;
			Date startOfWeek = ((WeekCalendarPanel) currentView).getWeekStart();
			Date endOfWeek = startOfWeek;
			endOfWeek.setDate(startOfWeek.getDate() + 6);
			while(iterator.hasNext()) {
				temp = iterator.next();
				// As long as the event does not begin after the week ends or end
				// before the week begins, then part of the event must occur within
				// the current week.
				startComparison = temp.getStartDate().compareTo(endOfWeek);
				endComparison = temp.getEndDate().compareTo(startOfWeek);
				if(startComparison <= 0 && endComparison >= 0) {
					inView.add(temp);
				}
			}
			return inView;
		}
		else if (currentView instanceof MonthCalendarView)
		{ 
			int month = ((MonthCalendarView) currentView).getMonth();
			int year = ((MonthCalendarView) currentView).getYear();
			Date lastMonth = new Date(year, month, 0);
			Date nextMonth = new Date(year, month + 1, 1);
			while(iterator.hasNext()) {
				temp = iterator.next();
				if(temp.getStartDate().before(nextMonth)
						&& temp.getEndDate().after(lastMonth)) {
					inView.add(temp);
				}
			}
			return inView;
		}
		else if (currentView instanceof YearCalendarView)
		{ 
			int year = ((YearCalendarView) currentView).getYear();
			while(iterator.hasNext()) {
				temp = iterator.next();
				if(temp.getStartDate().getYear() <= year
						&& temp.getEndDate().getYear() >= year) {
					inView.add(temp);
				}
			}
			return inView;
		}
		else return inView;
	}

	@Override
	public DeletableAbstractModel getElementAt(int index) {
		return filteredEvents.get(index);
	}

	@Override
	public int getSize() {
		return filteredEvents.size();
	}

	// Used to update the filtered contents when there are changes to the EventListModel

	@Override
	public void contentsChanged(ListDataEvent e) {
		filterEvents();
	}

	@Override
	public void intervalAdded(ListDataEvent e) {
		filterEvents();
	}

	@Override
	public void intervalRemoved(ListDataEvent e) {
		filterEvents();
	}

	@Override
	public void filterChanged() {
		filterEvents();
	}

	public synchronized List<DeletableAbstractModel> getList() {
		//filterEvents();
		return filteredEvents;
	}
}
