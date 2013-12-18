package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.CalendarUtils;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.DateUtils;

/**
 * Panel to hold the hour labels for day view
 */
public class HourLabels extends JPanel{
	private ArrayList<JPanel> hourlist;
	
	/**
	 * draws a list of hours pertaining to day and week view
	 */
	public HourLabels(){
		hourlist = new ArrayList<JPanel>();
		
		int height = 1440;
		int width = 100;
		
		this.setSize(width, height);
		this.setPreferredSize(new Dimension(width, height));
		this.setMinimumSize(new Dimension(0,1440));
	}
	
	/**
	 * Allows the parent to resize this view to a specific width
	 * @param width size the parent wants this view to assume
	 */
	public void reSize(int width){
		this.setSize(width, 1440);
		this.setPreferredSize(new Dimension(width, 1440));
		this.setMinimumSize(new Dimension(0,1440));
		
		this.removeAll();
		
		this.repaint();
	}
	
	/**
	 * Overrides the paint borders, so that it can paint in the hour labels
	 * This method is called before other components are added, so they will not interfere with other items added to view
	 */
	public void paintBorder(Graphics g){

		super.paintBorder(g);

		drawHours(g);

		this.validate();
	}

	/**
	 * Draws The Hours Of DayView
	 * @param g Graphics which will draw lines representing hours
	 */
	public void drawHours(Graphics g){
		for (int x =0; x <= 23; x++){
			g.setColor(Color.LIGHT_GRAY);
			g.drawString(DateUtils.hourString(x),0,60*x);
		}
	}
}
