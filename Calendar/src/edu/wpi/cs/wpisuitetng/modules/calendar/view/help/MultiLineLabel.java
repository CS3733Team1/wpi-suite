package edu.wpi.cs.wpisuitetng.modules.calendar.view.help;

import java.awt.Insets;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JTextArea;
import javax.swing.UIManager;

public class MultiLineLabel extends JTextArea {
	public MultiLineLabel(String s) {
		super(s);
		this.setFont(UIManager.getFont("Label.font")); 
		this.setEditable(false);
		this.setFocusable(false);
		this.setCursor(null);
		this.setLineWrap(true);
		this.setWrapStyleWord(true);
		this.setMargin(new Insets(0, 0, 0, 0));
		this.setOpaque(false);
		for(MouseListener ml: this.getMouseListeners()) this.removeMouseListener(ml);
		for(MouseMotionListener ml: this.getMouseMotionListeners()) this.removeMouseMotionListener(ml);
	}
}
