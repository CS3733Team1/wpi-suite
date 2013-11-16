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

import edu.wpi.cs.wpisuitetng.modules.calendar.model.MainModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.MainView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.event.EventTabPanel;

/**
 * This Controller is based largely off of DisplayCommitmentController 
 * in the calendar->controller.display
 * @version $Revision: 1.0 $
 * @author rbansal
 */

public class DisplayEventController implements ActionListener{
	MainModel model;
	MainView view;
	
	public DisplayEventController(MainView view, MainModel model){
		this.model = model;
		this.view = view;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		EventTabPanel pan = new EventTabPanel(view, model);
		view.getCalendarPanel().addTab("Add Event",pan);
		view.getCalendarPanel().setSelectedComponent(pan);
	}
	

}
