package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview;

import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class TimeDurationPickerPanel extends JPanel {

	private String[] times;

	private JComboBox<String> timeStart;
	private JComboBox<String> timeEnd;

	public TimeDurationPickerPanel() {
		this.setLayout(new MigLayout("insets 1"));

		times = new String[24];

		for(int i = 0; i < 24; i++) {
			times[i] = i + ":00";
		}

		timeStart = new JComboBox<String>(times);
		timeEnd = new JComboBox<String>(times);

		timeStart.setSelectedIndex(12);
		timeEnd.setSelectedIndex(12);
		
		this.add(new JLabel("Start:"), "split 2");
		this.add(timeStart, "alignx left, wrap");
		
		this.add(new JLabel("<html>&nbsp;&nbsp;End:</html>"), "split 2");
		this.add(timeEnd, "alignx left, wrap");
	}

	public int isInvalidTime() {
		if(timeStart.getSelectedIndex() == timeEnd.getSelectedIndex()) return 1;
		else if(timeStart.getSelectedIndex() > timeEnd.getSelectedIndex()) return 2;
		return 0;
	}
	
	public int getStartTime() {
		return timeStart.getSelectedIndex();
	}
	
	public int getEndTime() {
		return timeEnd.getSelectedIndex();
	}
	
	public void setActionListener(ActionListener al) {
		timeStart.addActionListener(al);
		timeEnd.addActionListener(al);
	}
}
