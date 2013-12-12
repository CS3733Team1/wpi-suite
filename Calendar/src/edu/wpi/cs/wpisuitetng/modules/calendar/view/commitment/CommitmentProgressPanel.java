/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.view.commitment;

import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;

public class CommitmentProgressPanel extends JPanel{
	private JComboBox<String> commitmentComboBox;
	private String[] states = {"New","In Progress","Complete"};
	
	public CommitmentProgressPanel(){
		commitmentComboBox = new JComboBox<String>(states);
		commitmentComboBox.setSelectedIndex(0);
		this.add(commitmentComboBox);
	}
	
	public void setSelected(String select){
		commitmentComboBox.setSelectedItem(select);
	}
	
	public void addActionListener(ActionListener l) {commitmentComboBox.addActionListener(l);}
	
	public String getSelectedState(){
		return (String)commitmentComboBox.getSelectedItem();
	}
}
