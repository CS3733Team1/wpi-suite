package edu.wpi.cs.wpisuitetng.modules.calendar.view.category;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class CategoryPanel extends JPanel{
	private CategoryListPanel categoryListPanel;

	private JButton addCategoryButton, deleteCategoryButton;

	public CategoryPanel() {
		this.setLayout(new MigLayout("", "[][]", "[][]"));
		//new ImageIcon(ImageIO.read(getClass().getResource("/images/categories.png")));

		try {
			addCategoryButton = new JButton("<html>New<br/>Category</html>",
					new ImageIcon(ImageIO.read(getClass().getResource("/images/add_category.png"))));

			deleteCategoryButton = new JButton("<html>Delete<br/>Category</html>",
					new ImageIcon(ImageIO.read(getClass().getResource("/images/delete_category.png"))));
		} catch (IOException e) {e.printStackTrace();}

		this.categoryListPanel = new CategoryListPanel();
		
		this.add(addCategoryButton, "cell 0 0, alignx center");
		this.add(deleteCategoryButton, "cell 1 0, alignx center");
		this.add(categoryListPanel, "cell 0 1, span 2 1, grow");
	}
}
