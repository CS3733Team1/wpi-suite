/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.view.event;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.commitment.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.event.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.filter.FilteredEventsListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.list.ListItemListener;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.list.SRList;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.RightClickMenu;

public class EventSubTabPanel extends JPanel implements ListItemListener<Event> {
	
	private SRList<Event> eventList;
	
	private CalendarPanel calendarPanel;
	
	public EventSubTabPanel(CalendarPanel calendarPanel) {
		this.setLayout(new MigLayout("fill"));
		this.calendarPanel = calendarPanel;
		
		eventList = new SRList<Event>(FilteredEventsListModel.getFilteredEventsListModel());
		eventList.setListItemRenderer(new EventListItemRenderer());
		eventList.addListItemListener(this);
		
		this.add(eventList, "grow");
	}
	
	public List<Event> getSelectedEvents() {
		return eventList.getSelectedItems();
	}

	@Override
	public void itemsSelected(List<Event> listObjects) {}

	@Override
	public void itemDoubleClicked(Event listObject) {}

	@Override
	public void itemFocused(Event listObject) {}

	@Override
	public void itemRightClicked(Event listObject, Point p) {
		List<Event> listofEvent = eventList.getSelectedItems();
		RightClickMenu menu = new RightClickMenu(listofEvent, new ArrayList<Commitment>(), calendarPanel);
		menu.show(eventList, (int)p.getX(), (int)p.getY());
	}
}
