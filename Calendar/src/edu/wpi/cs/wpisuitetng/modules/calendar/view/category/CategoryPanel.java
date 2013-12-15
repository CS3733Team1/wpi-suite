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
import java.awt.Point;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.CategoryListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.list.ListItemListener;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.list.SRList;

public class CategoryPanel extends JPanel implements ListItemListener<Category> {
	//private CategoryListPanel categoryListPanel;
	private SRList<Category> categoryList;
	
	private JButton addCategoryButton, deleteCategoryButton;
	JLabel errorLabel = new JLabel("Cannot delete default(*) categories");
	
	public CategoryPanel() {
		this.setLayout(new MigLayout("fill", "[grow, fill]", "[][][grow, fill]"));

		try {
			addCategoryButton = new JButton("<html>New<br/>Category</html>",
					new ImageIcon(ImageIO.read(getClass().getResource("/images/add_category.png"))));

			deleteCategoryButton = new JButton("<html>Delete<br/>Category</html>",
					new ImageIcon(ImageIO.read(getClass().getResource("/images/delete_category.png"))));
		} catch (IOException e) {e.printStackTrace();}

		this.categoryList = new SRList<Category>(CategoryListModel.getCategoryListModel());
		categoryList.setListItemRenderer(new CategoryListItemRenderer());
		categoryList.addListItemListener(this);
		
		addCategoryButton.setActionCommand("add");
		deleteCategoryButton.setActionCommand("delete");
		
		JPanel p = new JPanel();
		p.add(addCategoryButton);
		p.add(deleteCategoryButton);
		
		errorLabel.setForeground(Color.RED);
		errorLabel.setVisible(false);
		
		JLabel defaultCategory = new JLabel("Default(*) categories cannot be deleted");
		this.add(p, "wrap");
		//this.add(errorLabel, "alignx center, hmax 15, wrap");
		this.add(defaultCategory, "alignx center, hmax 15, wrap");
		//this.add(categoryListPanel, "grow, push");
		this.add(categoryList, "grow, push");
	}
	
	public void setAddCategoryListener(ActionListener al) {
		addCategoryButton.addActionListener(al);
	}
	
	public void setDeleteCategoryListener(ActionListener al) {
		deleteCategoryButton.addActionListener(al);
	}

	public List<Category> getSelectedCategories() {
		return categoryList.getSelectedItems();
	}

	@Override
	public void itemsSelected(List<Category> listObjects) {
		boolean deleteEnabled = true;
		for(Category c: listObjects) {
			if(CategoryListModel.getCategoryListModel().isDefault(c)) {
				deleteEnabled = false;
			}
		}
		deleteCategoryButton.setEnabled(deleteEnabled);
		errorLabel.setVisible(!deleteEnabled);
		repaint();
	}

	@Override
	public void itemDoubleClicked(Category listObject) {}

	@Override
	public void itemRightClicked(Category listObject, Point p) {}

	@Override
	public void itemFocused(Category listObject) {}
}
