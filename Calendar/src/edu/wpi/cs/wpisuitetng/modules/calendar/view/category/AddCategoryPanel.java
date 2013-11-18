package edu.wpi.cs.wpisuitetng.modules.calendar.view.category;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class AddCategoryPanel extends JPanel {
	
	private JButton randomColors;
	private JButton ok;
	private JButton cancel;
	
	public AddCategoryPanel() {
		this.setLayout(new MigLayout("", "[][][]", "[][][][]"));
	}
	
	this.removeAll();

	
	JPanel p = new JPanel();
	p.add(new JLabel("Name:"));
	p.add(new JTextField(20));
	ColorSwatch cs = new ColorSwatch();
	this.add(p, "cell 0 0");
	this.add(cs, "cell 0 2");
	JPanel p2 = new JPanel();
	p2.add(new JButton("Ref"));
	p2.add(new JButton("Ok"));
	p2.add(new JButton("Cancel"));
	this.add(p2, "cell 0 3, alignx center");
}
