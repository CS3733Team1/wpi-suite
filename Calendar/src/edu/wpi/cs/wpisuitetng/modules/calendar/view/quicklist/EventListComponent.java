package edu.wpi.cs.wpisuitetng.modules.calendar.view.quicklist;

import java.awt.Color;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.ISchedulable;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.event.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.MultiLineLabel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.buttons.TransparentButton;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.list.ListItem;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.list.ListItemExpandListener;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.list.RenderableComponent;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.CalendarUtils;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.DateUtils;

public class EventListComponent extends RenderableComponent<ISchedulable> {

	private JPanel p;
	private JLabel eventNameLabel;
	private JLabel startDateLabel;
	private JLabel endDateLabel;
	private JLabel isTeamLabel;
	private MultiLineLabel descriptionText;
	private JLabel createdByLabel;
	private TransparentButton expandButton;
	private TransparentButton compressButton;

	private boolean hasDescription;

	private ImageIcon expand;
	private ImageIcon compress;

	public EventListComponent(ImageIcon expand, ImageIcon compress) {
		this.expand = expand;
		this.compress = compress;
	}

	@Override
	public JComponent create(ListItem<ISchedulable> listItem) {
		Event event = (Event)listItem.getListObject();

		p = new JPanel(new MigLayout("fill", "[]push[]"));
		p.setBackground(listItem.isSelected() ? CalendarUtils.selectionColor : Color.WHITE);
		p.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));

		eventNameLabel = new JLabel(event.getName(), JLabel.CENTER);
		eventNameLabel.setBackground(event.getCategory().getColor());
		eventNameLabel.setOpaque(true);

		startDateLabel = new JLabel("Start: " + DateUtils.dateToSting(event.getStartDate()));
		endDateLabel = new JLabel("End: " + DateUtils.dateToSting(event.getEndDate()));

		if(event.getisTeam()) isTeamLabel = new JLabel("Calendar: Team");
		else isTeamLabel = new JLabel("Calendar: Personal");

		hasDescription = event.getDescription().length() > 0;
		descriptionText = new MultiLineLabel("Description: " + event.getDescription());

		createdByLabel = new JLabel("Created by: " + event.getOwnerName());

		compressButton = new TransparentButton(compress);
		compressButton.setMargin(new Insets(0, 0, 0 ,0));
		compressButton.setActionCommand("compress");
		compressButton.addActionListener(new ListItemExpandListener<ISchedulable>(listItem));

		expandButton = new TransparentButton(expand);
		expandButton.setMargin(new Insets(0, 0, 0 ,0));
		expandButton.setActionCommand("expand");
		expandButton.addActionListener(new ListItemExpandListener<ISchedulable>(listItem));

		p.add(eventNameLabel, "growx, span 2, wrap");
		p.add(startDateLabel);
		p.add(expandButton, "alignx right");

		return p;
	}

	@Override
	public void update(ListItem<ISchedulable> listItem) {
		p.setBackground(listItem.isSelected() ? CalendarUtils.selectionColor : Color.WHITE);

		if(listItem.isDirty()) {
			p.removeAll();

			p.add(eventNameLabel, "growx, span 2, wrap");
			p.add(startDateLabel, "span 2, wrap");

			if(!listItem.isExpanded()) {
				p.add(endDateLabel);
				expandButton.reset();
				p.add(expandButton, "alignx right");
			} else {
				p.add(endDateLabel, "span 2, wrap");
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
