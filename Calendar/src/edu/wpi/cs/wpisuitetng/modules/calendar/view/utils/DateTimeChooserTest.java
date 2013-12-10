package edu.wpi.cs.wpisuitetng.modules.calendar.view.utilities;

import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import com.toedter.calendar.JDateChooser;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import java.awt.Color;
import java.awt.Font;

public class DateTimeChooserTest extends JPanel {

	/**
	 * Create the panel.
	 */
	public DateTimeChooserTest() {
		setBackground(Color.LIGHT_GRAY);
		setForeground(Color.BLACK);
		setLayout(new MigLayout("", "[][grow][grow][]", "[]"));
		
		JLabel lblName = new JLabel("Name");
		add(lblName, "cell 0 0");
		
		JDateChooser dateChooser = new JDateChooser(new Date());
		add(dateChooser, "cell 1 0, growX");
		
		JComboBox comboBox = new JComboBox();
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 11));
		comboBox.setBackground(Color.MAGENTA);
		comboBox.setForeground(Color.YELLOW);
		comboBox.setEditable(true);
		add(comboBox, "cell 2 0,growx");
		
		JLabel lblError = new JLabel("Error");
		add(lblError, "cell 3 0");

	}
	
}