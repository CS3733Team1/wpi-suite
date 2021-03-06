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
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.category.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.category.CategoryListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.buttons.TransparentButton;

public class AddCategoryPanel extends JPanel implements KeyListener, ActionListener {
	private final static Logger LOGGER = Logger.getLogger(AddCategoryPanel.class.getName());

	private TransparentButton randomColors;
	private JButton ok;
	private JButton cancel;

	private JTextField nameTextField;

	private JLabel nameErrorLabel;

	private ColorSwatch cs;

	public AddCategoryPanel() {
		this.setLayout(new MigLayout("insets 0", "push[center]push", "[][][][]"));

		ImageIcon colorMeterIcon = new ImageIcon();

		try {
			colorMeterIcon = new ImageIcon(ImageIO.read(getClass().getResource("/images/color_meter.png")));
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "/images/color_meter.png not found.", e);
		}

		randomColors = new TransparentButton(colorMeterIcon);

		ok = new JButton("Ok");
		cancel = new JButton("Cancel");

		randomColors.setMargin(new Insets(0, 0, 0, 0));
		randomColors.addActionListener(this);

		ok.setActionCommand("addok");
		cancel.setActionCommand("addcancel");

		cs = new ColorSwatch();

		nameTextField = new JTextField(15);
		nameTextField.addKeyListener(this);

		JPanel p = new JPanel();
		p.add(new JLabel("Name:"));
		p.add(nameTextField, "pushy, gapafter 10px");

		nameErrorLabel = new JLabel("Enter a Name");
		nameErrorLabel.setForeground(Color.RED);

		this.add(p, "cell 0 0, alignx center");
		this.add(nameErrorLabel, "cell 0 1, alignx center");
		this.add(cs, "cell 0 2, w 200, h 200, alignx center");

		JPanel p3 = new JPanel();
		p3.add(randomColors);
		p3.add(ok);
		p3.add(cancel);

		this.add(p3, "cell 0 3, alignx center");

		this.validateFields();
	}

	private void validateFields() {
		nameErrorLabel.setVisible(false);
		ok.setEnabled(true);

		if(nameTextField.getText().trim().length() == 0) {
			nameErrorLabel.setText("Enter a Name");
			nameErrorLabel.setVisible(true);
			ok.setEnabled(false);
		} 

		if (CategoryListModel.getCategoryListModel().isReserved(nameTextField.getText())) {
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

	public Category getCategory() {
		return new Category(nameTextField.getText(), cs.getSelectedColor());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.remove(cs);
		cs = new ColorSwatch();
		cs.revalidate();
		this.add(cs, "cell 0 2, w 200, h 200, alignx center");
		this.revalidate();
	}
}
