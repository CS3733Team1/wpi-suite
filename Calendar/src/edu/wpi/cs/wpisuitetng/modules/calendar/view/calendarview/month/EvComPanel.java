package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.month;

import javax.swing.JPanel;

public abstract class EvComPanel extends JPanel {
	private boolean isMultiDay;
	
	public EvComPanel(boolean isMultiDay) {
		this.isMultiDay = isMultiDay;
	}
	
	public boolean isMultiDay() {
		return this.isMultiDay;
	}
}
