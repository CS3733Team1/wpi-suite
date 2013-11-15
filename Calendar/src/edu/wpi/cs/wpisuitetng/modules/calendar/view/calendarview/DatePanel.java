package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.EventModel;

public class DatePanel extends JPanel implements ListDataListener{
	private Date paneldate;
	private ArrayList<JPanel> eventlist;
	private JScrollPane showMEdaMONEY;
	private JPanel layout;
	private GridLayout grid;
	
	private boolean temp;
	
	public DatePanel(){
		grid = new GridLayout(0,1);
		eventlist = new ArrayList<JPanel>();
		showMEdaMONEY = new JScrollPane();
		layout = new JPanel();
		
		this.setLayout(grid);
		layout.setLayout(grid);
		showMEdaMONEY.setVisible(true);
		layout.setVisible(true);
		
		showMEdaMONEY.getViewport().add(layout);
		showMEdaMONEY.setOpaque(false);
		
		//showMEdaMONEY.setViewportView(layout);
		
		temp = false;
		
	}
	
	public void setDate(Date today){
		paneldate = today;
	}
	
	public Date getDate(){
		return paneldate;
	}
	
	public void addEventPanel(Event eve){
		JPanel eventpan = new JPanel();
		JTextPane eventdata = new JTextPane();
		eventdata.setText(eve.getName());
		eventdata.setEditable(false);
		eventpan.add(eventdata);
		
		eventpan.setOpaque(false);
		
		eventdata.setVisible(true);
		eventpan.setVisible(true);
		
		layout.add(eventpan);
		this.updateUI();
		eventlist.add(eventpan);
		
		if (temp == false){
			showMEdaMONEY.getViewport().setOpaque(false);
			
			add(showMEdaMONEY);
			temp = true;
		}
		
	}
	
	public void removeEventPanel(){
		for (int x = 0; x < eventlist.size(); x++){
			layout.remove(eventlist.get(x));
		}
		if (temp == true){
			remove(showMEdaMONEY);
		}
		temp = false;
	}
	
	public void updatePanel(){
		removeEventPanel();
		ListIterator<Event> event = EventModel.getEventModel().getList().listIterator();
		while(event.hasNext()){
			Event eve = event.next();
			Date evedate = eve.getStartDate();
			if (evedate.getYear() == paneldate.getYear() && evedate.getDate() == paneldate.getDate() && evedate.getMonth() == paneldate.getMonth()){
				addEventPanel(eve);
			}
		}
	}

	@Override
	public void intervalAdded(ListDataEvent e) {
		updatePanel();
	}

	@Override
	public void intervalRemoved(ListDataEvent e) {
		updatePanel();
	}

	@Override
	public void contentsChanged(ListDataEvent e) {
		updatePanel();
	}
}
