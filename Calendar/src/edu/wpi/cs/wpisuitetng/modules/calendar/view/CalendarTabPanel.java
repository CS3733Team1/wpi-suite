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
import java.awt.Insets;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.calendarview.CalendarViewNextController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.calendarview.CalendarViewPreviousController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.calendarview.CalendarViewTodayController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.calendarview.DisplayDayViewController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.calendarview.DisplayMonthViewController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.calendarview.DisplayWeekViewController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.calendarview.DisplayYearViewController;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.ICalendarView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.MonthCalendarView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.WeekCalendarView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.YearCalendarView;
<<<<<<< HEAD
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day.DayCalendarPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day.DayView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.week.WeekCalendarPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.week.WeekView;
=======
>>>>>>> origin/dev
import edu.wpi.cs.wpisuitetng.modules.calendar.view.category.CategoryTabPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.commitment.CommitmentListPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.event.EventListPanel;

public class CalendarTabPanel extends JPanel {
	private JButton prevButton, homeButton, nextButton;
	private JButton yearViewButton, monthViewButton, weekViewButton, dayViewButton;

	private JPanel calendarViewPanel;
	
	private CommitmentListPanel commitmentListPanel;
	private EventListPanel eventListPanel;
	
	private ICalendarView calendarView;
	private DayCalendarPanel dayView;
	private WeekCalendarPanel weekView;
	private MonthCalendarView monthView;
	private YearCalendarView yearView;

	private JLabel calendarViewTitleLabel;
	
	private JTabbedPane filterCategoryTabbedPane;

	private CategoryTabPanel categoryTabPanel;
	
	public CalendarTabPanel() {
		this.setLayout(new BorderLayout());

		filterCategoryTabbedPane = new JTabbedPane();
		
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
			
			filterCategoryTabbedPane.addTab("Categories", new ImageIcon(ImageIO.read(getClass().getResource("/images/categories.png"))), 
					new CategoryTabPanel());
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

		JPanel calendarViewButtonsPanel = new JPanel();

		calendarViewButtonsPanel.add(yearViewButton);
		calendarViewButtonsPanel.add(monthViewButton);
		calendarViewButtonsPanel.add(weekViewButton);
		calendarViewButtonsPanel.add(dayViewButton);
		calendarViewToolBar.add(p2, BorderLayout.WEST);
		calendarViewToolBar.add(calendarViewButtonsPanel, BorderLayout.EAST);

		
		calendarViewPanel = new JPanel();
		add(calendarViewToolBar, BorderLayout.NORTH);

		add(calendarViewPanel, BorderLayout.CENTER);

		commitmentListPanel = new CommitmentListPanel();
		eventListPanel = new EventListPanel();		
		
		calendarViewPanel.setLayout(new MigLayout("fill",
				"[grow,push][]", 
				"[grow,push][]"));
		calendarViewPanel.add(commitmentListPanel, 		"cell 1 0, width 250:300:350, grow");
		calendarViewPanel.add(filterCategoryTabbedPane, "cell 1 1, width 250:300:350, grow");
		
		
<<<<<<< HEAD
		dayView = new DayCalendarPanel();
		weekView = new WeekCalendarPanel();
=======
		dayView = new DayView();
		weekView = new WeekView();
>>>>>>> origin/dev
		monthView = new MonthCalendarView();
		yearView = new YearCalendarView();

		// Set the default view to month view
		calendarView = monthView;
		calendarViewPanel.add(monthView, "width 1000, dock west, grow");

		this.setCalendarViewTitle(monthView.getTitle());

	}

	public ICalendarView getCalendarView(){
		return calendarView;
	}

	public void refreshCalendarView(){
		calendarViewPanel.invalidate();
		calendarViewPanel.repaint();
	}

	public void resetSelection() {
		commitmentListPanel.getCommitmentList().clearSelection();
		eventListPanel.getEventList().clearSelection();
	}

	public List<Commitment> getSelectedCommitmentList(){
		return commitmentListPanel.getCommitmentList().getSelectedValuesList();
	}

	public List<Event> getSelectedEventList(){
		return eventListPanel.getEventList().getSelectedValuesList();
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
		if(!(calendarView instanceof DayCalendarPanel)) {
			calendarViewPanel.remove((Component)calendarView);
			calendarView = dayView;
			calendarViewPanel.add(dayView, "width 1000, cell 0 0, span 1 2, grow");

			this.setCalendarViewTitle(dayView.getTitle());
			this.refreshCalendarView();
		}
	}
	public void displayWeekView() {
		if(!(calendarView instanceof WeekCalendarView)){
			calendarViewPanel.remove((Component)calendarView);
			calendarView = weekView;
			calendarViewPanel.add(weekView, "width 1000, cell 0 0, span 1 2, grow");

			this.setCalendarViewTitle(weekView.getTitle());
			this.refreshCalendarView();
		}
	}

	public void displayMonthView() {
		if(!(calendarView instanceof MonthCalendarView)) {
			calendarViewPanel.remove((Component)calendarView);
			calendarView = monthView;
			calendarViewPanel.add(monthView, "width 1000, cell 0 0, span 1 2, grow");

			this.setCalendarViewTitle(monthView.getTitle());
			this.refreshCalendarView();
		}
	}

	public void displayYearView() {
		if(!(calendarView instanceof YearCalendarView)){
			calendarViewPanel.remove((Component)calendarView);
			calendarView = yearView;
			calendarViewPanel.add(yearView, "width 1000, cell 0 0, span 1 2, grow");

			this.setCalendarViewTitle(yearView.getTitle());
			this.refreshCalendarView();
		}
	}
}
