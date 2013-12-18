package edu.wpi.cs.wpisuitetng.modules.calendar.view.scheduledevent;

import java.awt.Point;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.scheduledevent.ScheduledEvent;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.scheduledevent.ScheduledEventListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.list.ListItemListener;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.list.SRList;

public class ScheduledEventSubTabPanel extends JPanel implements ListItemListener<ScheduledEvent> {

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
	public void itemDoubleClicked(ScheduledEvent listObject) {
		ScheduledEventTabPanel scheduledEventPanel = new ScheduledEventTabPanel(listObject);
		ImageIcon miniScheduledEventIcon = new ImageIcon();
		try {
			miniScheduledEventIcon = new ImageIcon(ImageIO.read(getClass().getResource("/images/scheduler_tabSize.png")));
		} catch (IOException exception) {}
		calendarPanel.addTab("Add Scheduled Event", miniScheduledEventIcon , scheduledEventPanel);
		calendarPanel.setSelectedComponent(scheduledEventPanel);
	}

	@Override
	public void itemFocused(ScheduledEvent listObject) {}

	@Override
	public void itemRightClicked(ScheduledEvent listObject, Point p) {}
}
