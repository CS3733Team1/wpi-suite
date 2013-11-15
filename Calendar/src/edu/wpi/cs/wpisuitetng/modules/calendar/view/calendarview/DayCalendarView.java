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

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.util.Date;
import java.util.GregorianCalendar;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class DayCalendarView extends JPanel implements ICalendarView {

	public static final String[] weekNames = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
	public static final String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	private ArrayList<JLabel> dayLabelList = new ArrayList<JLabel>();
	private ArrayList<JPanel> panelList = new ArrayList<JPanel>();
	private ArrayList<JLabel> nameLabelList = new ArrayList<JLabel>();
	private ArrayList<JPanel> nameList = new ArrayList<JPanel>();
	private ArrayList<GridBagConstraints> nameGridBagList = new ArrayList<GridBagConstraints>();
	private ArrayList<GridBagConstraints> gridBagList = new ArrayList<GridBagConstraints>();
	private JPanel panel = new JPanel();

	private Calendar mycal;
	private int currentMonth;
	private int currentDay;
	private int currentDayOfWeek;
	private int currentYear;

	public DayCalendarView() {
		
		int gridWidth = 110;
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
	}
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
		panel.add(dayPanelLabel,dayGridBagConstraint);
		dayPanelLabel.add(dayJLabel);
		
	}
	@Override
	public void next() {
		// TODO Auto-generated method stub

	}

	@Override
	public void previous() {

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
