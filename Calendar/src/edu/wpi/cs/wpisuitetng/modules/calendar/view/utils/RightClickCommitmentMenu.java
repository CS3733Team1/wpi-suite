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
import edu.wpi.cs.wpisuitetng.modules.calendar.view.commitment.CommitmentListPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.commitment.CommitmentTabPanel;

public class RightClickCommitmentMenu extends JPopupMenu{

	private List<Commitment> commitments;
	
	private JMenuItem deleteButton;
	private JMenuItem editButton;
	
	private CommitmentListPanel commitmentListPanel;

	public RightClickCommitmentMenu(List<Commitment> c, final CommitmentListPanel commitmentListPanel){
		commitments = c;
		deleteButton = new JMenuItem ("Delete");
		editButton = new JMenuItem ("Edit");
		this.add(deleteButton);
		this.add(editButton);
		
		//Mouse actions to perform for the delete button
		deleteButton.addMouseListener(new MouseListener(){
			
			@Override
			public void mouseClicked(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {
				DeleteCommitmentController.deleteCommitments(commitments);;
				System.out.println("mouse released on Delete Butotn");
			}
		});
		
		//Mouse actions to perform for the edit button
		editButton.addMouseListener(new MouseListener(){
			
			/**
			 * When the mouse is released on the Edit Button, Edit all selected commitments in new CommitmentTabs
			 */
			@Override
			public void mouseReleased(MouseEvent e) {
				//iterate through all commitments in this edit menu's list of selected commitments
				for(Commitment commitment : commitments){
					commitmentListPanel.setSelectedCommitment(commitment);	//set it as the selected commitment in the CommitmentListPanel
					commitmentListPanel.openUpdateCommitmentTabPanel();		//call the panel's method to edit the selected commitment
				}
				System.out.println("Mouse Released on edit button");
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
		});
	}

	public static void openUpdateCommitmentTab(CalendarPanel calendarPanel, Commitment commitment) {
		CommitmentTabPanel commitmentPanel = new CommitmentTabPanel(commitment);
		ImageIcon miniCommitmentIcon = new ImageIcon();
		try {
			miniCommitmentIcon = new ImageIcon(ImageIO.read(RightClickCommitmentMenu.class.getResource("/images/commitment.png")));
		} catch (IOException exception) {}
		calendarPanel.addTab("Update Commitment", miniCommitmentIcon, commitmentPanel);
		calendarPanel.setSelectedComponent(commitmentPanel);	
	}

}
