package edu.wpi.cs.wpisuitetng.modules.calendar.view.scheduledevent;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.DateUtils;

public class ScheduleEventSetup extends JPanel implements KeyListener, ActionListener {

	private final String EMPTY_NAME_ERROR = "Name is required.";
	private final String SETTIMEEND = "Please set a time before: ";
	private final String SETTIMEPREV = "Please set a time after: ";		
	private JTextField nameTextField;
	private JPanel nameErrorPanelWrapper;
	private JLabel nameErrorLabel;
	private JLabel dayOfWeekErrorLabel;
	private JLabel startDateSelection;
	private JLabel endDateSelection;
	private JButton addEventButton;
	private boolean eventSelection;
	private boolean enableAddEvent;
	private boolean containsDaysOfWeek;
	private DayOfWeekPanel dwp;
	private int startIndex;
	private int endIndex;
	private String title;
	private JComboBox<String> startTime;
	private JComboBox<String> endTime;

	private final String[] hour = { "Midnight", "1 AM", "2 AM", "3 AM", "4 AM", "5 AM", "6 AM","7 AM", "8 AM", "9 AM","10 AM", "11 AM", "Noon",
			"1 PM", "2 PM", "3 PM", "4 PM", "5 PM", "6 PM","7 PM", "8 PM", "9 PM","10 PM", "11 PM"};
	public ScheduleEventSetup()
	{
		this.setLayout(new MigLayout("fill",
				"[]", "[5%][5%][5%][5%][5%][50%][10%][10%][10%][10%]"));
		setup();
		enableAddEvent = true;
	}

	public void setup()
	{
		eventSelection = false;
		containsDaysOfWeek = false;
		this.add(new JLabel("Event Name:"), "cell 0 0, grow");
		nameErrorPanelWrapper = new JPanel(new MigLayout("fill, insets 0"));
		nameTextField = new JTextField();
		nameTextField.addKeyListener(this);
		nameErrorPanelWrapper.add(nameTextField, "alignx left, growx, w 5000");
		this.add(nameErrorPanelWrapper, "cell 0 0 ,growx,width 5000,alignx left");
		nameErrorLabel = new JLabel(EMPTY_NAME_ERROR);
		nameErrorLabel.setForeground(Color.RED);
		this.add(nameErrorLabel, "cell 0 0, grow");
		this.add(nameErrorLabel, "wrap");

		this.add(new JLabel("Start Time:"), "cell 0 2");

		startTime = new JComboBox<String>(hour);
		startTime.setSelectedIndex(8);
		startTime.setActionCommand("startcombo");
		startTime.addActionListener(this);
		this.add(startTime,"cell 0 2");
		startDateSelection = new JLabel();
		startDateSelection.setVisible(false);
		this.add(startDateSelection, "cell 0 2");

		this.add(new JLabel("End Time:"), "cell 0 3");
		endTime = new JComboBox<String>(hour);
		endTime.setSelectedIndex(9);
		endTime.setActionCommand("endcombo");
		endTime.addActionListener(this);
		endDateSelection = new JLabel();
		endDateSelection.setVisible(false);
		endDateSelection.setForeground(Color.red);
		this.add(endTime,"cell 0 3");
		this.add(endDateSelection, "cell 0 3");

		startDateSelection = new JLabel();
		this.add(new JLabel("Select Days to Schedule", JLabel.CENTER), "cell 0 4, alignx center, grow");
		dwp = new DayOfWeekPanel();
		this.add(dwp,"cell 0 5, grow,alignx center");
		dayOfWeekErrorLabel = new JLabel();
		dayOfWeekErrorLabel.setVisible(true);
		this.add(dayOfWeekErrorLabel, "cell 0 6, grow, alignx center");
		dayOfWeekErrorLabel.setText("Please Selected A Day Of The Week");
		dayOfWeekErrorLabel.setForeground(Color.red);

		addEventButton = new JButton("Add Event");
		addEventButton.setEnabled(false);
		addEventButton.setActionCommand("addevent");


		this.add(addEventButton, "cell 0 7,alignx left");
	}

	public ArrayList<String> getDayList()
	{
		return dwp.updateList();
	}
	public int getEndIndex()
	{
		return endIndex;
	}

	public int getStartIndex()
	{
		return startIndex;
	}


	public void isDayOfWeekHaveDays(boolean state)
	{
		containsDaysOfWeek = state;
		validate();
	}

	public String getTitle(){
		return nameTextField.getText();
	}


	public void validate()
	{
		boolean nameFieldValidate = false;
		boolean timePicker = false;
		
		if(startIndex < endIndex){
			timePicker = true;
		}else{
			timePicker = false;
		}

		if(nameTextField.getText().trim().length() > 0){
			nameFieldValidate = true;
			nameErrorLabel.setVisible(false);
		}else{
			nameErrorPanelWrapper.setBorder(BorderFactory.createLineBorder(new Color(255, 51, 51)));
			nameErrorLabel.setText(EMPTY_NAME_ERROR);
			nameErrorLabel.setVisible(true);
		}
		if(!(containsDaysOfWeek)){
			dayOfWeekErrorLabel.setVisible(true);
		}else{
			dayOfWeekErrorLabel.setVisible(false);
		}

		if(containsDaysOfWeek && nameFieldValidate && timePicker)
			addEventButton.setEnabled(true);
		else
			addEventButton.setEnabled(false);

	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		validate();
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}



	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getActionCommand().equals("startcombo")){
			startIndex = startTime.getSelectedIndex();
			endIndex = endTime.getSelectedIndex();
			
			if(startIndex > endIndex)
			{
				StringBuilder sb = new StringBuilder();
				sb.append(SETTIMEPREV);
				sb.append(DateUtils.hourString(endTime.getSelectedIndex()));
				startDateSelection.setText(sb.toString());
				startDateSelection.setVisible(true);
				
				endTime.setSelectedIndex(startIndex + 1);
				endIndex = endTime.getSelectedIndex();
				
			}else{
				eventSelection = true;
				startDateSelection.setVisible(false);
			}
		}
		if(e.getActionCommand().equals("endcombo")){
			startIndex = startTime.getSelectedIndex();
			endIndex = endTime.getSelectedIndex();
			
			if(startIndex > endIndex)
			{
				endDateSelection.setVisible(true);
				StringBuilder sb = new StringBuilder();
				sb.append(SETTIMEPREV);
				sb.append(DateUtils.hourString(startIndex));
				endDateSelection.setText(sb.toString());
				
				eventSelection = false;
			}else{
				eventSelection = true;
				endDateSelection.setVisible(false);
			}
		}
	}

	public void addEventListener(ActionListener l) {
		addEventButton.addActionListener(l);
	}
}
