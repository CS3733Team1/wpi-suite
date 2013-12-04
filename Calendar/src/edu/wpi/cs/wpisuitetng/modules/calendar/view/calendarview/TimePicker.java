package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.event.EventListenerList;

import net.miginfocom.swing.MigLayout;

import com.toedter.calendar.JCalendar;

import java.util.EventListener;
import java.util.EventObject;


/**
 * @author DanF
 * 
 */
public class TimePicker extends JPanel{
	
	//time changed events
	protected EventListenerList listenerList = new EventListenerList();

	
	// components within this panel
	private JLabel nameLabel;
	private JTextField hoursTextField;
	private JTextField minutesTextField;
	private JComboBox<String> dayNightComboBox;
	private JLabel colonLabel;
	private JLabel errorLabel;
	private String timeErrorText_ = "Invalid Time!";
	private JLabel debugLabel;

	// time Data containing the time currently displayed in this picker
	private int displayHours_;
	private int displayMinutes_;

	// time constants
	private final static int maxMinutes_ = 59;
	private final static int maxHours_ = 23;
	private final static int noonHours_ = 12;

//	private boolean validHours_ = false;
//	private boolean validMinutes_ = false;
	private boolean validTimeFlag_ = false;
	private boolean inAM_=true;
	private boolean militaryTime_=false;
	
	public void addTimeChangedEventListener(TimeChangedEventListener timeChangedEventListener) {
		listenerList.add(TimeChangedEventListener.class, timeChangedEventListener);
	}

	public void removeTimeChangedEventListener(TimeChangedEventListener listener) {
		listenerList.remove(TimeChangedEventListener.class, listener);
	}

	private void fireTimeChangedEvent(TimeChangedEvent evt) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i = i + 2) {
			if (listeners[i] == TimeChangedEventListener.class) {
				((TimeChangedEventListener) listeners[i + 1]).TimeChangedEventOccurred(evt);
			}
		}
	}
	
	private void TimeChanged(){
		fireTimeChangedEvent(new TimeChangedEvent(getTimeAsSting()));
	}
	
	/**
	 * gives the internally represented time as a string
	 * @return the time in "HH:MM:AM" format
	 */
	public String getTimeAsSting(){
		int hours=displayHours_;
		String txtAmPm="AM";
		if (displayHours_>=12){
			hours-=12;
			txtAmPm="PM";
		}else if (displayHours_==0){
			hours=12;
		}
		return Integer.toString(hours)+ ":"+Integer.toString(displayMinutes_)+":"+txtAmPm;
	}
	
	// default - makes a new time
	public TimePicker() {
		buildLayout(-1, -1, "Time:");
	}

	// default - makes a new time
	public TimePicker(String name) {
		buildLayout(-1, -1, name);
	}
	
	// TimePicker from given hours and minutes
	public TimePicker(int hours, int minutes, String name) {
		buildLayout(hours, minutes,name);
	}

	// TimePicker from given date
	public TimePicker(Date date, String name) {
		buildLayout(date.getHours(), date.getMinutes(), name);
	}

	// Build the component with the given hours and minutes
	void buildLayout(int hours, int minutes, String name) {

		this.setLayout(new MigLayout("",
				//name label, hourstextfield, colonLabel, minutesTextfield,errorlabel,debugLabel
				"[25:60, left][25:30,grow,fill][][25:30,grow,fill][][][]", 	//column spec
				"[30:n,center]"));								//row spec
		this.setBorder(null); // no border

		nameLabel=new JLabel(name);
		nameLabel.setHorizontalTextPosition(SwingConstants.LEFT);
		nameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		this.add(nameLabel, "cell 0 0,growx, gp1, aligny center");
		
		// hours
		hoursTextField = new JTextField();
		// hoursTextField.setText((displayHours_==0)? "HH" :
		// Integer.toString(displayHours_));
		hoursTextField.setHorizontalAlignment(SwingConstants.CENTER);
		hoursTextField.setColumns(5);
		hoursTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				hoursTextField.selectAll();
				updateDebugLabel();
			}
		});
		hoursTextField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				hoursTextField.selectAll();
				updateDebugLabel();
			}
		});
		hoursTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				hoursChanged();
				updateDebugLabel();
			}
		});
		this.add(hoursTextField, "cell 1 0,growx, gp1, aligny center");//"cell 0 0,growx, gp1, aligny center"

		// colon between H:M
		colonLabel = new JLabel(":");
		this.add(colonLabel, "cell 2 0");

		// minutes
		minutesTextField = new JTextField();
		// minutesTextField.setText((displayMinutes_==0)? "MM" :
		// Integer.toString(displayMinutes_));
		minutesTextField.setHorizontalAlignment(SwingConstants.CENTER);
		minutesTextField.setColumns(5);
		minutesTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				minutesTextField.selectAll();
				updateDebugLabel();
			}
		});
		minutesTextField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				minutesTextField.selectAll();
				updateDebugLabel();
			}
		});
		minutesTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				minutesChanged();
				updateDebugLabel();
			}
		});
		this.add(minutesTextField, "cell 3 0,growx, gp1, aligny center");

		// AM/PM
		dayNightComboBox = new JComboBox<String>();
		dayNightComboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "AM", "PM" }));
		dayNightComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				int id=dayNightComboBox.getSelectedIndex();
