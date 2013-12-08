package edu.wpi.cs.wpisuitetng.modules.calendar.controller;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.FilterListModel;
public class TeamPesonalCheckBoxChangeListener implements ChangeListener {

	@Override
	public void stateChanged(ChangeEvent arg0) {
		AbstractButton abstractButton =
		          (AbstractButton)arg0.getSource();
		        ButtonModel buttonModel = abstractButton.getModel();
		      if (buttonModel.isPressed() || buttonModel.isSelected())
		      {
		    	  FilterListModel.getFilterListModel().fireFilterChanged();
		      }
		
	}

}
