/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Rolling Thunder
 ******************************************************************************/


package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.wpi.cs.wpisuitetng.modules.calendar.controller.AddEventController;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.CalendarModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;

import javax.swing.JInternalFrame;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

/**
 * This panel tab is added whenever the user wants to create a new event or edit an existing one
 * most of this is copied/based from the CommitmentPanel in the calendar->view
 * @version $Revision: 1.0 $
 * @author rbansal
 */
public class EventPanel extends JPanel implements KeyListener{
	CalendarView view;
	CalendarModel model;
	
	
	//errors thrown for improper input
	private final String START_AFTER_END_ERROR = 	"Start date cannot be after end date.";
	private final String INVALID_NAME_ERROR = 		"Iteration exists with given name.";
	private final String EMPTY_NAME_ERROR = 		"Name is required.";
	private final String DATES_REQ = 				"Start and end date required.";
	private final String PAST_ERROR = 				"Commitment cannot occur in the past.";
	private JPanel buttonPanel;
	
	private JButton buttonAdd;
	private JButton buttonCancel;
	private JButton buttonUndoChanges;
	
	private ErrorPanel errorDisplay;
	
	
	private ViewMode vm;
	private Event displayEvent;	//the Event currently being represented in this panel
	
	private boolean forceRemove = false;
	private JPanel dataPanel;
	private JTextField nameTextField;
	private JLabel nameLabel;
	private JFormattedTextField dateFormattedTextField;
	private JTextField startTimeMinutesTextField;
	private JTextField startTimeHoursTextField;
	private JLabel endTimeLabel;
	private JTextField endTimeHoursTextField;
	private JLabel endTimeColonLabel;
	private JTextField endTimeMinutesTextField;
	private JComboBox<String> endTimeDayNightComboBox;
	private JComboBox<String> startTimeDayNightComboBox;
	
	/**
	 * The constructor for the event panel when creating a new event.
	 * @wbp.parser.constructor
	 */
	public EventPanel(CalendarView view, CalendarModel model) {
		this.view=view;
		this.model=model;
		
		//this.vm = ViewMode.CREATING;
		displayEvent = new Event();	//start by displaying an empty event
		buildLayout();
		refreshPanel();
	}
	
	/**
	 * Constructor for the iteration panel when editing an iteration
	 * @param iter the iteration to edit.
	 */
	public EventPanel(Event event, CalendarView view, CalendarModel model) {
		this.view=view;
		this.model=model;
		
		//this.vm = ViewMode.EDITING;
		displayEvent = event;
		buildLayout();
		populateInformation();
		refreshPanel();
	}
	
	/**
	 * Builds the GUI layout for the iteration panel
	 */
	private void buildLayout(){
		
		this.setLayout(new BorderLayout());
		
		//add event (submit) button
		String addButtonText = (vm == ViewMode.EDITING ? "Update Event" : "Add Event");
		buttonAdd = new JButton(addButtonText);
		buttonAdd.setAlignmentX(LEFT_ALIGNMENT);
		buttonAdd.setEnabled(false);

		//undo changes (revert) button
		buttonUndoChanges = new JButton("Undo Changes");
		buttonUndoChanges.setAlignmentX(LEFT_ALIGNMENT);
		buttonUndoChanges.setEnabled(false);
		buttonUndoChanges.setVisible(vm == ViewMode.EDITING);
		
		//cancel button
		buttonCancel = new JButton("Cancel");
		buttonCancel.setAlignmentX(LEFT_ALIGNMENT);
		
		//error panel
		errorDisplay = new ErrorPanel();
		
		//panel for the add, undo, and cancel buttons, and the error display
		buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		buttonPanel.setAlignmentX(LEFT_ALIGNMENT);
		buttonPanel.add(buttonAdd);
		buttonPanel.add(buttonUndoChanges);
		buttonPanel.add(buttonCancel);
		buttonPanel.add(errorDisplay);
		
		
		
		buttonAdd.addActionListener(new AddEventController(this.view, this.model));
		
		buttonUndoChanges.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				populateInformation();
				buttonUndoChanges.setEnabled(false);
			}
		});



		//TODO: add action listener and give it our AddEventController
