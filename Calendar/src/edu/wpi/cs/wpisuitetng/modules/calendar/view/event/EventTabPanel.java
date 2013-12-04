/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.view.event;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.event.AddEventController;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.DatePickerPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.TimeChangedEvent;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.TimeChangedEventListener;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.TimeDurationPickerPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.category.CategoryPickerPanel;

public class EventTabPanel extends JPanel implements KeyListener, ActionListener {
	// Errors strings
	private final String START_AFTER_END_ERROR = 	"Start time cannot be after end time.";
	private final String EMPTY_NAME_ERROR = 		"Name is required.";
	private final String INVALID_DATE_ERROR =		"Invalid date. Use: www mm/dd/yyyy ----EX: Sun 04/06/2014";
	private final String PAST_DATE_ERROR = 			"Event cannot occur in the past.";
	private final String ZERO_TIME_ERROR = 			"Event must have a duration greater than 0 minutes.";

	// Data entry components
	private JTextField nameTextField;
	private DatePickerPanel startDatePickerPanel;
	private DatePickerPanel endDatePickerPanel;
	private TimeDurationPickerPanel timeDurationPickerPanel;
	private CategoryPickerPanel categoryPickerPanel;
	private JTextArea descriptionTextArea;

	// Buttons
	private JButton addEventButton;
	private JButton cancelButton;

	// Error Labels
	private JLabel nameErrorLabel;
	private JLabel startDateErrorLabel;
	private JLabel endDateErrorLabel;
	private JLabel timeErrorLabel;

	// Error wrappers
	private JPanel nameErrorPanelWrapper;

	public EventTabPanel() {
		this.buildLayout();
	}

