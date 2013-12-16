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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.ISchedulable;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.MainView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.month.CommitmentPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.commitment.CommitmentTabPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.event.EventTabPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.CalendarUtils;

/**
 * SchedMouseListener implements MouseListener.
 * It is used for keeping track of selected ISchedulables in day and week view,
 * as well as setting the background color for their tooltips
 */
public class SchedMouseListener implements MouseListener{

	private ISchedulable sched;
	private JPanel epanel;

	private static List<ISchedulable> selectedScheds= Collections.synchronizedList(new ArrayList<ISchedulable>());
	private static List<JPanel> selectedPanels= Collections.synchronizedList(new ArrayList<JPanel>());

	public SchedMouseListener(ISchedulable sched, JPanel epanel){
		this.sched=sched;
		this.epanel=epanel;
		//store "default" color for this panel in foreground so it can be retrieved when de-selected
		this.epanel.setForeground(sched.getCategory().getColor());
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		UIManager.put("ToolTip.background",new ColorUIResource(sched.getCategory().getColor()));
		float[] hsb=new float[3];
		Color catColor=sched.getCategory().getColor();
		hsb=Color.RGBtoHSB(catColor.getRed(), catColor.getGreen(), catColor.getBlue(), hsb);

		//if background is too dark, turn text white
		if(hsb[2]<0.5){
			UIManager.put("ToolTip.foreground", new ColorUIResource(Color.WHITE));
		}
		else{
			UIManager.put("ToolTip.foreground", new ColorUIResource(Color.BLACK));
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		UIManager.put("ToolTip.foreground",UIManager.getDefaults().getString("ToolTip.foreground"));
		UIManager.put("ToolTip.background",UIManager.getDefaults().getString("ToolTip.background"));
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int ctrldwn = MouseEvent.CTRL_DOWN_MASK;
		
		if(e.getClickCount() == 2) {
			if (sched instanceof Event)
			{
				EventTabPanel editEventPanel = new EventTabPanel((Event)sched);
				MainView.getCurrentCalendarPanel().addTab("Update Event", editEventPanel);
				MainView.getCurrentCalendarPanel().setSelectedComponent(editEventPanel);
			}
			else if(sched instanceof Commitment) 
			{
				CommitmentTabPanel editCommitmentPanel = new CommitmentTabPanel((Commitment)sched);
				MainView.getCurrentCalendarPanel().addTab("Update Commitmement", editCommitmentPanel);
				MainView.getCurrentCalendarPanel().setSelectedComponent(editCommitmentPanel);
			} 
		}
		
		//was ctrl key held while mouse was clicked?
		boolean multiSelect = ((e.getModifiersEx() & ctrldwn) == ctrldwn);
		if(multiSelect){
			//if this one is already selected, remove it from the selection list
			if(selectedScheds.contains(sched)){
				selectedScheds.remove(sched);
				selectedPanels.remove(this.epanel);
				deselectPanel(this.epanel);
			}
			//if this one isn't already selected, add it to the list
			else{
				selectedScheds.add(sched);
				selectedPanels.add(this.epanel);
				selectPanel(this.epanel);

			}
		}
		else{
			if(selectedScheds.contains(sched)){
				//were multiple panels selected when click occurred
				boolean multiple = (selectedScheds.size()>1);
				selectedScheds.clear();
				for(JPanel panel : selectedPanels){
					deselectPanel(panel);
				}
				selectedPanels.clear();
				//remove all panels except the one clicked
				if(multiple){
					selectedScheds.add(sched);
					selectedPanels.add(this.epanel);
					selectPanel(this.epanel);
				}
			}
			else{
				selectedScheds.clear();
				for(JPanel panel : selectedPanels){
					deselectPanel(panel);
				}
				selectedPanels.clear();
				selectedScheds.add(sched);
				selectedPanels.add(this.epanel);
				selectPanel(this.epanel);

			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	/**
	 * Returns the selected events
	 * @return a List<Event> representing the events selected on the calendar
	 */
	public static List<Event> getSelectedEvents(){
		ArrayList<Event> selectedEvents = new ArrayList<Event>();
		ListIterator<ISchedulable> iter = selectedScheds.listIterator();
		while(iter.hasNext()){
			ISchedulable test = iter.next();
			if(test instanceof Event){
				selectedEvents.add((Event) test);
			}
		}
		return selectedEvents;
	}

	/**
	 * Returns the selected commitments
	 * @return a List<Commitment> representing the commitments selected on the calendar
	 */
	public static List<Commitment> getSelectedCommitments(){
		ArrayList<Commitment> selectedCommits = new ArrayList<Commitment>();
		ListIterator<ISchedulable> iter = selectedScheds.listIterator();
		while(iter.hasNext()){
			ISchedulable test = iter.next();
			if(test instanceof Commitment){
				selectedCommits.add((Commitment) test);
			}
		}
		return selectedCommits;
	}
	
	/**
	 * Removes all the ISchedulables being tracked as selected. Primarily for use by the DayArea panel.
	 */
	public static void clearSelection(){
		selectedScheds.clear();
		ListIterator<JPanel> iter = selectedPanels.listIterator();
		while(iter.hasNext()){
			deselectPanel(iter.next());
		}
		selectedPanels.clear();
	}

	private void selectPanel(JPanel panel){
		panel.setBackground(CalendarUtils.darken(panel.getBackground()));
		JLabel j = (JLabel)panel.getComponent(0);
		j.setForeground(CalendarUtils.textColor(panel.getBackground()));
	}

	private static void deselectPanel(JPanel panel){
		panel.setBackground(panel.getForeground());
		JLabel j = (JLabel)panel.getComponent(0);
		j.setForeground(CalendarUtils.textColor(panel.getBackground()));
	}
}
