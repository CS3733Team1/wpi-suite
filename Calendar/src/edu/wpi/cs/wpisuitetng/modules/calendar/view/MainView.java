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
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.ChangeToolBarController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.category.RetrieveCategoryController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.commitment.DeleteCommitmentController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.commitment.DisplayCommitmentTabController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.commitment.RetrieveCommitmentController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.event.DeleteEventController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.event.DisplayEventTabController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.event.RetrieveEventController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.filter.RetrieveFilterController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.help.HelpButtonController;

public class MainView {

	private List<JanewayTabModel> tabs;
	private static CalendarPanel calendarPanel;
	private static CalendarToolBar calendarToolBar;

	public MainView() {
		calendarPanel = new CalendarPanel();
		calendarToolBar = new CalendarToolBar();
		
		calendarPanel.addChangeListener(new ChangeToolBarController(calendarToolBar, calendarPanel));

		RetrieveCommitmentController	rcomc = new RetrieveCommitmentController();
		RetrieveEventController			revec = new RetrieveEventController();
		RetrieveCategoryController		rcatc = new RetrieveCategoryController();
		RetrieveFilterController		rfilc = new RetrieveFilterController();
		
		calendarPanel.addAncestorListener(rcomc);
		calendarPanel.addAncestorListener(revec);
		calendarPanel.addAncestorListener(rcatc);
		calendarPanel.addAncestorListener(rfilc);
		
		calendarToolBar.refreshButtonListener(rcomc);
		calendarToolBar.refreshButtonListener(revec);
		calendarToolBar.refreshButtonListener(rcatc);
		calendarToolBar.refreshButtonListener(rfilc);
		
		calendarToolBar.addEventButtonListener(new DisplayEventTabController(calendarPanel));
		calendarToolBar.deleteEventButtonListener(new DeleteEventController(calendarPanel));
		
		calendarToolBar.helpButtonListener(new HelpButtonController(calendarPanel));
		
		calendarToolBar.deleteCommitmentButtonListener(new DeleteCommitmentController(calendarPanel));
		calendarToolBar.addCommitmentButtonListener(new DisplayCommitmentTabController(calendarPanel));
		
		tabs = new ArrayList<JanewayTabModel>();

		JanewayTabModel tab = new JanewayTabModel(getName(), new ImageIcon(),
				calendarToolBar, calendarPanel);

		// Add the tab to the list of tabs owned by this module
		tabs.add(tab);

		calendarPanel.createCalendar();
	}

	public List<JanewayTabModel> getTabs() {
		return tabs;
	}

	public String getName() {
		return "Calendar";
	}
	
	public CalendarToolBar getCalendarToolBar() {
		return calendarToolBar;
	}
	
	public synchronized CalendarPanel getCalendarPanel(){
		return this.calendarPanel;
	}
	public static synchronized CalendarPanel getCurrentCalendarPanel(){
		return MainView.calendarPanel;
	}
	public static synchronized CalendarToolBar getCurrentCalendarToolbar()
	{
		return MainView.calendarToolBar;
	}
}
