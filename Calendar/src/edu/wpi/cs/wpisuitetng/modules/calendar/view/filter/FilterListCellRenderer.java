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

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Filter;

public class FilterListCellRenderer extends JPanel implements ListCellRenderer<Filter> {

	private JPanel colorSquare;
	private JLabel filterName;
	
	public FilterListCellRenderer() {
		this.setLayout(new MigLayout());
		filterName = new JLabel();
		colorSquare = new JPanel();
		colorSquare.setPreferredSize(new Dimension(16, 16));
		colorSquare.setBorder(new MatteBorder(2, 2, 2, 2, Color.BLACK));
		this.add(colorSquare, "split 2");
		this.add(filterName, "alignx left, wmax 200");
	}
	
	@Override
	public Component getListCellRendererComponent(JList<? extends Filter> list, Filter filter,
			int index, boolean isSelected, boolean cellHasFocus) {
		
		if(filter != null) {
			//TODO
			//colorSquare.setBackground(filter.getColor());
			//filterName.setText(filter.getName());
		}
		
		final Color background = UIManager.getDefaults().getColor("List.background");
		final Color foreground = UIManager.getDefaults().getColor("List.foreground");
		final Color selectionBackground = UIManager.getDefaults().getColor("List.selectionBackground");
		final Color selectionForeground = UIManager.getDefaults().getColor("List.selectionForeground");
		
		this.setBackground(isSelected ? selectionBackground : background);
		this.setForeground(isSelected ? selectionForeground : foreground);
		
		this.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		return this;
	}
}
