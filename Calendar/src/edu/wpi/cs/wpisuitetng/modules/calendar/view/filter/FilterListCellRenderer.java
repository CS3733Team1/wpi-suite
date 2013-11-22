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
import javax.swing.JRadioButton;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Filter;

public class FilterListCellRenderer extends JPanel implements ListCellRenderer<Filter> {

	private JPanel colorSquares;
	private JLabel filterName;
	private JRadioButton appliedFilterRadio;
	
	public FilterListCellRenderer() {
		this.setLayout(new MigLayout());
		filterName = new JLabel();
		colorSquares = new JPanel(new MigLayout("insets 0"));
		
		
		appliedFilterRadio = new JRadioButton();
		
		this.add(appliedFilterRadio, "split 2");
		this.add(filterName, "alignx left, wmax 140");
		this.add(colorSquares, "alignx right, wmax 60");
	}
	
	@Override
	public Component getListCellRendererComponent(JList<? extends Filter> list, Filter filter,
			int index, boolean isSelected, boolean cellHasFocus) {
		
		if(filter != null) {
			filterName.setText(filter.getName());
			for(Category c: filter.getCategories()) {
				JPanel colorSquare = new JPanel();
				colorSquare.setPreferredSize(new Dimension(16, 16));
				colorSquare.setBorder(new MatteBorder(2, 2, 2, 2, Color.BLACK));
				colorSquare.setBackground(c.getColor());
				colorSquares.add(colorSquare);
			}
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
