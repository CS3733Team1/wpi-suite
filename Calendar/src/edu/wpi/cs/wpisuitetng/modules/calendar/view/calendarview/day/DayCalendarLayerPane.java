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

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import javax.swing.JLayeredPane;
import javax.swing.Scrollable;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.EventListModel;

public class DayCalendarLayerPane extends JLayeredPane implements ListDataListener{
	private DayView dayview;
	private EventView eventviewlist;
	private MultidayEventView multiviewlist;
	private Integer layer;
	
	public DayCalendarLayerPane()
	{
		layer = 0;
		dayview = new DayView();
		
		int height = dayview.getPreferredSize().getSize().height;
		int width = 950;
		
		this.setSize(width, height);
		this.setPreferredSize(new Dimension(width, height));
		
		dayview.setSize(width,height);
		this.add(dayview, layer, -1);
		layer++;
		
		ChangeTheWorld();
		this.setVisible(true);
		
		EventListModel.getEventListModel().addListDataListener(this);
	}
	
	
	public void ChangeTheWorld(){
		Date key;
		
		ClearEvents();
		
		List<Event> test = new LinkedList<Event>();
		List<Event> multi = new LinkedList<Event>();
		ListIterator<Event> event = EventListModel.getEventListModel().getList().listIterator();
		
		
		while(event.hasNext()){
			Event eve = event.next();
			Date evedate = eve.getStartDate();
			key = new Date(evedate.getYear(),evedate.getMonth(),evedate.getDate(),evedate.getHours(),0);
			if (dayview.getMap().containsKey(key)){
				if(eve.getStartDate().getDay() == eve.getEndDate().getDay()
				&& eve.getStartDate().getMonth() == eve.getEndDate().getMonth()
				&& eve.getStartDate().getYear() == eve.getEndDate().getYear())
					test.add(eve);
				else
					multi.add(eve);	
			}
		}
		multi.add(new Event("RAISE YOUR DONGERS", new Date(113, 12, 2), new Date(113, 12, 5)));
		
		eventviewlist = new EventView(test, this.getSize());
		multiviewlist = new MultidayEventView(multi, this.getSize());
		this.add(multiviewlist, layer, -1);
		layer++;
		this.add(eventviewlist, layer,-1);
		layer++;
	}
	
	public Date getDayViewDate(){
		return dayview.getDate();
	}
	
	
	public void reSize(int width){
		this.setSize(width, this.getHeight());
		this.setPreferredSize(new Dimension(width, this.getHeight()));
		
		dayview.setSize(this.getSize());
		eventviewlist.setSize(this.getSize());
		multiviewlist.setSize(this.getSize());
		
		repaint();
	}
	
	public void repaint(){
		dayview.repaint();
		eventviewlist.repaint();
		multiviewlist.repaint();
		super.repaint();
	}
	
	public void ClearEvents()
	{
		if (eventviewlist != null){
			this.remove(eventviewlist);
			layer--;
		}
		if (multiviewlist != null){
			this.remove(multiviewlist);
			layer--;
		}
	}
	
	public String getTitle() {
		return dayview.getTitle();
	}
	
	public void next() {
		dayview.next();
		ChangeTheWorld();
		repaint();
	}
	
	public void previous() {
		dayview.previous();
		ChangeTheWorld();
		repaint();
	}
	
	public void today() {
		dayview.today();
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
