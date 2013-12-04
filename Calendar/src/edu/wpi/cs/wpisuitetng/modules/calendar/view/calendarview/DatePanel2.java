package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.EventListModel;

public class DatePanel2 extends JPanel implements ListDataListener{

	private Date paneldate;
	private ArrayList<JLabel> multiDayLabelList;
	private ArrayList<JLabel> eventLabelList;
	private ArrayList<JPanel> singleDayPanel;
	private ArrayList<JPanel> multiDayPanel;

	private JPanel date;
	private JPanel events;
	private JPanel multiDayEvents;
	private JPanel singleDayEvents;

	private Color text;
	private Color background;
	private JLabel dateLabel;
	private int day;
	private int unaddedEvents;
	private int heightOfJPanel;
	private int widthOfJPanel;
	private Date today;


	public DatePanel2(Date today){
		this.setLayout(new MigLayout("fill, insets 0, hmin 55", 
				"[]", 
				"[][]"));
		multiDayLabelList = new ArrayList<JLabel>();
		eventLabelList = new ArrayList<JLabel>();
		singleDayPanel = new ArrayList<JPanel>();
		multiDayPanel  = new ArrayList<JPanel>();

		
		this.setBorder(new MatteBorder(0,0, 1, 0, Color.gray));
		this.setDate(today);
		this.today = today;

		Calendar currentDay = Calendar.getInstance();
		Date d1 = new Date(currentDay.get(Calendar.YEAR)-1900,currentDay.get(Calendar.MONTH),currentDay.get(Calendar.DATE));
		Date d = new Date(113,11,27);
		Date d2 = new Date(113,11,28);
		Date d3 = new Date(113,11,16);
		Date d4 = new Date(113,11,1);
		Date d5 = new Date(113,11,15);

		Event eve = new Event("Test MultiDay",d3,d2);
		Event eve1 = new Event("Test 2", d, d3);
		Event ev2 = new Event("Overlap Test", d4, d5);
	
		addEvent(eve);	
		addEvent(ev2);
		updatePanel();
		configurePanel();
	}

	public void updateLayout()
	{
		setDay(day);
		setEvent();
	}

	public void setDate(Date today){
		paneldate = today;
	}

	public void setColors(Color text, Color background) {
		this.text = text;
		this.background = background;
	}

	public void setEvent()
	{
		configurePanel();
	}

	public Date getDate(){
		return paneldate;
	}

	public void addEvent(Event eve)
	{
		if(isMultiDayEvent(eve))
			addMultiDayEvents(eve);
		else
			addSingleDayEvent(eve);
		repaint();
	}

	private void addSingleDayEvent(Event eve) {
		JPanel eventPanel = new JPanel(new MigLayout("insets 0 5 0 0, hmin 15, hmax 15,gapy 0"));
		JLabel jLab = new JLabel(eve.getName());
		
		eventPanel.setBackground(background);
		Category c = eve.getCategory();
		if(c != null)
			jLab.setForeground(eve.getCategory().getColor());
		else
			jLab.setForeground(Color.black);

		eventPanel.add(jLab, "center, gapy 0 0");
		jLab.setFont(new Font(jLab.getFont().getFontName(), jLab.getFont().getStyle(), 10));
		singleDayPanel.add(eventPanel);
		eventLabelList.add(jLab);
	}

	private void addMultiDayEvents(Event eve)
	{
		if((isTodayWithinDates(today,eve.getStartDate(),eve.getEndDate())))
		{
			JPanel eventPanel = new JPanel(new MigLayout("insets 0, hmin 15, hmax 15,gapy 0"));
			if(isDateEqual(eve.getStartDate(),today)){
				JLabel jLab = new JLabel(eve.getName());
				jLab.setFont(new Font(jLab.getFont().getFontName(), jLab.getFont().getStyle(), 10));
				eventPanel.add(jLab, "gapy 0 0");
				eventPanel.validate();
				multiDayLabelList.add(jLab);
			}else{
			}
			Category c = eve.getCategory();
			if(c != null)
				eventPanel.setBackground(eve.getCategory().getColor());
			else
				eventPanel.setBackground(Color.orange);

			multiDayPanel.add(eventPanel);
		}
	}

