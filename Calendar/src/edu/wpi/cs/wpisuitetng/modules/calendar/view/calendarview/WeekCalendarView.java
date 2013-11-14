/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class WeekCalendarView extends JPanel implements ICalendarView {

	public WeekCalendarView() {
		// TODO Auto-generated constructor stub
		this.setLayout(new BorderLayout());
		this.add(new JLabel("Week View Unimplemented"), BorderLayout.CENTER);
	}

	@Override
	public void next() {
		// TODO Auto-generated method stub

	}

	@Override
	public void previous() {
		// TODO Auto-generated method stub

	}

	@Override
	public void today() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "Week view unimplemented";
	}
}
