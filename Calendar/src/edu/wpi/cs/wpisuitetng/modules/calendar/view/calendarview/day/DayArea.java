package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.FilteredCommitmentsListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.FilteredEventsListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.ISchedulable;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.SchedMouseListener;
//import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.EventMouseListener;

//Height of This Panel is 1440 Calculation Dependent
public class DayArea extends JPanel implements ListDataListener{
	private static final int pixPerMin = 1;
	private static final int timePix = 0; //width reserved for times on lefthand side
	private static final String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};


	private List<ISchedulable> events;
	private List<Event> multi;

	private Date currentDay;
	private SchedulableResizer resizelistener;
	private SchedulableMover movelistener;

	/**
	 * Default constructor, which sets the days date to NOW
	 */
	public DayArea(){
		events = new LinkedList<ISchedulable>();
		multi = new LinkedList<Event>();
		resizelistener = new SchedulableResizer();
		movelistener = new SchedulableMover();

		currentDay = new Date();
		currentDay = new Date(currentDay.getYear(), currentDay.getMonth(), currentDay.getDate());

		int height = 1440;
		int width = 900;

		this.setSize(width, height);
		this.setPreferredSize(new Dimension(width, height));
		this.setMinimumSize(new Dimension(0,1440));

		showEvent();

		FilteredEventsListModel.getFilteredEventsListModel().addListDataListener(this);
		FilteredCommitmentsListModel.getFilteredCommitmentsListModel().addListDataListener(this);
		
		this.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e){
				SchedMouseListener.clearSelection();
			}
			@Override
			public void mousePressed(MouseEvent e) {}
			
			@Override
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseExited(MouseEvent e) {}
		});

	}

	/**
	 * Constructor used by Week View to get each day to assume as specific date
	 * @param d
	 */
	public DayArea(Date d){
		events = new LinkedList<ISchedulable>();
		multi = new LinkedList<Event>();
		resizelistener = new SchedulableResizer();
		movelistener = new SchedulableMover();

		currentDay = d;
		currentDay = new Date(currentDay.getYear(), currentDay.getMonth(), currentDay.getDate());

		int height = 1440;
		int width = 900;

		this.setSize(width, height);
		this.setPreferredSize(new Dimension(width, height));
		this.setMinimumSize(new Dimension(0,1440));

		showEvent();

		FilteredEventsListModel.getFilteredEventsListModel().addListDataListener(this);
		FilteredCommitmentsListModel.getFilteredCommitmentsListModel().addListDataListener(this);

	}

	/**
	 * Used to resize the day's width to match with parent
	 * @param width width of parent wants this class to assume
	 */
	public void reSize(int width){
		this.setSize(width, 1440);
		this.setPreferredSize(new Dimension(width, 1440));
		this.setMinimumSize(new Dimension(0,1440));

		this.repaint();
	}

	/**
	 * Paints the borders of the view
	 * This method happens before child components are painted
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
		for (int x =0; x <= 24; x++){
			g.setColor(Color.LIGHT_GRAY);
			g.drawLine(0, 60*x, this.getWidth(), 60*x);
		}
	}

	private boolean isBetween(Date test, Date start, Date end){
		if(test.getHours() > start.getHours() &&
				test.getHours() < end.getHours()){
			return true;
		}
		else if(test.getHours() > start.getHours() &&
				test.getHours() == end.getHours()){
			if(test.getMinutes() < end.getMinutes()){
				return true;
			}
		}
		else if(test.getHours() == start.getHours() &&
				test.getHours() < end.getHours()){
			if(test.getMinutes() > start.getMinutes()){
				return true;
			}
		}
		else if(test.getHours() == start.getHours() &&
				test.getHours() == end.getHours()){
			if(test.getMinutes() > start.getMinutes() &&
					test.getMinutes() < end.getMinutes()){
				return true;
			}
		}
		return false;
	}

	/**
	 * Determines whether two Schedulable items overlap
	 * @param e1 First Item (Comes before e2)
	 * @param e2 Second Item (Comes after e1)
	 * @return whether two Schedulable items overlap
	 */
	private boolean overlapEvent(ISchedulable e1,ISchedulable e2){

		if(isBetween(e1.getStartDate(),e2.getStartDate(),e2.getEndDate()) ||
				isBetween(e1.getEndDate(),e2.getStartDate(),e2.getEndDate())||
				isBetween(e2.getStartDate(),e1.getStartDate(),e1.getEndDate()) ||
				isBetween(e2.getEndDate(),e1.getStartDate(),e1.getEndDate()) ||
				(e1.getStartDate().getHours()==e2.getStartDate().getHours() &&
				 e1.getStartDate().getMinutes()==e2.getStartDate().getMinutes() &&
				 e1.getEndDate().getHours()==e2.getEndDate().getHours() &&
				 e1.getEndDate().getMinutes()==e2.getEndDate().getMinutes())){
			return true;
		}
		return false;
	}

	/**
	 * Calculates the length of a scheudulable item based off startdate and enddate
	 * @param sched The instance to be calculated
	 * @return length of sched in minutes
	 */
	private int getLengthMinutes(ISchedulable sched){
		int length;
		length = (sched.getEndDate().getHours()-sched.getStartDate().getHours())*60;
		length += sched.getEndDate().getMinutes()-sched.getStartDate().getMinutes();
		return length;
	}

	/**
	 * Compares an ISchedulable for overlaps with a list of ISchedulables
	 * @param e1 event to be compared
	 * @param eventList list to be compared with
	 * @return ArrayList<ISchedulable> of items from the list that overlap with the test item
	 */
	private ArrayList<ISchedulable> overlapList(ISchedulable e1, ArrayList<ISchedulable> eventList){
		ArrayList<ISchedulable> overlaps = new ArrayList<ISchedulable>();
		for(ISchedulable e2:eventList){
			if(overlapEvent(e1,e2) && !e1.equals(e2)){
				overlaps.add(e2);
			}
		}
		return overlaps;
	}

	/**
	 * Sorts the List of Events by StartDate
	 */
	 public void sortEvents(){
		Collections.sort(events, new Comparator<ISchedulable>(){
			public int compare(ISchedulable o1, ISchedulable o2) {
				if(o1.getStartDate().equals(o2.getStartDate())){
					return getLengthMinutes(o1)-getLengthMinutes(o2);
				}
				return o1.getStartDate().compareTo(o2.getStartDate());
			}

		});
	}

	 /**
	  * Generates a 2D ArrayList indicating which column ISchedulables should be placed in
	  * on the calendar
	  * @return ArrayList<ArrayList<ISchedulable>> indicating what column events should be displayed in
	  */
	private ArrayList<ArrayList<ISchedulable>> generateMap(){
		sortEvents();
		ArrayList<ArrayList<ISchedulable>> map = new ArrayList<ArrayList<ISchedulable>>();

		for(int i=0;i<events.size();i++){
			boolean added=false;
			/* 
			 * starting from first column, test if event can be placed there,
			 * if yes, place event
			 */
			for(int j=0;j<map.size();j++){
				ArrayList<ISchedulable> testList = map.get(j);
				if(!added && !overlapEvent(events.get(i),testList.get(testList.size()-1))){
					map.get(j).add(events.get(i));
					added=true;
				}
			}
			/*
			 * if event could not be placed in any of existing columns, add a new column
			 */
			if(!added){
				ArrayList<ISchedulable> newList = new ArrayList<ISchedulable>();
				newList.add(events.get(i));
				map.add(newList);
			}
		}
		return map;
	}

	/**
	 * Take the 2D ArrayList generated by generateMap and display the events on the calendar
	 * @param map ArrayList<ArrayList<ISchedulable>> returned by generateMap
	 */
	private void displayMap(ArrayList<ArrayList<ISchedulable>> map){
		for(int i=0;i<map.size();i++){
			/*
			 * for each event in the column
			 */
			for(ISchedulable test:map.get(i)){
				ArrayList<ISchedulable> overlapEvents = new ArrayList<ISchedulable>();
				int divisions=1;
				/*
				 * determine the number of columns with events that overlap this one
				 * store the events in an ArrayList, and the number of columns in an integer
				 */
				for(int j=0;j<map.size();j++){
					if(j!=i){
						ArrayList<ISchedulable> overlaps = overlapList(test,map.get(j));
						if(overlaps.size()>0){
							divisions++;
							overlapEvents.addAll(overlaps);
						}
					}
				}
				/*
				 * determine the maximum number of columns needed to display any of
				 * the events that overlap with this one, use that as the number of
				 * columns to display this event with
				 */
				for(ISchedulable test2:overlapEvents){
					int eventDivs=1;
					for(int j=0;j<map.size();j++){
						if(overlapList(test2,map.get(j)).size()>0){
							eventDivs++;
						}
					}
					if(eventDivs>divisions){
						divisions=eventDivs;
					}
				}


				//horizontal start: space for times on left, plus number of events to left times width of each event
				int startXPixel = timePix + i*(this.getWidth()/divisions);


				//vertical start: pixels per minute
				int startMin = test.getStartDate().getHours()*60;
				startMin += test.getStartDate().getMinutes();
				int startYPixel = startMin*pixPerMin;

				//width: space for all events divided by number of columns to divide space into
				int pixelWidth = this.getWidth()/divisions;

				//height: pixels per minute
				int endMin = startMin + getLengthMinutes(test);

				JPanel panel = new ScheduleItem(test, startXPixel, startYPixel, pixelWidth, getLengthMinutes(test), divisions, i);

				JLabel name = new JLabel(test.getName());
				panel.add(name, "wmin 0");

				//Format information for tooltip
				StringBuilder infobuilder = new StringBuilder();
				infobuilder.append("<html><p style='width:175px'><b>Name: </b>");
				infobuilder.append(test.getName());

				//start and end dates/times
				if(test instanceof Event)
				{
					infobuilder.append("<br><b>Start: </b>");
					infobuilder.append(DateFormat.getInstance().format(test.getStartDate()));
					infobuilder.append("<br><b>End: </b>");
					infobuilder.append(DateFormat.getInstance().format(test.getEndDate()));
				}
				else if(test instanceof Commitment)
				{
					infobuilder.append("<br><b>Due: </b>");
					infobuilder.append(DateFormat.getInstance().format(test.getStartDate()));
				}

				//category
				infobuilder.append("<br><b>Category: </b>");
				infobuilder.append(test.getCategory().getName());

				//description
				if(test.getDescription() != null && test.getDescription().length()>0){
					infobuilder.append("<br><b>Description: </b>");
					infobuilder.append(test.getDescription());
				}
				infobuilder.append("</p></html>");
				panel.setToolTipText(infobuilder.toString());

				//add mouse listener to event panels, change tooltip background, allow for selection
				panel.addMouseListener(new SchedMouseListener(test, panel));
				//http://www.camick.com/java/source/ComponentResizer.java
				if (test instanceof Event){
					resizelistener.registerComponent(panel);
				}
				movelistener.registerComponent(panel);

				//set panel background based on category
				panel.setBackground(test.getCategory().getColor());

				//determine if text should be white or black
				Color catColor=test.getCategory().getColor();
				float[] hsb=new float[3];
				hsb=Color.RGBtoHSB(catColor.getRed(), catColor.getGreen(), catColor.getBlue(), hsb);
				if(hsb[2]<0.5){
					name.setForeground(Color.WHITE);
				}
				else{
					name.setForeground(Color.BLACK);
				}

				panel.setFocusable(true);

				this.add(panel, "wmin 0");
			}
		}
	}

	public void findSchedulableItems(){
		Date key;
		events = new LinkedList<ISchedulable>();
		multi = new LinkedList<Event>();

		List<Event> event = FilteredEventsListModel.getFilteredEventsListModel().getList();
		List<Commitment> comm = FilteredCommitmentsListModel.getFilteredCommitmentsListModel().getList();


		for (Event eve: event){
			Date evedate = eve.getStartDate();
			key = new Date(evedate.getYear(),evedate.getMonth(),evedate.getDate());
			if (currentDay.compareTo(key) == 0){
				if(eve.getStartDate().getDate() == eve.getEndDate().getDate()
						&& eve.getStartDate().getMonth() == eve.getEndDate().getMonth()
						&& eve.getStartDate().getYear() == eve.getEndDate().getYear())
					events.add(eve);
				else{
					multi.add(eve);	
				}
			}
			else if(currentDay.after(eve.getStartDate()) && currentDay.before(eve.getEndDate()))
			{
				multi.add(eve);
			}
		}

		for (Commitment c: comm){
			Date cdate = c.getStartDate();
			key = new Date(cdate.getYear(),cdate.getMonth(),cdate.getDate());
			if (currentDay.compareTo(key) == 0){
				if(c.getStartDate().getDate() == c.getEndDate().getDate()
						&& c.getStartDate().getMonth() == c.getEndDate().getMonth()
						&& c.getStartDate().getYear() == c.getEndDate().getYear())
					events.add(c);	
			}
		}
	}

	/**
	 * Creates a panel to be displayed on the day view
	 * @return JPanel generated
	 */
	public void showEvent()
	{
		this.removeAll();
		events.clear();
		multi.clear();
		findSchedulableItems();
		ArrayList<ArrayList<ISchedulable>> eventMap = new ArrayList<ArrayList<ISchedulable>>();
		eventMap=generateMap();
		displayMap(eventMap);
		this.revalidate();
		this.repaint();
	}

	public List<Event> getMultiDayEvents(){
		findSchedulableItems();
		return multi;
	}

	public Date getDayViewDate(){
		return currentDay;
	}

	public String getTitle() {
		return monthNames[currentDay.getMonth()] + " "+ currentDay.getDate()+ ", " + (currentDay.getYear() + 1900);
	}

	public void next() {
		currentDay.setDate(currentDay.getDate()+1);
		showEvent();
	}

	public void previous() {
		currentDay.setDate(currentDay.getDate()-1);
		showEvent();
	}

	public void today() {
		currentDay = new Date();
		currentDay = new Date(currentDay.getYear(), currentDay.getMonth(), currentDay.getDate());
		showEvent();
	}

	public void viewDate(Calendar date) {
		if (currentDay.compareTo(date.getTime()) != 0){
			currentDay = date.getTime();
			showEvent();
		}
	}

	public void viewDate(Date date){
		if (currentDay.compareTo(date) != 0){
			currentDay = date;
			showEvent();
		}
	}

	@Override
	public void intervalAdded(ListDataEvent e) {
		showEvent();
	}

	@Override
	public void intervalRemoved(ListDataEvent e) {
		showEvent();
	}

	@Override
	public void contentsChanged(ListDataEvent e) {
		showEvent();
	}
}
