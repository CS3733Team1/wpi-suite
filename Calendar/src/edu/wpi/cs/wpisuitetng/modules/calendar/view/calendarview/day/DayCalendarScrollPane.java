/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.MatteBorder;

/**
 * Holds a DayHolderPanel in a scrollpane
 */
public class DayCalendarScrollPane extends JScrollPane {
	private DayHolderPanel daylayer;
	public static final String[] weekNames = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
	private JLabel dayLabel;
	private int scrollSpeed = 15;
	
	public DayCalendarScrollPane(DayHolderPanel day){
		super(day);
		
		daylayer = day;
		
		this.setWheelScrollingEnabled(true);
		this.getVerticalScrollBar().setUnitIncrement(scrollSpeed);
		this.setBorder(new MatteBorder(1, 0, 0, 0, Color.GRAY));
		this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	}
	
}
