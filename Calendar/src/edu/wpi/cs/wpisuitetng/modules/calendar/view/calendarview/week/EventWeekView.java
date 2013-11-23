package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.week;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.DatePanel;

public class EventWeekView extends JPanel{

	private List<Event> events;
	private Date start;
	
	public EventWeekView(List<Event> e, Dimension size, Date current)
	{
		this.setSize(size);
		this.setPreferredSize(size);
		
		this.events = e;
		this.start = current;
		
		showEvent();
		this.setOpaque(false);
		this.setVisible(true);
	}
	
	public List<List<Event>> fixDays(){
		Date current;
		List<List<Event>> weekevents = new LinkedList<List<Event>>();
		
		for (int y = 0; y < 7; y++){
			current = new Date(start.getYear(), start.getMonth(), start.getDate()+y);
			weekevents.add(new LinkedList<Event>());
			
			for (int x = 0; x < events.size(); x++){
				Date evedate = events.get(x).getStartDate();
				
				System.out.println("event: " + evedate.toString() + " start: " + start.toString());
				if (evedate.getMonth() == current.getMonth() && evedate.getYear() == current.getYear() && evedate.getDate() == current.getDate()){
					weekevents.get(y).add(events.get(x));
				}
			}
		}
		
		return weekevents;
	}
	
	/**
	 * Sorts the List of Events by StartDate
	 */
	public List<Event> sortEvents(List<Event> events){
		Collections.sort(events, new Comparator<Event>(){
			public int compare(Event o1, Event o2) {
				return o1.getStartDate().compareTo(o2.getStartDate());
			}
			
		});
		return events;
	}
	
	/**
	 * Finds the Maximum Width Across Of a List of Events
	 * @return The Max Width of Group of Events
	 */
	public int findMaxWidth(List<Event> events){
		int maxwidth = 1;
		int curwidth = 0;
		for (int x = 0; x < events.size();){
			curwidth = grabOverlapChain(events, x).size();
			if (maxwidth < curwidth){
				maxwidth = curwidth;
			}
			x = x+curwidth;
		}
		return maxwidth;
	}
	
	/**
	 * Finds whether two events overlap
	 * @param e1 The Event that occurs right before e2 in Timeline
	 * @param e2 The Event that occurs right after e1 in Timeline
	 * @return True if events overlap or False if they don't
	 */
	public boolean overlaps(Event e1, List<Event> elist){
		for (Event e2: elist){
			if (e2.getStartDate().getHours() <= e1.getStartDate().getHours() && 
				e2.getEndDate().getHours() >= e1.getStartDate().getHours()){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Calculates the length between end and start of event
	 * @param e Event to calculate length of
	 * @return length of event e
	 */
	public int getLength(Event e){
		return e.getEndDate().getHours() - e.getStartDate().getHours();
	}
	
	/**
	 * Calculates the List of Overlapping Events From Starting Index
	 * @param start Index to Start Counting overlap
	 * @return List of Overlapping Events
	 */
	public List<Event> grabOverlapChain(List<Event> events, int start){
		List<Event> overlapchain = new LinkedList<Event>();
		if (start >= events.size()){
			return overlapchain;
		}
		else{
			overlapchain.add(events.get(start));
		}
		
		for (int x = start+1; x < events.size(); x++){
			if (overlaps(events.get(x), overlapchain)){
				overlapchain.add(events.get(x));
			}
			else{
				return overlapchain;
			}
		}
		return overlapchain;
	}
	
	/**
	 * Creates a Mig-Layout panel to be displayed on the day view
	 * @param e Event to be displayed
	 * @return JPanel generated
	 */
	public void showEvent()
	{
			
		List<List<Event>> weekevents = fixDays();
		StringBuilder layouts = new StringBuilder();
		
		layouts.append("[8%]");
		for (int currentday = 0; currentday < 7; currentday++){
			List<Event> currentlist = weekevents.get(currentday);
			currentlist = sortEvents(currentlist);
			
			int maxwidth = findMaxWidth(currentlist);
			int sectionsize = 13 / maxwidth;
			
			StringBuilder calclayout = new StringBuilder();
			for (int x = 0; x < maxwidth; x++){
				String size = "[" + sectionsize + "%]";
				if (x == maxwidth - 1){
					size = "[" + new Integer(13 - (sectionsize * x)).toString() + "%]";
				}
				calclayout.append(size);
			}
			layouts.append(calclayout);
		}
		
		this.setLayout(new MigLayout("fill", 
				layouts.toString(), 
				"[4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%]"));
		
		System.out.println(layouts.toString());
		
		int maxmove = 1;
		
		for (int currentday = 0; currentday < 7; currentday++){
			List<Event> currentlist = weekevents.get(currentday);
			currentlist = sortEvents(currentlist);
			
			int y = 0;
			while (y < currentlist.size()){
				List<Event> chain = grabOverlapChain(currentlist, y);
				if (chain.size() == 0){
					y = currentlist.size();
				}

				for (int z = 0; z < chain.size(); z++){
					Event e = chain.get(z);
					JPanel panel = new JPanel();
					StringBuilder evebuilder = new StringBuilder();
					evebuilder.append("cell ");
					evebuilder.append(new Integer(maxmove + z).toString());
					evebuilder.append(" ");
					evebuilder.append((new Integer(e.getStartDate().getHours()+1)).toString());
					evebuilder.append(" ");
					evebuilder.append("0");
					evebuilder.append(" ");
					evebuilder.append(new Integer(getLength(e)).toString());
					evebuilder.append(",grow, push, wmin 0");

					panel.add(new JLabel(e.getName()), "wmin 0, aligny center, alignx center");
					if (e.getCategory() != null){
						panel.setBackground(e.getCategory().getColor());
					}
					else{
						panel.setBackground(Color.CYAN);
					}
					panel.setFocusable(true);
					this.add(panel, evebuilder.toString());
				}
				y = y + chain.size();
			}
			
			maxmove = maxmove + findMaxWidth(currentlist);
		}
	}
}
