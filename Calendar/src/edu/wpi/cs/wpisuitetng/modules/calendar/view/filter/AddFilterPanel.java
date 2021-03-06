package edu.wpi.cs.wpisuitetng.modules.calendar.view.filter;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.category.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.filter.Filter;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.filter.FilterListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.category.CategoryListAddRemovePanel;

public class AddFilterPanel extends JPanel implements KeyListener, ActionListener {

	private CategoryListAddRemovePanel topCategoryListPanel;
	private CategoryListAddRemovePanel bottomCategoryListPanel;

	private JButton ok;
	private JButton cancel;
	private JButton addCat;
	private JButton removeCat;

	private JTextField nameTextField;

	private JLabel nameErrorLabel;

	public AddFilterPanel() {
		this.setLayout(new MigLayout("fill"));

		ok = new JButton("Ok");
		cancel = new JButton("Cancel");

		addCat = new JButton("Add Category");
		removeCat = new JButton("Remove Category");

		ok.setActionCommand("addok");
		cancel.setActionCommand("addcancel");
		addCat.setActionCommand("movedown");
		removeCat.setActionCommand("moveup");

		addCat.addActionListener(this);
		removeCat.addActionListener(this);

		nameTextField = new JTextField(20);
		nameTextField.addKeyListener(this);

		JPanel p = new JPanel();
		p.add(new JLabel("Name:"));
		p.add(nameTextField);

		nameErrorLabel = new JLabel("Enter a Name");
		nameErrorLabel.setForeground(Color.RED);

		this.add(p, "alignx center, wrap");
		this.add(nameErrorLabel, "alignx center, wrap");

		topCategoryListPanel = new CategoryListAddRemovePanel(false);

		this.add(topCategoryListPanel, "grow, push, alignx center, wrap");

		JPanel p2 = new JPanel();
		p2.add(addCat);
		p2.add(removeCat);

		this.add(p2, "alignx center, wrap");

		bottomCategoryListPanel = new CategoryListAddRemovePanel(true);
		this.add(bottomCategoryListPanel, "grow, push, alignx center, wrap");

		JPanel p3 = new JPanel();
		p3.add(ok);
		p3.add(cancel);

		this.add(p3, "alignx center");

		this.validateFields();
	}

	private void validateFields() {
		nameErrorLabel.setVisible(false);
		ok.setEnabled(true);
		addCat.setEnabled(true);
		removeCat.setEnabled(true);

		if (bottomCategoryListPanel.getCategories().isEmpty()){
			nameErrorLabel.setText("Add at least one category");
			nameErrorLabel.setVisible(true);
			ok.setEnabled(false);
			removeCat.setEnabled(false);
		}

		if (topCategoryListPanel.getCategories().isEmpty()){
			nameErrorLabel.setText("There are no more categories to add");
			nameErrorLabel.setVisible(true);
			addCat.setEnabled(false);
		}

		if(nameTextField.getText().trim().length() == 0) {
			nameErrorLabel.setText("Enter a Name");
			nameErrorLabel.setVisible(true);
			ok.setEnabled(false);
		}

		if (FilterListModel.getFilterListModel().isReserved(nameTextField.getText())){
			nameErrorLabel.setText("The current name is already in use");
			nameErrorLabel.setVisible(true);
			ok.setEnabled(false);
		}

		this.revalidate();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		this.validateFields();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		this.validateFields();
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	public void setCancelListener(ActionListener al) {
		cancel.addActionListener(al);
	}

	public void setOkListener(ActionListener al) {
		ok.addActionListener(al);
	}

	public Filter getFilter() {
		return new Filter(nameTextField.getText(), bottomCategoryListPanel.getCategories());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("movedown")) {
			List<Category> temp = topCategoryListPanel.getSelectedCategories();
			bottomCategoryListPanel.addCategories(temp);
			topCategoryListPanel.removeCategories(temp);
			topCategoryListPanel.clearSelection();
			bottomCategoryListPanel.clearSelection();
		} else if(e.getActionCommand().equals("moveup")) {
			List<Category> temp = bottomCategoryListPanel.getSelectedCategories();
			topCategoryListPanel.addCategories(temp);
			bottomCategoryListPanel.removeCategories(temp);
			topCategoryListPanel.clearSelection();
			bottomCategoryListPanel.clearSelection();
		}
		validateFields();
	}
}
