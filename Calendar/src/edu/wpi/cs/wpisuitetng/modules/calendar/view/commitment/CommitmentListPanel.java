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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.CommitmentListModel;

/**
 * This is the view where the list of commitments are displayed.
 * Creates both a scroll pane and JList which stores all of the commitments.
 * @version Revision: 1.0
 * @author
 */

public class CommitmentListPanel extends JPanel {

	private CommitmentListModel model;
	private JList<Commitment> commitmentList;
	/**
	 * Constructor for the CommitmentListPanel creates both the list of commitments
	 * and the scroll pane that they are displayed on.
	 */
	public CommitmentListPanel() {
		this.model = CommitmentListModel.getCommitmentListModel();
		
		this.setLayout(new MigLayout("fill, insets 0"));
		
		commitmentList = new JList<Commitment>(model);
		
		commitmentList.setCellRenderer(new CommitmentListCellRenderer());
		commitmentList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		commitmentList.setLayoutOrientation(JList.VERTICAL);
		
		commitmentList.setVisibleRowCount(0);

		JScrollPane scrollPane = new JScrollPane(commitmentList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		this.add(scrollPane, "grow, push");
		
//		 MouseListener mouseListener = new MouseAdapter() {
//		     public void mouseClicked(MouseEvent e) {
//		         if (e.getClickCount() == 2) {
//		             int index = commitmentList.locationToIndex(e.getPoint());
//		             System.out.println("Double clicked on Item " + index);
//		          }
//		     }
//		 };
//		 commitmentList.addMouseListener(mouseListener);
	}
	
	/**
	 * Public accessor for the JList of commitments
	 * @return JList<Commitment>: The list of Commitments.
	 */
	public JList<Commitment> getCommitmentList() {
		return commitmentList;
	}
}
