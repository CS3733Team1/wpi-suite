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
import javax.swing.ListSelectionModel;

import edu.wpi.cs.wpisuitetng.modules.calendar.controller.display.CalendarViewNextController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.display.CalendarViewNowController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.display.CalendarViewPreviousController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.display.DisplayMonthViewController;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.CalendarModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.CalendarObjectModel;

public class CalendarTabPanel extends JPanel{
	CalendarModel model;
	private JList<Object> commitments, events;

	private JButton prevButton, homeButton, nextButton;
	private JButton yearViewButton, monthViewButton, weekViewButton, dayViewButton;
	
	private JPanel calendarViewPanel;
	
	private Component currentview;
	private ICalendarViewComponent updateview;
	private MonthCalendar monthview;
	
	private JLabel calendarViewTitle;
	
	public CalendarTabPanel(CalendarObjectModel c, CalendarModel model){
		this.model = model;

		currentview = null;
		monthview = null;
		updateview = null;
		
		setLayout(new BorderLayout());

		calendarViewPanel = new JPanel(new BorderLayout());
		JPanel calendarViewButtonsPanel = new JPanel();

		try {
			prevButton = new JButton(new ImageIcon(ImageIO.read(getClass().getResource("/images/previous.png"))));
			homeButton = new JButton(new ImageIcon(ImageIO.read(getClass().getResource("/images/home.png"))));
			nextButton = new JButton(new ImageIcon(ImageIO.read(getClass().getResource("/images/next.png"))));
			
			homeButton.setMargin(new Insets(0, 0, 0, 0));
			prevButton.setMargin(new Insets(0, 0, 0, 0));
			nextButton.setMargin(new Insets(0, 0, 0, 0));
			
			
			dayViewButton = new JButton("Day",
					new ImageIcon(ImageIO.read(getClass().getResource("/images/day_cal.png"))));
			weekViewButton = new JButton("Week",
					new ImageIcon(ImageIO.read(getClass().getResource("/images/week_cal.png"))));
			monthViewButton = new JButton("Month",
					new ImageIcon(ImageIO.read(getClass().getResource("/images/month_cal.png"))));
			yearViewButton = new JButton("Year",
					new ImageIcon(ImageIO.read(getClass().getResource("/images/year_cal.png"))));

		} catch (IOException e) {}

		calendarViewTitle = new JLabel();
		
		JPanel temp = new JPanel(new BorderLayout());
		JPanel temp2 = new JPanel(new BorderLayout());
		JPanel temp3 = new JPanel();
		
		temp3.add(prevButton);
		temp3.add(homeButton);
		temp3.add(nextButton);
		
		temp2.add(temp3, BorderLayout.WEST);
		temp2.add(calendarViewTitle, BorderLayout.CENTER);
		
		prevButton.addActionListener(new CalendarViewPreviousController(this));
		nextButton.addActionListener(new CalendarViewNextController(this));
		homeButton.addActionListener(new CalendarViewNowController(this));
		monthViewButton.addActionListener(new DisplayMonthViewController(this));
		
		calendarViewButtonsPanel.add(yearViewButton);
		calendarViewButtonsPanel.add(monthViewButton);
		calendarViewButtonsPanel.add(weekViewButton);
		calendarViewButtonsPanel.add(dayViewButton);
		
		temp.add(temp2, BorderLayout.WEST);
		
		temp2.setPreferredSize(new Dimension(200, 1));
		
		temp.add(calendarViewButtonsPanel, BorderLayout.CENTER);
		
		
		
		calendarViewPanel.add(temp, BorderLayout.NORTH);
		//calendarViewPanel.add(new JScrollPane(new DayView()), BorderLayout.CENTER);
		
		add(calendarViewPanel, BorderLayout.CENTER);

		
		JPanel eventsListPanel = new JPanel(new BorderLayout());

		events = new JList<Object>(model.getEventModel());
		events.setCellRenderer(new EventListRenderer());
		events.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		events.setLayoutOrientation(JList.VERTICAL);
		events.setVisibleRowCount(-1);
		
		JScrollPane scrollPane = new JScrollPane(events, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		eventsListPanel.add(new JLabel("Events"), BorderLayout.NORTH);
		eventsListPanel.add(scrollPane, BorderLayout.CENTER);

		JPanel commitmentsListPanel = new JPanel(new BorderLayout());
		
		commitments = new JList<Object>(model.getcommitModel());
		commitments.setCellRenderer(new CommitmentListRenderer());
		commitments.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		commitments.setLayoutOrientation(JList.VERTICAL);
		commitments.setVisibleRowCount(-1);

		JScrollPane scrollPane2 = new JScrollPane(commitments, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		commitmentsListPanel.add(new JLabel("Commitments"), BorderLayout.NORTH);
		commitmentsListPanel.add(scrollPane2, BorderLayout.CENTER);

		commitmentsListPanel.setPreferredSize(new Dimension(300, 1));
		
		//JPanel eventCommitmentListPanel = new JPanel(new BorderLayout());
		//eventCommitmentListPanel.add(eventsListPanel, BorderLayout.NORTH);
		//eventCommitmentListPanel.add(commitmentsListPanel, BorderLayout.SOUTH);
		add(commitmentsListPanel, BorderLayout.LINE_END);
		
		//yearViewButton.setEnabled(false);
		//weekViewButton.setEnabled(false);
		//dayViewButton.setEnabled(false);
		
		changeMonthView();
	}

	public void changeMonthView(){
		if (monthview == null){
			monthview = new MonthCalendar();
		}
		if (currentview != null){
			calendarViewPanel.remove(currentview);
		}
		
		System.out.println("I'm at this spot! Again!");
		
		updateview = monthview;
		currentview = new JScrollPane(monthview);
		calendarViewPanel.add(currentview, BorderLayout.CENTER);

		monthview.setVisible(true);
		currentview.setVisible(true);
		
		setCalendarViewTitle(monthview.getTitle());
		
		this.refreshCalendarView();
	}
	
	
	public MonthCalendar grabMonthView(){
		if (monthview == null){
			monthview = new MonthCalendar();
		}	
		return monthview;
	}
	
	public ICalendarViewComponent getUpdateView(){
		return updateview;
	}
	
	public void refreshCalendarView(){
		calendarViewPanel.updateUI();
	}
	
	public JList<Object> getCommitmentJList(){
		return commitments;
	}

	public void ResetSelection(){
		commitments.clearSelection();
		events.clearSelection();
	}

	public JList<Object> getEventJList(){
		return events;
	}

	public void setCalendarViewTitle(String title) {
		calendarViewTitle.setText(title);
	}
}
