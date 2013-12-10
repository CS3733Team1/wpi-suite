package edu.wpi.cs.wpisuitetng.modules.calendar.view.utils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import net.miginfocom.swing.MigLayout;

public class TimeDurationChooser extends JPanel {
	List<DateTimeChangedEventListener> listeners = new ArrayList<DateTimeChangedEventListener>();
	
	private DateTimeChooser startDateChooser_;
	private DateTimeChooser endDateChooser_;
	private JLabel errorLabel_;
	
	private Date startDate_;
	private Date endDate_;
	
	private int defaultDuration_=60;
	
	private Border errorBorder_;
	private Border normalBorder_;
	
	private boolean startDateValid_;
	private boolean endDateValid_;
	private boolean durationValid_;
	private boolean allValid_;
	
	private String startDateErrorText_ = "Invalid start time";
	private String endDateErrorText_="Invalid end time";
	private String durationErrorText_="Start time not before time!";
	
	public void addDateTimeChangedEventListener(DateTimeChangedEventListener toAdd) {
		listeners.add(toAdd);
    }
	public void removeTimeChangedEventListener(DateTimeChangedEventListener toRemove) {
		listeners.remove(toRemove);
	}
	public void DateTimeChanged(){
		System.out.println("\tTDC: Firing DurationChangedEvent!");
		// Notify everybody that may be interested.
		DateTimeChangedEvent evt = new DateTimeChangedEvent(startDate_.toString()+"-"+endDate_.toString());
        for (DateTimeChangedEventListener hl : listeners)
            hl.DateTimeChangedEventOccurred(evt);

	}
	
	public void disable(){
		startDateChooser_.disable();
		endDateChooser_.disable();
		allValid_=true;
	}
	public void enable(){
		startDateChooser_.enable();
		endDateChooser_.enable();
		validateDuration();
	}
	
	public TimeDurationChooser(){
		buildLayout();
	}
	public TimeDurationChooser(Date startDate, Date endDate){
		buildLayout();
		
		startDateChooser_.setDate(startDate);
		endDateChooser_.setDate(endDate);
	}
	private void buildLayout(){
		this.setLayout(new MigLayout("", "[]", "[][][]"));
		
		//Start Date Chooser
		startDateChooser_=new DateTimeChooser("Start Time:");
		startDateChooser_.addDateTimeChangedEventListener(new DateTimeChangedEventListener(){
			@Override
			public void DateTimeChangedEventOccurred(DateTimeChangedEvent evt) {
				startDateChanged();
			}
		});
		startDate_=startDateChooser_.getDate();
		this.add(startDateChooser_,"cell 0 0");
		
		//End Date Chooser
		endDate_=new Date();
		endDate_.setMinutes(startDate_.getMinutes()+defaultDuration_);
		endDateChooser_=new DateTimeChooser("End Time:", endDate_);
		endDateChooser_.addDateTimeChangedEventListener(new DateTimeChangedEventListener(){
			@Override
			public void DateTimeChangedEventOccurred(DateTimeChangedEvent evt) {
				endDateChanged();
			}
		});
		this.add(endDateChooser_,"cell 0 1");
		
		//error label
		errorLabel_=new JLabel();
		errorLabel_.setVisible(false);
		this.add(errorLabel_,"cell 0 2");
		
		//validation
		normalBorder_=this.getBorder();
		errorBorder_=BorderFactory.createLineBorder(new Color(255, 51, 51));
		validateDuration();
	}
	
	private void startDateChanged(){
		System.out.println("TDC: Start Date Changed");
		boolean durationWasValidBefore = durationValid_;
		long originalDurationMillis = endDate_.getTime()-startDate_.getTime();
		if (durationWasValidBefore){
			System.out.println("\t\tTDC: adding "+Long.toString(originalDurationMillis)+ "ms to end time...");
		}
		
		fillStartDate();
		if (startDateValid_ && durationWasValidBefore){//if the new start time is valid, and there was a valid duration between the old start time and end time, then maintain it by adjusting the end time by the same amount
			originalDurationMillis+=startDate_.getTime();//add the original duration (difference in times) to the new start time to get the new end time
			System.out.println("\t\tDTC: new end time from start time " +Long.toString(startDate_.getTime())+" is " +Long.toString(originalDurationMillis));
			endDate_.setTime(originalDurationMillis);	//keep the same duration when the start time changes
			endDateChooser_.setDate(endDate_);
//			DateTimeChanged();//should be called automatically when the end date is set above
		}else{
			DateTimeChanged();
		}
	}
	private void endDateChanged(){
		System.out.println("TDC: End Date Changed");
		fillEndDate();
		DateTimeChanged();
	}
	
	private void fillStartDate(){
		System.out.println("\tTDC: Filling Start Date");
		if (startDateChooser_.hasValidDateTime()){
			startDate_=startDateChooser_.getDate();
			System.out.println("\t\tDTC: setting start date to "+startDate_.toString());
			startDateValid_=true;
			checkDuration();
			updateError();
		}else{
			startDateValid_=false;
			setStartDateError();
		}
	}
	private void fillEndDate(){
		System.out.println("\tTDC: Filling End Date");
		if (endDateChooser_.hasValidDateTime()){
			endDate_=endDateChooser_.getDate();
			System.out.println("\t\tDDC: setting end date to "+endDate_.toString());
			endDateValid_=true;
			checkDuration();
			updateError();
		}else{
			endDateValid_=false;
			setEndDateError();
		}
	}
	private void checkDuration(){
		if (!endDate_.after(startDate_)){//if the end date is not after the start date
			setDurationError();
		}else{
			durationValid_=true;
		}
	}
	
	public void setStartDate(Date date){
		startDateChooser_.setDate(date);
		fillStartDate();
	}
	public void setEndDate(Date date){
		endDateChooser_.setDate(date);
		fillEndDate();
	}
	
	public Date getStartDate(){
		return startDate_;
	}
	public Date getEndDate(){
		return endDate_;
	}
	
	private void updateError(){
		if (!startDateValid_){
			setStartDateError();
		}else if (!endDateValid_){
			setEndDateError();
		}else if (!durationValid_){
			setDurationError();
		}else{
			clearErrors();
		}
	}
	private void clearErrors(){
		System.out.println("\tTDC: Clearing Errors");
		allValid_=true;
		errorLabel_.setVisible(false);
		this.setBorder(normalBorder_);
	}
	private void setError(String errorText){
		allValid_=false;
		errorLabel_.setText(errorText);
		errorLabel_.setVisible(true);
		this.setBorder(errorBorder_);
	}
	private void setStartDateError(){
		startDateValid_=false;
		setError(startDateErrorText_);
	}	
	private void setEndDateError(){
		endDateValid_=false;
		setError(endDateErrorText_);
	}
	private void setDurationError(){
		durationValid_=false;
		setError(durationErrorText_);
	}
	
	private void validateDuration(){
		fillStartDate();
		fillEndDate();
	}
	public boolean hasValidDuration(){
		return allValid_;
	}
	
}
