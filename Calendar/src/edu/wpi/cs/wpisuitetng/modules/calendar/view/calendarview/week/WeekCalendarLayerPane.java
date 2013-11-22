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

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Date;
import java.util.ListIterator;

import javax.swing.JLayeredPane;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.EventListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day.DayView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day.EventView;

public class WeekCalendarLayerPane extends JLayeredPane implements ListDataListener{
	private WeekView weekview;
	private EventWeekView eventview;
	
	private ArrayList<Event> eventlist;
	private Integer layer;
	
	public WeekCalendarLayerPane()
	{
		layer = 0;
		weekview = new WeekView();
		eventlist = new ArrayList<Event>();
		
		int height = weekview.getPreferredSize().getSize().height;
		int width = 950;
		
		this.setSize(width, height);
		this.setPreferredSize(new Dimension(width, height));
		
		weekview.setSize(width,height);
		this.add(weekview, layer, -1);
		layer++;
		
		ChangeTheWorld();
		this.setVisible(true);
		
		EventListModel.getEventListModel().addListDataListener(this);
	}
	
	public void ChangeTheWorld(){
		Date key;
		
		ClearEvents();
		ListIterator<Event> event = EventListModel.getEventListModel().getList().listIterator();
		
		while(event.hasNext()){
			Event eve = event.next();
			Date evedate = eve.getStartDate();
			key = new Date(evedate.getYear(),evedate.getMonth(),evedate.getDate(),evedate.getHours(),0);
			if (weekview.getMap().containsKey(key)){
				eventlist.add(eve);
			}
		}
		eventview = new EventWeekView(eventlist, this.getSize(), weekview.getStart());
		
		
		this.add(eventview, layer,-1);
		layer++;
	}
	
	public void reSize(int width){
		this.setSize(width, this.getHeight());
		this.setPreferredSize(new Dimension(width, this.getHeight()));
		
		weekview.setSize(this.getSize());
		eventview.setSize(this.getSize());
		
		repaint();
	}
	
	public void repaint(){
		weekview.repaint();
		eventview.repaint();
		
		super.repaint();
	}
	
	public void ClearEvents()
	{
		if (eventview != null){
			this.remove(eventview);
			layer--;
		}

		eventlist = new ArrayList<Event>();
	}
	
	public String getTitle() {
		return weekview.getTitle();
	}
	
	public void next() {
		weekview.next();
		ChangeTheWorld();
		repaint();
	}
	
	public void previous() {
		weekview.previous();
		ChangeTheWorld();
		repaint();
	}
	
	public void today() {
		weekview.today();
		ChangeTheWorld();
		repaint();
	}
	
	public void contentsChanged(ListDataEvent arg0) {
		ChangeTheWorld();
		repaint();
	}
	
	public void intervalAdded(ListDataEvent arg0) {
		ChangeTheWorld();
		repaint();
	}
	
	public void intervalRemoved(ListDataEvent arg0) {
		ChangeTheWorld();
		repaint();
	}

}
