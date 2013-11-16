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
	public DatePicker(boolean singleSelection)
	{
		buildLayout(singleSelection);
	}
	
	private void buildLayout(boolean singleSelection){
		this.setPreferredColumnCount(1);
		this.setPreferredRowCount(1);
		
		this.setSelectionBackground(SELECTION);
		this.setFlaggedDayForeground(START_END_DAY);
		if(singleSelection){
			this.setSelectionMode(SelectionMode.SINGLE_INTERVAL_SELECTION);
		}
		else{
			this.setSelectionMode(SelectionMode.MULTIPLE_INTERVAL_SELECTION);
		}
		
		this.setAlignmentX(CENTER_ALIGNMENT);
	}
}
