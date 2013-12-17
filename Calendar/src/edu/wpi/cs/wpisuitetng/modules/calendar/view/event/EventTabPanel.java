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
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.commitment.UpdateCommitmentController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.event.AddEventController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.event.UpdateEventController;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarPicker;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.category.CategoryPickerPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.DateTimeChangedEvent;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.DateTimeChangedEventListener;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.DateTimeChooser;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.RecurringChangedEvent;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.RecurringChangedEventListener;
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
	private JDateChooser allDayTimeChooser_;
	boolean allDayEvent_;

	// Buttons
	private JButton addEventButton;
	private JButton cancelButton;

	// Error Labels
	private JLabel nameErrorLabel;

	// Error wrappers
	private JPanel nameErrorPanelWrapper;
	private EventRecurringPanel eventRecurringPanel;
	
	// Old Commitment
	private Event editEvent;
	private boolean isEditMode;

	public EventTabPanel() {
		this.buildLayout();
	}
	
	public EventTabPanel(Event e)
	{
		isEditMode = true;
		editEvent = e;

		this.buildLayout();
		
		//fill the fields with information from the provided Commitment
		nameTextField.setText(e.getName());
		durationChooser_.setStartDate(e.getStartDate());
		durationChooser_.setEndDate(e.getEndDate());
		categoryPickerPanel.setSelectedCategory(e.getCategory());
		descriptionTextArea.setText(e.getDescription());
		calendarPicker.setSelected(e.getisTeam() ? "Team": "Personal");
		//eventRecurringPanel.set
		addEventButton.setText("Update Event");
		addEventButton.setActionCommand("updateevent");
		addEventButton.removeActionListener(addEventButton.getActionListeners()[0]); //Remove the addCommitment action listener
		addEventButton.addActionListener(new UpdateEventController(this, e));	//Add the updateCommitment action listener
		addEventButton.setEnabled(false);
		validateFields();
	}
	
	public boolean validateEdit() {
		boolean noChangesMade = true;
		if(!editEvent.getName().equals(nameTextField.getText())) {
			noChangesMade = false;
		}
		else if(!editEvent.getStartDate().toString().equals(durationChooser_.getStartDate().toString())){
			noChangesMade = false;
		}
		else if(!editEvent.getEndDate().toString().equals(durationChooser_.getEndDate().toString())){
			noChangesMade = false;
		}
		else if(!editEvent.getCategory().equals(categoryPickerPanel.getSelectedCategory())){
			noChangesMade = false;
		}
		else if(!editEvent.getDescription().equals(descriptionTextArea.getText())){
			noChangesMade = false;
		}
		else if(editEvent.getisTeam() != calendarPicker.isTeam()){
			noChangesMade = false;
		}
		return noChangesMade;
	}

	/**
	 * Builds the GUI layout for the Event panel
	 */
	private void buildLayout() {
		this.setLayout(new MigLayout("fill", "[]", "[][][][][][][][][][]"));

		// Name
		this.add(new JLabel("Event Name:"), "cell 0 0");
		nameErrorPanelWrapper = new JPanel(new MigLayout("fill, insets 0"));
		nameTextField = new JTextField();
		nameTextField.addKeyListener(this);
		nameErrorPanelWrapper.add(nameTextField, "alignx left, growx, push, w 5000");
		this.add(nameErrorPanelWrapper, "cell 0 0,push ,growx,width 5000,alignx left");
		nameErrorLabel = new JLabel(EMPTY_NAME_ERROR);
		nameErrorLabel.setForeground(Color.RED);
		this.add(nameErrorLabel, "cell 0 0");
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

		// All Day Event
		allDayEvent_ = false;
		allDayEventCheckbox = new Checkbox("Is this an all day event?");
		ItemListener itemListener = new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				allDayEventCheckboxChanged();
			}
		};
		allDayEventCheckbox.addItemListener(itemListener);
		this.add(allDayEventCheckbox, "wrap");

		// All Day Event Date Chooser
		//DateChooser for choosing days
		allDayTimeChooser_=new JDateChooser(new Date());
		allDayTimeChooser_.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				dayChanged();
				validateFields();
			}
		});
		add(allDayTimeChooser_, "wmin 100");


		//duration
		durationChooser_=new TimeDurationChooser();
		durationChooser_.addDateTimeChangedEventListener(new DateTimeChangedEventListener(){
			@Override
			public void DateTimeChangedEventOccurred(DateTimeChangedEvent evt) {
				//				System.out.println("Time Duration Changed");
				validateFields();
			}
		});
		add(durationChooser_, "cell 0 1, alignx left");


		this.add(durationChooser_, "alignx left, wrap");

		// Calendar
		JLabel label = new JLabel("Calendar:");
		this.add(label, "flowx,cell 0 3");

		// Recurring Events
		eventRecurringPanel = new EventRecurringPanel(new Date());
		eventRecurringPanel.addRecurringChangedEventListener(new RecurringChangedEventListener() {

			@Override
			public void RecurringChangedEventOccurred(RecurringChangedEvent evt) {
				validateFields();
			}
		});
		add(eventRecurringPanel, "cell 0 4,alignx left");


		// Category
		this.add(new JLabel("Category:"), "cell 0 5");
		categoryPickerPanel = new CategoryPickerPanel();
		this.add(categoryPickerPanel, "cell 0 5,alignx left");

		// Description
		this.add(new JLabel("Description:"), "cell 0 6");
		descriptionTextArea = new JTextArea();
		descriptionTextArea.setLineWrap(true);
		descriptionTextArea.setWrapStyleWord(true);

		JScrollPane scrollp = new JScrollPane(descriptionTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		this.add(scrollp, "cell 0 6,push ,height 5000,grow");

		// Add / Cancel buttons
		addEventButton = new JButton("Add Event");
		addEventButton.setActionCommand("addevent");
		addEventButton.addActionListener(new AddEventController(this));

		this.add(addEventButton, "cell 0 8,alignx left");

		cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("cancel");

		this.add(cancelButton, "cell 0 8,alignx left");

		//Action Listener for Cancel Button
		cancelButton.addActionListener(this);
		calendarPicker = new CalendarPicker();
		this.add(calendarPicker, "cell 0 3,alignx left");

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
		
		if(isEditMode) {
			System.out.println("Edit Mode");
			enableAddEvent = !validateEdit();
		}

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
			allDayTimeChooser_.setEnabled(false);
			//validate times and duration between them
			if (!durationChooser_.hasValidDuration()){
				enableAddEvent=false;
			}
		} else {
			allDayTimeChooser_.setEnabled(true);
			Date currDate_ = new Date();
			if(!(currDate_.before(allDayTimeChooser_.getDate()))) {
				enableAddEvent=false;
			}
		}

		if(!eventRecurringPanel.validateRecurring(durationChooser_.getStartDate()))
		{
			enableAddEvent = false;
		}

		addEventButton.setEnabled(enableAddEvent);
	}

	public Event getFilledEvent() {
		Date startDate;
		Date endDate;
		if(!allDayEvent_){
			startDate=durationChooser_.getStartDate();
			endDate=durationChooser_.getEndDate();
		} else {
			Date temp = allDayTimeChooser_.getDate();
			startDate = temp;
			temp.setHours(24);
			endDate = temp;
		}

		//make a new event with start and end times
		return new Event(nameTextField.getText(), startDate, endDate, calendarPicker.isTeam(), 
				descriptionTextArea.getText(), categoryPickerPanel.getSelectedCategory());
	}

	public void allDayEventCheckboxChanged(){
		allDayEvent_=allDayEventCheckbox.getState();
		if (!allDayEvent_){
			durationChooser_.enable();
			allDayTimeChooser_.setEnabled(false);
		}else{
			durationChooser_.disable();
			allDayTimeChooser_.setEnabled(true);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//System.out.println("All Day Checkbox Changed!");
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

	public int numOfEvents() {
		return eventRecurringPanel.getOccurrences();
	}

	public ArrayList<Event> getFilledEvents() {
		int numOfEvents = numOfEvents();

		ArrayList<Event> eventList = new ArrayList<Event>();
		Date startDate;
		Date endDate;
		if(!allDayEvent_) {
			startDate=durationChooser_.getStartDate();
			endDate=durationChooser_.getEndDate();
		} else {
			startDate = allDayTimeChooser_.getDate();
			Date tempDate = allDayTimeChooser_.getDate();
			tempDate.setHours(23);
			tempDate.setHours(0);
			allDayTimeChooser_.setDate(tempDate);
			endDate = allDayTimeChooser_.getDate();
		}
		if(numOfEvents == 1)
		{
			eventList.add(getFilledEvent());
			return eventList;
		}
		for(int i = 0, day = startDate.getDay(); i < numOfEvents; ++day)
		{
			if(day == 7)
				day = 0;
			if(eventRecurringPanel.isDaySelected(day))
			{
				eventList.add(new Event(nameTextField.getText(), (Date) startDate.clone(), (Date) endDate.clone(), calendarPicker.isTeam(), 
						descriptionTextArea.getText(), categoryPickerPanel.getSelectedCategory()));
				++i;
			}
			startDate.setDate(startDate.getDate() + 1);
			endDate.setDate(endDate.getDate() + 1);
			//make a new event with start and end times

		}
		return eventList;
	}

	private void dayChanged(){
		//System.out.println("Day Changed");
		//eventRecurringPanel.setChkBox(allDayTimeChooser_.getDate());
		fillDay();
		//DateTimeChanged();
	}

	private void fillDay(){
		//System.out.println("\nFilling Day...");
		Date day=allDayTimeChooser_.getDate();
		day.setHours(0);

	}

	private long normalizeToTheMinute(long timeMs){
		long minutes=timeMs/60000;//count the minutes (integer)
		return minutes*60000+59999;//make it right at the end of the millis
	}

}
