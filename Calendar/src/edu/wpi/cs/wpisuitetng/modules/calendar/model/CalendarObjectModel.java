/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.model;

import java.util.List;

public class CalendarObjectModel {
	private String title;
	private List<Commitment> commitments;
	private List<Event> events;

	public CalendarObjectModel(String title) {
		this.title = title;

	}

	public String getTitle() {
		return title;
	}

}
