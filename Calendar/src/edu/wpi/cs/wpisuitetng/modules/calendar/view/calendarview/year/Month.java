package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.year;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.CalendarConstants;

public class Month extends JPanel {

	private ArrayList<JLabel> dates;
	private JLabel title;

	public Month() {
		this.setMinimumSize(new Dimension(200, 175));
		this.setLayout(new MigLayout("fill, wrap 7"));

		this.setBackground(Color.WHITE);

		this.dates = new ArrayList<JLabel>();
		for(int i = 0; i < 7*6; i++) dates.add(new JLabel());

		JPanel titleP = new JPanel();
		titleP.setBackground(new Color(138,173,209));

		title = new JLabel();
		titleP.add(title);
		this.add(titleP, "center, growx, span, wrap");

		for(String weekDay: CalendarConstants.weekNamesAbbr) this.add(new JLabel(weekDay), "growx, center");

		for(JLabel l: dates) this.add(l, "grow, center");
	}

	public void updateDates(Calendar calendar) {
		title.setText(CalendarConstants.monthNames[calendar.get(Calendar.MONTH)]);

		int currentMonth = calendar.get(Calendar.MONTH);
		
		if(calendar.get(Calendar.DAY_OF_WEEK) == 1) calendar.add(Calendar.DATE, -7);
		else calendar.add(Calendar.DATE, -calendar.get(Calendar.DAY_OF_WEEK)+1);

		for(JLabel l: dates) {
			if(calendar.get(Calendar.MONTH) != currentMonth) l.setForeground(Color.LIGHT_GRAY);
			else l.setForeground(Color.BLACK);

			l.setOpaque(false);
			l.setText(calendar.get(Calendar.DATE)+"");
			calendar.add(Calendar.DATE, 1);
		}
	}

	// Mark the date that is today, yellow.
	public void markDateToday(Calendar calendar) {
		int index = calendar.get(Calendar.DATE) - 1;
		calendar.set(Calendar.DATE, 1);
		if(calendar.get(Calendar.DAY_OF_WEEK) == 1) index += 7;
		else index += calendar.get(Calendar.DAY_OF_WEEK) - 1;

		JLabel today = dates.get(index);
		today.setOpaque(true);
		today.setBackground(new Color(236,252,144));
	}

	// Mark the date that has an event or commitment with red text.
	public void markEventCommitmentDate(Calendar calendar) {
		int index = calendar.get(Calendar.DATE) - 1;
		calendar.set(Calendar.DATE, 1);
		if(calendar.get(Calendar.DAY_OF_WEEK) == 1) index += 7;
		else index += calendar.get(Calendar.DAY_OF_WEEK) - 1;

		dates.get(index).setForeground(Color.RED);
	}
}
