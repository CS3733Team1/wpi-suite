package edu.wpi.cs.wpisuietng.modules.calendar;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.calendar.Calendar;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.commitment.CommitmentTabPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.event.EventTabPanel;

public class CalendarTest {

	@Test
	public void testCalendarInstance(){
		Calendar testcal = new Calendar();
		assertNotNull(testcal);
	}
	
	@Test
	public void testAddEventInstance(){
		EventTabPanel testaddevent = new EventTabPanel();
		assertNotNull(testaddevent);
	}
	
	@Test
	public void testAddCommitmentInstance(){
		CommitmentTabPanel testaddcommitment = new CommitmentTabPanel();
		assertNotNull(testaddcommitment);
	}
}
