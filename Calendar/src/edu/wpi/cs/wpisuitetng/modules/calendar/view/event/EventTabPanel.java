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

import java.awt.Checkbox;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarPicker;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.category.CategoryPickerPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.DateTimeChangedEvent;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.DateTimeChangedEventListener;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.TimeDurationChooser;


public class EventTabPanel extends JPanel implements KeyListener, ActionListener {
	// Errors strings
	private final String EMPTY_NAME_ERROR = "Name is required.";

	// Data entry components
	private JTextField nameTextField;
	private TimeDurationChooser durationChooser_;
	private CategoryPickerPanel categoryPickerPanel;
	private CalendarPicker calendarPicker;
	private JTextArea descriptionTextArea;

	private Checkbox allDayEventCheckbox;
	boolean allDayEvent_;
	
	// Buttons
	private JButton addEventButton;
	private JButton cancelButton;

	// Error Labels
	private JLabel nameErrorLabel;

	// Error wrappers
	private JPanel nameErrorPanelWrapper;
	private EventRecurringPanel eventRecurringPanel;

	public EventTabPanel() {
		this.buildLayout();
	}

	/**
	 * Builds the GUI layout for the Event panel
	 */
	private void buildLayout() {
		this.setLayout(new MigLayout("fill", "[]", "[][][][][][][][][][][]"));

		// Name
		this.add(new JLabel("Event Name:"), "cell 0 0");
		nameErrorPanelWrapper = new JPanel(new MigLayout("fill, insets 0"));
		nameTextField = new JTextField();
		nameTextField.addKeyListener(this);
		nameErrorPanelWrapper.add(nameTextField, "alignx left, growx, push, w 5000");
		this.add(nameErrorPanelWrapper, "cell 0 0,push ,growx,width 5000,alignx left");
		nameErrorLabel = new JLabel(EMPTY_NAME_ERROR);
		nameErrorLabel.setForeground(Color.RED);
<<<<<<< HEAD
		this.add(nameErrorLabel, "cell 0 0");
=======
		this.add(nameErrorLabel, "wrap");

		//all day event checkbox (uncomment for checkbox, but note it doesn't do anything yet)
//		allDayEvent_=false;
//		allDayEventCheckbox=new Checkbox("All Day Event");
//		allDayEventCheckbox.addItemListener(new ItemListener(){
//			@Override
//			public void itemStateChanged(ItemEvent e) {
//				allDayEventCheckboxChanged();
//			}
//		});
//		this.add(allDayEventCheckbox, "wrap");
>>>>>>> 1220bbc954826120d0be66e379f0e67b9e0da849
		
		//duration
		durationChooser_=new TimeDurationChooser();
		durationChooser_.addDateTimeChangedEventListener(new DateTimeChangedEventListener(){
			@Override
			public void DateTimeChangedEventOccurred(DateTimeChangedEvent evt) {
//				System.out.println("Time Duration Changed");
				validateFields();
			}
		});
<<<<<<< HEAD
		add(durationChooser_, "cell 0 2, alignx left");
		
		// Recurring Events
		eventRecurringPanel = new EventRecurringPanel(new Date());
		add(eventRecurringPanel, "cell 0 3,alignx left");

=======
		this.add(durationChooser_, "alignx left, wrap");
>>>>>>> 1220bbc954826120d0be66e379f0e67b9e0da849

		// Category
		this.add(new JLabel("Category:"), "cell 0 4");
		categoryPickerPanel = new CategoryPickerPanel();
		this.add(categoryPickerPanel, "cell 0 4,alignx left");

		// Calendar
		this.add(new JLabel("Calendar:"), "split 2");
		calendarPicker = new CalendarPicker();
		this.add(calendarPicker, "alignx left, wrap");

		// Description
		this.add(new JLabel("Description:"), "cell 0 5");

		descriptionTextArea = new JTextArea();
		descriptionTextArea.setLineWrap(true);
		descriptionTextArea.setWrapStyleWord(true);

		JScrollPane scrollp = new JScrollPane(descriptionTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		this.add(scrollp, "cell 0 9,push ,height 5000,grow");

		// Add / Cancel buttons
		addEventButton = new JButton("Add Event");
		addEventButton.setActionCommand("addevent");
		addEventButton.addActionListener(new AddEventController(this));

		this.add(addEventButton, "cell 0 10,alignx left");

		cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("cancel");

		this.add(cancelButton, "cell 0 10,alignx left");

		//Action Listener for Cancel Button
		cancelButton.addActionListener(this);

		validateFields();
	}

	// Kills the add event tab
	public void closeEventPanel() {
		this.getParent().remove(this);
	}

	/**
	 * Validates the options of the fields inputed.
	 * @return void
	 */
	private void validateFields() {		
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

		if (!allDayEvent_){
			//validate times and duration between them
			if (!durationChooser_.hasValidDuration()){
				enableAddEvent=false;
			}
		}

		addEventButton.setEnabled(enableAddEvent);
	}

	public Event getFilledEvent() {
		Date startDate=durationChooser_.getStartDate();
		Date endDate=durationChooser_.getEndDate();
		
		//make a new event with start and end times
		return new Event(nameTextField.getText(), startDate, endDate, calendarPicker.isTeam(), 
				descriptionTextArea.getText(), categoryPickerPanel.getSelectedCategory());
	}

	public void allDayEventCheckboxChanged(){
		allDayEvent_=allDayEventCheckbox.getState();
		if (allDayEvent_){
			durationChooser_.disable();
		}else{
			durationChooser_.enable();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
//		System.out.println("All Day Checkbox Changed!");
		if(e.getActionCommand().equals("cancel")) {
			this.closeEventPanel();
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
