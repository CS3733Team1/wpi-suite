package edu.wpi.cs.wpisuitetng.modules.calendar.view;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.category.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.commitment.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.commitment.CommitmentListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.event.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.event.EventListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day.DayArea;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day.DayCalendar;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day.ScheduleItem;

public class DayViewTest {
	DayCalendar day;
	Category cat;
	
	@Before
	public void setUp() {
		System.gc();
		EventListModel emodel = EventListModel.getEventListModel();
		CommitmentListModel cmodel = CommitmentListModel.getCommitmentListModel();
		emodel.emptyModel();
		cmodel.emptyModel();
		day = new DayCalendar();
		cat = new Category("This is not Being Test", Color.BLUE);
	}
	
	@Test
	/**
	 * Test to ensure can create valid dayview
	 */
	public void testDayCalendar(){
		DayCalendar test = new DayCalendar();
		assertNotNull(test);
	}
	
	@Test
	/**
	 * Test to ensure DayArea is not null
	 */
	public void testDayArea(){
		DayArea test = new DayArea();
		assertNotNull(test);
	}
	
	@Test
	/**
	 * Test Adding a Commitment To DayArea
	 */
	public void testAddCommitmentToDayArea(){
		DayArea test = new DayArea();
		EventListModel emodel = EventListModel.getEventListModel();
		CommitmentListModel cmodel = CommitmentListModel.getCommitmentListModel();
		emodel.emptyModel();
		cmodel.emptyModel();
		
		
		Commitment first = new Commitment("first", new Date(), true, cat);
		cmodel.addCommitment(first);
		assertEquals(1, test.getComponents().length);
		assertTrue(test.getComponent(0) instanceof ScheduleItem);
		ScheduleItem item = (ScheduleItem) test.getComponent(0);
		assertTrue(item.getDisplayItem() instanceof Commitment);
		assertEquals("first", item.getDisplayItem().getName());
	}
	
	@Test
	/**
	 * Test Adding a Event To DayArea
	 */
	public void testAddEventToDayArea(){
		DayArea test = new DayArea();
		EventListModel emodel = EventListModel.getEventListModel();
		CommitmentListModel cmodel = CommitmentListModel.getCommitmentListModel();
		emodel.emptyModel();
		cmodel.emptyModel();
		
		Event first = new Event("first", new Date(), new Date(), true, cat);
		emodel.addEvent(first);
		assertEquals(1, test.getComponents().length);
		assertTrue(test.getComponent(0) instanceof ScheduleItem);
		ScheduleItem item = (ScheduleItem) test.getComponent(0);
		assertTrue(item.getDisplayItem() instanceof Event);
		assertEquals("first", item.getDisplayItem().getName());
	}
}
