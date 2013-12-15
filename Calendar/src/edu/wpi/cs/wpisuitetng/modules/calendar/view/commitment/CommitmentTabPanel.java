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

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.commitment.AddCommitmentController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.commitment.UpdateCommitmentController;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarPicker;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.category.CategoryPickerPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.DateTimeChangedEvent;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.DateTimeChangedEventListener;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.DateTimeChooser;

public class CommitmentTabPanel extends JPanel implements ActionListener, KeyListener {	
	//Errors strings
	private final String EMPTY_NAME_ERROR =		"Name is required.";
//	private final String INVALID_DATE_ERROR =	"Invalid date. Use: www mm/dd/yyyy ----EX: Sun 04/06/2014";
//	private final String PAST_DATE_ERROR =		"Commitment cannot occur in the past.";

	// Data entry components
	private JTextField nameTextField;
//	private DatePickerPanel datePickerPanel;
//	private TimePicker timePicker;
	private DateTimeChooser dateTimeChooser_;
	
	private CategoryPickerPanel categoryPickerPanel;
	private CommitmentProgressPanel commitmentProgressPanel;
	private CalendarPicker calendarPicker;
	private JTextArea descriptionTextArea;

	// Buttons
	private JButton addCommitmentButton;
	private JButton cancelButton;

	// Error Labels
	private JLabel nameErrorLabel;
//	private JLabel dateErrorLabel;

	
	// Old Commitment
	private Commitment editCommitment;
	private boolean isEditMode;

	// Error wrappers
	private JPanel nameErrorPanelWrapper;
	JEditorPane display;

	public CommitmentTabPanel() {
		this.buildLayout();
	}

	public CommitmentTabPanel(Commitment c)
	{
		System.out.println("CommitmentTabPanel: Making tab to edit commitment: "+c.toString());
		
		isEditMode = true;
		editCommitment = c;
		

		this.buildLayout();
		
		//fill the fields with information from the provided Commitment
		nameTextField.setText(c.getName());
		dateTimeChooser_.setDate(c.getDueDate());
		commitmentProgressPanel.setSelected(c.getProgress());
		categoryPickerPanel.setSelectedCategory(c.getCategory());
		descriptionTextArea.setText(c.getDescription());
		calendarPicker.setSelected(c.getisTeam() ? "Team": "Personal");
		addCommitmentButton.setText("Update Commitment");
		addCommitmentButton.setActionCommand("updatecommitment");
		addCommitmentButton.removeActionListener(addCommitmentButton.getActionListeners()[0]); //Remove the addCommitment action listener
		addCommitmentButton.addActionListener(new UpdateCommitmentController(this, c));	//Add the updateCommitment action listener
		validateFields();
	}

	public boolean validateEdit() {
		boolean noChangesMade = true;
		if(!editCommitment.getName().equals(nameTextField.getText())) {
			noChangesMade = false;
		}
		
		else if(!editCommitment.getDueDate().toString().equals(dateTimeChooser_.getDate().toString())){
			noChangesMade = false;
		}
		else if(!editCommitment.getCategory().equals(categoryPickerPanel.getSelectedCategory())){
			noChangesMade = false;
		}
		else if(!editCommitment.getDescription().equals(descriptionTextArea.getText())){
			noChangesMade = false;
		}
		else if(editCommitment.getisTeam() != calendarPicker.isTeam()){
			noChangesMade = false;
		}
		else if(editCommitment.getProgress() != commitmentProgressPanel.getSelectedState()){
			noChangesMade = false;
		}
		return noChangesMade;
	}
	
