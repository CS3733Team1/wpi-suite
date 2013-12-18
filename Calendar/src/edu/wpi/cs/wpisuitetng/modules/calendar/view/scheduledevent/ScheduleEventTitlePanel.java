package edu.wpi.cs.wpisuitetng.modules.calendar.view.scheduledevent;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.CalendarUtils;

public class ScheduleEventTitlePanel extends JPanel{

	public ScheduleEventTitlePanel(ArrayList<String> days)
	{
		StringBuilder col = new StringBuilder();
		for(int i = 0; i < (days.size()*2)+2; i++)
		{
			if(i == 0 || i== (days.size()+1))
				col.append("0[5%]0");
			else{
				float panelSize = (float)(45.0/days.size());
				col.append("0[");
				col.append(panelSize);
				col.append("]0");
			}
			
		}
		this.setLayout(new MigLayout("fill, insets 0", col.toString(),"0[100%]0"));
		
		
		for(int i = 0; i < days.size(); i++)
		{
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
