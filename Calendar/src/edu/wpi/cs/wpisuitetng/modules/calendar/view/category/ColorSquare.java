package edu.wpi.cs.wpisuitetng.modules.calendar.view.category;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.JButton;

public class ColorSquare extends JButton {

	private Color color;
	
	public ColorSquare(Color color) {
		this.color = color;
		this.setMargin(new Insets(0, 0, 0, 0));
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