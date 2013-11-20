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
import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.MonthCalendarView;

/**
 * This class is in charge of how the commitments display in the list.
 * It creates a template and then uses that to print out every commitment
 *@version 1.0
 *@author 
 */
public class CommitmentListCellRenderer extends JPanel implements ListCellRenderer<Commitment> {

	private JLabel commitmentName;
	private JLabel dueDate;
	private JLabel category;
	
	private static final String[] daysOfWeek = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
	
	/**
	 * Default constructor for CommitmentListCellRenderer
	 * Sets up the general view and prepares it to have commitments placed in it
	 */
	public CommitmentListCellRenderer() {
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		commitmentName = new JLabel();
		dueDate = new JLabel();
		category = new JLabel();
		this.add(commitmentName);
		this.add(dueDate);
		this.add(category);
	}
	
	@Override
	public Component getListCellRendererComponent(JList<? extends Commitment> list, Commitment commitment,
			int index, boolean isSelected, boolean cellHasFocus) {
		commitmentName.setText("Name: " + commitment.getName());
		dueDate.setText("Date: " + daysOfWeek[commitment.getDueDate().getDay()] + ", " + MonthCalendarView.monthNames[commitment.getDueDate().getMonth()] + " "
		+ commitment.getDueDate().getDate() + ", "
				+ (commitment.getDueDate().getYear() + 1900));

		if(commitment.getCategory()!= null && !commitment.getCategory().equals("None")) {
			category.setVisible(true);
			category.setText("Category: " + commitment.getCategory().getName());
			category.setForeground(commitment.getCategory().getColor());
		} else {
			category.setVisible(false);
		}
		
		final Color background = UIManager.getDefaults().getColor("List.background");
		final Color foreground = UIManager.getDefaults().getColor("List.foreground");
		final Color selectionBackground = UIManager.getDefaults().getColor("List.selectionBackground");
		final Color selectionForeground = UIManager.getDefaults().getColor("List.selectionForeground");
		
		this.setBackground(isSelected ? selectionBackground : background);
		this.setForeground(isSelected ? selectionForeground : foreground);
		
		this.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		return this;
	}
}
