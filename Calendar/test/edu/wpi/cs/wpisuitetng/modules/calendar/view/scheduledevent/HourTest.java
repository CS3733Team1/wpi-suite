package edu.wpi.cs.wpisuitetng.modules.calendar.view.scheduledevent;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.ArrayList;

import org.junit.Test;

public class HourTest {

	@Test
	public void canCreatHour() {
		assertNotNull(new Hour());
	}
	
	@Test
	public void canSetState() {
		Hour testHour = new Hour();
		testHour.setState(true);
		assertTrue(testHour.getState());
		testHour.setState(false);
		assertFalse(testHour.getState());
	}

	@Test
	public void canSetHour() {
		Hour testHour = new Hour();
		testHour.setHour(12);
		assertEquals(12, testHour.getHour());
		testHour.setHour(5);
		assertEquals(5, testHour.getHour());
	}
	
	@Test
	public void canGetSwitchState() {
		Hour testHour = new Hour();
		testHour.setState(true);
		assertTrue(testHour.getState());
		assertFalse(testHour.switchState());
		testHour.setState(false);
		assertFalse(testHour.getState());
		assertTrue(testHour.switchState());
	}
	
	@Test
	public void canGetSelectedState() {
		Hour testHour = new Hour();
		testHour.setState(true);
		assertTrue(testHour.isSelected());
		testHour.setState(false);
		assertFalse(testHour.isSelected());
	}
	
	@Test
	public void getSelectedSameGetState() {
		Hour testHour = new Hour();
		testHour.setState(true);
		assertEquals(testHour.getState(), testHour.isSelected());
	}
	
	@Test
	public void canSetPoint() {
		Hour testHour = new Hour();
		testHour.setIndex(3, 5);
		assertEquals(new Point(3, 5), testHour.getLocation());
	}
	
	@Test
	public void canAddUser() {
		Hour testHour = new Hour();
		testHour.updateCount("Peter");
		testHour.updateCount("Shadi");
		testHour.updateCount("Chris");
		ArrayList<String> testList = new ArrayList<String>();
		testList.add("Peter");
		testList.add("Shadi");
		testList.add("Chris");
		assertEquals(testList, testHour.userList());
	}
	
	@Test
	public void doesNotDuplicate() {
		Hour testHour = new Hour();
		testHour.updateCount("Peter");
		testHour.updateCount("Shadi");
		testHour.updateCount("Peter");
		testHour.updateCount("Chris");
		ArrayList<String> testList = new ArrayList<String>();
		testList.add("Peter");
		testList.add("Shadi");
		testList.add("Chris");
		assertEquals(testList, testHour.userList());
	}
}
