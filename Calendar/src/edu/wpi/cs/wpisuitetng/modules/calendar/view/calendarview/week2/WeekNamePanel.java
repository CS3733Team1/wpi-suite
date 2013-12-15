package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.week2;

import java.util.Calendar;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class WeekNamePanel extends JPanel{
	
	private JLabel dayLabel;
	private Calendar date;
	
	public WeekNamePanel() {
		this.setLayout(new MigLayout("fill, insets 0"));

		dayLabel = new JLabel("", JLabel.CENTER);
		this.add(dayLabel, "grow, aligny bottom");
	}
	
	public void setDayLabel(JLabel day) {
		this.removeAll();
		this.setLayout(new MigLayout("fill, insets 0"));
		this.dayLabel = day;
		dayLabel.setText(day.getText());
		this.add(dayLabel, "grow, aligny bottom");
		repaint();
	}
	
	public JLabel getDayLabel() {
		return this.dayLabel;
	}
	
	public Calendar getDate() {
		return this.date;
	}
	
	public void setDate(Calendar date) {
		this.date = date;
		dayLabel.setText(date.get(Calendar.DATE) + "");
	}

}
