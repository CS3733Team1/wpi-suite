package edu.wpi.cs.wpisuitetng.modules.calendar.view.scheduledevent;

import java.awt.Color;

import javax.swing.JFrame;

import net.miginfocom.swing.MigLayout;

public class Main {

	public static void main(String[] args) {
		JFrame f = new JFrame("Swing Hello World");

		// by doing this, we prevent Swing from resizing
		// our nice component
		f.setLayout(new MigLayout("fill,insets 0"));
		f.setBackground(Color.white);
		ScheduledEventTabPanel setp = new ScheduledEventTabPanel(); 
		f.add(setp, "grow, push");
//		ScheduleEventMain wtm = new ScheduleEventMain();
//		wtm.setTime(7,0,48, Color.BLUE);
//		
		f.setSize(1000, 1000);

		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setVisible(true);
	}
}
