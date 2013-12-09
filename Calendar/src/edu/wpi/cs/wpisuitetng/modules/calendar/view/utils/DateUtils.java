package edu.wpi.cs.wpisuitetng.modules.calendar.view.utils;

import java.util.Date;

public class DateUtils {

	/**
	 * Helper function to get a string representation of a time contained in a Date.
	 * Used for pretty printing times.
	 * @param date
	 * @return a string representation of the date in standard (12 hour) format
	 */
	public static String timeToString(Date date){
		int hours=date.getHours();
		int minutes=date.getMinutes();
		
		String strAmPm= " " + ((hours < 12) ? "AM" : "PM");
		
		String strHours;
		if (hours == 0) strHours="12";
		else strHours=Integer.toString(hours%12);
		
		if(minutes == 0) {
			if (hours == 12) return "Noon";
			else if (hours == 0 || hours == 24) return "Midnight";
			else return strHours + strAmPm;
		} else if(minutes < 10) {
			String strMinutes = Integer.toString(minutes);
			return strHours + ":" + "0" + strMinutes + strAmPm;
		} else {
			String strMinutes = Integer.toString(minutes);
			return strHours + ":" + strMinutes + strAmPm;
		}
	}
	
	public static String  hourString(int hour)
	{
		String output;
		
		if(hour < 12){
			output = ""+hour+ " AM";
		}else if (hour == 12){
			output = "NOON";
		}else{
			hour = hour- 12;
			output = ""+hour+ " PM";
		}
		return output;
	}
}
