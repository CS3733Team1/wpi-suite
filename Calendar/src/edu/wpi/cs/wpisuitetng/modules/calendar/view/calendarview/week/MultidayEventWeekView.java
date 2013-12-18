package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.week;

import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.event.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.SchedMouseListener;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.CalendarUtils;

/**
 * This class shows multi-day events in week view. It is a component of the WeekCalendarLayerPane, along
 * with EventWeekView.
 */

public class MultidayEventWeekView extends JPanel {
	private List<List<Event>> multidaye;
	private List<JPanel> displayEvents;
	private Date cdate;
	private static boolean isEventShowing = true;
	
	private ImageIcon leftIcon, rightIcon;
	
	/**
	 * Creates a panel that holds the multiday events
	 * @param multiday the list of multiday events contained in the week
	 * @param current the start of the week
	 */
	public MultidayEventWeekView(List<List<Event>> multiday, Date current){
		multidaye = multiday;
		displayEvents = new LinkedList<JPanel>();
		cdate = current;
		
		leftIcon = new ImageIcon();
		rightIcon = new ImageIcon();
		
		try {
			leftIcon = new ImageIcon(ImageIO.read(getClass().getResource("/images/left.png")));
			rightIcon = new ImageIcon(ImageIO.read(getClass().getResource("/images/right.png")));
		} catch(IOException e){}
		
		showEvents();
		DisplayEventDropDown();
		
		this.setOpaque(false);
		this.setVisible(true);
	}
	
	/**
	 * Updates the week's multiday events it is holding
	 * @param multiday new multiday events that the view contains
	 * @param current the start date of the week
	 */
	public void updateMultiDay(List<List<Event>> multiday, Date current){
		
		multidaye = multiday;
		displayEvents = new LinkedList<JPanel>();
		cdate = current;

		this.removeAll();

		showEvents();
		DisplayEventDropDown();
		
	}
	
	/**
	 * Used to resize the view, ScrollPanes don't auto resize their width
	 * @param width the width that the parent wants this view to assume
	 */
	public void reSize(int width){
		this.setSize(width, this.getPreferredSize().height);
		this.setPreferredSize(new Dimension(width, this.getPreferredSize().height));
		
		this.repaint();
	}
	
	/**
	 * Retrieves number of multiday events
	 * @return number of events
	 */
	public int getNumberofEvents(){
		return this.getComponentCount();
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
		
		this.setLayout(new MigLayout("fillx, insets 0", "[9%]3[13%]3[13%]3[13%]3[13%]3[13%]3[13%]3[13%]"));
		
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
				JPanel multipane = new JPanel(new MigLayout("fill, insets 0", "[align left][center][align right]"));

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

				JLabel eventinfo = new JLabel("Event Name: " + eve.getName(), JLabel.CENTER);

				Date evestart = eve.getStartDate();
				if (new Date(evestart.getYear(), evestart.getMonth(), evestart.getDate()).before(cdate)){
					JLabel previous = new JLabel(leftIcon);
					multipane.add(previous, "cell 0 0");
				}

				multipane.add(eventinfo, "cell 1 0, alignx center, wmin 0");
				multipane.setToolTipText(bob.toString());
				multipane.addMouseListener(new SchedMouseListener(eve,multipane));
				
				Date eveend = eve.getEndDate();
				if (new Date(eveend.getYear(), eveend.getMonth(), eveend.getDate()).after(new Date(cdate.getYear(), cdate.getMonth(), cdate.getDate()+6))){
					JLabel next = new JLabel(rightIcon);
					multipane.add(next, "cell 2 0");
				}


				//Responsible for Getting Category Color and Setting Background Color
				if (eve.getCategory() != null){
					multipane.setBackground(eve.getCategory().getColor());
					multipane.setBorder(BorderFactory.createLineBorder(CalendarUtils.darken(eve.getCategory().getColor()), 2, false));
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
				
				int a = Math.min(8-y, getDateDiff(current, new Date(eveend.getYear(), eveend.getMonth(), eveend.getDate()))) + 1;

				this.add(multipane, "cell " + y + " " + x + " " + a + " 0, growx, wmin 0");
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
	
	/**
	 * Gets the difference between two dates
	 * @param d1 Starting Date, which comes before d2
	 * @param d2 End Date, which comes after d1
	 * @return The Minimum between 7 and the difference between d1 and d2
	 */
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
