package edu.wpi.cs.wpisuitetng.modules.calendar.view.category;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Category;

public class CategoryPanel extends JPanel{
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
}
