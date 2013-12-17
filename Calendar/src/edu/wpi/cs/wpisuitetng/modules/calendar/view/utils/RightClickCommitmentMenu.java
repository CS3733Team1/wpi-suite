package edu.wpi.cs.wpisuitetng.modules.calendar.view.utils;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import edu.wpi.cs.wpisuitetng.modules.calendar.controller.commitment.DeleteCommitmentController;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.commitment.CommitmentTabPanel;

public class RightClickCommitmentMenu extends JPopupMenu implements MouseListener {

	private List<Commitment> commitments;
	private CalendarPanel calendarPanel;

	private JMenuItem deleteButton;
	private JMenuItem editButton;


	public RightClickCommitmentMenu(List<Commitment> c, CalendarPanel calendarPanel) {
		this.calendarPanel = calendarPanel;
		commitments = c;
		deleteButton = new JMenuItem ("Delete");
		editButton = new JMenuItem ("Edit");
		this.add(deleteButton);
		this.add(editButton);

		deleteButton.addMouseListener(this);
		editButton.addMouseListener(this);
	}

	public void openUpdateCommitmentTab(Commitment commitment) {
		CommitmentTabPanel commitmentPanel = new CommitmentTabPanel(commitment);
		ImageIcon miniCommitmentIcon = new ImageIcon();
		try {
			miniCommitmentIcon = new ImageIcon(ImageIO.read(RightClickCommitmentMenu.class.getResource("/images/commitment.png")));
		} catch (IOException exception) {}
		calendarPanel.addTab("Update Commitment", miniCommitmentIcon, commitmentPanel);
		calendarPanel.setSelectedComponent(commitmentPanel);	
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getSource().equals(deleteButton)) {
			DeleteCommitmentController.deleteCommitments(commitments);
			System.out.println("mouse released on Delete Butotn");
		} else if(e.getSource().equals(editButton)) {
			for(Commitment commitment : commitments) openUpdateCommitmentTab(commitment);
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
