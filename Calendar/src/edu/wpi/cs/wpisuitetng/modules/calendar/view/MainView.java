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

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import edu.wpi.cs.wpisuitetng.janeway.modules.JanewayTabModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.CalendarObjectModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.MainModel;

public class MainView {

	private List<JanewayTabModel> tabs;
	private MainModel model;
	private CalendarPanel calendarPanel;
	private CalendarToolBar calendarToolBar;

	public MainView(MainModel model) {
		this.model = model;

		// this.model.setValue(InitialValue);
		// view should access all data from model to display

		calendarPanel = new CalendarPanel(model);
		calendarToolBar = new CalendarToolBar(model, this);

		tabs = new ArrayList<JanewayTabModel>();

		JanewayTabModel tab = new JanewayTabModel(getName(), new ImageIcon(),
				calendarToolBar, calendarPanel);

		// Add the tab to the list of tabs owned by this module
		tabs.add(tab);

		// changed from using addCalendarPanel()
		calendarPanel.createTeamCalendar(new CalendarObjectModel("Team Calendar"));
		calendarPanel.createPersonalCalendar(new CalendarObjectModel("Personal Calendar"));
	}

	public List<JanewayTabModel> getTabs() {
		return tabs;
	}

	public String getName() {
		return "Calendar";
	}
	
	public CalendarToolBar getcalendarToolBar(){
		return this.calendarToolBar;
	}
	
	public CalendarPanel getCalendarPanel(){
		return this.calendarPanel;
	}
}
