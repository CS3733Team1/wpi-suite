package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;

public class EventView extends JPanel {

	private Event e;
	
	public EventView(Event e)
	{
		this.e = e;
	}
	/**
	 * Creates a Mig-Layout panel to be displayed on the day view
	 * @param e Event to be displayed
	 * @return JPanel generated
	 */
	public JPanel showEvent()
	{
		int height;
		int width;
		double length;
		int loc;
		JPanel panel = new JPanel();
		StringBuilder panelbuilder = new StringBuilder();
		
		length = (e.getEndDate().getHours() + ((double)e.getEndDate().getMinutes())/60.0) - (e.getStartDate().getHours() + ((double)e.getStartDate().getMinutes())/60.0);
		
		width = 79;
		height = (int)Math.round(length*4) - 1; //each hour is 4%, leave 1% for border
		loc = (int)Math.round((e.getStartDate().getHours() + e.getStartDate().getMinutes()/60.0)*4);
		
		this.setLayout(new MigLayout("fill", 
				"[22%][76%][2%]", 
				"[4%][" + loc + "%][" + length + "%][" + (100 - loc - length - 4) + "%]"));
		
		panelbuilder.append("cell ");
		panelbuilder.append("1");
		panelbuilder.append(" ");
		panelbuilder.append("2");
		panelbuilder.append(",grow, push");
		
		panel.add(new JLabel(e.getName()));
		panel.setBackground(Color.blue);
		panel.setVisible(true);
		this.add(panel, panelbuilder.toString());
		
		return panel;
	}
	
}
