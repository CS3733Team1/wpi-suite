package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.week;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MultidayWeekListener implements MouseListener{
	private MultidayEventWeekView view;
	
	public MultidayWeekListener(MultidayEventWeekView v){
		view = v;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (MultidayEventWeekView.areEventsDisplaying()){
			MultidayEventWeekView.reverseEventsDisplaying();
			view.ClearEventDropDown();
		}
		else{
			view.ClearEventDropDown();
			MultidayEventWeekView.reverseEventsDisplaying();
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
		if (!MultidayEventWeekView.areEventsDisplaying()){
			view.DisplayEventDropDown();
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (!MultidayEventWeekView.areEventsDisplaying()){
			view.ClearEventDropDown();
		}
	}

}