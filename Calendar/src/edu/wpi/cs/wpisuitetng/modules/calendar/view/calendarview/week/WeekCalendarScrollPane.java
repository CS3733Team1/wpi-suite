package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.week;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day.DayCalendarLayerPane;

public class WeekCalendarScrollPane extends JScrollPane{
	private WeekCalendarLayerPane weeklayer;
	public static final String[] weekNames = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
	
	public WeekCalendarScrollPane(WeekCalendarLayerPane day){
		super(day);

		weeklayer = day;
		
		JPanel weektitle = new JPanel(new MigLayout("insets 14", "[][][][][][][][]"));
		weektitle.setBackground(Color.WHITE);
		

		JPanel time = new JPanel(new MigLayout("fill","[grow]", "[]"));
		time.add(new JLabel("Time"), "push, align center");
		time.setBackground(new Color(138,173,209));
		
		for(int days = 1; days < 8; days++){
			JPanel weekName = new JPanel(new MigLayout("fill"));
			
			StringBuilder weekbuilder = new StringBuilder();
			weekbuilder.append("cell ");
			weekbuilder.append((new Integer(days)).toString());
			weekbuilder.append(" ");
			weekbuilder.append("0");
			weekbuilder.append(",wmin 130, alignx left, growy");
			
			weekName.add(new JLabel(weekNames[days-1]),"align center");
			
			
			weekName.setBackground(new Color(138,173,209));
			weektitle.add(weekName, weekbuilder.toString());
		}
		
		weektitle.add(time, "cell 0 0, wmin 80, alignx left, growy");
		
		
		JPanel cornertile = new JPanel();
		cornertile.setBackground(Color.WHITE);
		
		
		
		
		
		this.setCorner(UPPER_RIGHT_CORNER, cornertile);
		this.setColumnHeaderView(weektitle);
		this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

	}
}
