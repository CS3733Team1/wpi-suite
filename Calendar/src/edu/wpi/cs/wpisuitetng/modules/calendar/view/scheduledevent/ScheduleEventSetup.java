package edu.wpi.cs.wpisuitetng.modules.calendar.view.scheduledevent;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class ScheduleEventSetup extends JPanel implements KeyListener, ActionListener {

	private final String EMPTY_NAME_ERROR = "Name is required.";
	private JTextField nameTextField;
	private JPanel nameErrorPanelWrapper;
	private JLabel nameErrorLabel;
	private JButton addEventButton;
	private boolean enableAddEvent;
	private final String[] hour = { "Midnight", "1 AM", "2 AM", "3 AM", "4 AM", "5 AM", "6 AM","7 AM", "8 AM", "9 AM","10 AM", "11 AM", "Noon",
			"1 PM", "2 PM", "3 PM", "4 PM", "5 PM", "6 PM","7 PM", "8 PM", "9 PM","10 PM", "11 PM"};
	public ScheduleEventSetup()
	{
		this.setLayout(new MigLayout("fill",
				"[]", "[5%][5%][5%][5%][50%][10%][10%][10%][10%][10%]"));
		setup();
		enableAddEvent = false;
	}
	
	public void setup()
	{
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
		
		this.add(new JLabel("Start Time:"), "cell 0 1");
		this.add(new JLabel("End Time:"), "cell 0 2");
		
		this.add(new JLabel("Select Days to Schedule", JLabel.CENTER), "cell 0 3, alignx center, grow");
		DayOfWeekPanel dwp = new DayOfWeekPanel();
		this.add(dwp,"cell 0 4, grow,alignx center");
		

		JComboBox startTime = new JComboBox(hour);
		startTime.setSelectedIndex(8);
		
		startTime.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                
            }
        });            
		this.add(startTime,"cell 0 1");
		
		JComboBox endTime = new JComboBox(hour);
		endTime.setSelectedIndex(8);
		endTime.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	 JComboBox combo = (JComboBox)e.getSource();
            	 int index = combo.getSelectedIndex();
                 String currentQuantity = hour[index];
                 
                 System.out.println(index);
            }
        });            
		this.add(endTime,"cell 0 2");
		
		addEventButton = new JButton("Add Event");
		addEventButton.setActionCommand("addevent");
//		addEventButton.addActionListener(new AddEventController(this));

		this.add(addEventButton, "cell 0 6,alignx left");
		
		
		
	}
	
	public void validate()
	{
		if(nameTextField.getText().trim().length() == 0) {
			nameErrorPanelWrapper.setBorder(BorderFactory.createLineBorder(new Color(255, 51, 51)));
			nameErrorLabel.setText(EMPTY_NAME_ERROR);
			nameErrorLabel.setVisible(true);
			enableAddEvent = false;
		} else {
			nameErrorPanelWrapper.setBorder(BorderFactory.createLineBorder(new Color(255, 51, 51, 0)));
			nameErrorLabel.setVisible(false);
		}
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
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
	
}
