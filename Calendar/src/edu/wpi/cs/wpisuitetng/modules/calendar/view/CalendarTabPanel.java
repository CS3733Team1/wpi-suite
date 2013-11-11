package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.CalendarModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.CalendarObjectModel;

public class CalendarTabPanel extends JPanel{
	CalendarModel model;
	private JList<Object> commitments, events;

	private JButton yearViewButton, monthViewButton, weekViewButton, dayViewButton;
	
	public CalendarTabPanel(CalendarObjectModel c, CalendarModel model){
		this.model = model;

		setLayout(new BorderLayout());

		JPanel calendarViewPanel = new JPanel(new BorderLayout());
		JPanel calendarViewButtonsPanel = new JPanel();

		try {

			dayViewButton = new JButton("Day",
					new ImageIcon(ImageIO.read(getClass().getResource("/images/day_cal.png"))));
			weekViewButton = new JButton("Week",
					new ImageIcon(ImageIO.read(getClass().getResource("/images/week_cal.png"))));
			monthViewButton = new JButton("Month",
					new ImageIcon(ImageIO.read(getClass().getResource("/images/month_cal.png"))));
			yearViewButton = new JButton("Year",
					new ImageIcon(ImageIO.read(getClass().getResource("/images/year_cal.png"))));

		} catch (IOException e) {}

		calendarViewButtonsPanel.add(yearViewButton);
		calendarViewButtonsPanel.add(monthViewButton);
		calendarViewButtonsPanel.add(weekViewButton);
		calendarViewButtonsPanel.add(dayViewButton);

		calendarViewPanel.add(calendarViewButtonsPanel, BorderLayout.NORTH);
		calendarViewPanel.add(new JLabel(c.getTitle()), BorderLayout.CENTER);
		
		add(calendarViewPanel, BorderLayout.CENTER);

		

		events = new JList<Object>(model.getEventModel());
		events
		.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		events.setLayoutOrientation(JList.VERTICAL);
		events.setVisibleRowCount(-1);


		JScrollPane scrollPane2 = new JScrollPane(commitments);

		add(scrollPane2, BorderLayout.LINE_END);

		commitments = new JList<Object>(model.getcommitModel());
		commitments.setCellRenderer(new CommitmentListRenderer());
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
		events.setSelectedIndices(null);
	}

	public JList<Object> getEventJList(){
		return events;
	}

}