//				System.out.println("Item Changed to "+Integer.toString(id));
				amPmChanged(id);
				updateDebugLabel();
			}
		});

		this.add(dayNightComboBox, "cell 4 0,alignx right,aligny center");
//		accountForMilitaryTime();

		errorLabel = new JLabel();
		errorLabelErrorMode();
		this.add(errorLabel, "cell 5 0,alignx right,aligny center");
		
		// DEBUG
//		debugLabel = new JLabel("DEBUG");
//		this.add(debugLabel, "cell 6 0");
		
		// set things based on given times
		setHours(hours);
		setMinutes(minutes);
		updateErrorLabel();
	}

	/**************************** VALIDATION ************************/

	/*
	 * calculated display hours (always in military time) based on the given am/pm picker index (0=am, =pm)
	 * assumed displayHours was valid (at-least in military time) before being called
	 * called whenever the dayNightPickerComboBox is changed by the user
	 * @param i the 0 or 1, corresponds to the index of the dayNightChooser combo box for AM/PM
	 */
	public void amPmChanged(int i) {
//		System.out.println("AM/PM changed to "+Integer.toString(i));
		inAM_=(i==0);
		int hours=getNumberFromTextField(hoursTextField);	//get the number that is actually in the text field
		if (isValidNumber(hours, maxHours_)){	//only change displayHours if it's already valid
			if (inAM_) {// just changed from PM to Am 
//				System.out.println("Changed from PM -> AM");
				if (hours >= noonHours_) {	//if hours is a valid pm military time [1pm-12pm] NOTE: this should never happen because if the hours is >12, then military time is detected, and the checkbox is disabled
//					System.out.println("ERROR: how did you change PM->Am when in military time?");
					displayHours_ = hours- noonHours_;
					hoursTextField.setText(Integer.toString(displayHours_));
				}else{
					displayHours_ = hours;
				}
			} else {// just changed from Am to PM
//				System.out.println("Changed from AM -> PM");
				if (hours < noonHours_) {	//if hours is a valid standard AM time [1-12], then add 12 to it. if it was 12 and we changed to pm, keep it as 12
//					System.out.println("\tAdding 12 to hours");
					displayHours_ = hours+noonHours_;
				}else{
					displayHours_=hours;
				}
				//if displayHours was 12, then keep it 12
				//it won't have been 0
			}
			validateDisplayTimes();
//			accountForMilitaryTime();
		}
		TimeChanged();
	}

