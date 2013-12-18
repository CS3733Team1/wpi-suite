/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.view.commitment;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.commitment.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.event.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.filter.FilteredCommitmentsListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.list.ListItemListener;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.list.SRList;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.RightClickMenu;

public class CommitmentSubTabPanel extends JPanel implements ListItemListener<Commitment> {
	
	private SRList<Commitment> commitmentList;
	
	private CalendarPanel calendarPanel;
	
	public CommitmentSubTabPanel(CalendarPanel calendarPanel) {
		this.setLayout(new MigLayout("fill"));
		this.calendarPanel = calendarPanel;
		
		commitmentList = new SRList<Commitment>(FilteredCommitmentsListModel.getFilteredCommitmentsListModel());
		commitmentList.setListItemRenderer(new CommitmentListItemRenderer());
		commitmentList.addListItemListener(this);
		
		this.add(commitmentList, "grow");
	}
	
	public List<Commitment> getSelectedCommitments() {
		return commitmentList.getSelectedItems();
	}

	@Override
	public void itemsSelected(List<Commitment> listObjects) {}

	@Override
	public void itemDoubleClicked(Commitment listObject) {}

	@Override
	public void itemFocused(Commitment listObject) {}

	@Override
	public void itemRightClicked(Commitment listObject, Point p) {
		List<Commitment> listofCommitment = commitmentList.getSelectedItems();
		RightClickMenu menu = new RightClickMenu(new ArrayList<Event>(), listofCommitment, calendarPanel);
		menu.show(commitmentList, (int)p.getX(), (int)p.getY());
	}
}
