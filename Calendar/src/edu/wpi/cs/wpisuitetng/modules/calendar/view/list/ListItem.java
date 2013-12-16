package edu.wpi.cs.wpisuitetng.modules.calendar.view.list;

import javax.swing.JComponent;

public class ListItem<T> {
	
	private SRList<T> srList;
	private T listObject;
	private boolean isSelected;
	private boolean isFocused;
	private boolean isDoubleClicked;
	private boolean isExpanded;
	private JComponent component;
	
	public ListItem(SRList<T> srList, T listItem) {
		this.srList = srList;
		this.listObject = listItem;
	}
	
	public T getListObject(){return this.listObject;}
	
	public JComponent getComponent(){return this.component;}
	public void setComponent(JComponent component) {this.component = component;}
	
	public boolean isFocused() {return isFocused;}
	public void setFocused(boolean focused) {this.isFocused = focused;}
	
	public boolean isSelected() {return this.isSelected;}
	public void setSelected(boolean selected) {this.isSelected = selected;}

	public boolean isDoubleClicked() {return this.isDoubleClicked;}
	public void setDoubleClicked(boolean doubleClicked){this.isDoubleClicked = doubleClicked;}

	public void toggleDoubleClicked() {this.isDoubleClicked = !this.isDoubleClicked;}

	public void setExpand(boolean expanded) {this.isExpanded = expanded;}
	public boolean isExpanded() {return this.isExpanded;}

	public void requesRender() {srList.render();}
}
