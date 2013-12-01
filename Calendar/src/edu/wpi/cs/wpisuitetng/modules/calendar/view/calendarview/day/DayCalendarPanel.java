package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.ICalendarView;

public class DayCalendarPanel extends JPanel implements ICalendarView{
	
	private DayCalendarScrollPane dayscroll;
	private DayCalendarLayerPane daylayer;
	

	public DayCalendarPanel(){
		this.setBorder(BorderFactory.createTitledBorder(null,
	            "Day View", TitledBorder.LEFT, TitledBorder.TOP,
	            new Font("null", Font.BOLD, 12), Color.BLUE));
		
		
		this.setLayout(new MigLayout("fill", 
				"[100%]", 
				"[100%]"));
		

		
		daylayer = new DayCalendarLayerPane();
		
		dayscroll = new DayCalendarScrollPane(daylayer);
		
		StringBuilder scrollbuilder = new StringBuilder();
		scrollbuilder.append("cell ");
		scrollbuilder.append("0");
		scrollbuilder.append(" ");
		scrollbuilder.append("0");
	
	
		this.add(dayscroll, scrollbuilder.toString());
		
	}
	
	
	public void paint(Graphics g){
		if (daylayer != null){
			daylayer.reSize(this.getWidth() - (dayscroll.getVerticalScrollBar().getWidth()*2));
		}
		
		super.paint(g);
	}
	
	public void repaint(){
		if (daylayer != null){
			daylayer.reSize(this.getWidth() - (dayscroll.getVerticalScrollBar().getWidth()*2));
		}
		
		super.repaint();
	}
	
	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return daylayer.getTitle();
	}

	@Override
	public void next() {
		// TODO Auto-generated method stub
		daylayer.next();
		dayscroll.updateDay();

		
	}

	@Override
	public void previous() {
		// TODO Auto-generated method stub
		
		daylayer.previous();
		dayscroll.updateDay();
		
	}

	@Override
	public void today() {
		// TODO Auto-generated method stub
		daylayer.today();
		dayscroll.updateDay();
		
	}

}
