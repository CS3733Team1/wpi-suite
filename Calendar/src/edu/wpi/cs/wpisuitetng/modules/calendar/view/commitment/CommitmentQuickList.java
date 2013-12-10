package edu.wpi.cs.wpisuitetng.modules.calendar.view.commitment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Date;

import javax.swing.AbstractListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.*;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.*;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.ICalendarView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.month.MonthCalendarView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day.DayCalendarPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.week.WeekCalendarPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.year.YearCalendarView;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.FilteredCommitmentsListModel;

public class CommitmentQuickList extends AbstractListModel<Commitment> implements ListDataListener {

	private ICalendarView currentView;
	private ArrayList<Commitment> quickList;
	
	private CommitmentQuickList(List<Commitment> list){
		quickList = commitmentsInView(FilteredCommitmentsListModel.getFilteredCommitmentsListModel().getList());
	}
	
	private ArrayList<Commitment> commitmentsInView(List<Commitment> list){
		ArrayList<Commitment> inView = new ArrayList<Commitment>();

		currentView = CalendarTabPanel.getCalendarView();
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
	
	@Override
	public void intervalAdded(ListDataEvent e) {
		// TODO Auto-generated method stub
		quickList = commitmentsInView(FilteredCommitmentsListModel.getFilteredCommitmentsListModel().getList());
	}

	@Override
	public void intervalRemoved(ListDataEvent e) {
		// TODO Auto-generated method stub
		quickList = commitmentsInView(FilteredCommitmentsListModel.getFilteredCommitmentsListModel().getList());
	}

	@Override
	public void contentsChanged(ListDataEvent e) {
		// TODO Auto-generated method stub
		quickList = commitmentsInView(FilteredCommitmentsListModel.getFilteredCommitmentsListModel().getList());
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Commitment getElementAt(int index) {
		// TODO Auto-generated method stub
		return null;
	}

}