//	/*
//	 * main input validation method
//	 * gets the current input from the hour and minute text fields and validates them
//	 */
//	private void validateInputTime() {
//		validateMinutes();
//		validateHours();
//		validateDisplayTimes();
//	}

	/*
	 * gets the minutes from the text field, validates, and sets as displayMinutes_ if it's valid
	 * called whenever a key is typed in the minutes text field
	 */
	private void minutesChanged() {
		int minutes = getNumberFromTextField(minutesTextField);
//		System.out.println("Minutes changed!" + Integer.toString(minutes));
		setDisplayMinutes(minutes);
//		validateDisplayTimes();
		TimeChanged();
	}

	/*
	 * gets the hours from the text field, validates, and sets as displayHours_ if it's valid 
	 * called whenver a key is typed in the hours text field
	 */
	private void hoursChanged() {
		int hours = getNumberFromTextField(hoursTextField);	//hour will be the hour if the text us a number>0, -1 otherwise
//		System.out.println("Hours changed! "+ Integer.toString(hours));
		setDisplayHours(hours);	//set display hours if it is a valid hour, or leave it alone if it's not
//		validateDisplayTimes(); //re-check to see if all times are now valid
		TimeChanged();
	}

	/**
	 * assumes displayHours has already been validated. 
	 * If displayHours is > 12 or 0 (military time), then sets AM/PM appropriately and disables that checkbox
	 */
	private void accountForNonMilitaryTime() {
		if (displayHoursAreValid()){
//			System.out.println("Converting "+Integer.toString(displayHours_)+" from standard time...");
			militaryTime_ = false;
			if (displayHours_ == 0) {	//the user entered 0 for an hour, which is military time for 12am
//				System.out.println("\tSetting 00 in military time");
				militaryTime_ = true;
				if (!inAM_){	//if we're not already in AM, then set AM
//					System.out.println("\t\tSetting to AM...");
					dayNightComboBox.setSelectedIndex(0); // set to AM
				}
			} else if (displayHours_ > noonHours_) {	//hours [3, 23] are also definitely military time
//				System.out.println("\tSetting PM military time");
				militaryTime_ = true;
				if (inAM_){
//					System.out.println("\t\tSetting to PM...");
					dayNightComboBox.setSelectedIndex(1); // set to PM
				}
			} else if (displayHours_ == noonHours_ && inAM_){	//12:00 am = 00 in military time
//				System.out.println("\tConverting 12Am to military");
				displayHours_=0;
			}else if (displayHours_<noonHours_ && !inAM_){//if displayHours are [1,11] and if they are in Pm, we must add 12 to get military time
				displayHours_+=noonHours_;
			}else{//otherwise, displayHours must be [1, 11] and in am, in which case leave it alone
//				System.out.println("\t Leaving hours as is.");
			}
	
			dayNightComboBox.setEnabled(!militaryTime_);

//		}else{
//			errorLabelErrorMode();
//			errorLabel.setVisible(true);
//			System.out.println("Cannot account for military time for badly formatted hours!");
		}
//		updateErrorLabel();
	}

