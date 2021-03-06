/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.controller;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarTabPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarToolBar;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.commitment.CommitmentTabPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.event.EventTabPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.help.HelpWindow;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.scheduledevent.ScheduledEventTabPanel;

public class ChangeToolBarController implements ChangeListener {

	private CalendarToolBar toolbar;
	private CalendarPanel calendarPanel;

	public ChangeToolBarController(CalendarToolBar toolbar, CalendarPanel calendarPanel) {
		this.toolbar = toolbar;
		this.calendarPanel = calendarPanel;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if(calendarPanel.getSelectedComponent() instanceof CalendarTabPanel) {
			toolbar.setToolBarCalendarTab();
		}
		if(calendarPanel.getSelectedComponent() instanceof CommitmentTabPanel) {
			toolbar.setToolBarCommitment();
		}
		if(calendarPanel.getSelectedComponent() instanceof EventTabPanel){
			toolbar.setToolBarEvent();
		}
		else if(calendarPanel.getSelectedComponent() instanceof ScheduledEventTabPanel || calendarPanel.getSelectedComponent() instanceof HelpWindow){
			toolbar.setToolBarSchedule();
		}
	}
}
