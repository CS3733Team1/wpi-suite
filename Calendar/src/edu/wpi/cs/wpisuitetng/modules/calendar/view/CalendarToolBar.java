/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class CalendarToolBar extends JPanel {
	// Always visible
	private JButton refreshButton;

	// Visible always except while user is focused on Manage Categories Tab
	private JButton categoryButton;

	// Visible only while user is focused on Manage Categories Tab
	private JButton addCategoryButton, deleteCategoryButton;

	// Visible always except while user is focused on Manage Filters Tab
	private JButton filterButton;

	// Visible only while user is focused on Manage Filters Tab
	private JButton addFilterButton, deleteFilterButton;

	// Always Visible
	private JButton addCommitmentButton;
	private JButton addEventButton;

	// Visible only when commitments/events are selected on either calendar tab
	private JButton deleteCommitmentButton;
	private JButton deleteEventButton;

	public CalendarToolBar() {
		try {
			refreshButton = new JButton(new ImageIcon(ImageIO.read(getClass().getResource("/images/refresh_icon.png"))));

			categoryButton = new JButton(new ImageIcon(ImageIO.read(getClass().getResource("/images/categories.png"))));

			addCategoryButton = new JButton("<html>New<br/>Category</html>",
					new ImageIcon(ImageIO.read(getClass().getResource("/images/add_category.png"))));
			deleteCategoryButton = new JButton("<html>Delete<br/>Category</html>",
					new ImageIcon(ImageIO.read(getClass().getResource("/images/delete_category.png"))));

			filterButton = new JButton(new ImageIcon(ImageIO.read(getClass().getResource("/images/filters.png"))));

			addFilterButton = new JButton("<html>New<br/>Filter</html>",
					new ImageIcon(ImageIO.read(getClass().getResource("/images/add_filter.png"))));
			deleteFilterButton = new JButton("<html>Delete<br/>Filter</html>",
					new ImageIcon(ImageIO.read(getClass().getResource("/images/delete_filter.png"))));

			addCommitmentButton = new JButton("<html>New<br/>Commitment</html>",
					new ImageIcon(ImageIO.read(getClass().getResource("/images/add_commitment.png"))));
			deleteCommitmentButton = new JButton("<html>Delete<br/>Commitment</html>",
					new ImageIcon(ImageIO.read(getClass().getResource("/images/delete_commitment.png"))));

			addEventButton = new JButton("<html>New<br/>Event</html>",
					new ImageIcon(ImageIO.read(getClass().getResource("/images/add_event.png"))));
			deleteEventButton = new JButton("<html>Delete<br/>Event</html>",
					new ImageIcon(ImageIO.read(getClass().getResource("/images/delete_event.png"))));
		} catch (IOException e) {}

		
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		this.setToolBarCalendarTab();
	}

	// Notifies CalendarToolBar that the buttons should switch to the CalendarTab button arrangement.
	// Delete Event and Commitment disabled until Events or Commitments are selected.
	public void setToolBarCalendarTab() {
		this.removeAll();

		this.add(refreshButton);

		this.add(Box.createHorizontalStrut(25));

		this.add(categoryButton);
		this.add(filterButton);

		this.add(Box.createHorizontalStrut(25));

		this.add(addCommitmentButton);
		this.add(deleteCommitmentButton);

		this.add(Box.createHorizontalStrut(25));

		this.add(addEventButton);
		this.add(deleteEventButton);
	}

	// Notifies CalendarToolBar that the buttons should switch to the Event/CommitmentTab button arrangement.
	// Delete Event and Commitment removed.
	public void setToolBarEventCommitment() {
		this.removeAll();

		this.add(refreshButton);

		this.add(Box.createHorizontalStrut(25));

		this.add(categoryButton);
		this.add(filterButton);

		this.add(Box.createHorizontalStrut(25));

		this.add(addCommitmentButton);

		this.add(Box.createHorizontalStrut(25));

		this.add(addEventButton);
	}

	// Notifies CalendarToolBar that the buttons should switch to the CategoryTab button arrangement.
	// Delete Event and Commitment removed.
	// Category button replaced by add and remove category.
	public void setToolBarCategory() {
		this.removeAll();

		this.add(refreshButton);

		this.add(Box.createHorizontalStrut(25));

		JPanel categoryButtonPanel = new JPanel();
		categoryButtonPanel.setLayout(new BoxLayout(categoryButtonPanel, BoxLayout.PAGE_AXIS));

		categoryButtonPanel.add(addCategoryButton);
		categoryButtonPanel.add(deleteCategoryButton);

		this.add(categoryButtonPanel);

		this.add(Box.createHorizontalStrut(25));

		this.add(filterButton);

		this.add(Box.createHorizontalStrut(25));

		this.add(addCommitmentButton);

		this.add(Box.createHorizontalStrut(25));

		this.add(addEventButton);
	}

	// Notifies CalendarToolBar that the buttons should switch to the FilterTab button arrangement.
	// Delete Event and Commitment removed.
	// Filter button replaced by add and remove filter.
	public void setToolBarFilter() {
		this.removeAll();

		this.add(refreshButton);

		this.add(Box.createHorizontalStrut(25));

		this.add(categoryButton);

		this.add(Box.createHorizontalStrut(25));

		JPanel filterButtonPanel = new JPanel();
		filterButtonPanel.setLayout(new BoxLayout(filterButtonPanel, BoxLayout.PAGE_AXIS));

		filterButtonPanel.add(addFilterButton);
		filterButtonPanel.add(deleteFilterButton);

		this.add(filterButtonPanel);

		this.add(Box.createHorizontalStrut(25));

		this.add(addCommitmentButton);

		this.add(Box.createHorizontalStrut(25));

		this.add(addEventButton);
	}

	// Functions to set listeners for the buttons

	public void refreshButtonListener(ActionListener l) {
		refreshButton.addActionListener(l);
	}

	public void categoryButtonListener(ActionListener l) {
		categoryButton.addActionListener(l);
	}

	public void addCategoryButtonListener(ActionListener l) {
		addCategoryButton.addActionListener(l);
	}

	public void deleteCategoryButtonListener(ActionListener l) {
		deleteCategoryButton.addActionListener(l);
	}

	public void filterButtonListener(ActionListener l) {
		filterButton.addActionListener(l);
	}

	public void addFilterButtonListener(ActionListener l) {
		addFilterButton.addActionListener(l);
	}

	public void deleteFilterButtonListener(ActionListener l) {
		deleteFilterButton.addActionListener(l);
	}

	public void addEventButtonListener(ActionListener l) {
		addEventButton.addActionListener(l);
	}

	public void deleteEventButtonListener(ActionListener l) {
		deleteEventButton.addActionListener(l);
	}

	public void addCommitmentButtonListener(ActionListener l) {
		addCommitmentButton.addActionListener(l);
	}

	public void deleteCommitmentButtonListener(ActionListener l) {
		deleteCommitmentButton.addActionListener(l);
	}
}
