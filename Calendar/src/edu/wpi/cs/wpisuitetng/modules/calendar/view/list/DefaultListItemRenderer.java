package edu.wpi.cs.wpisuitetng.modules.calendar.view.list;

import java.awt.Color;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.CalendarUtils;

public class DefaultListItemRenderer<T> implements ListItemRenderer<T> {
	@Override
	public void createRenderedListComponents(SRList<T> listPanel, List<ListItem<T>> listItems) {
		for(ListItem<T> listItem: listItems) {
			CategoryListItem categoryListItem = new CategoryListItem();
			listItem.setRenderableComponent(categoryListItem);
			listItem.setComponent(categoryListItem.create(listItem));;
		}
	}

	@Override
	public void updateRenderedListComponents(SRList<T> listPanel, List<ListItem<T>> listItems) {
		for(ListItem<T> listItem: listItems)
			listItem.getRenderableComponent().update(listItem);
	}

	class CategoryListItem extends RenderableComponent<T> {

		private JPanel p;

		@Override
		public JComponent create(ListItem<T> listItem) {
			p = new JPanel();
			JLabel label = new JLabel(listItem.getListObject().toString());
			p.add(label);
			p.setBackground(listItem.isSelected() ? CalendarUtils.selectionColor : Color.WHITE);
			return p;
		}

		@Override
		public void update(ListItem<T> listItem) {
			p.setBackground(listItem.isSelected() ? CalendarUtils.selectionColor : Color.WHITE);
		}
	}
}
