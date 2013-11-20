package edu.wpi.cs.wpisuitetng.modules.calendar.view.category;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.CategoryListModel;

public class PickCategoryPanel extends JPanel {
	
	private CategoryListModel model;
	private JComboBox<Category> categoryComboBox;
	
	public PickCategoryPanel() {
		this.model = CategoryListModel.getCategoryListModel();
		
		categoryComboBox = new JComboBox<Category>(model);
		categoryComboBox.setRenderer(new CategoryListCellRenderer());
		
		this.add(categoryComboBox);
	}
	
	public Category getSelectedCategory() {
		return (Category)categoryComboBox.getSelectedItem();
	}
}
