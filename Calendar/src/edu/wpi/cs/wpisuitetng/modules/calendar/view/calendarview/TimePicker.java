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

import net.miginfocom.swing.MigLayout;

import com.toedter.calendar.JCalendar;

/**
 * @author DanF
 * 
 */
public class TimePicker extends JPanel{
	// components within this panel
	private JTextField hoursTextField;
	private JTextField minutesTextField;
	private JComboBox<String> dayNightComboBox;
	private JLabel colonLabel;
	private JLabel errorLabel;
	private String timeErrortext_ = "Invalid Time!";
//	private JLabel debugLabel;

	// time Data containing the time currently displayed in this picker
	private int displayHours_;
	private int displayMinutes_;

	// time constants
	private final static int maxMinutes_ = 59;
	private final static int maxHours_ = 23;
	private final static int noonHours_ = 12;

	boolean validHours_ = false;
	boolean validMinutes_ = false;
	boolean validTimeFlag_ = false;

	// default - makes a new time
	public TimePicker() {
		buildLayout(-1, -1);
	}

	// TimePicker from given hours and minutes
	public TimePicker(int hours, int minutes) {
		validHours_ = false;
		validMinutes_ = false;
		validTimeFlag_ = false;
		errorLabel.setVisible(false);
		buildLayout(hours, minutes);
	}

	// TimePicker from given date
	public TimePicker(Date date) {
		validateDisplayTimes();
		buildLayout(displayHours_, displayMinutes_);
	}

	// Build the component with the given hours and minutes
	void buildLayout(int hours, int minutes) {

		this.setLayout(new MigLayout("",
				"[25:30,grow,fill][][25:30,grow,fill][][][]", "[30:n,center]"));
		this.setBorder(null); // no border

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
				validateHours();
				updateDebugLabel();
			}
		});
		this.add(hoursTextField, "cell 0 0,growx, gp1, aligny center");

		// colon between H:M
		colonLabel = new JLabel(":");
		this.add(colonLabel, "cell 1 0");

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
				validateMinutes();
				updateDebugLabel();
			}
		});
		this.add(minutesTextField, "cell 2 0,growx, gp1, aligny center");

		// AM/PM
		dayNightComboBox = new JComboBox<String>();
		dayNightComboBox.setModel(new DefaultComboBoxModel<String>(
				new String[] { "AM", "PM" }));
		dayNightComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				int Id = e.getID();
				setAmPm(Id);
			}
		});
		this.add(dayNightComboBox, "cell 3 0,alignx right,aligny center");
		accountForMilitaryTime();

		errorLabel = new JLabel();
		errorLabelErrorMode();
		this.add(errorLabel, "cell 4 0,alignx right,aligny center");

		// set things based on given times
		setHours(hours);
		setMinutes(minutes);
		updateErrorLabel();

