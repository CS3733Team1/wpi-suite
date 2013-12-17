package edu.wpi.cs.wpisuitetng.modules.calendar.view.day;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.CommitmentListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.EventListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.FilteredCommitmentsListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.FilteredEventsListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day.DayArea;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day.DayCalendar;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day.SchedulableMover;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day.ScheduleItem;

public class DayViewTest {
	DayCalendar day;
	Category cat;
	
	@After
	public void testTearDown(){
		System.gc();
		EventListModel emodel = EventListModel.getEventListModel();
		CommitmentListModel cmodel = CommitmentListModel.getCommitmentListModel();
		emodel.emptyModel();
		cmodel.emptyModel();
		
		FilteredEventsListModel femodel = FilteredEventsListModel.getFilteredEventsListModel();
		FilteredCommitmentsListModel fcmodel = FilteredCommitmentsListModel.getFilteredCommitmentsListModel();
		
		for (int x = 0; x < femodel.getListDataListeners().length; x++){
			femodel.removeListDataListener(femodel.getListDataListeners()[x]);
		}
		for (int x = 0; x < fcmodel.getListDataListeners().length; x++){
			fcmodel.removeListDataListener(fcmodel.getListDataListeners()[x]);
		}
	}
	
	@Before
	public void setUp() {
		System.gc();
		EventListModel emodel = EventListModel.getEventListModel();
		CommitmentListModel cmodel = CommitmentListModel.getCommitmentListModel();
		emodel.emptyModel();
		cmodel.emptyModel();
		
		FilteredEventsListModel femodel = FilteredEventsListModel.getFilteredEventsListModel();
		FilteredCommitmentsListModel fcmodel = FilteredCommitmentsListModel.getFilteredCommitmentsListModel();
		
		for (int x = 0; x < femodel.getListDataListeners().length; x++){
			femodel.removeListDataListener(femodel.getListDataListeners()[x]);
		}
		for (int x = 0; x < fcmodel.getListDataListeners().length; x++){
			fcmodel.removeListDataListener(fcmodel.getListDataListeners()[x]);
		}
		
		
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
	
	@Test
	/**
	 * Test Adding Multiple Objects to DayArea
	 */
	public void testAddMultipleItemsToDayArea(){
		DayArea test = new DayArea();
		EventListModel emodel = EventListModel.getEventListModel();
		CommitmentListModel cmodel = CommitmentListModel.getCommitmentListModel();
		emodel.emptyModel();
		cmodel.emptyModel();
		
		Event first = new Event("first", new Date(), new Date(), true, cat);
		emodel.addEvent(first);
		Commitment second = new Commitment("second", new Date(), true, cat);
		cmodel.addCommitment(second);
		Event third = new Event("third", new Date(), new Date(), true, cat);
		emodel.addEvent(third);
		
		assertEquals(3, test.getComponents().length);
		
		for (int x =0; x < test.getComponents().length; x++){
			assertTrue(test.getComponent(x) instanceof ScheduleItem);
		}
	}
	
	@Test
	/**
	 * Test Adding Event outside of CurrentDay
	 */
	public void testAddEventOutsideOfDay(){
		DayArea test = new DayArea();
		EventListModel emodel = EventListModel.getEventListModel();
		CommitmentListModel cmodel = CommitmentListModel.getCommitmentListModel();
		emodel.emptyModel();
		cmodel.emptyModel();
		
		Date start = new Date();
		Date end = new Date();
		//Move Dates Ahead By 1, so they should not appear on view
		start.setDate(start.getDate()+1);
		end.setDate(end.getDate()+1);
		
		Event first = new Event("first", start, end, true, cat);
		emodel.addEvent(first);
		assertEquals(0, test.getComponents().length);
	}
	
	@Test
	/**
	 * Test Adding Event outside of CurrentDay then Move Forward to Date
	 */
	public void testAddEventOutsideThenMoveForward(){
		DayArea test = new DayArea();
		EventListModel emodel = EventListModel.getEventListModel();
		CommitmentListModel cmodel = CommitmentListModel.getCommitmentListModel();
		emodel.emptyModel();
		cmodel.emptyModel();
		
		Date start = new Date();
		Date end = new Date();
		//Move Dates Ahead By 1, so they should not appear on view
		start.setDate(start.getDate()+1);
		end.setDate(end.getDate()+1);
		
		Event first = new Event("first", start, end, true, cat);
		emodel.addEvent(first);
		assertEquals(0, test.getComponents().length);
		
		test.next();
		assertEquals(1, test.getComponents().length);
		assertTrue(test.getComponent(0) instanceof ScheduleItem);
		ScheduleItem item = (ScheduleItem) test.getComponent(0);
		assertTrue(item.getDisplayItem() instanceof Event);
		assertEquals("first", item.getDisplayItem().getName());
	}
	
	@Test
	/**
	 * Test Adding Event outside of CurrentDay then Move Back to Date
	 */
	public void testAddEventOutsideThenMoveBackward(){
		DayArea test = new DayArea();
		EventListModel emodel = EventListModel.getEventListModel();
		CommitmentListModel cmodel = CommitmentListModel.getCommitmentListModel();
		emodel.emptyModel();
		cmodel.emptyModel();
		
		Date start = new Date();
		Date end = new Date();
		//Move Dates Ahead By 1, so they should not appear on view
		start.setDate(start.getDate()-1);
		end.setDate(end.getDate()-1);
		
		Event first = new Event("first", start, end, true, cat);
		emodel.addEvent(first);
		assertEquals(0, test.getComponents().length);
		
		test.previous();
		assertEquals(1, test.getComponents().length);
		assertTrue(test.getComponent(0) instanceof ScheduleItem);
		ScheduleItem item = (ScheduleItem) test.getComponent(0);
		assertTrue(item.getDisplayItem() instanceof Event);
		assertEquals("first", item.getDisplayItem().getName());
	}
	
	@Test
	/**
	 * Test Day Creates Day at Now
	 */
	public void testDayCreatesAtNow(){
		DayArea test = new DayArea();
		EventListModel emodel = EventListModel.getEventListModel();
		CommitmentListModel cmodel = CommitmentListModel.getCommitmentListModel();
		emodel.emptyModel();
		cmodel.emptyModel();
		
		Date now = new Date();
		//Fix now to only care about year, month, date
		now = new Date(now.getYear(), now.getMonth(), now.getDate());
		
		assertEquals(now, test.getDayViewDate());
	}
	
	@Test
	/**
	 * Test Day Creates Day at Specified Date
	 */
	public void testDayCreatesAtSpecifiedDate(){
		Date now = new Date();
		//Fix now to only care about year, month, date
		now = new Date(now.getYear(), now.getMonth(), now.getDate()+5);
		
		DayArea test = new DayArea(now);
		EventListModel emodel = EventListModel.getEventListModel();
		CommitmentListModel cmodel = CommitmentListModel.getCommitmentListModel();
		emodel.emptyModel();
		cmodel.emptyModel();
		
		assertEquals(now, test.getDayViewDate());
	}
	
	@Test
	/**
	 * Test Day Moves to Now
	 */
	public void testDayMovesToNOW(){
		Date random = new Date();
		//Fix now to only care about year, month, date
		random = new Date(random.getYear(), random.getMonth(), random.getDate()+5);
		
		DayArea test = new DayArea(random);
		EventListModel emodel = EventListModel.getEventListModel();
		CommitmentListModel cmodel = CommitmentListModel.getCommitmentListModel();
		emodel.emptyModel();
		cmodel.emptyModel();
		
		assertEquals(random, test.getDayViewDate());
		
		Date now = new Date();
		//Fix now to only care about year, month, date
		now = new Date(now.getYear(), now.getMonth(), now.getDate());
		
		test.today();
		
		assertEquals(now, test.getDayViewDate());
	}
	
	@Test
	/**
	 * Test Day Views Specific Date
	 */
	public void testDayViewsSpecificDate(){
		Date random = new Date();
		//Fix now to only care about year, month, date
		random = new Date(random.getYear(), random.getMonth(), random.getDate()+5);
		
		DayArea test = new DayArea(random);
		EventListModel emodel = EventListModel.getEventListModel();
		CommitmentListModel cmodel = CommitmentListModel.getCommitmentListModel();
		emodel.emptyModel();
		cmodel.emptyModel();
		
		assertEquals(random, test.getDayViewDate());
		
		Date now = new Date();
		//Fix now to only care about year, month, date
		now = new Date(now.getYear(), now.getMonth(), now.getDate()+5);
		
		test.viewDate(now);
		
		assertEquals(now, test.getDayViewDate());
	}
	
	@Test
	/**
	 * Test Moving Specific Items
	 */
	public void testMovingItems(){
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
		
		SchedulableMover move = new SchedulableMover();
		move.registerComponent(item);
		
		MouseEvent one = new MouseEvent(item, MouseEvent.MOUSE_PRESSED, 0, 0, 15, 20, 1, true);
		move.mouseClicked(one);
		
		MouseEvent three = new MouseEvent(item, MouseEvent.MOUSE_RELEASED, 0, 0, 50, 50, 1, true);
		move.mouseReleased(three);
		
	}
	
	@Test
	/**
	 * Test DayCalendar gets created with today's date
	 */
	public void testMoveDayCalendarCreationNow(){
		DayCalendar test = new DayCalendar();
		
		Date now = new Date();
		now = new Date(now.getYear(), now.getMonth(), now.getDate());
		
		assertEquals(now, test.getDate());
	}
	
	@Test
	/**
	 * Test DayCalendar can move ahead one day
	 */
	public void testMoveDayCalendarAheadbyOne(){
		DayCalendar test = new DayCalendar();
		
		Date now = new Date();
		now = new Date(now.getYear(), now.getMonth(), now.getDate());
		
		test.next();
		
		assertFalse(now.equals(test.getDate()));
		now.setDate(now.getDate()+1);
		assertEquals(now, test.getDate());
	}
	
	@Test
	/**
	 * Test DayCalendar can move back one day
	 */
	public void testMoveDayCalendarBackbyOne(){
		DayCalendar test = new DayCalendar();
		
		Date now = new Date();
		now = new Date(now.getYear(), now.getMonth(), now.getDate());
		
		test.previous();
		
		assertFalse(now.equals(test.getDate()));
		now.setDate(now.getDate()-1);
		assertEquals(now, test.getDate());
	}
	
	@Test
	/**
	 * Test DayCalendar can move back to Today
	 */
	public void testMoveDayCalendartoToday(){
		DayCalendar test = new DayCalendar();
		
		Date now = new Date();
		now = new Date(now.getYear(), now.getMonth(), now.getDate());
		
		test.previous();
		test.previous();
		assertFalse(now.equals(test.getDate()));
		
		test.today();
		assertEquals(now, test.getDate());
	}
	
	@Test
	/**
	 * Test DayCalendar can move back to Today
	 */
	public void testMoveDayCalendarMovetoSelectDate(){
		DayCalendar test = new DayCalendar();
		
		Date now = new Date();
		now = new Date(now.getYear(), now.getMonth(), now.getDate()+10);
		
		assertFalse(now.equals(test.getDate()));
		
		//test.viewDate(new Calendar()); 
		//assertEquals(now, test.getDate());
	}
	
	
	
	
	
	
	
	
}
