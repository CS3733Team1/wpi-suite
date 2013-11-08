package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class CalendarToolBar extends JPanel {
	public CalendarToolBar() {
		this.setLayout(new FlowLayout());
		this.add(new JButton("Add Calendar"));
		this.add(new JButton("Delete Calendar"));
		this.add(new JButton("Add Event"));
		this.add(new JButton("Delete Event"));
		this.add(new JButton("Add Commitment"));
		this.add(new JButton("Delete Commitment"));
	}
}
