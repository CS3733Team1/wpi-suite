/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Date;
import java.util.ListIterator;

import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.event.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.filter.FilteredEventsListModel;

public class DatePanel extends JPanel implements ListDataListener {
	private Date paneldate;
	private ArrayList<JPanel> eventlist;
	private JPanel layout;
	private GridLayout grid;
	
	public DatePanel() {
		grid = new GridLayout(0,1);
		eventlist = new ArrayList<JPanel>();
		layout = new JPanel();
		layout.setLayout(grid);
		layout.setVisible(true);
		
		this.setVisible(true);
		
		this.add(layout);
	}
	
	public void setDate(Date today){
		paneldate = today;
	}
	
	public Date getDate(){
		return paneldate;
	}
	
	public void addEventPanel(Event eve){
		JPanel eventpan = new JPanel();
		JTextPane eventdata = new JTextPane();
		eventdata.setText(eve.getName());
		eventdata.setEditable(false);
		eventpan.add(eventdata);
		
		eventpan.setOpaque(false);
		
		eventdata.setVisible(true);
		eventpan.setVisible(true);
		
		layout.add(eventpan);
		this.updateUI();
		eventlist.add(eventpan);
		
	}
	
	public void removeEventPanel(){
		for (int x = 0; x < eventlist.size(); x++){
			layout.remove(eventlist.get(x));
		}

	}
	
	public void updatePanel(){
		removeEventPanel();
		ListIterator<Event> event = FilteredEventsListModel.getFilteredEventsListModel().getList().listIterator();
		while(event.hasNext()){
			Event eve = event.next();
			Date evedate = eve.getStartDate();
			if (evedate.getYear() == paneldate.getYear() && evedate.getDate() == paneldate.getDate() && evedate.getMonth() == paneldate.getMonth()){
				addEventPanel(eve);
			}
		}
	}

	@Override
	public void intervalAdded(ListDataEvent e) {
		updatePanel();
	}

	@Override
	public void intervalRemoved(ListDataEvent e) {
		updatePanel();
	}

	@Override
	public void contentsChanged(ListDataEvent e) {
		updatePanel();
	}
}
