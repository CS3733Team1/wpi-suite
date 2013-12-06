package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.year;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
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

public class YearCalendarView extends JPanel implements ICalendarView, AncestorListener, ComponentListener, ListDataListener {

	// Defines the minimum width of a month to be displayed in the year calendar view
	// Used to know when to change the number of month columns displayed
	public static final int MIN_MONTH_WIDTH = 200;

	// Parent Panel containing all of the months [implements Scrollable to disable horizontal scrolling]
	private MonthsContainerPanel monthsContainerPanel;

	// A List holding all of the Month Panels as to be able to modify the contents [No need to reacrate a new month on view changes]
	private List<MonthPanel> monthList;

	// A Calendar to keep track of the year the user is currently viewing
	private Calendar currentYear;

	// Used to store the number of columns that the Months will occupy
	private int columns;

	// Handles on the events and commitments models
	private FilteredEventsListModel filteredEventsModel;
	private FilteredCommitmentsListModel filteredCommitmentsModel;

	public YearCalendarView() {
		this.filteredEventsModel = FilteredEventsListModel.getFilteredEventsListModel();
		this.filteredCommitmentsModel = FilteredCommitmentsListModel.getFilteredCommitmentsListModel();

		// Add ListDataListeners so the YearCalendarView can be notified of data changes.
		this.filteredEventsModel.addListDataListener(this);
		this.filteredCommitmentsModel.addListDataListener(this);

		this.setLayout(new MigLayout("fill, insets 0"));

		this.currentYear = Calendar.getInstance();

		this.columns = 3;

		this.monthsContainerPanel = new MonthsContainerPanel();

		this.monthList = new ArrayList<MonthPanel>();

		for(int i = 0; i < 12; i++) monthList.add(new MonthPanel());

		// Fill the newly created months with their respective dates as well as events or commitments
		this.updateDates();

		JScrollPane scrollPane = new JScrollPane(monthsContainerPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		this.add(scrollPane, "grow");
		this.addAncestorListener(this);
		this.addComponentListener(this);
	}

	/*
	 * Changes the layout of the Months depending on the variable columns
	 */
	private void updateLayout() {
		monthsContainerPanel.removeAll();
		monthsContainerPanel.setLayout(new MigLayout("fill, insets 0, wrap " + columns));

		for(MonthPanel month: monthList) monthsContainerPanel.add(month, "grow");
	}

	/*
	 * Fills the Months with their respective dates as well as events or commitments
	 */
	private void updateDates() {
		// Fill the dates for every month
		Calendar monthsCal = (Calendar)currentYear.clone();
		monthsCal.set(Calendar.MONTH, 0);
		monthsCal.set(Calendar.DATE, 1);
		for(MonthPanel month: monthList) {
			month.updateDates((Calendar)monthsCal.clone());
			monthsCal.add(Calendar.MONTH, 1);
		}

		// Set the current day to be highlighted yellow
		Calendar today = Calendar.getInstance();
		if(today.get(Calendar.YEAR) == currentYear.get(Calendar.YEAR))
			monthList.get(today.get(Calendar.MONTH)).markDateToday(today);

		// Highlight the day text for the correct month red if there is an event
		for(Event event: filteredEventsModel.getList()) {
			Date start = event.getStartDate();
			Calendar startCal = Calendar.getInstance();
			startCal.set(start.getYear()+1900, start.getMonth(), start.getDate());
			if(startCal.get(Calendar.YEAR) == currentYear.get(Calendar.YEAR))
				monthList.get(startCal.get(Calendar.MONTH)).markEventCommitmentDate((Calendar)startCal.clone());
		}

		// Highlight the day text for the correct month red if there is a commitment
		for(Commitment commitment: filteredCommitmentsModel.getList()) {
			Date date = commitment.getDueDate();
			Calendar dateCal = Calendar.getInstance();
			dateCal.set(date.getYear()+1900, date.getMonth(), date.getDate());
			if(dateCal.get(Calendar.YEAR) == currentYear.get(Calendar.YEAR))
				monthList.get(dateCal.get(Calendar.MONTH)).markEventCommitmentDate((Calendar)dateCal.clone());
		}
	}

	private void updateColumns() {
		if(this.getWidth()/(MIN_MONTH_WIDTH+25) >= 4) { // 4 columns and 3 rows of months
			if(columns != 4) {
				columns = 4;
				this.updateLayout();
			}
		} else if(this.getWidth()/(MIN_MONTH_WIDTH+25) == 3) { // 3 columns and 4 rows of months
			if(columns != 3) {
				columns = 3;
				this.updateLayout();
			}
		} else if(this.getWidth()/(MIN_MONTH_WIDTH+25) <= 2) { // 2 columns and 6 rows of months
			if(columns != 2) {
				columns = 2;
				this.updateLayout();
			}
		}
	}

	@Override
	public String getTitle() {
		return "Year of " + currentYear.get(Calendar.YEAR);
	}

	@Override
	public void next() {
		currentYear.add(Calendar.YEAR, 1);
		this.updateDates();
	}

	@Override
	public void previous() {
		currentYear.add(Calendar.YEAR, -1);
		this.updateDates();
	}

	@Override
	public void today() {
		if(this.currentYear.get(Calendar.YEAR) != Calendar.getInstance().get(Calendar.YEAR)) {
			this.currentYear = Calendar.getInstance();
			this.updateDates();
		}
	}

	@Override
	public void contentsChanged(ListDataEvent e) {
		this.updateDates();
	}

	@Override
	public void intervalAdded(ListDataEvent e) {
		this.updateDates();
	}

	@Override
	public void intervalRemoved(ListDataEvent e) {
		this.updateDates();
	}

	public void ancestorAdded(AncestorEvent e) {
		this.updateColumns();
	}

	@Override
	public void componentResized(ComponentEvent e) {
		this.updateColumns();
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
