package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.month;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;

public class EvComMouseListener implements MouseListener, MouseMotionListener{
	private MonthCalendarView monthView;
	private List<List<MultiDayEventPanel>> multiDayEventPanelLists;

	private Event eventBeingDragged;
	private Commitment commitmentBeingDragged;
	private int initialIndex;
	private int finalIndex;
	
	private boolean dragMultiDay;
	private int daysToLeft;
	private int totalDays;

	public EvComMouseListener(MonthCalendarView monthView) {
		this.monthView = monthView;
		this.multiDayEventPanelLists = new ArrayList<List<MultiDayEventPanel>>();
		monthView.addMouseMotionListener(this);
		monthView.addMouseListener(this);
	}

	public void setMultiDayLists(List<List<MultiDayEventPanel>> multiDayEventPanelLists) {this.multiDayEventPanelLists = multiDayEventPanelLists;};

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getSource() instanceof EvComPanel) {
			EvComPanel evComPanel = (EvComPanel)e.getSource();
			if(evComPanel.isMultiDay()) { // Select all multiday events
				List<MultiDayEventPanel> selectedMultiDayEventList = new ArrayList<MultiDayEventPanel>();
				for(List<MultiDayEventPanel> multiDayEventPanelList: multiDayEventPanelLists) {
					for(int i = 0; i < multiDayEventPanelList.size(); i++) {
						if(multiDayEventPanelList.get(i).equals(evComPanel)) {
							selectedMultiDayEventList = multiDayEventPanelList;
							dragMultiDay = true;
							daysToLeft = i;
							totalDays = multiDayEventPanelList.size();
							break;
						}
					}
				}
				if(evComPanel.isSelected())
					for(EvComPanel multiDayEvent: selectedMultiDayEventList) multiDayEvent.setSelected(false);
				else for(EvComPanel multiDayEvent: selectedMultiDayEventList) multiDayEvent.setSelected(true);
				eventBeingDragged = ((MultiDayEventPanel)evComPanel).getEvent();
			} else {
				dragMultiDay = false;
				
				if(evComPanel.isSelected())
					evComPanel.setSelected(false);
				else evComPanel.setSelected(true);

				if(evComPanel instanceof EventPanel) eventBeingDragged = ((EventPanel)evComPanel).getEvent();
				else commitmentBeingDragged = ((CommitmentPanel)evComPanel).getCommitment();
			}
			initialIndex = -1;
			finalIndex = -1;
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if(eventBeingDragged != null) monthView.setDragData(eventBeingDragged.getCategory().getColor(), eventBeingDragged.getName(), dragMultiDay, daysToLeft, totalDays);
		else if(commitmentBeingDragged != null) monthView.setDragData(commitmentBeingDragged.getCategory().getColor(), commitmentBeingDragged.getName(), dragMultiDay, daysToLeft, totalDays);
		monthView.setDragging(true);
		monthView.updateDragCoor(e.getPoint());
		monthView.repaint();
		int index = monthView.getDayPanels().indexOf(monthView.getComponentAt(e.getPoint()));
		if(initialIndex == -1 && index != -1) {
			initialIndex = index;
			finalIndex = index;
		} else {
			if(index != -1 && index != finalIndex) finalIndex = index;
		}
	}

	public void mouseMoved(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {
		monthView.setDragging(false);
		monthView.repaint();
		if(finalIndex != initialIndex) {
			if(eventBeingDragged != null) {
				eventBeingDragged.getStartDate().setDate(eventBeingDragged.getStartDate().getDate() + (finalIndex - initialIndex));
				eventBeingDragged.getEndDate().setDate(eventBeingDragged.getEndDate().getDate() + (finalIndex - initialIndex));
				
				//UPDATE EVENT
				
				monthView.createEvComPanels();
				monthView.updateEvComs();
			} else if(commitmentBeingDragged != null) {
				commitmentBeingDragged.getDueDate().setDate(commitmentBeingDragged.getDueDate().getDate() + (finalIndex - initialIndex));
				
				//UPDATE COMMITMENT
				
				
				monthView.createEvComPanels();
				monthView.updateEvComs();
			}
		}

		eventBeingDragged = null;
		commitmentBeingDragged = null;
		initialIndex = -1;
		finalIndex = -1;
	}

	// Unused
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mouseClicked(MouseEvent e) {}
}