//	/*
//	 * not used
//	 */
	private void updateDebugLabel() {
//		String debugText = Integer.toString(displayHours_)
//				+ ":"
//				+ Integer.toString(displayMinutes_) + ":"
//				+ Integer.toString(dayNightComboBox.getSelectedIndex())
//				+" "
//				+(militaryTime_?"(M)":"(S)");
//		System.out.println("Time="+debugText);
//		debugLabel.setText(debugText);
//
//		debugLabel.setForeground(validTimeFlag_ ? Color.BLACK : Color.RED);
	}

	/**
	 * makes the error label visible if the internally represented time is valid or a military time, invisible otherwise
	 */
	private void updateErrorLabel() {
		if (!validTimeFlag_){
			errorLabelErrorMode();
		}else if (militaryTime_){
			errorLabelInformativeMode("Military Time");
		}else{
			errorLabel.setVisible(false);
		}
	}
	
	/**
	 * sets the text of the error label with black color
	 * @param text the text to set the error label to use
	 */
	private void errorLabelInformativeMode(String text) {
		errorLabel.setText(text);
		errorLabel.setForeground(Color.black);
		errorLabel.setVisible(true);
	}

	/**
	 * sets the error label to have red text and the timeErrorText_ text
	 */
	private void errorLabelErrorMode() {
		errorLabelErrorMode(timeErrorText_);
	}

	/**
	 * Sets the text of the error label with red color
	 * @param text the text to set the error label to use
	 */
	private void errorLabelErrorMode(String text) {
		errorLabel.setText(text);
		errorLabel.setForeground(Color.RED);
		errorLabel.setVisible(true);
	}

	/**
	 * Verifies the given TextField has a valid positive number >0
	 * @param surceTextField the text field to get the number form
	 * @return the value if it is valid, or -1 if it is not
	 */
	private int getNumberFromTextField(JTextField sourceTextField) {
		int value = -1;
		try {
//			System.out.println("Validating text field: "+ sourceTextField.getText());
			value = Integer.parseInt(sourceTextField.getText());
//			System.out.println("Value of textField is: "+ Integer.toString(value));
		} catch (NumberFormatException e) {
//			System.err.println("Caught IOException: " + e.getMessage() + " returning "+Integer.toString(value));
		} catch (NullPointerException e) {
//			System.err.println("Caught IOException: " + e.getMessage() + " returning "+Integer.toString(value));
		}
		return value;
	}

	/**
	 * checks of a number is within the range [0 max] inclusive
	 * @param num the number to check
	 * @param max the upper bound of valid values the number can have
	 * @returns true if the number is within the bounds, false otherwise
	 */
	private boolean isValidNumber(int num, int max) {
		return ((num >= 0) && (num <= max));
	}

	/**
	 *  validates the current internal time variables displayMinutes_ and displayHours_
	 *  called whenever keys are typed in the minutes or hours text fields, after these texts have been validated as numbers
	 */
	private void validateDisplayTimes() {
		validTimeFlag_ = (displayHoursAreValid() && displayMinutesAreValid());
		if (!validTimeFlag_){
			errorLabelErrorMode();
		}
	}

	/**
	 * returns true of this TimePicker has been set to a valid time, false otherwise
	 * @return the validity of this timePicker's internal representation of its own time
	 */
	public boolean hasValidTime() {
		validateDisplayTimes();//validate one more time to make sure
		return validTimeFlag_;
	}

	/**
	 * returns the validity of this timePicekr's internal representation of its own hours
	 * @return true if the internal representation is valid, false otherwise
	 */
	private boolean displayHoursAreValid() {
		return isValidNumber(displayHours_, maxHours_);
	}

	/**
	 * returns the validity of this TimePicker's internal representation of its own minutes
	 * @return true if the internal representation is valid, false otherwise
	 */
	private boolean displayMinutesAreValid() {
		return isValidNumber(displayMinutes_, maxMinutes_);
	}

	/************************ SETTERS *********************/
	
	/**
	 * sets the minutes displayed and represented by this TimePicek
	 * @param minutes the minutes to set
	 */
	public void setMinutes(int minutes) {
		setDisplayMinutes(minutes);
		minutesTextField.setText(displayMinutesAreValid() ? Integer.toString(displayMinutes_) : "MM");
	}

	/**
	 * sets the hours displayed and represented by this TimePicker
	 * @param hours the hours to set in 24-hour time
	 */
	public void setHours(int hours) {
		setDisplayHours(hours);
		hoursTextField.setText(displayHoursAreValid() ? Integer.toString(displayHours_) : "HH");
	}


	/**
	 * sets the internally represented hour of this TimePicker if it is valid, sets to -1 if it is invalid
	 * @param hours the hours to set internally, in military time
	 */
	private void setDisplayHours(int hours) {
//		System.out.println("Setting Display Hours");
		if (isValidNumber(hours, maxHours_)) {
			displayHours_ = hours;
			validTimeFlag_ = displayMinutesAreValid();//hours are correct - lets see if minutes were as well
		} else {
			displayHours_ = -1;
			validTimeFlag_ = false;
		}
		accountForNonMilitaryTime();	//convert 12-hour standard time hours to military time if applicable
		updateErrorLabel();
	}

	/**
	 * sets the internally represented minutes of this TimePicker if it is valid
	 * @param minutes the minutes to set internally
	 */
	private void setDisplayMinutes(int minutes) {
		if (isValidNumber(minutes, maxMinutes_)) {
			displayMinutes_ = minutes;
			validTimeFlag_ = displayHoursAreValid();//minutes are correct, let's see if hours were as well.
		} else {
			displayMinutes_ = -1;
			validTimeFlag_ = false;
		}
		updateErrorLabel();
	}

	/************************ GETTERS *********************/

	/**
	 * get whether this timePicker is currently set to day time (AM) or night (PM)
	 * @return returns 0 if AM, 1 otherwise.
	 */
	public boolean getDayTime() {
		return (dayNightComboBox.getSelectedIndex() == 0);
	}

	/**
	 * gets the internally represented hours of this TimePicker
	 * @return the internal representation of hours (military time) or -1 if the internal representation is invalid
	 */
	public int getHours() {
		return displayHours_;
	}

	/**
	 * gets the internally represented minutes of this TimePicker
	 * @return the internal representation of minutes, or -1 if the internal represented minutes is invalid
	 */
	public int getMinutes() {
		return displayMinutes_;
	}

	/**
	 * gets the time represented by this TimePicker
	 * @return a Date object with hours and minutes corresponding to those represented by this TimePicker
	 */
	public Date getTime() {
		Date date = new Date();
		date.setHours(displayHours_);
		date.setMinutes(displayMinutes_);
		return date;
	}
}
