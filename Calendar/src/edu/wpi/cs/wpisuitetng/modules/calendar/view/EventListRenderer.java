package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;

public class EventListRenderer extends JPanel implements ListCellRenderer {

	private JLabel eventName;
	private JLabel startDate;
	private JLabel endDate;
	
	public EventListRenderer() {
		this.setLayout(new BorderLayout());
		eventName = new JLabel();
		startDate = new JLabel();
		endDate = new JLabel();
		this.add(eventName, BorderLayout.NORTH);
		this.add(startDate, BorderLayout.CENTER);
		this.add(endDate, BorderLayout.SOUTH);
	}
	
	@Override
	public Component getListCellRendererComponent(JList list, Object object,
			int index, boolean isSelected, boolean cellHasFocus) {
		
		Event event = (Event)object;
		
		eventName.setText("Name: " + event.getName());
		startDate.setText("Start: " + event.getStartDate().toString());
		startDate.setText("End: " + event.getStartDate().toString());
		
		final Color background = UIManager.getDefaults().getColor("List.background");
		final Color foreground = UIManager.getDefaults().getColor("List.foreground");
		final Color selectionBackground = UIManager.getDefaults().getColor("List.selectionBackground");
		final Color selectionForeground = UIManager.getDefaults().getColor("List.selectionForeground");
		
		this.setBackground(isSelected ? selectionBackground : background);
		this.setForeground(isSelected ? selectionForeground : foreground);
		
		return this;
	}
}
