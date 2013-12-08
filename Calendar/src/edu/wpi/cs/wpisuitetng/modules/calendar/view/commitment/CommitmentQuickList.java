package edu.wpi.cs.wpisuitetng.modules.calendar.view.commitment;

import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.*;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.*;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.ICalendarView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.MonthCalendarView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day.DayCalendarPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.week.WeekCalendarPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.year.YearCalendarView;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.FilteredCommitmentsListModel;

public class CommitmentQuickList extends AbstractListModel<Commitment> implements ListDataListener {

	private ICalendarView currentView;
	
	private CommitmentQuickList(List<Commitment> list){
		commitmentsInView(FilteredCommitmentsListModel.getFilteredCommitmentsListModel().getList());
	}
	
	private List<Commitment> commitmentsInView(List<Commitment> list){
		currentView = CalendarTabPanel.getCalendarView();
		Iterator<Commitment> iterator = list.iterator();
		if (currentView instanceof DayCalendarPanel)
		{
			while(iterator.hasNext())
			{
			}
			return list;
		}
		else if (currentView instanceof WeekCalendarPanel)
		{
			return list;
		}
		else if (currentView instanceof MonthCalendarView)
		{
			return list;
		}
		else if (currentView instanceof YearCalendarView)
		{
			return list;
		}
		else return list;
	}
	
	@Override
	public void intervalAdded(ListDataEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void intervalRemoved(ListDataEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contentsChanged(ListDataEvent e) {
		// TODO Auto-generated method stub

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
