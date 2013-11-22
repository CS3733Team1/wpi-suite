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

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.EventListModel;

public class DatePanel2 extends JPanel implements ListDataListener{
	private Date paneldate;
	private ArrayList<JLabel> eventLabelList;
	private JPanel date;
	private JPanel events;
	private Color text;
	private Color background;
	private JLabel dateLabel;
	private int day;

	public DatePanel2(Date today){
		this.setLayout(new MigLayout("fill, insets 0", 
				"[]", 
				"[][]"));
		eventLabelList = new ArrayList<JLabel>();
		this.setDate(today);
		updatePanel();
		loadAllLabels();
	}

	public void setDate(Date today){
		paneldate = today;
	}

	public Date getDate(){
		return paneldate;
	}

	public void addEvent(Event eve) {
		JLabel jLab = new JLabel(eve.getName());
		Category c = eve.getCategory();
		if(c != null)
			jLab.setForeground(eve.getCategory().getColor());
		else
			jLab.setForeground(Color.cyan);
		eventLabelList.add(jLab);
		
		loadAllLabels();
	}

	public void clearEvents(){
		eventLabelList.removeAll(eventLabelList);
	}

	public void updatePanel(){
		clearEvents();
		List<Event> events = EventListModel.getEventListModel().getList();
		for(Event eve: events){
			Date evedate = eve.getStartDate();
			if (evedate.getYear() == paneldate.getYear() && evedate.getDate() == paneldate.getDate() && evedate.getMonth() == paneldate.getMonth()){
				addEvent(eve);
			}
		}
		loadAllLabels();
	}

	public void setDay(int day) {
		this.day = day;
		date = new JPanel(new MigLayout("insets 0"));
		dateLabel = new JLabel(""+day);
		dateLabel.setForeground(text);
		date.add(dateLabel, "alignx right");
		date.setBackground(background);
		this.setBackground(background);
		this.setBorder(new MatteBorder(1, 1, 1, 1, Color.gray));
		this.add(date,"growx, aligny top, wrap");
		
		updatePanel();
	}

	public void setEvent()
	{
		loadAllLabels();
	}

	public void setColors(Color text, Color background) {
		this.text = text;
		this.background = background;
	}

	public void updateLayout()
	{
		setDay(day);
		setEvent();
	}

	private void loadAllLabels()
	{	
		if(events!=null)
			this.remove(events);

		if(eventLabelList!=null){
			if(eventLabelList.size()>0){
				events = new JPanel(new MigLayout("insets 0"));
				events.setBackground(background);
				int heightOfJPanel = this.getHeight();
				System.out.println("HEIGHT: "+heightOfJPanel);
				int unaddedEvents = 0;
				JLabel more = new JLabel("more");
				more.setFont(new Font(more.getFont().getFontName(), more.getFont().getStyle(), 10));

				for(JLabel jLabel: eventLabelList)
				{
					jLabel.setFont(new Font(jLabel.getFont().getFontName(), jLabel.getFont().getStyle(), 10));
					if(heightOfJPanel >= 44)//change to preffered size latter
					{
						System.out.println("HEIGHT2: "+heightOfJPanel);
						heightOfJPanel = heightOfJPanel-20;
						events.add(jLabel,"wmin 0, hmin 13, hmax 16, gapy 0 0, wrap");
					}else {
						unaddedEvents++;
					}
				}
				if(unaddedEvents > 0) {
					more.setText(unaddedEvents + " more..");
					events.add(more, "wmin 0, hmin 13, hmax 16, gapy 0 0");
				}

				this.add(events,"grow, h 5000, w 5000");
			}
		}
	}

	@Override
	public void repaint()
	{	
		super.repaint();
		loadAllLabels();
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
