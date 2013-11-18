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

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.EventListModel;

public class WeekView extends JPanel implements ICalendarView, ListDataListener {
	public static final String[] weekNames = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
	public static final String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	
	private ArrayList<DatePanel> nameList;
	private ArrayList<JPanel> hourlist;
	private HashMap<Date, DatePanel> paneltracker;

	private Calendar mycal;
	private int currentMonth;
	private int currentYear;
	private int currentDate;
	
	public WeekView(){
		nameList = new ArrayList<DatePanel>();
		paneltracker = new HashMap<Date, DatePanel>();
		hourlist = new ArrayList<JPanel>();
		
		Date today = new Date();
		
		int correctday = today.getDay();
		currentDate = today.getDate() - correctday;
		currentYear = today.getYear() + 1900;
		currentMonth = today.getMonth();
		
		mycal = new GregorianCalendar(currentYear, currentMonth, currentDate);
		this.setBackground(Color.white);
		
		this.setLayout(new MigLayout("fill", 
				"[8%][13%][13%][13%][13%][13%][13%][13%]", 
				"[4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%]"));
		
		this.setVisible(true);
		
		fillDayView();
		
		EventListModel.getEventListModel().addListDataListener(this);
	}
	
	public void fillDayView(){
		
		
		JPanel time = new JPanel();
		
		StringBuilder timebuilder = new StringBuilder();
		timebuilder.append("cell ");
		timebuilder.append("0");
		timebuilder.append(" ");
		timebuilder.append("0");
		timebuilder.append(",grow, push");
		
		time.add(new JLabel("Time"));
		time.setBackground(Color.blue);
		this.add(time, timebuilder.toString());
		
		for (int currenthour=0; currenthour < 24; currenthour++){
			JPanel hour = new JPanel();

			StringBuilder hourbuilder = new StringBuilder();
			hourbuilder.append("cell ");
			hourbuilder.append("0");
			hourbuilder.append(" ");
			hourbuilder.append(""+currenthour+1);
			hourbuilder.append(",grow, push");

			hour.add(new JLabel(currenthour+":00"));
			hour.setBackground(new Color(236,252,144));
			this.add(hour, hourbuilder.toString());
			hourlist.add(hour);
		}
		
		for (int currentday=1; currentday<8;currentday++){
			for (int currenthour=0; currenthour < 24; currenthour++){

				DatePanel thesecond = new DatePanel();

				StringBuilder datebuilder = new StringBuilder();
				datebuilder.append("cell ");
				datebuilder.append(""+currentday);
				datebuilder.append(" ");
				datebuilder.append(""+currenthour+1);
				datebuilder.append(",grow, push");

				Date hue = new Date(currentYear-1900, currentMonth, currentDate + (currentday - 1), currenthour, 0);
				thesecond.setDate(hue);
				thesecond.setBackground(Color.WHITE);
				thesecond.setBorder(new MatteBorder(1, 1, 1, 1, Color.gray));
				this.add(thesecond, datebuilder.toString());
				paneltracker.put(hue, thesecond);
				nameList.add(thesecond);
			}
		}
		
		
		for (int day = 0; day < 7; day++){
			JPanel event = new JPanel();

			StringBuilder eventbuilder = new StringBuilder();
			eventbuilder.append("cell ");
			eventbuilder.append(""+day+1);
			eventbuilder.append(" ");
			eventbuilder.append("0");
			eventbuilder.append(",grow, push");

			event.add(new JLabel(weekNames[day]));
			event.setBackground(Color.blue);
			this.add(event, eventbuilder.toString());

		}
	}
	
	@Override
	public String getTitle() {
		Date datdate = new Date(currentYear-1900, currentMonth, currentDate+6);
		int nextyear = datdate.getYear() + 1900;
		int nextmonth = datdate.getMonth();
		int nextdate = datdate.getDate();
		
		return monthNames[currentMonth] + " "+ currentDate + ", " + currentYear + " - " + monthNames[nextmonth] + " "+ nextdate + ", " + nextyear;
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
		currentDate = currentDate + 7;
		Date today = new Date(currentYear-1900, currentMonth, currentDate);
		currentDate = today.getDate();
		currentMonth = today.getMonth();
		currentYear = today.getYear() + 1900;
		
		this.removeAll();
		nameList = new ArrayList<DatePanel>();
		paneltracker = new HashMap<Date, DatePanel>();
		hourlist = new ArrayList<JPanel>();
		fillDayView();
		
		ChangeTheWorld();
	}

	@Override
	public void previous() {
		currentDate = currentDate - 7;
		Date today = new Date(currentYear-1900, currentMonth, currentDate);
		currentDate = today.getDate();
		currentMonth = today.getMonth();
		currentYear = today.getYear() + 1900;
		
		this.removeAll();
		nameList = new ArrayList<DatePanel>();
		paneltracker = new HashMap<Date, DatePanel>();
		hourlist = new ArrayList<JPanel>();
		fillDayView();
		
		ChangeTheWorld();
	}

	@Override
	public void today() {
		Date today = new Date();
		int correctday = today.getDay();
		currentDate = today.getDate() - correctday;
		currentMonth = today.getMonth();
		currentYear = today.getYear() + 1900;
		
		this.removeAll();
		nameList = new ArrayList<DatePanel>();
		paneltracker = new HashMap<Date, DatePanel>();
		hourlist = new ArrayList<JPanel>();
		fillDayView();
		
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
