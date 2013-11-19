package edu.wpi.cs.wpisuitetng.modules.calendar.view.category;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.category.AddCategoryController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.category.DeleteCategoryController;

public class CategoryTabPanel extends JPanel implements ActionListener {

	private CategoryPanel categoryPanel;
	private AddCategoryController addControl;
	private DeleteCategoryController delControl;
	private AddCategoryPanel addCatPanel;
	
	public CategoryTabPanel() {
		this.setLayout(new MigLayout("fill"));
		addControl = new AddCategoryController();
		delControl = new DeleteCategoryController();
		categoryPanel = new CategoryPanel();
		categoryPanel.setAddCategoryListener(this);
		categoryPanel.setDeleteCategoryListener(this);
		
		this.setViewCategoryPanel();
	}
	
	public void setViewCategoryPanel() {
		this.removeAll();
		this.add(categoryPanel, "grow, push");
		this.invalidate();
		this.repaint();
	}
	
	public void setViewAddCategoryPanel() {
		this.removeAll();
		addCatPanel = new AddCategoryPanel();
		addCatPanel.setOkListener(this);
		addCatPanel.setCancelListener(this);
		this.add(addCatPanel, "grow, push");
		this.invalidate();
		this.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("add")) {
			setViewAddCategoryPanel();
		} else if(e.getActionCommand().equals("delete")) {
			delControl.deleteCategories(categoryPanel.getSelectedCategories());
			categoryPanel.clearSelection();
		} else if(e.getActionCommand().equals("addok")) {
			addControl.addCategory(addCatPanel.getCategory());
			this.setViewCategoryPanel();
		} else if(e.getActionCommand().equals("addcancel")) {
			this.setViewCategoryPanel();
		}
	}
}
