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
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.ISchedulable;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.SchedMouseListener;

public class EventView extends JPanel {

	private List<ISchedulable> events;
	
	public EventView(List<ISchedulable> events, Dimension size)
	{
		this.setSize(size);
		this.setPreferredSize(size);
		
		this.events = events;
		showEvent();
		this.setOpaque(false);
		this.setVisible(true);
	}
	
	/**
	 * Sorts the List of Events by StartDate
	 */
	public void sortEvents(){
		Collections.sort(events, new Comparator<ISchedulable>(){
			public int compare(ISchedulable o1, ISchedulable o2) {
				return o1.getStartDate().compareTo(o2.getStartDate());
			}
			
		});
	}
	
	/**
	 * Finds the Maximum Width Across Of a List of Events
	 * @return The Max Width of Group of Events
	 */
	public int findMaxWidth(){
		int maxwidth = 1;
		int curwidth = 0;
		for (int x = 0; x < events.size();){
			curwidth = grabOverlapChain(x).size();
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
	public boolean overlaps(ISchedulable e1, List<ISchedulable> elist){
		for (ISchedulable e2: elist){
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
	public int getLength(ISchedulable e){
		return ((e.getEndDate().getHours()*4 + (int) Math.round(e.getEndDate().getMinutes()/15.0)+4) - (e.getStartDate().getHours()*4 + (int) Math.round(e.getStartDate().getMinutes()/15.0)+4));
	}
	
	/**
	 * Calculates the List of Overlapping Events From Starting Index
	 * @param start Index to Start Counting overlap
	 * @return List of Overlapping Events
	 */
	public List<ISchedulable> grabOverlapChain(int start){
		List<ISchedulable> overlapchain = new LinkedList<ISchedulable>();
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
	
	private boolean overlapEvent(ISchedulable e1,ISchedulable e2){
		if(isBetween(e1.getStartDate(),e2.getStartDate(),e2.getEndDate()) ||
				isBetween(e1.getEndDate(),e2.getStartDate(),e2.getEndDate())||
				isBetween(e2.getStartDate(),e1.getStartDate(),e1.getEndDate()) ||
				isBetween(e2.getEndDate(),e1.getStartDate(),e1.getEndDate()) ||
				(e1.getStartDate() == e2.getStartDate() && e1.getEndDate() == e2.getEndDate())){
			return true;
		}
		return false;
	}
	
	private ArrayList<ISchedulable> overlapList(ISchedulable e1, ArrayList<ISchedulable> eventList){
		ArrayList<ISchedulable> overlaps = new ArrayList<ISchedulable>();
		for(ISchedulable e2:eventList){
			if(overlapEvent(e1,e2)){
				overlaps.add(e2);
			}
		}
		return overlaps;
	}
	
	private ArrayList<ArrayList<ISchedulable>> generateMap(){
		sortEvents();
		ArrayList<ArrayList<ISchedulable>> map = new ArrayList<ArrayList<ISchedulable>>();
		
		for(int i=0;i<events.size();i++){
			boolean added=false;
			for(int j=0;j<map.size();j++){
				ArrayList<ISchedulable> testList = map.get(j);
				if(!overlapEvent(events.get(i),testList.get(testList.size()-1))){
					map.get(j).add(events.get(i));
					added=true;
				}
			}
			if(!added){
				ArrayList<ISchedulable> newList = new ArrayList<ISchedulable>();
				newList.add(events.get(i));
				map.add(newList);
			}
		}
		
		return map;
	}
	
	private void displayMap(ArrayList<ArrayList<ISchedulable>> map){
		String toomanyones = "";
		
		StringBuilder calclayout = new StringBuilder();
		calclayout.append("[12%]");
		for (int x = 0; x < 76; x++){
			String size = "[1%]1";
			calclayout.append(size);
		}
		calclayout.append("[2%]");
		
		for(int k = 0; k < 100; k++)
			toomanyones += "[1%]1";
		
		this.setLayout(new MigLayout("fill",
				calclayout.toString(),
				toomanyones));
		
		for(int i=0;i<map.size();i++){
			for(ISchedulable test:map.get(i)){
				ArrayList<ISchedulable> overlapEvents = new ArrayList<ISchedulable>();
				int divisions=1;
				for(int j=0;j<map.size();j++){
					if(j!=i){
						ArrayList<ISchedulable> overlaps = overlapList(test,map.get(j));
						if(overlaps.size()>0){
							divisions++;
							overlapEvents.addAll(overlaps);
						}
					}
				}
				for(ISchedulable test2:overlapEvents){
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
				JPanel panel = new EventPanel(test);
				StringBuilder evebuilder = new StringBuilder();
				evebuilder.append("cell ");
				evebuilder.append(new Integer(1 + i*(76/divisions)).toString());
				evebuilder.append(" ");
				evebuilder.append((new Integer((int)(4*(test.getStartDate().getHours()) + Math.round((test.getStartDate().getMinutes()/15.0) + 4))).toString()));
				evebuilder.append(" ");
				evebuilder.append(new Integer(76/divisions));
				evebuilder.append(" ");
				evebuilder.append(new Integer(getLength(test)).toString());
				evebuilder.append(",grow, push, wmin 0");
//				JLabel name = new JLabel(test.getName());
//				panel.add(name, "wmin 0, aligny center, alignx center");
				
				StringBuilder infobuilder = new StringBuilder();
				infobuilder.append("<html><p style='width:175px'><b>Name: </b>");
				infobuilder.append(test.getName());
				if(test instanceof Event)
				{
					infobuilder.append("<br><b>Start: </b>");
					infobuilder.append(DateFormat.getInstance().format(test.getStartDate()));
					infobuilder.append("<br><b>End: </b>");
					infobuilder.append(DateFormat.getInstance().format(test.getEndDate()));
				}
				else if(test instanceof Commitment)
				{
					infobuilder.append("<br><b>Due: </b>");
					infobuilder.append(DateFormat.getInstance().format(test.getStartDate()));
				}
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
				if(test instanceof Event)
					panel.addMouseListener(new SchedMouseListener((Event) test, panel));
				
				if (test.getCategory() != null){
					panel.setBackground(test.getCategory().getColor());
					Color catColor=test.getCategory().getColor();
					float[] hsb=new float[3];
					hsb=Color.RGBtoHSB(catColor.getRed(), catColor.getGreen(), catColor.getBlue(), hsb);
					if(hsb[2]<0.5){
						//name.setForeground(Color.WHITE);
					}
					else{
						//name.setForeground(Color.BLACK);
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
		sortEvents();
		
		ArrayList<ArrayList<ISchedulable>> eventMap = new ArrayList<ArrayList<ISchedulable>>();
		eventMap=generateMap();
		displayMap(eventMap);
	}
	
}
