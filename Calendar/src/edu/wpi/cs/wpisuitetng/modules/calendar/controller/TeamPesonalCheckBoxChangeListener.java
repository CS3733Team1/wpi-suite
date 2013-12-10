package edu.wpi.cs.wpisuitetng.modules.calendar.controller;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.CommitmentListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.EventListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.FilterListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.CalendarPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.MainView;
public class TeamPesonalCheckBoxChangeListener implements ItemListener {

	@Override
	public void itemStateChanged(ItemEvent arg0) {
		// TODO Auto-generated method stub
		AbstractButton abstractButton =
		          (AbstractButton)arg0.getSource();
		        ButtonModel buttonModel = abstractButton.getModel();
		      if (buttonModel.isPressed())
		      {
		    	  System.out.println("In statechanged for Team/Personal Checkboxs");
		    	  FilterListModel.getFilterListModel().Update();
		    	  CommitmentListModel.getCommitmentListModel().Update();
		    	  EventListModel.getEventListModel().Update();
		      }
	}

}
