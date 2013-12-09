/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.view.quicklist;

import java.awt.Color;

import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.commitment.CommitmentListPanel;

public class QuickListTabPanel extends JPanel {
	
	private CommitmentListPanel commitmentListPanel;

	private JCheckBox commitmentCheckBox, eventCheckBox;
	
	public QuickListTabPanel(CalendarPanel calendarPanel) {
		this.setLayout(new MigLayout("fill"));
		
		commitmentCheckBox = new JCheckBox("Commitments");
		eventCheckBox = new JCheckBox("Events");
		
		commitmentCheckBox.setBackground(Color.WHITE);
		eventCheckBox.setBackground(Color.WHITE);
		
		commitmentCheckBox.setFocusable(false);
		eventCheckBox.setFocusable(false);
		
		JPanel quickListPanel = new JPanel(new MigLayout());
		
		quickListPanel.add(commitmentCheckBox,	"split 2, center");
		quickListPanel.add(eventCheckBox);
		
		this.add(quickListPanel, "center, wrap");
		
		commitmentListPanel = new CommitmentListPanel(calendarPanel);
		JPanel p = new JPanel(new MigLayout("fill"));
		p.add(commitmentListPanel, "grow, push");
		
		this.add(p, "grow, push");
	}
	
	public JList<Commitment> getCommitmentsList() {
		return commitmentListPanel.getCommitmentList();
	}
}
