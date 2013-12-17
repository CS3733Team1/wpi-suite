/**
 * A combined General Date/Time Picker
 * self-validating
 */
package edu.wpi.cs.wpisuitetng.modules.calendar.view.utils;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import net.miginfocom.swing.MigLayout;

import com.toedter.calendar.JDateChooser;

//Possible glitch: when one component returns an internal Date directly and whatever uses that component sets its internal Date to that, both change simultaniously.


/**
 * Component that allows the user to pick a day and a time with a JDateChooser and editable JComboBox for times
 * self-validating
 * @author Dan
 */
public class DateTimeChooser extends JPanel implements ActionListener{
	List<DateTimeChangedEventListener> listeners = new ArrayList<DateTimeChangedEventListener>();
	
	//Internal date to store day and time
	private Date date_;
	
	//choosers
	private JDateChooser jDateChooser_;
	private JComboBox<String> timeCombo_;
	private JLabel errorLabel;
	
	//validity flags
	private boolean dayValid_;
	private boolean timeValid_;
	private boolean dtValid_;
	
	//errors
	private String invalidDayErrorText_="Invalid Day";
	private String invalidTimeErrorText_="Invalid Time";
	private Border errorBorder_;
	private Border normalBorder_;
	private Color normalForgroundColor_;
	private Color errorForgroundColor_;
	
	//Custom DateTimeChangedEventListeners
	public void addDateTimeChangedEventListener(DateTimeChangedEventListener toAdd) {
		listeners.add(toAdd);
    }
	public void removeTimeChangedEventListener(DateTimeChangedEventListener toRemove) {
		listeners.remove(toRemove);
	}
	public void DateTimeChanged(){
//		System.out.println("\tFiring DateTimeChangedEvent!");

		DateTimeChangedEvent evt = new DateTimeChangedEvent(date_.toString());
		
	    // Notify everybody that may be interested.
        for (DateTimeChangedEventListener hl : listeners)
            hl.DateTimeChangedEventOccurred(evt);
//		// Notify everybody that may be interested.
//        Object[] listeners = listenerList.getListenerList();
//		for (int i = 0; i < listeners.length; i = i + 2) {
//			if (listeners[i] == TimeChangedEventListener.class) {
//				((DateTimeChangedEventListener) listeners[i + 1]).DateTimeChangedEventOccurred(evt);
//			}
//		}
	}
	
	/**
	 * Disables this component and all sub components. Calls to isValid() will return true while it is disabled
	 */
	public void disable(){
		jDateChooser_.setEnabled(false);
		timeCombo_.setEnabled(false);
		dtValid_ = true;
	}
	
	/**
	 * Enabled this component and all sub components and re-validates the day and time
	 */
	public void enable(){
		jDateChooser_.setEnabled(false);
		timeCombo_.setEnabled(false);
		validateDateTime();
	}
	
	/**
	 * Create a DateTimeChooser with the given name and set it to the given date
	 * @param name the name to display in the name label
	 * @param date the date to set as the time chosen
	 */
	public DateTimeChooser(String name, Date date){
//		System.out.println("\n\n\nDTC: Making new DateTimeChooser with name "+name + " and date "+ date.toString());
		date_=date;
		buildLayout(name);
	}
	
	/**
	 * Create a new DateTimeChooser with the default time set at the next half hour time increment in the future
	 */
	public DateTimeChooser(){
		this("Date:");
//		System.out.println("DTC: Done making default DateTimeChooser");
	}
	
	/**
	 * Create a new DateTimeChooser with the default time set at the next half hour time increment in the future and the given name
	 * @param name the name to display in the name label
	 */
	public DateTimeChooser(String name){
//		System.out.println("DTC: Making new DateTimeChooser with name "+name);
		
		date_=new Date();
		
		//go to the nearest half hour time slot in the future
		int curMinutes=date_.getMinutes();
		int halfHours=curMinutes/30;	//get how many complete half hours we are into the current hour
		curMinutes=30*(halfHours+1);	//go that many half hours + 1
		date_.setMinutes(curMinutes);	//should automatically roll over on itself to still be valid...I hope
//		System.out.println("Setting default to nearest Half Hour increment: "+curMinutes);
		
		buildLayout(name);
	}
	
	/**
	 * Builds the GUI for this DateTimeChooser with the given name
	 * @param name the name to display for this time
	 */
	private void buildLayout(String name){
		setLayout(new MigLayout(""));
		
		//make it on the minute - kill all seconds and milliseconds that might screw up comparisons with dates we think are on the same minute
//		System.out.println("\tNormalizing time " + date_.toString());
		long millis = date_.getTime();
		millis=normalizeToTheMinute(millis);
		date_.setTime(millis);//don't care about seconds - put them as far in the future as we can (so current minute is considered the future)
//		System.out.println("\tDone Normalizing time " + date_.toString());
		
		//name label
		JLabel lblName = new JLabel(name);
		lblName.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblName);
				
