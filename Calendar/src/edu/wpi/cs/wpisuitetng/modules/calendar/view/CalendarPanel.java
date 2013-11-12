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
	
	public CalendarPanel(CalendarModel model) {
		
		this.model = model;
		// Initially Empty
		this.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	}

	// Method to set / add Tab [Calendars]

	public void addCalendar(CalendarObjectModel c) {
		CalendarTabPanel panel = new CalendarTabPanel(c, model);
		
		panel.addKeyListener(new RetrieveCommitmentController(model));
		panel.addMouseListener(new RetrieveCommitmentController(model));
		
		ImageIcon calIcon = new ImageIcon();
		try {
			calIcon = new ImageIcon(ImageIO.read(getClass().getResource("/images/personal_calendar.png")));
		} catch (IOException e) {}
		
		this.addTab(c.getTitle(), calIcon, panel, "A Calendar");
	}
	
	public CalendarTabPanel getCurrentPanel(){
		return (CalendarTabPanel)this.getSelectedComponent();
	}
	
	public JList<Object> getSelectedPanel(){
		return ((CalendarTabPanel)this.getSelectedComponent()).getCommitmentJList();
	}
	
	public JList<Object> getSelectedEventList(){
		return ((CalendarTabPanel)this.getSelectedComponent()).getEventJList();
	}
	
	public void RefreshSelectedPanel(){
		((CalendarTabPanel)this.getSelectedComponent()).ResetSelection();
	}
}
