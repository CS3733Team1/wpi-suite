package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.month;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public abstract class EvComPanel extends JPanel {
	private boolean isMultiDay;
	protected boolean isSelected;
	
	protected Color backgroundColor;
	protected Color selectedBackgroundColor;
	protected Color textColor, selectedTextColor;
	
	public EvComPanel(boolean isMultiDay, boolean isFiller) {
		this.isMultiDay = isMultiDay;
		
		if(!isFiller) {
			this.addMouseListener(new MouseListener() {
	
				@Override
				public void mousePressed(MouseEvent e) {
					getParent().getParent().getParent().dispatchEvent(e);
				}
				
				@Override
				public void mouseReleased(MouseEvent e) {
					getParent().getParent().getParent().dispatchEvent(e);
				}
				
				@Override
				public void mouseExited(MouseEvent e) {}	
				@Override
				public void mouseClicked(MouseEvent e) 
				{
					if (e.getClickCount() == 2) getParent().getParent().getParent().dispatchEvent(e);
				}
				@Override
				public void mouseEntered(MouseEvent e) {}
			});
			
			this.addMouseMotionListener(new MouseMotionListener() {
				@Override
				public void mouseDragged(MouseEvent e) {
					MouseEvent newEvent = SwingUtilities.convertMouseEvent(EvComPanel.this, e, getParent().getParent().getParent());
					newEvent.setSource(getParent().getParent());
					getParent().getParent().getParent().dispatchEvent(newEvent);
				}
				@Override
				public void mouseMoved(MouseEvent e) {}
			});
		}
	}
	
	public boolean isMultiDay() {
		return this.isMultiDay;
	}

	public boolean isSelected()  {return this.isSelected;}
	
	public abstract void setSelected(boolean isSelected);
}
