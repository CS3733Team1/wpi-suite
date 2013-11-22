/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.controller.commitment;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.commitment.CommitmentTabPanel;

public class DisplayCommitmentTabController implements ActionListener {
	private CalendarPanel calendarPanel;
	
	public DisplayCommitmentTabController(CalendarPanel calendarPanel){
		this.calendarPanel = calendarPanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		CommitmentTabPanel commitmentPanel = new CommitmentTabPanel();
		ImageIcon miniCommitmentIcon = new ImageIcon();
		try {
			miniCommitmentIcon = new ImageIcon(ImageIO.read(getClass().getResource("/images/commitment.png")));
		} catch (IOException exception) {}
		calendarPanel.addTab("Add Commitment", miniCommitmentIcon, commitmentPanel);
		calendarPanel.setSelectedComponent(commitmentPanel);
	}
}
