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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.category.CategoryPickerPanel;

import javax.swing.JCheckBox;
import javax.swing.border.Border;

public class EventRecurringPanel extends JPanel implements KeyListener, ActionListener {
	// Errors strings
	private final String INVALID_OCCURRENCES_ERROR = 	"Events must occur at least once.";
	private final String INVALID_DAY_SELECTION_ERROR = 	"Today's date must be recurring.";

	// Error Labels

	// Error wrappers
	private JCheckBox chckbxSunday;
	private JCheckBox chckbxMonday;
	private JCheckBox chckbxTuesday;
	private JCheckBox chckbxWednesday;
	private JCheckBox chckbxThursday;
	private JCheckBox chckbxFriday;
	private JCheckBox chckbxSaturday;
	private JTextField occurrencesTextField;
	private Date startDate;
	private JLabel occurrencesErrorLabel;
	private Border defaultBorder;
	private JLabel wrongDaySelectionErrorLabel;
	public EventRecurringPanel(Date startDate) {
		this.buildLayout(startDate);
	}

	/**
	 * Builds the GUI layout for the Event panel
	 */
	private void buildLayout(Date startDate) {
		
		setLayout(new MigLayout("", "[][grow][grow][][][][][]", "[][][]"));
		defaultBorder = this.getBorder();
		JLabel lblRecurring = new JLabel("Recurring: ");
		add(lblRecurring, "cell 0 0");
		
		chckbxSunday = new JCheckBox("Sunday");
		add(chckbxSunday, "cell 1 0");
		chckbxSunday.addActionListener(this);
		
		chckbxMonday = new JCheckBox("Monday");
		add(chckbxMonday, "cell 2 0");
		chckbxMonday.addActionListener(this);
		
		chckbxTuesday = new JCheckBox("Tuesday");
		add(chckbxTuesday, "cell 3 0");
		chckbxTuesday.addActionListener(this);
		
		chckbxWednesday = new JCheckBox("Wednesday");
		add(chckbxWednesday, "cell 4 0");
		chckbxWednesday.addActionListener(this);
		
		chckbxThursday = new JCheckBox("Thursday");
		add(chckbxThursday, "cell 5 0");
		chckbxThursday.addActionListener(this);
		
		chckbxFriday = new JCheckBox("Friday");
		add(chckbxFriday, "cell 6 0");
		chckbxFriday.addActionListener(this);
		
		chckbxSaturday = new JCheckBox("Saturday");
		add(chckbxSaturday, "cell 7 0");
		chckbxSaturday.addActionListener(this);
		
		JLabel lblNumberOfOccurences = new JLabel("Number of Occurences: ");
		add(lblNumberOfOccurences, "flowx,cell 0 1 3 1,alignx left");
		
		occurrencesErrorLabel = new JLabel(INVALID_OCCURRENCES_ERROR);
		occurrencesErrorLabel.setForeground(Color.RED);
		occurrencesErrorLabel.setVisible(false);
		add(occurrencesErrorLabel, "cell 3 1 3 1,alignx left");
		
		occurrencesTextField = new JTextField();
		occurrencesTextField.setText("1");
		add(occurrencesTextField, "cell 2 1,alignx left");
		occurrencesTextField.setColumns(10);
		occurrencesTextField.addKeyListener(this);
		
		wrongDaySelectionErrorLabel = new JLabel(INVALID_DAY_SELECTION_ERROR);
		wrongDaySelectionErrorLabel.setForeground(Color.RED);
		wrongDaySelectionErrorLabel.setVisible(false);
		add(wrongDaySelectionErrorLabel, "cell 0 2 6 1");
		validateRecurring(startDate);
	}


	/**
	 * Validates the options of the fields inputed with today's date.
	 * @return void
	 */
	public boolean validateRecurring(Date startDate) {
		this.startDate =  startDate;
		return validateRecurring();
	}

	/**
	 * Validates the options of the fields inputed with given date.
	 * @return True if valid
	 */
	public boolean validateRecurring() {
		boolean isValid = true;
		int dayToday = startDate.getDay();
		try
		{
			if(Integer.parseInt(occurrencesTextField.getText()) > 0){
				isValid = isValid && true;
				occurrencesErrorLabel.setVisible(false);
			}
			else{
				isValid = false;
				occurrencesErrorLabel.setVisible(true);
			}
		}
		catch(NumberFormatException e)
		{
			isValid = false;
			occurrencesErrorLabel.setVisible(true);
		}
		
		switch(dayToday){
			case 0: 
				if(!chckbxSunday.isSelected())
					isValid = false;
				else
					wrongDaySelectionErrorLabel.setVisible(false);
				break;
			case 1: 
				if(!chckbxMonday.isSelected()){
					isValid = false;
					wrongDaySelectionErrorLabel.setVisible(true);
				}
				else
					wrongDaySelectionErrorLabel.setVisible(false);
				break;
			case 2: 
				if(!chckbxTuesday.isSelected()) {
					isValid = false;
					wrongDaySelectionErrorLabel.setVisible(true);
				}
				else
					wrongDaySelectionErrorLabel.setVisible(false);
				break;
			case 3: 
				if(!chckbxWednesday.isSelected()) {
					isValid = false;
					wrongDaySelectionErrorLabel.setVisible(true);
				}
				else
					wrongDaySelectionErrorLabel.setVisible(false);
				break;
			case 4: 
				if(!chckbxThursday.isSelected()) {
					isValid = false;
					wrongDaySelectionErrorLabel.setVisible(true);
				}
				else
					wrongDaySelectionErrorLabel.setVisible(false);
				break;
			case 5: 
				if(!chckbxFriday.isSelected()) {
					isValid = false;
					wrongDaySelectionErrorLabel.setVisible(true);
				}
				else
					wrongDaySelectionErrorLabel.setVisible(false);
				break;
			case 6: 
				if(!chckbxSaturday.isSelected()) {
					isValid = false;
					wrongDaySelectionErrorLabel.setVisible(true);
				}
				else
					wrongDaySelectionErrorLabel.setVisible(false);
				break;
				
		}
		if(!isValid)
			this.setBorder(BorderFactory.createLineBorder(new Color(255, 51, 51)));
		else
			this.setBorder(defaultBorder);
		return isValid;
	}

	public Event getFilledEvent() {
		return null;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		validateRecurring();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		validateRecurring();
	}

	// Unused
	@Override
	public void keyPressed(KeyEvent e) {}
	@Override
	public void keyTyped(KeyEvent e) {}
}
