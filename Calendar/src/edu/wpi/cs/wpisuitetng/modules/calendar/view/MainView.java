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
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.category.AddCategoryController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.category.DeleteCategoryController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.category.DisplayCategoryTabController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.category.RetrieveCategoryController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.commitment.DeleteCommitmentController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.commitment.DisplayCommitmentTabController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.commitment.RetrieveCommitmentController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.event.DeleteEventController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.event.DisplayEventTabController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.event.RetrieveEventController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.filter.AddFilterController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.filter.DeleteFilterController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.filter.DisplayFilterTabController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.filter.RetrieveFilterController;

public class MainView {

	private List<JanewayTabModel> tabs;
	private CalendarPanel calendarPanel;
	private CalendarToolBar calendarToolBar;

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
		
		calendarToolBar.categoryButtonListener(new DisplayCategoryTabController(calendarPanel));
		
		calendarToolBar.addCategoryButtonListener(new AddCategoryController(calendarPanel));
		calendarToolBar.deleteCategoryButtonListener(new DeleteCategoryController(calendarPanel));
		
		calendarToolBar.filterButtonListener(new DisplayFilterTabController(calendarPanel));
		
		calendarToolBar.addFilterButtonListener(new AddFilterController(calendarPanel));
		calendarToolBar.deleteFilterButtonListener(new DeleteFilterController(calendarPanel));
		
		calendarToolBar.addEventButtonListener(new DisplayEventTabController(calendarPanel));
		calendarToolBar.deleteEventButtonListener(new DeleteEventController(calendarPanel));
		
		calendarToolBar.addCommitmentButtonListener(new DisplayCommitmentTabController(calendarPanel));
		calendarToolBar.deleteCommitmentButtonListener(new DeleteCommitmentController(calendarPanel));
		
		tabs = new ArrayList<JanewayTabModel>();

		JanewayTabModel tab = new JanewayTabModel(getName(), new ImageIcon(),
				calendarToolBar, calendarPanel);

		// Add the tab to the list of tabs owned by this module
		tabs.add(tab);

		calendarPanel.createTeamCalendar();
		calendarPanel.createPersonalCalendar();
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
