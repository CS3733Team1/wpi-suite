package edu.wpi.cs.wpisuitetng.modules.calendar.view.scheduledevent;
//package edu.wpi.cs.wpisuitetng.modules.calendar.view.scheduledevent;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import net.miginfocom.swing.MigLayout;
//import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.CalendarUtils;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.CalendarUtils;

public class DayOfWeekPanel extends JPanel {

	private int previoiusLocationY;
	private int dragLocationX;
	private int dragLocationY;
	private int screenX;
	private int screenY;
	private int myX;
	private int myY;
	private boolean isDragged;
	private boolean isAdding;
	private boolean isPressed;
	private int start;
	private int end;
	private ArrayList<DayOfWeek> listDay;
	
	public DayOfWeekPanel()
	{
		this.start = 0;
		this.end = 7;
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < end-start; i++)
		{
			float percentage = (float)(1.0/(end-start))*100;
			sb.append("0[");
			sb.append(percentage);
			sb.append("%]0");
		}
		this.setBorder(new MatteBorder(1,1,1,1,CalendarUtils.lineColor));
		listDay = new ArrayList<DayOfWeek>();
		this.setLayout(new MigLayout("fill,insets 0,gapy 0", 
				sb.toString(),"0[]0"));
		addDays();
		addMouseListener(new MouseListener() {

		      @Override
		      public void mouseClicked(MouseEvent e) {
		    	  int index = listDay.indexOf(DayOfWeekPanel.this.getComponentAt(e.getPoint()));
		    	  listDay.get(index).setState(!(listDay.get(index).getState()));
		    	  
		    	  DayOfWeekPanel.this.updateList();
		      }

		      @Override
		      public void mousePressed(MouseEvent e) {
		    	  int index = listDay.indexOf(DayOfWeekPanel.this.getComponentAt(e.getPoint()));
		    	  if(!listDay.get(index).isSelected()){
		    		  isAdding = true;
		    	  }else{
		    		  isAdding = false;
		    	  }
		    	  
		      }

		      @Override
		      public void mouseReleased(MouseEvent e) {
		    	  int deltaX = e.getXOnScreen() - screenX;
		          int deltaY = e.getYOnScreen() - screenY;
		    	  if(isDragged){
		    	  }
		    	  isPressed = false;
		    	  isDragged = false;
		    	 
		      }

		      @Override
		      public void mouseEntered(MouseEvent e) { }

		      @Override
		      public void mouseExited(MouseEvent e) { }

		    });
		    addMouseMotionListener(new MouseMotionListener() {

		      @Override
		      public void mouseDragged(MouseEvent e) {
		    	  
		        int dragLocationX = e.getXOnScreen();
		        int dragLocationY = e.getYOnScreen();
		        
		        int index = listDay.indexOf(DayOfWeekPanel.this.getComponentAt(e.getPoint()));
		        if(Math.abs(dragLocationY-previoiusLocationY) >= 0){
		        	if(index > 0)
		        		if(isAdding)
		        			listDay.get(index).setState(true);
		        		else
		        			listDay.get(index).setState(false);
		        }
		         previoiusLocationY = e.getYOnScreen();
		         
		        DayOfWeekPanel.this.updateList();
		         
		      }

		      @Override
		      public void mouseMoved(MouseEvent e) { }

		    });
		  }
	public ArrayList<String> updateList() {
		ArrayList<String> tempListStorage = new ArrayList<String>();
		for(int i = 0; i < listDay.size(); i++)
		{
			if(listDay.get(i).getState())
				tempListStorage.add(listDay.get(i).getDayString());
		}
		
		if(tempListStorage.size() > 0){
			ScheduleEventSetup swp =(ScheduleEventSetup) this.getParent();
			swp.isDayOfWeekHaveDays(true);
		}else{
			ScheduleEventSetup swp =(ScheduleEventSetup) this.getParent();
			swp.isDayOfWeekHaveDays(false);
		}
		
		return tempListStorage;
	}
	public void updatePanels(int index, boolean state)
	{
		
	}
	public ArrayList<DayOfWeek> getHourList()
	{
		return listDay;
	}

	public void setHour(DayOfWeek setHour)
	{
		listDay.get(setHour.getDay()-start).setState(setHour.getState());
	}
	
	public void setHourList(ArrayList<DayOfWeek> hours)
	{
		for(int i = 0; i < hours.size(); i++)
		{
			setHour(hours.get(i));
		}
	}
	
	private void addDays() {
		for(int i = 0; i < end-start; i++)
		{	
			DayOfWeek day = new DayOfWeek();
			day.setDay(i);
			day.setState(false);
			listDay.add(day);
			this.add(day, "cell "+i+" 0, grow, push");
			
		}
		
	}
	
	
}
