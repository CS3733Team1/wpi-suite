/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.view.filter;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Filter;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.FilterListModel;

/**
 * This is the view where the list of categories are displayed.
 * Creates both a scroll pane and JList which stores all of the categories.
 * @version Revision: 1.0
 * @author
 */
public class FilterListPanel extends JPanel {

	private FilterListModel model;
	private JList<Filter> filterList;
	private JScrollPane scrollPane;

	public FilterListPanel() {
		this.model = FilterListModel.getFilterListModel();

		this.setLayout(new MigLayout("fill, insets 0"));

		filterList = new JList<Filter>(model);

		filterList.setCellRenderer(new FilterListCellRenderer());
		filterList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		filterList.setLayoutOrientation(JList.VERTICAL);

		filterList.setVisibleRowCount(0);

		scrollPane = new JScrollPane(filterList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		this.add(scrollPane, "grow, push");
	}

	public JList<Filter> getFilterList() {
		return filterList;
	}

	public void clearSelection() {
		filterList.clearSelection();
	}
}
