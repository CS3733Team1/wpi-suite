package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;

public class EventHoverMouseListener implements MouseListener{

	Event e;
	JPanel epanel,calview;
	
	public EventHoverMouseListener(Event e, JPanel epanel, JPanel calview){
		this.e=e;
		this.epanel=epanel;
		this.calview=calview;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
		System.out.println("hovering over an event");
		this.epanel.setBackground(Color.RED);
		
		/*
		int epanelXPos,epanelWidth,epanelYPos,epanelHeight;
		epanelXPos=epanel.getX();
		epanelYPos=epanel.getY();
		epanelWidth=epanel.getWidth();
		epanelHeight=epanel.getHeight();
		
		JPanel infoPanel = new JPanel(new MigLayout());
		StringBuilder evebuilder = new StringBuilder();
		evebuilder.append("cell ");
		evebuilder.append(new Integer(epanelXPos+epanelWidth).toString());
		evebuilder.append(" ");
		evebuilder.append(new Integer(epanelYPos).toString());
		evebuilder.append(" ");
		evebuilder.append("0");
		evebuilder.append(" ");
		evebuilder.append(new Integer(epanelHeight).toString());
		evebuilder.append(",grow, push");
		
		JTextArea eventInfo = new JTextArea();
		eventInfo.append(this.e.getName());
		eventInfo.append(DateFormat.getInstance().format(this.e.getStartDate()));
		eventInfo.append(DateFormat.getInstance().format(this.e.getEndDate()));
		if(this.e.getCategory() != null){
			if(this.e.getCategory().getName() != null){
				eventInfo.append(this.e.getCategory().getName());
			}
		}
		if(this.e.getDescription() != null){
			eventInfo.append(this.e.getDescription());
		}
		infoPanel.add(eventInfo);
		
		System.out.println("built info panel");
		
		calview.add(infoPanel,evebuilder.toString());
		*/
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO remove the info panel
		this.epanel.setBackground(Color.GREEN);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
}
