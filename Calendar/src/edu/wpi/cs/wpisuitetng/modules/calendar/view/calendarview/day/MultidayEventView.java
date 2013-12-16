package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day;

import java.awt.Color;
import java.awt.Dimension;
import java.text.DateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;
//import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.EventMouseListener;

/**
 * This class shows multi-day events in day view. It is a component of the DayCalendarLayerPane, along
 * with EventdayView.
 */
public class MultidayEventView extends JPanel{
	private List<Event> multidaye;
	private List<JPanel> displayEvents;
	private Date cdate;
	private static boolean isEventShowing = false;

	public MultidayEventView(List<Event> multiday, Dimension size, Date current){
		multidaye = multiday;
		displayEvents = new LinkedList<JPanel>();
		cdate = current;

		this.setSize(size);
		this.setPreferredSize(size);

		showEvents();

		if (isEventShowing){
			DisplayEventDropDown();
		}

		this.setOpaque(false);
		this.setVisible(true);
	}

	/**
	 * Sorts the List of Events by StartDate
	 */
	public void sortEvents(){
		Collections.sort(multidaye, new Comparator<Event>(){
			public int compare(Event o1, Event o2) {
				return o1.getStartDate().compareTo(o2.getStartDate());
			}

		});
	}

	public void showEvents(){
		if (multidaye.size() == 0){
			return;
		}

		sortEvents();

		this.setLayout(new MigLayout("fill", 
				"[20%][80%]", 
				"[4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%]"));

		JPanel eventinfo = new JPanel();
		eventinfo.add(new JLabel("Multiday Event"), "wmin 0, aligny top, alignx center");
		StringBuilder evebuilder = new StringBuilder();
		evebuilder.append("cell ");
		evebuilder.append("0");
		evebuilder.append(" ");
		evebuilder.append("0");
		evebuilder.append(" ");
		evebuilder.append("2");
		evebuilder.append(" ");
		evebuilder.append("0");
		evebuilder.append(",grow, push");


		eventinfo.setBackground(Color.PINK);
		this.add(eventinfo, evebuilder.toString());
		eventinfo.addMouseListener(new MultiDayListener(this));

		StringBuilder bob = new StringBuilder();
		bob.append("<html>");
		for (Event eve: multidaye){
			bob.append("<p>");
			bob.append("<b>Name:</b> ");
			bob.append(eve.getName());
			bob.append("<br><b>Description:</b> ");
			bob.append(eve.getDescription());
			bob.append("</p>");
		}
		bob.append("</html>");
		eventinfo.setToolTipText(bob.toString());

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

		int x = 1;
		for (Event eve: multidaye){
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
			if (new Date(evestart.getYear(), evestart.getMonth(), evestart.getDate()).compareTo(cdate) != 0){
				System.err.println("Previous!");
				StringBuilder previousbuilder = new StringBuilder();
				previousbuilder.append("<html><p><b><font size=\"6\"> &lt;- </font></b></p></html>");
				JLabel previous = new JLabel(previousbuilder.toString());
				multipane.add(previous, "cell 0 0, grow, push, wmin 0");
			}

			multipane.add(eventinfo, "cell 1 0, grow, push, wmin 0");

			Date eveend = eve.getEndDate();
			if (new Date(eveend.getYear(), eveend.getMonth(), eveend.getDate()).compareTo(cdate) != 0){
				System.err.println("Next!");
				StringBuilder nextbuilder = new StringBuilder();
				nextbuilder.append("<html><p><b><font size=\"6\"> -&gt; </font></b></p></html>");
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
			evebuilder.append("0");
			evebuilder.append(" ");
			evebuilder.append(new Integer(x).toString());
			evebuilder.append(" ");
			evebuilder.append("2");
			evebuilder.append(" ");
			evebuilder.append("0");
			evebuilder.append(",grow, push, wmin 0");

			this.add(multipane, evebuilder.toString());
			displayEvents.add(multipane);
			x++;
		}

		repaint();
		this.updateUI();
	}
}
