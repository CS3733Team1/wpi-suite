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

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JTabbedPane;

public class CalendarPanel extends JTabbedPane {
	private CalendarTabPanel teamCalendarPanel;
	private CalendarTabPanel personalCalendarPanel;

	public CalendarPanel() {
		this.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	}

	// Create TeamCalendar
	public void createTeamCalendar() {
		teamCalendarPanel = new CalendarTabPanel();

		ImageIcon calIcon = new ImageIcon();
		try {
			calIcon = new ImageIcon(ImageIO.read(getClass().getResource("/images/team_calendar.png")));
		} catch (IOException e) {}

		this.addTab("Team Calendar", calIcon, teamCalendarPanel, "Team Calendar");
	}
	

	// Create PersonalCalendar
	public void createPersonalCalendar() {
		personalCalendarPanel = new CalendarTabPanel();

		ImageIcon calIcon = new ImageIcon();
		try {
			calIcon = new ImageIcon(ImageIO.read(getClass().getResource("/images/personal_calendar.png")));
		} catch (IOException e) {}

		this.addTab("Personal Calendar", calIcon, personalCalendarPanel, "Personal Calendar");
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
