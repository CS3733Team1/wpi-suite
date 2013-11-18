package edu.wpi.cs.wpisuitetng.modules.calendar.view.event;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;

public class EventListCellRenderer extends JPanel implements ListCellRenderer<Event> {

	private JLabel eventName;
	private JLabel startDate;
	private JLabel endDate;

	public EventListCellRenderer() {
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		eventName = new JLabel();
		startDate = new JLabel();
		endDate = new JLabel();
		this.add(eventName);
		this.add(startDate);
		this.add(endDate);
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends Event> list, Event event,
			int index, boolean isSelected, boolean cellHasFocus) {
		eventName.setText("Name: " + event.getName());
		startDate.setText("Date Start: " + event.getStartDate().toString());
		endDate.setText("Date End: " + event.getEndDate().toString());

		final Color background = UIManager.getDefaults().getColor("List.background");
		final Color foreground = UIManager.getDefaults().getColor("List.foreground");
		final Color selectionBackground = UIManager.getDefaults().getColor("List.selectionBackground");
		final Color selectionForeground = UIManager.getDefaults().getColor("List.selectionForeground");

		this.setBackground(isSelected ? selectionBackground : background);
		this.setForeground(isSelected ? selectionForeground : foreground);

		this.setBorder(new LineBorder(new Color(0, 0, 0)));

		return this;
	}

}
