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
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.QuickListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.filter.FilteredEventsListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarTabPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.MainView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.ICalendarView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.CalendarUtils;

/**
 * Holds all the GUI elements for the day view
 */
public class DayCalendar extends JPanel implements ICalendarView, ListDataListener, AdjustmentListener{

	private DayCalendarScrollPane dayscroll;
	private DayHolderPanel daylayer;
	private MultidayEventView multiview;
	private DayMultiScrollPane mscroll;
	public static final String[] WEEK_NAMES = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
	private JLabel dayLabel;
	private JPanel daytitle;
	private JPanel dayname;
	private boolean scrollchange;
	private int scrollSpeed = 15;

	private Date current;

	public DayCalendar(){
		scrollchange = false;
		this.setLayout(new MigLayout("fill, insets 0"));

		daylayer = new DayHolderPanel();

		daytitle = new JPanel(new MigLayout("fill, insets 0", "[10%]0[90%]"));

		dayLabel = new JLabel(WEEK_NAMES[(daylayer.getDayViewDate().getDay())], JLabel.CENTER);
		dayLabel.setFont(new Font(dayLabel.getName(), Font.BOLD, 14));
		dayname = new JPanel(new MigLayout("fill, insets 0"));
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
		dayscroll.getVerticalScrollBar().setValue(4*end/9);
		dayscroll.getVerticalScrollBar().addAdjustmentListener(this);
		
		//this.repaint();
		current = getDate();
	}

	/**
	 * Refresh the display of the day being shown
	 */
	public void updateDay(){
		Calendar cal = Calendar.getInstance();

		Date today = getDate();
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
		dayLabel.setText(WEEK_NAMES[getDate().getDay()]);
		daytitle.resize(size());
	}
	
	public Date getDate(){
		JScrollBar scroller = dayscroll.getVerticalScrollBar();
		if (scroller.getValue() < (1*scroller.getMaximum()/3)){
			return daylayer.getPreviousDayViewDate();
		}
		else if (scroller.getValue() > (2*scroller.getMaximum()/3)){
			return daylayer.getNextDayViewDate();
		}
		else{
			return daylayer.getDayViewDate();
		}
	}

	public void repaint(){
		if (daylayer != null){
			daylayer.reSize(this.getWidth() - 20);
		}
		if (multiview != null){
			multiview.reSize(this.getWidth() - 20);
			updateMultiDay();
		}

		super.repaint();
	}
	
	public void updateMultiDay(){
		multiview.updateMultiDay(daylayer.getMultiDayEvents(getDate()), getDate());
		if (multiview.getNumberofEvents() == 0){
			this.removeAll();
			this.add(daytitle, "grow, wrap, hmin 50, hmax 50");
			this.add(dayscroll, "grow, push");
		}
		else{
			this.removeAll();
			this.add(daytitle, "grow, wrap, hmin 50, hmax 50");
			this.add(mscroll, "grow, wrap, hmin 80, hmax 80");
			this.add(dayscroll, "grow, push");
		}
	}

	public String getScrollTitle(){
		return daylayer.getTitle(getDate());
	}
	
	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		int end = dayscroll.getVerticalScrollBar().getMaximum();
		if (!scrollchange){
			dayscroll.getVerticalScrollBar().setValue(4*end/9);
		}
		return daylayer.getTitle();
	}

	@Override
	public void next() {
		// TODO Auto-generated method stub
		daylayer.viewDate(new Date(getDate().getYear(), getDate().getMonth(), getDate().getDate()+1));
		updateDay();
		JScrollBar scroller = dayscroll.getVerticalScrollBar();
		scroller.repaint();
		scroller.updateUI();
		repaint();
		this.updateUI();
		current = getDate();
	}

	@Override
	public void previous() {
		// TODO Auto-generated method stub
		daylayer.viewDate(new Date(getDate().getYear(), getDate().getMonth(), getDate().getDate()-1));
		updateDay();
		JScrollBar scroller = dayscroll.getVerticalScrollBar();
		scroller.repaint();
		scroller.updateUI();
		repaint();
		this.updateUI();
		current = getDate();
	}

	@Override
	public void today() {
		// TODO Auto-generated method stub
		daylayer.viewDate(new Date());
		updateDay();
		JScrollBar scroller = dayscroll.getVerticalScrollBar();
		scroller.repaint();
		scroller.updateUI();
		repaint();
		this.updateUI();
		current = getDate();
	}


	@Override
	public void viewDate(Calendar date) {
		daylayer.viewDate(date);
		updateDay();
		JScrollBar scroller = dayscroll.getVerticalScrollBar();
		scroller.repaint();
		scroller.updateUI();
		repaint();
		this.updateUI();
		current = getDate();
	}

	@Override
	public void intervalAdded(ListDataEvent e) {
		repaint();
		this.updateUI();
	}

	@Override
	public void intervalRemoved(ListDataEvent e) {
		repaint();
		this.updateUI();
	}

	@Override
	public void contentsChanged(ListDataEvent e) {
		repaint();
		this.updateUI();
	}
	
	@Override
	public void adjustmentValueChanged(AdjustmentEvent e) {
		JScrollBar scroller = (JScrollBar) e.getSource();
		
		if (scroller.getValue() == scroller.getMinimum()){
			scroller.setValue(scroller.getMaximum()/3);
			scrollchange = true;
			if (MainView.getCurrentCalendarPanel() != null && MainView.getCurrentCalendarPanel().getCalendarTabPanel() != null){
				MainView.getCurrentCalendarPanel().getCalendarTabPanel().setCalendarViewPrevious();
			}
			else{
				previous();
			}
			scrollchange = false;
			updateDay();
			scroller.repaint();
			scroller.updateUI();
			repaint();
			this.updateUI();
			current = getDate();
		}
		
		if (scroller.getValue() == scroller.getMaximum()-scroller.getHeight()){
			scroller.setValue(2*scroller.getMaximum()/3-scroller.getHeight());
			scrollchange = true;
			if (MainView.getCurrentCalendarPanel() != null && MainView.getCurrentCalendarPanel().getCalendarTabPanel() != null){
				MainView.getCurrentCalendarPanel().getCalendarTabPanel().setCalendarViewNext();
			}
			else{
				next();
			}
			scrollchange = false;
			updateDay();
			scroller.repaint();
			scroller.updateUI();
			repaint();
			this.updateUI();
			current = getDate();
		}
		
		
		dayscroll.getViewport().repaint();
		dayscroll.getViewport().updateUI();
		
		if (!current.equals(getDate())){
			updateDay();
			repaint();
			scrollchange = true;
			if (MainView.getCurrentCalendarPanel() != null && MainView.getCurrentCalendarPanel().getCalendarTabPanel() != null){
				MainView.getCurrentCalendarPanel().getCalendarTabPanel().setCalendarViewTitle(getScrollTitle());
			}
			scrollchange = false;
			QuickListModel.getQuickListModel().updateList();
			current = getDate();
			this.updateUI();
		}
	}

}
