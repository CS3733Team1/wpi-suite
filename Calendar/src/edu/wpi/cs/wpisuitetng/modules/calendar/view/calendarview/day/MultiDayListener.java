package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MultiDayListener implements MouseListener{
	private MultidayEventView view;
	
	public MultiDayListener(MultidayEventView v){
		view = v;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (MultidayEventView.areEventsDisplaying()){
			MultidayEventView.reverseEventsDisplaying();
			view.ClearEventDropDown();
		}
		else{
			view.ClearEventDropDown();
			MultidayEventView.reverseEventsDisplaying();
			view.DisplayEventDropDown();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (!MultidayEventView.areEventsDisplaying()){
			view.DisplayEventDropDown();
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (!MultidayEventView.areEventsDisplaying()){
			view.ClearEventDropDown();
		}
	}

}
