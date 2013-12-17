package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.month;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.calendar.controller.commitment.UpdateCommitmentController;
import edu.wpi.cs.wpisuitetng.modules.calendar.controller.event.UpdateEventController;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarTabPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.MainView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.commitment.CommitmentTabPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.event.EventTabPanel;

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

	private boolean evComPanelPressed;

	public EvComMouseListener(MonthCalendarView monthView) {
		this.monthView = monthView;
		this.multiDayEventPanelLists = new ArrayList<List<MultiDayEventPanel>>();
		evComPanelPressed = false;
		monthView.addMouseMotionListener(this);
		monthView.addMouseListener(this);
	}

	public void setMultiDayLists(List<List<MultiDayEventPanel>> multiDayEventPanelLists) {this.multiDayEventPanelLists = multiDayEventPanelLists;};

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getSource() instanceof EvComPanel) {
			evComPanelPressed = true;
			EvComPanel evComPanel = (EvComPanel)e.getSource();
			boolean isSelected = evComPanel.isSelected();
			if(!e.isControlDown()) monthView.clearSelection();
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
				if(isSelected) {
					for(EvComPanel multiDayEvent: selectedMultiDayEventList) multiDayEvent.setSelected(false);
					monthView.selectedMultiDayEvents.remove(selectedMultiDayEventList);
				} else {
					for(EvComPanel multiDayEvent: selectedMultiDayEventList) multiDayEvent.setSelected(true);
					monthView.selectedMultiDayEvents.add(selectedMultiDayEventList);
				}
				eventBeingDragged = ((MultiDayEventPanel)evComPanel).getEvent();
			} else {
				dragMultiDay = false;

				if(evComPanel instanceof EventPanel) {
					EventPanel eventPanel = (EventPanel)evComPanel;
					if(isSelected) {
						eventPanel.setSelected(false);
						monthView.selectedEvents.remove(eventPanel);
					} else {
						eventPanel.setSelected(true);
						monthView.selectedEvents.add(eventPanel);
					}
					eventBeingDragged = eventPanel.getEvent();
				} else {
					CommitmentPanel commitmentPanel = (CommitmentPanel)evComPanel;
					if(isSelected) {
						commitmentPanel.setSelected(false);
						monthView.selectedCommitmentPanels.remove(commitmentPanel);
					} else {
						commitmentPanel.setSelected(true);
						monthView.selectedCommitmentPanels.add(commitmentPanel);
					}
					commitmentBeingDragged = commitmentPanel.getCommitment();
				}
			}
			initialIndex = -1;
			finalIndex = -1;
		} else {
			monthView.clearSelection();
			evComPanelPressed = false;
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if(evComPanelPressed) {
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
	}

	public void mouseMoved(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(evComPanelPressed) {
			monthView.setDragging(false);
			monthView.repaint();
			if(finalIndex != initialIndex) {
				monthView.clearSelection();
				if(eventBeingDragged != null) {
					eventBeingDragged.getStartDate().setDate(eventBeingDragged.getStartDate().getDate() + (finalIndex - initialIndex));
					eventBeingDragged.getEndDate().setDate(eventBeingDragged.getEndDate().getDate() + (finalIndex - initialIndex));

					//Save updated event on the server
					new UpdateEventController(eventBeingDragged);

					monthView.createEvComPanels();
					monthView.updateEvComs();
				} else if(commitmentBeingDragged != null) {
					commitmentBeingDragged.getDueDate().setDate(commitmentBeingDragged.getDueDate().getDate() + (finalIndex - initialIndex));

					//Save updated commitment on the server
					new UpdateCommitmentController(commitmentBeingDragged);

					monthView.createEvComPanels();
					monthView.updateEvComs();
				}

			}

			eventBeingDragged = null;
			commitmentBeingDragged = null;
			initialIndex = -1;
			finalIndex = -1;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getClickCount() == 2) {
			if(monthView.getComponentAt(e.getPoint()) instanceof DayPanel) {
				DayPanel day = (DayPanel)monthView.getComponentAt(e.getPoint());
				Calendar clickedDay = day.getDate();
				CalendarTabPanel tab = (CalendarTabPanel)(monthView.getParent().getParent());

				tab.displayDayView();
				tab.setCalendarViewDate(clickedDay);
			}
			else if(e.getSource() instanceof EventPanel) 
			{
				Event event = ((EventPanel)e.getSource()).getEvent();
				EventTabPanel editEventPanel = new EventTabPanel(event);
				MainView.getCurrentCalendarPanel().addTab("Update Event", editEventPanel);
				MainView.getCurrentCalendarPanel().setSelectedComponent(editEventPanel);
				
			} 
			else if(e.getSource() instanceof CommitmentPanel) 
			{
				Commitment commitment = ((CommitmentPanel)e.getSource()).getCommitment();
				CommitmentTabPanel editCommitmentPanel = new CommitmentTabPanel(commitment);
				MainView.getCurrentCalendarPanel().addTab("Update Commitmement", editCommitmentPanel);
				MainView.getCurrentCalendarPanel().setSelectedComponent(editCommitmentPanel);
			} 
			else if(e.getSource() instanceof MultiDayEventPanel) 
			{
				Event event = ((MultiDayEventPanel)e.getSource()).getEvent();
				EventTabPanel editEventPanel = new EventTabPanel(event);
				MainView.getCurrentCalendarPanel().addTab("Update Event", editEventPanel);
				MainView.getCurrentCalendarPanel().setSelectedComponent(editEventPanel);
			}
		}
	}

	// Unused
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
}
