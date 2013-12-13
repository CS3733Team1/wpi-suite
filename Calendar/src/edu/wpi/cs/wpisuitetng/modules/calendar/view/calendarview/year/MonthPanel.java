package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.year;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.CalendarUtils;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarTabPanel;
public class MonthPanel extends JPanel implements MouseListener {

	// Stores all the days in a month
	private ArrayList<DayPanel> days;
	private ArrayList<JLabel> weekDayLabels;

	// The name of the month
	private JLabel monthNameLabel;
	
	private JPanel titlePanel;
	
	private Calendar month;

	public MonthPanel() {
		this.setMinimumSize(new Dimension(YearCalendarView.MIN_MONTH_WIDTH, 140));
		this.setLayout(new MigLayout("fill, gap 0, wrap 7"));

		this.setBackground(Color.WHITE);

		titlePanel = new JPanel();
		
		monthNameLabel = new JLabel();
		monthNameLabel.setFont(new Font(monthNameLabel.getFont().getName(), Font.BOLD, 14));
		
		titlePanel.add(monthNameLabel);
		
		titlePanel.addMouseListener(this);
		
		this.add(titlePanel, "center, growx, span, wrap");

		this.weekDayLabels = new ArrayList<JLabel>();

		for(String weekDay: CalendarUtils.weekNamesAbbr) {
			JLabel weekDayLabel = new JLabel(weekDay, JLabel.CENTER);
			weekDayLabel.setBorder(new MatteBorder(0, 0, 1, 0, Color.DARK_GRAY));
			weekDayLabels.add(weekDayLabel);
			this.add(weekDayLabel, "growx, alignx center");
		}

		// Instantiate the labels in days
		this.days = new ArrayList<DayPanel>();

		for(int i = 0; i < 42; i++) {
			DayPanel day = new DayPanel();
			day.addMouseListener(this);
			days.add(day);
			if((i+1)%7 == 0 || i >= 35) {
				if((i+1)%7 == 0) {
					if(i < 35) day.setBorder(new MatteBorder(1, 1, 0, 1, Color.WHITE));
					else day.setBorder(new MatteBorder(1, 1, 1, 1, Color.WHITE));
				} else day.setBorder(new MatteBorder(1, 1, 1, 0, Color.WHITE));
			} else day.setBorder(new MatteBorder(1, 1, 0, 0, Color.WHITE));
		}

		// Add all the day labels to the month
		for(DayPanel dayPanel: days) this.add(dayPanel, "grow, alignx center");
	}

	/*
	 * Updates the day label text to match the dates in the given Calendar month
	 */
	public void updateDates(Calendar month) {
		this.month = (Calendar)month.clone();
		monthNameLabel.setText(CalendarUtils.monthNames[month.get(Calendar.MONTH)]);
		monthNameLabel.setForeground(CalendarUtils.titleNameColor);

		for(JLabel weekDayLabel: weekDayLabels) weekDayLabel.setForeground(Color.GRAY);

		int currentMonth = month.get(Calendar.MONTH);

		if(month.get(Calendar.DAY_OF_WEEK) == 1) month.add(Calendar.DATE, -7);
		else month.add(Calendar.DATE, -month.get(Calendar.DAY_OF_WEEK)+1);

		for(int i = 0; i < days.size(); i++) {
			DayPanel dayPanel = days.get(i);
			dayPanel.setIsCurrentMonth(month.get(Calendar.MONTH) == currentMonth);
			dayPanel.setIsWeekend((i+1)%7 <= 1);
			dayPanel.setDate((Calendar)month.clone());
			dayPanel.setIsToday(false);
			dayPanel.updateColors();

			dayPanel.resetEvCommCount();

			month.add(Calendar.DATE, 1);
		}
	}

	// Mark the date that is today, yellow
	public void markDateToday(Calendar calendar) {
		monthNameLabel.setForeground(CalendarUtils.thatBlue);
		weekDayLabels.get(calendar.get(Calendar.DAY_OF_WEEK) - 1).setForeground(CalendarUtils.thatBlue);

		int index = calendar.get(Calendar.DATE) - 1;
		calendar.set(Calendar.DATE, 1);
		if(calendar.get(Calendar.DAY_OF_WEEK) == 1) index += 7;
		else index += calendar.get(Calendar.DAY_OF_WEEK) - 1;

		days.get(index).setIsToday(true);
		days.get(index).updateColors();
	}

	// Mark the date that has an event or commitment with red text
	public void markEventCommitmentDate(Calendar calendar) {
		int index = calendar.get(Calendar.DATE) - 1;
		calendar.set(Calendar.DATE, 1);
		if(calendar.get(Calendar.DAY_OF_WEEK) == 1) index += 7;
		else index += calendar.get(Calendar.DAY_OF_WEEK) - 1;

		days.get(index).addEvComm();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() instanceof DayPanel){
			DayPanel day = (DayPanel)e.getSource();
			Calendar clickedDay = day.getDate();
			CalendarTabPanel tab = (CalendarTabPanel)(this.getParent().getParent().getParent().getParent().getParent().getParent());

			tab.displayDayView();
			tab.setCalendarViewDate(clickedDay);
		}
		else if(e.getSource().equals(titlePanel)){
			CalendarTabPanel tab = (CalendarTabPanel)(this.getParent().getParent().getParent().getParent().getParent().getParent());

			tab.displayMonthView();
			tab.setCalendarViewDate((Calendar)month.clone());
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
