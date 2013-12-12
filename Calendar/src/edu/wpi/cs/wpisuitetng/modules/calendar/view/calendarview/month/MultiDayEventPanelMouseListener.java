package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.month;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class MultiDayEventPanelMouseListener implements MouseListener {

	private List<MultiDayEventPanel> multidayEvents;
	
	public MultiDayEventPanelMouseListener(List<MultiDayEventPanel> multidayEvents) {
		this.multidayEvents = multidayEvents;
		for(MultiDayEventPanel multiDayEventPanel: multidayEvents) multiDayEventPanel.addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		for(MultiDayEventPanel eventPanel: multidayEvents) {
			if(eventPanel.getSelected()) eventPanel.setSelected(false);
			else eventPanel.setSelected(true);
		}
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
