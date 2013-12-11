package edu.wpi.cs.wpisuitetng.modules.calendar.view.utils;

import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import net.miginfocom.swing.MigLayout;

public class TimeDurationChooserLayoutTest extends JPanel {

	/**
	 * Create the panel.
	 */
	public TimeDurationChooserLayoutTest() {
		setLayout(new MigLayout("", "[56px,right][]", "[]"));
		
		JLabel lblName = new JLabel("name");
		lblName.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblName, "cell 0 0,alignx right");
	}

}
