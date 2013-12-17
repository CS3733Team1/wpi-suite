/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.week;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.MatteBorder;

import net.miginfocom.swing.MigLayout;

/**
 * Handles resizing and scrolling in week view.
 */

public class WeekCalendarScrollPane extends JScrollPane {
	private WeekHolderPanel weeklayer;
	private List<JPanel> weekpanel;
	private JPanel weektitle;
	private int scrollSpeed = 15;

	public WeekCalendarScrollPane(WeekHolderPanel day){
		super(day);

		weeklayer = day;

		weektitle = new JPanel(new MigLayout());
		weektitle.setBackground(Color.WHITE);
		weekpanel = new LinkedList<JPanel>();

		this.setWheelScrollingEnabled(true);
		this.getVerticalScrollBar().setUnitIncrement(scrollSpeed);

		this.setBorder(new MatteBorder(0, 0, 0, 0, Color.LIGHT_GRAY));
		for(int days = 1; days < 8; days++){
			JPanel weekName = new JPanel(new MigLayout());

			weekpanel.add(weekName);
			weektitle.add(weekName, "cell " + days + " 0, wmin 100, alignx left, growy");
		}

		JPanel cornertile = new JPanel();
		cornertile.setBackground(Color.WHITE);

		this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	}

	public void rebuildDays(){
		this.remove(weektitle);

		weektitle = new JPanel(new MigLayout());
		weektitle.setBackground(Color.WHITE);
		weekpanel = new LinkedList<JPanel>();


		for(int days = 1; days < 8; days++){
			JPanel weekName = new JPanel(new MigLayout());

			weekpanel.add(weekName);
			weektitle.add(weekName, "cell " + days + " 0, wmin 100, alignx left, growy");
		}

		JPanel cornertile = new JPanel();
		cornertile.setBackground(Color.WHITE);
	}
}
