package edu.wpi.cs.wpisuitetng.modules.calendar.view.utils;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import edu.wpi.cs.wpisuitetng.modules.calendar.controller.commitment.DeleteCommitmentController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.event.DeleteEventController;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.commitment.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.event.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.commitment.CommitmentTabPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.event.EventTabPanel;

public class RightClickMenu extends JPopupMenu implements MouseListener {
	private final static Logger LOGGER = Logger.getLogger(RightClickMenu.class.getName());

	private List<Commitment> commitments;
	private List<Event> events;
	
	private CalendarPanel calendarPanel;

	private JMenuItem deleteButton;
	private JMenuItem editButton;
	
	public RightClickMenu(List<Event> e, List<Commitment> c, CalendarPanel calendarPanel) {
		this.calendarPanel = calendarPanel;
		events = e;
		commitments = c;

		editButton = new JMenuItem ("Edit");
		deleteButton = new JMenuItem ("Delete");
		this.add(editButton);
		this.add(deleteButton);

		deleteButton.addMouseListener(this);
		editButton.addMouseListener(this);
	}

	public void openUpdateCommitmentTab(Commitment commitment) {
		CommitmentTabPanel commitmentPanel = new CommitmentTabPanel(commitment);
		ImageIcon miniCommitmentIcon = new ImageIcon();
		try {
			miniCommitmentIcon = new ImageIcon(ImageIO.read(RightClickMenu.class.getResource("/images/commitment.png")));
		} catch (IOException exception) {
			LOGGER.log(Level.WARNING, "/images/commitment.png not found.", exception);
		}
		calendarPanel.addTab("Update Commitment", miniCommitmentIcon, commitmentPanel);
		calendarPanel.setSelectedComponent(commitmentPanel);	
	}
	
	public void openUpdateEventTab(Event event) {
		EventTabPanel eventPanel = new EventTabPanel(event);
		ImageIcon miniEventIcon = new ImageIcon();
		try {
			miniEventIcon = new ImageIcon(ImageIO.read(RightClickMenu.class.getResource("/images/event.png")));
		} catch (IOException exception) {
			LOGGER.log(Level.WARNING, "/images/event.png not found.", exception);
		}
		calendarPanel.addTab("Update Event", miniEventIcon, eventPanel);
		calendarPanel.setSelectedComponent(eventPanel);	
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getSource().equals(deleteButton)) {
			DeleteCommitmentController.deleteCommitmentList(commitments);
			DeleteEventController.deleteEventList(events);
		} else if(e.getSource().equals(editButton)) {
			for(Event event: events) openUpdateEventTab(event);
			for(Commitment commitment: commitments) openUpdateCommitmentTab(commitment);
		}
	}

	//Unused
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
}
