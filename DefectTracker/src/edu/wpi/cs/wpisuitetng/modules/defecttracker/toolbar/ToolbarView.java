/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Andrew Hurle
 *    Chris Casola
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.defecttracker.toolbar;

import javax.swing.JButton;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.DefaultToolbarView;
import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.janeway.gui.widgets.JPlaceholderTextField;
import edu.wpi.cs.wpisuitetng.modules.defecttracker.tabs.MainTabController;

/**
 * The Defect tab's toolbar panel.
 * Always has a group of global commands (Create Defect, Search).
 */
@SuppressWarnings("serial")
public class ToolbarView extends DefaultToolbarView {

	private JButton createDefect;
	private JButton searchDefects;
	private JPlaceholderTextField searchField;
	
	/**
	 * Create a ToolbarView.
	 * @param tabController The MainTabController this view should open tabs with
	 */
	public ToolbarView(MainTabController tabController) {

		// Construct the content panel
		JPanel content = new JPanel(new MigLayout("", "[][]", "[][]"));
		content.setOpaque(false);
				
		// Construct the create defect button
		createDefect = new JButton();
		createDefect.setAction(new CreateDefectAction(tabController));
		
		// Construct the search button
		searchDefects = new JButton("Search Defects");
		searchDefects.setAction(new SearchDefectsAction(tabController));
		
		// Construct the search field
		searchField = new JPlaceholderTextField("Lookup by ID", 25);
		searchField.addActionListener(new LookupDefectController(tabController, searchField, this));
		
		// Add buttons to the content panel
		content.add(createDefect, "cell 0 0, center");
		content.add(searchDefects, "cell 1 0, center");
		content.add(searchField, "cell 0 1, span 2, center");
		
		// Construct a new toolbar group to be added to the end of the toolbar
		ToolbarGroupView toolbarGroup = new ToolbarGroupView("Home", content);
		toolbarGroup.setPreferredWidth(content.getPreferredSize().width + 8);
		// Calculate the width of the toolbar
		addGroup(toolbarGroup);
	}

}
