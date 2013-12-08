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

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;

public class CalendarPanel extends JTabbedPane {
	private CalendarTabPanel calendarPanel;
	
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
		int index = this.getTabCount();
		super.addTab(title, icon, component);
		if (!(component instanceof CalendarTabPanel)) {
			setTabComponentAt(index, new ClosableTabComponent(this));
		}
	}
	
	public void createCalendar() {
		calendarPanel = new CalendarTabPanel(this);
		this.addTab("Calendar", new ImageIcon(), calendarPanel, "Calendar");
	}

	public CalendarTabPanel getCalendarTabPanel() {
		return calendarPanel;
	}
	
	public void refreshSelectedPanel(){
		if(this.getSelectedComponent() instanceof CalendarTabPanel)
			((CalendarTabPanel)this.getSelectedComponent()).resetSelection();
	}
}
