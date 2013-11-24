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

import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.commitment.AddCommitmentController;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.CommitmentListModel;

/**
 * This is the view where the list of commitments are displayed.
 * Creates both a scroll pane and JList which stores all of the commitments.
 * @version Revision: 1.0
 * @author
 */

public class CommitmentListPanel extends JPanel implements MouseListener{

	private CommitmentListModel model;
	private JList<Commitment> commitmentList;
	private Boolean EDITMODE = false;
	
	private JButton updateCommitmentButton;
	private JButton cancelButton;
	private JButton deleteButton;
	
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
	
		 commitmentList.addMouseListener(this);
	}
	
	/**
	 * Public accessor for the JList of commitments
	 * @return JList<Commitment>: The list of Commitments.
	 */
	public JList<Commitment> getCommitmentList() {
		return commitmentList;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		   if (e.getClickCount() == 2) {
	        	 if (commitmentList.contains(e.getPoint())) { 
	        		  this.editCommitment(commitmentList.getSelectedValue());
	        		 }
	            
	          }
		
	}
	public void editCommitment(Commitment c)
	{
		this.removeAll();
		this.repaint();
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.add(new JLabel("<html>Commitment Name:  " + c.getName() + "</html>"));
		this.add(new JLabel("Commitment Date:  " + c.getDueDate()));
		this.add(new JLabel("Commitment Category:  " + c.getCategory().getName()));
		this.add(new JLabel("<html>Commitment Descrption:  " + c.getDescription() + "</html>"));
		/** Setup gui for editing commitments **/
		
		// Add / Cancel buttons
				updateCommitmentButton = new JButton("Update Commitment");
				updateCommitmentButton.setActionCommand("updatecommitment");
				//updateCommitmentButton.addActionListener(new AddCommitmentController(this));
				
				this.add(updateCommitmentButton, "alignx left, split 2");
				
				cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("cancel");
				
				this.add(cancelButton, "alignx left");
				
				//Action Listener for Cancel Button
				//cancelButton.addActionListener(this);
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
