package edu.wpi.cs.wpisuitetng.modules.calendar;

/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Chris Casola
 *    Andrew Hurle
 ******************************************************************************/

import java.util.List;

import edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule;
import edu.wpi.cs.wpisuitetng.janeway.modules.JanewayTabModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.CalendarController;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.CalendarModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarView;

public class Calendar implements IJanewayModule {

	private CalendarModel model;
	private CalendarView view;

	public Calendar() {
		model = new CalendarModel();
		view = new CalendarView(model);
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule#getName()
	 */
	@Override
	public String getName() {
		return view.getName();
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule#getTabs()
	 */
	@Override
	public List<JanewayTabModel> getTabs() {
		return view.getTabs();
	}
}
