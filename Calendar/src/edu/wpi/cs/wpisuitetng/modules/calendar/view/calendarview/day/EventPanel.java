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

import java.awt.Font;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import net.miginfocom.swing.MigLayout;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;

public class EventPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea titleTextArea;
	private JTextArea dataTextArea;
	
	public EventPanel(Event eve){
		
		this.setLayout(new MigLayout("fill", 
				"[100%]", 
				"[10%][90%]"));
		
		titleTextArea = new JTextArea(eve.getName());
		titleTextArea.setEditable(false);
		titleTextArea.setFont(new Font(null, Font.PLAIN, 20));
		titleTextArea.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		
		StringBuilder titlebuilder = new StringBuilder();
		titlebuilder.append("cell ");
		titlebuilder.append("0");
		titlebuilder.append(" ");
		titlebuilder.append("0");
		titlebuilder.append(",grow, push");
		
		this.add(titleTextArea, titlebuilder.toString());
		
		dataTextArea = new JTextArea("Description: " + eve.getDescription() +"\nCategory:" + eve.getCategory());
		dataTextArea.setEditable(false);
		dataTextArea.setFont(new Font(null, Font.PLAIN, 10));
		dataTextArea.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		
		StringBuilder databuilder = new StringBuilder();
		databuilder.append("cell ");
		databuilder.append("0");
		databuilder.append(" ");
		databuilder.append("1");
		databuilder.append(",grow, push");
		
		this.add(dataTextArea, databuilder.toString());
		
		this.setVisible(true);
		this.setFocusable(true);
		
	}
	
	public void fitText(){
		int start = titleTextArea.getText().length() - 1;
	    while(titleTextArea.getWidth() > titleTextArea.getPreferredSize().width && start>0) {  
	        titleTextArea.setText(titleTextArea.getText().substring(0,start) + "...");
	        start--;
	    }
	}
	
	public void paint(Graphics g){
		fitText();
		
		super.paint(g);
	}
	
}
