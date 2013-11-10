package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.SpringLayout;
import javax.swing.UIManager;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.CommitmentListModel;


public class CommitmentListRenderer extends JPanel implements ListCellRenderer {

	private JLabel commitmentName;
	private JLabel dueDate;
	
	public CommitmentListRenderer() {
		this.setLayout(new BorderLayout());
		commitmentName = new JLabel();
		dueDate = new JLabel();
		this.add(commitmentName, BorderLayout.NORTH);
		this.add(dueDate, BorderLayout.SOUTH);
		/*
		layout.putConstraint(SpringLayout.WEST, commitmentName,
                5,
                SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, commitmentName,
                5,
                SpringLayout.NORTH, this);
                */
	}
	
	@Override
	public Component getListCellRendererComponent(JList list, Object object,
			int index, boolean isSelected, boolean cellHasFocus) {
		
		Commitment commitment = (Commitment)object;
		
		commitmentName.setText("Name: " + commitment.getName());
		dueDate.setText("Date: " + commitment.getDueDate().toString());
		
		final Color background = UIManager.getDefaults().getColor("List.background");
		final Color foreground = UIManager.getDefaults().getColor("List.foreground");
		final Color selectionBackground = UIManager.getDefaults().getColor("List.selectionBackground");
		final Color selectionForeground = UIManager.getDefaults().getColor("List.selectionForeground");
		
		this.setBackground(isSelected ? selectionBackground : background);
		this.setForeground(isSelected ? selectionForeground : foreground);
		
		return this;
	}
}
