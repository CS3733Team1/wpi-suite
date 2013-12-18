package edu.wpi.cs.wpisuitetng.modules.calendar.view.scheduledevent;

import static org.junit.Assert.*;

import org.junit.Test;

public class DayOfWeekTest {

	@Test
	public void canCreateDayOfWeek() {
		assertNotNull(new DayOfWeek());
	}
	
	@Test
	public void canSetDay() {
		DayOfWeek testDay = new DayOfWeek();
		testDay.setDay(5);
		assertEquals(5, testDay.getDay());
	}
	
	@Test
	public void canUpdateDay() {
		DayOfWeek testDay = new DayOfWeek();
		testDay.setDay(5);
		assertEquals(5, testDay.getDay());
		testDay.setDay(2);
		assertEquals(2, testDay.getDay());
	}
	
	@Test
	public void canSetWeekend() {
		DayOfWeek testDay = new DayOfWeek();
		testDay.setDay(0);
		assertEquals(0, testDay.getDay());
		testDay.setDay(6);
		assertEquals(6, testDay.getDay());
	}

}
