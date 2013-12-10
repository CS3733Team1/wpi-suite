/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.week;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.FilteredCommitmentsListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.ICalendarView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.CalendarUtils;

/**
 * The second level of the week view hierarchy. Holds the WeekCalendarLayerPane and the WeekCalendarScrollPane.
 */

public class WeekCalendarPanel extends JPanel implements ICalendarView, ListDataListener, ComponentListener {

	private WeekCalendarScrollPane weekscroll;
	private WeekCalendarLayerPane weeklayer;
	private JPanel weektitle;
	private List<JPanel> weekpanel;
	List<JLabel> weekdays;
	private boolean isDisplayAbrrWeekDayNames;
	private int todayIndex;
	private int currentDay; 
	
	public WeekCalendarPanel(){
		
		this.setLayout(new MigLayout("fill, insets 0"));
		
		weeklayer = new WeekCalendarLayerPane();
		weekpanel = new LinkedList<JPanel>();
		weektitle = new JPanel(new MigLayout("fill, insets 0", "0[8.66%]3[12.312%]3[12.312%]3[12.312%]3[12.312%]3[12.312%]3[12.312%]3[12.312%]0[45]0"));
		
		JPanel time = new JPanel(new MigLayout("fill"));
		time.add(new JLabel(""), "grow, aligny bottom");
		time.setBackground(Color.white);
		
		weektitle.add(time, "aligny center, w 5000, grow");
		weekdays = new ArrayList<JLabel>();
		
		isDisplayAbrrWeekDayNames = true;
		todayIndex = 0;
		currentDay = 0; 
		
		weekscroll = new WeekCalendarScrollPane(weeklayer);
		for(int days = 1; days < 8; days++){
			JPanel weekName = new JPanel(new MigLayout("fill, insets 0"));
			JLabel label = new JLabel(CalendarUtils.weekNamesAbbr[days-1]);
			label.setFont(new Font(label.getName(), Font.BOLD, 14));
			weekdays.add(label);
			weekName.add(label,"grow, aligny bottom");
			if(days == 1 || days == 7)
				label.setForeground(CalendarUtils.timeColor);
			
			WeekView week = weeklayer.getWeek();
			if(week.isToday())
			{
				todayIndex = week.getIndex();
				if(days == todayIndex)
				{
					label.setForeground(CalendarUtils.thatBlue);
				}
			}
			
	
			weekName.setBackground(Color.white);
			weektitle.add(weekName, "aligny bottom, w 5000, grow");
			weekpanel.add(weekName);
		}
		JPanel space = new JPanel(new MigLayout("fill, insets 0"));
		weektitle.add(space);
		
		JPanel filler = new JPanel(new MigLayout("fill, insets 0"));
		filler.add(weektitle, "growx, wrap");
		this.add(filler, "growx, wrap");
		
		
		
		
		this.add(weekscroll, "grow");
		
		FilteredCommitmentsListModel.getFilteredCommitmentsListModel().addListDataListener(this);
		DisplayCommitments();
		
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
		
		weektitle.add(time, "aligny center, w 5000, grow");
		todayIndex = 0;
		for(int days = 1; days < 8; days++){
			JPanel weekName = new JPanel(new MigLayout("fill"));
			WeekView week = weeklayer.getWeek();
			JLabel label = new JLabel(CalendarUtils.weekNamesAbbr[days-1] + " " + week.getDate(days));
			label.setFont(new Font(label.getName(), Font.BOLD, 14));
			weekdays.add(label);
			weekName.add(label,"grow, aligny bottom");
			if(days == 1 || days == 7)
				label.setForeground(CalendarUtils.timeColor);
			
			System.out.println(week.isToday());
			if(week.isToday())
			{
				todayIndex = week.getIndex();
				if(days == todayIndex){
					label.setForeground(CalendarUtils.thatBlue);
					weekName.setBorder(new MatteBorder(0, 0, 5, 0, CalendarUtils.thatBlue));
				}
				else
					weekName.setBorder(new MatteBorder(0, 0, 5, 0, CalendarUtils.selectionColor));
			}
			else
				weekName.setBorder(new MatteBorder(0, 0, 5, 0, CalendarUtils.timeColor));
			weekName.setBackground(Color.white);
			weektitle.add(weekName, "aligny center, w 5000, grow");
			weekpanel.add(weekName);
		}
	}
	