	private static Calendar DateToCalendar(Date date){ 
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	private boolean isTodayWithinDates(Date currentDate, Date startDate, Date endDate)
	{
		Calendar today = DateToCalendar(currentDate);
		Calendar start = DateToCalendar(startDate);
		Calendar end = DateToCalendar(endDate);

		int todayYear =today.get(Calendar.YEAR);
		int startYear = start.get(Calendar.YEAR);
		int endYear = end.get(Calendar.YEAR);

		int todayMonth =today.get(Calendar.MONTH);
		int startMonth = start.get(Calendar.MONTH);
		int endMonth= end.get(Calendar.MONTH);

		int todayDay = today.get(Calendar.DAY_OF_MONTH);
		int startDay = start.get(Calendar.DAY_OF_MONTH);
		int endDay = end.get(Calendar.DAY_OF_MONTH);

		if((todayYear >= startYear && todayYear <=endYear) &&
				(todayMonth >= startMonth && todayMonth <= endMonth)&&
				(todayDay >= startDay && todayDay <= endDay)){
			return true;
		}else
			return false;
	}
	/**
	 * adds an additional Panel if there is a Multiday Event
	 */
	private boolean isMultiDayEvent(Event e)
	{	
		if(isDateEqual(e.getStartDate(),e.getEndDate()))
			return false;
		else
			return true;
	}

	private void configurePanel()
	{
		heightOfJPanel = this.getHeight();
		widthOfJPanel = this.getWidth();
		unaddedEvents = 0;
		if(events!=null)
			this.remove(events);
		events = new JPanel(new MigLayout("fill, h 5000,w "+widthOfJPanel+", insets 0"));
		if(multiDayPanel != null){
			if(multiDayPanel.size() > 0){
				multiDayEvents = new JPanel(new MigLayout("insets 0, gapy 0"));
				multiDayEvents=createMultiDayPanel();
				multiDayEvents.setBackground(background);
				events.add(multiDayEvents,"grow, gapy 0 0,h "+(multiDayPanel.size()*18)+", w "+widthOfJPanel+", wrap");
			}
		}
		if(singleDayPanel!=null){
			if(singleDayPanel.size() > 0){
				singleDayEvents = new JPanel(new MigLayout("insets 0, gapy 0"));
				singleDayEvents = createSingleDayPanel();
				singleDayEvents.setBackground(background);
				events.add(singleDayEvents,"grow,gapy 0 0, h 5000, w "+widthOfJPanel);
			}
		}
		events.setBackground(background);
		this.add(events,"gapy 0 0,grow, h 5000, w "+widthOfJPanel);
	}

	private void addEvents()
	{


	}

	public void clearEvents(){
		eventLabelList.removeAll(eventLabelList);
	}

	public void updatePanel(){
		clearEvents();
		List<Event> events = EventListModel.getEventListModel().getList();
		for(Event eve: events){
			Date evedate = eve.getStartDate();
			if(isDateEqual(paneldate,evedate))
				addEvent(eve);
		}
		configurePanel();
		
	}

	private boolean isDateEqual(Date d1, Date d2)
	{
		if (d1.getYear() == d2.getYear() && d1.getDate() == d2.getDate() && d1.getMonth() == d2.getMonth())
			return true;
		else
			return false;
	}


	private JPanel createSingleDayPanel() {
		JPanel last = new JPanel(new MigLayout("insets 0, hmin 14, hmax 14,center"));
		JLabel more = new JLabel("more");
		more.setFont(new Font(more.getFont().getFontName(), more.getFont().getStyle(), 10));
		for(JPanel jpanel: singleDayPanel){	
			if(heightOfJPanel >= 50){//change to preffered size latter
				heightOfJPanel = heightOfJPanel-15;
				singleDayEvents.add(jpanel,"w "+widthOfJPanel+",hmin 14, hmax 14, gapy 0 0, wrap, center");
			}else {
				unaddedEvents++;
			}
		}
		if(unaddedEvents > 0) {
			more.setText(unaddedEvents + " more..");
			last.add(more,"center");
			last.setBackground(background);
			singleDayEvents.add(last, "w "+widthOfJPanel+", hmin 12, hmax 12, gapy 0 0,center");
		}
		return singleDayEvents;
	}

	private JPanel createMultiDayPanel() {
		JPanel last = new JPanel(new MigLayout("insets 0, hmin 14, hmax 14,center"));
		JLabel more = new JLabel("more");
		more.setFont(new Font(more.getFont().getFontName(), more.getFont().getStyle(), 10));

		for(JPanel jpanel: multiDayPanel){	
			if(heightOfJPanel >= 50){//change to preffered size latter
				heightOfJPanel = heightOfJPanel-15;
				multiDayEvents.add(jpanel,"w "+widthOfJPanel+",hmin 14, hmax 14, gapy 0 0, wrap, center");
			}else {
				unaddedEvents++;
			}
		}
		if(unaddedEvents > 0) {
			more.setText(unaddedEvents + " more..");
			last.add(more,"center");
			last.setBackground(background);
			multiDayEvents.add(last, "w "+widthOfJPanel+", hmin 12, hmax 12, gapy 0 0,center");
		}
		return multiDayEvents;
	}


	public void setDay(int day) {
		this.day = day;
		date = new JPanel(new MigLayout("insets 0"));
		dateLabel = new JLabel(""+day);
		dateLabel.setForeground(text);
		date.add(dateLabel, "alignx right");
		date.setBackground(background);
		this.setBackground(background);
		this.add(date,"growx, aligny top, wrap");

		//		updatePanel();  Is this necessary
	}

	@Override
	public void repaint()
	{	
		super.repaint();
		configurePanel();
	}
	@Override
	public void intervalAdded(ListDataEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void intervalRemoved(ListDataEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contentsChanged(ListDataEvent e) {
		// TODO Auto-generated method stub

	}


}


