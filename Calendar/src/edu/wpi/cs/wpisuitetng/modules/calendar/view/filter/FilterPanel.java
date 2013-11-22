package edu.wpi.cs.wpisuitetng.modules.calendar.view.filter;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Category;

public class FilterPanel extends JPanel{
	FilterListPanel filterListPanel;

	private JButton addFilterButton, deleteFilterButton;

	public FilterPanel() {
		this.setLayout(new MigLayout("fill", "[grow, fill]", "[][grow, fill]"));

		try {
			addFilterButton = new JButton("<html>New<br/>Filter</html>",
					new ImageIcon(ImageIO.read(getClass().getResource("/images/add_filter.png"))));

			deleteFilterButton = new JButton("<html>Delete<br/>Filter</html>",
					new ImageIcon(ImageIO.read(getClass().getResource("/images/delete_filter.png"))));
		} catch (IOException e) {e.printStackTrace();}

		this.filterListPanel = new FilterListPanel();
		
		addFilterButton.setActionCommand("add");
		deleteFilterButton.setActionCommand("delete");
		
		JPanel p = new JPanel();
		p.add(addFilterButton);
		p.add(deleteFilterButton);
		
		this.add(p, "wrap");
		this.add(filterListPanel, "grow, push");
	}
	
	public void setAddCategoryListener(ActionListener al) {
		addFilterButton.addActionListener(al);
	}
	
	public void setDeleteCategoryListener(ActionListener al) {
		deleteFilterButton.addActionListener(al);
	}

	public List<Category> getSelectedCategories() {
		//TODO
		//return filterListPanel.getFilterList().getSelectedValuesList();
		return null;
	}

	public void clearSelection() {
		filterListPanel.clearSelection();
	}
}
