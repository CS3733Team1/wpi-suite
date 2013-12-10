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

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.CategoryListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.CustomListSelectionModel;

/**
 * This is the view where the list of categories are displayed.
 * Creates both a scroll pane and JList which stores all of the categories.
 * @version Revision: 1.0
 * @author
 */
public class CategoryListPanel extends JPanel implements MouseListener {

	private CategoryListModel model;
	private JList<Category> categoryList;
	private JScrollPane scrollPane;

	public CategoryListPanel() {
		this.model = CategoryListModel.getCategoryListModel();

		this.setLayout(new MigLayout("fill, insets 0"));

		categoryList = new JList<Category>(model);
		categoryList.setSelectionModel(new CustomListSelectionModel());

		categoryList.setCellRenderer(new CategoryListCellRenderer());
		categoryList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		categoryList.setLayoutOrientation(JList.VERTICAL);

		categoryList.setVisibleRowCount(0);
		
		categoryList.addMouseListener(this);

		scrollPane = new JScrollPane(categoryList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		this.add(scrollPane, "grow, push");
	}

	public JList<Category> getCategoryList() {
		return categoryList;
	}

	public void clearSelection() {
		categoryList.clearSelection();
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		Rectangle r = categoryList.getCellBounds(0, categoryList.getLastVisibleIndex());
		if(r == null || !r.contains(e.getPoint())) {
			if(Arrays.binarySearch(categoryList.getSelectedIndices(), categoryList.getLastVisibleIndex()) >= 0)
				categoryList.getSelectionModel().removeIndexInterval(categoryList.getLastVisibleIndex(), categoryList.getLastVisibleIndex());
			else categoryList.getSelectionModel().addSelectionInterval(categoryList.getLastVisibleIndex(), categoryList.getLastVisibleIndex());
		}
	}

	//Unused
	
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}	
	@Override
	public void mouseReleased(MouseEvent e) {}
}
