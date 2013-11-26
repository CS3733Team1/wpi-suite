package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;

public class EventMouseListener implements MouseListener{

	Event e;
	JPanel epanel,calview;
	
	static ArrayList<Event> selectedEvents= new ArrayList<Event>();
	static ArrayList<JPanel> selectedPanels= new ArrayList<JPanel>();
	
	public EventMouseListener(Event e, JPanel epanel, JPanel calview){
		this.e=e;
		this.epanel=epanel;
		this.calview=calview;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (this.e.getCategory() != null){
			UIManager.put("ToolTip.background",new ColorUIResource(this.e.getCategory().getColor()));
			float[] hsb=new float[3];
			Color catColor=this.e.getCategory().getColor();
			hsb=Color.RGBtoHSB(catColor.getRed(), catColor.getGreen(), catColor.getBlue(), hsb);
			if(hsb[2]<0.5){
				UIManager.put("ToolTip.foreground", new ColorUIResource(Color.WHITE));
			}
			else{
				UIManager.put("ToolTip.foreground", new ColorUIResource(Color.BLACK));
			}
		}
		else{
			UIManager.put("ToolTip.background", new ColorUIResource(Color.CYAN));
			UIManager.put("ToolTip.foreground",new ColorUIResource(Color.BLACK));
		}
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(selectedEvents.contains(this.e)){
			selectedEvents.remove(this.e);
			selectedPanels.remove(this.epanel);
		}
		else{
			selectedEvents.add(this.e);
			selectedPanels.add(this.epanel);
			
		}
		invertBGColor(this.epanel);
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
	
	public static ArrayList<Event> getSelected(){
		return selectedEvents;
	}
	
	private void invertBGColor(JPanel panel){
		int newRGB;
		float[] hsb = new float[3];
		Color panelColor=panel.getBackground();
		hsb=Color.RGBtoHSB(panelColor.getRed(), panelColor.getGreen(), panelColor.getBlue(), hsb);
		newRGB=Color.HSBtoRGB(1-hsb[0], hsb[1], hsb[2]);
		Color newPanelColor = new Color(newRGB);
		panel.setBackground(newPanelColor);
	}
}
