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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.jdesktop.swingx.calendar.DateSelectionModel.SelectionMode;
import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.event.AddEventController;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.ErrorPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.ViewMode;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.DatePickerPanel;

/**
 * This panel tab is added whenever the user wants to create a new event or edit an existing one
 * most of this is copied/based from the CommitmentPanel in the calendar->view
 * @version $Revision: 1.0 $
 * @author rbansal
 */

public class EventTabPanel extends JPanel implements KeyListener, MouseListener{
	//errors thrown for improper input
	private final String START_AFTER_END_ERROR = 	"Start time cannot be after end time.";
	private final String INVALID_TIME_ERROR =       "Invalid time entered";
	private final String INVALID_NAME_ERROR = 		"Iteration exists with given name.";
	private final String EMPTY_NAME_ERROR = 		"Name is required.";
	private final String DATES_REQ = 				"Start and end date required.";
	private final String PAST_ERROR = 				"Commitment cannot occur in the past.";
	private final String ZERO_TIME_ERROR = 			"Event must have a duration greater than 0 minutes.";

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
	private JTextField endTimeMinutesTextField;
	private JComboBox<String> endTimeDayNightComboBox;
	private JComboBox<String> startTimeDayNightComboBox;
	private JLabel startTimeColonLabel;
	private JLabel endTimeColonLabel;
	private JLabel nameErrorLabel;
	private JLabel dateErrorLabel;
	private JLabel startTimeErrorLabel;
	private JLabel endTimeErrorLabel;
	//TODO remove the initialization here and change as necessary for two date pickers
	private DatePickerPanel dateCalendar = new DatePickerPanel(SelectionMode.SINGLE_INTERVAL_SELECTION);


	/**
	 * The constructor for the event panel when creating a new event.
	 * @wbp.parser.constructor
	 */
	public EventTabPanel() {
		//this.vm = ViewMode.CREATING;
		displayEvent = new Event();	//start by displaying an empty event
		buildLayout();
		refreshPanel();
	}

