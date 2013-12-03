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
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import net.miginfocom.swing.MigLayout;

public class ColorSwatch extends JPanel implements ActionListener {
	private Color selectedColor;
	private JPanel centerPanel;

	public ColorSwatch() {
		selectedColor = Color.BLACK;
		this.setLayout(new MigLayout("fill", "[10]2[10]2[10]2[10]2[10]2[10]", "[10]2[10]2[10]2[10]2[10]2[10]"));

		centerPanel = new JPanel();
		centerPanel.setBackground(selectedColor);
		centerPanel.setBorder(new MatteBorder(5, 5, 5, 5, Color.BLACK));

		Color colors[] = {Color.BLACK, Color.RED, Color.GREEN, Color.BLUE, null, Color.CYAN, Color.MAGENTA, Color.YELLOW, Color.WHITE};

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if(i == 1 && j == 1) {
					this.add(centerPanel, "cell 2 2, span 2 2, grow");
				} else {
					for(int x = 0; x < 2; x++) {
						for(int y = 0; y < 2; y++) {
							ColorSquare cs = new ColorSquare(generateRandomColor(colors[3*j + i]));
							cs.addActionListener(this);
							this.add(cs, "cell " + (i*2+x) + " " + (j*2+y) + ", grow");
						}
					}
				}
			}
		}
	}

	public Color generateRandomColor(Color mix) {
		Random random = new Random();
		int red = random.nextInt(256);
		int green = random.nextInt(256);
		int blue = random.nextInt(256);

		// mix the color
		if (mix != null) {
			red = (red + mix.getRed()) / 2;
			green = (green + mix.getGreen()) / 2;
			blue = (blue + mix.getBlue()) / 2;
		}

		Color color = new Color(red, green, blue);
		return color;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		selectedColor = ((ColorSquare)e.getSource()).getColor();
		centerPanel.setBackground(selectedColor);
	}

	public Color getSelectedColor() {
		return selectedColor;
	}
}
