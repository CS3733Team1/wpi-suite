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
import java.awt.Font;
import java.awt.Graphics;
import java.util.Date;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.ICalendarView;

public class DayCalendarPanel extends JPanel implements ICalendarView{

	private DayCalendarScrollPane dayscroll;
	private DayCalendarLayerPane daylayer;
	public static final String[] weekNames = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
	private JLabel dayLabel;
	private JPanel daytitle;


	public DayCalendarPanel(){
		this.setBorder(BorderFactory.createTitledBorder(null,
				"Day View", TitledBorder.LEFT, TitledBorder.TOP,
				new Font("null", Font.BOLD, 12), Color.BLUE));


		this.setLayout(new MigLayout("fill"));

		daylayer = new DayCalendarLayerPane();

		JPanel daytitle = new JPanel(new MigLayout("fill, insets 0", "[20%][80%]"));

		dayLabel = new JLabel(weekNames[(daylayer.getDayViewDate().getDay())]);

		JPanel dayname = new JPanel(new MigLayout("fill"));
		dayname.add(dayLabel, "grow, aligny center");
		dayname.setBackground(new Color(138,173,209));

		JPanel time = new JPanel(new MigLayout("fill"));
		time.add(new JLabel("Time"), "grow, aligny center");
		time.setBackground(new Color(138,173,209));

		daytitle.add(time, "aligny center, w 5000, grow");
		daytitle.add(dayname,  "aligny center, w 5000, grow");

		this.add(daytitle, "grow, wrap");

		dayscroll = new DayCalendarScrollPane(daylayer);

		this.add(dayscroll, "grow, push");
		
		int end = dayscroll.getVerticalScrollBar().getMaximum();
		dayscroll.getVerticalScrollBar().setValue(end * 3 / 4);

	}


	public void paint(Graphics g){
		if (daylayer != null){
			daylayer.reSize(this.getWidth() - (dayscroll.getVerticalScrollBar().getWidth()*2));
		}

		super.paint(g);
	}

	public void updateDay(){
		dayLabel.setText(weekNames[daylayer.getDayViewDate().getDay()]);
//		daytitle.resize(size());
	}
	
	public Date getDate(){
		return daylayer.getDayViewDate();
	}

	public void repaint(){
		if (daylayer != null){
			daylayer.reSize(this.getWidth() - (dayscroll.getVerticalScrollBar().getWidth()*2));
		}

		super.repaint();
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return daylayer.getTitle();
	}

	@Override
	public void next() {
		// TODO Auto-generated method stub
		daylayer.next();
		updateDay();


	}

	@Override
	public void previous() {
		// TODO Auto-generated method stub

		daylayer.previous();
		updateDay();

	}

	@Override
	public void today() {
		// TODO Auto-generated method stub
		daylayer.today();
		updateDay();

	}


	@Override
	public void viewDate(Calendar date) {
		// TODO Auto-generated method stub
		daylayer.viewDate(date.getTime());
		updateDay();
	}

}
