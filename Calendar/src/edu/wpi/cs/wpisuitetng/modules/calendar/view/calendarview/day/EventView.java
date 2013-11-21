package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.DatePanel;

public class EventView extends JPanel {

	private Event e;
	
	public EventView(Event e, Dimension size)
	{
		this.setSize(size);
		this.setPreferredSize(size);
		
		this.e = e;
		showEvent();
		this.setOpaque(false);
		this.setVisible(true);
	}
	/**
	 * Creates a Mig-Layout panel to be displayed on the day view
	 * @param e Event to be displayed
	 * @return JPanel generated
	 */
	public void showEvent()
	{
		System.out.println(e.toString());
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
				"[4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%]"));
		
		System.out.println(loc);
		
		for (int currenthour=0; currenthour < 25; currenthour++){
			if (currenthour-1 == loc/4){
				JPanel event = new JPanel(new MigLayout());
				StringBuilder evebuilder = new StringBuilder();
				evebuilder.append("cell ");
				evebuilder.append("1");
				evebuilder.append(" ");
				evebuilder.append((new Integer(currenthour)).toString());
				evebuilder.append(" ");
				evebuilder.append("0");
				evebuilder.append(" ");
				evebuilder.append((new Integer((int)length)).toString());
				evebuilder.append(",grow, push");

				event.add(new JLabel(e.getName()), "wmin 0, aligny center, alignx center");
				if (e.getCategory() != null){
					event.setBackground(e.getCategory().getColor());
				}
				else{
					event.setBackground(Color.CYAN);
				}
				event.setFocusable(true);
				this.add(event, evebuilder.toString());
			}
			else{
				DatePanel thesecond = new DatePanel();

				StringBuilder datebuilder = new StringBuilder();
				datebuilder.append("cell ");
				datebuilder.append("1");
				datebuilder.append(" ");
				datebuilder.append((new Integer(currenthour)).toString());
				datebuilder.append(",grow, push");

				thesecond.setOpaque(false);
				this.add(thesecond, datebuilder.toString());
			}
		}
	}
	
}
