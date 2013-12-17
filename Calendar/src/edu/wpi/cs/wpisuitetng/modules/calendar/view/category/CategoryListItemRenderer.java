package edu.wpi.cs.wpisuitetng.modules.calendar.view.category;

import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.list.ListItem;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.list.ListItemRenderer;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.list.SRList;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.CalendarUtils;

public class CategoryListItemRenderer implements ListItemRenderer<Category> {
	@Override
	public void createRenderedListComponents(SRList<Category> listPanel, List<ListItem<Category>> listItems) {
		for(ListItem<Category> listItem: listItems) {
			Category category = listItem.getListObject();
			
			JPanel p = new JPanel(new MigLayout());

			JPanel colorSquare = new JPanel();
			JLabel categoryName = new JLabel();

			colorSquare.setPreferredSize(new Dimension(16, 16));
			colorSquare.setBorder(new MatteBorder(2, 2, 2, 2, Color.BLACK));
			p.add(colorSquare, "split 2");
			p.add(categoryName, "alignx left, wmax 200");
			p.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));

			if(category != null) {
				colorSquare.setBackground(category.getColor());
				categoryName.setText(category.getName());
			}

			p.setBackground(listItem.isSelected() ? CalendarUtils.selectionColor : Color.WHITE);
			
			listItem.setComponent(p);
		}
	}

	@Override
	public void updateRenderedListComponents(SRList<Category> listPanel, List<ListItem<Category>> listItems) {
		for(ListItem<Category> listItem: listItems)
			listItem.getComponent().setBackground(listItem.isSelected() ? CalendarUtils.selectionColor : Color.WHITE);
	}
}
