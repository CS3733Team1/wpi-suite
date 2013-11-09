package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.CalendarModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.CalendarObjectModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;

public class CalendarPanel extends JTabbedPane {

	private JPanel p;
	private JList commitments;
	private DefaultListModel listModel;
	private CalendarModel model;
	
	public CalendarPanel(CalendarModel model) {
		
		this.model = model;
		// Initially Empty
		this.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	}

	// Method to set / add Tab [Calendars]

	public void addCalendar(CalendarObjectModel c) {
		p = new JPanel();
		p.setLayout(new BorderLayout());
		p.add(new JLabel(c.getTitle()), BorderLayout.CENTER);

		listModel = new DefaultListModel();
		for (Commitment commitment : c.getCommitments()) {
			listModel.addElement(commitment.getName());
			System.out.println(commitment.getName());
		}

		listModel.addElement("Test1");
		listModel.addElement("Test2");
		listModel.addElement("Test3");
		listModel.addElement("Test4");
		listModel.addElement("Test5");
		listModel.addElement("Test6");

		commitments = new JList(listModel);
		commitments
				.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		commitments.setLayoutOrientation(JList.VERTICAL);
		commitments.setVisibleRowCount(-1);

		JScrollPane scrollPane = new JScrollPane(commitments);

		p.add(scrollPane, BorderLayout.LINE_END);

		this.addTab(c.getTitle(), null, p, "A Calendar");
	}
	
}
