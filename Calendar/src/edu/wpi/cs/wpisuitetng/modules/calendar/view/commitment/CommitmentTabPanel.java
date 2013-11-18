/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.view.commitment;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;

import com.toedter.calendar.JCalendar;

import edu.wpi.cs.wpisuitetng.modules.calendar.controller.commitment.AddCommitmentController;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.ViewMode;

/**
 * This panel tab is added whenever the user wants to create a new commitment or edit an existing one
 * most of this is copied/based from the IterationPanel in the requirements manager->view.iterations
 * @version $Revision: 1.0 $
 * @author djfitzgerald
 */
public class CommitmentTabPanel extends JPanel  implements KeyListener, MouseListener {	
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
	private JComboBox<String> categoryComboBox;
	private JPanel buttonPanel;
	private JPanel dataPanel;
	private JFormattedTextField dueDateBox;
	
	private JButton buttonAdd;
	private JButton buttonCancel;
	private JButton buttonUndoChanges;
	private JCalendar commitmentDate;
	
	private JLabel nameErrorLabel;
	private JLabel dateErrorLabel;
	
	private ViewMode vm;
	private Commitment displayCommitment;	//the Commitment currently being represented in this panel
	
	private boolean forceRemove = false;
	
	//defining temporary categories
	private final Category[] categories = {null, new Category("Personal", Color.GREEN), new Category("Team", Color.BLUE), 
			new Category("Important!", Color.RED)};
	
	/**
	 * The constructor for the commitment panel when creating a new commitment.
	 */
	public CommitmentTabPanel() {
		//this.vm = ViewMode.CREATING;
		displayCommitment = new Commitment();	//start by displaying an empy commitment
		buildLayout();
		refreshPanel();
	}
	
	/**
	 * Constructor for the iteration panel when editing an iteration
	 * @param iter the iteration to edit.
	 */
	public CommitmentTabPanel(Commitment commitment) {
		//this.vm = ViewMode.EDITING;
		displayCommitment = commitment;
		buildLayout();
		populateInformation();
		refreshPanel();
	}
	
	/**
	 * Builds the GUI layout for the iteration panel
	 * @return void
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
		
		//panel for the add, undo, and cancel buttons
		buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		buttonPanel.setAlignmentX(LEFT_ALIGNMENT);
		buttonPanel.add(buttonAdd);
		buttonPanel.add(buttonUndoChanges);
		buttonPanel.add(buttonCancel);
		
		//Action Listener for Add Commitment Button
		buttonAdd.addActionListener(new AddCommitmentController(this));

		//Action Listener for Undo Changes Button
		buttonUndoChanges.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				populateInformation();
				buttonUndoChanges.setEnabled(false);
			}
		});

		//Action Listener for Cancel Button
		buttonCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				killPanel();
			}
		});
		
		//Add button panel to build layout
		this.add(buttonPanel, BorderLayout.SOUTH);
		
		//Panel for the add commitment form
		dataPanel = new JPanel();
		add(dataPanel, BorderLayout.CENTER);
		dataPanel.setLayout(new MigLayout("", "[95px][26px][][32px][5px][50px]", "[17.00][20px][20px][20px][20px]"));

		//Commitment Name label
		JLabel nameLabel = new JLabel("Commitment Name");
		nameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		dataPanel.add(nameLabel, "cell 0 1,growx,aligny center");

		//Commitment Name text field
		nameTextField = new JTextField();
		nameTextField.setHorizontalAlignment(SwingConstants.LEFT);
		nameTextField.setColumns(10);
		nameTextField.addKeyListener(this);
		
		//ERROR panel to display name errors
		JPanel namePanel = new JPanel(new BorderLayout());
		nameErrorLabel = new JLabel();
		nameErrorLabel.setForeground(Color.RED);

		//Adding name text field and error label to namePanel
		namePanel.add(nameTextField, BorderLayout.WEST);
		namePanel.add(nameErrorLabel, BorderLayout.CENTER);
		
		//Adding namePanel to dataPanel
		dataPanel.add(namePanel, "cell 1 1 5 1,growx,aligny top");
		
		//Commitment Date label
		JLabel dateLabel = new JLabel("Date of Commitment:");
		dateLabel.setHorizontalAlignment(SwingConstants.LEFT);
		dataPanel.add(dateLabel, "cell 0 2,growx,aligny center");
		
		//Commitment datePicker panel and error label
		JPanel datePickerPanel = new JPanel(new BorderLayout());
		dateErrorLabel = new JLabel();
		dateErrorLabel.setForeground(Color.RED);
		
		//Commitment datePicker
		commitmentDate = new JCalendar();
		commitmentDate.addMouseListener(this);
		
		//Adding commitment datePicker and error label to datePicker panel
		datePickerPanel.add(commitmentDate, BorderLayout.WEST);
		datePickerPanel.add(dateErrorLabel, BorderLayout.CENTER);
		
		//Adding datePicker panel to dataPanel
		dataPanel.add(datePickerPanel, "cell 1 2 5 1,growx,aligny top");
		
		//Commitment Description label
		JLabel labelDesc = new JLabel("Description: ");
		labelDesc.setHorizontalAlignment(SwingConstants.LEFT);
		dataPanel.add(labelDesc, "cell 0 3,growx,aligny center");
		descriptionTextArea = new JTextArea();
		descriptionTextArea.setSize(new Dimension(200, 50));
		dataPanel.add(descriptionTextArea, "cell 1 3,growx,aligny top");
		
		//Temporary Categories
		String[] tempCategoriesNames = {"None", "Personal", "Team", "Important!"};
		
		//Category drop down box and adding it to dataPanel
		JLabel labelCat = new JLabel("Category: ");
		categoryComboBox = new JComboBox<String>(tempCategoriesNames);
		categoryComboBox.setPreferredSize(new Dimension(200, 20));
		dataPanel.add(labelCat, "cell 0 4,growx,aligny top");
		dataPanel.add(categoryComboBox, "cell 1 4,growx,aligny top");
	}
	
	// Kills the current tab
	public void killPanel(){
		this.getParent().remove(this);
	}
	
	/**
	 * Updates the display commitment
	 * @return void
	 */
	private void updateDisplayCommitment()
	{
		String name = nameTextField.getText();
		Date date = commitmentDate.getDate();
		String description = descriptionTextArea.getText();
		displayCommitment.setName(name);
		displayCommitment.setDueDate(date);
		displayCommitment.setDescription(description);
		displayCommitment.setCategory(categories[categoryComboBox.getSelectedIndex()]);

		if(vm == ViewMode.CREATING)
		{
			//TODO: create this commitment based on the current information of the UI components and add it to the model

		}
		else
		{
			//TODO: update the model with this new commitment
			
		}
		
	}
	
