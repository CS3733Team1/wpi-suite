package edu.wpi.cs.wpisuitetng.modules.calendar.view.utils;

import java.text.DateFormat;
import java.util.Date;

public class DateUtils {

	/**
	 * Helper function to get a string representation of a time contained in a Date.
	 * Used for pretty printing times.
	 * @param date
	 * @return a string representation of the date in standard (12 hour) format
	 */
	public static String timeToString(Date date){
		int hours = date.getHours();
		int minutes = date.getMinutes();

		return timeToString(hours, minutes);
	}

	public static String dateToSting(Date date){
		return DateFormat.getInstance().format(date);
	}
	
	public static String timeToString(int hours, int minutes){
		String strAmPm = " " + ((hours < 12) ? "AM" : "PM");

		if (minutes == 0) {
			if (hours == 12) {
				return "Noon";
			} else if(hours == 0 || hours == 24) {
				return "Midnight";
			}
		}

		hours %= 12;	//divide 24 hour day into two 12-hour cycles
		String strHours = Integer.toString(hours);
		if(hours < 10) {
			if(hours == 0) {
				strHours = "12";	//standard time is illogical
			} else {
				strHours = " " + strHours;//add a space so single-digit hours line up with the second digit of 2-digit hours
			}
		}

		//uncomment if you prefer the 3 PM instead of 3:00 PM
//		if (minutes==0){
//			return strHours+" "+strAmPm;
//		}else{
			String strMinutes=Integer.toString(minutes);
			if (minutes<10){
				strMinutes="0"+strMinutes;
			}
			return strHours + ":" + strMinutes+strAmPm;
		//}
	}

	public static String hourString(int hour) {
		String output = "";
		if(hour == 0) {
			output = "";
		} else if(hour < 12){
			output = "" + hour + " AM";
		} else if (hour == 12){
			output = "Noon";
		}else if(hour > 12)
		{
			output = "" + (hour-12) + " PM";
		}
		
		return output;
	}


	public static Date stringToDate(String strDate){	
		//		System.out.println("Parsing string time: "+strDate);

		strDate=strDate.trim();

		//check standard named-times
		if (strDate.equalsIgnoreCase("midnight")){
			return makeDateWithTime(0,0);
		}else if (strDate.equalsIgnoreCase("noon")){
			return makeDateWithTime(12,0);
		}else if (strDate.equalsIgnoreCase("noon-ish")){
			return makeDateWithTime(12,(int)Math.random()*15);
		}else{

			String[] strHalves = strDate.split(":");

			int hours = -1;
			if (strHalves.length>=2){
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
				minutes=tryToGetNumberFromFirstTwoDigitsOf(strSecondNum);
				if (minutes<0){
					return null;
				}

				if (hours >= 0 && hours <= 24 && minutes >= 0 && minutes < 60) {
					//			System.out.println("\tHours and Minutes valid");

					if (hours>12 || hours==0){
						//				System.out.println("\tMilirary time detected...");
					}else{
						//				System.out.println("\tStandard time detected...");

						//Check if it's PM
						//				System.out.println("\t\tchecking PM");
						boolean PM = findPM(strSecondNum);

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
					return makeDateWithTime(hours, minutes);

				}
			}else{//could not split around colon :, try to parse as things like 4PM
				hours=tryToGetNumberFromFirstTwoDigitsOf(strDate);
				if (hours<0){
					return null;
				}else{
					if (findPM(strDate)){
						hours+=12;
					}
					return makeDateWithTime(hours,0);
				}
			}
		}
		//		System.out.println("\tMinutes or Hours were invalid");
		return null;
	}

	private static Date makeDateWithTime(int hours, int minutes){
		Date date = new Date();
		date.setHours(hours);
		date.setMinutes(minutes);
		return date;
	}

	/**
	 * Helper
	 * attempts to find a number either as the first two chars or the first char
	 * for example, "12abcd" returns 12, "3abcd" returns 3, but "abcd" returns -1
	 * @param string to try to parse as a number
	 * @return the number if found, -1 otherwise
	 */
	private static int tryToGetNumberFromFirstTwoDigitsOf(String string){
		int num=-1;
		try {
			String strMinutes = string.substring(0, 2).trim();
			//			System.out.println("\tstrMinutes: ("+strMinutes+")");
			num = Integer.parseInt(strMinutes);
		} catch (NumberFormatException | StringIndexOutOfBoundsException e) {
			//			System.out.println("\t2-digit Minutes string could not be parsed to a number - trying only the first digit");

			//try it again with only the first digit
			try {
				String strMinutes = string.substring(0, 1).trim();
				//				System.out.println("\tstrMinutes: ("+strMinutes+")");
				num = Integer.parseInt(strMinutes);
			} catch (NumberFormatException | StringIndexOutOfBoundsException e2) {
				//				System.out.println("\t1-digit Minutes string could not be parsed to a number");
				return -1;
			}
		}
		return num;
	}

	/**
	 * Helper
	 * attempts for find some form of "PM" in the string.
	 * @param string the string to search
	 * @return true if some form is found, false otherwise
	 */
	private static boolean findPM(String string){
		String[] strPms={"PM", "Pm", "pm", "p.m.","P.M.", "P.m.","p.m", "P.M", "P.m"};
		for (int i=0;i<strPms.length;i++){
			if (string.contains(strPms[i])){
				return true;
			}
		}
		return false;
	}

}
