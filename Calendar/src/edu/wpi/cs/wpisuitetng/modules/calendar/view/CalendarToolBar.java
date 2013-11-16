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

import java.awt.BorderLayout;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.calendar.controller.commitment.DisplayCommitmentController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.commitment.RemoveCommitmentController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.event.DisplayEventController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.event.RemoveEventController;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.MainModel;

public class CalendarToolBar extends JPanel {
	private MainModel model;
	private MainView view;
	
	private JButton addCommitmentButton, deleteCommitmentButton;
	private JButton addEventButton, deleteEventButton;
	
	
	public CalendarToolBar(MainModel model, MainView view) {
		
		this.model = model;
		this.view = view;
		
		JPanel addDeletePanel = new JPanel();
		
		try {
			addCommitmentButton = new JButton("<html>New<br/>Commitment</html>",
					new ImageIcon(ImageIO.read(getClass().getResource("/images/add_commitment.png"))));
			deleteCommitmentButton = new JButton("<html>Delete<br/>Commitment</html>",
					new ImageIcon(ImageIO.read(getClass().getResource("/images/delete_commitment.png"))));
			
			addEventButton = new JButton("<html>New<br/>Event</html>",
					new ImageIcon(ImageIO.read(getClass().getResource("/images/add_event.png"))));
			deleteEventButton = new JButton("<html>Delete<br/>Event</html>",
					new ImageIcon(ImageIO.read(getClass().getResource("/images/delete_event.png"))));
		} catch (IOException e) {}
		
		this.setLayout(new BorderLayout());
		
		addEventButton.addActionListener(new DisplayEventController(view, model));
		deleteEventButton.addActionListener(new RemoveEventController(view, model));
		addCommitmentButton.addActionListener(new DisplayCommitmentController(view, model));
		deleteCommitmentButton.addActionListener(new RemoveCommitmentController(view, model));
		
		addDeletePanel.add(addCommitmentButton);
		addDeletePanel.add(deleteCommitmentButton);
		addDeletePanel.add(addEventButton);
		addDeletePanel.add(deleteEventButton);
		
		this.add(addDeletePanel, BorderLayout.CENTER);
	}
}
