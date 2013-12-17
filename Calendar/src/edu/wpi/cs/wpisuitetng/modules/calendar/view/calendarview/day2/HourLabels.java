package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day2;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.CalendarUtils;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.DateUtils;

public class HourLabels extends JPanel{
	private ArrayList<JPanel> hourlist;

	public HourLabels(){
		hourlist = new ArrayList<JPanel>();
		
		this.setLayout(new MigLayout("fill", 
				"[100%]", 
				"[4%]3[5%]3[5%]3[5%]3[5%]3[5%]3[5%]3[5%]3[5%]3[5%]3[5%]3[5%]3[5%]3[5%]3[5%]3[5%]3[5%]3[5%]3[5%]3[5%]3[5%]3[5%]3[5%]3[5%]3[5%]1"));
		
		int height = 1440;
		int width = 100;
		
		this.setSize(width, height);
		this.setPreferredSize(new Dimension(width, height));
		this.setMinimumSize(new Dimension(0,1440));
		
		rebuildHours();
	}
	
	
	public void reSize(int width){
		this.setSize(width, 1440);
		this.setPreferredSize(new Dimension(width, 1440));
		this.setMinimumSize(new Dimension(0,1440));
		
		this.repaint();
	}
	/**
	 * Rebuilds the hour panels, so that they are normalized
	 */
	public void rebuildHours(){
		for (int x = 0; x < hourlist.size(); x++){
			this.remove(hourlist.get(x));
		}

		hourlist = new ArrayList<JPanel>();
		for (int currenthour=0; currenthour < 24; currenthour++){
			JPanel hour = new JPanel();

			JLabel timeLabel = new JLabel(DateUtils.hourString(currenthour), JLabel.RIGHT);
			timeLabel.setForeground(CalendarUtils.timeColor);
			hour.add(timeLabel, "wmin 0");
			this.add(hour, "cell 0 " + currenthour + ", grow, push, wmin 0");
			hourlist.add(hour);
		}
	}
}
