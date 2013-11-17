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
import java.awt.Color;
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

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.calendarview.CalendarViewNextController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.calendarview.CalendarViewPreviousController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.calendarview.CalendarViewTodayController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.calendarview.DisplayDayViewController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.calendarview.DisplayMonthViewController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.calendarview.DisplayWeekViewController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.calendarview.DisplayYearViewController;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.CommitmentListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.DayCalendarView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.ICalendarView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.MonthCalendarView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.WeekCalendarView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.YearCalendarView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.commitment.CommitmentListPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.event.EventListPanel;

public class CalendarTabPanel extends JPanel {
	private JButton prevButton, homeButton, nextButton;
	private JButton yearViewButton, monthViewButton, weekViewButton, dayViewButton;

	private JPanel calendarViewPanel;

	private CommitmentListPanel commitmentListPanel;
	private EventListPanel eventListPanel;

	private JScrollPane currentViewScrollPane;

	private ICalendarView calendarView;
	private DayCalendarView dayView;
	private WeekCalendarView weekView;
	private MonthCalendarView monthView;
	private YearCalendarView yearView;

	private JLabel calendarViewTitleLabel;

	public CalendarTabPanel() {
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
		
		p3.setBackground(Color.orange);
		p3.add(prevButton);
		p3.add(homeButton);
		p3.add(nextButton);

		p2.add(p3, BorderLayout.WEST);
		p2.setBackground(Color.red);
		p2.add(calendarViewTitleLabel, BorderLayout.CENTER);
//		p2.setPreferredSize(new Dimension(250, 1));

		JPanel calendarViewButtonsPanel = new JPanel();

		calendarViewButtonsPanel.add(yearViewButton);
		calendarViewButtonsPanel.add(monthViewButton);
		calendarViewButtonsPanel.add(weekViewButton);
		calendarViewButtonsPanel.add(dayViewButton);
		calendarViewButtonsPanel.setBackground(Color.yellow);
		calendarViewToolBar.add(p2, BorderLayout.WEST);
		calendarViewToolBar.add(calendarViewButtonsPanel, BorderLayout.EAST);

		
		calendarViewPanel = new JPanel(new BorderLayout());
		calendarViewPanel.add(calendarViewToolBar, BorderLayout.NORTH);

		add(calendarViewPanel, BorderLayout.CENTER);

		commitmentListPanel = new CommitmentListPanel();
		eventListPanel = new EventListPanel();

//		add(eventListPanel, BorderLayout.WEST);
//		add(commitmentListPanel, BorderLayout.EAST);

		dayView = new DayCalendarView();
		weekView = new WeekCalendarView();
		monthView = new MonthCalendarView();
		yearView = new YearCalendarView();

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
		eventListPanel.getEventList().clearSelection();
	}

	public JList<Object> getEventJList(){
		return eventListPanel.getEventList();
	}

	public void setCalendarViewTitle(String title) {
		calendarViewTitleLabel.setText(title);
	}

	public void setCalendarViewNext() {
		calendarView.next();
		this.setCalendarViewTitle(calendarView.getTitle());
		this.refreshCalendarView();
	}

	public void setCalendarViewToday() {
		calendarView.today();
		this.setCalendarViewTitle(calendarView.getTitle());
		this.refreshCalendarView();
	}

	public void setCalendarViewPrevious() {
		calendarView.previous();
		this.setCalendarViewTitle(calendarView.getTitle());
		this.refreshCalendarView();
	}

	public void displayDayView() {
		if(!(calendarView instanceof DayCalendarView)){
			if (currentViewScrollPane != null) calendarViewPanel.remove(currentViewScrollPane);

			calendarView = dayView;
			currentViewScrollPane = new JScrollPane(dayView);
			calendarViewPanel.add(currentViewScrollPane, BorderLayout.CENTER);

			dayView.setVisible(true);
			currentViewScrollPane.setVisible(true);

			this.setCalendarViewTitle(dayView.getTitle());
			this.refreshCalendarView();
		}
	}

	public void displayWeekView() {
		if(!(calendarView instanceof WeekCalendarView)){
			if (currentViewScrollPane != null) calendarViewPanel.remove(currentViewScrollPane);

			calendarView = weekView;
			currentViewScrollPane = new JScrollPane(weekView);
			calendarViewPanel.add(currentViewScrollPane, BorderLayout.CENTER);

			weekView.setVisible(true);
			currentViewScrollPane.setVisible(true);

			this.setCalendarViewTitle(weekView.getTitle());
			this.refreshCalendarView();
		}
	}

	public void displayMonthView() {
		if(!(calendarView instanceof MonthCalendarView)){
//			if (currentViewScrollPane != null) calendarViewPanel.remove(currentViewScrollPane);

			
			JPanel viewTest = new JPanel();
			viewTest.setBackground(Color.green);
//			viewTest.setPreferredSize(new Dimension(800,800));
			viewTest.setLayout(new MigLayout("fill",
					"[grow,push][][]", "[grow,push][][]"));
			
			
			
			
			commitmentListPanel = new CommitmentListPanel();
			eventListPanel = new EventListPanel();	
			
			calendarView = monthView;
//			currentViewScrollPane = new JScrollPane(monthView, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

			
			viewTest.add((Component) calendarView, "width 1000, dock west, grow");
			viewTest.add(commitmentListPanel, "width 250:300:350, dock east, grow");
		
			
			
//			calendarViewPanel.add(currentViewScrollPane, BorderLayout.CENTER);
			calendarViewPanel.add((Component)viewTest, BorderLayout.CENTER);
			monthView.setVisible(true);
//			currentViewScrollPane.setVisible(true);

			this.setCalendarViewTitle(monthView.getTitle());
			this.refreshCalendarView();
		}
	}

	public void displayYearView() {
		if(!(calendarView instanceof YearCalendarView)){
			if (currentViewScrollPane != null) calendarViewPanel.remove(currentViewScrollPane);

			calendarView = yearView;
			currentViewScrollPane = new JScrollPane(yearView);
			calendarViewPanel.add(currentViewScrollPane, BorderLayout.CENTER);

			yearView.setVisible(true);
			currentViewScrollPane.setVisible(true);

			this.setCalendarViewTitle(yearView.getTitle());
			this.refreshCalendarView();
		}
	}
}
