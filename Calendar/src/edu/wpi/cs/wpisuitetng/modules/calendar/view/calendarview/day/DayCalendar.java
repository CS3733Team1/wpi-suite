/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day;

import java.awt.Color;
import java.awt.Font;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.FilteredEventsListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.ICalendarView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.CalendarUtils;

public class DayCalendar extends JPanel implements ICalendarView, ListDataListener{

	private DayCalendarScrollPane dayscroll;
	private DayHolderPanel daylayer;
	private MultidayEventView multiview;
	private DayMultiScrollPane mscroll;
	public static final String[] WEEK_NAMES = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
	private JLabel dayLabel;
	private JPanel daytitle;
	private JPanel dayname;
	private int scrollSpeed = 15;


	public DayCalendar(){

		this.setLayout(new MigLayout("fill, insets 0"));

		daylayer = new DayHolderPanel();

		daytitle = new JPanel(new MigLayout("fill, insets 0", "[10%]0[90%]"));

		dayLabel = new JLabel(WEEK_NAMES[(daylayer.getDayViewDate().getDay())], JLabel.CENTER);
		dayLabel.setFont(new Font(dayLabel.getName(), Font.BOLD, 14));
		dayname = new JPanel(new MigLayout("fill"));
		Calendar cal = Calendar.getInstance();

		Date today = daylayer.getDayViewDate();
		int currentYear = today.getYear() + 1900;
		int currentMonth = today.getMonth();
		int currentDate = today.getDate();

		if(cal.get(Calendar.MONTH) == currentMonth &&
				cal.get(Calendar.YEAR) == currentYear && 
				cal.get(Calendar.DATE) == currentDate){
			dayname.setBorder(new MatteBorder(0, 0, 5, 0, CalendarUtils.thatBlue));
		}else{
			dayname.setBorder(new MatteBorder(0, 0, 5, 0, Color.gray));
		}
		
		dayname.add(dayLabel, "grow, aligny center");
		dayname.setBackground(Color.white);

		JPanel time = new JPanel(new MigLayout("fill"));
		time.add(new JLabel(" "), "grow, aligny center");
		time.setBackground(Color.white);

		daytitle.add(time, "aligny center, w 5000, hmin 50, hmax 50, grow");
		daytitle.add(dayname,  "aligny center, w 5000,hmin 50, hmax 50, grow");

		this.add(daytitle, "grow, wrap, hmin 50, hmax 50");

		dayscroll = new DayCalendarScrollPane(daylayer);
		
		multiview = new MultidayEventView(daylayer.getMultiDayEvents(), daylayer.getDayViewDate());
		
		mscroll = new DayMultiScrollPane(multiview);
		this.add(mscroll, "grow, wrap, hmin 80, hmax 80");
		mscroll.setWheelScrollingEnabled(true);
		mscroll.getVerticalScrollBar().setUnitIncrement(scrollSpeed);
		mscroll.getViewport().repaint();
		multiview.repaint();
		mscroll.updateUI();

		this.add(dayscroll, "grow, push");
		
		FilteredEventsListModel.getFilteredEventsListModel().addListDataListener(this);
		
		int end = dayscroll.getVerticalScrollBar().getMaximum();
		dayscroll.getVerticalScrollBar().setValue(3*end/8);
		
		//this.repaint();

	}

	public void updateDay(){
		Calendar cal = Calendar.getInstance();

		Date today = daylayer.getDayViewDate();
		int currentYear = today.getYear() + 1900;
		int currentMonth = today.getMonth();
		int currentDate = today.getDate();

		if(cal.get(Calendar.MONTH) == currentMonth &&
				cal.get(Calendar.YEAR) == currentYear && 
				cal.get(Calendar.DATE) == currentDate){
			dayname.setBorder(new MatteBorder(0, 0, 5, 0, CalendarUtils.thatBlue));
		}else{
			dayname.setBorder(new MatteBorder(0, 0, 5, 0, Color.gray));
		}
		dayLabel.setText(WEEK_NAMES[daylayer.getDayViewDate().getDay()]);
		daytitle.resize(size());
	}
	
	public Date getDate(){
		return daylayer.getDayViewDate();
	}

	public void repaint(){
		if (daylayer != null){
			daylayer.reSize(this.getWidth() - (dayscroll.getVerticalScrollBar().getWidth()*2));
		}
		if (multiview != null){
			multiview.reSize(this.getWidth() - (dayscroll.getVerticalScrollBar().getWidth()*2));
			updateMultiDay();
		}

		super.repaint();
	}
	
	public void updateMultiDay(){
		multiview.updateMultiDay(daylayer.getMultiDayEvents(), daylayer.getDayViewDate());
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		int end = dayscroll.getVerticalScrollBar().getMaximum();
		dayscroll.getVerticalScrollBar().setValue(3*end/8);
		return daylayer.getTitle();
	}

	@Override
	public void next() {
		// TODO Auto-generated method stub
		daylayer.next();
		updateDay();
		updateMultiDay();
	}

	@Override
	public void previous() {
		// TODO Auto-generated method stub
		daylayer.previous();
		updateDay();
		updateMultiDay();
	}

	@Override
	public void today() {
		// TODO Auto-generated method stub
		daylayer.today();
		updateDay();
		updateMultiDay();
	}


	@Override
	public void viewDate(Calendar date) {
		daylayer.viewDate(date);
		updateDay();
		updateMultiDay();
	}

	@Override
	public void intervalAdded(ListDataEvent e) {
		updateDay();
		updateMultiDay();
	}

	@Override
	public void intervalRemoved(ListDataEvent e) {
		updateDay();
		updateMultiDay();
	}

	@Override
	public void contentsChanged(ListDataEvent e) {
		updateDay();
		updateMultiDay();
	}

}
