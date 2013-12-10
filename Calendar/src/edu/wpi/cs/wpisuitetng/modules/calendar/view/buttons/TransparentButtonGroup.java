package edu.wpi.cs.wpisuitetng.modules.calendar.view.buttons;

import java.util.ArrayList;
import java.util.List;

public class TransparentButtonGroup {

	private List<TransparentToggleButton> buttons;

	public TransparentButtonGroup() {
		buttons = new ArrayList<TransparentToggleButton>();
	}

	public void add(TransparentToggleButton b) {
		buttons.add(b);
	}
	
	public void setSelectedButton(int index) {		
		for(TransparentToggleButton b: buttons) {
			if(b.isSelected()) {
				b.setSelected(false);
			}
		}
		
		buttons.get(index).setSelected(true);
	}
}
