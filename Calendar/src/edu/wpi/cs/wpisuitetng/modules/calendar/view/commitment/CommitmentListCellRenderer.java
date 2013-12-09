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

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.MatteBorder;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utilities.CalendarUtils;

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
	
	/**
	 * Default constructor for CommitmentListCellRenderer
	 * Sets up the general view and prepares it to have commitments placed in it
	 */
	public CommitmentListCellRenderer() {
		this.setLayout(new MigLayout("flowy"));
		commitmentName = new JLabel();
		dueDate = new JLabel();
		category = new JLabel();
		this.add(commitmentName);
		this.add(dueDate);
		this.add(category);
		
		this.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
	}
	
	@Override
	public Component getListCellRendererComponent(JList<? extends Commitment> list, Commitment commitment,
			int index, boolean isSelected, boolean cellHasFocus) {
		commitmentName.setText(commitment.getName());
		dueDate.setText("Due: " + CalendarUtils.weekNamesAbbr[commitment.getDueDate().getDay()] + ", " + CalendarUtils.monthNames[commitment.getDueDate().getMonth()] + " "
		+ commitment.getDueDate().getDate() + ", "
				+ (commitment.getDueDate().getYear() + 1900));

		if(!commitment.getCategory().getName().equals("Uncategorized")) {
			category.setVisible(true);
			category.setText(commitment.getCategory().getName());
			category.setForeground(commitment.getCategory().getColor());
		} else {
			category.setVisible(false);
		}
		
		this.setBackground(isSelected ? CalendarUtils.selectionColor : Color.WHITE);
		
		return this;
	}
}
