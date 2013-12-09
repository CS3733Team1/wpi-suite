package edu.wpi.cs.wpisuitetng.modules.calendar.model;

import java.util.Date;

import org.junit.Test;
import static org.junit.Assert.*;

import edu.wpi.cs.wpisuitetng.modules.calendar.view.utilities.DateUtilities;

public class UtilitiesTest {
	
	/*************************** TEST TIME (IN DATE) TO STRING *************************/
	
	@Test
	public void testTimeToStringWithNoon() {
		Date noon = new Date();
		noon.setHours(12);
		noon.setMinutes(0);
		String strNoonToString=DateUtilities.timeToString(noon).trim();
		assertEquals("Noon", strNoonToString);
	}
	
	@Test
	public void testTimeToStringWithMidnight() {
		Date noon = new Date();
		noon.setHours(0);
		noon.setMinutes(0);
		String strNoonToString=DateUtilities.timeToString(noon).trim();
		assertEquals("Midnight", strNoonToString);
	}
	
	@Test
	public void testAmTimeToStringWithOnTheHourAmTime() {
		Date noon = new Date();
		noon.setHours(3);
		noon.setMinutes(0);
		String strNoonToString=DateUtilities.timeToString(noon).trim();
		assertEquals("3:00 AM", strNoonToString);
	}
	
	@Test
	public void testAmTimeToStringWithOnTheHourPmTime() {
		Date time = new Date();
		time.setHours(3+12);
		time.setMinutes(0);
		String strNoonToString=DateUtilities.timeToString(time).trim();
//		System.out.println(strNoonToString);
		assertEquals("3:00 PM", strNoonToString);
	}
	
	@Test
	public void testTimeToStringWithAmTime() {
		Date noon = new Date();
		noon.setHours(3);
		noon.setMinutes(5);
		String strNoonToString=DateUtilities.timeToString(noon).trim();
		assertEquals("3:05 AM", strNoonToString);
	}
	
	@Test
	public void testTimeToStringWithPmTime() {
		Date noon = new Date();
		noon.setHours(12+3);
		noon.setMinutes(05);
		String strNoonToString=DateUtilities.timeToString(noon).trim();
		assertEquals("3:05 PM", strNoonToString);
	}
	
	
	
	
	
	/*************************** TEST STRING TO TIME *************************/
	@Test
	public void testStringToTimeAm() {
		Date threeFifeteenAm=new Date();
		threeFifeteenAm.setHours(3);
		threeFifeteenAm.setMinutes(15);
		System.out.println(threeFifeteenAm);
		String strThreeFifeteenAm = "3:15 AM";//DateUtilities.timeToString(threeFifeteenAm);//"3:15 PM";
		System.out.println(strThreeFifeteenAm);
		Date parsedDate=DateUtilities.stringToDate(strThreeFifeteenAm);
		System.out.println(parsedDate);
		assertEquals(threeFifeteenAm.getHours(), parsedDate.getHours());
		assertEquals(threeFifeteenAm.getMinutes(), parsedDate.getMinutes());
	}
	
	@Test
	public void testStringToTimePm() {
		Date threeFifeteenAm=new Date();
		threeFifeteenAm.setHours(3+12);
		threeFifeteenAm.setMinutes(15);
		System.out.println(threeFifeteenAm);
		String strThreeFifeteenAm = "3:15 PM";//DateUtilities.timeToString(threeFifeteenAm);//"3:15 PM";
		System.out.println(strThreeFifeteenAm);
		Date parsedDate=DateUtilities.stringToDate(strThreeFifeteenAm);
		System.out.println(parsedDate);
		assertEquals(threeFifeteenAm.getHours(), parsedDate.getHours());
		assertEquals(threeFifeteenAm.getMinutes(), parsedDate.getMinutes());
	}
	
	@Test
	public void testStringToTimeWithMinutesLessThanTen() {
		Date threeFifeteenAm=new Date();
		threeFifeteenAm.setHours(3+12);
		threeFifeteenAm.setMinutes(5);
		System.out.println(threeFifeteenAm);
		String strThreeFifeteenAm = "3:05 PM";//DateUtilities.timeToString(threeFifeteenAm);//"3:15 PM";
		System.out.println(strThreeFifeteenAm);
		Date parsedDate=DateUtilities.stringToDate(strThreeFifeteenAm);
		System.out.println(parsedDate);
		assertEquals(threeFifeteenAm.getHours(), parsedDate.getHours());
		assertEquals(threeFifeteenAm.getMinutes(), parsedDate.getMinutes());
	}
	
	
	@Test
	public void testStringToTimeWithMinutesLessThanTenMalformed() {
		Date threeFifeteenAm=new Date();
		threeFifeteenAm.setHours(3+12);
		threeFifeteenAm.setMinutes(5);
		System.out.println(threeFifeteenAm);
		String strThreeFifeteenAm = "3:5 PM";//DateUtilities.timeToString(threeFifeteenAm);//"3:15 PM";
		System.out.println(strThreeFifeteenAm);
		Date parsedDate=DateUtilities.stringToDate(strThreeFifeteenAm);
		System.out.println(parsedDate);
		assertEquals(threeFifeteenAm.getHours(), parsedDate.getHours());
		assertEquals(threeFifeteenAm.getMinutes(), parsedDate.getMinutes());
	}
	
	@Test
	public void testStringToTimeWithNoonTime() {
		Date threeFifeteenAm=new Date();
		threeFifeteenAm.setHours(12);
		threeFifeteenAm.setMinutes(15);
		System.out.println(threeFifeteenAm);
		String strThreeFifeteenAm = "12:15 PM";//DateUtilities.timeToString(threeFifeteenAm);//"3:15 PM";
		System.out.println(strThreeFifeteenAm);
		Date parsedDate=DateUtilities.stringToDate(strThreeFifeteenAm);
		System.out.println(parsedDate);
		assertEquals(threeFifeteenAm.getHours(), parsedDate.getHours());
		assertEquals(threeFifeteenAm.getMinutes(), parsedDate.getMinutes());
	}
	
	@Test
	public void testStringToTimeWithMidnightTime() {
		Date threeFifeteenAm=new Date();
		threeFifeteenAm.setHours(0);
		threeFifeteenAm.setMinutes(15);
		System.out.println(threeFifeteenAm);
		String strThreeFifeteenAm = "12:15 AM";//DateUtilities.timeToString(threeFifeteenAm);//"3:15 PM";
		System.out.println(strThreeFifeteenAm);
		Date parsedDate=DateUtilities.stringToDate(strThreeFifeteenAm);
		System.out.println(parsedDate);
		assertEquals(threeFifeteenAm.getHours(), parsedDate.getHours());
		assertEquals(threeFifeteenAm.getMinutes(), parsedDate.getMinutes());
	}
	
	@Test
	public void testStringToTimeWithMilitaryTime() {
		Date threeFifeteenAm=new Date();
		threeFifeteenAm.setHours(14);
		threeFifeteenAm.setMinutes(15);
		System.out.println(threeFifeteenAm);
		String strThreeFifeteenAm = "14:15";//DateUtilities.timeToString(threeFifeteenAm);//"3:15 PM";
		System.out.println(strThreeFifeteenAm);
		Date parsedDate=DateUtilities.stringToDate(strThreeFifeteenAm);
		System.out.println(parsedDate);
		assertEquals(threeFifeteenAm.getHours(), parsedDate.getHours());
		assertEquals(threeFifeteenAm.getMinutes(), parsedDate.getMinutes());
	}
}
