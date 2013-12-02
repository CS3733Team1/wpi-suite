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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
	private String defaultErrorText_="ERROR: End Time after Start Time!";
	public TimeDurationPickerPanel() {
		this.setLayout(new MigLayout("insets 1"));

		StartTimePicker =  new TimePicker();
		EndTimePicker = new TimePicker();
		errorLabel=new JLabel(defaultErrorText_);
		errorLabel.setForeground(Color.RED);
		errorLabel.setVisible(false);
		
		//TODO: add a listener to each TimePicker to validate start tiem is before end time and change the visibility of the error label	
		StartTimePicker.addTimeChangedEventListener(new TimeChangedEventListener() {
			public void TimeChangedEventOccurred(TimeChangedEvent e) {
				validateStartEndTime();
			}
		});
		EndTimePicker.addTimeChangedEventListener(new TimeChangedEventListener() {
			public void TimeChangedEventOccurred(TimeChangedEvent e) {
				validateStartEndTime();
			}
		});
		
		this.add(StartTimePicker, "alignx left, wrap");
		this.add(EndTimePicker, "alignx left, wrap");
		this.add(errorLabel,  "alignx left, wrap");
	}

	private boolean validateStartEndTime(){
		Date startTime=StartTimePicker.getTime();
		Date endTime=EndTimePicker.getTime();
		boolean endTimeIsAfterStartTime=true;
		if (startTime.after(endTime)){
			errorLabel.setText(defaultErrorText_);
			endTimeIsAfterStartTime=false;
		}else if (startTime.equals(endTime)){
			errorLabel.setText("ERROR: Start and End time cannot be the same!");
			endTimeIsAfterStartTime=false;
		}
		errorLabel.setVisible(!endTimeIsAfterStartTime);
		return endTimeIsAfterStartTime;
	}
		
	public boolean isValidTime() {
		return (StartTimePicker.hasValidTime() && EndTimePicker.hasValidTime() &&  validateStartEndTime());
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
