/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.week2;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.FilteredCommitmentsListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarTabPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.ICalendarView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day2.DayMultiScrollPane;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day2.MultidayEventView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.week.WeekNamePanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.week.WeekView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.CalendarUtils;

/**
 * The second level of the week view hierarchy. Holds the WeekCalendarLayerPane and the WeekCalendarScrollPane.
 */

public class WeekCalendar extends JPanel implements ICalendarView, ListDataListener, ComponentListener, MouseListener {

	private WeekCalendarScrollPane weekscroll;
	private WeekHolderPanel weeklayer;
	private MultidayEventWeekView multiview;
	private WeekMultiScrollPane mscroll;
	private JPanel weektitle;
	private List<JPanel> weekpanel;
	List<JLabel> weekdays;
	private boolean isDisplayAbrrWeekDayNames;
	private int todayIndex;
	private int currentDay; 
	
	public WeekCalendar(){
		
		this.setLayout(new MigLayout("fill, insets 0"));
		
		weeklayer = new WeekHolderPanel();
		weekpanel = new LinkedList<JPanel>();
		weektitle = new JPanel(new MigLayout("fill, insets 0","0[10%]5[14%]5[14%]5[14%]5[14%]5[14%]5[14%]5[14%]0[45]0" ));
		
		JPanel time = new JPanel(new MigLayout("fill"));
		time.add(new JLabel(""), "grow, aligny bottom");
		time.setBackground(Color.white);
		
		weektitle.add(time, "aligny center, w 5000, grow, wmin 0");
		weekdays = new ArrayList<JLabel>();
		
		isDisplayAbrrWeekDayNames = true;
		todayIndex = 0;
		currentDay = 0; 
		
		weekscroll = new WeekCalendarScrollPane(weeklayer);
		for(int days = 1; days < 8; days++){
			WeekNamePanel weekName = new WeekNamePanel();
			weekName.setDate(weeklayer.getCalendarDate());
			System.out.println(weeklayer.getCalendarDate());
			JLabel label = new JLabel(CalendarUtils.weekNamesAbbr[days-1]);
			label.setFont(new Font(label.getName(), Font.BOLD, 14));
			weekdays.add(label);
			weekName.add(label,"grow, aligny bottom");
			if(days == 1 || days == 7)
				label.setForeground(CalendarUtils.timeColor);
			
	
			weekName.setBackground(Color.white);
			weekName.addMouseListener(this);
			weektitle.add(weekName, "aligny bottom, w 5000, grow, wmin 0");
			weekpanel.add(weekName);
		}
		JPanel space = new JPanel(new MigLayout("fill, insets 0"));
		weektitle.add(space);
		
		JPanel filler = new JPanel(new MigLayout("fill, insets 0"));
		filler.add(weektitle, "growx, wrap, wmin 0");
		this.add(filler, "growx, wrap, wmin 0");
		
		multiview = new MultidayEventWeekView(weeklayer.getMultiDayEvents(), weeklayer.getDayViewDate());
		
		mscroll = new WeekMultiScrollPane(multiview);
		this.add(mscroll, "grow, wrap, hmin 80, hmax 80");
		mscroll.getViewport().repaint();
		multiview.repaint();
		mscroll.updateUI();
		
		this.add(weekscroll, "grow");
		
		FilteredCommitmentsListModel.getFilteredCommitmentsListModel().addListDataListener(this);
		//DisplayCommitments();
		
		int end = weekscroll.getVerticalScrollBar().getMaximum();
		weekscroll.getVerticalScrollBar().setValue(end * 3 / 4);
		
		repaint();
	}
	
