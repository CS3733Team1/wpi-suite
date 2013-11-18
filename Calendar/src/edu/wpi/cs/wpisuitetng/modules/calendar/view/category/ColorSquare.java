package edu.wpi.cs.wpisuitetng.modules.calendar.view.category;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JToggleButton;

public class ColorSquare extends JToggleButton {

	private Color color;
	
	public ColorSquare(Color color) {
		this.color = color;
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(color);
        g.fillRect(0, 0, getSize().width, getSize().height);
	}

	public Color getColor() {
		return color;
	}
}
