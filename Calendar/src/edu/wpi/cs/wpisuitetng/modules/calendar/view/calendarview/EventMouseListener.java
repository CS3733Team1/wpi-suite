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
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
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
		int ctrldwn = MouseEvent.CTRL_DOWN_MASK;
		boolean multiSelect = ((e.getModifiersEx() & ctrldwn) == ctrldwn);
		if(multiSelect){
			System.out.println("ctrl!");
			if(selectedEvents.contains(this.e)){
				selectedEvents.remove(this.e);
				selectedPanels.remove(this.epanel);
				deselectPanel(this.epanel);
			}
			else{
				selectedEvents.add(this.e);
				selectedPanels.add(this.epanel);
				selectPanel(this.epanel);
				
			}
		}
		else{
			System.out.println("no ctrl!");
			if(selectedEvents.contains(this.e)){
				boolean multiple = (selectedEvents.size()!=1);
				selectedEvents.clear();
				for(JPanel panel : selectedPanels){
					deselectPanel(panel);
				}
				selectedPanels.clear();
				if(multiple){
					selectedEvents.add(this.e);
					selectedPanels.add(this.epanel);
					selectPanel(this.epanel);
				}
			}
			else{
				selectedEvents.clear();
				for(JPanel panel : selectedPanels){
					deselectPanel(panel);
				}
				selectedPanels.clear();
				selectedEvents.add(this.e);
				selectedPanels.add(this.epanel);
				selectPanel(this.epanel);
				
			}
		}
//		invertBGColor(this.epanel);
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
	
	private void selectPanel(JPanel panel){
		Color panelColor = panel.getBackground();
		panel.setBorder(new LineBorder(panelColor,3));
		panel.setBackground(Color.getHSBColor(0,0,(float).6));
	}
	
	private void deselectPanel(JPanel panel){
		LineBorder panelBorder = (LineBorder)panel.getBorder();
		Color borderColor = panelBorder.getLineColor();
		panel.setBackground(borderColor);
		panel.setBorder(new EmptyBorder(0,0,0,0));
	}
}
