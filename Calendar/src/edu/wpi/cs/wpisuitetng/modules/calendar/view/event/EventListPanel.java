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

import edu.wpi.cs.wpisuitetng.modules.calendar.model.EventModel;

public class EventListPanel extends JPanel {

	private JList eventList;
	
	public EventListPanel(EventModel eventModel) {
		this.setLayout(new BorderLayout());
		
		eventList = new JList<Object>(eventModel);
		
		eventList.setCellRenderer(new EventListCellRenderer());
		eventList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		eventList.setLayoutOrientation(JList.VERTICAL);
		eventList.setVisibleRowCount(-1);

		JScrollPane scrollPane2 = new JScrollPane(eventList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		this.add(new JLabel("Events"), BorderLayout.NORTH);
		this.add(scrollPane2, BorderLayout.CENTER);

		this.setPreferredSize(new Dimension(300, 1));
	}

	public JList<Object> getEventList() {
		return eventList;
	}
}