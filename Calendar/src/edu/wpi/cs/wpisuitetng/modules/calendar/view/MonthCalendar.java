package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Color;
import java.awt.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

public class MonthCalendar extends JPanel implements ICalendarViewComponent{

	private JPanel contentPane;
	private JPanel panel_1;
	private JPanel panel_7;
	private JPanel panel_8;
	private JPanel panel_9;
	private JPanel panel_10;
	private JPanel panel_11;
	private JPanel panel_12;
	private JPanel panel_13;
	private JLabel lblSunday;
	private JLabel lblMonday;
	private JLabel lblTuesday;
	private JLabel lblWednesday;
	private JLabel lblThursday;
	private JLabel lblFriday;
	private JLabel lblSaturday;
	private ArrayList<JLabel> dayLabel = new ArrayList<JLabel>();
	private ArrayList<JPanel> panelList = new ArrayList<JPanel>();
	private ArrayList<GridBagConstraints> gridBagList = new ArrayList<GridBagConstraints>();
	private JPanel panel = new JPanel();

	private Calendar mycal;
	private int currentMonth;

	/**
	 * Create the frame.
	 */
	public MonthCalendar() {
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setBounds(100, 100, 700, 600);
		//setBorder(new EmptyBorder(5, 5, 5, 5));
		//setLayout(new BorderLayout(0, 0));
		//setContentPane(contentPane);

		int gridSize = 100;

		add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{gridSize, gridSize, gridSize, gridSize, gridSize, gridSize, gridSize};
		gbl_panel.rowHeights = new int[]{gridSize, gridSize, gridSize, gridSize, gridSize, gridSize, gridSize};
		gbl_panel.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
		gbl_panel.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
		panel.setLayout(gbl_panel);

		
		currentMonth = Calendar.NOVEMBER;
		//TODO: Update with current month and year
		mycal = new GregorianCalendar(2013, Calendar.NOVEMBER, 1);
		
		// Get the number of days in that month
		int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH); // 28
		int dayOfWeek = mycal.get(Calendar.DAY_OF_WEEK);
		mycal.set(Calendar.DAY_OF_MONTH, daysInMonth);
		int numWeeksMonth = mycal.get(Calendar.WEEK_OF_MONTH);

		if(dayLabel.isEmpty()){
			addDayLabels();
			addDays(daysInMonth,dayOfWeek, numWeeksMonth);
		}
		else
		{
			updateDays(daysInMonth, dayOfWeek, numWeeksMonth);
		}
		
