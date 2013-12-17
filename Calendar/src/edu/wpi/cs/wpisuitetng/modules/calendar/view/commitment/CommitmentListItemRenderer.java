package edu.wpi.cs.wpisuitetng.modules.calendar.view.commitment;

import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.list.ListItem;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.list.ListItemRenderer;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.list.SRList;

public class CommitmentListItemRenderer implements ListItemRenderer<Commitment> {

	private ImageIcon expand, compress;

	public CommitmentListItemRenderer() {
		try {
			expand = new ImageIcon(ImageIO.read(getClass().getResource("/images/expand.png")));
			compress = new ImageIcon(ImageIO.read(getClass().getResource("/images/compress.png")));
		} catch (IOException e) {}
	}

	@Override
	public void createRenderedListComponents(SRList<Commitment> listPanel, List<ListItem<Commitment>> listItems) {
		for(ListItem<Commitment> listItem: listItems) {
			CommitmentListComponent renderableComponent = new CommitmentListComponent(expand, compress);
			listItem.setRenderableComponent(renderableComponent);
			listItem.setComponent(renderableComponent.create(listItem));
		}
	}

	@Override
	public void updateRenderedListComponents(SRList<Commitment> listPanel, List<ListItem<Commitment>> listItems) {
		for(ListItem<Commitment> listItem: listItems) {
			listItem.update();
		}
	}
}
