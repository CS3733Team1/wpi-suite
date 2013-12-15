package edu.wpi.cs.wpisuitetng.modules.calendar.view.list;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class SRList<T> extends JScrollPane implements MouseListener, ListDataListener {
	// Constants
	public static int SCROLLBAR_AS_NEEDED = 1;
	public static int SCROLLBAR_NEVER = 2;
	public static int SCROLLBAR_ALWAYS = 3;

	// Variables
	private ListItemRenderer<T> listItemRenderer;
	private ListModel<T> listModel;
	private boolean hasScrollPane;
	private List<ListItem<T>> listItems;
	
	private JScrollPane scrollPane;

	public SRList(ListModel<T> listModel) {
		this.listModel = listModel;
		hasScrollPane = false;
		listItems = new ArrayList<ListItem<T>>();

		setScrollable(SCROLLBAR_NEVER, SCROLLBAR_NEVER);

		this.addMouseListener(this);
		this.listModel.addListDataListener(this);

		render();
	}


	// ListPanel Settings

	public void setScrollable(int verticalScrollType, int horizontalScrollType) {
		if(SCROLLBAR_NEVER == verticalScrollType && verticalScrollType == horizontalScrollType) {
			if(hasScrollPane) { // Remove the ScrollPane
				this.remove(scrollPane);
				for(Component c: scrollPane.getComponents()) this.add(c);
				scrollPane.removeAll();
			}
		} else {
			if(!hasScrollPane) { // Build the ScrollPane
				scrollPane = new JScrollPane();
			}
			// Set the settings of the ScrollPane

		}
	}

	public void setListItemRenderer(ListItemRenderer<T> renderer) {
		this.listItemRenderer = renderer;
	}


	// Logic / Data Builders
	
	private void updateListItems() {
		
	}

	private void render() {
		if(hasScrollPane) ;
		else this.removeAll();
		for(ListItem<T> listItem: listItems) {
			listItem.setComponent(listItemRenderer.getRenderedListComponent(this, listItem.getListObject(), listItem.isSelected(), listItem.isFocused()));
		}
	}

	// Listeners
	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void contentsChanged(ListDataEvent e) {
		render();
	}

	@Override
	public void intervalAdded(ListDataEvent e) {
		for(int i = e.getIndex0(); i <= e.getIndex1(); i++) listItems.add(i, new ListItem<T>(listModel.getElementAt(i)));;
		render();
	}

	@Override
	public void intervalRemoved(ListDataEvent e) {
		for(int i = e.getIndex0(); i <= e.getIndex1(); i++) listItems.remove(i);
		render();
	}

	// Unused
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
}
