package edu.wpi.cs.wpisuitetng.modules.calendar.controller;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarTabPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarToolBar;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.category.CategoryTabPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.commitment.CommitmentTabPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.event.EventTabPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.filter.FilterTabPanel;

public class ChangeToolBarController implements ChangeListener {

	private CalendarToolBar toolbar;
	private CalendarPanel calendarPanel;

	public ChangeToolBarController(CalendarToolBar toolbar, CalendarPanel calendarPanel) {
		this.toolbar = toolbar;
		this.calendarPanel = calendarPanel;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if(calendarPanel.getSelectedComponent() instanceof CalendarTabPanel) {
			toolbar.setToolBarCalendarTab();
		} else if(calendarPanel.getSelectedComponent() instanceof CommitmentTabPanel ||
				calendarPanel.getSelectedComponent() instanceof EventTabPanel) {
			toolbar.setToolBarEventCommitment();
		} else if(calendarPanel.getSelectedComponent() instanceof CategoryTabPanel) {
			toolbar.setToolBarCategory();
		} else if(calendarPanel.getSelectedComponent() instanceof FilterTabPanel) {
			toolbar.setToolBarFilter();
		}
	}
}
