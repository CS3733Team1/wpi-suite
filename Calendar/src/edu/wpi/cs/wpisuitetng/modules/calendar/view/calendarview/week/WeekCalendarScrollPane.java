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
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.FilteredCommitmentsListModel;

/**
 * Handles resizing and scrolling in week view.
 */

public class WeekCalendarScrollPane extends JScrollPane implements ListDataListener {
	private WeekCalendarLayerPane weeklayer;
	private List<JPanel> weekpanel;
	private JPanel weektitle;
	
	public WeekCalendarScrollPane(WeekCalendarLayerPane day){
		super(day);

		weeklayer = day;
		
		weektitle = new JPanel(new MigLayout());
		weektitle.setBackground(Color.WHITE);
		weekpanel = new LinkedList<JPanel>();
		
		
		for(int days = 1; days < 8; days++){
			JPanel weekName = new JPanel(new MigLayout());
			
			StringBuilder weekbuilder = new StringBuilder();
			weekbuilder.append("cell ");
			weekbuilder.append((new Integer(days)).toString());
			weekbuilder.append(" ");
			weekbuilder.append("0");
			weekbuilder.append(",wmin 130, alignx left, growy");
			
		
			weekpanel.add(weekName);
			weektitle.add(weekName, weekbuilder.toString());
		}
		
	
		JPanel cornertile = new JPanel();
		cornertile.setBackground(Color.WHITE);

		this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		FilteredCommitmentsListModel.getFilteredCommitmentsListModel().addListDataListener(this);
		DisplayCommitments();
	}
	
	public void rebuildDays(){
		this.remove(weektitle);
		
		weektitle = new JPanel(new MigLayout());
		weektitle.setBackground(Color.WHITE);
		weekpanel = new LinkedList<JPanel>();
		

		for(int days = 1; days < 8; days++){
			JPanel weekName = new JPanel(new MigLayout());
			
			StringBuilder weekbuilder = new StringBuilder();
			weekbuilder.append("cell ");
			weekbuilder.append((new Integer(days)).toString());
			weekbuilder.append(" ");
			weekbuilder.append("0");
			weekbuilder.append(",wmin 130, alignx left, growy");
			
			weekpanel.add(weekName);
			weektitle.add(weekName, weekbuilder.toString());
		}
		
		JPanel cornertile = new JPanel();
		cornertile.setBackground(Color.WHITE);
	}
	
	public void DisplayCommitments(){
		List<List<Commitment>> foundyou = bananaSplit(weeklayer.getWeek().CommitmentsOnCalendar());
		
		for (int x = 0; x < 7; x++){
			if (foundyou.get(x).size() > 0){
				JPanel day = weekpanel.get(x);
				day.setBackground(Color.RED);
				StringBuilder bob = new StringBuilder();
				bob.append("<html>");
				for (Commitment commit: foundyou.get(x)){
					bob.append("<p>");
					bob.append("<b>Name:</b> ");
					bob.append(commit.getName());
					bob.append("<br><b>Description:</b> ");
					bob.append(commit.getDescription());
					bob.append("</p>");
				}
				bob.append("</html>");
				day.setToolTipText(bob.toString());
			}
		}
	}
	
	/**
	 * Splits The List Into Hourly Blocks
	 * @param thoseitems the list being split
	 * @return split list of hours
	 */
	public List<List<Commitment>> bananaSplit(List<Commitment> thoseitems){
		List<List<Commitment>> dalist = new LinkedList<List<Commitment>>();
		Date startdate = weeklayer.getWeek().getStart();
		
		for (int x = 0; x < 7; x++){
			List<Commitment> daylist = new LinkedList<Commitment>();
			for (Commitment commit: thoseitems){
				Date commitdate = new Date(commit.getDueDate().getYear(), commit.getDueDate().getMonth(), commit.getDueDate().getDate());
				if (commitdate.equals(startdate)){
					daylist.add(commit);
				}
			}
			startdate = new Date(startdate.getYear(), startdate.getMonth(), startdate.getDate()+1);
			dalist.add(daylist);
		}
		return dalist;
	}

	@Override
	public void intervalAdded(ListDataEvent e) {
		rebuildDays();
		DisplayCommitments();
	}

	@Override
	public void intervalRemoved(ListDataEvent e) {
		rebuildDays();
		DisplayCommitments();
	}

	@Override
	public void contentsChanged(ListDataEvent e) {
		rebuildDays();
		DisplayCommitments();
	}
	
	public void updateDayHeader(){
		rebuildDays();
		DisplayCommitments();
	}
	
	
}
