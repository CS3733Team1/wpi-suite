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

//import com.toedter.calendar.JCalendar;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.AddCommitmentController;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.CalendarModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;

/**
 * This panel tab is added whenever the user wants to create a new commitment or edit an existing one
 * most of this is copied/based from the IterationPanel in the requirements manager->view.iterations
 * @version $Revision: 1.0 $
 * @author djfitzgerald
 */
public class CommitmentPanel extends JPanel  implements KeyListener, MouseListener {
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
	
	private final Category[] categories = {new Category("Personal", Color.GREEN), new Category("Team", Color.BLUE), 
			new Category("Important!", Color.RED)};
	
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
		//errorDisplay = new ErrorPanel();
		
		//panel for the add, undo, and cancel buttons, and the error display
		buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		buttonPanel.setAlignmentX(LEFT_ALIGNMENT);
		buttonPanel.add(buttonAdd);
		buttonPanel.add(buttonUndoChanges);
		buttonPanel.add(buttonCancel);
		//buttonPanel.add(errorDisplay);
		
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
		
		JPanel namePanel = new JPanel(new BorderLayout());
		nameErrorLabel = new JLabel();
		nameErrorLabel.setForeground(Color.RED);
		
		nameTextField = new JTextField();
		nameTextField.setHorizontalAlignment(SwingConstants.LEFT);
		nameTextField.setColumns(10);
		nameTextField.addKeyListener(this);
		
		namePanel.add(nameTextField, BorderLayout.WEST);
		namePanel.add(nameErrorLabel, BorderLayout.CENTER);
		
		dataPanel.add(namePanel, "cell 1 1 5 1,growx,aligny top");
		
		JLabel dateLabel = new JLabel("Date of Commitment:");
		dateLabel.setHorizontalAlignment(SwingConstants.LEFT);
		dataPanel.add(dateLabel, "cell 0 2,growx,aligny center");
		
		JPanel datePickerPanel = new JPanel(new BorderLayout());
		dateErrorLabel = new JLabel();
		dateErrorLabel.setForeground(Color.RED);
		
		commitmentDate = new JCalendar();
		commitmentDate.addMouseListener(this);
		
		datePickerPanel.add(commitmentDate, BorderLayout.WEST);
		datePickerPanel.add(dateErrorLabel, BorderLayout.CENTER);
		
		dataPanel.add(datePickerPanel, "cell 1 2 5 1,growx,aligny top");
		
		JLabel labelDesc = new JLabel("Description: ");
		labelDesc.setHorizontalAlignment(SwingConstants.LEFT);
		dataPanel.add(labelDesc, "cell 0 3,growx,aligny center");
		descriptionTextArea = new JTextArea();
		descriptionTextArea.setPreferredSize(new Dimension(200, 20));
		dataPanel.add(descriptionTextArea, "cell 1 3,growx,aligny top");
		
		
		
		
		String[] tempCategoriesNames = {"Personal", "Team", "Important!"};
		
		JLabel labelCat = new JLabel("Category: ");
		categoryComboBox = new JComboBox<String>(tempCategoriesNames);
		
		categoryComboBox.setPreferredSize(new Dimension(200, 20));
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
		Date date = commitmentDate.getDate();
		String description = descriptionTextArea.getText();
		displayCommitment.setName(name);
		displayCommitment.setDueDate(date);
		displayCommitment.setDescription(description);
		displayCommitment.setCategory(categories[categoryComboBox.getSelectedIndex()]);

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
	//public void setDueDate(Date dueDate)
	//{
	//	if(dueDate != null) this.dueDateBox.setValue(dueDate);
	//	
	//	refreshPanel();
	//}
	
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
	 * 
	
	 * @return the display commitment */
	public Commitment getDisplayCommitment() {
		updateDisplayCommitment();
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
