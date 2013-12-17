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
	private static boolean isEventShowing = true;

	/**
	 * Creates a new multiday panel
	 * @param multiday new list of multiday events
	 * @param current the starting date the panel is supposed to show
	 */
	public MultidayEventView(List<Event> multiday, Date current){
		multidaye = multiday;
		displayEvents = new LinkedList<JPanel>();
		cdate = current;

		showEvents();
		DisplayEventDropDown();

		this.setOpaque(false);
		this.setVisible(true);
	}

	/**
	 * Updates the contents of the multiday panel
	 * @param multiday new list of multiday events
	 * @param current the starting date the panel is supposed to show
	 */
	public void updateMultiDay(List<Event> multiday, Date current){
		multidaye = multiday;
		displayEvents = new LinkedList<JPanel>();
		cdate = current;

		this.removeAll();

		showEvents();
		DisplayEventDropDown();

		System.err.println(this.getPreferredSize());
	}

	/**
	 * Resizing method for adapting to changed with
	 * @param width the new width to adapt panel to
	 */
	public void reSize(int width){
		this.setSize(width, this.getPreferredSize().height);
		this.setPreferredSize(new Dimension(width, this.getPreferredSize().height));

		this.repaint();
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

	/**
	 * Sorts the events and creates a new layout
	 */
	public void showEvents(){
		if (multidaye.size() == 0){
			return;
		}

		sortEvents();

		this.setLayout(new MigLayout("fill", 
				"[10%][90%]", 
				"[4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%][4%]"));


	}

	/**
	 * Retrieves number of multiday events
	 * @return number of events
	 */
	public int getNumberofEvents(){
		return multidaye.size();
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
		int x = 0;
		for (Event eve: multidaye){
			JPanel multipane = new JPanel(new MigLayout("fill", "[][][]", "[]"));

			//Bob The Builder builds the string, which contains the event info, for the tooltip
			StringBuilder bob = new StringBuilder();
			bob.append("<html>");
			bob.append("<html><p style='width:175px'><b>Event Name: </b>");
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

			StringBuilder eventNameBob = new StringBuilder();
			eventNameBob.append("<html>");
			eventNameBob.append("<html><p style='width:175px'><b>Event Name: </b>");
			eventNameBob.append(eve.getName());
			eventNameBob.append("</p></html>");


			//Adds a mouselistener to the event
			//multipane.addMouseListener(new EventMouseListener(eve, multipane));

			JLabel eventInfo = new JLabel(eventNameBob.toString());

			Date evestart = eve.getStartDate();
			if (new Date(evestart.getYear(), evestart.getMonth(), evestart.getDate()).compareTo(cdate) != 0){
				StringBuilder previousbuilder = new StringBuilder();
				previousbuilder.append("<html><p><b><font size=\"4\"> &lt;- </font></b></p></html>");
				JLabel previous = new JLabel(previousbuilder.toString());
				multipane.add(previous, "cell 0 0, grow, push, wmin 0");
			}

			multipane.add(eventInfo, "cell 1 0, grow, push, wmin 0");
			multipane.setToolTipText(bob.toString());

			Date eveend = eve.getEndDate();
			if (new Date(eveend.getYear(), eveend.getMonth(), eveend.getDate()).compareTo(cdate) != 0){
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
					eventInfo.setForeground(Color.WHITE);
				}
				else{
					eventInfo.setForeground(Color.BLACK);
				}
			}
			else{
				multipane.setBackground(Color.CYAN);
			}
			multipane.setFocusable(true);

			this.add(multipane, "cell 1 " + x + " 0 0, grow, push, wmin 0");
			height += multipane.getPreferredSize().height;
			displayEvents.add(multipane);
			x++;
		}

		this.setPreferredSize(new Dimension(this.getWidth(), height+20));
		this.revalidate();
		repaint();
		this.updateUI();
	}

}