	public void DisplayCommitments(){
		List<List<Commitment>> foundyou = bananaSplit(weeklayer.getWeek().CommitmentsOnCalendar());
		for (int x = 0; x < 7; x++){
			if (foundyou.get(x).size() > 0){
				JPanel day = weekpanel.get(x);
				day.setBackground(Color.RED);
				StringBuilder bob = new StringBuilder();
				bob.append("<html>");
				int i=1;
				for (Commitment commit: foundyou.get(x)){
					bob.append("<p style='width:175px'>");
					if(i != 1)
					{
					bob.append("<br>");
					}
					bob.append("Commitment ");
					bob.append(new Integer(i).toString()+":");
					i++;
					bob.append("<br>");
					bob.append("<b>Name:</b> ");
					bob.append(commit.getName());
					bob.append("<br><b>Due Date:</b> ");
					bob.append(DateFormat.getInstance().format(commit.getDueDate()));
					if(commit.getCategory()!=null){
						bob.append("<br><b>Category: </b>");
						bob.append(commit.getCategory().getName());
					}
					if(commit.getDescription().length()>0){
						bob.append("<br><b>Description:</b> ");
						bob.append(commit.getDescription());
					}
					bob.append("</p>");
				}
				bob.append("</html>");
				day.setToolTipText(bob.toString());
			}
		}
		weektitle.repaint();
		weektitle.updateUI();
	}
	
	/**
	 * Splits The List Into Hourly Blocks
	 * @param thoseitems the list being split
	 * @return split list of hours
	 */
	public List<List<Commitment>> bananaSplit(List<Commitment> thoseitems){
		List<List<Commitment>> dalist = new LinkedList<List<Commitment>>();
		Date startdate = weeklayer.getWeek().getStart();
		
		for (int x = 0; x < 7; x++){
			List<Commitment> daylist = new LinkedList<Commitment>();
			for (Commitment commit: thoseitems){
				Date commitdate = new Date(commit.getDueDate().getYear(), commit.getDueDate().getMonth(), commit.getDueDate().getDate());
				if (commitdate.equals(startdate)){
					daylist.add(commit);
				}
			}
			startdate = new Date(startdate.getYear(), startdate.getMonth(), startdate.getDate()+1);
			dalist.add(daylist);
		}
		return dalist;
	}

	public void updateWeekHeader(){
		rebuildDays();
		DisplayCommitments();
	}

	
	public void paint(Graphics g){
		if (weeklayer != null){
			weeklayer.reSize(this.getWidth() - (weekscroll.getVerticalScrollBar().getWidth()*3));
		}
		super.paint(g);
	}
	 
	public void repaint(){
		if (weeklayer != null){
			weeklayer.reSize(this.getWidth() - (weekscroll.getVerticalScrollBar().getWidth()*3));
		}
		super.repaint();
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
		updateWeekHeader();
		repaint();
	}

	@Override
	public void previous() {
		// TODO Auto-generated method stub
		weeklayer.previous();
		updateWeekHeader();
		repaint();
	}

	@Override
	public void today() {
		// TODO Auto-generated method stub
		weeklayer.today();
		updateWeekHeader();
		repaint();
	}

	@Override
	public void intervalAdded(ListDataEvent e) {
		rebuildDays();
		DisplayCommitments();
	}

	@Override
	public void intervalRemoved(ListDataEvent e) {
		rebuildDays();
		DisplayCommitments();
	}

	@Override
	public void contentsChanged(ListDataEvent e) {
		rebuildDays();
		DisplayCommitments();
	}

	@Override
	public void viewDate(Calendar date) {
		weeklayer.viewDate(date.getTime());
		updateWeekHeader();
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
}
