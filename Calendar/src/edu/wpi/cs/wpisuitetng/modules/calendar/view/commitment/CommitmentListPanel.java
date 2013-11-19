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

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;
//import edu.wpi.cs.wpisuitetng.modules.calendar.model.CategoryListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.CommitmentListModel;

/**
 * This is the view where the list of commitments are displayed.
 * Creates both a scroll pane and JList which stores all of the commitments.
 * @version Revision: 1.0
 * @author
 */
@SuppressWarnings("serial")
public class CommitmentListPanel extends JPanel {

	private CommitmentListModel model;
	private JList<Commitment> commitmentList;
	/**
	 * Constructor for the CommitmentListPanel creates both the list of commitments
	 * and the scroll pane that they are displayed on.
	 */
	public CommitmentListPanel() {
		this.model = CommitmentListModel.getCommitmentListModel();
		
		this.setLayout(new BorderLayout());
		
		commitmentList = new JList<Commitment>(model);
		
		commitmentList.setCellRenderer(new CommitmentListCellRenderer());
		commitmentList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		commitmentList.setLayoutOrientation(JList.VERTICAL);
		commitmentList.setVisibleRowCount(-1);

		JScrollPane scrollPane2 = new JScrollPane(commitmentList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		this.add(new JLabel("Commitments"), BorderLayout.NORTH);
		this.add(scrollPane2, BorderLayout.CENTER);
	}
	
	/**
	 * Public accsesor for the JList of commitments
	 * @return JList<Commitment>: The list of Commitments.
	 */
	public JList<Commitment> getCommitmentList() {
		return commitmentList;
	}
}
