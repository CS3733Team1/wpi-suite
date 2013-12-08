/**
 * A combined General Date/Time Picker
 * self-validating
 */
package edu.wpi.cs.wpisuitetng.modules.calendar.view.utilities;

import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import com.toedter.calendar.JDateChooser;

/**
 * @author Dan
 *
 */
public class DateTimePicker extends JPanel {
	private Date date_;
	
	private JDateChooser jDateChooser_;
	private JComboBox<String> timeCombo_;
	
	private boolean dtValid_;
	
	public DateTimePicker(Date date){
		date_=date;
		buildLayout();
	}
	public void DateTimePicker(){
		//TODO: set date_ as current time
		date_=new Date();
		buildLayout();
	}
	
	private void buildLayout(){
		setLayout(new MigLayout("", "[]", "[]"));
		
		jDateChooser_=new JDateChooser(date_);
		add(timeCombo_, "cell 0 0");
		
		String[] times;
		times = new String[24];
		for(int i = 0; i < 24; i++) {
			times[i] = i + ":00";
		}
		timeCombo_ = new JComboBox<String>(times);
		timeCombo_.setSelectedIndex(date_.getHours());
		add(jDateChooser_, "cell 1 0");
		
	}
	
	public void setDate(Date date){
		date_=date;
	}
	public Date getDate(){
		return date_;
	}
	
	public int getHour(){
		return date_.getHours();
	}
	
	public int getMinutes(){
		return date_.getMinutes();
	}
	
	public boolean hasValidDateTime(){
		return dtValid_;
	}
}
