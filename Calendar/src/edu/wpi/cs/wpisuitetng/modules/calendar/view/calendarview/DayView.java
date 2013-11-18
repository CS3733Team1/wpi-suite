package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.ListIterator;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.EventListModel;

import net.miginfocom.swing.MigLayout;

public class DayView extends JPanel implements ICalendarView, ListDataListener{
	public static final String[] weekNames = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
	public static final String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	
	private ArrayList<DatePanel> nameList;
	private ArrayList<JPanel> hourlist;
	private HashMap<Date, DatePanel> paneltracker;

	private Calendar mycal;
	private int currentMonth;
	private int currentYear;
	private int currentDate;
	
	public DayView(){
		nameList = new ArrayList<DatePanel>();
		paneltracker = new HashMap<Date, DatePanel>();
		
		Date today = new Date();
		
		currentYear = today.getYear() + 1900;
		currentMonth = today.getMonth();
		currentDate = today.getDate();

		mycal = new GregorianCalendar(currentYear, currentMonth, currentDate);
		this.setBackground(Color.white);
		
		this.setLayout(new MigLayout("fill", 
				"[20%][80%]", 
				"[4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%]"));
		
		EventListModel.getEventListModel().addListDataListener(this);
	}
	
	public void fillDayView(){
		for (int currenthour=0; currenthour < 24; currenthour++){
			JPanel hour = new JPanel();
			
			StringBuilder hourbuilder = new StringBuilder();
			hourbuilder.append("cell ");
			hourbuilder.append(0);
			hourbuilder.append(" ");
			hourbuilder.append(currenthour);
			hourbuilder.append(",grow, push");
			
			hour.add(new JLabel(currenthour+":00"));
			hour.setBackground(new Color(236,252,144));
			this.add(hour, hourbuilder);
			hourlist.add(hour);
			
			DatePanel thesecond = new DatePanel();
			
			StringBuilder datebuilder = new StringBuilder();
			datebuilder.append("cell ");
			datebuilder.append(1);
			datebuilder.append(" ");
			datebuilder.append(currenthour);
			datebuilder.append(",grow, push");
			
			Date hue = new Date(currentYear-1900, currentMonth, currentDate, currenthour, 0);
			thesecond.setDate(hue);
			thesecond.setBackground(Color.WHITE);
			thesecond.setBorder(new MatteBorder(0, 0, 1, 0, Color.gray));
			this.add(thesecond, datebuilder);
			paneltracker.put(hue, thesecond);
			nameList.add(thesecond);
		}
	}
	
	@Override
	public String getTitle() {
		return monthNames[currentMonth] + " "+ currentDate+ ", " + currentYear;
	}

	public void ClearEvents(){
		for (int x = 0;x < nameList.size(); x++){
			nameList.get(x).removeEventPanel();
		}
	}
	
	public void ChangeTheWorld(){
		Date key;
		
		ClearEvents();
		ListIterator<Event> event = EventListModel.getEventListModel().getList().listIterator();
		
		while(event.hasNext()){
			Event eve = event.next();
			Date evedate = eve.getStartDate();
			key = new Date(evedate.getYear(),evedate.getMonth(),evedate.getDate(),evedate.getHours(),0);
			
			if (paneltracker.containsKey(key)){
				paneltracker.get(key).addEventPanel(eve);
			}
		}
	}
	
	@Override
	public void next() {
		currentDate++;
		Date today = new Date(currentYear-1900, currentMonth, currentDate);
		currentDate = today.getDate();
		currentMonth = today.getMonth();
		currentYear = today.getYear() + 1900;
		
		ChangeTheWorld();
	}

	@Override
	public void previous() {
		currentDate--;
		Date today = new Date(currentYear-1900, currentMonth, currentDate);
		currentDate = today.getDate();
		currentMonth = today.getMonth();
		currentYear = today.getYear() + 1900;
		
		ChangeTheWorld();
	}

	@Override
	public void today() {
		Date today = new Date();
		currentDate = today.getDate();
		currentMonth = today.getMonth();
		currentYear = today.getYear() + 1900;
		
		ChangeTheWorld();
	}

	@Override
	public void intervalAdded(ListDataEvent e) {
		ChangeTheWorld();
	}

	@Override
	public void intervalRemoved(ListDataEvent e) {
		ChangeTheWorld();
	}

	@Override
	public void contentsChanged(ListDataEvent e) {
		ChangeTheWorld();
	}

}