	/**
	 * Constructor for the iteration panel when editing an iteration
	 * @param iter the iteration to edit.
	 */
	public EventTabPanel(Event event) {		
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

		buttonAdd.addActionListener(new AddEventController(this));

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
		dataPanel.setForeground(Color.RED);
		add(dataPanel, BorderLayout.CENTER);
		dataPanel.setLayout(new MigLayout("", "[95px][26px][][32px][5px][][50px][][]", "[17.00][20px][20px][20px][20px]"));

		nameLabel = new JLabel("Event Name");
		nameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		dataPanel.add(nameLabel, "cell 0 1,growx,aligny center");

		nameTextField = new JTextField();
		nameTextField.setHorizontalAlignment(SwingConstants.LEFT);
		nameTextField.addKeyListener(this);
		dataPanel.add(nameTextField, "cell 1 1 6 1,growx,aligny top");
		nameTextField.setColumns(10);

		nameErrorLabel = new JLabel("");
		nameErrorLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		nameErrorLabel.setForeground(Color.RED);
		dataPanel.add(nameErrorLabel, "cell 7 1 2 1");

		JLabel dateLabel = new JLabel("Date of Event");
		dateLabel.setHorizontalAlignment(SwingConstants.LEFT);
		dataPanel.add(dateLabel, "cell 0 2,growx,aligny center");

		dateCalendar.addMouseListener(this);
		dataPanel.add(dateCalendar, "cell 1 2 6 1,growx,aligny top");

		dateErrorLabel = new JLabel("");
		dateErrorLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		dateErrorLabel.setForeground(Color.RED);
		dataPanel.add(dateErrorLabel, "cell 7 2 2 1");

		JLabel startTimeLabel = new JLabel("Start Time");
		startTimeLabel.setHorizontalAlignment(SwingConstants.LEFT);
		dataPanel.add(startTimeLabel, "cell 0 3,growx,aligny center");

		startTimeColonLabel = new JLabel(":");
		dataPanel.add(startTimeColonLabel, "cell 2 3");

		startTimeMinutesTextField = new JTextField();
		startTimeMinutesTextField.setText("mm");
		startTimeMinutesTextField.setHorizontalAlignment(SwingConstants.CENTER);
		startTimeMinutesTextField.setColumns(10);
		startTimeMinutesTextField.addKeyListener(this);
		dataPanel.add(startTimeMinutesTextField, "cell 3 3,growx,aligny top");

		startTimeHoursTextField = new JTextField();
		startTimeHoursTextField.setText("hh");
		startTimeHoursTextField.setHorizontalAlignment(SwingConstants.CENTER);
		startTimeHoursTextField.setColumns(10);
		startTimeHoursTextField.addKeyListener(this);
		dataPanel.add(startTimeHoursTextField, "cell 1 3,growx,aligny top");

		startTimeDayNightComboBox = new JComboBox<String>();
		startTimeDayNightComboBox.addMouseListener(this);
		startTimeDayNightComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"AM", "PM"}));
		dataPanel.add(startTimeDayNightComboBox, "cell 5 3,growx,aligny top");

		startTimeErrorLabel = new JLabel("");
		startTimeErrorLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		startTimeErrorLabel.setForeground(Color.RED);
		dataPanel.add(startTimeErrorLabel, "cell 6 3 3 1");

		endTimeLabel = new JLabel("End Time");
		endTimeLabel.setHorizontalAlignment(SwingConstants.LEFT);
		dataPanel.add(endTimeLabel, "cell 0 4,growx,aligny center");

		endTimeHoursTextField = new JTextField();
		endTimeHoursTextField.setText("hh");
		endTimeHoursTextField.setHorizontalAlignment(SwingConstants.CENTER);
		endTimeHoursTextField.setColumns(10);
		endTimeHoursTextField.addKeyListener(this);
		dataPanel.add(endTimeHoursTextField, "cell 1 4,growx,aligny top");

		endTimeColonLabel = new JLabel(":");
		dataPanel.add(endTimeColonLabel, "cell 2 4");

		endTimeMinutesTextField = new JTextField();
		endTimeMinutesTextField.setText("mm");
		endTimeMinutesTextField.setHorizontalAlignment(SwingConstants.CENTER);
		endTimeMinutesTextField.setColumns(10);
		endTimeMinutesTextField.addKeyListener(this);
		dataPanel.add(endTimeMinutesTextField, "cell 3 4,growx,aligny top");

		endTimeDayNightComboBox = new JComboBox<String>();
		endTimeDayNightComboBox.addMouseListener(this);
		endTimeDayNightComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"AM", "PM"}));
		dataPanel.add(endTimeDayNightComboBox, "cell 5 4,growx,aligny top");

		endTimeErrorLabel = new JLabel("");
		endTimeErrorLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		endTimeErrorLabel.setForeground(Color.RED);
		dataPanel.add(endTimeErrorLabel, "cell 6 4 3 1");
	}

	public void killPanel() {
		this.getParent().remove(this);
	}

	/**
	 * Updates the display event
	 */
	private void updateDisplayEvent()
	{
		displayEvent.setName(nameTextField.getText());
		displayEvent.setStartDate(dateCalendar.getStartDate());
		displayEvent.setEndDate(dateCalendar.getEndDate());


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
		//this.nameTextField.setText(displayEvent.getName());
		// displayEvent.getEndDate());
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
	 * Validates the options of the fields inputed.
	 */
	private void validateFields()
	{
		int startHours = 0, startMin = 0, endHours = 0, endMin = 0;
		boolean enableAdd = true;

		if(nameTextField.getText().trim().length() == 0) {
			nameErrorLabel.setVisible(true);
			nameErrorLabel.setText(EMPTY_NAME_ERROR);
			enableAdd = false;
		} else {
			nameErrorLabel.setVisible(false);
		}

		if(dateCalendar.getStartDate() == null) {
			dateErrorLabel.setVisible(true);
			dateErrorLabel.setText(DATES_REQ);
			enableAdd = false;
		} else {
			dateErrorLabel.setVisible(false);
		}

		try
		{  
			startHours = Integer.parseInt(startTimeHoursTextField.getText());  
			startMin =  Integer.parseInt(startTimeMinutesTextField.getText());
			if(startHours > 12 || startHours < 1 || startMin > 59 || startMin < 0)
			{
				startTimeErrorLabel.setVisible(true);
				startTimeErrorLabel.setText(INVALID_TIME_ERROR);
				enableAdd = false;
			}
			else
			{
				startTimeErrorLabel.setVisible(false);
			}
		}  
		catch(NumberFormatException nfe)  
		{ 
			startTimeErrorLabel.setVisible(true);
			startTimeErrorLabel.setText(INVALID_TIME_ERROR);
			enableAdd = false;
		}   
		
		try  
		{  
			endHours = Integer.parseInt(endTimeHoursTextField.getText());  
			endMin =  Integer.parseInt(endTimeMinutesTextField.getText());
			if(endHours > 12 || endHours < 1 || endMin > 59 || endMin < 0)
			{
				endTimeErrorLabel.setVisible(true);
				endTimeErrorLabel.setText(INVALID_TIME_ERROR);
				enableAdd = false;
			}
			else
			{
				endTimeErrorLabel.setVisible(false);
			}
		}  
		catch(NumberFormatException nfe)  
		{ 
			endTimeErrorLabel.setVisible(true);
			endTimeErrorLabel.setText(INVALID_TIME_ERROR);
			enableAdd = false;
		}   

		if(enableAdd && dateCalendar.getStartDate() == dateCalendar.getEndDate())
		{
			if(startTimeDayNightComboBox.getSelectedItem() == "PM" && endTimeDayNightComboBox.getSelectedItem() =="AM")
			{
				endTimeErrorLabel.setVisible(true);
				endTimeErrorLabel.setText(START_AFTER_END_ERROR);
				enableAdd = false;
			}
			else if(startTimeDayNightComboBox.getSelectedItem() == endTimeDayNightComboBox.getSelectedItem())
			{
				if(startHours > endHours)
				{
					endTimeErrorLabel.setVisible(true);
					endTimeErrorLabel.setText(START_AFTER_END_ERROR);
					enableAdd = false;
				}
				else if(startHours == endHours && startMin > endMin)
				{
					endTimeErrorLabel.setVisible(true);
					endTimeErrorLabel.setText(START_AFTER_END_ERROR);
					enableAdd = false;
				}
				else if(startHours == endHours && startMin == endMin)
				{
					endTimeErrorLabel.setVisible(true);
					endTimeErrorLabel.setText(ZERO_TIME_ERROR);
					enableAdd = false;
				}
			}
		}

		buttonAdd.setEnabled(enableAdd);



		//events can't conflict with each other, so we don't check this
		//		else
		//		{
		//			Event conflicting = EventModel.getInstance().getConflictingIteration((Date)dueDateBox.getValue());
		//			if(conflicting != null && conflicting != displayIteration)
		//			{
		//				errorDisplay.displayError(OVERLAPPING_ERROR + " Overlaps with " + conflicting.getName() + ".");
		//			}
		//		}

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
		refreshPanel();
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



	public Event getEvent()
	{

		Event thisEvent = new Event(nameTextField.getText(), dateCalendar.getStartDate(), dateCalendar.getEndDate());
		return thisEvent;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		refreshPanel();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		refreshPanel();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		refreshPanel();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		refreshPanel();
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		refreshPanel();
		
	}
}
