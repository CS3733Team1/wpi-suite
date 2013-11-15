package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JPanel;

import org.jdesktop.swingx.JXMonthView;
import org.jdesktop.swingx.calendar.DateSelectionModel.SelectionMode;

import edu.wpi.cs.wpisuitetng.modules.calendar.view.ErrorPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.ViewMode;

public class DatePickerPanel extends JPanel {
	private JXMonthView calendarMonthView;
	public static final Color START_END_DAY = new Color(47, 150, 9);
	public static final Color SELECTION = new Color(236,252,144);
	public static final Color UNSELECTABLE = Color.red;

	/**
	 * Create the panel.
	 */
	public DatePickerPanel() {
		
		
		buildLayout();
		

	}
	
	private void buildLayout(){
		
		this.setLayout(new BorderLayout());
		/*
		 * potential inputs to daypicker constructor:
		 * 		number of months to be displayed
		 * 		this (so it can set local start and end date variables)
		 */
		calendarMonthView = new DatePicker();
		this.setAlignmentX(CENTER_ALIGNMENT);
		this.add(calendarMonthView);
	}
	
	public ArrayList<Date> getDates(){
		ArrayList<Date> dates = new ArrayList<Date>();
		Date firstSel,lastSel,testDate;
		firstSel=calendarMonthView.getFirstSelectionDate();
		lastSel=calendarMonthView.getLastSelectionDate();
		testDate=firstSel;
		while(testDate.compareTo(lastSel)<=0){
			if(calendarMonthView.isSelected(testDate)){
				dates.add(new Date(testDate.getYear(),testDate.getMonth(),testDate.getDate()));
			}
			testDate.setDate(testDate.getDate()+1);
		}
		return dates;
	}
	
	/*
	 * temporary function while event is modified
	 */
	public Date getDate(){
		return calendarMonthView.getFirstSelectionDate();
	}
}
