package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day2;

import java.awt.Dimension;

import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.ISchedulable;

public class ScheduleItem extends JPanel{
	private ISchedulable display;
	private int StartX, StartY, width, height, division, location;
	
	
	public ScheduleItem(ISchedulable item, int sx, int sy, int w, int h, int div, int spot){
		display = item;
		StartX = sx;
		StartY = sy;
		width = w;
		height = h;
		division = div;
		location = spot;
		
		this.setMinimumSize(new Dimension(0,0));
	}
	
	public void repaint(){
		if (this.getParent() != null){
			 StartX = location*(this.getParent().getWidth()/division);
             width = this.getParent().getWidth()/division;
             System.err.println(this.getParent().getWidth());
		}
		
		this.setBounds(StartX, StartY, width, height);
		
		super.repaint();
	}
    
   
}
