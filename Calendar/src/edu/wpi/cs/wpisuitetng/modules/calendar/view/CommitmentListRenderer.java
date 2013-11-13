package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;


public class CommitmentListRenderer extends JPanel implements ListCellRenderer {

	private JLabel commitmentName;
	private JLabel dueDate;
	private JLabel category;
	
	private static final String[] daysOfWeek = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
	
	public CommitmentListRenderer() {
		this.setLayout(new BorderLayout());
		commitmentName = new JLabel();
		dueDate = new JLabel();
		category = new JLabel();
		this.add(commitmentName, BorderLayout.NORTH);
		this.add(dueDate, BorderLayout.CENTER);
		this.add(category, BorderLayout.SOUTH);
	}
	
	@Override
	public Component getListCellRendererComponent(JList list, Object object,
			int index, boolean isSelected, boolean cellHasFocus) {
		
		Commitment commitment = (Commitment)object;
		
		commitmentName.setText("Name: " + commitment.getName());
		dueDate.setText("Date: " + daysOfWeek[commitment.getDueDate().getDay()] + ", " + MonthCalendar.monthNames[commitment.getDueDate().getMonth()] + " "
		+ commitment.getDueDate().getDate() + ", "
				+ (commitment.getDueDate().getYear() + 1900));
		category.setText("Category: " + commitment.getCategory().getName());
		category.setForeground(commitment.getCategory().getColor());
		
		final Color background = UIManager.getDefaults().getColor("List.background");
		final Color foreground = UIManager.getDefaults().getColor("List.foreground");
		final Color selectionBackground = UIManager.getDefaults().getColor("List.selectionBackground");
		final Color selectionForeground = UIManager.getDefaults().getColor("List.selectionForeground");
		
		this.setBackground(isSelected ? selectionBackground : background);
		this.setForeground(isSelected ? selectionForeground : foreground);
		
		this.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		return this;
	}
}
