package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.year;

import java.awt.Color;
import java.util.Calendar;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.CalendarUtils;

public class DayPanel extends JPanel {
	private static final Color highDensity = CalendarUtils.thatBlue;
	private static final Color mediumHighDensity = CalendarUtils.blend(highDensity, Color.white, (float) 0.8);
	private static final Color mediumDensity  =  CalendarUtils.blend(highDensity, Color.white, (float) 0.6);
	private static final Color mediumLowDensity = CalendarUtils.blend(highDensity, Color.white, (float) 0.4);
	private static final Color lowDensity = CalendarUtils.blend(highDensity, Color.white, (float) 0.2);
	
	private int numEvComms;
	private boolean isToday;
	private boolean isWeekend;
	private boolean isCurrentMonth;
	
	private JLabel day;
	private Calendar date;
	
	public DayPanel() {
		this.setLayout(new MigLayout("fill"));
		this.numEvComms = 0;
		this.isToday = false;
		this.isWeekend = false;
		this.isCurrentMonth = true;
		
		day = new JLabel("", JLabel.CENTER);
		this.add(day, "align center");
	}
	
	public void setIsCurrentMonth(boolean isCurrentMonth) {
		this.isCurrentMonth = isCurrentMonth;
	}

	public void setIsWeekend(boolean isWeekend) {
		this.isWeekend = isWeekend;
	}

	public void setIsToday(boolean isToday) {
		this.isToday = isToday;
	}

	public void setDate(Calendar date) {
		this.date = date;
		day.setText(date.get(Calendar.DATE) + "");
	}
	
	public Calendar getDate() {
		return this.date;
	}
	
	public void updateColors() {
		if(isCurrentMonth) day.setForeground(Color.DARK_GRAY);
		else day.setForeground(Color.LIGHT_GRAY);
		
		if(isToday) {
			this.setBackground(CalendarUtils.todayYearColor);
			day.setForeground(CalendarUtils.thatBlue);
		} else {
			if(isWeekend) this.setBackground(CalendarUtils.weekendColor);
			else this.setBackground(Color.WHITE);
		}
	}

	public void resetEvCommCount() {numEvComms = 0;}
	
	public void addEvComm() {
		numEvComms++;
		if(!isToday) {
			switch(numEvComms) {
			case 1:
				this.setBackground(lowDensity);
				day.setForeground(CalendarUtils.textColor(lowDensity));
				break;
			case 2:
				this.setBackground(mediumLowDensity);
				day.setForeground(CalendarUtils.textColor(mediumLowDensity));
				break;
			case 3:
				this.setBackground(mediumDensity);
				day.setForeground(CalendarUtils.textColor(mediumDensity));
				break;
			case 4:
				this.setBackground(mediumHighDensity);
				day.setForeground(CalendarUtils.textColor(mediumHighDensity));
				break;
			default:
				this.setBackground(highDensity);
				day.setForeground(CalendarUtils.textColor(highDensity));
				break;
			}
		}
	}
}