	/**
	 * Builds the GUI layout for the Event panel
	 */
	private void buildLayout() {
		this.setLayout(new MigLayout("fill"));

		// Name
		this.add(new JLabel("Event Name:"), "split 3");
		nameErrorPanelWrapper = new JPanel(new MigLayout("fill, insets 0"));
		nameTextField = new JTextField();
		nameTextField.addKeyListener(this);
		nameErrorPanelWrapper.add(nameTextField, "alignx left, growx, push, w 5000");
		this.add(nameErrorPanelWrapper, "alignx left, growx, push, w 5000");
		nameErrorLabel = new JLabel(EMPTY_NAME_ERROR);
		nameErrorLabel.setForeground(Color.RED);
		this.add(nameErrorLabel, "wrap");

		// Start Date
		this.add(new JLabel("Start Date:"), "split 3");
		startDatePickerPanel = new DatePickerPanel();
		startDatePickerPanel.setKeyListener(this);
		this.add(startDatePickerPanel, "alignx left");
		startDateErrorLabel = new JLabel(INVALID_DATE_ERROR);
		startDateErrorLabel.setForeground(Color.RED);
		this.add(startDateErrorLabel, "wrap");

		// End Date
		this.add(new JLabel("End Date:"), "split 3");
		endDatePickerPanel = new DatePickerPanel();
		endDatePickerPanel.setKeyListener(this);
		this.add(endDatePickerPanel, "alignx left");
		endDateErrorLabel = new JLabel(INVALID_DATE_ERROR);
		endDateErrorLabel.setForeground(Color.RED);
		this.add(endDateErrorLabel, "wrap");
		
		// Start/End Time
		timeDurationPickerPanel = new TimeDurationPickerPanel();
		timeDurationPickerPanel.addTimeChangedEventListener(new TimeChangedEventListener() {
			public void TimeChangedEventOccurred(TimeChangedEvent e) {
				validateFields();
			}
		});
		this.add(timeDurationPickerPanel, "alignx left, split 2");
		timeErrorLabel = new JLabel();
		timeErrorLabel.setForeground(Color.RED);
		this.add(timeErrorLabel, "aligny center, wrap");

		// Category
		this.add(new JLabel("Category:"), "split 2");
		categoryPickerPanel = new CategoryPickerPanel();
		this.add(categoryPickerPanel, "alignx left, wrap");

		// Description
		this.add(new JLabel("Description:"), "wrap");

		descriptionTextArea = new JTextArea();
		descriptionTextArea.setLineWrap(true);
		descriptionTextArea.setWrapStyleWord(true);

		JScrollPane scrollp = new JScrollPane(descriptionTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		this.add(scrollp, "grow, push, span, h 5000, wrap");

		// Add / Cancel buttons
		addEventButton = new JButton("Add Event");
		addEventButton.setActionCommand("addevent");
		addEventButton.addActionListener(new AddEventController(this));

		this.add(addEventButton, "alignx left, split 2");

		cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("cancel");

		this.add(cancelButton, "alignx left");

		//Action Listener for Cancel Button
		cancelButton.addActionListener(this);

		validateFields();
	}

	// Kills the add event tab
	public void killEventPanel() {
		this.getParent().remove(this);
	}

	/**
	 * Validates the options of the fields inputed.
	 * @return void
	 */
	private void validateFields() {
//		System.out.println("Validating Event Fields");
		
		boolean enableAddEvent = true;

		//Check the name
		if(nameTextField.getText().trim().length() == 0) {
			nameErrorPanelWrapper.setBorder(BorderFactory.createLineBorder(new Color(255, 51, 51)));
			nameErrorLabel.setText(EMPTY_NAME_ERROR);
			nameErrorLabel.setVisible(true);
			enableAddEvent = false;
		} else {
			nameErrorPanelWrapper.setBorder(BorderFactory.createLineBorder(new Color(255, 51, 51, 0)));
			nameErrorLabel.setVisible(false);
		}

		//check the start date
		startDatePickerPanel.validateDate();
		if(startDatePickerPanel.isInvalidDate() == 1) {
			startDatePickerPanel.setBorder(BorderFactory.createLineBorder(new Color(255, 51, 51)));
			startDateErrorLabel.setText(INVALID_DATE_ERROR);
			startDateErrorLabel.setVisible(true);
			enableAddEvent = false;
		} else if (startDatePickerPanel.isInvalidDate() == 2) {
			startDatePickerPanel.setBorder(BorderFactory.createLineBorder(new Color(255, 51, 51)));
			startDateErrorLabel.setText(PAST_DATE_ERROR);
			startDateErrorLabel.setVisible(true);
			enableAddEvent = false;
		} else {
			startDatePickerPanel.setBorder(BorderFactory.createLineBorder(new Color(255, 51, 51, 0)));
			startDateErrorLabel.setVisible(false);
		}

		//check the end date
		endDatePickerPanel.validateDate();
		if(endDatePickerPanel.isInvalidDate() == 1) {
			endDatePickerPanel.setBorder(BorderFactory.createLineBorder(new Color(255, 51, 51)));
			endDateErrorLabel.setText(INVALID_DATE_ERROR);
			endDateErrorLabel.setVisible(true);
			enableAddEvent = false;
		} else if (endDatePickerPanel.isInvalidDate() == 2) {
			endDatePickerPanel.setBorder(BorderFactory.createLineBorder(new Color(255, 51, 51)));
			endDateErrorLabel.setText(PAST_DATE_ERROR);
			endDateErrorLabel.setVisible(true);
			enableAddEvent = false;
		} else {
			endDatePickerPanel.setBorder(BorderFactory.createLineBorder(new Color(255, 51, 51, 0)));
			endDateErrorLabel.setVisible(false);
		}
		
		//ensure the timeDurationPicker knows what days the times are for before checking if it's valid
		timeDurationPickerPanel.setStartDay(startDatePickerPanel.getDate());
		timeDurationPickerPanel.setEndDay(endDatePickerPanel.getDate());
		
		//check the time
		if (!timeDurationPickerPanel.isValidTime()){
//			System.out.println("\tCannot enable add Event button - bad time duration!");
			enableAddEvent=false;
		}
		
//		if(timeDurationPickerPanel.isInvalidTime() == 1) {
//			timeDurationPickerPanel.setBorder(BorderFactory.createLineBorder(new Color(255, 51, 51)));
//			timeErrorLabel.setText(ZERO_TIME_ERROR);
//			timeErrorLabel.setVisible(true);
//			enableAddEvent = false;
//		} else if (timeDurationPickerPanel.isInvalidTime() == 2) {
//			timeDurationPickerPanel.setBorder(BorderFactory.createLineBorder(new Color(255, 51, 51)));
//			timeErrorLabel.setText(START_AFTER_END_ERROR);
//			timeErrorLabel.setVisible(true);
//			enableAddEvent = false;
//		} else {
//			timeDurationPickerPanel.setBorder(BorderFactory.createLineBorder(new Color(255, 51, 51, 0)));
//			timeErrorLabel.setVisible(false);
//		}

		addEventButton.setEnabled(enableAddEvent);
	}

	public Event getFilledEvent() {
		//start date
		Date startDateDay = startDatePickerPanel.getDate();	//get's a Date which has the day of the start of the event
		//set a calendar thingy with this start day and the time from the timeDurationPicker start time
		Calendar startCal = Calendar.getInstance();
		startCal.set(Calendar.HOUR_OF_DAY, 0);
		startCal.set(Calendar.YEAR, startDateDay.getYear()+1900);
		startCal.set(Calendar.MONTH, startDateDay.getMonth());
		startCal.set(Calendar.DATE, startDateDay.getDate());
		startCal.set(Calendar.HOUR_OF_DAY, timeDurationPickerPanel.getStartTime().getHours());
		startCal.set(Calendar.MINUTE, timeDurationPickerPanel.getStartTime().getMinutes());
		startCal.set(Calendar.SECOND, 0);
		Date startDate = startCal.getTime();//convert the calendar back to a date

		//end date
		Date endDateDay = endDatePickerPanel.getDate();	//get's a Date which has the day of the end of the event
		//set a calendar thingy with this end day and the time from the timeDurationPicker end time
		Calendar endCal = Calendar.getInstance();
		endCal.set(Calendar.HOUR_OF_DAY, 0);
		endCal.set(Calendar.YEAR, endDateDay.getYear()+1900);
		endCal.set(Calendar.MONTH, endDateDay.getMonth());
		endCal.set(Calendar.DATE, endDateDay.getDate());
		endCal.set(Calendar.HOUR_OF_DAY, timeDurationPickerPanel.getEndTime().getHours());
		endCal.set(Calendar.MINUTE, timeDurationPickerPanel.getEndTime().getMinutes());
		endCal.set(Calendar.SECOND, 0);
		Date endDate = endCal.getTime();//convert the calendar back to a date

		//make a new event with start and end times
		return new Event(nameTextField.getText(), startDate, endDate,
				descriptionTextArea.getText(), categoryPickerPanel.getSelectedCategory());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("cancel")) {
			this.killEventPanel();
		} else {
			validateFields();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		validateFields();
	}

	// Unused
	@Override
	public void keyPressed(KeyEvent e) {}
	@Override
	public void keyTyped(KeyEvent e) {}
}
