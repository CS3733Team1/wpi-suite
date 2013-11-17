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

import java.awt.Color;
import org.jdesktop.swingx.JXMonthView;
import org.jdesktop.swingx.calendar.DateSelectionModel.SelectionMode;

/**
 * date picking calendar
 */
public class DatePicker extends JXMonthView{

	public static final Color START_END_DAY = new Color(47, 150, 9);
	public static final Color SELECTION = new Color(236,252,144);
	public static final Color UNSELECTABLE = Color.red;
	
	/**
	 * constructor for the date picking calendar
	 * @param singleSelection selects whether panel will be single or multiple
	 * interval, true selects single interval selection
	 */
	public DatePicker(SelectionMode sm)
	{
		buildLayout(sm);
	}
	
	private void buildLayout(SelectionMode sm){
		this.setPreferredColumnCount(1);
		this.setPreferredRowCount(1);
		
		this.setSelectionBackground(SELECTION);
		this.setFlaggedDayForeground(START_END_DAY);
		this.setSelectionMode(sm);
		
		this.setAlignmentX(CENTER_ALIGNMENT);
	}
}
