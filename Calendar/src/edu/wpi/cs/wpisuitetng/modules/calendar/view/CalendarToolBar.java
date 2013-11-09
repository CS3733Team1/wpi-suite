package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.calendar.controller.AddCommitmentController;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.CalendarModel;

public class CalendarToolBar extends JPanel {
	
	private JButton addCommitment;
	private CalendarModel model;
	private CalendarView view;
	
	public CalendarToolBar(CalendarModel model, CalendarView view) {
		
		this.model = model;
		this.view = view;
		
		addCommitment = new JButton("Add Commitmentz");
		
		this.setLayout(new FlowLayout());
		this.add(new JButton("Add Calendar"));
		this.add(new JButton("Delete Calendar"));
		this.add(new JButton("Add Event"));
		this.add(new JButton("Delete Event"));
		this.add(new JButton("Delete Commitment"));
		
		addCommitment.addActionListener(new AddCommitmentController(view, model));
		
		
		this.add(addCommitment);
	}
}
