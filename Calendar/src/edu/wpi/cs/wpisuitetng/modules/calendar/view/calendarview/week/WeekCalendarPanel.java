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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.ICalendarView;

/**
 * The second level of the week view hierarchy. Holds the WeekCalendarLayerPane and the WeekCalendarScrollPane.
 */

public class WeekCalendarPanel extends JPanel implements ICalendarView {

	private WeekCalendarScrollPane weekscroll;
	private WeekCalendarLayerPane weeklayer;
	private JPanel weektitle;
	public static final String[] weekNames = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
	
	
	public WeekCalendarPanel(){
		this.setBorder(BorderFactory.createTitledBorder(null,
	            "Week View", TitledBorder.LEFT, TitledBorder.TOP,
	            new Font("null", Font.BOLD, 12), Color.BLUE));
		
		
		this.setLayout(new MigLayout("fill"));
		
		weeklayer = new WeekCalendarLayerPane();
		
		weektitle = new JPanel(new MigLayout("fill, insets 0", "[9%][13%][13%][13%][13%][13%][13%][13%]18px"));
		
		JPanel time = new JPanel(new MigLayout("fill"));
		time.add(new JLabel("Time"), "grow, aligny center");
		time.setBackground(new Color(138,173,209));
		
		weektitle.add(time, "aligny center, w 5000, grow");
		
		for(int days = 1; days < 8; days++){
			JPanel weekName = new JPanel(new MigLayout("fill"));
			
			weekName.add(new JLabel(weekNames[days-1]),"grow, aligny center");
			weekName.setBackground(new Color(138,173,209));
			weektitle.add(weekName, "aligny center, w 5000, grow");
		}
		
		JPanel filler = new JPanel(new MigLayout("fill"));
		filler.add(weektitle, "grow, wrap");
		this.add(filler, "grow, wrap");
		
		weekscroll = new WeekCalendarScrollPane(weeklayer);
		
		this.add(weekscroll, "grow, push");
		
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
		weekscroll.updateDayHeader();
	}

	@Override
	public void previous() {
		// TODO Auto-generated method stub
		weeklayer.previous();
		weekscroll.updateDayHeader();
	}

	@Override
	public void today() {
		// TODO Auto-generated method stub
		weeklayer.today();
		weekscroll.updateDayHeader();
	}

}
