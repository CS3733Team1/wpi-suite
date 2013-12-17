package edu.wpi.cs.wpisuitetng.modules.calendar.view.scheduledevent;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.CalendarUtils;
import net.miginfocom.swing.MigLayout;

public class ScheduleEventTitlePanel extends JPanel{

	public ScheduleEventTitlePanel(ArrayList<String> days)
	{
		System.out.println(days.size());
		StringBuilder col = new StringBuilder();
		for(int i = 0; i < (days.size()*2)+2; i++)
		{
			if(i == 0 || i== (days.size()+1))
				col.append("0[5%]0");
			else{
				float panelSize = (float)(45.0/days.size());
				System.out.println(panelSize);
				col.append("0[");
				col.append(panelSize);
				col.append("]0");
			}
			
		}
		this.setLayout(new MigLayout("fill, insets 0", col.toString(),"0[100%]0"));
		
		
		for(int i = 0; i < days.size(); i++)
		{
			System.out.println("days"+days.get(i));
			JLabel day = new JLabel(days.get(i), JLabel.CENTER);
			day.setForeground(CalendarUtils.titleNameColor);
			day.setFont(new Font(day.getFont().getFontName(), Font.BOLD, 12));
			this.add(day, "cell "+(i+1)+" 0, grow, push, wmin 0, alignx center");
		}
//		this.add(empty, "cell "+(days.size()+1)+" 0, grow, push, wmin 0");
		for(int i = 0; i < days.size(); i++)
		{
			JLabel day = new JLabel(days.get(i), JLabel.CENTER);
			day.setForeground(CalendarUtils.titleNameColor);
			day.setFont(new Font(day.getFont().getFontName(), Font.BOLD, 12));
			this.add(day, "cell "+(days.size()+2+i)+" 0, grow, push, wmin 0, alignx center");
		}
		JPanel space = new JPanel(new MigLayout("fill, insets 0"));

	}
}
