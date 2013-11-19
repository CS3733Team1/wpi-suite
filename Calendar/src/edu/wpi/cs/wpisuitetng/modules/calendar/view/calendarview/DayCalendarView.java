/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview;

import java.awt.*;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ListIterator;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.OverlayLayout;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;

import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.EventListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day.DayView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day.EventView;


public class DayCalendarView extends JPanel implements ICalendarView, ListDataListener {
	private DayView dayview;
	private ArrayList<EventView> eventviewlist;
	private JLayeredPane calendar;
	private Integer layer;
	
	public DayCalendarView()
	{
		layer = 0;
		dayview = new DayView();
		eventviewlist = new ArrayList<EventView>();
		calendar = new JLayeredPane();
		
		
		calendar.add(dayview, layer, -1);
		layer++;
		
		ChangeTheWorld();
		
		this.add(calendar);
		calendar.setVisible(true);
		this.setVisible(true);
	}
	
	public void ChangeTheWorld(){
		Date key;
		
		ClearEvents();
		ListIterator<Event> event = EventListModel.getEventListModel().getList().listIterator();
		
		while(event.hasNext()){
			Event eve = event.next();
			Date evedate = eve.getStartDate();
			key = new Date(evedate.getYear(),evedate.getMonth(),evedate.getDate(),evedate.getHours(),0);
			if (dayview.getMap().containsKey(key)){
				//eventviewlist.add(new EventView(eve));
			}
		}
		
		for(EventView e: eventviewlist)
		{
			calendar.add(e, layer,-1);
			layer++;
		}
	}
	
	public void ClearEvents()
	{
		for(EventView e: eventviewlist)
		{
			calendar.remove(e);
		}
		
		eventviewlist = new ArrayList<EventView>();
	}
	
	public void update(Graphics g){
		dayview.setBounds(0,0,this.getWidth(),this.getHeight());
		dayview.setPreferredSize(new Dimension(this.getWidth(),this.getHeight()));
		dayview.setSize(this.getWidth(), this.getHeight());
		
		for (int x = 0; x < eventviewlist.size();x++){
			eventviewlist.get(x).setBounds(0,12,this.getWidth(), this.getHeight());
			eventviewlist.get(x).setPreferredSize(new Dimension(this.getWidth(),this.getHeight()));
			eventviewlist.get(x).setSize(this.getWidth(), this.getHeight());
		}
		
		super.update(g);
	}
	
	public void repaint(){
		dayview.setBounds(0,0,this.getWidth(),this.getHeight());
		dayview.setPreferredSize(new Dimension(this.getWidth(),this.getHeight()));
		dayview.setSize(this.getWidth(), this.getHeight());
		
		for (int x = 0; x < eventviewlist.size();x++){
			eventviewlist.get(x).setBounds(0,12,this.getWidth(), this.getHeight());
			eventviewlist.get(x).setPreferredSize(new Dimension(this.getWidth(),this.getHeight()));
			eventviewlist.get(x).setSize(this.getWidth(), this.getHeight());
		}
		
		super.repaint();
	}
	
	public void paint(Graphics g){
		dayview.setBounds(0,0,this.getWidth(),this.getHeight());
		dayview.setPreferredSize(new Dimension(this.getWidth(),this.getHeight()));
		dayview.setSize(this.getWidth(), this.getHeight());
		
		for (int x = 0; x < eventviewlist.size();x++){
			eventviewlist.get(x).setBounds(0,12,this.getWidth(), this.getHeight());
			eventviewlist.get(x).setPreferredSize(new Dimension(this.getWidth(),this.getHeight()));
			eventviewlist.get(x).setSize(this.getWidth(), this.getHeight());
		}
		
		
		super.paint(g);
	}
	
	@Override
	public String getTitle() {
		return dayview.getTitle();
	}
	@Override
	public void next() {
		dayview.next();
		ChangeTheWorld();
		repaint();
	}
	@Override
	public void previous() {
		dayview.previous();
		ChangeTheWorld();
		repaint();
	}
	@Override
	public void today() {
		dayview.today();
		ChangeTheWorld();
		repaint();
	}
	@Override
	public void contentsChanged(ListDataEvent arg0) {
		ChangeTheWorld();
	}
	@Override
	public void intervalAdded(ListDataEvent arg0) {
		ChangeTheWorld();
	}
	@Override
	public void intervalRemoved(ListDataEvent arg0) {
		ChangeTheWorld();
	}
	
}
