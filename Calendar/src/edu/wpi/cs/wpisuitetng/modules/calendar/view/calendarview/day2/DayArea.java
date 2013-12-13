package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day2;

import java.awt.Color;
import java.awt.Graphics;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.FilteredCommitmentsListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.FilteredEventsListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.ISchedulable;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.EventMouseListener;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.ICalendarView;

//Height of This Panel is 1440 Calculation Dependent
public class DayArea extends JPanel implements ICalendarView, ListDataListener{
	private static final int pixPerMin = 1;
    private static final int timePix = 0; //width reserved for times on lefthand side
    
    private List<ISchedulable> events;
    
    private Date currentDay;
    
	public DayArea(){
		events = new LinkedList<ISchedulable>();
		
		currentDay = new Date();
		currentDay = new Date(currentDay.getYear(), currentDay.getMonth(), currentDay.getDate());
		this.setSize(1000, 1440);
		showEvent();
		
		FilteredEventsListModel.getFilteredEventsListModel().addListDataListener(this);
		FilteredCommitmentsListModel.getFilteredCommitmentsListModel().addListDataListener(this);
	}
	
	public void paint(Graphics g){
		super.paint(g);
		
		drawHours(g);
		super.paintComponents(g);
	}
	
	/**
	 * Draws The Hours Of DayView
	 * @param g Graphics which will draw lines representing hours
	 */
	public void drawHours(Graphics g){
		for (int x =1; x <= 24; x++){
			g.setColor(Color.BLACK);
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
    
     private boolean overlapEvent(ISchedulable e1,ISchedulable e2){
            if(isBetween(e1.getStartDate(),e2.getStartDate(),e2.getEndDate()) ||
                         isBetween(e1.getEndDate(),e2.getStartDate(),e2.getEndDate())||
                         isBetween(e2.getStartDate(),e1.getStartDate(),e1.getEndDate()) ||
                         isBetween(e2.getEndDate(),e1.getStartDate(),e1.getEndDate()) ||
                         (e1.getStartDate() == e2.getStartDate() && e1.getEndDate() == e2.getEndDate())){
                   return true;
            }
            return false;
     }
    
     private int getLengthMinutes(ISchedulable sched){
         int length;
         length = (sched.getEndDate().getHours()-sched.getStartDate().getHours())*60;
         length += sched.getEndDate().getMinutes()-sched.getStartDate().getMinutes();
         return length;
     }
     
     private ArrayList<ISchedulable> overlapList(ISchedulable e1, ArrayList<ISchedulable> eventList){
            ArrayList<ISchedulable> overlaps = new ArrayList<ISchedulable>();
            for(ISchedulable e2:eventList){
                   if(overlapEvent(e1,e2)){
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
 				return o1.getStartDate().compareTo(o2.getStartDate());
 			}
 			
 		});
 	}
     
     private ArrayList<ArrayList<ISchedulable>> generateMap(){
            sortEvents();
            ArrayList<ArrayList<ISchedulable>> map = new ArrayList<ArrayList<ISchedulable>>();
           
            for(int i=0;i<events.size();i++){
                   boolean added=false;
                   for(int j=0;j<map.size();j++){
                         ArrayList<ISchedulable> testList = map.get(j);
                         if(!overlapEvent(events.get(i),testList.get(testList.size()-1))){
                                map.get(j).add(events.get(i));
                                added=true;
                         }
                   }
                   if(!added){
                         ArrayList<ISchedulable> newList = new ArrayList<ISchedulable>();
                         newList.add(events.get(i));
                         map.add(newList);
                   }
            }
           
            return map;
     }
    
     private void displayMap(ArrayList<ArrayList<ISchedulable>> map){
            for(int i=0;i<map.size();i++){
                   for(ISchedulable test:map.get(i)){
                         ArrayList<ISchedulable> overlapEvents = new ArrayList<ISchedulable>();
                         int divisions=1;
                         for(int j=0;j<map.size();j++){
                                if(j!=i){
                                       ArrayList<ISchedulable> overlaps = overlapList(test,map.get(j));
                                       if(overlaps.size()>0){
                                              divisions++;
                                              overlapEvents.addAll(overlaps);
                                       }
                                }
                         }
                         for(ISchedulable test2:overlapEvents){
                                int eventDivs=0;
                                for(int j=0;j<map.size();j++){
                                       if(overlapList(test2,map.get(j)).size()>0){
                                              eventDivs++;
                                       }
                                }
                                if(eventDivs>divisions){
                                       divisions=eventDivs;
                                }
                         }
                        
                         //TODO replace with new event panel class, add to this
                         
                        
                         //horizontal start: space for times on left, plus number of events to left times width of each event
                                //constant + i*(totSPace/divisions)
                         int startXPixel = timePix + i*(this.getWidth()/divisions);
                        
                        
                         //vertical start: pixels per minute
                         int startMin = test.getStartDate().getHours()*60;
                         startMin += test.getStartDate().getMinutes();
                         int startYPixel = startMin*pixPerMin;
                        
                         //width: space for all events divided by number of events to share with (divisions)
                                //totSpace/divisions
                         int pixelWidth = this.getWidth()/divisions;
                        
                         //height: pixels per minute
                         int endMin = startMin + getLengthMinutes(test);
                         int endYPixel = endMin * pixPerMin;
                         
                         int endXPixel = startXPixel + pixelWidth;

                         JPanel panel = new ScheduleItem(test, startXPixel, startYPixel, endYPixel, endXPixel);
                         
                         JLabel name = new JLabel(test.getName());
                         //TODO align name in center, set minimum width to 0, maximum width to panel width
                                //panel width is totSpace/divisions
                         panel.add(name);
                        
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
                         if(test.getCategory()!=null){
                                infobuilder.append("<br><b>Category: </b>");
                                infobuilder.append(test.getCategory().getName());
                         }
                        
                         //description
                         if(test.getDescription().length()>0){
                                infobuilder.append("<br><b>Description: </b>");
                                infobuilder.append(test.getDescription());
                          }
                        
                         infobuilder.append("</p></html>");
                         panel.setToolTipText(infobuilder.toString());
                        
                         //add mouse listener to event panels, change tooltip background, allow for selection
                         if(test instanceof Event)
                                panel.addMouseListener(new EventMouseListener((Event) test, panel));
                        
                         //set panel background based on category
                         if (test.getCategory() != null){
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
                         }
                         else{
                                panel.setBackground(Color.CYAN);
                         }
                        
                         panel.setFocusable(true);
                        
                         //TODO add panel w/ new constraint system (pixels)
                         this.add(panel);
                   }
            }
     }
    
     public void findSchedulableItems(){
    	 Date key;
    	 
    	ListIterator<Event> event = FilteredEventsListModel.getFilteredEventsListModel().getList().listIterator();
 		ListIterator<Commitment> comm = FilteredCommitmentsListModel.getFilteredCommitmentsListModel().getList().listIterator();
 		
 		while(event.hasNext()){
			Event eve = event.next();
			Date evedate = eve.getStartDate();
			key = new Date(evedate.getYear(),evedate.getMonth(),evedate.getDate(),evedate.getHours(),0);
			if (currentDay.compareTo(key) == 0){
				if(eve.getStartDate().getDate() == eve.getEndDate().getDate()
				&& eve.getStartDate().getMonth() == eve.getEndDate().getMonth()
				&& eve.getStartDate().getYear() == eve.getEndDate().getYear())
					events.add(eve);
				else{
					//multi.add(eve);	
				}
			}
			else if(currentDay.after(eve.getStartDate()) && currentDay.before(eve.getEndDate()))
			{
				//multi.add(eve);
			}
		}
		
		while(comm.hasNext()){
			Commitment c = comm.next();
			Date cdate = c.getStartDate();
			key = new Date(cdate.getYear(),cdate.getMonth(),cdate.getDate(),cdate.getHours(),0);
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
    	 	findSchedulableItems();
            ArrayList<ArrayList<ISchedulable>> eventMap = new ArrayList<ArrayList<ISchedulable>>();
            eventMap=generateMap();
            displayMap(eventMap);
     }
	
	
	@Override
	public String getTitle() {
		return "hello";
	}

	@Override
	public void next() {
		currentDay.setDate(currentDay.getDate()+1);
		showEvent();
	}

	@Override
	public void previous() {
		currentDay.setDate(currentDay.getDate()-1);
		showEvent();
	}

	@Override
	public void today() {
		currentDay = new Date();
		currentDay = new Date(currentDay.getYear(), currentDay.getMonth(), currentDay.getDate());
		showEvent();
	}

	@Override
	public void viewDate(Calendar date) {
		currentDay = date.getTime();
		showEvent();
	}

	@Override
	public void intervalAdded(ListDataEvent e) {
		// TODO Auto-generated method stub
		showEvent();
	}

	@Override
	public void intervalRemoved(ListDataEvent e) {
		// TODO Auto-generated method stub
		showEvent();
	}

	@Override
	public void contentsChanged(ListDataEvent e) {
		// TODO Auto-generated method stub
		showEvent();
	}
}
