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
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.calendar.Calendar;

import net.miginfocom.swing.MigLayout;

public class TimeDurationPickerPanel extends JPanel {

	private TimePicker StartTimePicker;
	private TimePicker EndTimePicker;
	private JLabel errorLabel;
	
	public TimeDurationPickerPanel() {
		this.setLayout(new MigLayout("insets 1"));

		StartTimePicker =  new TimePicker();
		EndTimePicker = new TimePicker();
		errorLabel=new JLabel("ERROR: End Time after Start Time!");
		errorLabel.setForeground(Color.RED);
		errorLabel.setVisible(false);
		
		//TODO: add a listener to each TimePicker to validate start tiem is before end time and change the visibility of the error label	
		
		this.add(StartTimePicker, "alignx left, wrap");
		this.add(EndTimePicker, "alignx left, wrap");
		this.add(errorLabel,  "alignx left, wrap");
	}

	public boolean isValidTime() {
		return (StartTimePicker.hasValidTime() && EndTimePicker.hasValidTime() &&  EndTimePicker.getTime().after( StartTimePicker.getTime()));
	}
	
	public Date getStartTime() {
		return StartTimePicker.getTime();
	}
	
	public Date getEndTime() {
		return EndTimePicker.getTime();
	}
	
	
//	public void setActionListener(ActionListener al) {
//		timeStart.addActionListener(al);
//		timeEnd.addActionListener(al);
//	}
}
