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

import java.awt.*;
import java.util.Date;
import java.util.GregorianCalendar;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;

import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;


public class DayCalendarView extends JPanel implements ICalendarView {

	public static final String[] weekNames = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
	public static final String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	private ArrayList<JLabel> dayLabelList = new ArrayList<JLabel>();
	private ArrayList<JPanel> panelList = new ArrayList<JPanel>();
	private ArrayList<JLabel> nameLabelList = new ArrayList<JLabel>();
	private ArrayList<JPanel> nameList = new ArrayList<JPanel>();
	private JPanel grid1 = new JPanel();
	private JPanel grid2 = new JPanel();

	private Calendar mycal;
	private int currentMonth;
	private int currentDay;
	private int currentDayOfWeek;
	private int currentYear;

	public DayCalendarView() {
		mycal = new GregorianCalendar();
		currentDay = 1; //mycal.get(Calendar.DAY_OF_MONTH);
		currentMonth = 3; //mycal.get(Calendar.MONTH);
		currentYear = 2013; //mycal.get(Calendar.YEAR) + 1900;
		currentDayOfWeek = 3; // mycal.get(Calendar.DAY_OF_WEEK);
		
		grid1.setLayout(new GridLayout(0, 1));
		grid2.setLayout(new GridLayout(0, 1));
		
		JButton b = new JButton("Fake");
		Dimension buttonSize = b.getPreferredSize();
		grid1.setPreferredSize(new Dimension(600, 2000));
		grid2.setPreferredSize(new Dimension(50, 2000));
		
		JPanel header1 = new JPanel();
		JPanel header2 = new JPanel();
		header1.setBackground(new Color(138, 173, 209));
		header2.setBackground(new Color(138, 173, 209));
		header1.add(new JLabel("Events"));
		header2.add(new JLabel("Time"));
		
		grid1.add(header1);
		grid2.add(header2);
		
		grid2.add(new JLabel("0:00 AM"), BorderLayout.PAGE_START);
		grid2.add(new JLabel());
		//grid2.add(new JLabel("0:30 AM"));
		grid2.add(new JLabel("1:00 AM"), BorderLayout.PAGE_START);
		grid2.add(new JLabel());
		//grid2.add(new JLabel("1:30 AM"));
		grid2.add(new JLabel("2:00 AM"));
		grid2.add(new JLabel());
		//grid2.add(new JLabel("2:30 AM"));
		grid2.add(new JLabel("3:00 AM"));
		grid2.add(new JLabel());
		//grid2.add(new JLabel("3:30 AM"));
		grid2.add(new JLabel("4:00 AM"));
		grid2.add(new JLabel());
		//grid2.add(new JLabel("4:30 AM"));
		grid2.add(new JLabel("5:00 AM"));
		grid2.add(new JLabel());
		//grid2.add(new JLabel("5:30 AM"));
		grid2.add(new JLabel("6:00 AM"));
		grid2.add(new JLabel());
		//grid2.add(new JLabel("6:30 AM"));
		grid2.add(new JLabel("7:00 AM"));
		grid2.add(new JLabel());
		//grid2.add(new JLabel("7:30 AM"));
		grid2.add(new JLabel("8:00 AM"));
		grid2.add(new JLabel());
		//grid2.add(new JLabel("8:30 AM"));
		grid2.add(new JLabel("9:00 AM"));
		grid2.add(new JLabel());
		//grid2.add(new JLabel("9:30 AM"));
		grid2.add(new JLabel("10:00 AM"));
		grid2.add(new JLabel());
		//grid2.add(new JLabel("10:30 AM"));
		grid2.add(new JLabel("11:00 AM"));
		grid2.add(new JLabel());
		//grid2.add(new JLabel("11:30 AM"));
		grid2.add(new JLabel("12:00 PM"));
		grid2.add(new JLabel());
		//grid2.add(new JLabel("12:30 PM"));
		grid2.add(new JLabel("1:00 PM"));
		grid2.add(new JLabel());
		//grid2.add(new JLabel("1:30 PM"));
		grid2.add(new JLabel("2:00 PM"));
		grid2.add(new JLabel());
		//grid2.add(new JLabel("2:30 PM"));
		grid2.add(new JLabel("3:00 PM"));
		grid2.add(new JLabel());
		//grid2.add(new JLabel("3:30 PM"));
		grid2.add(new JLabel("4:00 PM"));
		grid2.add(new JLabel());
		//grid2.add(new JLabel("4:30 PM"));
		grid2.add(new JLabel("5:00 PM"));
		grid2.add(new JLabel());
		//grid2.add(new JLabel("5:30 PM"));
		grid2.add(new JLabel("6:00 PM"));
		grid2.add(new JLabel());
		//grid2.add(new JLabel("6:30 PM"));
		grid2.add(new JLabel("7:00 PM"));
		grid2.add(new JLabel());
		//grid2.add(new JLabel("7:30 PM"));
		grid2.add(new JLabel("8:00 PM"));
		grid2.add(new JLabel());
		//grid2.add(new JLabel("8:30 PM"));
		grid2.add(new JLabel("9:00 PM"));
		grid2.add(new JLabel());
		//grid2.add(new JLabel("9:30 PM"));
		grid2.add(new JLabel("10:00 PM"));
		grid2.add(new JLabel());
		//grid2.add(new JLabel("10:30 PM"));
		grid2.add(new JLabel("11:00 PM"));
		grid2.add(new JLabel());
		//grid2.add(new JLabel("11:30 PM"));
		
		for(int i = 0; i < 48; i++) {
			JPanel temp1 = new JPanel();
			temp1.setBackground(Color.white);
			temp1.add(new JLabel("JPanel: "+i));
			if(i%2 == 0)
				temp1.setBorder(new MatteBorder(0, 0, 2, 0, Color.GRAY));
			else
				temp1.setBorder(new MatteBorder(0, 0, 2, 0, Color.BLACK));
			grid1.add(temp1);
		}
		
		grid1.setVisible(true);
		grid2.setVisible(true);
		
		this.add(grid2);
		this.add(grid1);
		
		/*int gridWidth = 110;
		int gridHeight = 100;
		add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();

		gbl_panel.columnWidths = new int[]{100, 400};
		gbl_panel.rowHeights = new int[]{30, gridHeight, gridHeight, gridHeight, gridHeight, gridHeight, gridHeight};
		gbl_panel.columnWeights = new double[]{1.0, 1.0, 1.0};
		gbl_panel.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
		panel.setLayout(gbl_panel);

		Date today = new Date();
		currentDay = today.getDay();
		currentMonth = today.getMonth();
		currentYear = today.getYear() + 1900;
		//TODO: Update with current month and year
		mycal = new GregorianCalendar(currentYear, currentMonth, currentDay);
		currentDayOfWeek = mycal.get(Calendar.DAY_OF_WEEK);
		
		//addDayLabels();
//		addDays(mycal);
		panel.setVisible(true);
		// TODO Auto-generated constructor stub
*/	}
	public void addHourLabels()
	{
		ArrayList<String> hours = new ArrayList<String>();
		int hour = 0;
		
		for(int i = 0; i < 23; i++)
		{
			if(i > 12) hour = i+-13;
			else hour = i-1;
			
		}
	}
	
	
	public void addDayLabels(Calendar c)
	{
		
		StringBuilder sb = new StringBuilder();
		sb = sb.append(weekNames[c.get(Calendar.DAY_OF_WEEK)]);
		sb = sb.append(", ");
		sb = sb.append(monthNames[c.get(Calendar.DAY_OF_WEEK)]);
		sb = sb.append(" ");
		sb = sb.append(c.get(Calendar.DAY_OF_MONTH));
		sb = sb.append(", ");
		sb = sb.append(c.get(Calendar.YEAR));

		JLabel dayJLabel = new JLabel(sb.toString(),SwingConstants.RIGHT);
		dayJLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		JPanel dayPanelLabel = new JPanel();
		GridBagConstraints dayGridBagConstraint = new GridBagConstraints();
		dayPanelLabel.setBackground(new Color(138, 173, 209));
		dayGridBagConstraint.insets = new Insets(0,0,0,0);
		dayGridBagConstraint.fill = GridBagConstraints.BOTH;
		dayGridBagConstraint.weightx = 1;
		dayGridBagConstraint.weighty = 1;
		dayGridBagConstraint.gridx = 1;
		dayGridBagConstraint.gridy = 0;
		grid1.add(dayPanelLabel,dayGridBagConstraint);
		dayPanelLabel.add(dayJLabel);
		
	}
	@Override
	public void next() {
		Calendar current = Calendar.getInstance();
		int month = current.get(Calendar.MONTH);

		if (currentMonth == 11 && currentDay == 31){
			currentMonth = 0;
			currentDay = 1;
			currentYear++;
		} else if(mycal.getActualMaximum(Calendar.DAY_OF_MONTH) == currentDay){
			currentDay = 1;
			currentMonth++;
		}
		else{
			currentDay++;
		}
		Calendar next = new GregorianCalendar(currentYear, currentMonth, 1);
		mycal = next;

	}

	@Override
	public void previous() {
		Calendar current = Calendar.getInstance();
		int month = current.get(Calendar.MONTH);

		if (currentMonth == 0 && currentDay == 1){
			currentMonth = 11;
			currentDay = 31;
			currentYear--;
		} else if(mycal.getActualMinimum(Calendar.DAY_OF_MONTH) == currentDay) {
			currentMonth--;
			Calendar next = new GregorianCalendar(currentYear, currentMonth, 1);
			currentDay = next.getActualMaximum(Calendar.DAY_OF_MONTH);
		}
		else{
			currentDay--;
		}
		Calendar next = new GregorianCalendar(currentYear, currentMonth, 1);
		mycal = next;
	}

	@Override
	public void today() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getTitle() {
		
		StringBuilder sb = new StringBuilder();
		sb = sb.append(weekNames[currentDayOfWeek]);
		sb = sb.append(", ");
		sb = sb.append(monthNames[currentMonth]);
		sb = sb.append(" ");
		sb = sb.append(currentDay);
		sb = sb.append(", ");
		sb = sb.append(currentYear);
		return sb.toString();
	}
}
