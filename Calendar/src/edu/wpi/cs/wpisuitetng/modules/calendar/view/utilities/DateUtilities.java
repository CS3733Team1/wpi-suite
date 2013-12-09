package edu.wpi.cs.wpisuitetng.modules.calendar.view.utilities;

import java.text.DateFormat;
import java.util.Date;

public class DateUtilities {

	/**
	 * Helper function to get a string representation of a time contained in a Date.
	 * Used for pretty printing times.
	 * @param date
	 * @return a string representation of the date in standard (12 hour) format
	 */
	public static String timeToString(Date date){
		int hours=date.getHours();
		int minutes=date.getMinutes();
		
		return timeToString(hours, minutes);
	}
	
	public static String timeToString(int hours, int minutes){
		String strAmPm= " "+((hours < 12) ? "AM" : "PM");
		
		if (minutes==0){
			if (hours==12){
				return "Noon";
			}else if (hours==0 || hours ==24){
				return "Midnight";
			}
		}
		
		hours%=12;	//divide 24 hour day into two 12-hour cycles
		String strHours=Integer.toString(hours);
		if (hours<0){
			if (hours==0){
				strHours="12";	//standard time is illogical
			}else{
				strHours=" "+strHours;
			}
		}

//		if (minutes==0){
//			return strHours+" "+strAmPm;
//		}else{
			String strMinutes=Integer.toString(minutes);
			if (minutes<10){
				strMinutes="0"+strMinutes;
			}
			return strHours+":"+strMinutes+strAmPm;
//		}
	}
	
	public static Date stringToDate(String strDate){	
//		System.out.println("Parsing string time: "+strDate);

		strDate=strDate.trim();
		
		String[] strHalves = strDate.split(":");
		
		if (strHalves.length>=2){
			int hours = -1;
			int minutes = -1;
			boolean goodHour = true;
			boolean goodMinute = true;
	
			String strFirstNum = strHalves[0].trim();
	//		System.out.println("\tFirst Number string: "+ strFirstNum);
			try {
				hours = Integer.parseInt(strFirstNum);
			} catch (NumberFormatException e) {
	//			System.out.println("\tHours string could not be parsed to a number");
				return null;
			}
	//		System.out.println("\tHours parsed to "+Integer.toString(hours));
			
			
			String strSecondNum = strHalves[1].trim();
	//		System.out.println("\tSecond Number string: "+strSecondNum);
			try {
				String strMinutes = strSecondNum.substring(0, 2).trim();
	//			System.out.println("\tstrMinutes: ("+strMinutes+")");
				minutes = Integer.parseInt(strMinutes);
			} catch (NumberFormatException | StringIndexOutOfBoundsException e) {
	//			System.out.println("\t2-digit Minutes string could not be parsed to a number - trying only the first digit");
				
				//try it again with only the first digit
				try {
					String strMinutes = strSecondNum.substring(0, 1).trim();
	//				System.out.println("\tstrMinutes: ("+strMinutes+")");
					minutes = Integer.parseInt(strMinutes);
				} catch (NumberFormatException | StringIndexOutOfBoundsException e2) {
	//				System.out.println("\t1-digit Minutes string could not be parsed to a number");
					return null;
				}
			}
			
			if (hours >= 0 && hours <= 24 && minutes >= 0 && minutes < 60) {
	//			System.out.println("\tHours and Minutes valid");
				
				if (hours>12 || hours==0){
	//				System.out.println("\tMilirary time detected...");
				}else{
	//				System.out.println("\tStandard time detected...");
					
					//Check if it's PM
	//				System.out.println("\t\tchecking PM");
					String[] strPms={"PM", "Pm", "pm", "p.m.","P.M.", "P.m.","p.m", "P.M", "P.m"};
					boolean PM = false;
					for (int i=0;i<strPms.length;i++){
						if (strSecondNum.contains(strPms[i])){
							PM=true;
							break;
						}
					}
					
					//PM time Logic
					if (PM){
	//					System.out.println("\t\tPM detected");
						if (hours!=12){
	//						System.out.println("\t\t\tadding 12 to hours");
							hours += 12;
						}
					}else{
						if (hours==12){
	//						System.out.println("\t\tMidnight detected - setting hours to zero");
							hours=0;
						}
					}
				}
	
				//Reconstruct a date with the given time
				Date date = new Date();
				date.setHours(hours);
				date.setMinutes(minutes);
				return date;
			}
		}
//		System.out.println("\tMinutes or Hours were invalid");
		return null;
	}

}
