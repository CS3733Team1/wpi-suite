package edu.wpi.cs.wpisuitetng.modules.calendar.view.list;

import java.util.List;

public interface ListItemRenderer<T> {
	public void createRenderedListComponents(SRList<T> listPanel, List<ListItem<T>> listItems);
	public void updateRenderedListComponents(SRList<T> listPanel, List<ListItem<T>> listItems);
}