		//DateChooser for choosing days
		jDateChooser_=new JDateChooser(date_);
		jDateChooser_.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				dayChanged();
			}
		});
		add(jDateChooser_, "wmin 100");
		
		//combo for choosing times
		String[] strTimes;
		strTimes = new String[48];
		int index=0;
		for(int hour = 0; hour < 24; hour++) {
			index=2*hour;
			strTimes[index] = DateUtils.timeToString(hour,0);
			strTimes[index+1] = DateUtils.timeToString(hour,30);
		}
		timeCombo_ = new JComboBox<String>(strTimes);//new DefaultComboBoxModel(strTimes)
		timeCombo_.setSelectedIndex(dateTohalfHourIndex(date_));
		timeCombo_.setEditable(true);
		timeCombo_.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
//				System.out.println("timeCombo action performed...");
				timeChanged();
			}
		});
		timeCombo_.getEditor().getEditorComponent().addKeyListener(new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e) {}
			
			@Override
			public void keyPressed(KeyEvent e) {}
			
			@Override
			public void keyReleased(KeyEvent e) {
//				System.out.println("timeCombo key relseased...");
				timeChanged();
			}
		});
		add(timeCombo_);
		
		//Error Label
		errorLabel=new JLabel("Invalid time!");
		errorLabel.setVisible(false);
		add(errorLabel);
		
		//validation
		normalBorder_=this.getBorder();
		errorBorder_=BorderFactory.createLineBorder(new Color(255, 51, 51));
		normalForgroundColor_=timeCombo_.getBackground();
		errorForgroundColor_=Color.RED;
		validateDateTime();
	}
	
	/**
<<<<<<< HEAD
	 * Helper method "cieling" the given duration of time in milliseconds as close as possible to the nearest larger minute interval
=======
	 * Helper method "ceiling" the given duration of time in milliseconds as close as possible to the nearest larger minute interval
>>>>>>> 032e703545c99d14c41b0b32eaa744c81ac2f63e
	 * @param timeMs the time, in Milliseconds to "normalize" to the minute
	 * @return the same time in milliseconds to the minute, one millisecond before the next minute
	 */
	private long normalizeToTheMinute(long timeMs){
		long minutes=timeMs/60000;//count the minutes (integer)
		return minutes*60000+59999;//make it right at the end of the millis
	}
	

	/**
	 * Converts the given hour and minute to an index corresponding to a half-hour increment in the day [0 47]
	 * @param hour		The hour of the time [0 12]
	 * @param minutes	The minute of the time [0 60]
	 * @return			The index (number) of the half hour increment of the time, starting at 0 for midnight, 1 for 12:30 AM, etc. to 47 for 11:30
	 */
	private int timeToHalfHourIndex(int hour, int minutes){
		return 2*hour+minutes/30;
	}
	
	/**
	 * Converts the time of the given date to an index corresponding to a half-hour increment in the day [0 47]
	 * @param date a Date containing the time to convert
	 * @return The index (number) of the half hour increment of the time, starting at 0 for midnight, 1 for 12:30 AM, etc. to 47 for 11:30
	 */
	private int dateTohalfHourIndex(Date date){
		return timeToHalfHourIndex(date.getHours(),date.getMinutes());
	}
	
	/**
	 * converts a half hour index to a time in the day
	 * @param index the half-hour index to convert starting at 0 for midnight, 1 for 12:30 AM, etc. to 47 for 11:30
	 * @return a Date containing the corresponding time
	 */
	private Date halfHourIndexToTime(int index){
		Date date = new Date();
		date.setHours(index/2);
		date.setMinutes(30*(index%30));
		return date;
	}
	
	/**
	 * Called any time the time is changed from the combo box or by program with the setTime() or setDate() methods
	 * validates the time currently in the Combo Box
	 */
	private void timeChanged(){
//		System.out.println("TIME Changed");
		fillTime();
		DateTimeChanged();
	}
	
	/**
	 * Called any time the day is changes from the JDatePicker or by the program with the setDay() or setDate() methods
	 * validates the day currently in the JDatePicker
	 */
	private void dayChanged(){
//		System.out.println("Day Changed");
		fillDay();
		DateTimeChanged();
	}
	
	/**
	 * Gets the time from the combo box, parses it, validates it, and sets this pickers time to it if it was valid.
	 * Sets errors and invalidates this chooser's time if the time in the ComboBox is invalid
	 * Invalid times are either unparsable or in the past
	 * Time can be entered in standard 12-hour format (7:30 PM) or 24-hour/military time (16:30) with a wide range of robustness
	 */
	private void fillTime(){
//		System.out.println("\tFilling Time...");
		//normally we'de use getSelectedItem() but this does not change as characters are changed in the ComboBox's internal editor (text box), so we get it directly
		String strTime=(String)timeCombo_.getEditor().getItem();//(String)timeCombo_.getSelectedItem();
//		System.out.println("\t\tConverting "+strTime+" to time...");
		Date time = DateUtils.stringToDate(strTime);
		if (time!=null){
			date_.setHours(time.getHours());
			date_.setMinutes(time.getMinutes());
			if (date_.after(new Date())){
//				System.out.println("\t\tDate with new time is "+date_.toString());
				timeValid_=true;
//				dtValid_=dayValid_;
				updateError();
			}else{//time in the past
//				System.out.println("\tDTC: ERROR: time in the past!");
				setTimeError();
			}
		}else{
			setTimeError();
		}
//		System.out.println("\tDone filling time...");
	}
	
	/**
	 * Gets the day from the JDateCHooser, validates it, and sets this chooser's date to it if it is valid
	 * Sets an error and invalidates this chooser's DayTime if the day is invalid
	 * Valid days are today or in the future
	 */
	private void fillDay(){
//		System.out.println("\nFilling Day...");
		Date day=jDateChooser_.getDate();
//		System.out.println("\t day was changed to: "+day.toString());
		if (day!=null){
			//normalize the day to the very start of the day
			day.setHours(0);
			day.setMinutes(1);
			day.setTime(normalizeToTheMinute(day.getTime()));

			//normalize right now to the very start of today + 1 minute so it's in the future
			Date rightNow = new Date();
			rightNow.setHours(0);
			rightNow.setMinutes(0);
			rightNow.setTime(normalizeToTheMinute(rightNow.getTime()));
			
//			System.out.println("\tComparing new date ("+day.toString()+") to today ("+rightNow.toString()+")");
			
			//validate
			if (day.after(rightNow)){
				date_.setYear(day.getYear());
				date_.setMonth(day.getMonth());
				date_.setDate(day.getDate());
				dayValid_=true;
//				dtValid_=timeValid_;
				updateError();
			}else{
//				System.out.println("\tError: day was not after right now!");
				setDayError();
			}
		}else{
//			System.out.println("\tError: date was null");
			setDayError();
		}
//		System.out.println("\t...Done Filling day");
	}

	/**
	 * Set the date of this chooser and validate it
	 * @param date
	 */
	public void setDate(Date date){
//		System.out.println("\t\tDTC: Setting date to " +date.toString());
		date.setSeconds(59);
		setDay(date);
		setTime(date);
//		System.out.println("\t\tDTC: DONE setting date");
	}
	
	/**
	 * Set the day of this chooser and validate it
	 * @param day
	 */
	public void setDay(Date day){
//		System.out.println("\t\t\tDTC: Setting day to " +day.toString());
		jDateChooser_.setDate(day);
//		fillDay();//the setDay() above should automatically fire a property changed event which calls fillTime()
	}
	
	/**
	 * Set the time of this chooser and validate it
	 * @param date
	 */
	public void setTime(Date date){
		String strTime=DateUtils.timeToString(date);
//		System.out.println("\t\t\tDTC: Setting time to " +DateUtils.timeToString(date));
		timeCombo_.setSelectedItem(strTime);
//		fillTime(); //the setSelecteditem() above should automatically fire a propertychanged event which calls fillTime()
	}
	
	/**
	 * Get a date set to the day and time represented by this chooser
	 * @return
	 */
	public Date getDate(){
//		System.out.println("\t\tDTC: returning date " + date_.toString());
		return date_;
	}

	/**
	 * Sets an error with the given description and invalidates the DayTime
	 * @param errorText description of the error to set
	 */
	private void setError(String errorText){
		dtValid_=false;
		errorLabel.setText(errorText);
		errorLabel.setVisible(true);
		this.setBorder(errorBorder_);
	}
	
	/**
	 * Clears all errors and sets the validity to true
	 */
	private void clearErrors(){
//		System.out.println("\tDTC: Clearing Error");
		dtValid_=true;
		errorLabel.setVisible(false);
//		timeCombo_.setBorder(normalBorder_);
//		jDateChooser_.setBorder(normalBorder_);
		this.setBorder(normalBorder_);
//		System.out.println("\tDTC: ... done clearing Error");
	}
	
	/**
	 * Sets an error with the day and invalidates the day and the DateTime
	 */
	private void setDayError(){
//		System.out.println("DTC: Setting Day Error");
		dayValid_=false;
//		jDateChooser_.setBorder(errorBorder_);
		setError(invalidDayErrorText_);
	}
	
	/**
	 * Sets an error with the time and invalidates the time and the dateTime
	 */
	private void setTimeError(){
//		System.out.println("\tDTC: Setting Time Error");
		timeValid_=false;
//		timeCombo_.setForeground(errorForgroundColor_);
		setError(invalidTimeErrorText_);
	}
	
	/**
	 * Sets an appropriate error if anything is invalid or clears all errors in everything is valid
	 */
	private void updateError(){
		if (!dayValid_){
			setDayError();
		}else if (!timeValid_){
			setTimeError();
		}else if (!dtValid_){	//the day and time are both valid but dtValis is still false
			clearErrors();		//clear all errors and set dt to True
		}
	}
	
	/**
	 * Gets and validates the day and the time for this component
	 */
	private void validateDateTime(){
//		System.out.println("Validating DateTime");
		fillDay();
		fillTime();
	}
	
	/**
	 * The validity of the Day and Time represented in this component
	 * @return true if the day and time are valid, false otherwise
	 * Valid Days/Times are properly formatted and in the future
	 */
	public boolean hasValidDateTime(){
//		fillTime();
//		fillDay();
//		verifyDateTime();
		return dtValid_;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
