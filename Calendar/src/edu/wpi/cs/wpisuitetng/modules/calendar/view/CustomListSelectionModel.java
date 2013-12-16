package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import javax.swing.DefaultListSelectionModel;

public class CustomListSelectionModel extends DefaultListSelectionModel {
	boolean gestureStarted = false;

    @Override
    public void setSelectionInterval(int index0, int index1) {
        if(!gestureStarted){
            if (isSelectedIndex(index0)) {
                super.removeSelectionInterval(index0, index1);
            } else {
                super.addSelectionInterval(index0, index1);
                //Makes default categories unselectable
                super.removeSelectionInterval(0, 4);
            }
        }
        gestureStarted = true;
    }

    @Override
    public void setValueIsAdjusting(boolean isAdjusting) {
        if (!isAdjusting) {
            gestureStarted = false;
        }
    }
}