		System.out.println("finished!");
		panel.setVisible(true);

	}

	public void addDayLabels()
	{

		panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 0;
		panel.add(panel_1, gbc_panel_1);

		lblSunday = new JLabel("Sunday");
		panel_1.add(lblSunday);

		panel_7 = new JPanel();
		panel_7.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_panel_7 = new GridBagConstraints();
		gbc_panel_7.insets = new Insets(0, 0, 5, 5);
		gbc_panel_7.fill = GridBagConstraints.BOTH;
		gbc_panel_7.gridx = 1;
		gbc_panel_7.gridy = 0;
		panel.add(panel_7, gbc_panel_7);

		lblMonday = new JLabel("Monday");
		panel_7.add(lblMonday);

		panel_8 = new JPanel();
		panel_8.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_panel_8 = new GridBagConstraints();
		gbc_panel_8.insets = new Insets(0, 0, 5, 5);
		gbc_panel_8.fill = GridBagConstraints.BOTH;
		gbc_panel_8.gridx = 2;
		gbc_panel_8.gridy = 0;
		panel.add(panel_8, gbc_panel_8);

		lblTuesday = new JLabel("Tuesday");
		panel_8.add(lblTuesday);

		panel_9 = new JPanel();
		panel_9.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_panel_9 = new GridBagConstraints();
		gbc_panel_9.insets = new Insets(0, 0, 5, 5);
		gbc_panel_9.fill = GridBagConstraints.BOTH;
		gbc_panel_9.gridx = 3;
		gbc_panel_9.gridy = 0;
		panel.add(panel_9, gbc_panel_9);

		lblWednesday = new JLabel("Wednesday");
		panel_9.add(lblWednesday);


		panel_10 = new JPanel();
		panel_10.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_panel_10 = new GridBagConstraints();
		gbc_panel_10.insets = new Insets(0, 0, 5, 5);
		gbc_panel_10.fill = GridBagConstraints.BOTH;
		gbc_panel_10.gridx = 4;
		gbc_panel_10.gridy = 0;
		panel.add(panel_10, gbc_panel_10);

		lblThursday = new JLabel("Thursday");
		panel_10.add(lblThursday);

		panel_11 = new JPanel();
		panel_11.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_panel_11 = new GridBagConstraints();
		gbc_panel_11.insets = new Insets(0, 0, 5, 5);
		gbc_panel_11.fill = GridBagConstraints.BOTH;
		gbc_panel_11.gridx = 5;
		gbc_panel_11.gridy = 0;
		panel.add(panel_11, gbc_panel_11);

		lblFriday = new JLabel("Friday");
		panel_11.add(lblFriday);

		panel_12 = new JPanel();
		panel_12.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_panel_12 = new GridBagConstraints();
		gbc_panel_12.insets = new Insets(0, 0, 5, 0);
		gbc_panel_12.fill = GridBagConstraints.BOTH;
		gbc_panel_12.gridx = 6;
		gbc_panel_12.gridy = 0;
		panel.add(panel_12, gbc_panel_12);

		lblSaturday = new JLabel("Saturday");
		panel_12.add(lblSaturday);
	}


	public void addDays(int daysInMonth, int dayOfWeekFirstWeek, int numWeeksMonth)
	{
		dayOfWeekFirstWeek = dayOfWeekFirstWeek-1;
		System.out.println("Day of Week: " +dayOfWeekFirstWeek);
		boolean firstDaySet = false;
		int day = 1;
		int gridIndex = 0;
		for(int i = 1; i < 6; i++)
		{
			for(int j = 0; j < 7; j++)
			{

				if(j == dayOfWeekFirstWeek && !firstDaySet)
				{
					firstDaySet = true;
					panelList.add(new JPanel());
					panelList.get(gridIndex).setBorder(new LineBorder(new Color(0, 0, 0)));
					gridBagList.add(new GridBagConstraints());
					gridBagList.get(gridIndex).insets = new Insets(0, 0, 5, 5);
					gridBagList.get(gridIndex).fill = GridBagConstraints.BOTH;
					gridBagList.get(gridIndex).gridx = j;
					gridBagList.get(gridIndex).gridy = i;
					panel.add(panelList.get(gridIndex), gridBagList.get(gridIndex));
					StringBuilder sb = new StringBuilder();
					sb = sb.append("");
					sb = sb.append(day);
					dayLabel.add(new JLabel(sb.toString()));
					panelList.get(gridIndex).add(dayLabel.get(day-1));
				}
				else if(day < daysInMonth && firstDaySet)
				{
					day++;
					panelList.add(new JPanel());
					panelList.get(gridIndex).setBorder(new LineBorder(new Color(0, 0, 0)));
					gridBagList.add(new GridBagConstraints());
					gridBagList.get(gridIndex).insets = new Insets(0, 0, 5, 5);
					gridBagList.get(gridIndex).fill = GridBagConstraints.BOTH;
					gridBagList.get(gridIndex).gridx = j;
					gridBagList.get(gridIndex).gridy = i;
					panel.add(panelList.get(gridIndex), gridBagList.get(gridIndex));
					StringBuilder sb = new StringBuilder();
					sb = sb.append("");
					sb = sb.append(day);
					dayLabel.add(new JLabel(sb.toString()));
					panelList.get(gridIndex).add(dayLabel.get(day-1));

				}
				else
				{
					panelList.add(new JPanel());
					panelList.get(gridIndex).setBorder(new LineBorder(new Color(0, 0, 0)));
					gridBagList.add(new GridBagConstraints());
					gridBagList.get(gridIndex).insets = new Insets(0, 0, 5, 5);
					gridBagList.get(gridIndex).fill = GridBagConstraints.BOTH;
					gridBagList.get(gridIndex).gridx = j;
					gridBagList.get(gridIndex).gridy = i;
					panel.add(panelList.get(gridIndex), gridBagList.get(gridIndex));
				}
				gridIndex++;

			}
		}
	}
	
	public void updateDays(int daysInMonth, int dayOfWeekFirstWeek, int numWeeksMonth)
	{
		dayLabel.clear();
		dayOfWeekFirstWeek = dayOfWeekFirstWeek-1;
		boolean firstDaySet = false;
		int day = 1;
		int gridIndex = 0;
		for(int i = 1; i < 6; i++)
		{
			for(int j = 0; j < 7; j++)
			{

				if(j == dayOfWeekFirstWeek && !firstDaySet)
				{
					firstDaySet = true;
					StringBuilder sb = new StringBuilder();
					sb = sb.append("");
					sb = sb.append(day);
					dayLabel.add(new JLabel(sb.toString()));
					panelList.get(gridIndex).add(dayLabel.get(day-1));
				}
				else if(day < daysInMonth && firstDaySet)
				{
					day++;
					StringBuilder sb = new StringBuilder();
					sb = sb.append("");
					sb = sb.append(day);
					dayLabel.add(new JLabel(sb.toString()));
					panelList.get(gridIndex).add(dayLabel.get(day-1));

				}
				else
				{

				}
				gridIndex++;

			}
		}
	}

	@Override
	public void next() {
		if (currentMonth == 12){
			currentMonth = 1;
		}
		else{
			currentMonth++;
		}
		Calendar next = new GregorianCalendar(2013, currentMonth, 1);
		mycal = next;
		
		
		int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH); // 28
		int dayOfWeek = mycal.get(Calendar.DAY_OF_WEEK);
		mycal.set(Calendar.DAY_OF_MONTH, daysInMonth);
		int numWeeksMonth = mycal.get(Calendar.WEEK_OF_MONTH);
		
		this.remove(panel);
		
		dayLabel = new ArrayList<JLabel>();
		panelList = new ArrayList<JPanel>();
		gridBagList = new ArrayList<GridBagConstraints>();
		panel = new JPanel();
		
		int gridSize = 100;

		add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{gridSize, gridSize, gridSize, gridSize, gridSize, gridSize, gridSize};
		gbl_panel.rowHeights = new int[]{gridSize, gridSize, gridSize, gridSize, gridSize, gridSize, gridSize};
		gbl_panel.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
		gbl_panel.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
		panel.setLayout(gbl_panel);

		if(dayLabel.isEmpty()){
			addDayLabels();
			addDays(daysInMonth,dayOfWeek, numWeeksMonth);
		}
		else
		{
			updateDays(daysInMonth, dayOfWeek, numWeeksMonth);
		}
		
	}

	@Override
	public void previous() {
		if (currentMonth == 1){
			currentMonth = 12;
		}
		else{
			currentMonth--;
		}
		Calendar next = new GregorianCalendar(2013, currentMonth, 1);
		mycal = next;
		
		
		int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH); // 28
		int dayOfWeek = mycal.get(Calendar.DAY_OF_WEEK);
		mycal.set(Calendar.DAY_OF_MONTH, daysInMonth);
		int numWeeksMonth = mycal.get(Calendar.WEEK_OF_MONTH);
		
		this.remove(panel);
		
		dayLabel = new ArrayList<JLabel>();
		panelList = new ArrayList<JPanel>();
		gridBagList = new ArrayList<GridBagConstraints>();
		panel = new JPanel();
		
		int gridSize = 100;

		add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{gridSize, gridSize, gridSize, gridSize, gridSize, gridSize, gridSize};
		gbl_panel.rowHeights = new int[]{gridSize, gridSize, gridSize, gridSize, gridSize, gridSize, gridSize};
		gbl_panel.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
		gbl_panel.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
		panel.setLayout(gbl_panel);

		if(dayLabel.isEmpty()){
			addDayLabels();
			addDays(daysInMonth,dayOfWeek, numWeeksMonth);
		}
		else
		{
			updateDays(daysInMonth, dayOfWeek, numWeeksMonth);
		}
		
	}

	@Override
	public void today() {
		Date today = new Date();
		currentMonth = today.getMonth();
		
		Calendar next = new GregorianCalendar(2013, currentMonth, 1);
		mycal = next;
		
		
		int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH); // 28
		int dayOfWeek = mycal.get(Calendar.DAY_OF_WEEK);
		mycal.set(Calendar.DAY_OF_MONTH, daysInMonth);
		int numWeeksMonth = mycal.get(Calendar.WEEK_OF_MONTH);
		
		this.remove(panel);
		
		dayLabel = new ArrayList<JLabel>();
		panelList = new ArrayList<JPanel>();
		gridBagList = new ArrayList<GridBagConstraints>();
		panel = new JPanel();
		
		int gridSize = 100;

		add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{gridSize, gridSize, gridSize, gridSize, gridSize, gridSize, gridSize};
		gbl_panel.rowHeights = new int[]{gridSize, gridSize, gridSize, gridSize, gridSize, gridSize, gridSize};
		gbl_panel.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
		gbl_panel.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
		panel.setLayout(gbl_panel);

		if(dayLabel.isEmpty()){
			addDayLabels();
			addDays(daysInMonth,dayOfWeek, numWeeksMonth);
		}
		else
		{
			updateDays(daysInMonth, dayOfWeek, numWeeksMonth);
		}
	}

}
