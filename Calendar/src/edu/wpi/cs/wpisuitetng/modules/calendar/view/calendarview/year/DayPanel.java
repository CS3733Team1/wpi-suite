package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.year;

import java.awt.Color;

import javax.swing.JPanel;

public class DayPanel extends JPanel {

	private int numEvents = 0;
	
	
	
	
	
	
	
	public void setIsCurrentMonth(boolean isCurrentMonth) {
		
	}

	public void setIsWeekend(boolean isWeekend) {
		
	}

	public void setDate(int i) {
		
	}

	public void setIsToday(boolean isToday) {
		
	}

	public void addEvComm() {
		numEvents++;
		
	}
	public Color blend(Color clOne, Color clTwo, float fAmount) {
	    float fInverse = (float) (1.0 - fAmount);

	    // I had to look up getting colour components in java.  Google is good :)
	    float afOne[] = new float[3];
	    clOne.getColorComponents(afOne);
	    float afTwo[] = new float[3]; 
	    clTwo.getColorComponents(afTwo);    

	    float afResult[] = new float[3];
	    afResult[0] = afOne[0] * fAmount + afTwo[0] * fInverse;
	    afResult[1] = afOne[1] * fAmount + afTwo[1] * fInverse;
	    afResult[2] = afOne[2] * fAmount + afTwo[2] * fInverse;

	    return new Color (afResult[0], afResult[1], afResult[2]);
	}
	
	public Color textColor(Color backgroundColor)
	{
		if(backgroundColor.equals(Color.white))
			return Color.black;
		int colorBrightness = backgroundColor.getRed() + backgroundColor.getGreen() + backgroundColor.getBlue();
		System.out.println(colorBrightness);
		if (colorBrightness < 383 ) 
			return blend(backgroundColor, Color.white, (float)0.25);
		
		return blend(backgroundColor, Color.black, (float)0.25);
	}
	
}
