/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.week2;

import java.awt.Color;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.FilteredCommitmentsListModel;

/**
 * Handles resizing and scrolling in week view.
 */

public class WeekCalendarScrollPane extends JScrollPane {
	private WeekHolderPanel weeklayer;
	private List<JPanel> weekpanel;
	private JPanel weektitle;
	
	public WeekCalendarScrollPane(WeekHolderPanel day){
		super(day);

		weeklayer = day;
		
		weektitle = new JPanel(new MigLayout());
		weektitle.setBackground(Color.WHITE);
		weekpanel = new LinkedList<JPanel>();
		
		
		this.setBorder(new MatteBorder(0, 0, 0, 0, Color.LIGHT_GRAY));
		for(int days = 1; days < 8; days++){
			JPanel weekName = new JPanel(new MigLayout());
			
			StringBuilder weekbuilder = new StringBuilder();
			weekbuilder.append("cell ");
			weekbuilder.append((new Integer(days)).toString());
			weekbuilder.append(" ");
			weekbuilder.append("0");
			weekbuilder.append(",wmin 100, alignx left, growy");
			
		
			weekpanel.add(weekName);
			weektitle.add(weekName, weekbuilder.toString());
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
			
			StringBuilder weekbuilder = new StringBuilder();
			weekbuilder.append("cell ");
			weekbuilder.append((new Integer(days)).toString());
			weekbuilder.append(" ");
			weekbuilder.append("0");
			weekbuilder.append(",wmin 100, alignx left, growy");
			
			weekpanel.add(weekName);
			weektitle.add(weekName, weekbuilder.toString());
		}
		
		JPanel cornertile = new JPanel();
		cornertile.setBackground(Color.WHITE);
	}
}
