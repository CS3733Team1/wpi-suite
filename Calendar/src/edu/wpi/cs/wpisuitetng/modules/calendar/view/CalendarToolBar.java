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
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.buttons.TransparentButton;

public class CalendarToolBar extends JPanel implements ActionListener {
	// Always visible
	private TransparentButton refreshButton;

	// Always Visible
	private TransparentButton addCommitmentButton;
	private TransparentButton addEventButton;
	private TransparentButton helpButton;
	
	// Visible only when commitments/events are selected on either calendar tab
	private TransparentButton deleteCommitmentButton;
	private TransparentButton deleteEventButton;

	//private TransparentButton easterEgg;
	
	//private ImageIcon[] easterEggIcons;

	//int eggState;
	//boolean eggHatched;
	
	//private WeatherPanel weatherPanel;

	public CalendarToolBar() {
		try {
			refreshButton = new TransparentButton(new ImageIcon(ImageIO.read(getClass().getResource("/images/refresh_icon.png"))));

			helpButton = new TransparentButton(new ImageIcon(ImageIO.read(getClass().getResource("/images/question.png"))));
			
			addCommitmentButton = new TransparentButton("<html>New<br/>Commitment</html>",
					new ImageIcon(ImageIO.read(getClass().getResource("/images/add_commitment.png"))));
			deleteCommitmentButton = new TransparentButton("<html>Delete<br/>Commitment</html>",
					new ImageIcon(ImageIO.read(getClass().getResource("/images/delete_commitment.png"))));
			
			
			addEventButton = new TransparentButton("<html>New<br/>Event</html>",
					new ImageIcon(ImageIO.read(getClass().getResource("/images/add_event.png"))));
			deleteEventButton = new TransparentButton("<html>Delete<br/>Event</html>",
					new ImageIcon(ImageIO.read(getClass().getResource("/images/delete_event.png"))));

			//easterEggIcons = new ImageIcon[4];
			//easterEggIcons[0] = new ImageIcon(ImageIO.read(getClass().getResource("/images/color_meter.png")));
			//easterEggIcons[1] = new ImageIcon(ImageIO.read(getClass().getResource("/images/color_meter.png")));
			//easterEggIcons[2] = new ImageIcon(ImageIO.read(getClass().getResource("/images/color_meter.png")));
			//easterEggIcons[3] = new ImageIcon(ImageIO.read(getClass().getResource("/images/color_meter.png")));
			
			//easterEgg =  new TransparentButton(easterEggIcons[0]);
		} catch (IOException e) {}

		//weatherPanel = new WeatherPanel();
		//eggHatched = false;
		//eggState = 0;
		//easterEgg.addActionListener(this);

		this.setToolBarCalendarTab();
	}

	// Notifies CalendarToolBar that the buttons should switch to the CalendarTab button arrangement.
	// Delete Event and Commitment disabled until Events or Commitments are selected.
	public void setToolBarCalendarTab() {
		this.removeAll();
		//this.setLayout(new MigLayout("fill", "[][][][][]push[]"));
		this.setLayout(new MigLayout("fill", "[33.3333%][33.3333%][33.3333%]"));
		this.add(refreshButton);

		this.add(addCommitmentButton, "align center, split 4");
		this.add(deleteCommitmentButton);

		this.add(addEventButton);
		this.add(deleteEventButton);
		
		this.add(helpButton, "align right");

		//if(eggHatched) {
		//	this.add(weatherPanel);
		//} else this.add(easterEgg);
		
		this.updateUI();
	}

	// Notifies CalendarToolBar that the buttons should switch to the Event/CommitmentTab button arrangement.
	// Delete Event and Commitment removed.
	public void setToolBarEventCommitment() {
		this.removeAll();
		this.setLayout(new MigLayout("fill", "[33.3333%][33.3333%][33.3333%]"));
		this.add(refreshButton);

		this.add(addCommitmentButton, "align center, split 2");
		this.add(addEventButton);
		
		this.add(helpButton, "align right");

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

	public void helpButtonListener(ActionListener l) {
		helpButton.addActionListener(l);
	}
	
	public synchronized void clickRefreshButton() {
		this.refreshButton.doClick();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		//eggState++;
		//if(eggState > 3) {
		//	eggHatched = true;
		//	setToolBarCalendarTab();
		//} else {
		//	easterEgg.setIcon(easterEggIcons[eggState]);
		//	easterEgg.setText("" + eggState);
		//}
	}
}