package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.month;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.CalendarUtils;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.DateUtils;

public class DayPanel extends JPanel {
	private boolean isToday;
	private boolean isWeekend;
	private boolean isInCurrentMonth;
	
	private int indexInMonth;
	private JLabel day;
	private JPanel datePanel;
	private JPanel containerPanel;

	private List<JPanel> multiDayEventsList;
	private List<JPanel> eventsList;
	private List<JPanel> commitmentsList;
	private Calendar todayDate;

	public DayPanel(int indexInMonth) {
		this.setLayout(new MigLayout("fill, insets 0"));
		/*
		if((indexInMonth+1)%7 == 0 || indexInMonth >= 35) {
			if((indexInMonth+1)%7 == 0) {
				if(indexInMonth < 35) this.setBorder(new MatteBorder(1, 1, 0, 1, Color.LIGHT_GRAY));
				else this.setBorder(new MatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
			} else this.setBorder(new MatteBorder(1, 1, 1, 0, Color.LIGHT_GRAY));
		} else this.setBorder(new MatteBorder(1, 1, 0, 0, Color.LIGHT_GRAY));
		*/
		
		this.setBorder(new MatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
		
		this.isToday = false;
		
		this.indexInMonth = indexInMonth;
		multiDayEventsList = new ArrayList<JPanel>();
		eventsList = new ArrayList<JPanel>();
		commitmentsList = new ArrayList<JPanel>();

		day = new JLabel("", JLabel.LEFT);
		containerPanel = new JPanel(new MigLayout("flowy, insets 0, gap 0 0 0 0"));

		datePanel = new JPanel(new MigLayout("fill, insets 0"));
		datePanel.add(day, "grow");

		this.isWeekend = (indexInMonth + 1) % 7 <= 1;
		
		this.add(datePanel, "grow, wrap");
		this.add(containerPanel, "grow, push");
	}

	public void setIsToday(boolean isToday) {
		this.isToday = isToday;
	}
	
	public void updateColors() {
		if(isInCurrentMonth) day.setForeground(Color.BLACK);
		else day.setForeground(Color.LIGHT_GRAY);
		if(isToday) {
			day.setForeground(CalendarUtils.thatBlue);
			this.setBackground(CalendarUtils.selectionColor);
			datePanel.setBackground(CalendarUtils.selectionColor);
			containerPanel.setBackground(CalendarUtils.selectionColor);
		} else {
			if(isWeekend) {
				this.setBackground(CalendarUtils.weekendColor);
				datePanel.setBackground(CalendarUtils.weekendColor);
				containerPanel.setBackground(CalendarUtils.weekendColor);
			}
			else {
				this.setBackground(Color.WHITE);
				datePanel.setBackground(Color.WHITE);
				containerPanel.setBackground(Color.WHITE);
			}
		}
	}

	public void setDate(Calendar date, boolean isInCurrentMonth) {
		this.isInCurrentMonth = isInCurrentMonth;
		this.todayDate = date;
		if(indexInMonth == 0 || date.get(Calendar.DATE) == 1)
			day.setText(CalendarUtils.monthNamesAbbr[date.get(Calendar.MONTH)] + " " + date.get(Calendar.DATE)+" ");
		else day.setText(date.get(Calendar.DATE) + " ");
	}

	public void clearEvComs() {
		eventsList = new ArrayList<JPanel>();
		commitmentsList = new ArrayList<JPanel>();
		containerPanel.removeAll();
	}

	public void addEvent(Event event) {		
		JPanel eventsPanel = new JPanel(new MigLayout("insets 0, gap 0", "0[]push[]0", "0[]0"));
		if(isToday) eventsPanel.setBackground(CalendarUtils.selectionColor);
		else {
			if(isWeekend) eventsPanel.setBackground(CalendarUtils.weekendColor);
			else eventsPanel.setBackground(Color.WHITE);
		}
		
		JLabel eventNameLabel = new JLabel(event.getName());
		eventNameLabel.setForeground(new Color(84, 84, 8));
		
		JLabel eventTimeLabel = new JLabel(DateUtils.timeToString(event.getStartDate()));
		eventTimeLabel.setFont(new Font(eventTimeLabel.getFont().getName(), Font.PLAIN, 8));
		eventTimeLabel.setForeground(new Color(84, 84, 8));

		eventsPanel.add(eventNameLabel, "wmin 0");
		eventsPanel.add(eventTimeLabel);
		
		eventsList.add(eventsPanel);
	}

	public void addMultiDayEvent(Event event, boolean isAllDay) {

	}
	
	public void addAllDayCommitment(Commitment commitment) {

	}

	public void addCommitment(Commitment commitment) {
		JPanel commitmentsPanel = new JPanel(new MigLayout("insets 0, gap 0", "0[]push[]0", "0[]0"));
		if(isToday) commitmentsPanel.setBackground(CalendarUtils.selectionColor);
		else {
			if(isWeekend) commitmentsPanel.setBackground(CalendarUtils.weekendColor);
			else commitmentsPanel.setBackground(Color.WHITE);
		}
		
		JLabel commitmentNameLabel = new JLabel(commitment.getName());
		commitmentNameLabel.setForeground(new Color(84, 84, 8));
		
		JLabel commitmentTimeLabel = new JLabel(DateUtils.timeToString(commitment.getDueDate()));
		commitmentTimeLabel.setFont(new Font(commitmentTimeLabel.getFont().getName(), Font.PLAIN, 8));
		commitmentTimeLabel.setForeground(new Color(84, 84, 8));

		commitmentsPanel.add(commitmentNameLabel, "wmin 0");
		commitmentsPanel.add(commitmentTimeLabel);
		
		commitmentsList.add(commitmentsPanel);
	}

	// Updates the layout [if there are new commitments or events or a resize]
	public void updateEveComs() {
		containerPanel.removeAll();
		JLabel temp = new JLabel("I have Height!");

		int numEveComs = eventsList.size() + commitmentsList.size();
		int width = this.getParent().getWidth()/7;

		if(numEveComs > 0) {
			int numRows = Math.max(1, (int)((float)containerPanel.getHeight() / (float)temp.getPreferredSize().height + 0.5));
			int savedRows = numRows;
			if(numRows >= numEveComs) {
				for(JPanel eventLabel: eventsList) containerPanel.add(eventLabel, "gap left 5, gap right 5, aligny top, wmin 0, hmin 0, w " + width + ", wmax " + width);
				for(JPanel commitmentLabel: commitmentsList) containerPanel.add(commitmentLabel, "gap left 5, gap right 5, aligny top, wmin 0, hmin 0, w " + width + ", wmax " + width);
			} else {
				for(JPanel eventLabel: eventsList) {
					if(numRows-- > 1) containerPanel.add(eventLabel, "gap left 5, gap right 5, aligny top, wmin 0, hmin 0, w " + width + ", wmax " + width);
					else break;
				}

				for(JPanel commitmentLabel: commitmentsList) {
					if(numRows-- > 1) containerPanel.add(commitmentLabel, "gap left 5, gap right 5, aligny top, wmin 0, hmin 0, w " + width + ", wmax " + width);
					else break;
				}

				containerPanel.add(new JLabel((numEveComs-savedRows+1) + " more..."), "gap left 5");
			}
		}
	}
}
