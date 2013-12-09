package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.month;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.DateUtils;

public class MultiDayEventPanel extends JPanel {
	
	private Event event;
	
	private Color backgroundColor, selectedBackgroundColor;
	private Color textColor, selectedTextColor;
	
	private boolean isSelected;
	
	private JLabel eventNameLabel;
	private JLabel eventTimeLabel;
	
	private int index;
	
	public MultiDayEventPanel(int index, Event event, int textType, Color backgroundColor, Color selectedBackgroundColor, Color textColor, Color selectedTextColor) {
		this.index = index;
		this.event = event;
		this.backgroundColor = backgroundColor;
		this.selectedBackgroundColor = selectedBackgroundColor;
		this.textColor = textColor;
		this.selectedTextColor = selectedTextColor;
		this.isSelected = false;
			
		this.setLayout(new MigLayout("insets 0, gap 0", "0[]push[]0", "0[]0"));
		
		this.setBackground(backgroundColor);
		
		eventNameLabel = new JLabel("");
		eventNameLabel.setForeground(this.textColor);
		
		eventTimeLabel = new JLabel("");
		eventTimeLabel.setFont(new Font(eventTimeLabel.getFont().getName(), Font.PLAIN, 8));
		eventTimeLabel.setForeground(this.textColor);

		switch(textType) {
		case 1:
			eventNameLabel.setText(event.getName());
			eventTimeLabel.setText(DateUtils.timeToString(event.getStartDate()));
			break;
		case 2:
			eventNameLabel.setText(event.getName());
			break;
		case 3:
			eventTimeLabel.setText(DateUtils.timeToString(event.getEndDate()));
			break;
		}
		
		this.add(eventNameLabel, "wmin 0");
		this.add(eventTimeLabel);
	}
	
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
		
		if(this.isSelected) {
			this.setBackground(selectedBackgroundColor);
			eventNameLabel.setForeground(selectedTextColor);
		} else {
			this.setBackground(backgroundColor);
			eventNameLabel.setForeground(textColor);
		}
	}
	
	public boolean getSelected()  {return this.isSelected;}
	
	public Event getEvent() {return this.event;}

	public int getIndex() {
		return this.index;
	}
}
