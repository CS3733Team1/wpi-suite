package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.month;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.event.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.CalendarUtils;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.DateUtils;

public class EventPanel extends EvComPanel {

	private Event event;
	
	private JLabel eventNameLabel;
	private JLabel eventTimeLabel;

	public EventPanel(Event event) {
		super(false, false);
		this.event = event;

		this.backgroundColor = Color.white;
		this.selectedBackgroundColor = event.getCategory().getColor();
		this.textColor = CalendarUtils.titleNameColor;
		this.selectedTextColor = CalendarUtils.textColor(selectedBackgroundColor);

		this.setLayout(new MigLayout("insets 0, gap 0", "0[]push[]0", "0[]0"));

		eventNameLabel = new JLabel(event.getName());

		eventTimeLabel = new JLabel(DateUtils.timeToString(event.getStartDate()));
		eventTimeLabel.setFont(new Font(eventTimeLabel.getFont().getName(), Font.PLAIN, 8));
		
		this.setSelected(false);

		this.add(eventNameLabel, "gap left 5, wmin 0");
		this.add(eventTimeLabel, "gap right 5");
	}

	private void update() {
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
	
	public void setSelected(boolean isSelected) {		
		this.isSelected = isSelected;
		
		update();
	}

	public Event getEvent() {return this.event;}

	public void setBackgroundColor(Color background) {
		this.backgroundColor = background;
		this.update();
	}
}
