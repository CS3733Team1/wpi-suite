package edu.wpi.cs.wpisuitetng.modules.calendar.view.week;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.category.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.commitment.CommitmentListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.event.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.event.EventListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.filter.FilteredCommitmentsListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.filter.FilteredEventsListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day.DayCalendar;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.week.MultidayEventWeekView;

public class WeekMultiEventView {
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
	 * Test Constructing a multidayview with an empty list
	 */
	public void testMultidayConstructorEmptyList(){
		List<List<Event>> weeklist = new LinkedList<List<Event>>();
		for (int x = 0; x < 7; x++){
			weeklist.add(new LinkedList<Event>());
		}
		MultidayEventWeekView view = new MultidayEventWeekView(weeklist, new Date());
		assertNotNull(view);
		assertEquals(0, view.getComponentCount());
	}
	
	@Test
	/**
	 * Test Passing List With Events
	 */
	public void testMultiConstructorFilledList(){
		List<List<Event>> weeklist = new LinkedList<List<Event>>();
		for (int x = 0; x < 7; x++){
			List<Event> testlist = new LinkedList<Event>();
			testlist.add(new Event("first", new Date(), new Date(), true, cat));
			testlist.add(new Event("second", new Date(), new Date(), true, cat));
			testlist.add(new Event("third", new Date(), new Date(), true, cat));
			weeklist.add(testlist);
		}
		
		MultidayEventWeekView view = new MultidayEventWeekView(weeklist, new Date());
		assertEquals(21, view.getComponentCount());
	}
	
	@Test
	/**
	 * Test Passing EmptyList then Updating to Filled
	 */
	public void testMultidayEmptyListtoFilledList(){
		List<List<Event>> weeklist = new LinkedList<List<Event>>();
		for (int x = 0; x < 7; x++){
			weeklist.add(new LinkedList<Event>());
		}
		MultidayEventWeekView view = new MultidayEventWeekView(weeklist, new Date());
		assertNotNull(view);
		assertEquals(0, view.getComponentCount());
		
		weeklist = new LinkedList<List<Event>>();
		for (int x = 0; x < 7; x++){
			List<Event> testlist = new LinkedList<Event>();
			testlist.add(new Event("first", new Date(), new Date(), true, cat));
			testlist.add(new Event("second", new Date(), new Date(), true, cat));
			testlist.add(new Event("third", new Date(), new Date(), true, cat));
			weeklist.add(testlist);
		}
		
		view.updateMultiDay(weeklist, new Date());
		
		assertEquals(21, view.getComponentCount());
	}
	
	@Test
	/**
	 * Test Passing FilledList then Updating to Empty
	 */
	public void testMultidayFilledListtoEmptyList(){
		List<List<Event>> weeklist = new LinkedList<List<Event>>();
		for (int x = 0; x < 7; x++){
			List<Event> testlist = new LinkedList<Event>();
			testlist.add(new Event("first", new Date(), new Date(), true, cat));
			testlist.add(new Event("second", new Date(), new Date(), true, cat));
			testlist.add(new Event("third", new Date(), new Date(), true, cat));
			weeklist.add(testlist);
		}
		
		MultidayEventWeekView view = new MultidayEventWeekView(weeklist, new Date());
		assertEquals(21, view.getComponentCount());

		
		
		weeklist = new LinkedList<List<Event>>();
		for (int x = 0; x < 7; x++){
			weeklist.add(new LinkedList<Event>());
		}
		
		view.updateMultiDay(weeklist, new Date());
		
		assertNotNull(view);
		assertEquals(0, view.getComponentCount());
	}
	
	@Test
	/**
	 * Test Clearing Multiday Events
	 */
	public void testRemoveMultidayEvents(){
		List<List<Event>> weeklist = new LinkedList<List<Event>>();
		for (int x = 0; x < 7; x++){
			List<Event> testlist = new LinkedList<Event>();
			testlist.add(new Event("first", new Date(), new Date(), true, cat));
			testlist.add(new Event("second", new Date(), new Date(), true, cat));
			testlist.add(new Event("third", new Date(), new Date(), true, cat));
			weeklist.add(testlist);
		}
		
		MultidayEventWeekView view = new MultidayEventWeekView(weeklist, new Date());
		assertEquals(21, view.getComponentCount());
		
		view.ClearEventDropDown();
		assertEquals(0, view.getComponentCount());
	}
	
	@Test
	/**
	 * Test Resizing Multiday Event View
	 */
	public void testreSizingMultidayView(){
		List<List<Event>> weeklist = new LinkedList<List<Event>>();
		for (int x = 0; x < 7; x++){
			List<Event> testlist = new LinkedList<Event>();
			testlist.add(new Event("first", new Date(), new Date(), true, cat));
			testlist.add(new Event("second", new Date(), new Date(), true, cat));
			testlist.add(new Event("third", new Date(), new Date(), true, cat));
			weeklist.add(testlist);
		}
		
		MultidayEventWeekView view = new MultidayEventWeekView(weeklist, new Date());
		assertEquals(21, view.getComponentCount());
		
		int width = view.getWidth();
		width = width + 5;
		view.reSize(width);
		assertEquals(width, view.getWidth());
	}
	
	@Test
	/**
	 * Test Static Displaying Variables
	 */
	public void testGetStaticDisplay(){
		assertTrue(MultidayEventWeekView.areEventsDisplaying());
		MultidayEventWeekView.reverseEventsDisplaying();
		assertFalse(MultidayEventWeekView.areEventsDisplaying());
	}
}
