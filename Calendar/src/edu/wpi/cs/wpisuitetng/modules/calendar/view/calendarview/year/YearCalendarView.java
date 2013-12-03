package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.year;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.CommitmentListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.EventListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.ICalendarView;

public class YearCalendarView extends JPanel implements ICalendarView, AncestorListener, ComponentListener, ListDataListener {

	private static final int MIN_MONTH_WIDTH = 200;

	private Year year;
	
	private ArrayList<Month> monthList;
	
	private Calendar calendar;

	private int c;

	public YearCalendarView() {
		this.setLayout(new MigLayout("fill, insets 0"));

		EventListModel.getEventListModel().addListDataListener(this);
		CommitmentListModel.getCommitmentListModel().addListDataListener(this);
		
		this.calendar = Calendar.getInstance();
		
		this.c = 0;

		this.year = new Year();
		
		this.monthList = new ArrayList<Month>();
		
		for(int i = 0; i < 12; i++) monthList.add(new Month());
		
		this.updateDates();

		JScrollPane scrollPane = new JScrollPane(year, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(scrollPane, "grow");
		this.addAncestorListener(this);
		this.addComponentListener(this);
	}

	private void updateLayout() {
		year.removeAll();
		year.setLayout(new MigLayout("fill, insets 0, gap 1% 1%, wrap " + c));

		for(Month month: monthList) year.add(month, "grow");
	}
	
	private void updateDates() {
		Calendar cal = (Calendar)calendar.clone();
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DATE, 1);
		for(Month month: monthList) {
			month.updateDates((Calendar)cal.clone());
			cal.add(Calendar.MONTH, 1);
		}
		
		Calendar today = Calendar.getInstance();
		
		if(today.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)) {
			monthList.get(today.get(Calendar.MONTH)).markDateToday(today);
		}
		
		for(Event event: EventListModel.getEventListModel().getList()) {
			Date start = event.getStartDate();
			Calendar startCal = Calendar.getInstance();
			startCal.set(start.getYear()+1900, start.getMonth(), start.getDate());
			if(startCal.get(Calendar.YEAR) == calendar.get(Calendar.YEAR))
				monthList.get(startCal.get(Calendar.MONTH)).markEventCommitmentDate((Calendar)startCal.clone());
		}
		
		for(Commitment commitment: CommitmentListModel.getCommitmentListModel().getList()) {
			Date date = commitment.getDueDate();
			Calendar dateCal = Calendar.getInstance();
			dateCal.set(date.getYear()+1900, date.getMonth(), date.getDate());
			if(dateCal.get(Calendar.YEAR) == calendar.get(Calendar.YEAR))
				monthList.get(dateCal.get(Calendar.MONTH)).markEventCommitmentDate((Calendar)dateCal.clone());
		}
	}

	@Override
	public String getTitle() {
		return "Year of " + calendar.get(Calendar.YEAR);
	}

	@Override
	public void next() {
		calendar.add(Calendar.YEAR, 1);
		updateDates();
		updateLayout();
	}

	@Override
	public void previous() {
		calendar.add(Calendar.YEAR, -1);
		updateDates();
		updateLayout();
	}

	@Override
	public void today() {
		if(calendar.get(Calendar.YEAR) != Calendar.getInstance().get(Calendar.YEAR)) {
			calendar = Calendar.getInstance();
			updateDates();
			updateLayout();
		}
	}
	
	@Override
	public void contentsChanged(ListDataEvent e) {
		updateDates();
	}
	
	@Override
	public void intervalAdded(ListDataEvent e) {
		updateDates();
	}
	
	@Override
	public void intervalRemoved(ListDataEvent e) {
		updateDates();
	}

	public void ancestorAdded(AncestorEvent e) {
		if(this.getWidth()/(MIN_MONTH_WIDTH+25) >= 4) { // 4 columns and 3 rows of months
			if(c != 4) {
				c = 4;
				this.updateLayout();
			}
		} else if(this.getWidth()/(MIN_MONTH_WIDTH+25) == 3) { // 3 columns and 4 rows of months
			if(c != 3) {
				c = 3;
				this.updateLayout();
			}
		} else if(this.getWidth()/(MIN_MONTH_WIDTH+25) <= 2) { // 2 columns and 6 rows of months
			if(c != 2) {
				c = 2;
				this.updateLayout();
			}
		}
	}

	@Override
	public void componentResized(ComponentEvent e) {
		if(this.getWidth()/(MIN_MONTH_WIDTH+25) >= 4) { // 4 columns and 3 rows of months
			if(c != 4) {
				c = 4;
				this.updateLayout();
			}
		} else if(this.getWidth()/(MIN_MONTH_WIDTH+25) == 3) { // 3 columns and 4 rows of months
			if(c != 3) {
				c = 3;
				this.updateLayout();
			}
		} else if(this.getWidth()/(MIN_MONTH_WIDTH+25) <= 2) { // 2 columns and 6 rows of months
			if(c != 2) {
				c = 2;
				this.updateLayout();
			}
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
