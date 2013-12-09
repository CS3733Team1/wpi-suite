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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.category.CategoryPickerPanel;

import javax.swing.JCheckBox;

public class EventRecurringPanel extends JPanel implements KeyListener, ActionListener {
	// Errors strings
	//private final String START_AFTER_END_ERROR = 	"Start time cannot be after end time.";
	// Error Labels

	// Error wrappers
	private JCheckBox chckbxSunday;
	private JCheckBox chckbxMonday;
	private JCheckBox chckbxTuesday;
	private JCheckBox chckbxWednesday;
	private JCheckBox chckbxThursday;
	private JCheckBox chckbxFriday;
	private JCheckBox chckbxSaturday;

	public EventRecurringPanel() {
		this.buildLayout();
	}

	/**
	 * Builds the GUI layout for the Event panel
	 */
	private void buildLayout() {
		
setLayout(new MigLayout("", "[][grow][grow][][][][][]", "[][]"));
		
		JLabel lblRecurring = new JLabel("Recurring: ");
		add(lblRecurring, "cell 0 0");
		
		chckbxSunday = new JCheckBox("Sunday");
		add(chckbxSunday, "cell 1 0");
		
		chckbxMonday = new JCheckBox("Monday");
		add(chckbxMonday, "cell 2 0");
		
		chckbxTuesday = new JCheckBox("Tuesday");
		add(chckbxTuesday, "cell 3 0");
		
		chckbxWednesday = new JCheckBox("Wednesday");
		add(chckbxWednesday, "cell 4 0");
		
		chckbxThursday = new JCheckBox("Thursday");
		add(chckbxThursday, "cell 5 0");
		
		chckbxFriday = new JCheckBox("Friday");
		add(chckbxFriday, "cell 6 0");
		
		chckbxSaturday = new JCheckBox("Saturday");
		add(chckbxSaturday, "cell 7 0");
		
		JLabel lblNumberOfOccurences = new JLabel("Number of Occurences: ");
		add(lblNumberOfOccurences, "cell 0 1,alignx trailing");
		
		JTextField occurrencesTextField = new JTextField();
		occurrencesTextField.setText("1");
		add(occurrencesTextField, "cell 1 1,growx");
		occurrencesTextField.setColumns(10);
		validateFields();
	}


	/**
	 * Validates the options of the fields inputed.
	 * @return void
	 */
	private void validateFields() {		
	}

	public Event getFilledEvent() {
		return null;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
			validateFields();
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	// Unused
	@Override
	public void keyPressed(KeyEvent e) {}
	@Override
	public void keyTyped(KeyEvent e) {}
}
