package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.week;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.DatePanel;

public class EventWeekView extends JPanel{

	private ArrayList<Event> events;
	private Date start;
	
	public EventWeekView(ArrayList<Event> e, Dimension size, Date current)
	{
		this.setSize(size);
		this.setPreferredSize(size);
		
		this.events = e;
		this.start = current;
		
		showEvent();
		this.setOpaque(false);
		this.setVisible(true);
	}
	
	public ArrayList<ArrayList<Event>> fixDays(){
		Date current;
		ArrayList<ArrayList<Event>> weekevents = new ArrayList<ArrayList<Event>>();
		
		for (int y = 0; y < 7; y++){
			current = new Date(start.getYear(), start.getMonth(), start.getDate()+y);
			weekevents.add(new ArrayList<Event>());
			
			for (int x = 0; x < events.size(); x++){
				Date evedate = events.get(x).getStartDate();
				
				System.out.println("event: " + evedate.toString() + " start: " + start.toString());
				if (evedate.getMonth() == current.getMonth() && evedate.getYear() == current.getYear() && evedate.getDate() == current.getDate()){
					weekevents.get(y).add(events.get(x));
				}
			}
		}
		
		return weekevents;
	}
	
	public int calculateLength(Event e){
		int height;
		int width;
		double length;
		int loc;
		
		length = (e.getEndDate().getHours() + ((double)e.getEndDate().getMinutes())/60.0) - (e.getStartDate().getHours() + ((double)e.getStartDate().getMinutes())/60.0);
		
		width = 79;
		height = (int)Math.round(length*4) - 1; //each hour is 4%, leave 1% for border
		loc = (int)Math.round((e.getStartDate().getHours() + e.getStartDate().getMinutes()/60.0)*4);
		
		return (int) length;
	}
	
	/**
	 * Creates a Mig-Layout panel to be displayed on the day view
	 * @param e Event to be displayed
	 * @return JPanel generated
	 */
	public void showEvent()
	{
		this.setLayout(new MigLayout("fill", 
				"[8%][13%][13%][13%][13%][13%][13%][13%]", 
				"[4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%]"));
		
		int height;
		int width;
		double length;
		int loc;
		
		
		ArrayList<ArrayList<Event>> weekevents = fixDays();
		
		for (int currentday = 0; currentday < 7; currentday++){
			ArrayList<Event> currentlist = weekevents.get(currentday);
			
			ArrayList<Integer> occupied = new ArrayList<Integer>();
			
			for (Event e: currentlist){
				System.out.println("Event:" + e.toString());
				
				length = (e.getEndDate().getHours() + ((double)e.getEndDate().getMinutes())/60.0) - (e.getStartDate().getHours() + ((double)e.getStartDate().getMinutes())/60.0);
				
				width = 79;
				height = (int)Math.round(length*4) - 1; //each hour is 4%, leave 1% for border
				loc = (int)Math.round((e.getStartDate().getHours() + e.getStartDate().getMinutes()/60.0)*4);
				
				JPanel event = new JPanel(new MigLayout());
				StringBuilder evebuilder = new StringBuilder();
				evebuilder.append("cell ");
				evebuilder.append(new Integer(currentday+1).toString());
				evebuilder.append(" ");
				evebuilder.append((new Integer((loc/4)+1)).toString());
				evebuilder.append(" ");
				evebuilder.append("0");
				evebuilder.append(" ");
				evebuilder.append((new Integer((int)length)).toString());
				evebuilder.append(",grow, push");

				event.add(new JLabel(e.getName()), "wmin 0, aligny center, alignx center");
				
				if (e.getCategory() != null){
					event.setBackground(e.getCategory().getColor());
				}
				else{
					event.setBackground(Color.CYAN);
				}
				event.setFocusable(true);
				this.add(event, evebuilder.toString());
				occupied.add(new Integer(loc/4));
			}
			
			
			for (int currenthour=0; currenthour < 25; currenthour++){
				if (occupied.contains(new Integer(currenthour-1))){
				}
				else{
					DatePanel thesecond = new DatePanel();

					StringBuilder datebuilder = new StringBuilder();
					datebuilder.append("cell ");
					datebuilder.append(new Integer(currentday+1).toString());
					datebuilder.append(" ");
					datebuilder.append((new Integer(currenthour)).toString());
					datebuilder.append(",grow, push");

					thesecond.setOpaque(false);
					this.add(thesecond, datebuilder.toString());
				}
			}
		}
	}
}