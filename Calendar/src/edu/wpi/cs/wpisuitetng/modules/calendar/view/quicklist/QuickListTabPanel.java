/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.calendar.view.quicklist;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.ISchedulable;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.QuickListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.commitment.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.event.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.list.ListItemListener;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.list.SRList;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.RightClickMenu;

/**
 * This class is used to calculate a list of the user's commitments that fall
 * within current view of the user's calendar.
 * 
 * A CommitmentQuickList has:
 * -The user's current view in the calendar
 * -An ArrayList of the commitments within the current view
 * 
 * @author Team TART
 *
 */
public class QuickListTabPanel extends JPanel implements ActionListener, ListItemListener<ISchedulable> {
	
	private SRList<ISchedulable> quickList;
	private JCheckBox eventsCheckBox;
	private JCheckBox commitmentsCheckBox;
	private CalendarPanel calendarPanel;
	
	public QuickListTabPanel(CalendarPanel calendarPanel) {
		super(new MigLayout("fill"));
		
		this.calendarPanel = calendarPanel;
		
		quickList = new SRList<ISchedulable>(QuickListModel.getQuickListModel());
		quickList.setListItemRenderer(new QuickListItemRenderer());
		quickList.addListItemListener(this);
		
		eventsCheckBox = new JCheckBox("Events");
		commitmentsCheckBox = new JCheckBox("Commitments");
		
		eventsCheckBox.setSelected(true);
		commitmentsCheckBox.setSelected(true);
		
		eventsCheckBox.addActionListener(this);
		commitmentsCheckBox.addActionListener(this);
		
		this.add(eventsCheckBox, "split 2, center");
		this.add(commitmentsCheckBox, "wrap");
		this.add(quickList, "grow, push");
	}
	
	public List<Commitment> getSelectedCommitments() {
		List<Commitment> rtnCommitments = new ArrayList<Commitment>();
		for(ISchedulable item: quickList.getSelectedItems()) {
			if(item instanceof Commitment) rtnCommitments.add((Commitment)item);
		}
		return rtnCommitments;
	}

	public List<Event> getSelectedEvents() {
		List<Event> rtnEvents = new ArrayList<Event>();
		for(ISchedulable item: quickList.getSelectedItems()) {
			if(item instanceof Event) rtnEvents.add((Event)item);
		}
		return rtnEvents;
	}

	
	@Override
	public void itemsSelected(List<ISchedulable> listObjects) {}

	@Override
	public void itemDoubleClicked(ISchedulable listObject) {}

	@Override
	public void itemFocused(ISchedulable listObject) {}

	@Override
	public void itemRightClicked(ISchedulable listObject, Point p) {
		RightClickMenu menu = new RightClickMenu(this.getSelectedEvents(), this.getSelectedCommitments(), calendarPanel);
		menu.show(quickList, (int)p.getX(), (int)p.getY());
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(eventsCheckBox.isSelected() && commitmentsCheckBox.isSelected()) {
			QuickListModel.getQuickListModel().setState(3);
		} else if(eventsCheckBox.isSelected()) {
			QuickListModel.getQuickListModel().setState(1);
		} else if(commitmentsCheckBox.isSelected()) {
			QuickListModel.getQuickListModel().setState(2);
		} else {
			QuickListModel.getQuickListModel().setState(0);
		}
	}
}