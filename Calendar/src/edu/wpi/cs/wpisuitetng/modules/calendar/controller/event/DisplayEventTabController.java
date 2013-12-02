/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.controller.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.event.EventTabPanel;

/**
 * This Controller is based largely off of DisplayCommitmentController 
 * in the calendar->controller.display
 * @version $Revision: 1.0 $
 * @author rbansal
 */

public class DisplayEventTabController implements ActionListener {
	private CalendarPanel calendarPanel;
	
	public DisplayEventTabController(CalendarPanel calendarPanel){
		this.calendarPanel = calendarPanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		EventTabPanel eventPanel = new EventTabPanel();
		ImageIcon miniEventIcon = new ImageIcon();
		try {
			miniEventIcon = new ImageIcon(ImageIO.read(getClass().getResource("/images/event.png")));
		} catch (IOException exception) {}
		calendarPanel.addTab("Add Event", miniEventIcon , eventPanel);
		calendarPanel.setSelectedComponent(eventPanel);
	}
	

}
