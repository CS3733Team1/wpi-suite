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
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import javax.swing.JLayeredPane;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.FilteredCommitmentsListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.FilteredEventsListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.ISchedulable;

public class DayCalendarLayerPane extends JLayeredPane implements ListDataListener{
	private DayView dayview;
	private EventView eventviewlist;
	private MultidayEventView multiviewlist;
	private Integer layer;
	
	public DayCalendarLayerPane()
	{
		layer = 0;
		dayview = new DayView();
		
		int height = dayview.getPreferredSize().getSize().height * 3;
		int width = 950;
		
		this.setSize(width, height);
		this.setPreferredSize(new Dimension(width, height));
		
		dayview.setSize(width,height);
		this.add(dayview, layer, -1);
		layer++;
		
		ChangeTheWorld();
		this.setVisible(true);
		
		FilteredEventsListModel.getFilteredEventsListModel().addListDataListener(this);
		FilteredCommitmentsListModel.getFilteredCommitmentsListModel().addListDataListener(this);
	}
	
	
	public void ChangeTheWorld(){
		Date key;
		
		ClearEvents();
		
		List<ISchedulable> test = new LinkedList<ISchedulable>();
		List<Event> multi = new LinkedList<Event>();
		ListIterator<Event> event = FilteredEventsListModel.getFilteredEventsListModel().getList().listIterator();
		ListIterator<Commitment> comm = FilteredCommitmentsListModel.getFilteredCommitmentsListModel().getList().listIterator();
		
		while(event.hasNext()){
			Event eve = event.next();
			Date evedate = eve.getStartDate();
			key = new Date(evedate.getYear(),evedate.getMonth(),evedate.getDate(),evedate.getHours(),0);
			if (dayview.getMap().containsKey(key)){
				if(eve.getStartDate().getDate() == eve.getEndDate().getDate()
				&& eve.getStartDate().getMonth() == eve.getEndDate().getMonth()
				&& eve.getStartDate().getYear() == eve.getEndDate().getYear())
					test.add(eve);
				else
					multi.add(eve);	
			}
			else if(dayview.getDate().after(eve.getStartDate()) && dayview.getDate().before(eve.getEndDate()))
			{
				multi.add(eve);
			}
		}
		
		while(comm.hasNext()){
			Commitment c = comm.next();
			Date cdate = c.getStartDate();
			key = new Date(cdate.getYear(),cdate.getMonth(),cdate.getDate(),cdate.getHours(),0);
			if (dayview.getMap().containsKey(key)){
				if(c.getStartDate().getDate() == c.getEndDate().getDate()
				&& c.getStartDate().getMonth() == c.getEndDate().getMonth()
				&& c.getStartDate().getYear() == c.getEndDate().getYear())
					test.add(c);	
			}
		}
		//multi.add(new Event("RAISE YOUR DONGERS", new Date(113, 12, 2), new Date(113, 12, 5)));
		
		eventviewlist = new EventView(test, this.getSize());
		multiviewlist = new MultidayEventView(multi, this.getSize(), getDayViewDate());
		
		
		this.add(eventviewlist, layer,-1);
		layer++;
		this.add(multiviewlist, layer, -1);
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
	
	public void viewDate(Date day){
		dayview.viewDate(day);
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
