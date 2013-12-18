package edu.wpi.cs.wpisuitetng.modules.calendar.view.scheduledevent;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.scheduledevent.ScheduledEvent;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.list.ListItem;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.list.ListItemRenderer;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.list.SRList;

public class ScheduledEventListItemRenderer implements ListItemRenderer<ScheduledEvent> {
	private final static Logger LOGGER = Logger.getLogger(ScheduledEventListItemRenderer.class.getName());

	private ImageIcon expand, compress;

	public ScheduledEventListItemRenderer() {
		
		expand = new ImageIcon();
		compress = new ImageIcon();
		
		try {
			expand = new ImageIcon(ImageIO.read(getClass().getResource("/images/expand.png")));
			compress = new ImageIcon(ImageIO.read(getClass().getResource("/images/compress.png")));
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Images not found.", e);
		}
	}

	@Override
	public void createRenderedListComponents(SRList<ScheduledEvent> listPanel, List<ListItem<ScheduledEvent>> listItems) {
		for(ListItem<ScheduledEvent> listItem: listItems) {
			ScheduledEventListComponent renderableComponent = new ScheduledEventListComponent(expand, compress);
			listItem.setRenderableComponent(renderableComponent);
			listItem.setComponent(renderableComponent.create(listItem));
		}
	}

	@Override
	public void updateRenderedListComponents(SRList<ScheduledEvent> listPanel, List<ListItem<ScheduledEvent>> listItems) {
		for(ListItem<ScheduledEvent> listItem: listItems) {
			listItem.update();
		}
	}
}
