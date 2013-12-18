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

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.buttons.TransparentButton;

public class CalendarToolBar extends JPanel implements ActionListener {
	private final static Logger LOGGER = Logger.getLogger(CalendarToolBar.class.getName());

	// Always visible
	private TransparentButton refreshButton;

	// Always Visible
	private TransparentButton addCommitmentButton;
	private TransparentButton addEventButton;
	private TransparentButton helpButton;

	// Visible only when commitments/events are selected on either calendar tab
	private TransparentButton deleteButton;

	private TransparentButton scheduledEventButton;

	//private TransparentButton easterEgg;

	//private ImageIcon[] easterEggIcons;

	//int eggState;
	//boolean eggHatched;

	//private WeatherPanel weatherPanel;

	public CalendarToolBar() {

		ImageIcon refreshIcon = new ImageIcon();
		ImageIcon questionIcon = new ImageIcon();
		ImageIcon addCommitmentIcon = new ImageIcon();
		ImageIcon deleteIcon = new ImageIcon();
		ImageIcon addEventIcon = new ImageIcon();
		ImageIcon scheduleEventIcon = new ImageIcon();

		try {
			refreshIcon = new ImageIcon(ImageIO.read(getClass().getResource("/images/refresh_icon.png")));
			questionIcon = new ImageIcon(ImageIO.read(getClass().getResource("/images/question.png")));
			addCommitmentIcon = new ImageIcon(ImageIO.read(getClass().getResource("/images/add_commitment.png")));
			deleteIcon = new ImageIcon(ImageIO.read(getClass().getResource("/images/delete_icon.png")));
			addEventIcon = new ImageIcon(ImageIO.read(getClass().getResource("/images/add_event.png")));
			scheduleEventIcon = new ImageIcon(ImageIO.read(getClass().getResource("/images/scheduler.png")));

			//easterEggIcons = new ImageIcon[4];
			//easterEggIcons[0] = new ImageIcon(ImageIO.read(getClass().getResource("/images/color_meter.png")));
			//easterEggIcons[1] = new ImageIcon(ImageIO.read(getClass().getResource("/images/color_meter.png")));
			//easterEggIcons[2] = new ImageIcon(ImageIO.read(getClass().getResource("/images/color_meter.png")));
			//easterEggIcons[3] = new ImageIcon(ImageIO.read(getClass().getResource("/images/color_meter.png")));
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Images not found.", e);
		}

		deleteButton = new TransparentButton("Delete", deleteIcon);
		deleteButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		refreshButton = new TransparentButton(refreshIcon);

		addCommitmentButton = new TransparentButton("<html>New<br/>Commitment</html>", addCommitmentIcon);
		addCommitmentButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		addEventButton = new TransparentButton("<html>New<br/>Event</html>", addEventIcon);
		addEventButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		scheduledEventButton = new TransparentButton("<html>New<br/>Scheduled<br/>Event</html>", scheduleEventIcon);
		scheduledEventButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		helpButton = new TransparentButton(questionIcon);

		//easterEgg =  new TransparentButton(easterEggIcons[0]);

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

		this.add(addCommitmentButton, "align left, split 3");
		this.add(addEventButton);
		this.add(scheduledEventButton);

		this.add(deleteButton);


		this.add(helpButton, "align right");

		//if(eggHatched) {
		//	this.add(weatherPanel);
		//} else this.add(easterEgg);

		this.updateUI();
	}

	// Notifies CalendarToolBar that the buttons should switch to the Event/CommitmentTab button arrangement.
	// Delete Event and Commitment removed.
	public void setToolBarCommitment() {
		this.removeAll();
		this.setLayout(new MigLayout("fill", "[50%][50%]"));
		//this.add(refreshButton);

		this.add(addCommitmentButton, "align left, split 3");
		this.add(addEventButton);
		this.add(scheduledEventButton);

		this.add(helpButton, "align right");

		this.repaint();
	}

	public void setToolBarEvent() {
		this.removeAll();
		this.setLayout(new MigLayout("fill", "[50%][50%]"));
		//this.add(refreshButton);

		this.add(addCommitmentButton, "align left, split 3");
		this.add(addEventButton);
		this.add(scheduledEventButton);

		this.add(helpButton, "align right");

		this.repaint();
	}

	public void setToolBarSchedule() {
		this.removeAll();
		this.setLayout(new MigLayout("fill", "[50%][50%]"));
		//this.add(refreshButton);

		this.add(addCommitmentButton, "align left, split 3");
		this.add(addEventButton);
		this.add(scheduledEventButton);

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
		deleteButton.addActionListener(l);
	}

	public void addCommitmentButtonListener(ActionListener l) {
		addCommitmentButton.addActionListener(l);
	}

	public void deleteCommitmentButtonListener(ActionListener l) {
		deleteButton.addActionListener(l);
	}
	
	public void deleteScheduledEventListener(ActionListener l) {
		deleteButton.addActionListener(l);
	}

	public void helpButtonListener(ActionListener l) {
		helpButton.addActionListener(l);
	}

	public synchronized void clickRefreshButton() {
		this.refreshButton.doClick();
	}
	
	public void scheduleButtonListener(ActionListener l){
		scheduledEventButton.addActionListener(l);
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

	public void addScheduledEventButtonListener (ActionListener l) {
		this.scheduledEventButton.addActionListener(l);
	}
}