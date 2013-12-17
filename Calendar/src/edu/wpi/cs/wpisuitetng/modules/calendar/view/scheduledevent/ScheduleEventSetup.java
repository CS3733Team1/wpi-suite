package edu.wpi.cs.wpisuitetng.modules.calendar.view.scheduledevent;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.wpi.cs.wpisuitetng.modules.calendar.controller.scheduledevent.AddEventController;
import net.miginfocom.swing.MigLayout;

public class ScheduleEventSetup extends JPanel implements KeyListener, ActionListener {

	private final String EMPTY_NAME_ERROR = "Name is required.";
	private JTextField nameTextField;
	private JTextField enterNameTextField;
	private JPanel enterNameErrorPanelWrapper;
	private JPanel nameErrorPanelWrapper;
	private JLabel nameErrorLabel;
	private JLabel enterNameErrorLabel;
	private JLabel dayOfWeekErrorLabel;
	private JButton addEventButton;
	private boolean enableAddEvent;
	private boolean containsDaysOfWeek;
	private DayOfWeekPanel dwp;
	private int startIndex;
	private int endIndex;
	private String title;
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
		
		this.add(new JLabel("Name:"), "cell 0 1, grow");
		enterNameErrorPanelWrapper = new JPanel(new MigLayout("fill, insets 0"));
		enterNameTextField = new JTextField();
		enterNameTextField.addKeyListener(this);
		enterNameErrorPanelWrapper.add(enterNameTextField, "alignx left, growx, w 5000");
		this.add(enterNameErrorPanelWrapper, "cell 0 1 ,growx,width 5000,alignx left");
		enterNameErrorLabel = new JLabel(EMPTY_NAME_ERROR);
		enterNameErrorLabel.setForeground(Color.RED);
		this.add(enterNameErrorLabel, "cell 0 1, grow");
		this.add(enterNameErrorLabel, "wrap");
		
		
		

		this.add(new JLabel("Start Time:"), "cell 0 2");
		this.add(new JLabel("End Time:"), "cell 0 3");



		JComboBox startTime = new JComboBox(hour);
		startTime.setSelectedIndex(8);

		startTime.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JComboBox combo = (JComboBox)e.getSource();
				startIndex = combo.getSelectedIndex();
			}
		});            
		this.add(startTime,"cell 0 2");

		JComboBox endTime = new JComboBox(hour);
		endTime.setSelectedIndex(8);
		endTime.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JComboBox combo = (JComboBox)e.getSource();
				endIndex = combo.getSelectedIndex();
//				String currentQuantity = hour[index];

//				System.out.println(index);
			}
		});            
		this.add(endTime,"cell 0 3");


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
		addEventButton.addActionListener(new AddEventController(this));


		this.add(addEventButton, "cell 0 7,alignx left");



	}

	public ArrayList<String> getDayList()
	{
		return dwp.updateList();
	}
	public int getEndIndex()
	{
		return startIndex;
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

	public String getUser(){
		System.out.println("user"+enterNameTextField.getText());
		return enterNameTextField.getText();
	}
	
	public String getTitle(){
		return nameTextField.getText();
	}
	

	public void validate()
	{
		boolean nameFieldValidate = false;
		boolean enterNameFieldValidate = false;

		if(enterNameTextField.getText().trim().length() > 0){
			enterNameFieldValidate = true;
			enterNameErrorLabel.setVisible(false);
		}else{
			enterNameErrorPanelWrapper.setBorder(BorderFactory.createLineBorder(new Color(255, 51, 51)));
			enterNameErrorLabel.setText(EMPTY_NAME_ERROR);
			enterNameErrorLabel.setVisible(true);
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

		if(containsDaysOfWeek && nameFieldValidate && enterNameFieldValidate)
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
		if(e.getActionCommand().equals("addevent")) {
			this.createSchedule();
		} else {
			validate();
		}

	}

	private void createSchedule() {
		System.out.println("We Did It Ladies and Gents");
		
	}








}
