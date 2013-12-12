/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day;

import java.awt.Font;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.ISchedulable;

public class EventPanel extends JPanel{
	/**
	 * 
	 */
	private ISchedulable e;
	
	public EventPanel(ISchedulable eve){
		e = eve;
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawString(e.getName(), 0, this.getHeight()*3/4);
	}
	
}
