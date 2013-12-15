package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.week2;

import java.awt.Color;
import java.awt.Dimension;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;

/**
 * This class shows multi-day events in week view. It is a component of the WeekCalendarLayerPane, along
 * with EventWeekView.
 */

public class MultidayEventWeekView extends JPanel {
	private List<List<Event>> multidaye;
	private List<JPanel> displayEvents;
	private Date cdate;
	private static boolean isEventShowing = true;
	
	public MultidayEventWeekView(List<List<Event>> multiday, Date current){
		multidaye = multiday;
		displayEvents = new LinkedList<JPanel>();
		cdate = current;
		
		showEvents();
		DisplayEventDropDown();
		
		this.setOpaque(false);
		this.setVisible(true);
	}
	
	
	public void updateMultiDay(List<List<Event>> multiday, Date current){
		System.err.println(multiday);
		System.err.println(current);
		
		multidaye = multiday;
		displayEvents = new LinkedList<JPanel>();
		cdate = current;

		this.removeAll();

		showEvents();
		DisplayEventDropDown();
		
	}
	
	public void reSize(int width){
		this.setSize(width, this.getPreferredSize().height);
		this.setPreferredSize(new Dimension(width, this.getPreferredSize().height));
		
		this.repaint();
	}
	
	/**
	 * Sorts the List of Events by StartDate
	 */
	public void showEvents(){
		ArrayList<String> names = new ArrayList<String>();
		
		boolean shouldret = true;
		for (List<Event> elist: multidaye){
			if (elist.size() > 0){
				shouldret = false;
			}
		}
		if (shouldret){
			return;
		}
		
		this.setLayout(new MigLayout("fill", 
				"0[9%]3[13%]3[13%]3[13%]3[13%]3[13%]3[13%]3[13%]0", 
				"0[4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%]"));
		
	}
	
	/**
	 * Getter Method for isEventShowing
	 * @return Returns boolean value whether DisplayEventDropDown is active
	 */
	public static boolean areEventsDisplaying(){
		return isEventShowing;
	}
	
	/**
	 * Setter Method For Events Displaying
	 */
	public static void reverseEventsDisplaying(){
		isEventShowing = !isEventShowing;
	}
	
	/**
	 * Clears The JPanel Drop Down of Multiday Events
	 */
	public void ClearEventDropDown(){
		
		for (int x = 0;x < displayEvents.size();x++){
			this.remove(displayEvents.get(x));
		}
		repaint();
	}
	
	/**
	 * Displays Panels Of MultiDay Events
	 */
	public void DisplayEventDropDown(){
		displayEvents.clear();
		int height = 0;
		int y = 1;
		int x = 1;
		Date current = cdate;
		for (List<Event> elist: multidaye){
			for (Event eve: elist){
				JPanel multipane = new JPanel(new MigLayout("fill", "[][][]", "[]"));

				//Bob The Builder builds the string, which contains the event info
				StringBuilder bob = new StringBuilder();
				bob.append("<html>");
				bob.append("<html><p style='width:175px'><b>Name: </b>");
				bob.append(eve.getName());
				bob.append("<br><b>Start: </b>");
				bob.append(DateFormat.getInstance().format(eve.getStartDate()));
				bob.append("<br><b>End: </b>");
				bob.append(DateFormat.getInstance().format(eve.getEndDate()));
				if(eve.getCategory()!=null){
					bob.append("<br><b>Category: </b>");
					bob.append(eve.getCategory().getName());
				}
				if(eve.getDescription() != null && eve.getDescription().length()>0){
					bob.append("<br><b>Description: </b>");
					bob.append(eve.getDescription());
				}
				bob.append("</p></html>");

				//Adds a mouselistener to the event
				//multipane.addMouseListener(new EventMouseListener(eve, multipane));

				JLabel eventinfo = new JLabel(bob.toString());

				Date evestart = eve.getStartDate();
				if (new Date(evestart.getYear(), evestart.getMonth(), evestart.getDate()).before(cdate)){
					StringBuilder previousbuilder = new StringBuilder();
					previousbuilder.append("<html><p><b><font size=\"4\"> &lt;- </font></b></p></html>");
					JLabel previous = new JLabel(previousbuilder.toString());
					multipane.add(previous, "cell 0 0, grow, push, wmin 0");
				}

				multipane.add(eventinfo, "cell 1 0, grow, push, wmin 0");

				Date eveend = eve.getEndDate();
				if (new Date(eveend.getYear(), eveend.getMonth(), eveend.getDate()).after(new Date(cdate.getYear(), cdate.getMonth(), cdate.getDate()+6))){
					StringBuilder nextbuilder = new StringBuilder();
					nextbuilder.append("<html><p><b><font size=\"4\"> -&gt; </font></b></p></html>");
					JLabel next = new JLabel(nextbuilder.toString());
					multipane.add(next, "cell 2 0, grow, push, wmin 0");
				}


				//Responsible for Getting Category Color and Setting Background Color
				if (eve.getCategory() != null){
					multipane.setBackground(eve.getCategory().getColor());
					Color catColor=eve.getCategory().getColor();
					float[] hsb=new float[3];
					//Determines whether text needs to be black or white
					hsb=Color.RGBtoHSB(catColor.getRed(), catColor.getGreen(), catColor.getBlue(), hsb);
					if(hsb[2]<0.5){
						eventinfo.setForeground(Color.WHITE);
					}
					else{
						eventinfo.setForeground(Color.BLACK);
					}
				}
				else{
					multipane.setBackground(Color.CYAN);
				}
				multipane.setFocusable(true);

				//Builds a string to place it in miglayout
				StringBuilder evebuilder = new StringBuilder();
				evebuilder.append("cell ");
				evebuilder.append(new Integer(y).toString());
				evebuilder.append(" ");
				evebuilder.append(new Integer(x).toString());
				evebuilder.append(" ");
				evebuilder.append(Math.min(8-y, getDateDiff(current, new Date(eveend.getYear(), eveend.getMonth(), eveend.getDate())))+ 1);
				evebuilder.append(" ");
				evebuilder.append("0");
				evebuilder.append(",grow, push, wmin 0");

				this.add(multipane, evebuilder.toString());
				height += multipane.getPreferredSize().height;
				displayEvents.add(multipane);
				x++;
			}
			current = new Date(current.getYear(), current.getMonth(), current.getDate()+1);
			y++;
		}
		
		this.setPreferredSize(new Dimension(this.getWidth(), height+20));
		this.revalidate();
		repaint();
		this.updateUI();
	}
	
	public int getDateDiff(Date d1, Date d2){
		Date current = d1;
		int diff = 0;
		while(diff<=7){
			//System.out.println(current + " " + d2);
			if (current.compareTo(d2) == 0){
				return diff;
			}
			current = new Date(current.getYear(), current.getMonth(), current.getDate()+1);
			diff++;
		}
		
		return diff;
	}
}
