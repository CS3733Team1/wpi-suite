package edu.wpi.cs.wpisuitetng.modules.calendar.view.list;

import javax.swing.JComponent;

public class ListItem<T> {
	
	private T listObject;
	private boolean isSelected;
	private boolean isFocused;
	private JComponent component;
	
	public ListItem(T listItem) {
		this.listObject = listItem;
		this.isSelected = false;
	}
	
	public T getListObject(){return this.listObject;}
	
	public JComponent getComponent(){return this.component;}
	public void setComponent(JComponent component) {this.component = component;}
	
	public boolean isFocused() {return isFocused;}
	public void setFocused(boolean focused) {this.isFocused = focused;}
	
	public boolean isSelected() {return this.isSelected;}
	public void setSelected(boolean selected) {this.isSelected = selected;}
	public boolean toggleSelection() {
		isSelected = !isSelected;
		return isSelected;
	}
}
