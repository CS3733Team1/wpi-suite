package edu.wpi.cs.wpisuitetng.modules.calendar.view.scheduledevent;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class TimePanel extends JPanel{

	private int end;
	private int start;
	private ArrayList<JPanel> listHour;

	public TimePanel(int start, int end)
	{
		this.end = end;
		this.start = start;
		listHour = new ArrayList<JPanel>();
		StringBuilder sb = new StringBuilder();
		System.out.println("time"+(end-start));
		for(int i = 0; i < (end-start+1); i++)
		{
			float percentage = (float)(1.0/((end-start)+1))*100;
			sb.append("0[");
			if(i == 0 || i == (end-start))
				percentage = percentage - percentage/2;
			else 
				percentage = percentage;
			sb.append(percentage);
			sb.append("%]0");
		}
		this.setLayout(new MigLayout("fill,insets 0,gapy 0", 
				"0[]0",
				sb.toString()));
		addHours();
	}
	
	
	private void addHours() {
		for(int i = 0; i < (end-start+1); i++)
		{	
			JPanel hourPanel = new JPanel();
			hourPanel.setBackground(Color.white);
//			hourPanel.setBorder(new MatteBorder(1, 1, 1, 1, Color.white));
			hourPanel.setLayout(new MigLayout("insets 0 0 0 10"));
			if(i > 0 && i < end-start){
				JLabel hourLabel = new JLabel(""+(start+i),JLabel.RIGHT);
				hourPanel.add( hourLabel, "push, aligny center, alignx right" );
				hourLabel.setForeground(Color.gray);
			}
			listHour.add(hourPanel);
			this.add(hourPanel, "cell 0 "+i+", grow, push");

		}
	}

}
