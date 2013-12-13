package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day2;

import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.ISchedulable;

public class ScheduleItem extends JPanel{
	private ISchedulable display;
	private int StartX, StartY, width, height;
	
	
	public ScheduleItem(ISchedulable item, int sx, int sy, int w, int h){
		display = item;
		StartX = sx;
		StartY = sy;
		width = w;
		height = h;
	}
	
	public void repaint(){
		System.err.println(StartX +" "+ StartY + " " + width + " " + height);
		
		this.setBounds(StartX, StartY, width, height);
		super.repaint();
	}
    
   
}
