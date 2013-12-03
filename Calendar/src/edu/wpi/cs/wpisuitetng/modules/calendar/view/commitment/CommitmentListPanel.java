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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.commitment.AddCommitmentController;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.CommitmentListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.filter.FilterListPanel;

/**
 * This is the view where the list of commitments are displayed.
 * Creates both a scroll pane and JList which stores all of the commitments.
 * @version Revision: 1.0
 * @author
 */

public class CommitmentListPanel extends JPanel implements ActionListener, MouseListener{

	private CommitmentListModel model;
	private CalendarPanel calendarPanel;
	private JList<Commitment> commitmentList;
	private Boolean EDITMODE = false;
	
	private JEditorPane detailDisplay;
	private JButton updateCommitmentButton;
	private JButton cancelButton;
	private JButton deleteButton;
	private Commitment selectedCommitment;
	
	/**
	 * Constructor for the CommitmentListPanel creates both the list of commitments
	 * and the scroll pane that they are displayed on.
	 */
	public CommitmentListPanel(CalendarPanel calendarPanel) {
		this.model = CommitmentListModel.getCommitmentListModel();
		this.calendarPanel = calendarPanel;
		viewCommitments();
		/*
		this.setLayout(new MigLayout("fill, insets 0"));
		
		commitmentList = new JList<Commitment>(model);
		
		commitmentList.setCellRenderer(new CommitmentListCellRenderer());
		commitmentList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		commitmentList.setLayoutOrientation(JList.VERTICAL);
		
		commitmentList.setVisibleRowCount(0);

		JScrollPane scrollPane = new JScrollPane(commitmentList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		this.add(scrollPane, "grow, push");
	
		 commitmentList.addMouseListener(this);*/
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
	        	 if (commitmentList.contains(e.getPoint())) 
	        	 { 
	        		 selectedCommitment = commitmentList.getSelectedValue();
	        		 editCommitment();
	        	}
	            
	          }
		
	}
	public void editCommitment()
	{
		this.removeAll();
		this.repaint();
		
		this.setLayout(new MigLayout("fill", "[grow, fill]", "[][grow, fill]"));

		//try { Required to use Icon, none used now
		updateCommitmentButton = new JButton("<html>Edit</html>");
		cancelButton = new JButton("<html>Close</html>");
		//} catch (IOException e) {e.printStackTrace();}

		detailDisplay = new JEditorPane("text/html", selectedCommitment.toString());
		detailDisplay.setEditable(false);
		
		updateCommitmentButton.setActionCommand("updatecommitment");
		updateCommitmentButton.addActionListener(this);
		cancelButton.setActionCommand("cancel");
		cancelButton.addActionListener(this);
		
		JPanel p = new JPanel();
		p.add(updateCommitmentButton);
		p.add(cancelButton);
		
		this.add(p, "wrap");
		this.add(detailDisplay, "grow, push");
		
		
		
		/*
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		JEditorPane detailDisplay = new JEditorPane("text/html", c.toString());
		//detailDisplay.setLineWrap(true);
		//detailDisplay.setWrapStyleWord(true);
		this.add(detailDisplay);
		/*this.add(new JLabel("<html>Commitment Name:  " + c.getName() + "</html>"));
		this.add(new JLabel("Commitment Date:  " + c.getDueDate()));
		this.add(new JLabel("Commitment Category:  " + c.getCategory().getName()));
		this.add(new JLabel("<html>Commitment Descrption:  " + c.getDescription() + "</html>"));*/
		/** Setup gui for editing commitments **/
		/*
		// Add / Cancel buttons
				updateCommitmentButton = new JButton("Update Commitment");
				updateCommitmentButton.setActionCommand("updatecommitment");
				//updateCommitmentButton.addActionListener(new AddCommitmentController(this));
				
				this.add(updateCommitmentButton, "alignx left, split 2");
				
				cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("cancel");
				
				this.add(cancelButton, "alignx left");
				
				//Action Listener for Cancel Button
				//cancelButton.addActionListener(this); */
	}
	
	private void viewCommitments()
	{
		this.removeAll();
		this.repaint();
		
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
	
	private void openUpdateCommitmentTabPanel()
	{
		CommitmentTabPanel commitmentPanel = new CommitmentTabPanel(detailDisplay, selectedCommitment);
		ImageIcon miniCommitmentIcon = new ImageIcon();
		try {
			miniCommitmentIcon = new ImageIcon(ImageIO.read(getClass().getResource("/images/commitment.png")));
		} catch (IOException exception) {}
		calendarPanel.addTab("Update Commitment", miniCommitmentIcon, commitmentPanel);
		calendarPanel.setSelectedComponent(commitmentPanel);	
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("cancel")) {
			viewCommitments();
		}
		else if (e.getActionCommand().equals("updatecommitment"))
		{
			viewCommitments();
			openUpdateCommitmentTabPanel();
		}
	}
}
