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
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;

import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.EventListModel;


public class DayCalendarView extends JLayeredPane implements ICalendarView, ListDataListener {
	DayView dayview;
	ArrayList<EventView> eventviewlist;
	
	public DayCalendarView()
	{
		dayview = new DayView();
		eventviewlist = new ArrayList<EventView>();
		
		this.setPreferredSize(new Dimension(500,500));
		//this.setLayout(new GridLayout());
		
		//Dimension d = dayview.getPreferredSize();
		dayview.setBounds(0,0,1000,1000);
		
		this.add(dayview,new Integer(2), -1);
		//this.setLayer(dayview, 10);
		//this.moveToBack(dayview);
		
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
				eventviewlist.add(new EventView(eve));
				//paneltracker.get(key).addEventPanel(eve);
			}
		}
		
		System.out.println(this.getLayer(dayview));
		for(EventView e: eventviewlist)
		{
			e.setBounds(0,10,1000,1000);
			this.add(e, new Integer(3), -1);
			//this.setLayer(e, 0);
			this.moveToFront(e);
		}
	}
	
	public void ClearEvents()
	{
		for(EventView e: eventviewlist)
		{
			this.remove(e);
		}
		
		eventviewlist = new ArrayList<EventView>();
	}
	
	@Override
	public String getTitle() {
		return dayview.getTitle();
	}
	@Override
	public void next() {
		dayview.next();
		ChangeTheWorld();
	}
	@Override
	public void previous() {
		dayview.previous();
		ChangeTheWorld();
	}
	@Override
	public void today() {
		dayview.today();
		ChangeTheWorld();
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
