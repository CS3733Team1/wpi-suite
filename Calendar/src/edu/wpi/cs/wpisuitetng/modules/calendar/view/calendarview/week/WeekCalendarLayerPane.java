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
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import javax.swing.JLayeredPane;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.FilteredEventsListModel;

/**
 * This class is used to display events in week view. It is the third level of the week view hierarchy:
 * it is on top of the WeekCalendarPanel, and it holds an EventWeekView and a MultidayEventWeekView.
 */

public class WeekCalendarLayerPane extends JLayeredPane implements ListDataListener{
	private WeekView weekview;
	private EventWeekView eventview;
	private MultidayEventWeekView multiview;
	
	private List<Event> eventlist;
	private List<List<Event>> multilistlist;
	private Integer layer;
	
	public WeekCalendarLayerPane()
	{
		layer = 0;
		weekview = new WeekView();
		eventlist = new LinkedList<Event>();
		
		multilistlist = new LinkedList<List<Event>>();
		for(int i = 0; i < 7; i++)
		{
			multilistlist.add(new LinkedList<Event>());
		}
		
		int height = weekview.getPreferredSize().getSize().height;
		int width = 950;
		
		this.setSize(width, height);
		this.setPreferredSize(new Dimension(width, height));
		
		weekview.setSize(width,height);
		this.add(weekview, layer, -1);
		layer++;
		
		ChangeTheWorld();
		this.setVisible(true);
		
		FilteredEventsListModel.getFilteredEventsListModel().addListDataListener(this);
	}
	
	/**
	 * Clears and reassembles the event displays.
	 */
	
	public void ChangeTheWorld(){
		Date key;
		Date current = (Date) weekview.getStart().clone();
		Date iter = new Date();
		int startind, endind;
		ArrayList<Integer> weekdays = new ArrayList<Integer>();
		List<Event> multilist = new LinkedList<Event>();
		
		ClearEvents();
		ListIterator<Event> event = FilteredEventsListModel.getFilteredEventsListModel().getList().listIterator();
		
		while(event.hasNext()){
			Event eve = new Event(event.next());
			Date evedate = eve.getStartDate();
			key = new Date(evedate.getYear(),evedate.getMonth(),evedate.getDate(),evedate.getHours(),0);
			if (weekview.getMap().containsKey(key)){
				if(eve.getStartDate().getDate() == eve.getEndDate().getDate()
				&& eve.getStartDate().getMonth() == eve.getEndDate().getMonth()
				&& eve.getStartDate().getYear() == eve.getEndDate().getYear())
					eventlist.add(eve);
				else
				{
					multilist.add(eve);
				}
			}
			else if(current.after(eve.getStartDate()) && current.before(eve.getEndDate()))
			{
				multilist.add(eve);
			}
		}
		
		eventview = new EventWeekView(eventlist, this.getSize(), weekview.getStart());
		
		for(int i = 0; i < 7; i++)
		{
			iter = (Date) current.clone();
			weekdays.add(i, iter.getDate());
			iter.setDate(iter.getDate() + 1);
		}
		
		for(Event e: multilist)
		{
			if(current.before(e.getStartDate()))
				iter = (Date) e.getStartDate().clone();
			else
				iter = (Date) current.clone();
			
			while(!(iter.equals(e.getEndDate()) || iter.getDate() == weekdays.get(6)))
			{
				if(weekdays.contains(iter.getDate()))
				{
					multilistlist.get(weekdays.indexOf(iter.getDate())).add(e);
				}
				iter.setDate(iter.getDate() + 1); //setDate knows where month boundaries are
			}
		}

		multiview = new MultidayEventWeekView(multilistlist, this.getSize());
		
		this.add(multiview, layer, -1);
		layer++;
		this.add(eventview, layer,-1);
		layer++;
	}
	
	public WeekView getWeek(){
		return weekview;
	}
	
	public void reSize(int width){
		this.setSize(width, this.getHeight());
		this.setPreferredSize(new Dimension(width, this.getHeight()));
		
		weekview.setSize(this.getSize());
		eventview.setSize(this.getSize());
		multiview.setSize(this.getSize());
		
		repaint();
	}
	
	public void repaint(){
		weekview.repaint();
		eventview.repaint();
		multiview.repaint();
		super.repaint();
	}
	
	public void ClearEvents()
	{
		if (eventview != null){
			this.remove(eventview);
			layer--;
		}

		if (multiview != null){
			this.remove(multiview);
			layer--;
		}
		
		eventlist = new LinkedList<Event>();
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