	/**
	 * Populate the information in the iteration panel for
	 * the display requirement.
	 * @return void
	 */
	private void populateInformation()
	{
		this.nameTextField.setText(displayCommitment.getName());
		this.dueDateBox.setValue(displayCommitment.getDueDate().getDate());
		refreshPanel();
	}
	
	
	/**
	 * Refreshes the panel
	 * @return void
	 */
	private void refreshPanel()
	{
		updateDisplayCommitment();
		validateFields();
		checkForChanges();
	}
	
	/**
	 * Validates the options of the fields inputed.
	 * @return void
	 */
	private void validateFields() {
		boolean enableAdd = true;
		
		if(nameTextField.getText().trim().length() == 0) {
			nameErrorLabel.setVisible(true);
			nameErrorLabel.setText(EMPTY_NAME_ERROR);
			enableAdd = false;
		} else {
			nameErrorLabel.setVisible(false);
		}
		
		if(commitmentDate.getCalendar().get(Calendar.YEAR) <= 0) {
			dateErrorLabel.setVisible(true);
			dateErrorLabel.setText("Invalid Date");
			enableAdd = false;
		} else {
			dateErrorLabel.setVisible(false);
		}

		buttonAdd.setEnabled(enableAdd);
	}
	
	/**
	 * Checks whether anything changed and updates buttons as needed.
	 * @return Boolean: (Not yet implemented only returns false)
	 */
	private boolean checkForChanges()
	{
		//TODO: Implement this function
		return false;
	}

	/**
	 * Returns the current display commitment
	 * @return Commitment: The current Display commitment
	 */
	public Commitment getDisplayCommitment() {
		updateDisplayCommitment();
		return displayCommitment;
	}

	/**
	 * Checks if the panel is ready to be removed.
	 * @return Boolean: whether the iteration panel as a whole is ready to be removed.
	 */
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
	
	//Listeners for key presses and mouse clicks
	@Override
	public void keyPressed(KeyEvent arg0) {}

	@Override
	public void keyReleased(KeyEvent arg0) {validateFields();}

	@Override
	public void keyTyped(KeyEvent arg0) {}

	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		validateFields();
	}
}
