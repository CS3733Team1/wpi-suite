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

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.Category;

public class CategoryListCellRenderer extends JPanel implements ListCellRenderer<Category> {

	private JLabel categoryName;
	
	public CategoryListCellRenderer() {
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		categoryName = new JLabel();
		this.add(categoryName);
	}
	
	@Override
	public Component getListCellRendererComponent(JList<? extends Category> list, Category category,
			int index, boolean isSelected, boolean cellHasFocus) {
		categoryName.setText("Name: " + category.getName());
		
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
