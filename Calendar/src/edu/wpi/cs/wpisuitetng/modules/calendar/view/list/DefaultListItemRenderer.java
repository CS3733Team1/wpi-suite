package edu.wpi.cs.wpisuitetng.modules.calendar.view.list;

import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.CalendarUtils;

public class DefaultListItemRenderer<T> implements ListItemRenderer<T> {

	@Override
	public JComponent getRenderedListComponent(SRList<T> listPanel,
			T listObject, boolean isSelected, boolean hasFocus) {
		
		JPanel p = new JPanel();
		JLabel label = new JLabel(listObject.toString());
		p.add(label);
		
		p.setBackground(isSelected ? CalendarUtils.selectionColor : Color.WHITE);
		
		return p;
	}
}
