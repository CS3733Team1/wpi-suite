package edu.wpi.cs.wpisuitetng.modules.calendar.view.buttons;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class TransparentButtonGroup implements MouseListener {

	private List<TransparentToggleButton> buttons;

	public TransparentButtonGroup() {
		buttons = new ArrayList<TransparentToggleButton>();
	}

	public void add(TransparentToggleButton b) {
		buttons.add(b);
		b.addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {
		TransparentToggleButton buttonPressed = (TransparentToggleButton)e.getSource();
		buttons.remove(buttonPressed);
		
		boolean noOtherSelected = true;
		
		for(TransparentToggleButton b: buttons) {
			if(b.isSelected()) {
				b.setSelected(false);
				noOtherSelected = false;
			}
		}
		
		if(noOtherSelected) buttonPressed.setSelected(true);
		
		buttons.add(buttonPressed);
	}
}
