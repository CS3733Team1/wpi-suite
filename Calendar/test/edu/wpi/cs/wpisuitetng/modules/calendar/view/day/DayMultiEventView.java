package edu.wpi.cs.wpisuitetng.modules.calendar.view.day;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.CommitmentListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.EventListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.FilteredCommitmentsListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.FilteredEventsListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day.DayCalendar;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.day.MultidayEventView;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.week.MultidayEventWeekView;

public class DayMultiEventView {
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
		MultidayEventView view = new MultidayEventView(new LinkedList<Event>(), new Date());
		assertNotNull(view);
		assertEquals(0, view.getComponentCount());
	}
	
	@Test
	/**
	 * Test Passing List With Events
	 */
	public void testMultiConstructorFilledList(){
		List<Event> testlist = new LinkedList<Event>();
		testlist.add(new Event("first", new Date(), new Date(), true, cat));
		testlist.add(new Event("second", new Date(), new Date(), true, cat));
		testlist.add(new Event("third", new Date(), new Date(), true, cat));
		MultidayEventView view = new MultidayEventView(testlist, new Date());
		assertEquals(3, view.getComponentCount());
	}
	
	@Test
	/**
	 * Test Passing EmptyList then Updating to Filled
	 */
	public void testMultidayEmptyListtoFilledList(){
		MultidayEventView view = new MultidayEventView(new LinkedList<Event>(), new Date());
		assertNotNull(view);
		assertEquals(0, view.getComponentCount());
		
		List<Event> testlist = new LinkedList<Event>();
		testlist.add(new Event("first", new Date(), new Date(), true, cat));
		testlist.add(new Event("second", new Date(), new Date(), true, cat));
		testlist.add(new Event("third", new Date(), new Date(), true, cat));
		
		view.updateMultiDay(testlist, new Date());
		
		assertEquals(3, view.getComponentCount());
	}
	
	@Test
	/**
	 * Test Passing FilledList then Updating to Empty
	 */
	public void testMultidayFilledListtoEmptyList(){
		List<Event> testlist = new LinkedList<Event>();
		testlist.add(new Event("first", new Date(), new Date(), true, cat));
		testlist.add(new Event("second", new Date(), new Date(), true, cat));
		testlist.add(new Event("third", new Date(), new Date(), true, cat));
		MultidayEventView view = new MultidayEventView(testlist, new Date());
		assertEquals(3, view.getComponentCount());
		
		view.updateMultiDay(new LinkedList<Event>(), new Date());
		
		assertNotNull(view);
		assertEquals(0, view.getComponentCount());
	}
	
	@Test
	/**
	 * Test Clearing Multiday Events
	 */
	public void testRemoveMultidayEvents(){
		List<Event> testlist = new LinkedList<Event>();
		testlist.add(new Event("first", new Date(), new Date(), true, cat));
		testlist.add(new Event("second", new Date(), new Date(), true, cat));
		testlist.add(new Event("third", new Date(), new Date(), true, cat));
		MultidayEventView view = new MultidayEventView(testlist, new Date());
		assertEquals(3, view.getComponentCount());
		
		view.ClearEventDropDown();
		assertEquals(0, view.getComponentCount());
	}
	
	@Test
	/**
	 * Test Resizing Multiday Event View
	 */
	public void testreSizingMultidayView(){
		List<Event> testlist = new LinkedList<Event>();
		testlist.add(new Event("first", new Date(), new Date(), true, cat));
		testlist.add(new Event("second", new Date(), new Date(), true, cat));
		testlist.add(new Event("third", new Date(), new Date(), true, cat));
		MultidayEventView view = new MultidayEventView(testlist, new Date());
		assertEquals(3, view.getComponentCount());
		
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
		assertTrue(MultidayEventView.areEventsDisplaying());
		MultidayEventView.reverseEventsDisplaying();
		assertFalse(MultidayEventView.areEventsDisplaying());
	}
}
