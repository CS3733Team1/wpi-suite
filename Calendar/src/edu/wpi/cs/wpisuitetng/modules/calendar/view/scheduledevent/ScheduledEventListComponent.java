package edu.wpi.cs.wpisuitetng.modules.calendar.view.scheduledevent;

import java.awt.Color;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.scheduledevent.ScheduledEvent;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.buttons.TransparentButton;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.list.ListItem;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.list.ListItemExpandListener;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.list.RenderableComponent;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.CalendarUtils;

public class ScheduledEventListComponent extends RenderableComponent<ScheduledEvent> {

	private JPanel p;
	private JLabel scheduledEventLabel;
	private JLabel daysOfWeekAndTime;
	private JLabel currentParticipants;
	private JLabel createdByLabel;
	private TransparentButton expandButton;
	private TransparentButton compressButton;

	private ImageIcon expand;
	private ImageIcon compress;

	public ScheduledEventListComponent(ImageIcon expand, ImageIcon compress) {
		this.expand = expand;
		this.compress = compress;
	}

	@Override
	public JComponent create(ListItem<ScheduledEvent> listItem) {
		ScheduledEvent scheduledEvent = listItem.getListObject();

		p = new JPanel(new MigLayout("fill", "[]push[]"));
		p.setBackground(listItem.isSelected() ? CalendarUtils.selectionColor : Color.WHITE);
		p.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));

		scheduledEventLabel = new JLabel(scheduledEvent.getTitle(), JLabel.CENTER);

		daysOfWeekAndTime = new JLabel("Days~");
		currentParticipants = new JLabel("Current participants: X");

		createdByLabel = new JLabel("Created by: " + scheduledEvent.getOwnerName());

		compressButton = new TransparentButton(compress);
		compressButton.setMargin(new Insets(0, 0, 0 ,0));
		compressButton.setActionCommand("compress");
		compressButton.addActionListener(new ListItemExpandListener<ScheduledEvent>(listItem));

		expandButton = new TransparentButton(expand);
		expandButton.setMargin(new Insets(0, 0, 0 ,0));
		expandButton.setActionCommand("expand");
		expandButton.addActionListener(new ListItemExpandListener<ScheduledEvent>(listItem));

		p.add(scheduledEventLabel, "growx, span 2, wrap");
		p.add(daysOfWeekAndTime, "span 2, wrap");
		p.add(currentParticipants);
		p.add(expandButton, "alignx right");

		return p;
	}

	@Override
	public void update(ListItem<ScheduledEvent> listItem) {
		p.setBackground(listItem.isSelected() ? CalendarUtils.selectionColor : Color.WHITE);

		if(listItem.isDirty()) {
			p.removeAll();

			p.add(scheduledEventLabel, "growx, span 2, wrap");
			p.add(daysOfWeekAndTime, "span 2, wrap");

			if(!listItem.isExpanded()) {
				expandButton.reset();
				p.add(currentParticipants);
				p.add(expandButton, "alignx right");
			} else {
				p.add(currentParticipants, "span 2, wrap");

				p.add(new JLabel("List OF NAMES"), "span 2, wrap");

				p.add(createdByLabel);
				compressButton.reset();
				p.add(compressButton, "alignx right");
			}
			p.repaint();
		}
	}
}
