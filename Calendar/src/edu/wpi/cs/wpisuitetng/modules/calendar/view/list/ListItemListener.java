package edu.wpi.cs.wpisuitetng.modules.calendar.view.list;

import java.util.List;

public interface ListItemListener<T> {
	public void itemsSelected(List<T> listObjects); // Or deselected
	public void itemDoubleClicked(T listObject);
	public void itemRightClicked(T listObject);
	public void itemFocused(T listObject);
}
