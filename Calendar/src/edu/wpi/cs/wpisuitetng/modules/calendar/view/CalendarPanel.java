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

import java.awt.Component;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;

public class CalendarPanel extends JTabbedPane {
	private CalendarTabPanel teamCalendarPanel;
	private CalendarTabPanel personalCalendarPanel;
	private CalendarTabPanel BothCalendarPanel;
	
	public CalendarPanel() {
		this.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	}

	
	/**
	 * Overridden insertTab function to add the closable tab element.
	 * 
	 * @param title	Title of the tab
	 * @param icon	Icon for the tab
	 * @param component	The tab
	 * @param tip	Showing mouse tip when hovering over tab
	 * @param index	Location of the tab
	 */
	@Override
	public void addTab(String title, Icon icon, Component component) {
		int index=this.getTabCount();
		super.addTab(title, icon, component);
		if (!(component instanceof CalendarTabPanel)) {
			setTabComponentAt(index, new ClosableTabComponent(this));
		}
	}
	
	// Create TeamCalendar
	public void createTeamCalendar() {
		teamCalendarPanel = new CalendarTabPanel(this);

		ImageIcon calIcon = new ImageIcon();
		try {
			calIcon = new ImageIcon(ImageIO.read(getClass().getResource("/images/team_calendar.png")));
		} catch (IOException e) {}

		this.addTab("Team Calendar", calIcon, teamCalendarPanel, "Team Calendar");
	}
	
	// Create PersonalCalendar
	public void createPersonalCalendar() {
		personalCalendarPanel = new CalendarTabPanel(this);

		ImageIcon calIcon = new ImageIcon();
		try {
			calIcon = new ImageIcon(ImageIO.read(getClass().getResource("/images/personal_calendar.png")));
		} catch (IOException e) {}

		this.addTab("Personal Calendar", calIcon, personalCalendarPanel, "Personal Calendar");
	}

	public CalendarTabPanel getCalendarTabPanel() {
		return (CalendarTabPanel)this.getSelectedComponent();
	}
	
	public void refreshSelectedPanel(){
		if(this.getSelectedComponent() instanceof CalendarTabPanel)
			((CalendarTabPanel)this.getSelectedComponent()).resetSelection();
	}


	public void createCalendar() {
		BothCalendarPanel = new CalendarTabPanel(this);

		ImageIcon calIcon = new ImageIcon();
		try {
			calIcon = new ImageIcon(ImageIO.read(getClass().getResource("/images/team_calendar.png")));
		} catch (IOException e) {}

		this.addTab("Both Calendars", calIcon, BothCalendarPanel, "Both Calendars");
		
	}
}
