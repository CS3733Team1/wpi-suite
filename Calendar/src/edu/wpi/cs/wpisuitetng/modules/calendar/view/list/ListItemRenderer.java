package edu.wpi.cs.wpisuitetng.modules.calendar.view.list;

import javax.swing.JComponent;

public interface ListItemRenderer<T> {
	public JComponent getRenderedListComponent(SRList<T> listPanel, T listObject, boolean isSelected, boolean hasFocus, boolean doubleClicked);
}
