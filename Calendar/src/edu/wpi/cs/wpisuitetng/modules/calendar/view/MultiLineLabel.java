package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.awt.AWTEvent;
import java.awt.Insets;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;

import javax.swing.JTextArea;
import javax.swing.UIManager;

public class MultiLineLabel extends JTextArea {
	public MultiLineLabel(String s) {
		super(s);
		this.setFont(UIManager.getFont("Label.font")); 
		this.setEditable(false);
		this.setFocusable(false);
		this.setCursor(null);
		this.setDragEnabled(false);
		this.setLineWrap(true);
		this.setWrapStyleWord(true);
		this.setMargin(new Insets(0, 0, 0, 0));
		this.setOpaque(false);
	}

	@Override
	public synchronized void addMouseListener(MouseListener l) {}

	@Override
	public synchronized void addMouseMotionListener(MouseMotionListener l) {}

	@Override
	public synchronized void addMouseWheelListener(MouseWheelListener l) {}

	@Override
	public void addNotify() {
		disableEvents(AWTEvent.MOUSE_EVENT_MASK | 
				AWTEvent.MOUSE_MOTION_EVENT_MASK | 
				AWTEvent.MOUSE_WHEEL_EVENT_MASK);
		super.addNotify();
	}
}
