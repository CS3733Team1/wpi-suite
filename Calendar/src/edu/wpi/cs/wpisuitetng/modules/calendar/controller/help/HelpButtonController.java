/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.controller.help;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.help.HelpWindow;

public class HelpButtonController implements ActionListener {
	private final static Logger LOGGER = Logger.getLogger(HelpButtonController.class.getName());
	
	private CalendarPanel calendarPanel;
	
	public HelpButtonController(CalendarPanel calendarPanel){
		this.calendarPanel = calendarPanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		HelpWindow helpWindow = new HelpWindow();
		ImageIcon miniHelpIcon = new ImageIcon();
		try {
			miniHelpIcon = new ImageIcon(ImageIO.read(getClass().getResource("/images/question.png")));
		} catch (IOException exception) {
			LOGGER.log(Level.WARNING, "/images/question.png not found.", exception);
		}
		calendarPanel.addTab("Help", miniHelpIcon, helpWindow);
		calendarPanel.setSelectedComponent(helpWindow);
	}
}
