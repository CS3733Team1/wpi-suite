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
import java.awt.Dimension;
import java.text.DateFormat;
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
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.EventMouseListener;

/**
 * This class shows single-day events in week view. It is a component of the WeekCalendarLayerPane, along
 * with MultidayEventWeekView.
 */

public class EventWeekView extends JPanel{

	private List<Event> events;
	private Date start;
	
	public EventWeekView(List<Event> e, Dimension size, Date current)
	{
		this.setSize(size);
//		this.setPreferredSize(size);
		
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
				
				if (evedate.getMonth() == current.getMonth() && evedate.getYear() == current.getYear() && evedate.getDate() == current.getDate()){
					weekevents.get(y).add(new Event(events.get(x)));
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
		return ((e.getEndDate().getHours()*4 + (int) Math.round(e.getEndDate().getMinutes()/15.0)) - (e.getStartDate().getHours()*4 + (int) Math.round(e.getStartDate().getMinutes()/15.0)));
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
	
	private boolean isBetween(Date test, Date start, Date end){
		if(test.getHours() > start.getHours() &&
				test.getHours() < end.getHours()){
			return true;
		}
		else if(test.getHours() > start.getHours() &&
				test.getHours() == end.getHours()){
			if(test.getMinutes() < end.getMinutes()){
				return true;
			}
		}
		else if(test.getHours() == start.getHours() &&
				test.getHours() < end.getHours()){
			if(test.getMinutes() > start.getMinutes()){
				return true;
			}
		}
		else if(test.getHours() == start.getHours() &&
				test.getHours() == end.getHours()){
			if(test.getMinutes() > start.getMinutes() &&
					test.getMinutes() < end.getMinutes()){
				return true;
			}
		}
		return false;
	}
	
	private boolean overlapEvent(Event e1,Event e2){
		if(isBetween(e1.getStartDate(),e2.getStartDate(),e2.getEndDate()) ||
				isBetween(e1.getEndDate(),e2.getStartDate(),e2.getEndDate())||
				isBetween(e2.getStartDate(),e1.getStartDate(),e1.getEndDate()) ||
				isBetween(e2.getEndDate(),e1.getStartDate(),e1.getEndDate()) ||
				(e1.getStartDate() == e2.getStartDate() && e1.getEndDate() == e2.getEndDate())){
			return true;
		}
		return false;
	}
	
	private ArrayList<Event> overlapList(Event e1,ArrayList<Event> eventList){
		ArrayList<Event> overlaps = new ArrayList<Event>();
		for(Event e2:eventList){
			if(overlapEvent(e1,e2)){
				overlaps.add(e2);
			}
		}
		return overlaps;
	}
	
	private ArrayList<ArrayList<Event>> generateMap(List<Event> dayEvents){
		ArrayList<ArrayList<Event>> map = new ArrayList<ArrayList<Event>>();
		
		for(int i=0;i<dayEvents.size();i++){
			boolean added=false;
			for(int j=0;j<map.size();j++){
				ArrayList<Event> testList = map.get(j);
				if(!overlapEvent(dayEvents.get(i),testList.get(testList.size()-1))){
					map.get(j).add(dayEvents.get(i));
					added=true;
				}
			}
			if(!added){
				ArrayList<Event> newList = new ArrayList<Event>();
				newList.add(dayEvents.get(i));
				map.add(newList);
			}
		}
		
		return map;
	}
	
	private void displayMap(ArrayList<ArrayList<Event>> map,int currentday){
		for(int i=0;i<map.size();i++){
			for(Event test:map.get(i)){
				ArrayList<Event> overlapEvents = new ArrayList<Event>();
				int divisions=1;
				for(int j=0;j<map.size();j++){
					if(j!=i){
						ArrayList<Event> overlaps = overlapList(test,map.get(j));
						if(overlaps.size()>0){
							divisions++;
							overlapEvents.addAll(overlaps);
						}
					}
				}
				for(Event test2:overlapEvents){
					int eventDivs=0;
					for(int j=0;j<map.size();j++){
						if(overlapList(test2,map.get(j)).size()>0){
							eventDivs++;
						}
					}
					if(eventDivs>divisions){
						divisions=eventDivs;
					}
				}
				JPanel panel = new JPanel();
				StringBuilder evebuilder = new StringBuilder();
				evebuilder.append("cell ");
				evebuilder.append(new Integer(1 + 13*currentday + i*(13/divisions)).toString());
				evebuilder.append(" ");
				evebuilder.append((new Integer((int)(4*(test.getStartDate().getHours()) + Math.round((test.getStartDate().getMinutes()/15.0) + 4))).toString()));
				evebuilder.append(" ");
				evebuilder.append(new Integer(13/divisions));
				evebuilder.append(" ");
				evebuilder.append(new Integer(getLength(test)).toString());
				evebuilder.append(",grow, push, wmin 0");
				JLabel name = new JLabel(test.getName());
				panel.setLayout(new MigLayout("fill"));
				panel.add(name, "hmin 0, wmin 0");
				
				StringBuilder infobuilder = new StringBuilder();
				infobuilder.append("<html><p style='width:175px'><b>Name: </b>");
				infobuilder.append(test.getName());
				infobuilder.append("<br><b>Start: </b>");
				infobuilder.append(DateFormat.getInstance().format(test.getStartDate()));
				infobuilder.append("<br><b>End: </b>");
				infobuilder.append(DateFormat.getInstance().format(test.getEndDate()));
				if(test.getCategory()!=null){
					infobuilder.append("<br><b>Category: </b>");
					infobuilder.append(test.getCategory().getName());
				}
				if(test.getDescription().length()>0){
					infobuilder.append("<br><b>Description: </b>");
					infobuilder.append(test.getDescription());
				}
				infobuilder.append("</p></html>");
				panel.setToolTipText(infobuilder.toString());
				panel.addMouseListener(new EventMouseListener(test, panel));
				
				if (test.getCategory() != null){
					panel.setBackground(test.getCategory().getColor());
					Color catColor=test.getCategory().getColor();
					float[] hsb=new float[3];
					hsb=Color.RGBtoHSB(catColor.getRed(), catColor.getGreen(), catColor.getBlue(), hsb);
					if(hsb[2]<0.5){
						name.setForeground(Color.WHITE);
					}
					else{
						name.setForeground(Color.BLACK);
					}
				}
				else{
					panel.setBackground(Color.CYAN);
				}
				panel.setFocusable(true);
				this.add(panel, evebuilder.toString());
			}
		}
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
		String toomanyones = "";
		
		layouts.append("[9%]2");
		StringBuilder calclayout = new StringBuilder();
		for(int j=0; j<7;j++){
			for(int i=0; i<12; i++){
				calclayout.append("[1%]1");
			}
			calclayout.append("[1%]1");
		}
		layouts.append(calclayout);
		
		for(int i = 0; i < 100; i++)
			toomanyones += "[1%]1";

		this.setLayout(new MigLayout("fill, debug",
				layouts.toString(),
				toomanyones));

		for (int currentday = 0; currentday < 7; currentday++){
			List<Event> currentlist = weekevents.get(currentday);
			currentlist = sortEvents(currentlist);
			
			ArrayList<ArrayList<Event>> eventMap = new ArrayList<ArrayList<Event>>();
			eventMap=generateMap(currentlist);
			displayMap(eventMap,currentday);
		}
	}
}
