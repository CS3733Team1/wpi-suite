/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview;

import java.awt.event.KeyListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

import net.miginfocom.swing.MigLayout;

public class DatePickerPanel extends JPanel {

	private int isValid; // 0 = valid, 1 = format error, 2 = before date error
	private Calendar pickedDate;
	private SimpleDateFormat df;

	private JSpinner inputField;
	private JTextField rawText;

	private Date parsedDate;

	public DatePickerPanel() {
		pickedDate = Calendar.getInstance();
		pickedDate.set(Calendar.HOUR_OF_DAY, 0);
		pickedDate.set(Calendar.MINUTE, 0);
		pickedDate.set(Calendar.SECOND, 0);

		isValid = 0;

		Calendar calendar = Calendar.getInstance();

		Date initDate = new Date(pickedDate.get(Calendar.YEAR)-1900, pickedDate.get(Calendar.MONTH), pickedDate.get(Calendar.DATE));
		Date latestDate = new Date(pickedDate.get(Calendar.YEAR)+1000, pickedDate.get(Calendar.MONTH), pickedDate.get(Calendar.DATE));
		SpinnerDateModel model = new SpinnerDateModel(initDate, initDate, latestDate, Calendar.YEAR);
		inputField = new JSpinner(model);

		inputField.setEditor(new JSpinner.DateEditor(inputField, "EEE MM/dd/yyyy"));

		rawText = ((JSpinner.DefaultEditor)inputField.getEditor()).getTextField();

		df = new SimpleDateFormat("EEE MM/dd/yyyy");

		this.setLayout(new MigLayout("insets 0"));

		this.add(inputField, "split 2");
		//this.add(new JLabel("[picker]"));
	}

	public void validateDate() {
		System.out.println(rawText.getText());
		try {
			parsedDate = df.parse(rawText.getText());
			System.out.println(parsedDate.toString());
			this.isValid = isBeforeToday();
		} catch (ParseException e) {
			this.isValid = 1;
		}
	}

	public int isInvalidDate() {
		return isValid;
	}

	public int isBeforeToday() {
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);

		Calendar parsedCal = Calendar.getInstance();
		parsedCal.set(Calendar.HOUR_OF_DAY, 0);
		parsedCal.set(Calendar.MINUTE, 0);
		parsedCal.set(Calendar.SECOND, 0);
		parsedCal.set(Calendar.YEAR, parsedDate.getYear()+1900);
		parsedCal.set(Calendar.MONTH, parsedDate.getMonth());
		parsedCal.set(Calendar.DATE, parsedDate.getDate());

		System.out.println(parsedCal.get(Calendar.YEAR) + "   " + today.get(Calendar.YEAR));
		
		if(parsedCal.getTimeInMillis() < today.getTimeInMillis()) return 2;
		else return 0;
	}

	public Date getDate() {
		return (Date)inputField.getValue();
	}

	public void setKeyListener(KeyListener l) {
		rawText.addKeyListener(l);
	}
}
