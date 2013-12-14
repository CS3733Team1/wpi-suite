package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.month;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class EventPanelMouseListener implements MouseListener{
	@Override
	public void mousePressed(MouseEvent e) {
		EventPanel eventPanel = (EventPanel)e.getSource();
		if(eventPanel.isSelected()) eventPanel.setSelected(false);
		else eventPanel.setSelected(true);
	}
	
	// Unused
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
}
