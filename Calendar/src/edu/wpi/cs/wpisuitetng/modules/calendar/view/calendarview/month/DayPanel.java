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
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.CalendarUtils;

public class DayPanel extends JPanel {
	private boolean isToday;
	private boolean isWeekend;
	private boolean isInCurrentMonth;

	private int indexInMonth;
	private JLabel day;
	private JPanel datePanel;
	private JPanel containerPanel;

	private List<EventPanel> eventsList;
	private List<CommitmentPanel> commitmentsList;
	
	private List<JPanel> multiDayEventPanelsWithFiller;
	
	private Calendar date;

	public DayPanel(int indexInMonth) {
		this.setLayout(new MigLayout("fill, insets 0"));

		this.setBorder(new MatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

		this.isToday = false;

		this.indexInMonth = indexInMonth;
		eventsList = new ArrayList<EventPanel>();
		commitmentsList = new ArrayList<CommitmentPanel>();
		multiDayEventPanelsWithFiller = new ArrayList<JPanel>();

		day = new JLabel("", JLabel.RIGHT);
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
		this.date = date;
		if(indexInMonth == 0 || date.get(Calendar.DATE) == 1)
			day.setText(CalendarUtils.monthNamesAbbr[date.get(Calendar.MONTH)] + " " + date.get(Calendar.DATE)+" ");
		else day.setText(date.get(Calendar.DATE) + " ");
	}

	public Calendar getDate() {return this.date;}

	public void clearEvComs() {
		multiDayEventPanelsWithFiller.clear();
		eventsList.clear();
		commitmentsList.clear();
		containerPanel.removeAll();
	}

	public EventPanel addEvent(Event event) {
		Color backgroundColor, selectedBackgroundColor;
		Color textColor, selectedTextColor;

		selectedBackgroundColor = event.getCategory().getColor();
		textColor = new Color(84, 84, 8);
		selectedTextColor = CalendarUtils.textColor(selectedBackgroundColor);

		if(isToday) backgroundColor = CalendarUtils.selectionColor;
		else {
			if(isWeekend) backgroundColor = CalendarUtils.weekendColor;
			else backgroundColor = Color.WHITE;
		}

		EventPanel eventPanel = new EventPanel(event, backgroundColor, selectedBackgroundColor, textColor, selectedTextColor);

		eventsList.add(eventPanel);
		
		return eventPanel;
	}

	public MultiDayEventPanel addMultiDayEvent(int indexOfMultiDay, Event event, Calendar startCal, Calendar endCal, boolean isFirstPanel, boolean isAllDay) {
		Color backgroundColor, selectedBackgroundColor;
		Color textColor, selectedTextColor;

		selectedBackgroundColor = event.getCategory().getColor();
		backgroundColor = CalendarUtils.blend(selectedBackgroundColor, Color.white, (float) 0.4);
		textColor = new Color(84, 84, 8);
		selectedTextColor = CalendarUtils.textColor(selectedBackgroundColor);

		int textType = 0;
		
		if(isAllDay) { // No Event times shown
			if(isFirstPanel) textType = 2;
			else if((indexInMonth+1)%7 == 1) textType = 2;
		} else { // Event times shown
			// If this EventPanel is on the first day show the start time
			if(startCal.get(Calendar.YEAR) == date.get(Calendar.YEAR) &&
					startCal.get(Calendar.DAY_OF_YEAR) == date.get(Calendar.DAY_OF_YEAR)) {
				textType = 1;
			}
			
			// Else if its not the start day but it's the first panel visible, only show the name
			else if(isFirstPanel) textType = 2;

			// Else If this EventPanel is on the last day show the end time
			else if(endCal.get(Calendar.YEAR) == date.get(Calendar.YEAR) &&
					endCal.get(Calendar.DAY_OF_YEAR) == date.get(Calendar.DAY_OF_YEAR)) {
				textType = 3;
			} else if((indexInMonth+1)%7 == 1) textType = 2;

			// Else don't show any times or text i.e. Do nothing
		}
		
		if(isFirstPanel) indexOfMultiDay = multiDayEventPanelsWithFiller.size();
		else {
			Color fillerColor;
			if(isToday) fillerColor = CalendarUtils.selectionColor;
			else {
				if(isWeekend) fillerColor = CalendarUtils.weekendColor;
				else fillerColor = Color.WHITE;
			}
			
			// We must put in filler panels so multiday events between days are aligned
			for(int i = 0; i < indexOfMultiDay - multiDayEventPanelsWithFiller.size(); i++) {
				JPanel filler = new JPanel(new MigLayout("insets 0"));
				filler.setBackground(fillerColor);
				JLabel fillerLabel = new JLabel("filler");
				fillerLabel.setForeground(fillerColor);
				filler.add(fillerLabel);
				multiDayEventPanelsWithFiller.add(filler);
			}
		}
		
		MultiDayEventPanel multiDayEventPanel = new MultiDayEventPanel(indexOfMultiDay, event, textType, backgroundColor, selectedBackgroundColor, textColor, selectedTextColor);

		multiDayEventPanelsWithFiller.add(multiDayEventPanel);

		return multiDayEventPanel;
	}

	public void addAllDayCommitment(Commitment commitment) {

	}

	public CommitmentPanel addCommitment(Commitment commitment) {
		Color backgroundColor, selectedBackgroundColor;
		Color textColor, selectedTextColor;

		selectedBackgroundColor = commitment.getCategory().getColor();
		textColor = new Color(84, 84, 8);
		selectedTextColor = CalendarUtils.textColor(selectedBackgroundColor);

		if(isToday) backgroundColor = CalendarUtils.selectionColor;
		else {
			if(isWeekend) backgroundColor = CalendarUtils.weekendColor;
			else backgroundColor = Color.WHITE;
		}
		
		CommitmentPanel commitmentPanel = new CommitmentPanel(commitment, backgroundColor, selectedBackgroundColor, textColor, selectedTextColor);

		commitmentsList.add(commitmentPanel);
		
		return commitmentPanel;
	}

	// Updates the layout [if there are new commitments or events or a resize]
	public void updateEveComs() {
		containerPanel.removeAll();
		JLabel temp = new JLabel("I have Height!");
		
		int numEvComs = multiDayEventPanelsWithFiller.size() + eventsList.size() + commitmentsList.size();
		int width = this.getParent().getWidth()/7;
		
		if(numEvComs > 0) {
			int numRows = Math.max(1, (int)((float)containerPanel.getHeight() / (float)temp.getPreferredSize().height));
			int savedRows = numRows;
			
			if(numRows >= numEvComs) {
				for(JPanel panel: multiDayEventPanelsWithFiller) containerPanel.add(panel, "aligny top, wmin 0, hmin 0, w " + width + ", wmax " + width);
				for(JPanel panel: eventsList) containerPanel.add(panel, "aligny top, wmin 0, hmin 0, w " + width + ", wmax " + width);
				for(JPanel panel: commitmentsList) containerPanel.add(panel, "aligny top, wmin 0, hmin 0, w " + width + ", wmax " + width);
			} else {
				for(JPanel panel: multiDayEventPanelsWithFiller) {
					if(numRows-- > 1) containerPanel.add(panel, "aligny top, wmin 0, hmin 0, w " + width + ", wmax " + width);
					else break;
				}
				
				for(JPanel panel: eventsList) {
					if(numRows-- > 1) containerPanel.add(panel, "aligny top, wmin 0, hmin 0, w " + width + ", wmax " + width);
					else break;
				}

				for(JPanel panel: commitmentsList) {
					if(numRows-- > 1) containerPanel.add(panel, "aligny top, wmin 0, hmin 0, w " + width + ", wmax " + width);
					else break;
				}

				containerPanel.add(new JLabel((numEvComs-savedRows+1) + " more..."), "gap left 5");
			}
		}
	}
}
