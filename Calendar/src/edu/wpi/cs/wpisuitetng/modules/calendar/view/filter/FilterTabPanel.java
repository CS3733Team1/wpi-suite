/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.view.filter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.filter.AddFilterController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.filter.DeleteFilterController;

public class FilterTabPanel extends JPanel implements ActionListener {

	private FilterPanel filterPanel;
	private AddFilterController addControl;
	private DeleteFilterController delControl;
	private AddFilterPanel addFilPanel;
	
	public FilterTabPanel() {
		this.setLayout(new MigLayout("fill"));
		addControl = new AddFilterController();
		delControl = new DeleteFilterController();
		filterPanel = new FilterPanel();
		filterPanel.setAddFilterListener(this);
		filterPanel.setDeleteFilterListener(this);
		
		this.setViewFilterPanel();
	}
	
	public void setViewFilterPanel() {
		this.removeAll();
		this.add(filterPanel, "grow, push");
		this.invalidate();
		this.repaint();
	}
	
	public void setViewAddFilterPanel() {
		this.removeAll();
		addFilPanel = new AddFilterPanel();
		addFilPanel.setOkListener(this);
		addFilPanel.setCancelListener(this);
		this.add(addFilPanel, "grow, push");
		this.invalidate();
		this.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("add")) {
			setViewAddFilterPanel();
		} else if(e.getActionCommand().equals("delete")) {
			delControl.deleteFilters(filterPanel.getSelectedFilters());
			filterPanel.clearSelection();
		} else if(e.getActionCommand().equals("addok")) {
			addControl.addFilter(addFilPanel.getFilter());
			this.setViewFilterPanel();
		} else if(e.getActionCommand().equals("addcancel")) {
			this.setViewFilterPanel();
		}
	}
}
