package edu.wpi.cs.wpisuitetng.modules.calendar.view.scheduledevent;

import java.awt.Point;
import java.util.List;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.list.SRList;

public class ScheduledEventSubTabPanel extends JPanel {

	private SRList<ScheduledEvent> scheduledEventList;

	private CalendarPanel calendarPanel;

	public ScheduledEventSubTabPanel(CalendarPanel calendarPanel) {
		this.setLayout(new MigLayout("fill"));
		this.calendarPanel = calendarPanel;

		scheduledEventList = new SRList<ScheduledEvent>(ScheduledEventListModel.getScheduledEventListModel());
		scheduledEventList.setListItemRenderer(new ScheduledEventListItemRenderer());
		scheduledEventList.addListItemListener(this);

		this.add(scheduledEventList, "grow");
	}

	public List<ScheduledEvent> getSelectedScheduledEvents() {
		return scheduledEventList.getSelectedItems();
	}

	@Override
	public void itemsSelected(List<ScheduledEvent> listObjects) {}

	@Override
	public void itemDoubleClicked(ScheduledEvent listObject) {}

	@Override
	public void itemFocused(ScheduledEvent listObject) {}

	@Override
	public void itemRightClicked(ScheduledEvent listObject, Point p) {}
}
