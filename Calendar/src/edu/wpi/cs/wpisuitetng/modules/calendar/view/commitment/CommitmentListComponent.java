package edu.wpi.cs.wpisuitetng.modules.calendar.view.commitment;

import java.awt.Color;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.commitment.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.MultiLineLabel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.buttons.TransparentButton;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.list.ListItem;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.list.ListItemExpandListener;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.list.RenderableComponent;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.CalendarUtils;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.DateUtils;

public class CommitmentListComponent extends RenderableComponent<Commitment> {

	private JPanel p;
	private JLabel commitmentNameLabel;
	private JLabel dueDateLabel;
	private JLabel progressLabel;
	private JLabel isTeamLabel;
	private MultiLineLabel descriptionText;
	private JLabel createdByLabel;
	private TransparentButton expandButton;
	private TransparentButton compressButton;
	
	private boolean hasDescription;

	private ImageIcon expand;
	private ImageIcon compress;

	public CommitmentListComponent(ImageIcon expand, ImageIcon compress) {
		this.expand = expand;
		this.compress = compress;
	}

	@Override
	public JComponent create(ListItem<Commitment> listItem) {
		Commitment commitment = listItem.getListObject();

		p = new JPanel(new MigLayout("fill", "[]push[]"));
		p.setBackground(listItem.isSelected() ? CalendarUtils.selectionColor : Color.WHITE);
		p.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));

		commitmentNameLabel = new JLabel(commitment.getName(), JLabel.CENTER);
		commitmentNameLabel.setBackground(commitment.getCategory().getColor());
		commitmentNameLabel.setOpaque(true);

		dueDateLabel = new JLabel("Due: " + DateUtils.dateToSting(commitment.getDueDate()));

		progressLabel = new JLabel("Progress: " + commitment.getProgress());

		if(commitment.getisTeam()) isTeamLabel = new JLabel("Calendar: Team");
		else isTeamLabel = new JLabel("Calendar: Personal");

		hasDescription = commitment.getDescription().length() > 0;
		descriptionText = new MultiLineLabel("Description: " + commitment.getDescription());

		createdByLabel = new JLabel("Created by: " + commitment.getOwnerName());

		compressButton = new TransparentButton(compress);
		compressButton.setMargin(new Insets(0, 0, 0 ,0));
		compressButton.setActionCommand("compress");
		compressButton.addActionListener(new ListItemExpandListener<Commitment>(listItem));

		expandButton = new TransparentButton(expand);
		expandButton.setMargin(new Insets(0, 0, 0 ,0));
		expandButton.setActionCommand("expand");
		expandButton.addActionListener(new ListItemExpandListener<Commitment>(listItem));

		p.add(commitmentNameLabel, "growx, span 2, wrap");
		p.add(dueDateLabel);
		p.add(expandButton, "alignx right");

		return p;
	}

	@Override
	public void update(ListItem<Commitment> listItem) {
		p.setBackground(listItem.isSelected() ? CalendarUtils.selectionColor : Color.WHITE);

		if(listItem.isDirty()) {
			p.removeAll();

			p.add(commitmentNameLabel, "growx, span 2, wrap");

			if(!listItem.isExpanded()) {
				p.add(dueDateLabel);
				expandButton.reset();
				p.add(expandButton, "alignx right");
			} else {
				p.add(dueDateLabel, "span 2, wrap");
				p.add(progressLabel, "span 2, wrap");
				p.add(isTeamLabel, "span 2, wrap");
				if(hasDescription) p.add(descriptionText, "growx, span 2, wrap");
				p.add(createdByLabel);
				compressButton.reset();
				p.add(compressButton, "alignx right");
			}

			p.repaint();
		}
	}
}
