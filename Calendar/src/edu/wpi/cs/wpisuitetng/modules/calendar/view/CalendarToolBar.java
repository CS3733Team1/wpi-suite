package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.calendar.controller.AddEventController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.RemoveCommitmentController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.RemoveEventController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.display.DisplayCommitmentController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.display.DisplayEventController;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.CalendarModel;

public class CalendarToolBar extends JPanel {
	private CalendarModel model;
	private CalendarView view;
	
	private JButton addCalendarButton, deleteCalendarButton;
	private JButton addCommitmentButton, deleteCommitmentButton;
	private JButton addEventButton, deleteEventButton;
	
	
	public CalendarToolBar(CalendarModel model, CalendarView view) {
		
		this.model = model;
		this.view = view;
		
		JPanel addDeletePanel = new JPanel();
		
		try {
			addCalendarButton = new JButton("<html>Create New<br/>Calendar</html>",
					new ImageIcon(ImageIO.read(getClass().getResource("/images/add_cal.png"))));
			deleteCalendarButton = new JButton("<html>Delete<br/>Calendar</html>",
					new ImageIcon(ImageIO.read(getClass().getResource("/images/delete_cal.png"))));
			
			addCommitmentButton = new JButton("<html>New<br/>Commitment</html>",
					new ImageIcon(ImageIO.read(getClass().getResource("/images/add_commitment.png"))));
			deleteCommitmentButton = new JButton("<html>Delete<br/>Commitment</html>",
					new ImageIcon(ImageIO.read(getClass().getResource("/images/delete_commitment.png"))));
			
			addEventButton = new JButton("<html>New<br/>Event</html>",
					new ImageIcon(ImageIO.read(getClass().getResource("/images/add_event.png"))));
			deleteEventButton = new JButton("<html>Delete<br/>Event</html>",
					new ImageIcon(ImageIO.read(getClass().getResource("/images/delete_event.png"))));
		} catch (IOException e) {}
		
		this.setLayout(new BorderLayout());
		
		addEventButton.addActionListener(new AddEventController(view, model));
		deleteEventButton.addActionListener(new RemoveEventController(view, model));
		addCommitmentButton.addActionListener(new DisplayCommitmentController(view, model));
		deleteCommitmentButton.addActionListener(new RemoveCommitmentController(view, model));
		

		//addDeletePanel.add(addCalendarButton);
		//addDeletePanel.add(deleteCalendarButton);
		addDeletePanel.add(addCommitmentButton);
		addDeletePanel.add(deleteCommitmentButton);
		addDeletePanel.add(addEventButton);
		addDeletePanel.add(deleteEventButton);
		
		this.add(addDeletePanel, BorderLayout.CENTER);
	}
}
