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
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.EventMouseListener;

public class EventView extends JPanel {

	private List<Event> events;
	
	public EventView(List<Event> events, Dimension size)
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
		Collections.sort(events, new Comparator<Event>(){
			public int compare(Event o1, Event o2) {
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
	public List<Event> grabOverlapChain(int start){
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
		sortEvents();
		
		int maxwidth = findMaxWidth();
		int sectionsize = 76 / maxwidth;
		String toomanyones = "";
		
		StringBuilder calclayout = new StringBuilder();
		calclayout.append("[22%]");
		for (int x = 0; x < maxwidth; x++){
			String size = "[" + sectionsize + "%]";
			calclayout.append(size);
		}
		calclayout.append("[2%]");
		
		for(int i = 0; i < 100; i++)
			toomanyones += "[1%]";
		
		this.setLayout(new MigLayout("fill",
				calclayout.toString(),
				toomanyones));
		
		int y = 0;
		while (y < events.size()){
			List<Event> chain = grabOverlapChain(y);
			if (chain.size() == 0){
				y = events.size();
			}
			
			for (int z = 0; z < chain.size(); z++){
				Event e = new Event(chain.get(z));
				JPanel panel = new JPanel();
				StringBuilder evebuilder = new StringBuilder();
				evebuilder.append("cell ");
				evebuilder.append(new Integer(1 + z).toString());
				evebuilder.append(" ");
				evebuilder.append((new Integer((int)(4*(e.getStartDate().getHours()) + Math.round((e.getStartDate().getMinutes()/15.0) + 4))).toString()));
				evebuilder.append(" ");
				evebuilder.append("0");
				evebuilder.append(" ");
				evebuilder.append(new Integer(getLength(e)).toString());
				evebuilder.append(",grow, push, wmin 0");
				JLabel name = new JLabel(e.getName());
				panel.add(name, "wmin 0, aligny center, alignx center");
				
				StringBuilder infobuilder = new StringBuilder();
				infobuilder.append("<html><p style='width:175px'><b>Name: </b>");
				infobuilder.append(e.getName());
				infobuilder.append("<br><b>Start: </b>");
				infobuilder.append(DateFormat.getInstance().format(e.getStartDate()));
				infobuilder.append("<br><b>End: </b>");
				infobuilder.append(DateFormat.getInstance().format(e.getEndDate()));
				if(e.getCategory()!=null){
					infobuilder.append("<br><b>Category: </b>");
					infobuilder.append(e.getCategory().getName());
				}
				if(e.getDescription().length()>0){
					infobuilder.append("<br><b>Description: </b>");
					infobuilder.append(e.getDescription());
				}
				infobuilder.append("</p></html>");
				panel.setToolTipText(infobuilder.toString());
				panel.addMouseListener(new EventMouseListener(e, panel));
				
				if (e.getCategory() != null){
					panel.setBackground(e.getCategory().getColor());
					Color catColor=e.getCategory().getColor();
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
			y = y + chain.size();
		}
	}
	
}
