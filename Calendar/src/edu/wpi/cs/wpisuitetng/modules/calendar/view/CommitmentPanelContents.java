package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import javax.swing.JPanel;
import javax.swing.JButton;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JComboBox;

public class CommitmentPanelContents extends JPanel {
	private JTextField textField;

	/**
	 * Create the panel.
	 */
	public CommitmentPanelContents() {
		setLayout(new MigLayout("", "[100px][50px][]", "[15px][][::100px,grow][]"));
		
		JLabel lblNewLabel = new JLabel("New label");
		add(lblNewLabel, "cell 0 0,alignx trailing");
		
		textField = new JTextField();
		add(textField, "cell 1 0,growx");
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		add(lblNewLabel_1, "cell 0 1");
		
		JButton btnNewButton = new JButton("New button");
		add(btnNewButton, "cell 1 1");
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		add(lblNewLabel_2, "cell 0 2");
		
		JTextArea textArea = new JTextArea();
		add(textArea, "cell 1 2,grow");
		
		JLabel lblNewLabel_3 = new JLabel("New label");
		add(lblNewLabel_3, "cell 0 3,alignx trailing");
		
		JComboBox comboBox = new JComboBox();
		add(comboBox, "cell 1 3,growx");

	}

}
