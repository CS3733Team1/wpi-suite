package edu.wpi.cs.wpisuitetng.modules.calendar.view.category;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Category;

public class CategoryTabPanel extends JPanel {

	public CategoryTabPanel() {
		this.add(new CategoryPanel());
	}
	public void displayAddCategory() {
		this.removeAll();

		this.setLayout(new MigLayout("", "[120]", "[][][120][]"));
		JPanel p = new JPanel();
		p.add(new JLabel("Name:"));
		p.add(new JTextField(20));
		ColorSwatch cs = new ColorSwatch();
		this.add(p, "cell 0 0");
		this.add(cs, "cell 0 2");
		JPanel p2 = new JPanel();
		p2.add(new JButton("Ref"));
		p2.add(new JButton("Ok"));
		p2.add(new JButton("Cancel"));
		this.add(p2, "cell 0 3, alignx center");
	}
	
	public List<Category> getSelectedCategories() {
		return null;// categoryListPanel.getCategoryList().getSelectedValuesList();
	}
}
