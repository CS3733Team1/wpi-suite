package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Date;
import java.util.List;

import org.jdesktop.swingx.JXMonthView;
import org.jdesktop.swingx.calendar.DateSelectionModel.SelectionMode;

public class DatePicker extends JXMonthView implements ActionListener, KeyListener{

	public static final Color START_END_DAY = new Color(47, 150, 9);
	public static final Color SELECTION = new Color(236,252,144);
	public static final Color UNSELECTABLE = Color.red;
	
	private Date startDate = null;
	private Date endDate = null;
	
	public DatePicker()
	{
		buildLayout();
	}
	
	public void buildLayout(){
		//set rows and columns of months displayed
		this.setPreferredColumnCount(1);
		this.setPreferredRowCount(1);
		
		this.setSelectionBackground(SELECTION);
		this.setFlaggedDayForeground(START_END_DAY);
		this.setSelectionMode(SelectionMode.SINGLE_INTERVAL_SELECTION);
		
		this.setAlignmentX(CENTER_ALIGNMENT);
		
		/*
		 * copied from iteration calendar, unsure of function so not deleting yet
		 */
//		this.addActionListener(this);
//		this.addMouseMotionListener(new MouseMotionListener()
//		{
//			@Override
//			public void mouseDragged(MouseEvent e) {				
//			}
//
//			@Override
//			public void mouseMoved(MouseEvent e) {
//				int x = e.getX();
//				int y = e.getY();
//				
//				Date forLocation = IterationCalendar.this.getDayAtLocation(x, y);
//				
//				if(forLocation != null)
//				{
//					List<Iteration> atDate = IterationModel.getInstance().getIterationForDate(forLocation);
//					
//					if(atDate.size() > 0)
//					{
//						String toolTipText = "<html>";
//						
//						for(Iteration it : atDate)
//						{
//							toolTipText += it.getName() + "<br>";
//							toolTipText += "&nbsp &nbsp Estimate: " + it.getEstimate() + "<br>";
//						}
//						toolTipText += "</html>";
//						IterationCalendar.this.setToolTipText(toolTipText);
//					}
//					else
//					{
//						IterationCalendar.this.setToolTipText(null);
//					}
//				}
//			}		
//		});
//		
//		this.addMouseListener(new MouseAdapter()
//		{
//			@Override
//			public void mouseClicked(MouseEvent e)
//			{
//				if(e.getClickCount() == 2)
//				{
//					int x = e.getX();
//					int y = e.getY();
//					Date forClick = IterationCalendar.this.getDayAtLocation(x, y);
//					
//					List<Iteration> forDate = IterationModel.getInstance().getIterationForDate(forClick);
//									
//					for(Iteration it : forDate)
//					{
//						ViewEventController.getInstance().editIteration(it);
//					}
//					
//				}	
//			}
//		});
//		
//		this.addKeyListener(this);
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
