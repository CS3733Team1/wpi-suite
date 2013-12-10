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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.CategoryListModel;

public class CategoryPanel extends JPanel implements MouseListener{
	private CategoryListPanel categoryListPanel;

	private JButton addCategoryButton, deleteCategoryButton;

	public CategoryPanel() {
		this.setLayout(new MigLayout("fill", "[grow, fill]", "[][grow, fill]"));

		try {
			addCategoryButton = new JButton("<html>New<br/>Category</html>",
					new ImageIcon(ImageIO.read(getClass().getResource("/images/add_category.png"))));

			deleteCategoryButton = new JButton("<html>Delete<br/>Category</html>",
					new ImageIcon(ImageIO.read(getClass().getResource("/images/delete_category.png"))));
		} catch (IOException e) {e.printStackTrace();}

		this.categoryListPanel = new CategoryListPanel();
		
		addCategoryButton.setActionCommand("add");
		deleteCategoryButton.setActionCommand("delete");
		
		JPanel p = new JPanel();
		p.add(addCategoryButton);
		p.add(deleteCategoryButton);
		
		this.add(p, "wrap");
		this.add(categoryListPanel, "grow, push");
	}
	
	public void setAddCategoryListener(ActionListener al) {
		addCategoryButton.addActionListener(al);
	}
	
	public void setDeleteCategoryListener(ActionListener al) {
		deleteCategoryButton.addActionListener(al);
	}

	public List<Category> getSelectedCategories() {
		return categoryListPanel.getCategoryList().getSelectedValuesList();
	}

	public void clearSelection() {
		categoryListPanel.clearSelection();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		boolean deleteEnabled = true;
		for( Category c: getSelectedCategories()){
			if(CategoryListModel.getCategoryListModel().isDefault(c))
			{
				deleteEnabled = false;
			}
		}
		deleteCategoryButton.setEnabled(deleteEnabled);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}
}
