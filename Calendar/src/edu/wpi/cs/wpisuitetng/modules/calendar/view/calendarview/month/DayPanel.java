package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.month;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.CalendarUtils;

public class DayPanel extends JPanel {
	private boolean isToday;
	private boolean isWeekend;
	
	private int indexInMonth;
	private JLabel dateLabel;
	private JPanel datePanel;
	private JPanel containerPanel;

	private List<JPanel> multiDayEventsList;
	private List<JPanel> eventsList;
	private List<JPanel> commitmentsList;
	private Calendar todayDate;

	public DayPanel(int indexInMonth) {
		this.setLayout(new MigLayout("fill, insets 0"));
		if((indexInMonth+1)%7 == 0 || indexInMonth >= 35) {
			if((indexInMonth+1)%7 == 0) {
				if(indexInMonth < 35) this.setBorder(new MatteBorder(1, 1, 0, 1, Color.LIGHT_GRAY));
				else this.setBorder(new MatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
			} else this.setBorder(new MatteBorder(1, 1, 1, 0, Color.LIGHT_GRAY));
		} else this.setBorder(new MatteBorder(1, 1, 0, 0, Color.LIGHT_GRAY));

		todayDate = Calendar.getInstance();
		this.isToday = false;
		
		this.indexInMonth = indexInMonth;
		multiDayEventsList = new ArrayList<JPanel>();
		eventsList = new ArrayList<JPanel>();
		commitmentsList = new ArrayList<JPanel>();

		dateLabel = new JLabel("test",JLabel.RIGHT);
		containerPanel = new JPanel(new MigLayout("flowy, insets 0, gap 0 0 0 0"));

		datePanel = new JPanel(new MigLayout("fill, insets 0"));
		datePanel.add(dateLabel, "grow, alignx center");

		this.add(datePanel, "grow, wrap");
		this.add(containerPanel, "grow, push");
	}
	
	public boolean isWeekend()
	{
		if((this.todayDate.get(Calendar.DAY_OF_WEEK)+1)%7 == 2|| (this.todayDate.get(Calendar.DAY_OF_WEEK)+1)%7 == 1)
			return true;
		return false;
	}
	
	public void setIsToday(boolean isToday) {
		this.isToday = isToday;
		
		if(isToday) {
			this.setBackground(CalendarUtils.todayYellow);
			datePanel.setBackground(CalendarUtils.todayYellow);
			containerPanel.setBackground(CalendarUtils.todayYellow);
		} else if (isWeekend()){
			this.setBackground(CalendarUtils.weekendColor);
			datePanel.setBackground(CalendarUtils.weekendColor);
			containerPanel.setBackground(CalendarUtils.weekendColor);
		}else{
			this.setBackground(Color.WHITE);
			datePanel.setBackground(Color.WHITE);
			containerPanel.setBackground(Color.WHITE);
		}
	}

	public void setDate(Calendar date, boolean isInCurrentMonth) {
		this.todayDate = date;
		if(indexInMonth == 0 || date.get(Calendar.DATE) == 1)
			dateLabel.setText(CalendarUtils.monthNamesAbbr[date.get(Calendar.MONTH)] + " " + date.get(Calendar.DATE)+" ");
		else dateLabel.setText(date.get(Calendar.DATE) + " ");

		if(isInCurrentMonth) dateLabel.setForeground(Color.BLACK);
		else dateLabel.setForeground(Color.LIGHT_GRAY);
		
	}

	public void clearEvComs() {
		eventsList = new ArrayList<JPanel>();
		commitmentsList = new ArrayList<JPanel>();
		containerPanel.removeAll();
	}

	public void addEvent(Event event) {		
		JPanel eventsPanel = new JPanel(new MigLayout("insets 0, gap 0", "0[]push[]0", "0[]0"));
		if(isToday) eventsPanel.setBackground(new Color(236,252,144));
		else eventsPanel.setBackground(Color.WHITE);
		
		JLabel eventNameLabel = new JLabel(event.getName());
		eventNameLabel.setForeground(new Color(84, 84, 8));
		
		JLabel eventTimeLabel = new JLabel(" " + event.getStartDate().getHours() + ":"
				+ event.getStartDate().getMinutes());
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
		if(isToday) commitmentsPanel.setBackground(new Color(236,252,144));
		else commitmentsPanel.setBackground(Color.WHITE);
		
		JLabel commitmentNameLabel = new JLabel(commitment.getName());
		commitmentNameLabel.setForeground(new Color(84, 84, 8));
		
		JLabel commitmentTimeLabel = new JLabel(" " + commitment.getDueDate().getHours() + ":"
				+ commitment.getDueDate().getMinutes());
		commitmentTimeLabel.setFont(commitmentTimeLabel.getFont().getName(), commitmentTimeLabel.getFont().getStyle(), 8);
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
