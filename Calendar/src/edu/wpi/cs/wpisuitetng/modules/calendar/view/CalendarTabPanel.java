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
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import edu.wpi.cs.wpisuitetng.modules.calendar.model.QuickListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.commitment.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.commitment.CommitmentListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.event.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.event.EventListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.scheduledevent.ScheduledEvent;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.buttons.TransparentButton;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.buttons.TransparentButtonGroup;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.buttons.TransparentToggleButton;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.ICalendarView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.SchedMouseListener;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day.DayCalendar;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.month.MonthCalendarView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.week.WeekCalendar;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.year.YearCalendarView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.category.CategoryTabPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.commitment.CommitmentSubTabPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.event.EventSubTabPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.filter.FilterTabPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.quicklist.QuickListTabPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.scheduledevent.ScheduledEventSubTabPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.CalendarUtils;


public class CalendarTabPanel extends JPanel {
	private final static Logger LOGGER = Logger.getLogger(CalendarTabPanel.class.getName());

	private JCheckBox personalCalCheckBox, teamCalCheckBox;

	private TransparentButton prevButton, homeButton, nextButton;

	private TransparentToggleButton yearViewButton, monthViewButton, weekViewButton, dayViewButton;
	private TransparentButtonGroup viewButtonGroup;

	private JPanel calendarViewPanel;

	static private ICalendarView calendarView;
	private DayCalendar dayView;
	private WeekCalendar weekView;
	private MonthCalendarView monthView;
	private YearCalendarView yearView;

	private JLabel calendarTitleLabel;

	private JTabbedPane subTabPane;

	private EventSubTabPanel eventSubTabPanel;
	private CommitmentSubTabPanel commitmentSubTabPanel;
	private QuickListTabPanel quickListTabPanel;
	private ScheduledEventSubTabPanel scheduledEventSubTabPanel;

