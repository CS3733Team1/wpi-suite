/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.view.utilities;

import java.awt.Color;

public class CalendarUtils {
	public static final String[] weekNamesAbbr = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
	public static final String[] weekNames = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
	public static final String[] monthNamesAbbr = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
	public static final String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

	public static final Color selectionColor = new Color(204, 232, 252);
	public static final Color titleNameColor = new Color(50, 50, 50);
	public static final Color thatBlue = new Color(0, 139, 239);
	public static final Color todayYearColor = new Color(255, 244, 103);
	public static final Color weekendColor = new Color(245, 245, 245);
	public static final Color timeColor = new Color(181,181,181);
	
	public static Color blend(Color clOne, Color clTwo, float fAmount) {
	    float fInverse = (float) (1.0 - fAmount);

	    // I had to look up getting color components in java.  Google is good :)
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
	
	public static Color textColor(Color backgroundColor) {
		if(backgroundColor.equals(Color.white))
			return Color.black;
		int colorBrightness = backgroundColor.getRed() + backgroundColor.getGreen() + backgroundColor.getBlue();
		if (colorBrightness < 383 ) 
			return blend(backgroundColor, Color.white, (float)0.25);
		
		return blend(backgroundColor, Color.black, (float)0.25);
	}
}
