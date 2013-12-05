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
import java.awt.Font;
import java.awt.Graphics;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.FilteredCommitmentsListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.ICalendarView;

public class WeekCalendarPanel extends JPanel implements ICalendarView, ListDataListener {

	private WeekCalendarScrollPane weekscroll;
	private WeekCalendarLayerPane weeklayer;
	private List<JPanel> weekpanel;
	private JPanel weektitle, filler;
	public static final String[] weekNames = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
	
	
	public WeekCalendarPanel(){
		this.setBorder(BorderFactory.createTitledBorder(null,
	            "Week View", TitledBorder.LEFT, TitledBorder.TOP,
	            new Font("null", Font.BOLD, 12), Color.BLUE));
		
		
		this.setLayout(new MigLayout("fill"));
		
		weeklayer = new WeekCalendarLayerPane();
		weekpanel = new LinkedList<JPanel>();
		weektitle = new JPanel(new MigLayout("fill, insets 0", "[9%][13%][13%][13%][13%][13%][13%][13%]18px"));
		
		JPanel time = new JPanel(new MigLayout("fill"));
		time.add(new JLabel("Time"), "grow, aligny center");
		time.setBackground(new Color(138,173,209));
		
		weektitle.add(time, "aligny center, w 5000, grow");
		
		for(int days = 1; days < 8; days++){
			JPanel weekName = new JPanel(new MigLayout("fill"));
			
			weekName.add(new JLabel(weekNames[days-1]),"grow, aligny center");
			weekName.setBackground(new Color(138,173,209));
			weekpanel.add(weekName);
			weektitle.add(weekName, "aligny center, w 5000, grow");
		}
		
		
		
		
		
		filler = new JPanel(new MigLayout("fill"));
		filler.add(weektitle, "grow, wrap");
		this.add(filler, "grow, wrap");
		
		weekscroll = new WeekCalendarScrollPane(weeklayer);
		
		this.add(weekscroll, "grow, push");
		
		
		FilteredCommitmentsListModel.getFilteredCommitmentsListModel().addListDataListener(this);
		DisplayCommitments();
		repaint();
	}
	
	public void rebuildDays(){
		filler.remove(weektitle);
		
		weeklayer = new WeekCalendarLayerPane();
		weekpanel = new LinkedList<JPanel>();
		weektitle = new JPanel(new MigLayout("fill, insets 0", "[9%][13%][13%][13%][13%][13%][13%][13%]18px"));
		
		JPanel time = new JPanel(new MigLayout("fill"));
		time.add(new JLabel("Time"), "grow, aligny center");
		time.setBackground(new Color(138,173,209));
		
		weektitle.add(time, "aligny center, w 5000, grow");
		
		for(int days = 1; days < 8; days++){
			JPanel weekName = new JPanel(new MigLayout("fill"));
			
			weekName.add(new JLabel(weekNames[days-1]),"grow, aligny center");
			weekName.setBackground(new Color(138,173,209));
			weekpanel.add(weekName);
			weektitle.add(weekName, "aligny center, w 5000, grow");
		}
		
		filler.add(weektitle, "grow, wrap");
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
		
		repaint();
		this.updateUI();
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
	
	public void paint(Graphics g){
		if (weeklayer != null){
			weeklayer.reSize(this.getWidth() - (weekscroll.getVerticalScrollBar().getWidth()*3));
		}
		if (weekscroll != null){
			weekscroll.repaint();
		}
		super.paint(g);
	}
	 
	public void repaint(){
		if (weeklayer != null){
			weeklayer.reSize(this.getWidth() - (weekscroll.getVerticalScrollBar().getWidth()*3));
		}
		if (weekscroll != null){
			weekscroll.repaint();
		}
		super.repaint();
	}
	
	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return weeklayer.getTitle();
	}

	@Override
	public void next() {
		// TODO Auto-generated method stub
		weeklayer.next();
	}

	@Override
	public void previous() {
		// TODO Auto-generated method stub
		weeklayer.previous();
	}

	@Override
	public void today() {
		// TODO Auto-generated method stub
		weeklayer.today();
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

}
