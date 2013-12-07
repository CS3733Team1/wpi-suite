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

import java.awt.Font;
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
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.ICalendarView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day.DayCalendarPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.month.MonthCalendarView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.week.WeekCalendarPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.year.YearCalendarView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.category.CategoryTabPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.commitment.CommitmentSubTabPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.filter.FilterTabPanel;

public class CalendarTabPanel extends JPanel {
	private JButton personalCalButton, teamCalButton, bothCalButton;
	private JButton prevButton, homeButton, nextButton;
	private JButton yearViewButton, monthViewButton, weekViewButton, dayViewButton;

	private JPanel calendarViewPanel;
	
	private ICalendarView calendarView;
	private DayCalendarPanel dayView;
	private WeekCalendarPanel weekView;
	private MonthCalendarView monthView;
	private YearCalendarView yearView;

	private JLabel calendarTitleLabel;
	
	private JTabbedPane filterCategoryTabbedPane;
	
	private CommitmentSubTabPanel commitmentSubTabPanel;
	
	public CalendarTabPanel(CalendarPanel calendarPanel) {
		this.setLayout(new MigLayout("fill"));

		filterCategoryTabbedPane = new JTabbedPane();
		filterCategoryTabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
		
		commitmentSubTabPanel = new CommitmentSubTabPanel(calendarPanel);
		
		personalCalButton = new JButton("Personal");
		teamCalButton = new JButton("Team");
		bothCalButton = new JButton("Both");
		
		try {
			prevButton = new JButton(new ImageIcon(ImageIO.read(getClass().getResource("/images/previous.png"))));
			homeButton = new JButton("Today");
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
			
			filterCategoryTabbedPane.addTab("Filters", new ImageIcon(ImageIO.read(getClass().getResource("/images/filters.png"))), new FilterTabPanel());
		} catch (IOException e) {}

		prevButton.setMargin(new Insets(0, 0, 0, 0));
		nextButton.setMargin(new Insets(0, 0, 0, 0));

		prevButton.addActionListener(new CalendarViewPreviousController(this));
		homeButton.addActionListener(new CalendarViewTodayController(this));
		nextButton.addActionListener(new CalendarViewNextController(this));
		
		dayViewButton.addActionListener(new DisplayDayViewController(this));
		weekViewButton.addActionListener(new DisplayWeekViewController(this));
		monthViewButton.addActionListener(new DisplayMonthViewController(this));
		yearViewButton.addActionListener(new DisplayYearViewController(this));

		calendarTitleLabel = new JLabel();
		calendarTitleLabel.setFont(new Font(calendarTitleLabel.getFont().getName(), calendarTitleLabel.getFont().getStyle(), 16));

		JPanel topButtonPanel = new JPanel(new MigLayout("fill, insets 0 n 0 n", "[33.3333%][33.3333%][33.3333%]", "[][]"));
		
		topButtonPanel.add(yearViewButton,		"cell 0 0, center, span 3");
		topButtonPanel.add(monthViewButton,		"cell 0 0");
		topButtonPanel.add(weekViewButton,		"cell 0 0");
		topButtonPanel.add(dayViewButton,		"cell 0 0");

		topButtonPanel.add(prevButton,			"cell 0 1, align left");
		topButtonPanel.add(homeButton,			"cell 0 1");
		topButtonPanel.add(nextButton,			"cell 0 1");
		
		topButtonPanel.add(personalCalButton,	"cell 1 1, center");
		topButtonPanel.add(teamCalButton,		"cell 1 1");
		topButtonPanel.add(bothCalButton,		"cell 1 1");
		
		topButtonPanel.add(calendarTitleLabel,	"cell 2 1, align right");
		
		this.add(topButtonPanel, "growx");
		
		this.add(filterCategoryTabbedPane, "grow, span 1 2, wrap");
		
		calendarViewPanel = new JPanel(new MigLayout("fill"));
		this.add(calendarViewPanel, "grow, push");
		
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
		calendarTitleLabel.setText(title);
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
			calendarViewPanel.removeAll();
			calendarView = dayView;
			calendarViewPanel.add(dayView, "w 5000, h 5000");

			this.setCalendarViewTitle(dayView.getTitle());
			this.refreshCalendarView();
		}
	}
	public void displayWeekView() {
		if(!(calendarView instanceof WeekCalendarPanel)){
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
