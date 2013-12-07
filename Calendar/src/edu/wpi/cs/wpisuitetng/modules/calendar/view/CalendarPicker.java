/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import javax.swing.JComboBox;
import javax.swing.JPanel;

public class CalendarPicker extends JPanel {

	private JComboBox<String> calendarComboBox;
	private String[] selectedCalendar = {"Personal","Team"};
	
	public CalendarPicker(){
		calendarComboBox = new JComboBox<String>(selectedCalendar);
		calendarComboBox.setSelectedIndex(0);
		this.add(calendarComboBox);
	}
	
	public void setSelected(String select){
		calendarComboBox.setSelectedItem(select);
	}
	
	// Returns true if commitment or event is personal
	public boolean isTeam(){
		return calendarComboBox.getSelectedItem().equals(selectedCalendar[1]);
	}
	
}
