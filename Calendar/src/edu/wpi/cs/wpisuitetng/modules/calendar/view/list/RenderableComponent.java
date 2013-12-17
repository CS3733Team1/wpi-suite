package edu.wpi.cs.wpisuitetng.modules.calendar.view.list;

import javax.swing.JComponent;

public abstract class RenderableComponent<T> {
	public abstract JComponent create(ListItem<T> listItem);
	public abstract void update(ListItem<T> listItem);	
}
