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

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.FilteredCommitmentsListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.CustomListSelectionModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.RightClickCommitmentMenu;

/**
 * This is the view where the list of commitments are displayed.
 * Creates both a scroll pane and JList which stores all of the commitments.
 * @version Revision: 1.0
 * @author
 */

public class CommitmentListPanel extends JPanel implements ActionListener, MouseListener {

	private FilteredCommitmentsListModel model;
	private CalendarPanel calendarPanel;
	private JList<Commitment> commitmentList;

	private JEditorPane detailDisplay;
	private JButton updateCommitmentButton;
	private JButton cancelButton;
	private Commitment selectedCommitment;
	private RightClickCommitmentMenu rightClickCommitmentMenu;
	/**
	 * Constructor for the CommitmentListPanel creates both the list of commitments
	 * and the scroll pane that they are displayed on.
	 */
	public CommitmentListPanel(CalendarPanel calendarPanel) {
		this.model = FilteredCommitmentsListModel.getFilteredCommitmentsListModel();
		this.calendarPanel = calendarPanel;
		viewCommitments();
	}

	/**
	 * Public accessor for the JList of commitments
	 * @return JList<Commitment>: The list of Commitments.
	 */
	public JList<Commitment> getCommitmentList() {
		return commitmentList;
	}

	public void editCommitment() {
		this.removeAll();
		this.repaint();

		this.setLayout(new MigLayout("fill", "[grow, fill]", "[][grow, fill]"));

		updateCommitmentButton = new JButton("<html>Edit</html>");
		cancelButton = new JButton("<html>Close</html>");

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


		/** Setup gui for editing commitments **/
	}

	private void viewCommitments() {
		this.removeAll();
		this.repaint();

		this.setLayout(new MigLayout("fill, insets 0 0","[]","[][]"));

		commitmentList = new JList<Commitment>(model);
		commitmentList.setSelectionModel(new CustomListSelectionModel());

		commitmentList.setCellRenderer(new CommitmentListCellRenderer());
		commitmentList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		commitmentList.setLayoutOrientation(JList.VERTICAL);

		commitmentList.setVisibleRowCount(0);

		JScrollPane scrollPane = new JScrollPane(commitmentList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(scrollPane, "grow, push");

		commitmentList.addMouseListener(this);
	}

	public void openUpdateCommitmentTabPanel() {
//		CommitmentTabPanel commitmentPanel = new CommitmentTabPanel(selectedCommitment);
//		ImageIcon miniCommitmentIcon = new ImageIcon();
//		try {
//			miniCommitmentIcon = new ImageIcon(ImageIO.read(getClass().getResource("/images/commitment.png")));
//		} catch (IOException exception) {}
//		calendarPanel.addTab("Update Commitment", miniCommitmentIcon, commitmentPanel);
//		calendarPanel.setSelectedComponent(commitmentPanel);	
		RightClickCommitmentMenu.openUpdateCommitmentTab(calendarPanel, selectedCommitment);
	}

	public void setSelectedCommitment(Commitment c){
		selectedCommitment = c;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("cancel")) {
			viewCommitments();
		}
		else if (e.getActionCommand().equals("updatecommitment")) {
			viewCommitments();
			openUpdateCommitmentTabPanel();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			Rectangle r = commitmentList.getCellBounds(0, commitmentList.getLastVisibleIndex());
			if (r != null && r.contains(e.getPoint())) {
				selectedCommitment = commitmentList.getModel().getElementAt(commitmentList.locationToIndex(e.getPoint()));
				editCommitment();
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Rectangle r = commitmentList.getCellBounds(0, commitmentList.getLastVisibleIndex());
		if(r == null || !r.contains(e.getPoint())) {
			if(Arrays.binarySearch(commitmentList.getSelectedIndices(), commitmentList.getLastVisibleIndex()) >= 0)
				commitmentList.getSelectionModel().removeIndexInterval(commitmentList.getLastVisibleIndex(), commitmentList.getLastVisibleIndex());
			else commitmentList.getSelectionModel().addSelectionInterval(commitmentList.getLastVisibleIndex(), commitmentList.getLastVisibleIndex());
		}
	}

	// Unused
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.isPopupTrigger()){
			Rectangle r = commitmentList.getCellBounds(0, commitmentList.getLastVisibleIndex());
			if (r != null && r.contains(e.getPoint())) {
				//selectedCommitment = commitmentList.getModel().getElementAt(commitmentList.locationToIndex(e.getPoint()));
				commitmentList.setSelectedIndex(commitmentList.locationToIndex(e.getPoint()));
				rightClickCommitmentMenu = new RightClickCommitmentMenu(commitmentList.getSelectedValuesList(), calendarPanel);
				rightClickCommitmentMenu.show(e.getComponent(),e.getX(),e.getY());
			}
		}
	}
}
