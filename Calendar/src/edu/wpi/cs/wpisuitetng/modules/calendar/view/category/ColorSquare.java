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
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.border.MatteBorder;

public class ColorSquare extends JButton {

	private Color color;
	
	public ColorSquare(Color color) {
		this.color = color;
		this.setMargin(new Insets(0, 0, 0, 0));
		this.setBorder(new MatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(this.color);
        g.fillRect(0, 0, getSize().width, getSize().height);
	}

	public Color getColor() {
		return color;
	}
}
