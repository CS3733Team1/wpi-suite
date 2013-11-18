/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.view.category;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;
//import edu.wpi.cs.wpisuitetng.modules.calendar.model.CategoryListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.CommitmentListModel;

public class CategoryListPanel extends JPanel {

	private CommitmentListModel model;
	private JList<Commitment> commitmentList;
	
	public CategoryListPanel() {
		this.model = CommitmentListModel.getCommitmentListModel();
		
		this.setLayout(new BorderLayout());
		
		commitmentList = new JList<Commitment>(model);
		
		commitmentList.setCellRenderer(new CategoryListCellRenderer());
		commitmentList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		commitmentList.setLayoutOrientation(JList.VERTICAL);
		commitmentList.setVisibleRowCount(-1);

		JScrollPane scrollPane2 = new JScrollPane(commitmentList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		this.add(new JLabel("Commitments"), BorderLayout.NORTH);
		this.add(scrollPane2, BorderLayout.CENTER);

		this.setPreferredSize(new Dimension(300, 1));
	}

	public JList<Commitment> getCommitmentList() {
		return commitmentList;
	}
}
