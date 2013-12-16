package edu.wpi.cs.wpisuitetng.modules.calendar.view.commitment;

import java.awt.Color;
import java.awt.Insets;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.buttons.TransparentButton;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.help.MultiLineLabel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.list.ListItem;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.list.ListItemExpandListener;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.list.ListItemRenderer;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.list.SRList;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.CalendarUtils;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.DateUtils;

public class CommitmentListItemRenderer implements ListItemRenderer<Commitment> {

	private ImageIcon expand, compress;

	public CommitmentListItemRenderer() {
		try {
			expand = new ImageIcon(ImageIO.read(getClass().getResource("/images/expand.png")));
			compress = new ImageIcon(ImageIO.read(getClass().getResource("/images/compress.png")));
		} catch (IOException e) {}
	}

	@Override
	public JComponent getRenderedListComponent(SRList<Commitment> listPanel, ListItem<Commitment> listItem, Commitment commitment) {
		JPanel p = new JPanel(new MigLayout("fill", "[]push[]"));
		p.setBackground(listItem.isSelected() ? CalendarUtils.selectionColor : Color.WHITE);
		p.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));

		JLabel commitmentName = new JLabel(commitment.getName(), JLabel.CENTER);
		commitmentName.setBackground(commitment.getCategory().getColor());
		commitmentName.setOpaque(true);

		JLabel dueDate = new JLabel("Due: " + DateUtils.dateToSting(commitment.getDueDate()));

		p.add(commitmentName, "growx, span 2, wrap");

		if(listItem.isExpanded()) {
			p.add(dueDate, "span 2, wrap");
			p.add(new JLabel("Progress: " + commitment.getProgress()), "span 2, wrap");
			if(commitment.getisTeam()) p.add(new JLabel("Calendar: Team"), "span 2, wrap");
			else p.add(new JLabel("Calendar: Personal"), "span 2, wrap");
			if(commitment.getDescription().length() > 0) {
				p.add(new JLabel("Description: "), "span 2, wrap");
				MultiLineLabel descriptionText = new MultiLineLabel(commitment.getDescription());
				p.add(descriptionText, "growx, span 2, wrap");
			}
			p.add(new JLabel("Created by: " + commitment.getOwnerName()));

			TransparentButton compressButton = new TransparentButton(compress);
			compressButton.setMargin(new Insets(0, 0, 0 ,0));
			compressButton.setActionCommand("compress");
			compressButton.addActionListener(new ListItemExpandListener<Commitment>(listItem));

			p.add(compressButton, "alignx right");
		} else {
			p.add(dueDate);

			TransparentButton expandButton = new TransparentButton(expand);
			expandButton.setMargin(new Insets(0, 0, 0 ,0));
			expandButton.setActionCommand("expand");
			expandButton.addActionListener(new ListItemExpandListener<Commitment>(listItem));

			p.add(expandButton, "alignx right");
		}

		return p;
	}
}
