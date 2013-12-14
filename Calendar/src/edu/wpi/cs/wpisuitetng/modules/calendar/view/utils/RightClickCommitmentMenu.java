package edu.wpi.cs.wpisuitetng.modules.calendar.view.utils;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import edu.wpi.cs.wpisuitetng.modules.calendar.controller.commitment.DeleteCommitmentController;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.commitment.CommitmentListPanel;

public class RightClickCommitmentMenu extends JPopupMenu implements MouseListener{

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
		deleteButton.addMouseListener(new MouseListener(){
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				DeleteCommitmentController.deleteCommitments(commitments);;
				System.out.println("mouse released on Delete Butotn");
			}
		});
		
		editButton.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			
			/**
			 * When the mouse is released on the Edit Button, Edit all selected commitments
			 */
			@Override
			public void mouseReleased(MouseEvent e) {
				//iterate through all commitments in this edit menu's list of commitments
				for(Commitment commitment : commitments){
					commitmentListPanel.setSelectedCommitment(commitment);	//set it as the selected commitment in the CommitmentListPanel
					commitmentListPanel.openUpdateCommitmentTabPanel();		//call the panel's method to edit the selected commitment
				}
				System.out.println("Mouse Released on edit button");
			}
		});
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