	public CalendarTabPanel(CalendarPanel calendarPanel) {
		this.setLayout(new MigLayout("fill"));
		this.setBackground(Color.WHITE);

		subTabPane = new JTabbedPane();
		subTabPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
		subTabPane.setMinimumSize(new Dimension(330, 64));

		eventSubTabPanel = new EventSubTabPanel(calendarPanel);
		commitmentSubTabPanel = new CommitmentSubTabPanel(calendarPanel);
		quickListTabPanel = new QuickListTabPanel(calendarPanel);
		scheduledEventSubTabPanel = new ScheduledEventSubTabPanel(calendarPanel);

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

		ImageIcon prevIcon = new ImageIcon();
		ImageIcon nextIcon = new ImageIcon();
		ImageIcon dayIcon = new ImageIcon();
		ImageIcon weekIcon = new ImageIcon();
		ImageIcon monthIcon = new ImageIcon();
		ImageIcon yearIcon = new ImageIcon();
		ImageIcon quickListIcon = new ImageIcon();
		ImageIcon eventIcon = new ImageIcon();
		ImageIcon commitmentIcon = new ImageIcon();
		ImageIcon categoryIcon = new ImageIcon();
		ImageIcon filterIcon = new ImageIcon();
		ImageIcon scheduledEventIcon = new ImageIcon();

		try {
			prevIcon = new ImageIcon(ImageIO.read(getClass().getResource("/images/previous.png")));
			nextIcon = new ImageIcon(ImageIO.read(getClass().getResource("/images/next.png")));
			dayIcon = new ImageIcon(ImageIO.read(getClass().getResource("/images/day_cal.png")));
			weekIcon = new ImageIcon(ImageIO.read(getClass().getResource("/images/week_cal.png")));
			monthIcon = new ImageIcon(ImageIO.read(getClass().getResource("/images/month_cal.png")));
			yearIcon = new ImageIcon(ImageIO.read(getClass().getResource("/images/year_cal.png")));
			quickListIcon = new ImageIcon(ImageIO.read(getClass().getResource("/images/quicklist.png")));
			eventIcon = new ImageIcon(ImageIO.read(getClass().getResource("/images/event.png")));
			scheduledEventIcon = new ImageIcon(ImageIO.read(getClass().getResource("/images/scheduler_tabSize.png")));
			commitmentIcon = new ImageIcon(ImageIO.read(getClass().getResource("/images/commitment.png")));
			categoryIcon = new ImageIcon(ImageIO.read(getClass().getResource("/images/categories.png")));
			filterIcon = new ImageIcon(ImageIO.read(getClass().getResource("/images/filters.png")));
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Images not found.", e);
		} catch (IllegalArgumentException e){
			LOGGER.log(Level.WARNING, "Input == NULL!", e);
		}

		prevButton = new TransparentButton(prevIcon);
		homeButton = new TransparentButton("Today");
		nextButton = new TransparentButton(nextIcon);

		dayViewButton = new TransparentToggleButton("Day", dayIcon);
		dayViewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		weekViewButton = new TransparentToggleButton("Week", weekIcon);
		weekViewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		monthViewButton = new TransparentToggleButton("Month", monthIcon);
		monthViewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		yearViewButton = new TransparentToggleButton("Year", yearIcon);
		yearViewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

//		subTabPane.addTab("Scheduled Events", scheduledEventIcon, scheduledEventSubTabPanel);
		subTabPane.addTab("Quick List", quickListIcon, quickListTabPanel);
		subTabPane.addTab("Events", eventIcon, eventSubTabPanel);
		subTabPane.addTab("Commitments", commitmentIcon, commitmentSubTabPanel);
		subTabPane.addTab("Categories", categoryIcon, new CategoryTabPanel());
		subTabPane.addTab("Filters", filterIcon, new FilterTabPanel());

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

		JPanel topButtonPanel = new JPanel(new MigLayout("fill, insets 0 n 0 n", "[20%][60%][20%]", "[][]"));
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

		this.add(subTabPane, "grow, span 1 2, wrap");

		calendarViewPanel = new JPanel(new MigLayout("fill"));
		calendarViewPanel.setBackground(Color.WHITE);
		this.add(calendarViewPanel, "grow, push");

		dayView = new DayCalendar();
		weekView = new WeekCalendar();
		monthView = new MonthCalendarView();
		yearView = new YearCalendarView();

		displayMonthView();
	}

	public static ICalendarView getCalendarView(){
		return calendarView;
	}

	public void refreshCalendarView(){
		calendarViewPanel.invalidate();
		calendarViewPanel.repaint();
	}

	public List<Commitment> getSelectedCommitmentList() {
		List<Commitment> selectedCommitments =  new ArrayList<Commitment>();
		if(calendarView instanceof MonthCalendarView)
			selectedCommitments.addAll(((MonthCalendarView)calendarView).getSelectedCommitments());
		else if(calendarView instanceof DayCalendar)
			selectedCommitments.addAll(SchedMouseListener.getSelectedCommitments());
		else if(calendarView instanceof WeekCalendar)
			selectedCommitments.addAll(SchedMouseListener.getSelectedCommitments());

		if(subTabPane.getSelectedComponent() instanceof CommitmentSubTabPanel)
			selectedCommitments.addAll(commitmentSubTabPanel.getSelectedCommitments());
		else if(subTabPane.getSelectedComponent() instanceof QuickListTabPanel)
		selectedCommitments.addAll(quickListTabPanel.getSelectedCommitments());

		return selectedCommitments;
	}

	public List<Event> getSelectedEventList(){
		List<Event> selectedEvents =  new ArrayList<Event>();
		
		if(calendarView instanceof MonthCalendarView) selectedEvents.addAll(((MonthCalendarView)calendarView).getSelectedEvents());
		else if(calendarView instanceof DayCalendar) selectedEvents.addAll(SchedMouseListener.getSelectedEvents());
		else if(calendarView instanceof WeekCalendar) selectedEvents.addAll(SchedMouseListener.getSelectedEvents());
		
		if(subTabPane.getSelectedComponent() instanceof QuickListTabPanel)
			selectedEvents.addAll(quickListTabPanel.getSelectedEvents());
		else if(subTabPane.getSelectedComponent() instanceof EventSubTabPanel)
			selectedEvents.addAll(eventSubTabPanel.getSelectedEvents());
		
		return selectedEvents;
	}
	
