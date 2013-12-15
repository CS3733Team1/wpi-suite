package edu.wpi.cs.wpisuitetng.modules.calendar.view.list;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class SRList<T> extends JScrollPane implements MouseListener, ListDataListener {
	// Variables
	private ListItemRenderer<T> listItemRenderer;
	private ListModel<T> listModel;
	private List<ListItem<T>> listItems;
	private ListPanel listPanel;

	private List<ListItem<T>> selectedListItems;

	private int selectionStartIndex;

	List<ListItemListener<T>> listItemListeners;

	public SRList(ListModel<T> listModel) {
		this.listModel = listModel;
		listItems = new ArrayList<ListItem<T>>();
		listItemListeners = new ArrayList<ListItemListener<T>>();
		selectedListItems = new ArrayList<ListItem<T>>();

		listPanel = new ListPanel();
		listPanel.addMouseListener(this);
		this.addMouseListener(this);

		this.setViewportView(listPanel);
		this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.setListItemRenderer(new DefaultListItemRenderer<T>());

		fillListItems();
		this.listModel.addListDataListener(this);
		render();
	}

	// ListPanel Settings

	public void setListItemRenderer(ListItemRenderer<T> renderer) {
		this.listItemRenderer = renderer;
	}

	@Override
	public void setHorizontalScrollBarPolicy(int policy) {
		if(listPanel != null) {
			listPanel.setTracksViewportWidth(policy == JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			super.setHorizontalScrollBarPolicy(policy);
		}
	}

	@Override
	public void setVerticalScrollBarPolicy(int policy) {
		if(listPanel != null) {
			listPanel.setTracksViewportHeight(policy == JScrollPane.VERTICAL_SCROLLBAR_NEVER);
			super.setVerticalScrollBarPolicy(policy);
		}
	}

	// Logic / Data Builders / Other
	private void fillListItems() {
		listItems.clear();
		selectedListItems.clear();
		for(int i = 0; i < listModel.getSize(); i++) listItems.add(new ListItem<T>(listModel.getElementAt(i)));;
	}

	private void render() {
		listPanel.clear();
		for(ListItem<T> listItem: listItems) {
			listItem.setComponent(listItemRenderer.getRenderedListComponent(this, listItem.getListObject(), listItem.isSelected(), listItem.isFocused(), listItem.isDoubleClicked()));
			listPanel.add(listItem.getComponent());
		}
		this.revalidate();
		this.repaint();
	}

	private void clearSelection() {
		for(ListItem<T> listItem: selectedListItems) {
			listItem.setSelected(false);
			listItem.setFocused(false);
		}
		this.selectedListItems.clear();
	}

	public List<T> getSelectedItems() {
		List<T> selected = new ArrayList<T>();
		for(ListItem<T> listItem: selectedListItems) selected.add(listItem.getListObject());
		return selected;
	}

	// Listeners
	@Override
	public void mousePressed(MouseEvent e) {
		int indexOfMousePress = listPanel.getIndexOfComponent(listPanel.getComponentAt(e.getPoint()));
		if(indexOfMousePress >= 0) {
			if(e.getClickCount() == 2) {
				clearSelection();
				listItems.get(indexOfMousePress).toggleDoubleClicked();
				this.fireItemDoubleClicked(listItems.get(indexOfMousePress).getListObject());
			} else if(e.isShiftDown()) {
				clearSelection();
				if(selectionStartIndex <= indexOfMousePress) {
					for(int i = selectionStartIndex; i <= indexOfMousePress; i++) {
						listItems.get(i).setSelected(true);
						selectedListItems.add(listItems.get(i));
					}
				} else if(selectionStartIndex > indexOfMousePress) {
					for(int i = indexOfMousePress; i <= selectionStartIndex; i++) {
						listItems.get(i).setSelected(true);
						selectedListItems.add(listItems.get(i));
					}
				}
				this.fireItemsSelected(getSelectedItems());
			} else if(e.isControlDown()) {
				listItems.get(selectionStartIndex).setFocused(false);
				if(listItems.get(indexOfMousePress).isSelected()) {
					listItems.get(indexOfMousePress).setSelected(false);
					listItems.get(indexOfMousePress).setFocused(false);
					selectedListItems.remove(listItems.get(indexOfMousePress));
				} else {
					listItems.get(indexOfMousePress).setSelected(true);
					listItems.get(indexOfMousePress).setFocused(true);
					selectedListItems.add(listItems.get(indexOfMousePress));
				}
				selectionStartIndex = indexOfMousePress;
				this.fireItemsSelected(getSelectedItems());
			} else {
				// Clear the selection
				boolean prevSelectionState = listItems.get(indexOfMousePress).isSelected();
				clearSelection();
				if(prevSelectionState) {
					listItems.get(indexOfMousePress).setSelected(false);
					listItems.get(indexOfMousePress).setFocused(false);
					selectedListItems.remove(listItems.get(indexOfMousePress));
				} else {
					listItems.get(indexOfMousePress).setSelected(true);
					listItems.get(indexOfMousePress).setFocused(true);
					selectedListItems.add(listItems.get(indexOfMousePress));
				}
				selectionStartIndex = indexOfMousePress;
				this.fireItemsSelected(getSelectedItems());
			}
		} else {
			selectionStartIndex = 0;
			clearSelection();
		}
		render();
	}

	@Override
	public void contentsChanged(ListDataEvent e) {
		render();
	}

	@Override
	public void intervalAdded(ListDataEvent e) {
		if(listModel.getSize() != 0) {
			//for(int i = e.getIndex0(); i <= e.getIndex1(); i++) listItems.add(i, new ListItem<T>(listModel.getElementAt(i)));;
			fillListItems();
			render();
		}
	}

	@Override
	public void intervalRemoved(ListDataEvent e) {
		//for(int i = e.getIndex0(); i <= e.getIndex1(); i++) listItems.remove(i);
		fillListItems();
		render();
	}

	public void fireItemDoubleClicked(T listObject) {
		for(ListItemListener<T> l: listItemListeners) l.itemDoubleClicked(listObject);
	}

	public void fireItemRightClicked(T listObject) {
		for(ListItemListener<T> l: listItemListeners) l.itemRightClicked(listObject);
	}

	public void fireItemsSelected(List<T> listObjects) {
		for(ListItemListener<T> l: listItemListeners) l.itemsSelected(listObjects);
	}

	public void fireItemFocused(T listObject) {
		for(ListItemListener<T> l: listItemListeners) l.itemFocused(listObject);
	}

	public void addListItemListener(ListItemListener<T> l) {
		listItemListeners.add(l);
	}

	public void removeListItemListener(ListItemListener<T> l) {
		listItemListeners.remove(l);
	}

	public List<ListItemListener<T>> getListItemListeners() {
		return listItemListeners;
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