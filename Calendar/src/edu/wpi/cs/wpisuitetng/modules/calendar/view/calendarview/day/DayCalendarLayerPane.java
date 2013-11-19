package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Date;
import java.util.ListIterator;

import javax.swing.JLayeredPane;
import javax.swing.Scrollable;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.EventListModel;

public class DayCalendarLayerPane extends JLayeredPane implements ListDataListener{
	private DayView dayview;
	private ArrayList<EventView> eventviewlist;
	private Integer layer;
	
	public DayCalendarLayerPane()
	{
		layer = 0;
		dayview = new DayView();
		eventviewlist = new ArrayList<EventView>();
		
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
		ListIterator<Event> event = EventListModel.getEventListModel().getList().listIterator();
		
		while(event.hasNext()){
			Event eve = event.next();
			Date evedate = eve.getStartDate();
			key = new Date(evedate.getYear(),evedate.getMonth(),evedate.getDate(),evedate.getHours(),0);
			if (dayview.getMap().containsKey(key)){
				eventviewlist.add(new EventView(eve, this.getSize()));
			}
		}
		
		for(EventView e: eventviewlist)
		{
			this.add(e, layer,-1);
			layer++;
		}
	}
	
	public void reSize(int width){
		this.setSize(width, this.getHeight());
		this.setPreferredSize(new Dimension(width, this.getHeight()));
		
		dayview.setSize(this.getSize());
		for (EventView e: eventviewlist){
			e.setSize(this.getSize());
		}
		
		repaint();
	}
	
	public void repaint(){
		dayview.repaint();
		for(EventView e: eventviewlist)
		{
			e.repaint();
		}
		
		super.repaint();
	}
	
	public void ClearEvents()
	{
		for(EventView e: eventviewlist)
		{
			this.remove(e);
			layer--;
		}
		
		eventviewlist = new ArrayList<EventView>();
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
