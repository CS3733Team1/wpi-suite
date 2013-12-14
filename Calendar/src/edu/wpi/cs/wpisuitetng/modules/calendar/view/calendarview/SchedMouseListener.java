/*******************************************************************************
x * Copyright (c) 2013 WPI-Suite
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
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.ISchedulable;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.CalendarUtils;

public class SchedMouseListener implements MouseListener{

	ISchedulable sched;
	JPanel epanel;
	
	static List<ISchedulable> selectedScheds= Collections.synchronizedList(new ArrayList<ISchedulable>());
	static List<JPanel> selectedPanels= Collections.synchronizedList(new ArrayList<JPanel>());
	
	private Color originalColor;
	
	public SchedMouseListener(ISchedulable sched, JPanel epanel){
		this.sched=sched;
		this.epanel=epanel;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (sched.getCategory() != null){
			UIManager.put("ToolTip.background",new ColorUIResource(sched.getCategory().getColor()));
			float[] hsb=new float[3];
			Color catColor=sched.getCategory().getColor();
			hsb=Color.RGBtoHSB(catColor.getRed(), catColor.getGreen(), catColor.getBlue(), hsb);
			if(hsb[2]<0.5){
				UIManager.put("ToolTip.foreground", new ColorUIResource(Color.WHITE));
			}
			else{
				UIManager.put("ToolTip.foreground", new ColorUIResource(Color.BLACK));
			}
		}
		else{
			UIManager.put("ToolTip.background", new ColorUIResource(Color.CYAN));
			UIManager.put("ToolTip.foreground",new ColorUIResource(Color.BLACK));
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
		boolean multiSelect = ((e.getModifiersEx() & ctrldwn) == ctrldwn);
		if(multiSelect) {
			if(selectedScheds.contains(sched)){
				selectedScheds.remove(sched);
				selectedPanels.remove(this.epanel);
				deselectPanel(this.epanel);
			}
			else{
				selectedScheds.add(sched);
				selectedPanels.add(this.epanel);
				selectPanel(this.epanel);
				
			}
		} else {
			if(selectedScheds.contains(sched)){
				boolean multiple = (selectedScheds.size()!=1);
				selectedScheds.clear();
				for(JPanel panel : selectedPanels){
					deselectPanel(panel);
				}
				selectedPanels.clear();
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
//		invertBGColor(this.epanel);
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
	
	public static List<Event> getSelectedEvents(){
		ArrayList<Event> selectedEvents = new ArrayList<Event>();
		ListIterator<ISchedulable> iter;
		iter=selectedScheds.listIterator();
		while(iter.hasNext()){
			ISchedulable test = iter.next();
			if(test instanceof Event){
				selectedEvents.add((Event) test);
			}
		}
		return selectedEvents;
	}
	
	public static List<Commitment> getSelectedCommitments(){
		ArrayList<Commitment> selectedCommits = new ArrayList<Commitment>();
		ListIterator<ISchedulable> iter;
		iter=selectedScheds.listIterator();
		while(iter.hasNext()){
			ISchedulable test = iter.next();
			if(test instanceof Commitment){
				selectedCommits.add((Commitment) test);
			}
		}
		return selectedCommits;
	}
	
	private void invertBGColor(JPanel panel){
		int newRGB;
		float[] hsb = new float[3];
		Color panelColor=panel.getBackground();
		hsb=Color.RGBtoHSB(panelColor.getRed(), panelColor.getGreen(), panelColor.getBlue(), hsb);
		newRGB=Color.HSBtoRGB(1-hsb[0], hsb[1], hsb[2]);
		Color newPanelColor = new Color(newRGB);
		panel.setBackground(newPanelColor);
	}
	
	private void selectPanel(JPanel panel){
		originalColor = panel.getBackground();
		panel.setBackground(CalendarUtils.darken(originalColor));
		JLabel j = (JLabel)panel.getComponent(0);
		j.setForeground(CalendarUtils.textColor(panel.getBackground()));
		
	}
	
	private void deselectPanel(JPanel panel){
		
		if (originalColor == null || panel.getBackground() == null){
			selectPanel(panel);
			return;
		}
		panel.setBackground(originalColor);
		JLabel j = (JLabel)panel.getComponent(0);
		j.setForeground(CalendarUtils.textColor(panel.getBackground()));
	}
}
