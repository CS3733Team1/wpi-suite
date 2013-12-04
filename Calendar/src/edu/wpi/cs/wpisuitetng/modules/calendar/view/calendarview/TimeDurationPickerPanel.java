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

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import edu.wpi.cs.wpisuitetng.modules.calendar.Calendar;

import net.miginfocom.swing.MigLayout;

public class TimeDurationPickerPanel extends JPanel {

	private TimePicker StartTimePicker;
	private TimePicker EndTimePicker;
	private JLabel errorLabel;
	private String defaultErrorText_="ERROR: Start Time after End Time!";
	
	private Border errorBorder;
	private Border normalBorder;
	
	private Date startDay_;
	private Date endDay_;
	
	public void addTimeChangedEventListener(TimeChangedEventListener timeChangedEventListener) {
		listenerList.add(TimeChangedEventListener.class, timeChangedEventListener);
	}

	public void removeTimeChangedEventListener(TimeChangedEventListener listener) {
		listenerList.remove(TimeChangedEventListener.class, listener);
	}

	private void fireTimeChangedEvent(TimeChangedEvent evt) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i = i + 2) {
			if (listeners[i] == TimeChangedEventListener.class) {
				((TimeChangedEventListener) listeners[i + 1]).TimeChangedEventOccurred(evt);
			}
		}
	}
	
	private void TimeChanged(){
		fireTimeChangedEvent(new TimeChangedEvent(StartTimePicker.getTimeAsSting()+"-"+EndTimePicker.getTimeAsSting()));
	}
	
	public void setStartDay(Date day){
		startDay_=day;
//		System.out.println("Setting start Date: "+startDay_.toString());
//		validateStartEndTime();
	}
	public void setEndDay(Date day){
		endDay_=day;
//		System.out.println("Setting end Date: "+endDay_.toString());
		validateStartEndTime();
	}
	
	public TimeDurationPickerPanel() {
		errorBorder=BorderFactory.createLineBorder(new Color(255, 51, 51));
		normalBorder=BorderFactory.createLineBorder(new Color(255, 51, 51,0));
		
		startDay_=new Date();
		endDay_=new Date();
		
		this.setLayout(new MigLayout("insets 1"));

		StartTimePicker =  new TimePicker("Start Time:");
		EndTimePicker = new TimePicker("End Time:");
		errorLabel=new JLabel(defaultErrorText_);
		errorLabel.setForeground(Color.RED);
		errorLabel.setVisible(false);
		
		StartTimePicker.addTimeChangedEventListener(new TimeChangedEventListener() {
			public void TimeChangedEventOccurred(TimeChangedEvent e) {
				validateStartEndTime();
				TimeChanged();
			}
		});
		EndTimePicker.addTimeChangedEventListener(new TimeChangedEventListener() {
			public void TimeChangedEventOccurred(TimeChangedEvent e) {
				validateStartEndTime();
				TimeChanged();
			}
		});
		
		this.add(StartTimePicker, "alignx left, wrap");
		this.add(EndTimePicker, "alignx left, wrap");
		this.add(errorLabel,  "alignx left, wrap");
	}

	private boolean validateStartEndTime(){
		if (StartTimePicker.hasValidTime() && EndTimePicker.hasValidTime()){
			
			
			//take into account the start day
			Date startTime=StartTimePicker.getTime();
			startTime.setYear(startDay_.getYear());
			startTime.setMonth(startDay_.getMonth());
			startTime.setDate(startDay_.getDate());
			startTime.setSeconds(0);
			
			//take into account the end day
			Date endTime=EndTimePicker.getTime();
			endTime.setYear(endDay_.getYear());
			endTime.setMonth(endDay_.getMonth());
			endTime.setDate(endDay_.getDate());
			endTime.setSeconds(0);
			
//			System.out.println("Validating time duration for start time "+startTime.toString()+" end time "+endTime.toString());

			boolean endTimeIsAfterStartTime=true;
			if (startTime.after(endTime)){
				errorLabel.setText(defaultErrorText_);
				endTimeIsAfterStartTime=false;
			}else if (startTime.equals(endTime)){
				errorLabel.setText("ERROR: Start and End time cannot be the same!");
				endTimeIsAfterStartTime=false;
			}
			
			if (endTimeIsAfterStartTime){
				setNoError();
			}else{
				setError();
			}
			return endTimeIsAfterStartTime;
			
		}else{	//one of the timePickers has a bad time
//			System.out.println("One of the timepickers is bad - time duration is not valid");
			this.setBorder(errorBorder);	//let the user know something is wrong within this component
			return false;
		}
	}
	
	private void setError(){
		errorLabel.setVisible(true);
		this.setBorder(errorBorder);
	}
	
	private void setNoError(){
		errorLabel.setVisible(false);
		this.setBorder(normalBorder);
	}
	
	public boolean isValidTime() {
		return validateStartEndTime();
	}
	
	public Date getStartTime() {
		return StartTimePicker.getTime();
	}
	
	public Date getEndTime() {
		return EndTimePicker.getTime();
	}
	
	
	public void setActionListener(ActionListener al) {
//		StartTimePicker.addTimeChangedEventListener(al);
//		StartTimePicker.addTimeChangedEventListener(al);
	}
}
