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

public class WeekCalendarScrollPane extends JScrollPane implements ListDataListener {
	private WeekCalendarLayerPane weeklayer;
	private List<JPanel> weekpanel;
	private JPanel weektitle;
	public static final String[] weekNames = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
	
	public WeekCalendarScrollPane(WeekCalendarLayerPane day){
		super(day);

		weeklayer = day;
		
		weektitle = new JPanel(new MigLayout("insets 14", "[][][][][][][][]"));
		weekpanel = new LinkedList<JPanel>();
		weektitle.setBackground(Color.WHITE);
		

		JPanel time = new JPanel(new MigLayout("fill","[grow]", "[]"));
		time.add(new JLabel("Time"), "push, align center");
		time.setBackground(new Color(138,173,209));
		
		for(int days = 1; days < 8; days++){
			JPanel weekName = new JPanel(new MigLayout("fill"));
			
			StringBuilder weekbuilder = new StringBuilder();
			weekbuilder.append("cell ");
			weekbuilder.append((new Integer(days)).toString());
			weekbuilder.append(" ");
			weekbuilder.append("0");
			weekbuilder.append(",wmin 130, alignx left, growy");
			
			weekName.add(new JLabel(weekNames[days-1]),"align center");
			
			
			weekName.setBackground(new Color(138,173,209));
			weekpanel.add(weekName);
			weektitle.add(weekName, weekbuilder.toString());
		}
		
		weektitle.add(time, "cell 0 0, wmin 80, alignx left, growy");
		
		
		JPanel cornertile = new JPanel();
		cornertile.setBackground(Color.WHITE);
		
		
		
		
		
		this.setCorner(UPPER_RIGHT_CORNER, cornertile);
		this.setColumnHeaderView(weektitle);
		this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		FilteredCommitmentsListModel.getFilteredCommitmentsListModel().addListDataListener(this);
		DisplayCommitments();
	}
	
	public void rebuildDays(){
		this.remove(weektitle);
		
		weektitle = new JPanel(new MigLayout("insets 14", "[][][][][][][][]"));
		weekpanel = new LinkedList<JPanel>();
		weektitle.setBackground(Color.WHITE);
		

		JPanel time = new JPanel(new MigLayout("fill","[grow]", "[]"));
		time.add(new JLabel("Time"), "push, align center");
		time.setBackground(new Color(138,173,209));
		
		for(int days = 1; days < 8; days++){
			JPanel weekName = new JPanel(new MigLayout("fill"));
			
			StringBuilder weekbuilder = new StringBuilder();
			weekbuilder.append("cell ");
			weekbuilder.append((new Integer(days)).toString());
			weekbuilder.append(" ");
			weekbuilder.append("0");
			weekbuilder.append(",wmin 130, alignx left, growy");
			
			weekName.add(new JLabel(weekNames[days-1]),"align center");
			
			
			weekName.setBackground(new Color(138,173,209));
			weekpanel.add(weekName);
			weektitle.add(weekName, weekbuilder.toString());
		}
		
		weektitle.add(time, "cell 0 0, wmin 80, alignx left, growy");
		
		
		JPanel cornertile = new JPanel();
		cornertile.setBackground(Color.WHITE);
		
		
		
		
		
		this.setCorner(UPPER_RIGHT_CORNER, cornertile);
		this.setColumnHeaderView(weektitle);
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
