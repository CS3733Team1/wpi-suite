/**
 * A combined General Date/Time Picker
 * self-validating
 */
package edu.wpi.cs.wpisuitetng.modules.calendar.view.utilities;

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
import javax.swing.border.Border;

import net.miginfocom.swing.MigLayout;

import com.toedter.calendar.JDateChooser;

//TODO
/*DayTimeChangedEvent -- break into day changed and time changed events?
 * input validation
 * test
 * integrate into commitments
 * add all day commitment checkbox
 * integrate with events
 * -increment end time appropriately when start time changes
 * -input validation
 * add all day event checkbox
 * */

/**
 * @author Dan
 *
 */
public class DateTimeChooser extends JPanel {
	private List<DateTimeChangedEventListener> listeners = new ArrayList<DateTimeChangedEventListener>();

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
	
	public void addDateTimeChangedListener(DateTimeChangedEventListener toAdd) {
        listeners.add(toAdd);
    }
	public void DateTimeChanged(){
		// Notify everybody that may be interested.
		DateTimeChangedEvent evt = new DateTimeChangedEvent(date_.toString());
        for (DateTimeChangedEventListener dtcel : listeners)
            dtcel.DateTimeChangedEventOccurred(evt);
	}
	
	public DateTimeChooser(String name, Date date){
		date_=date;
		System.out.println("Making DateTimeChooser with date: "+date_.toString());
		buildLayout(name);
	}
	public DateTimeChooser(){
		this("Date:");
	}
	public DateTimeChooser(String name){
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
		setLayout(new MigLayout("", "[][grow][grow][]", "[]"));
		
		//name label
		add(new JLabel(name), "cell 0 0");
				
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
			strTimes[index] = DateUtilities.timeToString(hour,0);
			strTimes[index+1] = DateUtilities.timeToString(hour,30);
		}
		timeCombo_ = new JComboBox<String>(strTimes);//new DefaultComboBoxModel(strTimes)
		timeCombo_.setSelectedIndex(dateTohalfHourIndex(date_));
		timeCombo_.setEditable(true);
		timeCombo_.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
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
		verifyDateTime();
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
		System.out.println("TIME CHANGED");
		fillTime();
	}
	private void dayChanged(){
		System.out.println("Day CHANGED");
		fillDay();
	}
	
	private void fillTime(){
		//normally we'de use getSelectedItem() but this does not change as characters are changed in the ComboBox's internal editor (text box), so we get it directly
		String strTime=(String)timeCombo_.getEditor().getItem();//(String)timeCombo_.getSelectedItem();
		System.out.println("Converting "+strTime+" to time...");
		Date time = DateUtilities.stringToDate(strTime);
		if (time!=null){
			date_.setHours(time.getHours());
			date_.setMinutes(time.getMinutes());
			if (date_.after(new Date())){
				timeValid_=true;
				dtValid_=dayValid_;
				updateError();
			}else{
				setTimeError();
			}
		}else{
			setTimeError();
		}
	}
	private void fillDay(){
		Date day=jDateChooser_.getDate();
		if (day!=null){
			Date rightNow = new Date();
			rightNow.setHours(0);
			rightNow.setMinutes(0);
			rightNow.setSeconds(0);
			if (day.after(rightNow)){
				date_.setYear(day.getYear());
				date_.setMonth(day.getMonth());
				date_.setDate(day.getDate());
				dayValid_=true;
				dtValid_=timeValid_;
				updateError();
			}else{
				setDayError();
			}
		}else{
			setDayError();
		}
	}

	public void setDate(Date date){
		jDateChooser_.setDate(date_);
		fillDay();
	}
	public void setTime(Date date){
		timeCombo_.setSelectedItem(DateUtilities.timeToString(date_));
		fillTime();
	}
	
	public Date getDate(){
//		fillTime();
//		fillDay();
//		verifyDateTime();
		return date_;
	}
	public int getHour(){
		return date_.getHours();
	}
	public int getMinutes(){
		return date_.getMinutes();
	}

	private void setError(String errorText){
		dtValid_=false;
		errorLabel.setText(errorText);
		errorLabel.setVisible(true);
		this.setBorder(errorBorder_);
	}
	private void clearErrors(){
		System.out.println("Clearing Error");
		errorLabel.setVisible(false);
//		timeCombo_.setBorder(normalBorder_);
//		jDateChooser_.setBorder(normalBorder_);
		this.setBorder(normalBorder_);
	}
	private void setDayError(){
		System.out.println("Setting Day Error");
		dayValid_=false;
//		jDateChooser_.setBorder(errorBorder_);
		setError(invalidDayErrorText_);
	}
	private void setTimeError(){
		System.out.println("Setting Time Error");
		timeValid_=false;
//		timeCombo_.setForeground(errorForgroundColor_);
		setError(invalidTimeErrorText_);
	}
	private void updateError(){
		if (dtValid_){
			clearErrors();
		}else{
			if (!dayValid_){
				setDayError();
			}else if (!timeValid_){
				setTimeError();
			}
		}
	}
	private void verifyDateTime(){
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
