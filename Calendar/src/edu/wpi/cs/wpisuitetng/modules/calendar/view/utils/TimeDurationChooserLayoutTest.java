package edu.wpi.cs.wpisuitetng.modules.calendar.view.utilities;

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

		DateTimeChooser dtcA = new DateTimeChooser();
		add(dtcA);
		setLayout(new MigLayout("", "[]", "[][][]"));
		
		JLabel lblNewLabel = new JLabel("New label");
		add(lblNewLabel, "cell 0 0");
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		add(lblNewLabel_1, "cell 0 1");
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		add(lblNewLabel_2, "cell 0 2");
		
	}

}
