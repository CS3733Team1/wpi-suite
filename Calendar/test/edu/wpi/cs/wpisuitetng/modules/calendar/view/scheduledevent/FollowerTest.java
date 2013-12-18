package edu.wpi.cs.wpisuitetng.modules.calendar.view.scheduledevent;

import static org.junit.Assert.*;

import org.junit.Test;

public class FollowerTest {

	@Test
	public void canMakeFollower() {
		assertNotNull(new Follower());
	}
	
	@Test
	public void canAddHours() {
		Follower testFollower = new Follower();
		testFollower.setTimeFrame(5, 5, 10);
		assertNotNull(testFollower.updateHourList());
	}

}
