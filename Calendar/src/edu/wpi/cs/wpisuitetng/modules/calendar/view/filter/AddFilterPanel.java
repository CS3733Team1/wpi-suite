package edu.wpi.cs.wpisuitetng.modules.calendar.view.filter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Filter;

public class AddFilterPanel extends JPanel implements KeyListener, ActionListener {
	
	private JButton randomColors;
	private JButton ok;
	private JButton cancel;
	private JButton moveUp;
	private JButton moveDown;
	
	private JTextField nameTextField;
	
	private JLabel nameErrorLabel;

	public AddFilterPanel() {
		this.setLayout(new MigLayout("", "[]", "[][][][][][]"));
		
		try {
			randomColors = new JButton(new ImageIcon(ImageIO.read(getClass().getResource("/images/color_meter.png"))));
		} catch (IOException e) {}
		
		ok = new JButton("Ok");
		cancel = new JButton("Cancel");
		
		moveUp = new JButton("Move Up");
		moveDown = new JButton("Move Down");
		
		randomColors.addActionListener(this);
		
		ok.setActionCommand("addok");
		cancel.setActionCommand("addcancel");
		moveUp.setActionCommand("moveup");
		moveDown.setActionCommand("movedown");
		
		nameTextField = new JTextField(20);
		nameTextField.addKeyListener(this);
		
		JPanel p = new JPanel();
		p.add(new JLabel("Name:"));
		p.add(nameTextField);
		
		nameErrorLabel = new JLabel("Enter a Name");
		nameErrorLabel.setForeground(Color.RED);
		
		JPanel availableCategories = new JPanel();
		availableCategories.setSize(new Dimension(100, 200));
		availableCategories.setMinimumSize(new Dimension(100, 100));
		availableCategories.setOpaque(true);
		availableCategories.setBackground(Color.WHITE);
		this.add(p, "cell 0 0, alignx center");
		this.add(nameErrorLabel, "cell 0 1, alignx center");;
		this.add(availableCategories, "cell 0 2, growx, growy, alignx center");
		
		JPanel p2 = new JPanel();
		p2.add(randomColors);
		p2.add(ok);
		p2.add(cancel);
		
		JPanel p3 = new JPanel();
		p3.add(moveUp);
		p3.add(moveDown);
		
		JPanel filterCategories = new JPanel();
		filterCategories.setSize(new Dimension(100, 200));
		filterCategories.setMinimumSize(new Dimension(100, 100));
		filterCategories.setOpaque(true);
		filterCategories.setBackground(Color.WHITE);
		this.add(p3, "cell 0 3, alignx center");
		this.add(p2, "cell 0 5, alignx center");
		this.add(filterCategories, "cell 0 4, growx, growy, alignx center");
		this.validateFields();
	}

	private void validateFields() {
		if(nameTextField.getText().trim().length() == 0) {
			nameErrorLabel.setVisible(true);
			ok.setEnabled(false);
		} else {
			nameErrorLabel.setVisible(false);
			ok.setEnabled(true);
		}
		this.revalidate();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		this.validateFields();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		this.validateFields();
	}

	@Override
	public void keyTyped(KeyEvent e) {}
	
	public void setCancelListener(ActionListener al) {
		cancel.addActionListener(al);
	}
	
	public void setOkListener(ActionListener al) {
		ok.addActionListener(al);
	}
	
	public Filter getFilter() {
		return new Filter(nameTextField.getText());// cs.getSelectedColor());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		/*this.remove(cs);
		cs = new ColorSwatch();
		cs.revalidate();
		this.add(cs, "cell 0 2, w 200, h 200, alignx center, aligny center");
		this.revalidate();*/
	}
}
