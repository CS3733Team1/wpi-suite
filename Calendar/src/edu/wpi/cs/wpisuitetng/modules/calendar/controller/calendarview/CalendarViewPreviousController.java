/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.controller.calendarview;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarTabPanel;

public class CalendarViewPreviousController implements ActionListener{
	CalendarTabPanel view;

	public CalendarViewPreviousController(CalendarTabPanel view) {
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		view.setCalendarViewPrevious();
	}
}
