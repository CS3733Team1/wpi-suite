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

import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.category.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.category.CategoryListModel;

public class CategoryPickerPanel extends JPanel {
	
	private CategoryListModel model;
	private JComboBox<Category> categoryComboBox;
	
	public CategoryPickerPanel() {
		this.model = CategoryListModel.getCategoryListModel();
		
		categoryComboBox = new JComboBox<Category>(model);
		categoryComboBox.setSelectedIndex(0);
		categoryComboBox.setRenderer(new CategoryListCellRenderer());
		
		this.add(categoryComboBox);
	}
	
	public Category getSelectedCategory() {
		return (Category)categoryComboBox.getSelectedItem();
	}
	
	public void addActionListener(ActionListener l) {categoryComboBox.addActionListener(l);}
	
	public void setSelectedCategory(Category toSelect)
	{
		categoryComboBox.setSelectedItem(toSelect);
	}
}
