package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.calendar.controller.AddCommitmentController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.RemoveCommitmentController;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.CalendarModel;

public class CalendarToolBar extends JPanel {
	
	private JButton addCommitment, removeCommitment;
	private CalendarModel model;
	private CalendarView view;
	
	public CalendarToolBar(CalendarModel model, CalendarView view) {
		
		this.model = model;
		this.view = view;
		
		addCommitment = new JButton("Add Commitment");
		removeCommitment = new JButton("Delete Commitment");
		
		this.setLayout(new FlowLayout());
		this.add(new JButton("Add Calendar"));
		this.add(new JButton("Delete Calendar"));
		this.add(new JButton("Add Event"));
		this.add(new JButton("Delete Event"));
		
		addCommitment.addActionListener(new AddCommitmentController(view, model));
		removeCommitment.addActionListener(new RemoveCommitmentController(view, model));
		
		this.add(addCommitment);
		this.add(removeCommitment);
	}
}
