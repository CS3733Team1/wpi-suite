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

import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import edu.wpi.cs.wpisuitetng.modules.calendar.controller.commitment.RetrieveCommitmentController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.event.RetrieveEventController;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.CalendarObjectModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.MainModel;

public class CalendarPanel extends JTabbedPane {

	private ArrayList<JPanel> p;
	private JList<Object> commitments;
	private MainModel model;

	private CalendarTabPanel teamCalendarPanel;
	private CalendarTabPanel personalCalendarPanel;

	public CalendarPanel(MainModel model) {

		this.model = model;
		// Initially Empty
		this.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		
		this.addAncestorListener(new RetrieveCommitmentController(model));
		this.addAncestorListener(new RetrieveEventController(model));
	}

	// Create TeamCalendar
	public void createTeamCalendar(CalendarObjectModel c) {
		teamCalendarPanel = new CalendarTabPanel(model);

		ImageIcon calIcon = new ImageIcon();
		try {
			calIcon = new ImageIcon(ImageIO.read(getClass().getResource("/images/team_calendar.png")));
		} catch (IOException e) {}

		this.addTab(c.getTitle(), calIcon, teamCalendarPanel, "Team Calendar");
	}
	

	// Create PersonalCalendar
	public void createPersonalCalendar(CalendarObjectModel c) {
		personalCalendarPanel = new CalendarTabPanel(model);

		ImageIcon calIcon = new ImageIcon();
		try {
			calIcon = new ImageIcon(ImageIO.read(getClass().getResource("/images/personal_calendar.png")));
		} catch (IOException e) {}

		this.addTab(c.getTitle(), calIcon, personalCalendarPanel, "Personal Calendar");
	}

	public CalendarTabPanel getCurrentPanel(){
		return (CalendarTabPanel)this.getSelectedComponent();
	}

	public JList<Object> getSelectedCommitmentsInList(){
		if(this.getSelectedComponent() instanceof CalendarTabPanel)
			return ((CalendarTabPanel)this.getSelectedComponent()).getCommitmentJList();
		else return new JList<Object>();
	}

	public JList<Object> getSelectedEventsInList(){
		if(this.getSelectedComponent() instanceof CalendarTabPanel)
			return ((CalendarTabPanel)this.getSelectedComponent()).getEventJList();
		else return new JList<Object>();
	}

	public void refreshSelectedPanel(){
		if(this.getSelectedComponent() instanceof CalendarTabPanel)
			((CalendarTabPanel)this.getSelectedComponent()).resetSelection();
	}
}
