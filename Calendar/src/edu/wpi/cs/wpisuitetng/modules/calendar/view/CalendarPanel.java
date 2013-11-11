package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;

import edu.wpi.cs.wpisuitetng.modules.calendar.controller.RetrieveCommitmentController;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.CalendarModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.CalendarObjectModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;

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
		
		this.addTab(c.getTitle(), null, panel, "A Calendar");
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
