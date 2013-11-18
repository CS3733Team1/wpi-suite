package edu.wpi.cs.wpisuitetng.modules.calendar.view.category;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JPanel;

public class ColorSwatch extends JPanel implements ActionListener {
	private Color selectedColor;
	JPanel centerPanel;

	public ColorSwatch() {
		selectedColor = Color.BLACK;
		this.setMinimumSize(new Dimension(100, 100));
		this.setMaximumSize(new Dimension(400, 400));
		this.setPreferredSize(new Dimension(200, 200));
		this.setLayout(new GridLayout(3, 3));
		
		Color colors[] = {Color.BLACK, Color.RED, Color.GREEN, Color.BLUE, Color.CYAN, Color.MAGENTA, Color.YELLOW, Color.WHITE};
		
		for(int i = 0; i < 4; i++) {
			JPanel p = new JPanel(new GridLayout(2, 2));
			for(int j = 0; j < 4; j++) {
				ColorSquare cs = new ColorSquare(generateRandomColor(colors[i]));
				cs.addActionListener(this);
				p.add(cs);
			}
			this.add(p);
		}
		centerPanel = new JPanel();
		centerPanel.setBackground(selectedColor);
		centerPanel.setForeground(selectedColor);
		this.add(centerPanel);
		for(int i = 4; i < 8; i++) {
			JPanel p = new JPanel(new GridLayout(2, 2));
			for(int j = 0; j < 4; j++) {
				ColorSquare cs = new ColorSquare(generateRandomColor(colors[i]));
				cs.addActionListener(this);
				p.add(cs);
			}
			this.add(p);
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
		centerPanel.setForeground(selectedColor);
	}

	public Color getSelectedColor() {
		return selectedColor;
	}
}
