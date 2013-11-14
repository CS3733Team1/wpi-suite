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

import edu.wpi.cs.wpisuitetng.modules.calendar.model.MainModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.MainView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.commitment.AddCommitmentTabPanel;

public class DisplayCommitmentController implements ActionListener{
	MainModel model;
	MainView view;
	
	public DisplayCommitmentController(MainView view, MainModel model){
		this.model = model;
		this.view = view;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		AddCommitmentTabPanel pan = new AddCommitmentTabPanel(view, model);
		view.getCalendarPanel().addTab("Add Commitment",pan);
		view.getCalendarPanel().setSelectedComponent(pan);
	}
}
