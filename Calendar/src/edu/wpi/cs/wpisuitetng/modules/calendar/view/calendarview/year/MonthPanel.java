package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.year;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.CalendarConstants;

public class MonthPanel extends JPanel {

	// Stores all the days in a month
	private ArrayList<DayPanel> days;
	
	// The name of the month
	private JLabel monthNameLabel;

	public MonthPanel() {
		this.setMinimumSize(new Dimension(YearCalendarView.MIN_MONTH_WIDTH, 175));
		this.setLayout(new MigLayout("fill, gap 0 0, wrap 7"));

		this.setBackground(Color.WHITE);

		// Instantiate the labels in days
		this.days = new ArrayList<DayPanel>();
		for(int i = 0; i < 42; i++) {
			DayPanel day = new DayPanel();
			days.add(day);
			if((i+1)%7 == 0 || i >= 35) {
				if((i+1)%7 == 0) {
					if(i < 35) day.setBorder(new MatteBorder(1, 1, 0, 1, Color.LIGHT_GRAY));
					else day.setBorder(new MatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
				} else day.setBorder(new MatteBorder(1, 1, 1, 0, Color.LIGHT_GRAY));
			} else day.setBorder(new MatteBorder(1, 1, 0, 0, Color.LIGHT_GRAY));
		}

		JPanel titlePanel = new JPanel();
		titlePanel.setBackground(new Color(138,173,209));

		monthNameLabel = new JLabel();
		titlePanel.add(monthNameLabel);
		this.add(titlePanel, "center, growx, span, wrap");

		for(String weekDay: CalendarConstants.weekNamesAbbr) this.add(new JLabel(weekDay, JLabel.CENTER), "growx, alignx center");

		// Add all the day labels to the month
		for(DayPanel dayPanel: days) this.add(dayPanel, "grow, alignx center");
	}

	/*
	 * Updates the day label text to match the dates in the given Calendar month
	 */
	public void updateDates(Calendar month) {
		monthNameLabel.setText(CalendarConstants.monthNames[month.get(Calendar.MONTH)]);

		int currentMonth = month.get(Calendar.MONTH);
		
		if(month.get(Calendar.DAY_OF_WEEK) == 1) month.add(Calendar.DATE, -7);
		else month.add(Calendar.DATE, -month.get(Calendar.DAY_OF_WEEK)+1);

		for(int i = 0; i < days.size(); i++) {
			DayPanel dayPanel = days.get(i);
			dayPanel.setIsCurrentMonth(month.get(Calendar.MONTH) != currentMonth);
			dayPanel.setIsWeekend(i%7 <= 1);
			dayPanel.setDate(month.get(Calendar.DATE));
			
			month.add(Calendar.DATE, 1);
		}
	}

	// Mark the date that is today, yellow
	public void markDateToday(Calendar calendar) {
		int index = calendar.get(Calendar.DATE) - 1;
		calendar.set(Calendar.DATE, 1);
		if(calendar.get(Calendar.DAY_OF_WEEK) == 1) index += 7;
		else index += calendar.get(Calendar.DAY_OF_WEEK) - 1;

		days.get(index).setIsToday(true);
	}

	// Mark the date that has an event or commitment with red text
	public void markEventCommitmentDate(Calendar calendar) {
		int index = calendar.get(Calendar.DATE) - 1;
		calendar.set(Calendar.DATE, 1);
		if(calendar.get(Calendar.DAY_OF_WEEK) == 1) index += 7;
		else index += calendar.get(Calendar.DAY_OF_WEEK) - 1;

		days.get(index).addEvComm();
	}
}