//		// DEBUG
//		JLabel debugLabel = new JLabel("DEBUG");
//		this.add(debugLabel, "cell 5 0");
	}

	/**************************** VALIDATION ************************/

	public void setAmPm(int i) {
		if (i == 1) {// PM
			if (displayHours_ < noonHours_) {
				displayHours_ += noonHours_;
			}
		} else {// AM
			if (displayHours_ > noonHours_) {
				displayHours_ -= noonHours_;
			}
		}
	}

	// main input validation method
	// gets the current input from the hour and minute text fields and validates
	// them.
	private void validateInputTime() {
		validateMinutes();
		validateHours();
		validateDisplayTimes();
	}

	// gets the minutes from the text field, validates, and sets as
	// displayMinutes_ if it's valid
	private void validateMinutes() {
		int minutes = getNumberFromTextField(minutesTextField);
		setDisplayMinutes(minutes);

		validateDisplayTimes();
	}

	// gets the hours from the text field, validates, and sets as displayHours_
	// if it's valid
	private void validateHours() {
		int hours = getNumberFromTextField(hoursTextField);
		setDisplayHours(hours);
		validateDisplayTimes();
	}

	// assumes displayHours has already been validated. If displayHours is > 12
	// or 0 (military time), then sets AM/PM appropriately and disables that
	// checkbox
	private void accountForMilitaryTime() {
		boolean militaryTime = false;
		if (displayHours_ == 0) {
			militaryTime = true;
			dayNightComboBox.setSelectedIndex(0); // set to AM
		} else if (displayHours_ > noonHours_) {
			militaryTime = true;
			dayNightComboBox.setSelectedIndex(1); // set to PM
		} else {
			militaryTime = false;
		}

		if (militaryTime) {
			dayNightComboBox.setEnabled(false);

			// if the time is valid
			if (validTimeFlag_) {
				// adapt the error label to indicate military time is being used
				errorLabelInformativeMode("Military Time");
				errorLabel.setVisible(true);
			}
		} else {
			dayNightComboBox.setEnabled(true);
			errorLabelErrorMode();
		}
	}

	private void updateDebugLabel() {
		String debugText = Integer.toString(displayHours_) + ":"
				+ Integer.toString(displayMinutes_) + ":"
				+ Integer.toString(dayNightComboBox.getSelectedIndex());
		System.out.println(debugText);
		// debugLabel.setText(debugText);
	}

	private void errorLabelInformativeMode(String text) {
		errorLabel.setText(text);
		errorLabel.setForeground(Color.black);
	}

	private void errorLabelErrorMode() {
		errorLabelErrorMode(timeErrortext_);
	}

	private void errorLabelErrorMode(String text) {
		errorLabel.setText(text);
		errorLabel.setForeground(Color.RED);
	}

	// Verifies the given TextField has a valid positive number >0
	// Returns the value if it is valued, or -1 if it is not
	private int getNumberFromTextField(JTextField sourceTextField) {
		int value = -1;
		try {
			System.out.println("Validating text field: "
					+ sourceTextField.getText());
			value = Integer.parseInt(sourceTextField.getText());
			System.out.println("Value of textField is: "
					+ Integer.toString(value));
		} catch (NumberFormatException e) {
			System.err.println("Caught IOException: " + e.getMessage());
		} catch (NullPointerException e) {
			System.err.println("Caught IOException: " + e.getMessage());
		}
		return value;
	}

	private boolean isValidNumber(int num, int max) {
		return ((num <= max) && (num >= 0));
	}

	// validates the current internal time variables
	private void validateDisplayTimes() {
		validTimeFlag_ = (isValidNumber(displayMinutes_, maxMinutes_) && isValidNumber(displayHours_, maxHours_));
		errorLabel.setVisible(!validTimeFlag_);
		if (!validTimeFlag_){
			errorLabelErrorMode();
		}
		accountForMilitaryTime();
	}

	public boolean hasValidTime() {
		return validTimeFlag_;
	}

	private boolean displayHoursAreValid() {
		return isValidNumber(displayHours_, maxHours_);
	}

	private boolean displayMinutesAreValid() {
		return isValidNumber(displayMinutes_, maxMinutes_);
	}

	/************************ SETTERS *********************/
	public void setMinutes(int minutes) {
		setDisplayMinutes(minutes);
		updateMinutesTextField();
	}

	public void setHours(int hours) {
		setDisplayHours(hours);
		updateHoursTextField();
	}

	private void updateHoursTextField() {
		hoursTextField.setText(displayHoursAreValid() ? Integer
				.toString(displayHours_) : "HH");
	}

	private void updateMinutesTextField() {
		minutesTextField.setText(displayMinutesAreValid() ? Integer
				.toString(displayMinutes_) : "MM");
	}

	private void updateErrorLabel() {
		errorLabel.setVisible(!validTimeFlag_);
	}

	private void setDisplayHours(int hours) {
		if (isValidNumber(hours, maxHours_)) {
			displayHours_ = hours;
			validTimeFlag_ = isValidNumber(displayMinutes_, maxMinutes_);
		} else {
			displayHours_ = -1;
			validTimeFlag_ = false;
		}
		updateErrorLabel();
	}

	private void setDisplayMinutes(int minutes) {
		if (isValidNumber(minutes, maxMinutes_)) {
			displayMinutes_ = minutes;
			validTimeFlag_ = isValidNumber(displayHours_, maxHours_);
		} else {
			displayMinutes_ = -1;
			validTimeFlag_ = false;
		}
		updateErrorLabel();
	}

	/************************ GETTERS *********************/

	// returns 0 if AM, 1 otherwise.
	public boolean getDayTime() {
		return (dayNightComboBox.getSelectedIndex() == 0);
	}

	public int getHours() {
		return displayHours_;
	}

	public int getMinutes() {
		return displayMinutes_;
	}

	public Date getTime() {
		Date date = new Date();
		date.setHours(displayHours_);
		date.setMinutes(displayMinutes_);
		return date;
	}
}
