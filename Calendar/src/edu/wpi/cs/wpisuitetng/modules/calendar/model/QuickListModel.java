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
				tempDate.setDate(tempDate.getDate() - tempDate.getDay());
				if(temp.getDueDate() == ((WeekCalendarPanel) currentView).getWeekStart()) {
					inView.add(temp);
				}
			}
			return inView;
		}
		else if (currentView instanceof MonthCalendarView)
		{
			while(iterator.hasNext()) {
				temp = iterator.next();
				int month = ((MonthCalendarView) currentView).getMonth();
				if(temp.getDueDate().getMonth() == month) {
					inView.add(temp);
				}
			}
			return inView;
		}
		else if (currentView instanceof YearCalendarView)
		{
			while(iterator.hasNext()) {
				temp = iterator.next();
				int year = ((YearCalendarView) currentView).getYear();
				if(temp.getDueDate().getYear() == year) {
					inView.add(temp);
				}

			}
			return inView;
		}
		else return inView;
	}
	
	private ArrayList<Commitment> eventsInView(List<Event> list){
		ArrayList<Commitment> inView = new ArrayList<Commitment>();

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
						|| (startComparison == -1 && endComparison == 1))
					inView.add(temp);
			}
			return inView;
		}
		else if (currentView instanceof WeekCalendarPanel)
		{
			while(iterator.hasNext()) {
				temp = iterator.next();
				Date tempDate = temp.getStartDate();
				tempDate.setDate(tempDate.getDate() - tempDate.getDay());
				if(temp.getStartDate() == ((WeekCalendarPanel) currentView).getWeekStart()) {
					inView.add(temp);
				}
			}
			return inView;
		}
		else if (currentView instanceof MonthCalendarView)
		{
			while(iterator.hasNext()) {
				temp = iterator.next();
				int month = ((MonthCalendarView) currentView).getMonth();
				if(temp.getStartDate().getMonth() == month) {
					inView.add(temp);
				}
			}
			return inView;
		}
		else if (currentView instanceof YearCalendarView)
		{
			while(iterator.hasNext()) {
				temp = iterator.next();
				int year = ((YearCalendarView) currentView).getYear();
				if(temp.getStartDate().getYear() == year) {
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
