package edu.wpi.cs.wpisuitetng.modules.calendar.view.event;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.event.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.list.ListItem;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.list.ListItemRenderer;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.list.SRList;

public class EventListItemRenderer implements ListItemRenderer<Event> {
	private final static Logger LOGGER = Logger.getLogger(EventListItemRenderer.class.getName());

	private ImageIcon expand, compress;

	public EventListItemRenderer() {
		
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
	public void createRenderedListComponents(SRList<Event> listPanel, List<ListItem<Event>> listItems) {
		for(ListItem<Event> listItem: listItems) {
			EventListComponent renderableComponent = new EventListComponent(expand, compress);
			listItem.setRenderableComponent(renderableComponent);
			listItem.setComponent(renderableComponent.create(listItem));
		}
	}

	@Override
	public void updateRenderedListComponents(SRList<Event> listPanel, List<ListItem<Event>> listItems) {
		for(ListItem<Event> listItem: listItems) {
			listItem.update();
		}
	}
}
