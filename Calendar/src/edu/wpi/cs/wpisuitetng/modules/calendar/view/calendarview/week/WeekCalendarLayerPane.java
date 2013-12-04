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
import edu.wpi.cs.wpisuitetng.modules.calendar.model.EventListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day.DayView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day.EventView;

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
		
		EventListModel.getEventListModel().addListDataListener(this);
	}
	
	public void ChangeTheWorld(){
		Date key;
		Date current = (Date) weekview.getStart().clone();
		Date iter = new Date();
		int startind, endind;
		ArrayList<Integer> weekdays = new ArrayList<Integer>();
		List<Event> multilist = new LinkedList<Event>();
		
		ClearEvents();
		ListIterator<Event> event = EventListModel.getEventListModel().getList().listIterator();
		
		while(event.hasNext()){
			Event eve = event.next();
			Date evedate = eve.getStartDate();
			key = new Date(evedate.getYear(),evedate.getMonth(),evedate.getDate(),evedate.getHours(),0);
			if (weekview.getMap().containsKey(key)){
				if(eve.getStartDate().getDay() == eve.getEndDate().getDay()
				&& eve.getStartDate().getMonth() == eve.getEndDate().getMonth()
				&& eve.getStartDate().getYear() == eve.getEndDate().getYear())
					eventlist.add(eve);
				else
				{
					multilist.add(eve);
				}
			}
		}
		multilist.add(new Event("RAISE YOUR DONGERS", new Date(113, 12, 2), new Date(113, 12, 5)));
		
		eventview = new EventWeekView(eventlist, this.getSize(), weekview.getStart());
		
		for(int i = 0; i < 7; i++)
		{
			iter = (Date) current.clone();
			weekdays.add(i, iter.getDate());
			iter.setDate(iter.getDate() + 1);
		}
		
		for(Event e: multilist)
		{
			System.out.println("MULTIDAY EVENT" + e.toString());
			if(current.before(e.getStartDate()))
				iter = (Date) e.getStartDate().clone();
			else
				iter = (Date) current.clone();
			
			while(!iter.equals(e.getEndDate()))
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
