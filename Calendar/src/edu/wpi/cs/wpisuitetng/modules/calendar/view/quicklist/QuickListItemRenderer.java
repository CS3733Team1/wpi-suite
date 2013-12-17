package edu.wpi.cs.wpisuitetng.modules.calendar.view.quicklist;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.ISchedulable;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.commitment.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.list.ListItem;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.list.ListItemRenderer;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.list.RenderableComponent;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.list.SRList;

public class QuickListItemRenderer implements ListItemRenderer<ISchedulable> {
	private final static Logger LOGGER = Logger.getLogger(QuickListItemRenderer.class.getName());

	private ImageIcon expand, compress;

	public QuickListItemRenderer() {
		
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
	public void createRenderedListComponents(SRList<ISchedulable> listPanel, List<ListItem<ISchedulable>> listItems) {
		for(ListItem<ISchedulable> listItem: listItems) {
			RenderableComponent<ISchedulable> renderableComponent;
			if(listItem.getListObject() instanceof Commitment) renderableComponent = new CommitmentListComponent(expand, compress);
			else renderableComponent = new EventListComponent(expand, compress);
			
			listItem.setRenderableComponent(renderableComponent);
			listItem.setComponent(renderableComponent.create(listItem));
		}
	}

	@Override
	public void updateRenderedListComponents(SRList<ISchedulable> listPanel, List<ListItem<ISchedulable>> listItems) {
		for(ListItem<ISchedulable> listItem: listItems) {
			listItem.update();
		}
	}
}
