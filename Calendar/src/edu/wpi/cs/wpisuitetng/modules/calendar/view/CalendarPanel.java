package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import edu.wpi.cs.wpisuitetng.modules.calendar.controller.RetrieveCommitmentController;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.CalendarModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.CalendarObjectModel;

public class CalendarPanel extends JTabbedPane {

	private ArrayList<JPanel> p;
	private JList<Object> commitments;
	private CalendarModel model;

	private CalendarTabPanel teamCalendarPanel;
	private CalendarTabPanel personalCalendarPanel;
	
	private RetrieveCommitmentController retrieve;

	public CalendarPanel(CalendarModel model) {

		this.model = model;
		// Initially Empty
		this.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	}

	// Create TeamCalendar
	public void createTeamCalendar(CalendarObjectModel c) {
		teamCalendarPanel = new CalendarTabPanel(c, model);

		teamCalendarPanel.addKeyListener(retrieve);
		teamCalendarPanel.addMouseListener(retrieve);

		ImageIcon calIcon = new ImageIcon();
		try {
			calIcon = new ImageIcon(ImageIO.read(getClass().getResource("/images/team_calendar.png")));
		} catch (IOException e) {}

		this.addTab(c.getTitle(), calIcon, teamCalendarPanel, "Team Calendar");
	}
	

	// Create PersonalCalendar
	public void createPersonalCalendar(CalendarObjectModel c) {
		personalCalendarPanel = new CalendarTabPanel(c, model);

		retrieve = new RetrieveCommitmentController(model);
		personalCalendarPanel.addKeyListener(retrieve);
		personalCalendarPanel.addMouseListener(retrieve);

		ImageIcon calIcon = new ImageIcon();
		try {
			calIcon = new ImageIcon(ImageIO.read(getClass().getResource("/images/personal_calendar.png")));
		} catch (IOException e) {}

		this.addTab(c.getTitle(), calIcon, personalCalendarPanel, "Personal Calendar");
	}

	// UNUSED
	// Method to set / add Tab [Calendars] PHASE THIS OUT FOR NOW
	public void addCalendar(CalendarObjectModel c) {
		CalendarTabPanel panel = new CalendarTabPanel(c, model);

		retrieve = new RetrieveCommitmentController(model);
		panel.addKeyListener(retrieve);
		panel.addMouseListener(retrieve);
		
		ImageIcon calIcon = new ImageIcon();
		try {
			calIcon = new ImageIcon(ImageIO.read(getClass().getResource("/images/personal_calendar.png")));
		} catch (IOException e) {}

		this.addTab(c.getTitle(), calIcon, panel, "A Calendar");
		
		retrieve.grabMessages();
	}

	public CalendarTabPanel getCurrentPanel(){
		return (CalendarTabPanel)this.getSelectedComponent();
	}

	public JList<Object> getSelectedPanel(){
		if(this.getSelectedComponent() instanceof CalendarTabPanel)
			return ((CalendarTabPanel)this.getSelectedComponent()).getCommitmentJList();
		else return new JList<Object>();
	}

	public JList<Object> getSelectedEventList(){
		if(this.getSelectedComponent() instanceof CalendarTabPanel)
			return ((CalendarTabPanel)this.getSelectedComponent()).getEventJList();
		else return new JList<Object>();
	}

	public void RefreshSelectedPanel(){
		if(this.getSelectedComponent() instanceof CalendarTabPanel)
			((CalendarTabPanel)this.getSelectedComponent()).ResetSelection();
	}
}
