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
import java.text.DateFormat;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.toedter.calendar.JCalendar;

import net.miginfocom.swing.MigLayout;

//import com.toedter.calendar.JCalendar;






import edu.wpi.cs.wpisuitetng.modules.calendar.controller.AddCommitmentController;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.CalendarModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;

/**
 * This panel tab is added whenever the user wants to create a new commitment or edit an existing one
 * most of this is copied/based from the IterationPanel in the requirements manager->view.iterations
 * @version $Revision: 1.0 $
 * @author djfitzgerald
 */
public class CommitmentPanel extends JPanel implements KeyListener{
	CalendarView view;
	CalendarModel model;
	
	
	//errors thrown for improper input
	private final String START_AFTER_END_ERROR = 	"Start date cannot be after end date.";
	private final String INVALID_NAME_ERROR = 		"Iteration exists with given name.";
	private final String EMPTY_NAME_ERROR = 		"Name is required.";
	private final String DATES_REQ = 				"Start and end date required.";
	private final String PAST_ERROR = 				"Commitment cannot occur in the past.";
	
	//private IterationRequirements requirements;
	//private IterationCalendarPanel calPanel;
	
	//components that will be in the panel
	private JTextField nameTextField;
	private JTextArea descriptionTextArea;
	private JComboBox categoryComboBox;
	private JPanel buttonPanel;
	private JPanel dataPanel;
	private JFormattedTextField dueDateBox;
	
	private JButton buttonAdd;
	private JButton buttonCancel;
	private JButton buttonUndoChanges;
	
	private ErrorPanel errorDisplay;
	
	
	private ViewMode vm;
	private Commitment displayCommitment;	//the Commitment currently being represented in this panel
	
	private boolean forceRemove = false;
	
	/**
	 * The constructor for the commitment panel when creating a new commitment.
	 */
	public CommitmentPanel(CalendarView view, CalendarModel model) {
		this.view=view;
		this.model=model;
		
		//this.vm = ViewMode.CREATING;
		displayCommitment = new Commitment();	//start by displaying an empy commitment
		buildLayout();
		refreshPanel();
	}
	
	/**
	 * Constructor for the iteration panel when editing an iteration
	 * @param iter the iteration to edit.
	 */
	public CommitmentPanel(Commitment commitment, CalendarView view, CalendarModel model) {
		this.view=view;
		this.model=model;
		
		//this.vm = ViewMode.EDITING;
		displayCommitment = commitment;
		buildLayout();
		populateInformation();
		refreshPanel();
	}
	
