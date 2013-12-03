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

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import net.miginfocom.swing.MigLayout;

public class DayCalendarScrollPane extends JScrollPane{
	private DayCalendarLayerPane daylayer;
	public static final String[] weekNames = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
	private JLabel dayLabel;
	
	public DayCalendarScrollPane(DayCalendarLayerPane day){
		super(day);
		
		daylayer = day;
		
		JPanel daytitle = new JPanel(new MigLayout("fill, insets 6","[pref!][800px,grow,fill]", "[]"));
		daytitle.setBackground(Color.WHITE);
		
		
		dayLabel = new JLabel(weekNames[daylayer.getDayViewDate().getDay()]);
		
		JPanel dayname = new JPanel(new MigLayout("fill"));
		dayname.add(dayLabel, "push, align center");
		dayname.setBackground(new Color(138,173,209));

		JPanel time = new JPanel(new MigLayout("fill","[175px,grow]", "[]"));
		time.add(new JLabel("Time"), "push, align center");
		time.setBackground(new Color(138,173,209));

		daytitle.add(time, "cell 0 0");
		daytitle.add(dayname,  "cell 1 0,wmin 80,alignx left,growy");
		
		JPanel cornertile = new JPanel();
		cornertile.setBackground(Color.WHITE);
		
		this.setCorner(UPPER_RIGHT_CORNER, cornertile);
		this.setColumnHeaderView(daytitle);
		this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	
	}
	
	
	public void updateDay(){
		dayLabel.setText(weekNames[daylayer.getDayViewDate().getDay()]);
		repaint();
	}
	
}
