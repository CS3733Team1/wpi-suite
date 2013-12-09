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

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.io.IOException;
import java.util.ArrayList;

import java.util.Collections;

import java.util.Calendar;

import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.TeamPesonalCheckBoxChangeListener;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.calendarview.CalendarViewNextController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.calendarview.CalendarViewPreviousController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.calendarview.CalendarViewTodayController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.calendarview.DisplayDayViewController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.calendarview.DisplayMonthViewController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.calendarview.DisplayWeekViewController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.calendarview.DisplayYearViewController;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.buttons.TransparentButton;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.buttons.TransparentButtonGroup;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.buttons.TransparentToggleButton;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.ICalendarView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day.DayCalendarPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.month.MonthCalendarView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.week.WeekCalendarPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.year.YearCalendarView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.category.CategoryTabPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.commitment.CommitmentSubTabPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.filter.FilterTabPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.quicklist.QuickListTabPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.CalendarUtils;

public class CalendarTabPanel extends JPanel {
	private JCheckBox personalCalCheckBox, teamCalCheckBox;
	
	private TransparentButton prevButton, homeButton, nextButton;
	
	private TransparentToggleButton yearViewButton, monthViewButton, weekViewButton, dayViewButton;
	private TransparentButtonGroup viewButtonGroup;
	
	private JPanel calendarViewPanel;
	
	private ICalendarView calendarView;
	private DayCalendarPanel dayView;
	private WeekCalendarPanel weekView;
	private MonthCalendarView monthView;
	private YearCalendarView yearView;

	private JLabel calendarTitleLabel;
	
	private JTabbedPane filterCategoryTabbedPane;
	
	private CommitmentSubTabPanel commitmentSubTabPanel;
	private QuickListTabPanel quickListTabPanel;
	
	
	public CalendarTabPanel(CalendarPanel calendarPanel) {
		this.setLayout(new MigLayout("fill"));
		this.setBackground(Color.WHITE);

		filterCategoryTabbedPane = new JTabbedPane();
		filterCategoryTabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
		
		commitmentSubTabPanel = new CommitmentSubTabPanel(calendarPanel);
		quickListTabPanel = new QuickListTabPanel(calendarPanel);
		
		personalCalCheckBox = new JCheckBox("Personal");
		teamCalCheckBox = new JCheckBox("Team");
		personalCalCheckBox.addItemListener(new TeamPesonalCheckBoxChangeListener());
		teamCalCheckBox.addItemListener(new TeamPesonalCheckBoxChangeListener());
		teamCalCheckBox.doClick();
		personalCalCheckBox.setSelected(true);
		
		personalCalCheckBox.setBackground(Color.WHITE);
		teamCalCheckBox.setBackground(Color.WHITE);
		
		personalCalCheckBox.setFocusable(false);
		teamCalCheckBox.setFocusable(false);
		
		try {
			prevButton = new TransparentButton(new ImageIcon(ImageIO.read(getClass().getResource("/images/previous.png"))));
			homeButton = new TransparentButton("Today");
			nextButton = new TransparentButton(new ImageIcon(ImageIO.read(getClass().getResource("/images/next.png"))));

			dayViewButton = new TransparentToggleButton("Day",
					new ImageIcon(ImageIO.read(getClass().getResource("/images/day_cal.png"))));
			
			weekViewButton = new TransparentToggleButton("Week",
					new ImageIcon(ImageIO.read(getClass().getResource("/images/week_cal.png"))));
			
			monthViewButton = new TransparentToggleButton("Month",
					new ImageIcon(ImageIO.read(getClass().getResource("/images/month_cal.png"))));
			
			yearViewButton = new TransparentToggleButton("Year",
					new ImageIcon(ImageIO.read(getClass().getResource("/images/year_cal.png"))));
			
			filterCategoryTabbedPane.addTab("Quick List", new ImageIcon(ImageIO.read(getClass().getResource("/images/quicklist.png"))), 
					quickListTabPanel);
			
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
		
		monthViewButton.setSelected(true);
		viewButtonGroup = new TransparentButtonGroup();
		viewButtonGroup.add(dayViewButton);
		viewButtonGroup.add(weekViewButton);
		viewButtonGroup.add(monthViewButton);
		viewButtonGroup.add(yearViewButton);
		
		dayViewButton.addActionListener(new DisplayDayViewController(this));
		weekViewButton.addActionListener(new DisplayWeekViewController(this));
		monthViewButton.addActionListener(new DisplayMonthViewController(this));
		yearViewButton.addActionListener(new DisplayYearViewController(this));
		
		calendarTitleLabel = new JLabel();
		calendarTitleLabel.setFont(new Font(calendarTitleLabel.getFont().getName(), Font.BOLD, 16));
		calendarTitleLabel.setForeground(CalendarUtils.titleNameColor);

		JPanel topButtonPanel = new JPanel(new MigLayout("fill, insets 0 n 0 n", "[33.3333%][33.3333%][33.3333%]", "[][]"));
		topButtonPanel.setBackground(Color.WHITE);
		
		topButtonPanel.add(yearViewButton,		"cell 0 0, center, span 3");
		topButtonPanel.add(monthViewButton,		"cell 0 0");
		topButtonPanel.add(weekViewButton,		"cell 0 0");
		topButtonPanel.add(dayViewButton,		"cell 0 0");

		topButtonPanel.add(prevButton,			"cell 0 1, align left");
		topButtonPanel.add(homeButton,			"cell 0 1");
		topButtonPanel.add(nextButton,			"cell 0 1");
		
		topButtonPanel.add(personalCalCheckBox,	"cell 1 1, center");
		topButtonPanel.add(teamCalCheckBox,		"cell 1 1");
		
		topButtonPanel.add(calendarTitleLabel,	"cell 2 1, align right");
		
		this.add(topButtonPanel, "growx");
		
		this.add(filterCategoryTabbedPane, "grow, span 1 2, wrap");
		
		calendarViewPanel = new JPanel(new MigLayout("fill"));
		calendarViewPanel.setBackground(Color.WHITE);
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
		quickListTabPanel.getCommitmentsList().clearSelection();
	}

	public List<Commitment> getSelectedCommitmentList(){
		if(filterCategoryTabbedPane.getSelectedComponent() instanceof CommitmentSubTabPanel)
			return commitmentSubTabPanel.getCommitmentsList().getSelectedValuesList();
		else if(filterCategoryTabbedPane.getSelectedComponent() instanceof QuickListTabPanel)
			return quickListTabPanel.getCommitmentsList().getSelectedValuesList();
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
	
	public void setCalendarViewDate(Calendar date) {
		calendarView.viewDate(date);
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
	/*
	 * Returns 0 for both unchecked
	 * returns 1 for Team checked
	 * returns 2 for Personal Checked
	 * returns 3 for Both Checked
	 */
	synchronized public int getTeamPersonalState(){
		int state = 0;// Default to both unchecked, the harmless filter
		if(teamCalCheckBox != null && teamCalCheckBox.isSelected())
		{
			System.out.println("Team Checkbox is selected");
			if (personalCalCheckBox != null && personalCalCheckBox.isSelected())
			{
				System.out.println("Personal checkbox also selected");
				return 3;
			}
			else 
			{
				return 1;
			}
		}
		else if (personalCalCheckBox != null && personalCalCheckBox.isSelected())
		{
			System.out.println("Only personal checkbox selected");
			return 2;
		}
		else
			return 0;
	}
}