//		buttonUndoChanges.addActionListener(new ActionListener(){
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				populateInformation();
//				buttonUndoChanges.setEnabled(false);
//			}
//		});
		
		buttonCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				killPanel();
			}
		});
		this.add(buttonPanel, BorderLayout.SOUTH);
		
		dataPanel = new JPanel();
		add(dataPanel, BorderLayout.CENTER);
		dataPanel.setLayout(null);
		
		nameLabel = new JLabel("Event Name");
		nameLabel.setBounds(9, 44, 95, 14);
		nameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		dataPanel.add(nameLabel);
		
		nameTextField = new JTextField();
		nameTextField.setBounds(114, 41, 117, 20);
		nameTextField.setHorizontalAlignment(SwingConstants.LEFT);
		dataPanel.add(nameTextField);
		nameTextField.setColumns(10);
		
		JLabel dateLabel = new JLabel("Date of Event");
		dateLabel.setHorizontalAlignment(SwingConstants.LEFT);
		dateLabel.setBounds(9, 72, 95, 14);
		dataPanel.add(dateLabel);
		
		dateFormattedTextField = new JFormattedTextField();
		dateFormattedTextField.setText("mm/dd/yyyy");
		dateFormattedTextField.setHorizontalAlignment(SwingConstants.LEFT);
		dateFormattedTextField.setColumns(10);
		dateFormattedTextField.setBounds(114, 69, 117, 20);
		dataPanel.add(dateFormattedTextField);
		
		JLabel startTimeLabel = new JLabel("Start Time");
		startTimeLabel.setHorizontalAlignment(SwingConstants.LEFT);
		startTimeLabel.setBounds(9, 100, 95, 14);
		dataPanel.add(startTimeLabel);
		
		startTimeMinutesTextField = new JTextField();
		startTimeMinutesTextField.setText("mm");
		startTimeMinutesTextField.setHorizontalAlignment(SwingConstants.CENTER);
		startTimeMinutesTextField.setColumns(10);
		startTimeMinutesTextField.setBounds(150, 97, 26, 20);
		dataPanel.add(startTimeMinutesTextField);
		
		JLabel startTimeColonLabel = new JLabel(":");
		startTimeColonLabel.setBounds(144, 100, 12, 14);
		dataPanel.add(startTimeColonLabel);
		
		startTimeDayNightComboBox = new JComboBox<String>();
		startTimeDayNightComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"AM", "PM"}));
		startTimeDayNightComboBox.setBounds(181, 97, 50, 20);
		dataPanel.add(startTimeDayNightComboBox);
		
		startTimeHoursTextField = new JTextField();
		startTimeHoursTextField.setText("hh");
		startTimeHoursTextField.setHorizontalAlignment(SwingConstants.CENTER);
		startTimeHoursTextField.setColumns(10);
		startTimeHoursTextField.setBounds(114, 97, 26, 20);
		dataPanel.add(startTimeHoursTextField);
		
		endTimeLabel = new JLabel("End Time");
		endTimeLabel.setHorizontalAlignment(SwingConstants.LEFT);
		endTimeLabel.setBounds(9, 128, 95, 14);
		dataPanel.add(endTimeLabel);
		
		endTimeHoursTextField = new JTextField();
		endTimeHoursTextField.setText("hh");
		endTimeHoursTextField.setHorizontalAlignment(SwingConstants.CENTER);
		endTimeHoursTextField.setColumns(10);
		endTimeHoursTextField.setBounds(114, 125, 26, 20);
		dataPanel.add(endTimeHoursTextField);
		
		endTimeColonLabel = new JLabel(":");
		endTimeColonLabel.setBounds(144, 128, 12, 14);
		dataPanel.add(endTimeColonLabel);
		
		endTimeMinutesTextField = new JTextField();
		endTimeMinutesTextField.setText("mm");
		endTimeMinutesTextField.setHorizontalAlignment(SwingConstants.CENTER);
		endTimeMinutesTextField.setColumns(10);
		endTimeMinutesTextField.setBounds(150, 125, 26, 20);
		dataPanel.add(endTimeMinutesTextField);
		
		endTimeDayNightComboBox = new JComboBox<String>();
		endTimeDayNightComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"AM", "PM"}));
		endTimeDayNightComboBox.setBounds(181, 125, 50, 20);
		dataPanel.add(endTimeDayNightComboBox);
	}
	
	public void killPanel(){
		view.getCalendarPanel().remove(this);
	}
	
	/**
	 * Updates the display event
	 */
	private void updateDisplayEvent()
	{
		displayEvent.setName(nameTextField.getText());
		displayEvent.setEndDate(new Date());

		if(vm == ViewMode.CREATING)
		{
			//TODO: create this event based on the current information of the UI components and add it to the model
			
//			int id = EventModel.getInstance().getNextID();
//			displayEvent.setId(id);
//			EventModel.getInstance().addEvent(displayEvent);
		}
		else
		{
			//TODO: update the model with this new event
			
//			UpdateEventController.getInstance().updateEvent(displayEvent);
		}
		
		//Why are they removing this tab after updating the information being displayed?
		
//		forceRemove = true;
//		
//		ViewEventController.getInstance().removeTab(IterationPanel.this);
	}
	
	/**
	 * Populate the information in the iteration panel for
	 * the display requirement.
	 */
	private void populateInformation()
	{
		this.nameTextField.setText(displayEvent.getName());
		this.dateFormattedTextField.setValue(displayEvent.getEndDate());
		refreshPanel();
	}
	
	
	/**
	 * Sets the dates for the event panel
	 * @param dueDate the due date
	 */
	public void setDueDate(Date dueDate)
	{
		if(dueDate != null) this.dateFormattedTextField.setValue(dueDate);
		
		refreshPanel();
	}
	
	/**
	 * Refreshes the panel
	 */
	private void refreshPanel()
	{
		updateDisplayEvent();
		validateFields();
		checkForChanges();
//		if(vm == ViewMode.EDITING) ;//refreshEstimate();
	}
	
	/**
	 * Validates the options of the fields inputted.
	 */
	private void validateFields()
	{
//		errorDisplay.removeAllErrors();
//		Calendar cal = new GregorianCalendar();
//		cal.setTime(Calendar.getInstance().getTime());
//		cal.add(Calendar.DAY_OF_YEAR, -1);
//		Event forName;//TODO: get the Event from the model with the name in our boxName textbox // = EventModel.getInstance().getIteration(boxName.getText().trim());
//		if(boxName.getText().trim().length() == 0)
//		{
//			errorDisplay.displayError(EMPTY_NAME_ERROR);
//		}
//		
//		else if(forName != null && forName != displayEvent)	//the name is already taken
//		{
//			errorDisplay.displayError(INVALID_NAME_ERROR);
//		}
		
//		if(dueDateBox.getText().trim().length() == 0 || dueDateBox.getText().trim().length() == 0)
//		{
//			errorDisplay.displayError(DATES_REQ);
//		}
		
		//TODO: check if the due date is in the calendar's past
		
//		else if(((Date)dueDateBox.getValue()).before(cal.getTime()))
//		{
//			errorDisplay.displayError(PAST_ERROR);
//		}
		
		//events can't conflict with each other, so we don't check this
//		else
//		{
//			Event conflicting = EventModel.getInstance().getConflictingIteration((Date)dueDateBox.getValue());
//			if(conflicting != null && conflicting != displayIteration)
//			{
//				errorDisplay.displayError(OVERLAPPING_ERROR + " Overlaps with " + conflicting.getName() + ".");
//			}
//		}
		
		buttonAdd.setEnabled(!errorDisplay.hasErrors());
	}
	
	/**
	 * Checks whether anything changed and updates buttons as needed.
	
	 * @return boolean */
	private boolean checkForChanges()
	{
//		boolean nameChanged = false;
//		boolean dueDateChanged = false;
//		if(vm == ViewMode.CREATING)
//		{
//			nameChanged = !boxName.getText().trim().equals("");
//			dueDateChanged = !dueDateBox.getText().equals("");
//		}
//		else
//		{
//			nameChanged = !boxName.getText().equals(displayEvent.getName());
//			Date dueDate = (Date)dueDateBox.getValue();
//			
//			dueDateChanged = !dueDate.equals(displayEvent.getDueDate().getDate());
//		}
//		
//		boolean anythingChanged = nameChanged || dueDateChanged;
//		buttonAdd.setEnabled(buttonAdd.isEnabled() && anythingChanged);
//		buttonUndoChanges.setEnabled(anythingChanged);
//		return anythingChanged;
		return false;
	}

	/**
	 * Method keyTyped.
	 * @param e KeyEvent
	
	 * @see java.awt.event.KeyListener#keyTyped(KeyEvent) */
	@Override
	public void keyTyped(KeyEvent e) {
	}

	/**
	 * Method keyPressed.
	 * @param e KeyEvent
	
	 * @see java.awt.event.KeyListener#keyPressed(KeyEvent) */
	@Override
	public void keyPressed(KeyEvent e) {
	}

	/**
	 * Method keyReleased.
	 * @param e KeyEvent
	
	 * @see java.awt.event.KeyListener#keyReleased(KeyEvent) */
	@Override
	public void keyReleased(KeyEvent e) {
	}

	/**
	 * 
	
	 * @return the display event */
	public Event getDisplayEvent() {
		return displayEvent;
	}

	/**
	
	 * @return whether the iteration panel as a whole is ready to be removed. */
	public boolean readyToRemove() {
		boolean readyToRemove;
		
		if(forceRemove) return true;
		
		if(checkForChanges())
		{
			int result = JOptionPane.showConfirmDialog(this, "Discard unsaved changes and close tab?", "Discard Changes?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			readyToRemove = result == 0;		
		}
		else
		{
			readyToRemove = true;
		}
		
		return readyToRemove;
	}
	
	/**
	* Overrides the paintComponent method to retrieve the requirements on the first painting.
	* 
	 * @param g	The component object to paint
	 */
	@Override
	public void paintComponent(Graphics g)
	{
		refreshPanel();
		super.paintComponent(g);
	}
}
