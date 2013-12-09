package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.week;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;

/**
 * This class shows multi-day events in week view. It is a component of the WeekCalendarLayerPane, along
 * with EventWeekView.
 */

public class MultidayEventWeekView extends JPanel{
	private List<List<Event>> multidaye;
	
	
	public MultidayEventWeekView(List<List<Event>> multiday, Dimension size){
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
	
	public void showEvents(){
		ArrayList<String> names = new ArrayList<String>();
		
		if (multidaye.size() == 0){
			return;
		}
		
		this.setLayout(new MigLayout("fill", 
				"0[9%]3[13%]3[13%]3[13%]3[13%]3[13%]3[13%]3[13%]0", 
				"0[4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%]"));
		
		int y = 1;
		for(List<Event> list: multidaye)
		{
			if(list.size() == 0)
				continue;

			JPanel eventinfo = new JPanel();
			eventinfo.add(new JLabel("Multiday Event"), "wmin 0, aligny center, alignx center");
			StringBuilder evebuilder = new StringBuilder();
			evebuilder.append("cell ");
			evebuilder.append(new Integer(y).toString());
			evebuilder.append(" ");
			evebuilder.append("0");
			evebuilder.append(" ");
			evebuilder.append("0");
			evebuilder.append(" ");
			evebuilder.append("0");
			evebuilder.append(",grow, push");
			
		
			eventinfo.setBackground(Color.PINK);
			this.add(eventinfo, evebuilder.toString());
			y++;
			
			StringBuilder bob = new StringBuilder();
			bob.append("<html>");
			for (Event eve: list){
				if(names.contains(eve.getName()))
					continue;
				bob.append("<p>");
				bob.append("<b>Name:</b> ");
				bob.append(eve.getName());
				bob.append("<br><b>Description:</b> ");
				bob.append(eve.getDescription());
				bob.append("</p>");
				
				names.add(eve.getName());
			}
			bob.append("</html>");
			eventinfo.setToolTipText(bob.toString());
		}		
	}
}
