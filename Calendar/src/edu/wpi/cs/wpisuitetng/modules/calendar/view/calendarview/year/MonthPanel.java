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
	private ArrayList<JLabel> days;
	
	// The name of the month
	private JLabel monthNameLabel;

	public MonthPanel() {
		this.setMinimumSize(new Dimension(YearCalendarView.MIN_MONTH_WIDTH, 175));
		this.setLayout(new MigLayout("fill, gap 0 0, wrap 7"));

		this.setBackground(Color.WHITE);

		// Instantiate the labels in days
		this.days = new ArrayList<JLabel>();
		for(int i = 0; i < 7*6; i++) {
			JLabel day = new JLabel("", JLabel.CENTER);
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
		for(JLabel l: days) this.add(l, "grow, alignx center");
	}

	/*
	 * Updates the day label text to match the dates in the given Calendar month
	 */
	public void updateDates(Calendar month) {
		monthNameLabel.setText(CalendarConstants.monthNames[month.get(Calendar.MONTH)]);

		int currentMonth = month.get(Calendar.MONTH);
		
		if(month.get(Calendar.DAY_OF_WEEK) == 1) month.add(Calendar.DATE, -7);
		else month.add(Calendar.DATE, -month.get(Calendar.DAY_OF_WEEK)+1);

		for(JLabel l: days) {
			if(month.get(Calendar.MONTH) != currentMonth) l.setForeground(Color.LIGHT_GRAY);
			else l.setForeground(Color.BLACK);

			l.setOpaque(false);
			l.setText(month.get(Calendar.DATE)+"");
			month.add(Calendar.DATE, 1);
		}
	}

	// Mark the date that is today, yellow
	public void markDateToday(Calendar calendar) {
		int index = calendar.get(Calendar.DATE) - 1;
		calendar.set(Calendar.DATE, 1);
		if(calendar.get(Calendar.DAY_OF_WEEK) == 1) index += 7;
		else index += calendar.get(Calendar.DAY_OF_WEEK) - 1;

		JLabel today = days.get(index);
		today.setOpaque(true);
		today.setBackground(new Color(236,252,144));
	}

	// Mark the date that has an event or commitment with red text
	public void markEventCommitmentDate(Calendar calendar) {
		int index = calendar.get(Calendar.DATE) - 1;
		calendar.set(Calendar.DATE, 1);
		if(calendar.get(Calendar.DAY_OF_WEEK) == 1) index += 7;
		else index += calendar.get(Calendar.DAY_OF_WEEK) - 1;

		days.get(index).setForeground(Color.RED);
	}
}