	public List<ScheduledEvent> getSelectedScheduledEvents() {
		return scheduledEventSubTabPanel.getSelectedScheduledEvents();
	}

	public void setCalendarViewTitle(String title) {
		calendarTitleLabel.setText(title);
	}

	public void setCalendarViewNext() {
		calendarView.next();
		
		QuickListModel.getQuickListModel().updateList();
		this.setCalendarViewTitle(calendarView.getTitle());
		this.refreshCalendarView();
	}

	public void setCalendarViewToday() {
		calendarView.today();
		
		QuickListModel.getQuickListModel().updateList();
		this.setCalendarViewTitle(calendarView.getTitle());
		this.refreshCalendarView();
	}

	public void setCalendarViewPrevious() {
		calendarView.previous();
		
		QuickListModel.getQuickListModel().updateList();
		this.setCalendarViewTitle(calendarView.getTitle());
		this.refreshCalendarView();
	}

	public void setCalendarViewDate(Calendar date) {
		dayView.viewDate(date);
		weekView.viewDate(date);
		monthView.viewDate(date);
		yearView.viewDate(date);
		this.setCalendarViewTitle(calendarView.getTitle());
		this.refreshCalendarView();
	}

	public void displayDayView() {
		if(!(calendarView instanceof DayCalendar)) {
			viewButtonGroup.setSelectedButton(0);
			calendarViewPanel.removeAll();
			calendarView = dayView;
			calendarViewPanel.add(dayView, "w 5000, h 5000");

			EventListModel.getEventListModel().update();
			CommitmentListModel.getCommitmentListModel().update();
			QuickListModel.getQuickListModel().updateList();
			this.setCalendarViewTitle(dayView.getTitle());
			this.refreshCalendarView();
		}
	}
	public void displayWeekView() {
		if(!(calendarView instanceof WeekCalendar)){
			viewButtonGroup.setSelectedButton(1);
			calendarViewPanel.removeAll();
			calendarView = weekView;
			calendarViewPanel.add(weekView, "w 5000, h 5000");

			EventListModel.getEventListModel().update();
			CommitmentListModel.getCommitmentListModel().update();
			QuickListModel.getQuickListModel().updateList();
			this.setCalendarViewTitle(weekView.getTitle());
			this.refreshCalendarView();
		}
	}

	public void displayMonthView() {
		if(!(calendarView instanceof MonthCalendarView)) {
			viewButtonGroup.setSelectedButton(2);
			calendarViewPanel.removeAll();
			calendarView = monthView;
			calendarViewPanel.add(monthView, "w 5000, h 5000");

			EventListModel.getEventListModel().update();
			CommitmentListModel.getCommitmentListModel().update();
			QuickListModel.getQuickListModel().updateList();
			this.setCalendarViewTitle(monthView.getTitle());
			this.refreshCalendarView();
		}
	}

	public void displayYearView() {
		if(!(calendarView instanceof YearCalendarView)){
			viewButtonGroup.setSelectedButton(3);
			calendarViewPanel.removeAll();
			calendarView = yearView;
			calendarViewPanel.add(yearView, "w 5000, h 5000");

			EventListModel.getEventListModel().update();
			CommitmentListModel.getCommitmentListModel().update();
			QuickListModel.getQuickListModel().updateList();
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
	synchronized public int getTeamPersonalState() {
		if(teamCalCheckBox != null && teamCalCheckBox.isSelected()) {
			if (personalCalCheckBox != null && personalCalCheckBox.isSelected()) return 3;
			else return 1;
		} else if(personalCalCheckBox != null && personalCalCheckBox.isSelected()) return 2;
		else return 0;
	}
}
