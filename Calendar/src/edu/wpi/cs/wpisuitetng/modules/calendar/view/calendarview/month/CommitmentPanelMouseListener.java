package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.month;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class CommitmentPanelMouseListener implements MouseListener, MouseMotionListener {
	private MonthCalendarView monthView;
	private CommitmentPanel panelBeingDragged;
	
	public CommitmentPanelMouseListener(MonthCalendarView monthView) {
		this.monthView = monthView;
		monthView.addMouseMotionListener(this);
	}
	
	
	@Override
	public void mousePressed(MouseEvent e) {
		CommitmentPanel commitmentPanel = (CommitmentPanel)e.getSource();
		if(commitmentPanel.isSelected()) commitmentPanel.setSelected(false);
		else commitmentPanel.setSelected(true);
		
		panelBeingDragged = commitmentPanel;
	}
	

	@Override
	public void mouseDragged(MouseEvent e) {
		if(monthView.getComponentAt(e.getPoint()) instanceof DayPanel) {
			int index = monthView.getDayPanels().indexOf(monthView.getComponentAt(e.getPoint()));
			System.out.println("Currently in DayPanel at index: " + index);
		} else {
			System.out.println("Not in a DayPanel????");
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		if(monthView.getComponentAt(e.getPoint()) instanceof DayPanel) {
			int index = monthView.getDayPanels().indexOf(monthView.getComponentAt(e.getPoint()));
			System.out.println("Currently in DayPanel at index: " + index);
		} else {
			System.out.println("Not in a DayPanel????");
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// Save event!
		panelBeingDragged = null;
	}
	
	// Unused
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mouseClicked(MouseEvent e) {}
	
}
