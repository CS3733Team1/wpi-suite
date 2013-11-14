/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import edu.wpi.cs.wpisuitetng.modules.calendar.controller.calendarview.CalendarViewNextController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.calendarview.CalendarViewPreviousController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.calendarview.CalendarViewTodayController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.calendarview.DisplayDayViewController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.calendarview.DisplayMonthViewController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.calendarview.DisplayWeekViewController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.calendarview.DisplayYearViewController;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.MainModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.ICalendarView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.MonthCalendarView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.commitment.CommitmentListPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.event.EventListPanel;

public class CalendarTabPanel extends JPanel{
	private MainModel model;

	private JButton prevButton, homeButton, nextButton;
	private JButton yearViewButton, monthViewButton, weekViewButton, dayViewButton;

	private JPanel calendarViewPanel;

	private CommitmentListPanel commitmentListPanel;
	private EventListPanel eventListPanel;

	private Component currentview;
	private ICalendarView calendarView;
	private MonthCalendarView monthview;

	private JLabel calendarViewTitleLabel;

	public CalendarTabPanel(MainModel model){
		this.model = model;

		this.setLayout(new BorderLayout());

		try {
			prevButton = new JButton(new ImageIcon(ImageIO.read(getClass().getResource("/images/previous.png"))));
			homeButton = new JButton(new ImageIcon(ImageIO.read(getClass().getResource("/images/home.png"))));
			nextButton = new JButton(new ImageIcon(ImageIO.read(getClass().getResource("/images/next.png"))));

			dayViewButton = new JButton("Day",
					new ImageIcon(ImageIO.read(getClass().getResource("/images/day_cal.png"))));
			weekViewButton = new JButton("Week",
					new ImageIcon(ImageIO.read(getClass().getResource("/images/week_cal.png"))));
			monthViewButton = new JButton("Month",
					new ImageIcon(ImageIO.read(getClass().getResource("/images/month_cal.png"))));
			yearViewButton = new JButton("Year",
					new ImageIcon(ImageIO.read(getClass().getResource("/images/year_cal.png"))));
		} catch (IOException e) {}
		
		homeButton.setMargin(new Insets(0, 0, 0, 0));
		prevButton.setMargin(new Insets(0, 0, 0, 0));
		nextButton.setMargin(new Insets(0, 0, 0, 0));
		
		prevButton.addActionListener(new CalendarViewPreviousController(this));
		nextButton.addActionListener(new CalendarViewNextController(this));
		homeButton.addActionListener(new CalendarViewTodayController(this));
		
		dayViewButton.addActionListener(new DisplayDayViewController(this));
		weekViewButton.addActionListener(new DisplayWeekViewController(this));
		monthViewButton.addActionListener(new DisplayMonthViewController(this));
		yearViewButton.addActionListener(new DisplayYearViewController(this));
		
		calendarViewTitleLabel = new JLabel();

		JPanel calendarViewToolBar = new JPanel(new BorderLayout());
		JPanel p2 = new JPanel(new BorderLayout());
		JPanel p3 = new JPanel();

		p3.add(prevButton);
		p3.add(homeButton);
		p3.add(nextButton);

		p2.add(p3, BorderLayout.WEST);
		p2.add(calendarViewTitleLabel, BorderLayout.CENTER);
		p2.setPreferredSize(new Dimension(200, 1));
		
		JPanel calendarViewButtonsPanel = new JPanel();
		
		calendarViewButtonsPanel.add(yearViewButton);
		calendarViewButtonsPanel.add(monthViewButton);
		calendarViewButtonsPanel.add(weekViewButton);
		calendarViewButtonsPanel.add(dayViewButton);

		calendarViewToolBar.add(p2, BorderLayout.WEST);
		calendarViewToolBar.add(calendarViewButtonsPanel, BorderLayout.CENTER);

		calendarViewPanel = new JPanel(new BorderLayout());
		calendarViewPanel.add(calendarViewToolBar, BorderLayout.NORTH);

		add(calendarViewPanel, BorderLayout.CENTER);

		commitmentListPanel = new CommitmentListPanel(model.getCommitmentModel());

		add(commitmentListPanel, BorderLayout.LINE_END);
		
		displayMonthView();
	}

	public ICalendarView getCalendarView(){
		return calendarView;
	}

	public void refreshCalendarView(){
		calendarViewPanel.invalidate();
		calendarViewPanel.repaint();
	}

	public JList<Object> getCommitmentJList(){
		return commitmentListPanel.getCommitmentList();
	}

	public void resetSelection() {
		commitmentListPanel.getCommitmentList().clearSelection();
		//eventListPanel.getEventList().clearSelection();
	}

	public JList<Object> getEventJList(){
		return eventListPanel.getEventList();
	}

	public void setCalendarViewTitle(String title) {
		calendarViewTitleLabel.setText(title);
	}

	public void setCalendarViewNext() {
		calendarView.next();;
	}

	public void setCalendarViewToday() {
		calendarView.today();
	}

	public void setCalendarViewPrevious() {
		calendarView.previous();
	}

	public void displayDayView() {
		// TODO Auto-generated method stub
	}

	public void displayWeekView() {
		// TODO Auto-generated method stub
	}
	
	public void displayMonthView(){
		if (monthview == null) monthview = new MonthCalendarView();
		if (currentview != null) calendarViewPanel.remove(currentview);

		calendarView = monthview;
		currentview = new JScrollPane(monthview);
		calendarViewPanel.add(currentview, BorderLayout.CENTER);

		monthview.setVisible(true);
		currentview.setVisible(true);

		this.setCalendarViewTitle(monthview.getTitle());
		this.refreshCalendarView();
	}

	public void displayYearView() {
		// TODO Auto-generated method stub
	}
}
