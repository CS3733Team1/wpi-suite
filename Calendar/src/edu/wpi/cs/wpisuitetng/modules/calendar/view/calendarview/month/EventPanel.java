package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.month;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.CalendarUtils;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.DateUtils;

public class EventPanel extends EvComPanel {

	private Event event;

	private Color backgroundColor, selectedBackgroundColor;
	private Color textColor, selectedTextColor;

	private boolean isSelected;

	private JLabel eventNameLabel;
	private JLabel eventTimeLabel;

	public EventPanel(Event event, Color backgroundColor) {
		super(false);
		this.event = event;

		this.backgroundColor = backgroundColor;
		this.selectedBackgroundColor = event.getCategory().getColor();
		this.textColor = CalendarUtils.titleNameColor;
		this.selectedTextColor = CalendarUtils.textColor(selectedBackgroundColor);

		this.isSelected = false;

		this.setLayout(new MigLayout("insets 0, gap 0", "0[]push[]0", "0[]0"));

		this.setBackground(backgroundColor);

		eventNameLabel = new JLabel(event.getName());
		eventNameLabel.setForeground(this.textColor);

		eventTimeLabel = new JLabel(DateUtils.timeToString(event.getStartDate()));
		eventTimeLabel.setFont(new Font(eventTimeLabel.getFont().getName(), Font.PLAIN, 8));
		eventTimeLabel.setForeground(this.textColor);

		this.add(eventNameLabel, "gap left 5, wmin 0");
		this.add(eventTimeLabel, "gap right 5");
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;

		if(this.isSelected) {
			this.setBackground(selectedBackgroundColor);
			eventNameLabel.setForeground(selectedTextColor);
			eventTimeLabel.setForeground(selectedTextColor);
		} else {
			this.setBackground(backgroundColor);
			eventNameLabel.setForeground(textColor);
			eventTimeLabel.setForeground(textColor);
		}
	}

	public boolean getSelected()  {return this.isSelected;}

	public Event getEvent() {return this.event;}
}
