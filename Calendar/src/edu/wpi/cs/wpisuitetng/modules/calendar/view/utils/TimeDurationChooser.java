package edu.wpi.cs.wpisuitetng.modules.calendar.view.utils;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import net.miginfocom.swing.MigLayout;

/**
 * This Component allows a user to pick a start day and time and end day and time to form a duration of time
 * Uses two DateTimeChooser components
 * self-validating time duration
 * @author Dan
 */
public class TimeDurationChooser extends JPanel {
	List<DateTimeChangedEventListener> listeners = new ArrayList<DateTimeChangedEventListener>();	//list of custom listeners that get called whenever the duration is chaged
	
	private DateTimeChooser startDateChooser_;
	private DateTimeChooser endDateChooser_;
	private JLabel errorLabel_;
	
	//private internal representations of the Dates
	private Date startDate_;
	long prevStartDateTimeMs_;
	private Date endDate_;
	
	//end day/time will be this many minutes after the start day/time by default
	private int defaultDuration_=60;
	
	private Border errorBorder_;
	private Border normalBorder_;
	
	//validity tracking flags
	private boolean startDateValid_;
	private boolean endDateValid_;
	private boolean durationValid_;
	private boolean allValid_;
	
	//errors  to display
	private String startDateErrorText_ = "Invalid start time";
	private String endDateErrorText_="Invalid end time";
	private String durationErrorText_="Start time not before end time!";
	
	//for DateTimeChangedEvent listeners
	public void addDateTimeChangedEventListener(DateTimeChangedEventListener toAdd) {
		listeners.add(toAdd);
    }
	public void removeTimeChangedEventListener(DateTimeChangedEventListener toRemove) {
		listeners.remove(toRemove);
	}
	public void DateTimeChanged(){
//		System.out.println("\tTDC: Firing DurationChangedEvent!");
		// Notify everybody that may be interested.
		DateTimeChangedEvent evt = new DateTimeChangedEvent(startDate_.toString()+"-"+endDate_.toString());
        for (DateTimeChangedEventListener hl : listeners)
            hl.DateTimeChangedEventOccurred(evt);
	}
	
	/**
	 * Enable every sub-component of this duration chooser so the user can select a duration
	 */
	public void disable(){
		startDateChooser_.disable();
		endDateChooser_.disable();
		allValid_=true;
	}
	
	/**
	 * Disable every sub component of this duration chooser so athe user can't select a duration
	 * This component will anways report that it has a valid duration if it is disabled
	 */
	public void enable(){
		startDateChooser_.enable();
		endDateChooser_.enable();
		validateDuration();
	}
	
	/**
	 * Create a new TimeDurationChooser and use the next half-hour increment of today as the default start time
	 */
	public TimeDurationChooser(){
		buildLayout();
	}
	
	/**
	 * Create a TimeDurationChooser with the given start and end dates for days/times (to the minute)
	 * @param startDate the start day/time of the duration (year, month ignored)
	 * @param endDate the end day/time of the duration (year, month ignored)
	 */
	public TimeDurationChooser(Date startDate, Date endDate){
		buildLayout();
		
		startDateChooser_.setDate(startDate);
		endDateChooser_.setDate(endDate);
	}
	
