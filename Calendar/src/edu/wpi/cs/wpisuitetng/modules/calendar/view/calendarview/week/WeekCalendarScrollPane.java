/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.week;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day.DayCalendarLayerPane;

public class WeekCalendarScrollPane extends JScrollPane{
	private WeekCalendarLayerPane weeklayer;

	public WeekCalendarScrollPane(WeekCalendarLayerPane day){
		super(day);

		weeklayer = day;

		this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

	}
}
