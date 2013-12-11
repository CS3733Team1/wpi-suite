package edu.wpi.cs.wpisuitetng.modules.calendar.controller;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.CommitmentListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.EventListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.FilterListModel;
public class TeamPesonalCheckBoxChangeListener implements ItemListener {

	@Override
	public void itemStateChanged(ItemEvent e) {
		AbstractButton abstractButton = (AbstractButton)e.getSource();
		ButtonModel buttonModel = abstractButton.getModel();
		if (buttonModel.isPressed()) {
			FilterListModel.getFilterListModel().update();
			CommitmentListModel.getCommitmentListModel().update();
			EventListModel.getEventListModel().update();
		}
	}
}