	/**
	 * Builds the GUI for a TimeDuration Component, consisting of DateTimeChoosers for start and end times and an error label
	 * Uses the next half-hour increment of time in the future as start time, and an hour after that as end time
	 */
	private void buildLayout(){
		this.setLayout(new MigLayout("insets 1"));
		
		//Start Date Chooser
		startDateChooser_=new DateTimeChooser("Start Time:");
		startDateChooser_.addDateTimeChangedEventListener(new DateTimeChangedEventListener(){
			@Override
			public void DateTimeChangedEventOccurred(DateTimeChangedEvent evt) {
				startDateChanged();
			}
		});
		startDate_=startDateChooser_.getDate();
		prevStartDateTimeMs_=startDate_.getTime();
		this.add(startDateChooser_, "wrap");
		
		//End Date Chooser
		long endTime=startDate_.getTime()+defaultDuration_*60000;
		endDate_=new Date(endTime);
//		System.out.println("\tTDC: setting end time one hour after start time ("+startDate_.toString()+") as "+ endDate_.toString());
		//endDate_.setMinutes(startDate_.getMinutes()+defaultDuration_);
		endDateChooser_=new DateTimeChooser("End Time:", endDate_);
		endDateChooser_.addDateTimeChangedEventListener(new DateTimeChangedEventListener(){
			@Override
			public void DateTimeChangedEventOccurred(DateTimeChangedEvent evt) {
				endDateChanged();
			}
		});
		this.add(endDateChooser_, "wrap");
		
		//error label
		errorLabel_=new JLabel();
		errorLabel_.setVisible(false);
		this.add(errorLabel_);
		
		//validation
		normalBorder_=this.getBorder();
		errorBorder_=BorderFactory.createLineBorder(new Color(255, 51, 51));
		validateDuration();
	}
	
	/**
	 * Called whenever the date of the start date DateTimeChooser is changed, either by a user or elsewhere programatically with the setDate() method of the DateTimeChooser
	 */
	private void startDateChanged(){
//		System.out.println("\n\nTDC: Start Date Changed...");
		boolean durationWasValidBefore = durationValid_;
		long originalDurationMillis = endDate_.getTime()-prevStartDateTimeMs_;
		
//		//printing
//		if (durationWasValidBefore){
//			System.out.println("\tTDC: STARTDATECHANGED current start time is "+startDate_.toString()+" and end time is  "+endDate_.toString());
//			System.out.println("\tTDC: STARTDATECHANGED will be adding "+Long.toString(originalDurationMillis)+ "ms duration to new start time to get new end time...");
//		}else{
//			System.out.println("\t\tTDC: STARTDATECHANGED bad previous duration - not incrementing end date");
//		}
//		
		fillStartDate();
		if (startDateValid_ && durationWasValidBefore){//if the new start time is valid, and there was a valid duration between the old start time and end time, then maintain it by adjusting the end time by the same amount
			originalDurationMillis+=startDate_.getTime();//add the original duration (difference in times) to the new start time to get the new end time
//			System.out.println("\tDTC: STARTDATECHANGED new end time from start time " +Long.toString(startDate_.getTime())+" is " +Long.toString(originalDurationMillis));
			endDate_.setTime(originalDurationMillis);	//keep the same duration when the start time changes
			endDateChooser_.setDate(endDate_);
//			DateTimeChanged();//should be called automatically when the end date is set above
		}else{
			DateTimeChanged();
		}
//		System.out.println("TDC: ... Done Start Date Changed");
	}
	
	/**
	 * Called any time the date in the end date DateTimeChooser is changed, either by the user or in program by calling the setDate() method of the DateTimeChooser
	 */
	private void endDateChanged(){
//		System.out.println("TDC: End Date Changed");
		fillEndDate();
		DateTimeChanged();
	}
	
	/**
	 * Reads the start date from the start-date DateTimeCHooser.
	 * If the date and time are both valid, the internal end-date is set to this new date
	 * Errors are raised and the duration is invalidated if the start time is after the end time 
	 */
	private void fillStartDate(){
//		System.out.println("\tTDC: Filling Start Date...");
		if (startDateChooser_.hasValidDateTime()){
			startDate_=startDateChooser_.getDate();
			prevStartDateTimeMs_=startDate_.getTime();
//			System.out.println("\t\tDTC: setting start date to "+startDate_.toString());
			startDateValid_=true;
			checkDuration();
			updateError();
		}else{
			startDateValid_=false;
			setStartDateError();
		}
//		System.out.println("\tTDC:  ...Done filling Start Date");
	}
	
