package edu.wpi.cs.wpisuitetng.modules.calendar.model;

import java.util.Date;

import org.junit.Test;
import static org.junit.Assert.*;

import edu.wpi.cs.wpisuitetng.modules.calendar.view.utilities.DateUtilities;

public class UtilitiesTest {
	
	@Test
	public void testTimeToStringWithNoon() {
		Date noon = new Date();
		noon.setHours(12);
		noon.setMinutes(0);
		String strNoonToString=DateUtilities.timeToString(noon);
		assertEquals("Noon", strNoonToString);
	}
	
	@Test
	public void testTimeToStringWithMidnight() {
		Date noon = new Date();
		noon.setHours(0);
		noon.setMinutes(0);
		String strNoonToString=DateUtilities.timeToString(noon);
		assertEquals("Midnight", strNoonToString);
	}
	
	@Test
	public void testAmTimeToStringWithOnTheHourAmTime() {
		Date noon = new Date();
		noon.setHours(3);
		noon.setMinutes(0);
		String strNoonToString=DateUtilities.timeToString(noon);
		assertEquals("3 AM", strNoonToString);
	}
	
	@Test
	public void testAmTimeToStringWithOnTheHourPmTime() {
		Date noon = new Date();
		noon.setHours(3+12);
		noon.setMinutes(0);
		String strNoonToString=DateUtilities.timeToString(noon);
		assertEquals("3 PM", strNoonToString);
	}
	
	@Test
	public void testTimeToStringWithAmTime() {
		Date noon = new Date();
		noon.setHours(3);
		noon.setMinutes(35);
		String strNoonToString=DateUtilities.timeToString(noon);
		assertEquals("3:35 AM", strNoonToString);
	}
	
	@Test
	public void testTimeToStringWithPmTime() {
		Date noon = new Date();
		noon.setHours(12+3);
		noon.setMinutes(35);
		String strNoonToString=DateUtilities.timeToString(noon);
		assertEquals("3:35 PM", strNoonToString);
	}
}
