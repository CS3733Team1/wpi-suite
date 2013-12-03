package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;

public class MultidayEventView extends JPanel{
	private List<Event> multidaye;
	
	
	public MultidayEventView(List<Event> multiday, Dimension size){
		multidaye = multiday;

		this.setSize(size);
		this.setPreferredSize(size);
		
		showEvents();
		
		this.setOpaque(false);
		this.setVisible(true);
	}
	
	/**
	 * Sorts the List of Events by StartDate
	 */
	public void sortEvents(){
		Collections.sort(multidaye, new Comparator<Event>(){
			public int compare(Event o1, Event o2) {
				return o1.getStartDate().compareTo(o2.getStartDate());
			}
			
		});
	}
	
	public void showEvents(){
		if (multidaye.size() == 0){
			return;
		}
		
		sortEvents();
		
		System.out.println("WHY U NO SHOW");
		
		this.setLayout(new MigLayout("fill", 
				"[20%][80%]", 
				"[4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%]"));
		
		JPanel eventinfo = new JPanel();
		eventinfo.add(new JLabel("Multiday Event"), "wmin 0, aligny center, alignx center");
		StringBuilder evebuilder = new StringBuilder();
		evebuilder.append("cell ");
		evebuilder.append("0");
		evebuilder.append(" ");
		evebuilder.append("0");
		evebuilder.append(" ");
		evebuilder.append("2");
		evebuilder.append(" ");
		evebuilder.append("0");
		evebuilder.append(",grow, push");
		
		
		eventinfo.setBackground(Color.PINK);
		this.add(eventinfo, evebuilder.toString());
		
		
		StringBuilder bob = new StringBuilder();
		bob.append("<html>");
		for (Event eve: multidaye){
			bob.append("<p>");
			bob.append("<b>Name:</b> ");
			bob.append(eve.getName());
			bob.append("<br><b>Description:</b> ");
			bob.append(eve.getDescription());
			bob.append("</p>");
		}
		bob.append("</html>");
		eventinfo.setToolTipText(bob.toString());
		
	}
}
