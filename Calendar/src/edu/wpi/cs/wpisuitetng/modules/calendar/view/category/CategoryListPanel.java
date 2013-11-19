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

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.CategoryListModel;

/**
 * This is the view where the list of categories are displayed.
 * Creates both a scroll pane and JList which stores all of the categories.
 * @version Revision: 1.0
 * @author
 */
public class CategoryListPanel extends JPanel {

	private CategoryListModel model;
	private JList<Category> categoryList;
	
	public CategoryListPanel() {
		this.model = CategoryListModel.getCategoryListModel();
		
		this.setLayout(new MigLayout("fill"));
		
		categoryList = new JList<Category>(model);
		
		categoryList.setCellRenderer(new CategoryListCellRenderer());
		categoryList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		categoryList.setLayoutOrientation(JList.VERTICAL);

		JScrollPane scrollPane = new JScrollPane(categoryList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		this.add(new JLabel("Categories"), "wrap");
		this.add(scrollPane, "grow");
	}

	public JList<Category> getCategoryList() {
		return categoryList;
	}
}
