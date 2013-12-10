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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.buttons.TransparentButton;

public class CalendarToolBar extends JPanel implements ActionListener {
	// Always visible
	private TransparentButton refreshButton;

	// Always Visible
	private TransparentButton addCommitmentButton;
	private TransparentButton addEventButton;

	// Visible only when commitments/events are selected on either calendar tab
	private TransparentButton deleteCommitmentButton;
	private TransparentButton deleteEventButton;
	
	private TransparentButton easterEgg;
	
	int eggState;
	boolean eggHatched;

	public CalendarToolBar() {
		try {
			refreshButton = new TransparentButton(new ImageIcon(ImageIO.read(getClass().getResource("/images/refresh_icon.png"))));

			addCommitmentButton = new TransparentButton("<html>New<br/>Commitment</html>",
					new ImageIcon(ImageIO.read(getClass().getResource("/images/add_commitment.png"))));
			deleteCommitmentButton = new TransparentButton("<html>Delete<br/>Commitment</html>",
					new ImageIcon(ImageIO.read(getClass().getResource("/images/delete_commitment.png"))));

			addEventButton = new TransparentButton("<html>New<br/>Event</html>",
					new ImageIcon(ImageIO.read(getClass().getResource("/images/add_event.png"))));
			deleteEventButton = new TransparentButton("<html>Delete<br/>Event</html>",
					new ImageIcon(ImageIO.read(getClass().getResource("/images/delete_event.png"))));
			
			easterEgg = new TransparentButton("0");//new ImageIcon(ImageIO.read(getClass().getResource("/images/egg0.png"))));
		} catch (IOException e) {}

		eggHatched = false;
		eggState = 0;
		easterEgg.addActionListener(this);
		
		this.setToolBarCalendarTab();
	}

	// Notifies CalendarToolBar that the buttons should switch to the CalendarTab button arrangement.
	// Delete Event and Commitment disabled until Events or Commitments are selected.
	public void setToolBarCalendarTab() {
		this.removeAll();
		this.setLayout(new MigLayout("fill", "[][][][][]push[]"));

		this.add(refreshButton);

		this.add(addCommitmentButton);
		this.add(deleteCommitmentButton);

		this.add(addEventButton);
		this.add(deleteEventButton);
		
		if(eggHatched) {
			this.add(new JLabel("Weather", JLabel.RIGHT));
		} else this.add(easterEgg);
		
		this.repaint();
	}

	// Notifies CalendarToolBar that the buttons should switch to the Event/CommitmentTab button arrangement.
	// Delete Event and Commitment removed.
	public void setToolBarEventCommitment() {
		this.removeAll();
		this.setLayout(new MigLayout());
		this.add(refreshButton);

		this.add(addCommitmentButton);

		this.add(addEventButton);
		
		this.repaint();
	}

	// Functions to set listeners for the buttons

	public void refreshButtonListener(ActionListener l) {
		refreshButton.addActionListener(l);
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

	@Override
	public void actionPerformed(ActionEvent arg0) {
		eggState++;
		easterEgg.setText("" + eggState);
		if(eggState > 5) {
			eggHatched = true;
			setToolBarCalendarTab();
		}
	}
}
