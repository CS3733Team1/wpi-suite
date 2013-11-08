package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class CalendarPanel extends JTabbedPane {

	public CalendarPanel() {
		JPanel p1 = new JPanel();
		p1.add(new JLabel("TEAM CALENDAR"));
		JPanel p2 = new JPanel();
		p2.add(new JLabel("PERSONAL CALENDAR"));
		JPanel p3 = new JPanel();
		p3.add(new JLabel("ANOTHER CALENDAR"));

		this.addTab("Team Calendar", null, p1, "Does nothing");
		this.setMnemonicAt(0, KeyEvent.VK_1);

		this.addTab("Personal Calendar", null, p2, "Does twice as much nothing");
		this.setMnemonicAt(1, KeyEvent.VK_2);

		this.addTab("Another Calendar", null, p3, "Still does nothing");
		this.setMnemonicAt(2, KeyEvent.VK_3);

		this.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	}
}
