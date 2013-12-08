package edu.wpi.cs.wpisuitetng.modules.calendar.view.utilities;

import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import com.toedter.calendar.JDateChooser;
import net.miginfocom.swing.MigLayout;

public class DateTimePickerLayoutTest extends JPanel {
	
	private String[] times;
	
	private JComboBox<String> timeCombo;
	
	/**
	 * Create the panel.
	 */
	public DateTimePickerLayoutTest() {
		setLayout(new MigLayout("", "[]", "[]"));
		
		times = new String[24];
		for(int i = 0; i < 24; i++) {
			times[i] = i + ":00";
		}
		timeCombo = new JComboBox<String>(times);
		int curHour =(new Date()).getHours();
		timeCombo.setSelectedIndex(curHour);
		add(timeCombo, "cell 0 0");
		
		JDateChooser jDatePicker_= new JDateChooser();
		add(jDatePicker_, "cell 1 0");
	}
}
