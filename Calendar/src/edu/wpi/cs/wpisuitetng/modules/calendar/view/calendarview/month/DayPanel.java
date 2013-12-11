package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.month;

import java.awt.Color;
import java.util.Arrays;
import java.util.Calendar;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.CalendarUtils;

public class DayPanel extends JPanel {
	private boolean isToday;
	private boolean isWeekend;
	private boolean isInCurrentMonth;

	private int indexInMonth;
	private JLabel day;
	private JPanel datePanel;
	private JPanel containerPanel;

	private boolean hasMultiDay;
	private int numEvComs;
	private int numDisplayableEvComs;
	private EvComPanel[] evComPanels;

	private int evComPanelsIndex;

	private Calendar date;
	private int numEvComRows;

	public DayPanel(int indexInMonth) {
		this.indexInMonth = indexInMonth;
		this.isWeekend = (indexInMonth + 1) % 7 <= 1;

		this.isToday = false;

		this.setLayout(new MigLayout("fill, insets 0"));
		this.setBorder(new MatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

		datePanel = new JPanel(new MigLayout("fill, insets 0"));

		day = new JLabel("", JLabel.RIGHT);
		datePanel.add(day, "grow, gap right 5");

		containerPanel = new JPanel(new MigLayout("flowy, insets 0, gap 0 0 0 0"));

		this.clearEvComs();

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
			day.setText(CalendarUtils.monthNamesAbbr[date.get(Calendar.MONTH)] + " " + date.get(Calendar.DATE));
		else day.setText(date.get(Calendar.DATE) + "");
	}

	public Calendar getDate() {return this.date;}

	public void clearEvComs() {
		this.numEvComs = 0;
		this.evComPanels = null;
		this.evComPanelsIndex = 0;
		this.numDisplayableEvComs = 0;
		this.numEvComRows = 0;
		this.hasMultiDay = false;
	}

	public void setHasMultiDay(boolean hasMultiDay) {this.hasMultiDay = hasMultiDay;}
	public boolean hasMultiDay() {return this.hasMultiDay;}

	public void initEvComPanels() {this.evComPanels = new EvComPanel[this.numEvComs];}

	public int getNumEvComs() {return this.numEvComs;}
	public void incrementNumEvComs() {this.numEvComs++;}

	public void setNumDisplayableEvComs(int numDisplayableEvComs) {
		this.numDisplayableEvComs = numDisplayableEvComs;
		this.numEvComRows = numDisplayableEvComs;
	}

	public void decrementNumDisplayableEvComs() {this.numDisplayableEvComs--;}

	public int addEvComPanel(EvComPanel evComPanel) {
		while(evComPanels[evComPanelsIndex] != null) evComPanelsIndex++;
		evComPanels[evComPanelsIndex] = evComPanel;
		return evComPanelsIndex;
	}

	public void addEvComPanelAt(EvComPanel evComPanel, int index) {
		if(index >= evComPanels.length) evComPanels = Arrays.copyOf(evComPanels, index+1);
		evComPanels[index] = evComPanel;
	}

	public void addFillerPanels() {
		for(int i = evComPanelsIndex; i < evComPanels.length; i++) {
			if(evComPanels[i] == null) evComPanels[i] = new FillerPanel(this.getBackground());
		}
	}

	public boolean hasHiddinMultiDayEvent() {
		if(this.numDisplayableEvComs < this.numEvComs) return evComPanels[this.numDisplayableEvComs-1].isMultiDay();
		else return false;
	}

	public MultiDayEventPanel getHiddenMultiDayEvent() {
		return (MultiDayEventPanel)evComPanels[this.numDisplayableEvComs-1];
	}

	// Updates the layout [if there are new commitments or events or a resize]
	public void updateEveComs(int width) {
		containerPanel.removeAll();
		int numRows = this.numEvComRows;

		if(this.numDisplayableEvComs < this.numEvComRows) {
			numRows = numDisplayableEvComs;
			for(JPanel panel: evComPanels) {
				if(numRows > 0) {
					numRows--;
					containerPanel.add(panel, "aligny top, wmin 0, hmin 0, w " + width + ", wmax " + width);
				}
				else break;
			}
			containerPanel.add(new JLabel((this.numEvComs-this.numDisplayableEvComs) + " more..."), "grow, south, gap left 5");
		} else {
			if(numRows >= this.numEvComs) {
				for(JPanel panel: evComPanels) {
					containerPanel.add(panel, "aligny top, wmin 0, hmin 0, w " + width + ", wmax " + width);
				}
			} else {
				for(JPanel panel: evComPanels) {
					if(numRows > 1) {
						numRows--;
						containerPanel.add(panel, "aligny top, wmin 0, hmin 0, w " + width + ", wmax " + width);
					}
					else break;
				}
				containerPanel.add(new JLabel((this.numEvComs-this.numDisplayableEvComs+1) + " more..."), "grow, south, gap left 5");
			}
		}
	}

	public int getContainerPanelHeight() {return containerPanel.getHeight();}
}
