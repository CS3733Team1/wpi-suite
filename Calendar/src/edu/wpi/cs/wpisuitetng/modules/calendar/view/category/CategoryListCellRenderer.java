/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.view.category;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.MatteBorder;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.CalendarUtils;

public class CategoryListCellRenderer extends JPanel implements ListCellRenderer<Category> {

	private JPanel colorSquare;
	private JLabel categoryName;
	
	public CategoryListCellRenderer() {
		this.setLayout(new MigLayout());
		categoryName = new JLabel();
		colorSquare = new JPanel();
		colorSquare.setPreferredSize(new Dimension(16, 16));
		colorSquare.setBorder(new MatteBorder(2, 2, 2, 2, Color.BLACK));
		this.add(colorSquare, "split 2");
		this.add(categoryName, "alignx left, wmax 200");
		
		this.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
	}
	
	@Override
	public Component getListCellRendererComponent(JList<? extends Category> list, Category category,
			int index, boolean isSelected, boolean cellHasFocus) {
		
		if(category != null) {
			colorSquare.setBackground(category.getColor());
			categoryName.setText(category.getName());
		}
		
		this.setBackground(isSelected ? CalendarUtils.selectionColor : Color.WHITE);
		
		return this;
	}
}
