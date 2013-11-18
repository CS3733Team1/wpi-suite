package edu.wpi.cs.wpisuitetng.modules.calendar.view.category;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.Category;

public class CategoryTabPanel extends JPanel {
	private CategoryListPanel categoryListPanel;
	
	public CategoryTabPanel() {
		this.setLayout(new BorderLayout());
		this.categoryListPanel = new CategoryListPanel();
		this.displayCategories();
	}
	
	public void displayCategories() {
		this.removeAll();
		this.add(categoryListPanel, BorderLayout.CENTER);
	}
	
	public void displayAddCategory() {
		this.removeAll();
		JPanel p = new JPanel();
		ColorSwatch cs = new ColorSwatch();
		p.add(cs);
		p.add(new JLabel("Name:"));
		p.add(new JTextField());
		this.add(p, BorderLayout.CENTER);
		this.add(categoryListPanel, BorderLayout.EAST);
	}

	public List<Category> getSelectedCategories() {
		return categoryListPanel.getCategoryList().getSelectedValuesList();
	}
}
