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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.DayCalendarView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.DayView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.ICalendarView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.MonthCalendarView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.WeekCalendarView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.WeekView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.YearCalendarView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.category.CategoryTabPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.commitment.CommitmentListPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.event.EventListPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.filter.FilterTabPanel;

public class CalendarTabPanel extends JPanel {
	private JButton prevButton, homeButton, nextButton;
	private JButton yearViewButton, monthViewButton, weekViewButton, dayViewButton;

	private JPanel calendarViewPanel;
	
	private CommitmentListPanel commitmentListPanel;
	private EventListPanel eventListPanel;
	
	private ICalendarView calendarView;
	private DayView dayView;
	private WeekView weekView;
	private MonthCalendarView monthView;
	private YearCalendarView yearView;

	private JLabel calendarViewTitleLabel;
	
	private JTabbedPane filterCategoryTabbedPane;
	
	public CalendarTabPanel() {
		this.setLayout(new MigLayout("insets 0", "[]", "[][]"));

		filterCategoryTabbedPane = new JTabbedPane();
		filterCategoryTabbedPane.setMinimumSize(new Dimension(230,0));
		filterCategoryTabbedPane.setPreferredSize(new Dimension(275,0));
		
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
			
			filterCategoryTabbedPane.addTab("Commitments", new ImageIcon(ImageIO.read(getClass().getResource("/images/categories.png"))), 
					new CommitmentListPanel());
			
			filterCategoryTabbedPane.addTab("Categories", new ImageIcon(ImageIO.read(getClass().getResource("/images/categories.png"))), 
					new CategoryTabPanel());
			
			filterCategoryTabbedPane.addTab("Filters", new ImageIcon(ImageIO.read(getClass().getResource("/images/filters.png"))), 
					new FilterTabPanel());
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

		JPanel calendarViewToolBar = new JPanel(new MigLayout("insets 0", "[][]", "[]"));
		JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p.add(prevButton);
		p.add(homeButton);
		p.add(nextButton);
		p.add(calendarViewTitleLabel);
		
		calendarViewToolBar.add(p, "cell 0 0, width 350");
		
		JPanel calendarViewButtonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		calendarViewButtonsPanel.add(yearViewButton);
		calendarViewButtonsPanel.add(monthViewButton);
		calendarViewButtonsPanel.add(weekViewButton);
		calendarViewButtonsPanel.add(dayViewButton);
		
		calendarViewToolBar.add(calendarViewButtonsPanel, "cell 1 0");

		
		calendarViewPanel = new JPanel();
		add(calendarViewToolBar, "cell 0 0");
		add(calendarViewPanel, "cell 0 1");

		commitmentListPanel = new CommitmentListPanel();
		eventListPanel = new EventListPanel();		
		
		calendarViewPanel.setLayout(new MigLayout("fill","[][]", "[]"));

		calendarViewPanel.add(filterCategoryTabbedPane, "cell 1 0, grow");
		//calendarViewPanel.add(commitmentListPanel, 		"cell 1 1, width 250:300:350");
		//calendarViewPanel.add(new CategoryListPanel(), 		"cell 1 0, width 250:300:350, grow");
		
		
		dayView = new DayView();
		weekView = new WeekView();
		monthView = new MonthCalendarView();
		yearView = new YearCalendarView();

		// Set the default view to month view
		calendarView = monthView;
		calendarViewPanel.add(monthView, "cell 0 0, grow, push");

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
		if(!(calendarView instanceof DayCalendarView)) {
			calendarViewPanel.remove((Component)calendarView);
			calendarView = dayView;
			calendarViewPanel.add(dayView, "width 1000, cell 0 0, grow");

			this.setCalendarViewTitle(dayView.getTitle());
			this.refreshCalendarView();
		}
	}
	public void displayWeekView() {
		if(!(calendarView instanceof WeekCalendarView)){
			calendarViewPanel.remove((Component)calendarView);
			calendarView = weekView;
			calendarViewPanel.add(weekView, "width 1000, cell 0 0, grow");

			this.setCalendarViewTitle(weekView.getTitle());
			this.refreshCalendarView();
		}
	}

	public void displayMonthView() {
		if(!(calendarView instanceof MonthCalendarView)) {
			calendarViewPanel.remove((Component)calendarView);
			calendarView = monthView;
			calendarViewPanel.add(monthView, "width 1000, cell 0 0, grow");

			this.setCalendarViewTitle(monthView.getTitle());
			this.refreshCalendarView();
		}
	}

	public void displayYearView() {
		if(!(calendarView instanceof YearCalendarView)){
			calendarViewPanel.remove((Component)calendarView);
			calendarView = yearView;
			calendarViewPanel.add(yearView, "width 1000, cell 0 0, grow");

			this.setCalendarViewTitle(yearView.getTitle());
			this.refreshCalendarView();
		}
	}
}
