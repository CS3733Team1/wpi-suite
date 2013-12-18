/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.controller.scheduledevent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.scheduledevent.ScheduledEventTabPanel;

public class DisplayScheduledEventTabController implements ActionListener {
	private final static Logger LOGGER = Logger.getLogger(DisplayScheduledEventTabController.class.getName());
	
		private CalendarPanel calendarPanel;

		public DisplayScheduledEventTabController(CalendarPanel calendarPanel){
			this.calendarPanel = calendarPanel;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			ScheduledEventTabPanel scheduledEventPanel = new ScheduledEventTabPanel();
			ImageIcon miniScheduledEventIcon = new ImageIcon();
			try {
				miniScheduledEventIcon = new ImageIcon(ImageIO.read(getClass().getResource("/images/scheduler_tabSize.png")));
			} catch (IOException exception) {
				LOGGER.log(Level.WARNING, "/images/scheduler_tabSize.png not found.", exception);
			}
			calendarPanel.addTab("Create Scheduled Event", miniScheduledEventIcon , scheduledEventPanel);
			calendarPanel.setSelectedComponent(scheduledEventPanel);
		}
	}
