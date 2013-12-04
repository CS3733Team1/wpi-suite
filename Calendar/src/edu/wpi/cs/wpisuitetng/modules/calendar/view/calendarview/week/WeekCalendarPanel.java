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
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day.DayCalendarLayerPane;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day.DayCalendarScrollPane;

public class WeekCalendarPanel extends JPanel implements ICalendarView {

	private WeekCalendarScrollPane weekscroll;
	private WeekCalendarLayerPane weeklayer;
	private JPanel weektitle;
	public static final String[] weekNames = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
	
	
	public WeekCalendarPanel(){
		this.setBorder(BorderFactory.createTitledBorder(null,
	            "Week View", TitledBorder.LEFT, TitledBorder.TOP,
	            new Font("null", Font.BOLD, 12), Color.BLUE));
		
		
		this.setLayout(new MigLayout("fill","[100%]","[15%][100%]"));
		
		weeklayer = new WeekCalendarLayerPane();
		
		weektitle = new JPanel(new MigLayout("insets 6", "[grow][grow][grow][grow][grow][grow][grow][grow]"));
		
		JPanel time = new JPanel(new MigLayout("fill"));
		time.add(new JLabel("Time"), "align center");
		time.setBackground(new Color(138,173,209));
		
		for(int days = 1; days < 8; days++){
			JPanel weekName = new JPanel(new MigLayout("fill"));
			
			StringBuilder weekbuilder = new StringBuilder();
			weekbuilder.append("cell ");
			weekbuilder.append((new Integer(days)).toString());
			weekbuilder.append(" ");
			weekbuilder.append("0");
			weekbuilder.append(",wmin 130, alignx center, growy");
			
			weekName.add(new JLabel(weekNames[days-1]),"align center");
			
			
			weekName.setBackground(new Color(138,173,209));
			weektitle.add(weekName, weekbuilder.toString());
		}
		
		weektitle.add(time, "cell 0 0, wmin 80, alignx center, growy");
		
		this.add(weektitle, "cell 0 0,grow,aligny top");
		
		weekscroll = new WeekCalendarScrollPane(weeklayer);
		
		StringBuilder scrollbuilder = new StringBuilder();
		scrollbuilder.append("cell ");
		scrollbuilder.append("0");
		scrollbuilder.append(" ");
		scrollbuilder.append("1");
		scrollbuilder.append(",grow, push, aligny top");
		
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
