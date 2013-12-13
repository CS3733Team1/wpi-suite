package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day2;

import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.ISchedulable;

public class ScheduleItem extends JPanel{
	private ISchedulable display;
	private int StartX, StartY, EndX, EndY;
	
	
	public ScheduleItem(ISchedulable item, int sx, int sy, int ex, int ey){
		display = item;
		StartX = sx;
		StartY = sy;
		EndX = ex;
		EndY = ey;
	}
	
	public void repaint(){
		this.setBounds(StartX, StartY, EndX - StartX, EndY - StartY);
		super.repaint();
	}
    
   
}
