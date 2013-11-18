package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview;

import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import net.miginfocom.swing.MigLayout;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;

public class EventPanel extends JPanel{
	private JTextArea titleTextArea;
	private JTextArea dataTextArea;
	
	public EventPanel(Event eve){
		
		this.setLayout(new MigLayout("fill", 
				"[100%]", 
				"[10%][90%]"));
		
		titleTextArea = new JTextArea(eve.getName());
		titleTextArea.setEditable(false);
		titleTextArea.setFont(new Font(null, Font.PLAIN, 20));
		titleTextArea.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		
		StringBuilder titlebuilder = new StringBuilder();
		titlebuilder.append("cell ");
		titlebuilder.append("0");
		titlebuilder.append(" ");
		titlebuilder.append("0");
		titlebuilder.append(",grow, push");
		
		this.add(titleTextArea, titlebuilder.toString());
		
		dataTextArea = new JTextArea("Description: " + eve.getDescription() +"\nCategory:" + eve.getCategory());
		dataTextArea.setEditable(false);
		dataTextArea.setFont(new Font(null, Font.PLAIN, 10));
		dataTextArea.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		
		StringBuilder databuilder = new StringBuilder();
		databuilder.append("cell ");
		databuilder.append("0");
		databuilder.append(" ");
		databuilder.append("1");
		databuilder.append(",grow, push");
		
		this.add(dataTextArea, databuilder.toString());
		
		this.setVisible(true);
		
	}
	
}
