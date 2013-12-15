package edu.wpi.cs.wpisuitetng.modules.calendar.view.commitment;

import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.MatteBorder;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.list.ListItemRenderer;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.list.SRList;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.CalendarUtils;

public class CommitmentListItemRenderer implements ListItemRenderer<Commitment> {

	@Override
	public JComponent getRenderedListComponent(SRList<Commitment> listPanel,
			Commitment commitment, boolean isSelected, boolean hasFocus, boolean doubleClicked) {
		JPanel p = new JPanel(new MigLayout("flowy"));
		p.setBackground(isSelected ? CalendarUtils.selectionColor : Color.WHITE);
		p.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));

		JLabel commitmentName = new JLabel();
		JLabel dueDate = new JLabel();

		commitmentName.setText(commitment.getName());
		commitmentName.setBackground(commitment.getCategory().getColor());
		commitmentName.setOpaque(true);

		dueDate.setText("Due: " + CalendarUtils.weekNamesAbbr[commitment.getDueDate().getDay()] + ", " + CalendarUtils.monthNames[commitment.getDueDate().getMonth()] + " "
				+ commitment.getDueDate().getDate() + ", "
				+ (commitment.getDueDate().getYear() + 1900));

		p.add(commitmentName);
		p.add(dueDate);

		if(doubleClicked) {
			p.add(new JLabel("Progress: " + commitment.getProgress()));
			if(commitment.getisTeam()) p.add(new JLabel("Calendar: Team"));
			else p.add(new JLabel("Calendar: Personal"));
			if(commitment.getDescription().length() > 0) {
				p.add(new JLabel("Description: "));
				JTextArea descriptionText = new JTextArea(commitment.getDescription());
				descriptionText.setLineWrap(true);
				descriptionText.setWrapStyleWord(true);
				p.add(descriptionText);
			}

			p.add(new JLabel("Created by: " + commitment.getOwnerName()));
		}
		return p;
	}
}
