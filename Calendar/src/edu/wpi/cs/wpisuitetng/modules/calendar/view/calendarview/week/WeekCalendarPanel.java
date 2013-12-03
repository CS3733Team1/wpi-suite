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
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.ICalendarView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day.DayCalendarLayerPane;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day.DayCalendarScrollPane;

public class WeekCalendarPanel extends JPanel implements ICalendarView {

	private WeekCalendarScrollPane weekscroll;
	private WeekCalendarLayerPane weeklayer;
	
	public WeekCalendarPanel(){
		this.setBorder(BorderFactory.createTitledBorder(null,
	            "Week View", TitledBorder.LEFT, TitledBorder.TOP,
	            new Font("null", Font.BOLD, 12), Color.BLUE));
		
		
		this.setLayout(new MigLayout("fill", 
				"[100%]", 
				"[100%]"));
		
		weeklayer = new WeekCalendarLayerPane();
		
		weekscroll = new WeekCalendarScrollPane(weeklayer);
		
		StringBuilder scrollbuilder = new StringBuilder();
		scrollbuilder.append("cell ");
		scrollbuilder.append("0");
		scrollbuilder.append(" ");
		scrollbuilder.append("0");
		scrollbuilder.append(",grow, push");
		
		this.add(weekscroll, scrollbuilder.toString());
		
		repaint();
	}
	
	public void paint(Graphics g){
		if (weeklayer != null){
			weeklayer.reSize(this.getWidth() - (weekscroll.getVerticalScrollBar().getWidth()*3));
		}
		
		super.paint(g);
	}
	 
	public void repaint(){
		if (weeklayer != null){
			weeklayer.reSize(this.getWidth() - (weekscroll.getVerticalScrollBar().getWidth()*3));
		}
		
		super.repaint();
	}
	
	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return weeklayer.getTitle();
	}

	@Override
	public void next() {
		// TODO Auto-generated method stub
		weeklayer.next();
	}

	@Override
	public void previous() {
		// TODO Auto-generated method stub
		weeklayer.previous();
	}

	@Override
	public void today() {
		// TODO Auto-generated method stub
		weeklayer.today();
	}

}
