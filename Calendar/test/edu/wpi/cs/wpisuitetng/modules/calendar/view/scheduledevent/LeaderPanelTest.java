package edu.wpi.cs.wpisuitetng.modules.calendar.view.scheduledevent;

import static org.junit.Assert.*;

import org.junit.Test;

public class LeaderPanelTest {

	@Test
	public void canCreateLeaderPanel() {
		assertNotNull(new LeaderPanel());
	}
	
	@Test
	public void canAddUser() {
		LeaderPanel testPanel = new LeaderPanel();
		testPanel.addUser("Peter");
		assertEquals("Peter", testPanel.getUser());
	}
	
	@Test
	public void canUpdateUser() {
		LeaderPanel testPanel = new LeaderPanel();
		testPanel.addUser("Peter");
		assertEquals("Peter", testPanel.getUser());
		testPanel.addUser("Shadi");
		assertEquals("Shadi", testPanel.getUser());
	}

	@Test
	public void canSetTimeFrame() {
		LeaderPanel testPanel = new LeaderPanel();
		testPanel.setTimeFrame(5, 5, 10);
	}
	
	@Test
	public void canReSize() {
		LeaderPanel testPanel = new LeaderPanel();
		testPanel.setTimeFrame(5, 5, 10);
		testPanel.reSize(5);
	}
}
