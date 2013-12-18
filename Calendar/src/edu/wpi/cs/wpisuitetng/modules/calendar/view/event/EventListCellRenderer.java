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

import java.awt.Color;
import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.LineBorder;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.event.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.CalendarUtils;

public class EventListCellRenderer extends JPanel implements ListCellRenderer<Event> {

	private JLabel eventName;
	private JLabel startDate;
	private JLabel endDate;

	public EventListCellRenderer() {
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		eventName = new JLabel();
		startDate = new JLabel();
		endDate = new JLabel();
		this.add(eventName);
		this.add(startDate);
		this.add(endDate);
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends Event> list, Event event,
			int index, boolean isSelected, boolean cellHasFocus) {
		eventName.setText("Name: " + event.getName());
		startDate.setText("Date Start: " + event.getStartDate().toString());
		endDate.setText("Date End: " + event.getEndDate().toString());

		this.setBackground(isSelected ? CalendarUtils.selectionColor : Color.WHITE);

		this.setBorder(new LineBorder(new Color(0, 0, 0)));

		return this;
	}

}
