package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import edu.wpi.cs.wpisuitetng.janeway.modules.JanewayTabModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.CalendarModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.CalendarObjectModel;

public class CalendarView {

	private List<JanewayTabModel> tabs;
	private CalendarModel model;
	private CalendarPanel calendarPanel;
	private CalendarToolBar calendarToolBar;

	public CalendarView(CalendarModel model) {
		this.model = model;

		// this.model.setValue(InitialValue);
		// view should access all data from model to display

		calendarPanel = new CalendarPanel(model);
		calendarToolBar = new CalendarToolBar(model, this);

		tabs = new ArrayList<JanewayTabModel>();

		JanewayTabModel tab = new JanewayTabModel(getName(), new ImageIcon(),
				calendarToolBar, calendarPanel);

		// Add the tab to the list of tabs owned by this module
		tabs.add(tab);

		// Remove later
		calendarPanel.addCalendar(new CalendarObjectModel("Team Calendar"));
		calendarPanel.addCalendar(new CalendarObjectModel("Personal Calendar"));
	}

	public List<JanewayTabModel> getTabs() {
		return tabs;
	}

	public String getName() {
		return "Calendar";
	}
	
	public CalendarToolBar getcalendarToolBar(){
		return this.calendarToolBar;
	}
	
	public CalendarPanel getCalendarPanel(){
		return this.calendarPanel;
	}
}
