package edu.wpi.cs.wpisuitetng.modules.calendar.view.utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class IconMaker {
	public static JLabel makeEventIcon(Color c) {
		BufferedImage event = new BufferedImage(14, 14, BufferedImage.TYPE_INT_ARGB);
		Graphics g = event.getGraphics();
		g.setColor(c);
		g.fillRect(0, 0, event.getWidth(), event.getHeight());
				
		return new JLabel(new ImageIcon(event));
	}

	public static JLabel makeCommitmentIcon(Color c) {
		BufferedImage commitment = new BufferedImage(14, 14, BufferedImage.TYPE_INT_ARGB);
		Graphics g = commitment.getGraphics();
		g.setColor(c);
		g.fillRect(0, 0, commitment.getWidth(), commitment.getHeight());
		
		BufferedImage check = null;
		try {
			check = ImageIO.read(IconMaker.class.getResource("/images/commitCheck.png"));
		} catch (IOException e) {
		}
		g.drawImage(check, 0, 0, null);
		
		return new JLabel(new ImageIcon(commitment));
	}
}
