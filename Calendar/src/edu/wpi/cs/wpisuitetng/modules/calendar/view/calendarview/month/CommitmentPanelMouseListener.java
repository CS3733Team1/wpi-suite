package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.month;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CommitmentPanelMouseListener implements MouseListener{
	@Override
	public void mouseClicked(MouseEvent e) {
		CommitmentPanel commitmentPanel = (CommitmentPanel)e.getSource();
		if(commitmentPanel.getSelected()) commitmentPanel.setSelected(false);
		else commitmentPanel.setSelected(true);
	}
	
	// Unused
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
}