	/**
	 * Builds the GUI layout for the Commitment panel
	 * @return void
	 */
	private void buildLayout() {
		this.setLayout(new MigLayout("fill"));

		// Name
		this.add(new JLabel("Commitment Name:"), "split 3");
		nameErrorPanelWrapper = new JPanel(new MigLayout("fill, insets 0"));
		nameTextField = new JTextField();
		nameTextField.addKeyListener(this);
		nameErrorPanelWrapper.add(nameTextField, "alignx left, growx, push, w 5000");
		this.add(nameErrorPanelWrapper, "alignx left, growx, push, w 5000");
		nameErrorLabel = new JLabel(EMPTY_NAME_ERROR);
		nameErrorLabel.setForeground(Color.RED);
		this.add(nameErrorLabel, "wrap");

		//Date and Time
		this.add(new JLabel("Due date:"), "split 2");
		dateTimeChooser_=new DateTimeChooser("");//empty label in the component itself
//		dateTimeChooser_.addKeyListener(this);
//		dateTimeChooser_.addActionListener(this);
		dateTimeChooser_.addDateTimeChangedEventListener(new DateTimeChangedEventListener(){
			@Override
			public void DateTimeChangedEventOccurred(DateTimeChangedEvent evt) {
				validateFields();
			}
		});
		this.add(dateTimeChooser_, "alignx left, wrap");
		
		
		// Category
		this.add(new JLabel("Category:"), "split 2");
		categoryPickerPanel = new CategoryPickerPanel();
		categoryPickerPanel.addActionListener(this);
		this.add(categoryPickerPanel, "alignx left, wrap");

		//Progress
		this.add(new JLabel("Progress:"), "split 2");
		commitmentProgressPanel = new CommitmentProgressPanel();
		commitmentProgressPanel.addActionListener(this);
		this.add(commitmentProgressPanel, "alignx left, wrap");

		// Calendar
		this.add(new JLabel("Calendar:"), "split 2");
		calendarPicker = new CalendarPicker();
		calendarPicker.addActionListener(this);
		this.add(calendarPicker, "alignx left, wrap");

		// Description
		this.add(new JLabel("Description:"), "wrap");

		descriptionTextArea = new JTextArea();
		descriptionTextArea.addKeyListener(this);
		descriptionTextArea.setLineWrap(true);
		descriptionTextArea.setWrapStyleWord(true);

		JScrollPane scrollp = new JScrollPane(descriptionTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		this.add(scrollp, "grow, push, span, h 5000, wrap");

		// Add / Cancel buttons
		addCommitmentButton = new JButton("Add Commitment");
		addCommitmentButton.setActionCommand("addcommitment");
		addCommitmentButton.addActionListener(new AddCommitmentController(this));

		this.add(addCommitmentButton, "alignx left, split 2");

		cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("cancel");

		this.add(cancelButton, "alignx left");

		//Action Listener for Cancel Button
		cancelButton.addActionListener(this);

		validateFields();
	}

	// Kills this add commitment tab
	public void closeCommitmentPanel() {
		this.getParent().remove(this);
	}

	/**
	 * Validates the options of the fields inputed.
	 * @return void
	 */
	private void validateFields() {
		boolean enableAddCommitment = true;
		
		if(isEditMode) {
			System.out.println("CommitmentTabPanel: Edit Mode");
			enableAddCommitment = !validateEdit();
		}

		//check name
		if(nameTextField.getText().trim().length() == 0) {
			nameErrorPanelWrapper.setBorder(BorderFactory.createLineBorder(new Color(255, 51, 51)));
			nameErrorLabel.setText(EMPTY_NAME_ERROR);
			nameErrorLabel.setVisible(true);
			enableAddCommitment = false;
		} else {
			nameErrorPanelWrapper.setBorder(BorderFactory.createLineBorder(new Color(255, 51, 51, 0)));
			nameErrorLabel.setVisible(false);
		}

		if (! dateTimeChooser_.hasValidDateTime()){
			enableAddCommitment=false;
		}

		addCommitmentButton.setEnabled(enableAddCommitment);
	}

	/**
	 * Returns the currently inputed data in the form of a commitment
	 * @return Commitment: A filled in Commitment
	 */
	public Commitment getFilledCommitment() {
		return new Commitment(nameTextField.getText(), dateTimeChooser_.getDate(), calendarPicker.isTeam(),
				descriptionTextArea.getText(), categoryPickerPanel.getSelectedCategory(), commitmentProgressPanel.getSelectedState());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("cancel")) {
			this.closeCommitmentPanel();
		} else {
			validateFields();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	// Unused
	@Override
	public void keyPressed(KeyEvent e) {validateFields();}
	@Override
	public void keyTyped(KeyEvent e) {}
}
