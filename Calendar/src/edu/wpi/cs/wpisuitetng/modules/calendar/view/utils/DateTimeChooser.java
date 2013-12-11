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
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import net.miginfocom.swing.MigLayout;

import com.toedter.calendar.JDateChooser;

//TODO
/*DayTimeChangedEvent -- break into day changed and time changed events?
 * -increment end time appropriately when start time changes
 * TEST
 * add all day commitment checkbox
 * add all day event checkbox
 * 
 * Possible glitch: when one component returns an internal Date directly and whatever uses that component sets its internal Date to that, both change simultaniously.
 * 
 * */

/**
 * @author Dan
 *
 */
public class DateTimeChooser extends JPanel {
	List<DateTimeChangedEventListener> listeners = new ArrayList<DateTimeChangedEventListener>();
	
	private Date date_;
	
	private JDateChooser jDateChooser_;
	private JComboBox<String> timeCombo_;
	private JLabel errorLabel;
	
	private boolean dayValid_;
	private boolean timeValid_;
	private boolean dtValid_;
	
	private String invalidDayErrorText_="Invalid Day";
	private String invalidTimeErrorText_="Invalid Time";
	private Border errorBorder_;
	private Border normalBorder_;
	private Color normalForgroundColor_;
	private Color errorForgroundColor_;
	
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
	
	public void disable(){
		jDateChooser_.disable();
		timeCombo_.disable();
		dtValid_=true;
	}
	public void enable(){
		jDateChooser_.disable();
		timeCombo_.disable();
		validateDateTime();
	}
	
	public DateTimeChooser(String name, Date date){
//		System.out.println("\n\n\nDTC: Making new DateTimeChooser with name "+name + " and date "+ date.toString());
		date_=date;
		buildLayout(name);
	}
	public DateTimeChooser(){
		this("Date:");
//		System.out.println("DTC: Done making default DateTimeChooser");
	}
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
	private void buildLayout(String name){
		setLayout(new MigLayout("", "[55px,right][grow][grow][]", "[]"));
		
		//make it on the minute - kill all seconds and milliseconds that might screw up comparisons with dates we think are on the same minute
//		System.out.println("\tNormalizing time " + date_.toString());
		long millis = date_.getTime();
		millis=normalizeToTheMinute(millis);
		date_.setTime(millis);//don't care about seconds - put them as far in the future as we can (so current minute is considered the future)
//		System.out.println("\tDone Normalizing time " + date_.toString());
		
		//name label
		JLabel lblName = new JLabel(name);
		lblName.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblName, "cell 0 0, alignx right");
				
		//DateChooser for choosing days
		jDateChooser_=new JDateChooser(date_);
		jDateChooser_.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				dayChanged();
			}
		});
		add(jDateChooser_, "cell 1 0");
		
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
		add(timeCombo_, "cell 2 0,growx");
		
		//Error Label
		errorLabel=new JLabel("Invalid time!");
		errorLabel.setVisible(false);
		add(errorLabel, "cell 3 0");
		
		//validation
		normalBorder_=this.getBorder();
		errorBorder_=BorderFactory.createLineBorder(new Color(255, 51, 51));
		normalForgroundColor_=timeCombo_.getBackground();
		errorForgroundColor_=Color.RED;
		validateDateTime();
	}
	
	private long normalizeToTheMinute(long timeMs){
		long minutes=timeMs/60000;//count the minutes (integer)
		return minutes*60000+59999;//make it right at the end of the millis
	}
	
	private int timeToHalfHourIndex(int hour, int minutes){
		return 2*hour+minutes/30;
	}
	private int dateTohalfHourIndex(Date date){
		return timeToHalfHourIndex(date.getHours(),date.getMinutes());
	}
	private Date halfHourIndexToTime(int index){
		Date date = new Date();
		date.setHours(index/2);
		date.setMinutes(30*(index%30));
		return date;
	}
	
	private void timeChanged(){
//		System.out.println("TIME Changed");
		fillTime();
		DateTimeChanged();
	}
	private void dayChanged(){
//		System.out.println("Day Changed");
		fillDay();
		DateTimeChanged();
	}
	
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

	public void setDate(Date date){
//		System.out.println("\t\t\tDTC: Setting date to " +date.toString());
		date.setSeconds(59);
		setDay(date);
		setTime(date);
//		System.out.println("\t\tDTC: DONE setting date");
	}
	public void setDay(Date day){
//		System.out.println("\t\t\tDTC: Setting day to " +day.toString());
		jDateChooser_.setDate(day);
//		fillDay();//the setDay() above should automatically fire a property changed event which calls fillTime()
	}
	public void setTime(Date date){
		String strTime=DateUtils.timeToString(date_);
//		System.out.println("\t\t\tDTC: Setting time to " +DateUtils.timeToString(date_));
		timeCombo_.setSelectedItem(strTime);
//		fillTime(); //the setSelecteditem() above should automatically fire a propertychanged event which calls fillTime()
	}
	
	public Date getDate(){
//		System.out.println("\t\tDTC: returning date " + date_.toString());
		return date_;
	}

	private void setError(String errorText){
		dtValid_=false;
		errorLabel.setText(errorText);
		errorLabel.setVisible(true);
		this.setBorder(errorBorder_);
	}
	private void clearErrors(){
//		System.out.println("\tDTC: Clearing Error");
		dtValid_=true;
		errorLabel.setVisible(false);
//		timeCombo_.setBorder(normalBorder_);
//		jDateChooser_.setBorder(normalBorder_);
		this.setBorder(normalBorder_);
//		System.out.println("\tDTC: ... done clearing Error");
	}
	private void setDayError(){
//		System.out.println("DTC: Setting Day Error");
		dayValid_=false;
//		jDateChooser_.setBorder(errorBorder_);
		setError(invalidDayErrorText_);
	}
	private void setTimeError(){
//		System.out.println("\tDTC: Setting Time Error");
		timeValid_=false;
//		timeCombo_.setForeground(errorForgroundColor_);
		setError(invalidTimeErrorText_);
	}
	private void updateError(){
		if (!dayValid_){
			setDayError();
		}else if (!timeValid_){
			setTimeError();
		}else if (!dtValid_){	//the day and time are both valid but dtValis is still false
			clearErrors();		//clear all errors and set dt to True
		}
	}
	private void validateDateTime(){
//		System.out.println("Validating DateTime");
		fillDay();
		fillTime();
	}
	public boolean hasValidDateTime(){
//		fillTime();
//		fillDay();
//		verifyDateTime();
		return dtValid_;
	}
}
