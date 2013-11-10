package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.CalendarModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.CalendarObjectModel;

public class CalendarTabPanel extends JPanel{
	CalendarModel model;
	private JList<Object> commitments;
	
	public CalendarTabPanel(CalendarObjectModel c, CalendarModel model){
		this.model = model;
		
		setLayout(new BorderLayout());
		add(new JLabel(c.getTitle()), BorderLayout.CENTER);


		commitments = new JList<Object>(model.getcommitModel());
		commitments
		.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		commitments.setLayoutOrientation(JList.VERTICAL);
		commitments.setVisibleRowCount(-1);


		JScrollPane scrollPane = new JScrollPane(commitments);

		add(scrollPane, BorderLayout.LINE_END);
	}
	
	public JList<Object> getCommitmentJList(){
		return commitments;
	}
	
	public void ResetSelection(){
		commitments.setSelectedIndices(null);
	}
	
}