	/**
	 * Builds the GUI layout for the iteration panel
	 */
	private void buildLayout(){
		
		this.setLayout(new BorderLayout());
		
		//add commitment (submit) button
		String addButtonText = (vm == ViewMode.EDITING ? "Update Commitment" : "Add Commitment");
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
		
		buttonAdd.addActionListener(new AddCommitmentController(this, this.model));
		
		buttonUndoChanges.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				populateInformation();
				buttonUndoChanges.setEnabled(false);
			}
		});

		//TODO: add action listener and give it our AddCommitmentController
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
		dataPanel.setLayout(new MigLayout("", "[95px][26px][][32px][5px][50px]", "[17.00][20px][20px][20px][20px]"));

		JLabel nameLabel = new JLabel("Commitment Name");
		nameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		dataPanel.add(nameLabel, "cell 0 1,growx,aligny center");
		
		nameTextField = new JTextField();
		nameTextField.setHorizontalAlignment(SwingConstants.LEFT);
		dataPanel.add(nameTextField, "cell 1 1 5 1,growx,aligny top");
		nameTextField.setColumns(10);
		
		JLabel dateLabel = new JLabel("Date of Commitment:");
		dateLabel.setHorizontalAlignment(SwingConstants.LEFT);
		dataPanel.add(dateLabel, "cell 0 2,growx,aligny center");
		
		JCalendar commitmentDate = new JCalendar();
		dataPanel.add(commitmentDate, "cell 1 2 5 1,growx,aligny top");
		
		JLabel labelDesc = new JLabel("Description: ");
		labelDesc.setHorizontalAlignment(SwingConstants.LEFT);
		dataPanel.add(labelDesc, "cell 0 3,growx,aligny center");
		descriptionTextArea = new JTextArea();
		descriptionTextArea.setPreferredSize(new Dimension(200, 20));
		descriptionTextArea.addKeyListener(this);
		dataPanel.add(descriptionTextArea, "cell 1 3,growx,aligny top");
		
		//
		String[] tempCategory = {"Test1", "Test2"};
		JLabel labelCat = new JLabel("Category: ");
		categoryComboBox = new JComboBox<String>(tempCategory);
		categoryComboBox.setPreferredSize(new Dimension(200, 20));
		categoryComboBox.addKeyListener(this);
		dataPanel.add(labelCat, "cell 0 4,growx,aligny top");
		dataPanel.add(categoryComboBox, "cell 1 4,growx,aligny top");

	}
	
	public void killPanel(){
		view.getCalendarPanel().remove(this);
	}
	
	/**
	 * Updates the display commitment
	 */
	private void updateDisplayCommitment()
	{
		String name = nameTextField.getText();
		displayCommitment.setName(name);
		displayCommitment.setDueDate(new Date());

		if(vm == ViewMode.CREATING)
		{
			//TODO: create this commitment based on the current information of the UI components and add it to the model
			
//			int id = CommitmentModel.getInstance().getNextID();
//			displayCommitment.setId(id);
//			CommitmentModel.getInstance().addCommitment(displayCommitment);
		}
		else
		{
			//TODO: update the model with this new commitment
			
//			UpdateCommitmentController.getInstance().updateCommitment(displayCommitment);
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
		this.nameTextField.setText(displayCommitment.getName());
		this.dueDateBox.setValue(displayCommitment.getDueDate().getDate());
		refreshPanel();
	}
	
	
	/**
	 * Sets the dates for the commitment panel
	 * @param dueDate the due date
	 */
	public void setDueDate(Date dueDate)
	{
		if(dueDate != null) this.dueDateBox.setValue(dueDate);
		
		refreshPanel();
	}
	
	/**
	 * Refreshes the panel
	 */
	private void refreshPanel()
	{
		updateDisplayCommitment();
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
//		
//		//TODO: get the Commitment from the model with the name in our boxName textbox // = CommitmentModel.getInstance().getIteration(boxName.getText().trim());
//		if(nameTextField.getText().trim().length() == 0)
//		{
//			errorDisplay.displayError(EMPTY_NAME_ERROR);
//		}
//		
//		
//		if(dueDateBox.getText().trim().length() == 0)
//		{		
//			errorDisplay.displayError(DATES_REQ);
//		}
//		
//		
//		//TODO: check if the due date is in the calendar's past
//		
//		else if(((Date)dueDateBox.getValue()).before(cal.getTime()))
//		{
//			errorDisplay.displayError(PAST_ERROR);
//		}
//		
//		//commitments can't conflict with eachother, so we don't check this
//		else
//		{
//			Commitment conflicting = CommitmentModel.getInstance().getConflictingIteration((Date)dueDateBox.getValue());
//			if(conflicting != null && conflicting != displayIteration)
//			{
//				errorDisplay.displayError(OVERLAPPING_ERROR + " Overlaps with " + conflicting.getName() + ".");
//			}
//		}
//		
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
//			nameChanged = !boxName.getText().equals(displayCommitment.getName());
//			Date dueDate = (Date)dueDateBox.getValue();
//			
//			dueDateChanged = !dueDate.equals(displayCommitment.getDueDate().getDate());
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
		refreshPanel();
	}

	/**
	 * Method keyPressed.
	 * @param e KeyEvent
	
	 * @see java.awt.event.KeyListener#keyPressed(KeyEvent) */
	@Override
	public void keyPressed(KeyEvent e) {
		refreshPanel();
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
	 * 
	
	 * @return the display commitment */
	public Commitment getDisplayCommitment() {
		return displayCommitment;
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
