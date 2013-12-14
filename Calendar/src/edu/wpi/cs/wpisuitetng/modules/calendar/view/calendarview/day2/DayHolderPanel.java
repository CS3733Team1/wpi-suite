package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day2;

import java.awt.Dimension;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class DayHolderPanel extends JPanel{
	private DayArea day;
	private HourLabels hour;

	public DayHolderPanel(){
		this.setLayout(new MigLayout("inset 0, fill", 
				"[10%][90%]"));

		hour = new HourLabels();
		day = new DayArea();
		
		int height = 1440;
		int width = 1000;
		
		this.setSize(width, height);
		this.setPreferredSize(new Dimension(width, height));
		this.setMinimumSize(new Dimension(0,1440));
		
		this.add(hour);
		this.add(day);
	}
	
	public void reSize(int width){
		this.setSize(width, 1440);
		this.setPreferredSize(new Dimension(width, 1440));
		this.setMinimumSize(new Dimension(0,1440));
		
		day.reSize(new Integer((int) (width * .9)));
		hour.reSize(new Integer((int) (width * .1)));
		
		this.repaint();
	}
	
	public Date getDayViewDate(){
		return day.getDayViewDate();
	}
     
	public String getTitle() {
		return day.getTitle();
	}

	public void next() {
		day.next();
	}

	public void previous() {
		day.previous();
	}

	public void today() {
		day.today();
	}

	public void viewDate(Calendar date) {
		day.viewDate(date);
	}
}
