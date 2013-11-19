package edu.wpi.cs.wpisuitetng.modules.calendar.view.filter;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FilterTabPanel extends JPanel {

	private JButton addFilterButton, deleteFilterButton;

	public FilterTabPanel() {
		try {
			addFilterButton = new JButton("<html>New<br/>Filter</html>",
					new ImageIcon(ImageIO.read(getClass().getResource("/images/add_filter.png"))));

			deleteFilterButton = new JButton("<html>Delete<br/>Filter</html>",
					new ImageIcon(ImageIO.read(getClass().getResource("/images/delete_filter.png"))));
		} catch (IOException e) {}

		this.add(new JLabel("Unimplemented"));
	}
}
