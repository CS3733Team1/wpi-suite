package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.month;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.CalendarUtils;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.DateUtils;

public class MultiDayEventPanel extends EvComPanel {

	private Event event;

	private Color backgroundColor, selectedBackgroundColor;
	private Color textColor, selectedTextColor;

	private boolean isSelected;

	private JLabel eventNameLabel;
	private JLabel eventTimeLabel;

	private int textType;

	public MultiDayEventPanel(Event event, int textType) {
		super(true);
		this.event = event;
		this.selectedBackgroundColor = event.getCategory().getColor();
		this.backgroundColor = CalendarUtils.blend(selectedBackgroundColor, Color.white, (float) 0.4);
		this.textColor = CalendarUtils.titleNameColor;
		this.selectedTextColor = CalendarUtils.textColor(selectedBackgroundColor);
		
		this.isSelected = false;
		this.textType = textType;

		this.setLayout(new MigLayout("insets 0, gap 0", "0[]push[]0", "0[]0"));

		this.setBackground(backgroundColor);

		eventNameLabel = new JLabel("");

		eventTimeLabel = new JLabel("");
		eventTimeLabel.setFont(new Font(eventTimeLabel.getFont().getName(), Font.PLAIN, 8));

		switch(textType) {
		case 0:
			eventNameLabel.setText("empty");
			break;
		case 1:
			eventNameLabel.setText(event.getName());
			eventTimeLabel.setText(DateUtils.timeToString(event.getStartDate()));
			break;
		case 2:
			eventNameLabel.setText(event.getName());
			break;
		case 3:
			eventNameLabel.setText("empty");
			eventTimeLabel.setText(DateUtils.timeToString(event.getEndDate()));
			break;
		}

		this.setSelected(false);
		
		this.add(eventNameLabel, "gap left 5, wmin 0");
		this.add(eventTimeLabel, "gap right 5");
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;

		if(this.isSelected) {
			this.setBackground(selectedBackgroundColor);
			if(textType == 0) {
				eventNameLabel.setForeground(selectedBackgroundColor);
				eventTimeLabel.setForeground(selectedBackgroundColor);
			} else if (textType == 3) {
				eventNameLabel.setForeground(selectedBackgroundColor);
				eventTimeLabel.setForeground(selectedTextColor);
			} else {
				eventNameLabel.setForeground(selectedTextColor);
				eventTimeLabel.setForeground(selectedTextColor);
			}
		} else {
			this.setBackground(backgroundColor);
			if(textType == 0) {
				eventNameLabel.setForeground(backgroundColor);
				eventTimeLabel.setForeground(backgroundColor);
			} else if (textType == 3) {
				eventNameLabel.setForeground(backgroundColor);
				eventTimeLabel.setForeground(textColor);
			} else {
				eventNameLabel.setForeground(textColor);
				eventTimeLabel.setForeground(textColor);
			}
		}
	}

	public boolean getSelected()  {return this.isSelected;}

	public Event getEvent() {return this.event;}
}
