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

import java.awt.Insets;
import java.io.IOException;
import java.util.ArrayList;
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
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.DayCalendarView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.ICalendarView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.MonthCalendarView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.WeekCalendarView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.YearCalendarView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day.DayCalendarPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.week.WeekCalendarPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.category.CategoryTabPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.commitment.CommitmentSubTabPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.filter.FilterTabPanel;

public class CalendarTabPanel extends JPanel {
	private JButton prevButton, homeButton, nextButton;
	private JButton yearViewButton, monthViewButton, weekViewButton, dayViewButton;

	private JPanel calendarViewPanel;
	
	private ICalendarView calendarView;
	private DayCalendarPanel dayView;
	private WeekCalendarPanel weekView;
	private MonthCalendarView monthView;
	private YearCalendarView yearView;

	private JLabel calendarViewTitleLabel;
	
	private JTabbedPane filterCategoryTabbedPane;
	
	private CommitmentSubTabPanel commitmentSubTabPanel;
	
	public CalendarTabPanel() {
		this.setLayout(new MigLayout());

		filterCategoryTabbedPane = new JTabbedPane();
		
		commitmentSubTabPanel = new CommitmentSubTabPanel();
		
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
			
			filterCategoryTabbedPane.addTab("Commitments", new ImageIcon(ImageIO.read(getClass().getResource("/images/commitment.png"))), 
					commitmentSubTabPanel);
			
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

		this.add(prevButton, "split 4");
		this.add(homeButton);
		this.add(nextButton);
		this.add(calendarViewTitleLabel);

		this.add(yearViewButton, "align right, split 4");
		this.add(monthViewButton);
		this.add(weekViewButton);
		this.add(dayViewButton);

		this.add(filterCategoryTabbedPane, "growy, wmin 272, span 1 2, wrap");
		
		calendarViewPanel = new JPanel(new MigLayout("fill"));
		this.add(calendarViewPanel, "grow, push, span 2");
		
		dayView = new DayCalendarPanel();
		weekView = new WeekCalendarPanel();
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

	public void resetSelection() {
		commitmentSubTabPanel.getCommitmentsList().clearSelection();
	}

	public List<Commitment> getSelectedCommitmentList(){
		if(filterCategoryTabbedPane.getSelectedComponent() instanceof CommitmentSubTabPanel)
			return commitmentSubTabPanel.getCommitmentsList().getSelectedValuesList();
		else return new ArrayList<Commitment>();
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
		System.out.println(filterCategoryTabbedPane.getWidth());
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
			calendarViewPanel.removeAll();
			calendarView = dayView;
			calendarViewPanel.add(dayView, "w 5000, h 5000");

			this.setCalendarViewTitle(dayView.getTitle());
			this.refreshCalendarView();
		}
	}
	public void displayWeekView() {
		if(!(calendarView instanceof WeekCalendarView)){
			calendarViewPanel.removeAll();
			calendarView = weekView;
			calendarViewPanel.add(weekView, "w 5000, h 5000");

			this.setCalendarViewTitle(weekView.getTitle());
			this.refreshCalendarView();
		}
	}

	public void displayMonthView() {
		if(!(calendarView instanceof MonthCalendarView)) {
			calendarViewPanel.removeAll();
			calendarView = monthView;
			calendarViewPanel.add(monthView, "w 5000, h 5000");

			this.setCalendarViewTitle(monthView.getTitle());
			this.refreshCalendarView();
		}
	}

	public void displayYearView() {
		if(!(calendarView instanceof YearCalendarView)){
			calendarViewPanel.removeAll();
			calendarView = yearView;
			calendarViewPanel.add(yearView, "w 5000, h 5000");

			this.setCalendarViewTitle(yearView.getTitle());
			this.refreshCalendarView();
		}
	}
}
