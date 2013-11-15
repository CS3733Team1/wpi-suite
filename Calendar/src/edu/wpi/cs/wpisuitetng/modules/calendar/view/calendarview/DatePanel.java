package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.EventModel;

public class DatePanel extends JPanel implements ListDataListener{
	private Date paneldate;
	private ArrayList<JPanel> eventlist;
	
	public DatePanel(){
		eventlist = new ArrayList<JPanel>();
	}
	
	public void setDate(Date today){
		paneldate = today;
	}
	
	public Date getDate(){
		return paneldate;
	}
	
	public void addEventPanel(Event eve){
		System.out.println(paneldate.toString());
		System.out.println(eve.getStartDate().toString());
		JPanel eventpan = new JPanel();
		JTextArea eventdata = new JTextArea();
		eventdata.setText(eve.getName());
		eventpan.add(eventdata);
		
		eventdata.setSize(50,50);
		eventpan.setSize(50,50);
		
		eventdata.setVisible(true);
		eventpan.setVisible(true);
		
		this.add(eventpan);
		this.updateUI();
		eventlist.add(eventpan);
	}
	
	public void removeEventPanel(){
		for (int x = 0; x < eventlist.size(); x++){
			remove(eventlist.get(x));
		}
	}

	@Override
	public void intervalAdded(ListDataEvent e) {
		removeEventPanel();
		ListIterator<Event> event = EventModel.getEventModel().getList().listIterator();
		while(event.hasNext()){
			Event eve = event.next();
			Date evedate = eve.getStartDate();
			if (evedate.getYear() == paneldate.getYear() && evedate.getDay() == paneldate.getDay() && evedate.getMonth() == paneldate.getMonth()){
				addEventPanel(eve);
			}
		}
		
	}

	@Override
	public void intervalRemoved(ListDataEvent e) {
		removeEventPanel();
		ListIterator<Event> event = EventModel.getEventModel().getList().listIterator();
		while(event.hasNext()){
			Event eve = event.next();
			Date evedate = eve.getStartDate();
			if (evedate.getYear() == paneldate.getYear() && evedate.getDay() == paneldate.getDay() && evedate.getMonth() == paneldate.getMonth()){
				addEventPanel(eve);
			}
		}
	}

	@Override
	public void contentsChanged(ListDataEvent e) {
		removeEventPanel();
		ListIterator<Event> event = EventModel.getEventModel().getList().listIterator();
		while(event.hasNext()){
			Event eve = event.next();
			Date evedate = eve.getStartDate();
			if (evedate.getYear() == paneldate.getYear() && evedate.getDay() == paneldate.getDay() && evedate.getMonth() == paneldate.getMonth()){
				addEventPanel(eve);
			}
		}
	}
}
