package edu.wpi.cs.wpisuitetng.modules.calendar.view.scheduledevent;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.List;
import java.util.ArrayList;

import org.junit.Test;

public class LeaderTest {

	@Test
	public void canMakeLeader() {
		assertNotNull(new Leader());
	}
	
	@Test
	public void canAddHour() {
		Leader testLeader = new Leader();
		testLeader.setTimeFrame(5, 5, 10);
		assertNotNull(testLeader.getList());
		assertNotNull(testLeader.getListPanel());
	}
	
	@Test
	public void canSetColor() {
		Leader testLeader = new Leader();
		testLeader.setTimeFrame(5, 5, 10);
		testLeader.setColor(new Color(0));
	}
	
	@Test
	public void canResize() {
		Leader testLeader = new Leader();
		testLeader.setTimeFrame(5, 10, 24);
		testLeader.reSize(10);
	}

}