	/**
	 * Reads the end date from the end-date DateTimeCHooser.
	 * If the date and time are both valid, the internal end-date is set to this new date
	 * Errors are raised and the duration is invalidated if the start time is after the end time 
	 */
	private void fillEndDate(){
//		System.out.println("\tTDC: Filling End Date");
		if (endDateChooser_.hasValidDateTime()){
			endDate_=endDateChooser_.getDate();
//			System.out.println("\t\tDDC: setting end date to "+endDate_.toString());
			endDateValid_=true;
			checkDuration();
			updateError();
		}else{
			endDateValid_=false;
			setEndDateError();
		}
	}
	
	/**
	 * Validates the duration so that future calls to @hasValidDuration() are accurate
	 */
	private void checkDuration(){
		if (!endDate_.after(startDate_)){//if the end date is not after the start date
			setDurationError();
		}else{
			durationValid_=true;
		}
	}
	
	/**
	 * Set the start date and time of the duration
	 * The duration will be invalidated if this date is in the past or is not before the end date
	 * @param date the new start date to set
	 */
	public void setStartDate(Date date){
		startDateChooser_.setDate(date);
		fillStartDate();
	}
	
	/**
	 * Set the end date and time of the duration
	 * The duration will be invalidated if this date is in the past or is not after the start date
	 * @param date the new start date to set
	 */
	public void setEndDate(Date date){
		endDateChooser_.setDate(date);
		fillEndDate();
	}
	
	/**
	 * Get the start date. Note that if the start date of this chooser is changed, even if it is invalid, any other Dates set to that returned by this will also change
	 * @return the start date/tie of the duration
	 */
	public Date getStartDate(){
		return startDate_;
	}
	
	/**
	 * Get the end date. Note that if the end date of this chooser is changed, even if it is invalid, any other Dates set to that returned by this will also change
	 * @return the end date/time of the duration
	 */
	public Date getEndDate(){
		return endDate_;
	}
	
	/**
	 * Update the error label to set errors appropriately for invalid fields, or clear an error if it should not be shown
	 */
	private void updateError(){
		if (!startDateValid_){
			setStartDateError();
		}else if (!endDateValid_){
			setEndDateError();
		}else if (!durationValid_){
			setDurationError();
		}else if (!allValid_){
			clearErrors();
		}
	}
	
	/**
	 * stops showing any errors
	 */
	private void clearErrors(){
//		System.out.println("\t\tTDC: Clearing Errors...");
		allValid_=true;
		errorLabel_.setVisible(false);
		this.setBorder(normalBorder_);
//		System.out.println("\t\tTDC:...done clearing Errors");
	}
	
	/**
	 * Shows an error with the given description and invalidates the duration
	 * @param errorText the description of the error
	 */
	private void setError(String errorText){
		allValid_=false;
		errorLabel_.setText(errorText);
		errorLabel_.setVisible(true);
		this.setBorder(errorBorder_);
	}
	
	/**
	 * Sets an error for the start date and invalidates the start date and the duration
	 */
	private void setStartDateError(){
		startDateValid_=false;
		setError(startDateErrorText_);
	}	
	
	/**
	 * Sets an error for the end date and invalidates the end date and the duration
	 */
	private void setEndDateError(){
		endDateValid_=false;
		setError(endDateErrorText_);
	}
	
	/**
	 * Sets an error for the duration and invalidates the duration
	 */
	private void setDurationError(){
		durationValid_=false;
		setError(durationErrorText_);
	}
	
	/**
	 * Validates the duration represented by this component 
	 */
	private void validateDuration(){
		fillStartDate();
		fillEndDate();
	}
	
	/**
	 * The validity of the time duration stored and represented by this component
	 * @return true of a valid duration of time has been set, false otherwise
	 * Valid durations have valid start and end times and the start time is before the end time
	 * Valid times are properly formed in the future
	 */
	public boolean hasValidDuration(){
		return allValid_;
	}
	
}
