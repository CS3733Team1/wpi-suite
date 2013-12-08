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

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.FilteredEventsListModel;

public class EventListPanel extends JPanel {

	private FilteredEventsListModel model;
	private JList<Event> eventList;
	
	public EventListPanel() {
		this.model = FilteredEventsListModel.getFilteredEventsListModel();
		this.setLayout(new BorderLayout());
		
		eventList = new JList<Event>(model);
		
		eventList.setCellRenderer(new EventListCellRenderer());
		eventList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		eventList.setLayoutOrientation(JList.VERTICAL);

		JScrollPane scrollPane2 = new JScrollPane(eventList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		this.add(new JLabel("Events"), BorderLayout.NORTH);
		this.add(scrollPane2, BorderLayout.CENTER);

		this.setPreferredSize(new Dimension(300, 1));
	}

	public JList<Event> getEventList() {
		return eventList;
	}
}
