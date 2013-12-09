package edu.wpi.cs.wpisuitetng.modules.calendar.controller;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.FilterListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.MainView;
public class TeamPesonalCheckBoxChangeListener implements ChangeListener {

	@Override
	public void stateChanged(ChangeEvent arg0) {
		AbstractButton abstractButton =
		          (AbstractButton)arg0.getSource();
		        ButtonModel buttonModel = abstractButton.getModel();
		      if (buttonModel.isPressed() || buttonModel.isSelected())
		      {
		    	  System.out.println("In statechanged for Team/Personal Checkbox's");
		    	  MainView.getCurrentCalendarPanel().refreshSelectedPanel();
		    	  FilterListModel.getFilterListModel().fireFilterChanged();
		      }
		
	}

}
