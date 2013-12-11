package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.month;

import java.awt.Color;

import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;

public class FillerPanel extends EvComPanel {

	public FillerPanel(Color background) {
		super(false);
		this.setLayout(new MigLayout("insets 0"));
		this.setBackground(this.getBackground());
		JLabel fillerLabel = new JLabel("filler");
		fillerLabel.setForeground(this.getBackground());
		this.add(fillerLabel);
	}
}
