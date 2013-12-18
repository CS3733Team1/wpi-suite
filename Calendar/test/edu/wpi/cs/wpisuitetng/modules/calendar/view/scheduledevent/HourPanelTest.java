package edu.wpi.cs.wpisuitetng.modules.calendar.view.scheduledevent;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class HourPanelTest {

	@Test
	public void canCreateHourPanel() {
		assertNotNull(new HourPanel());
	}
	
	@Test
	public void canSetState() {
		HourPanel testPanel = new HourPanel();
		testPanel.setState(true);
		assertTrue(testPanel.getState());
		testPanel.setState(false);
		assertFalse(testPanel.getState());
	}

}
