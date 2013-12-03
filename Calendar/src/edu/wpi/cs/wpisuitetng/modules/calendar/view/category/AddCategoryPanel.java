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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Category;

public class AddCategoryPanel extends JPanel implements KeyListener, ActionListener {
	
	private JButton randomColors;
	private JButton ok;
	private JButton cancel;
	
	private JTextField nameTextField;
	
	private JLabel nameErrorLabel;
	
	private ColorSwatch cs;
	
	
	public AddCategoryPanel() {
		this.setLayout(new MigLayout("insets 0", "push[center]push", "[][][][]"));
		
		try {
			randomColors = new JButton(new ImageIcon(ImageIO.read(getClass().getResource("/images/color_meter.png"))));
		} catch (IOException e) {}
		
		ok = new JButton("Ok");
		cancel = new JButton("Cancel");
		
		randomColors.addActionListener(this);
		
		ok.setActionCommand("addok");
		cancel.setActionCommand("addcancel");
		
		cs = new ColorSwatch();
		
		nameTextField = new JTextField(20);
		nameTextField.addKeyListener(this);
		
		JPanel p = new JPanel();
		p.add(new JLabel("Name:"));
		p.add(nameTextField);
		
		nameErrorLabel = new JLabel("Enter a Name");
		nameErrorLabel.setForeground(Color.RED);
		
		this.add(p, "cell 0 0, alignx center");
		this.add(nameErrorLabel, "cell 0 1, alignx center");
		this.add(cs, "cell 0 2, w 200, h 200, alignx center");
		
		JPanel p2 = new JPanel();
		p2.add(randomColors);
		p2.add(ok);
		p2.add(cancel);
		
		this.add(p2, "cell 0 3, alignx center");
		
		this.validateFields();
	}

	private void validateFields() {
		if(nameTextField.getText().trim().length() == 0) {
			nameErrorLabel.setVisible(true);
			ok.setEnabled(false);
		} else {
			nameErrorLabel.setVisible(false);
			ok.setEnabled(true);
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
