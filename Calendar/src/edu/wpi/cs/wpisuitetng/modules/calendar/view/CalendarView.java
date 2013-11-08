package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import edu.wpi.cs.wpisuitetng.janeway.modules.JanewayTabModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.CalendarModel;

public class CalendarView {

	private List<JanewayTabModel> tabs;
	private CalendarModel model;
	private CalendarPanel calendarPanel;
	private CalendarMenuBar calendarMenuBar;

	public CalendarView(CalendarModel model) {
		this.model = model;

		calendarPanel = new CalendarPanel();
		calendarMenuBar = new CalendarMenuBar();

		tabs = new ArrayList<JanewayTabModel>();

		JanewayTabModel tab = new JanewayTabModel(getName(), new ImageIcon(),
				calendarMenuBar, calendarPanel);

		// Add the tab to the list of tabs owned by this module
		tabs.add(tab);
	}

	public List<JanewayTabModel> getTabs() {
		return tabs;
	}

	public String getName() {
		return "Calendar";
	}
}