package edu.wpi.cs.wpisuitetng.modules.calendar.controller.help;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.commitment.CommitmentTabPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.help.HelpWindow;

public class HelpButtonController implements ActionListener {
	private CalendarPanel calendarPanel;
	
	public HelpButtonController(CalendarPanel calendarPanel){
		this.calendarPanel = calendarPanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		HelpWindow helpWindow = new HelpWindow();
		ImageIcon miniHelpIcon = new ImageIcon();
		try {
			miniHelpIcon = new ImageIcon(ImageIO.read(getClass().getResource("/images/question.png")));
		} catch (IOException exception) {}
		calendarPanel.addTab("Help", miniHelpIcon, helpWindow);
		calendarPanel.setSelectedComponent(helpWindow);
	}
}