	public void rebuildDays(){
		weekdays.clear();
		weektitle.removeAll();
		weekpanel = new LinkedList<JPanel>();
		
		JPanel time = new JPanel(new MigLayout("fill"));
		time.add(new JLabel(""), "grow, aligny bottom");
		time.setBackground(Color.white);
		
		weektitle.add(time, "aligny center, w 5000, grow, wmin 0");
		todayIndex = 0;
		for(int days = 1; days < 8; days++){
			WeekNamePanel weekName = new WeekNamePanel();
			weekName.setDate(weeklayer.getCalendarDate());
			System.out.println(weeklayer.getCalendarDate());
			JLabel label = new JLabel(CalendarUtils.weekNamesAbbr[days-1]);
			label.setFont(new Font(label.getName(), Font.BOLD, 14));
			weekdays.add(label);
			weekName.add(label,"grow, aligny bottom");
			if(days == 1 || days == 7)
				label.setForeground(CalendarUtils.timeColor);
			
			
			weekName.setBorder(new MatteBorder(0, 0, 5, 0, CalendarUtils.timeColor));
			weekName.setBackground(Color.white);
			weekName.addMouseListener(this);
			weektitle.add(weekName, "aligny center, w 5000, grow, wmin 0");
			weekpanel.add(weekName);
		}
	}

	public void updateWeekHeader(){
		rebuildDays();
	}
	 
	public void repaint(){
		if (weeklayer != null){
			weeklayer.reSize(this.getWidth() - (weekscroll.getVerticalScrollBar().getWidth()*3));
		}
		if (multiview != null){
			multiview.reSize(this.getWidth() - (weekscroll.getVerticalScrollBar().getWidth()*2));
			updateMultiDay();
		}
		super.repaint();
	}
	
	public void updateMultiDay(){
		multiview.updateMultiDay(weeklayer.getMultiDayEvents(), weeklayer.getDayViewDate());
	}
	
	public Date getWeekStart()
	{
		return weeklayer.getDate(1);
	}
	/*
	 * Changes the week day names to abbreviations if the panel is too small
	 */
	private void updateLayout() {
		if(this.getWidth() <= 900 && !isDisplayAbrrWeekDayNames) {
			isDisplayAbrrWeekDayNames = true;
			for(int i = 0; i < weekdays.size(); i++)
				weekdays.get(i).setText(CalendarUtils.weekNamesAbbr[i]);
		} else if(this.getWidth() > 900 && isDisplayAbrrWeekDayNames) {
			isDisplayAbrrWeekDayNames = false;
			for(int i = 0; i < weekdays.size(); i++)
				weekdays.get(i).setText(CalendarUtils.weekNames[i]);
		}
	}
	
	
	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		int end = weekscroll.getVerticalScrollBar().getMaximum();
		weekscroll.getVerticalScrollBar().setValue(3*end/8);
		
		return weeklayer.getTitle();
	}

	@Override
	public void next() {
		// TODO Auto-generated method stub
		weeklayer.next();
		updateMultiDay();
		repaint();
	}

	@Override
	public void previous() {
		// TODO Auto-generated method stub
		weeklayer.previous();
		updateMultiDay();
		repaint();
	}

	@Override
	public void today() {
		// TODO Auto-generated method stub
		weeklayer.today();
		updateMultiDay();
		repaint();
	}

	@Override
	public void intervalAdded(ListDataEvent e) {
		updateMultiDay();
	}

	@Override
	public void intervalRemoved(ListDataEvent e) {
		updateMultiDay();
	}

	@Override
	public void contentsChanged(ListDataEvent e) {
		updateMultiDay();
	}

	@Override
	public void viewDate(Calendar date) {
		weeklayer.viewDate(date);
		updateMultiDay();
		repaint();
	}
	
	@Override
	public void componentResized(ComponentEvent e) {
		this.updateLayout();
	}
	
	// Unused
	@Override
	public void componentHidden(ComponentEvent e) {}
	@Override
	public void componentMoved(ComponentEvent e) {}
	@Override
	public void componentShown(ComponentEvent e) {}

	public void mouseClicked(MouseEvent e) {
		if(e.getClickCount() == 2) {
			WeekNamePanel weekName = (WeekNamePanel)e.getSource();
			Calendar clickedDay = weekName.getDate();
			System.out.println(clickedDay);
			if(this.getParent().getParent() != null)
			{
				CalendarTabPanel tab = (CalendarTabPanel)(this.getParent().getParent());
				tab.displayDayView();
				tab.setCalendarViewDate(clickedDay);
			}
		}
	}

	//Unused
	@Override
	public void mouseEntered(MouseEvent arg0) {}
	@Override
	public void mouseExited(MouseEvent arg0) {}
	@Override
	public void mousePressed(MouseEvent arg0) {}
	@Override
	public void mouseReleased(MouseEvent arg0) {}
}
