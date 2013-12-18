package edu.wpi.cs.wpisuitetng.modules.calendar.view.scheduledevent;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.commitment.CommitmentListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.event.EventListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.filter.FilteredCommitmentsListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.filter.FilteredEventsListModel;

public class DayOfWeekTest {
	
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
	}

	@Test
	public void canCreateDayOfWeek() {
		assertNotNull(new DayOfWeek());
	}
	
	@Test
	public void canSetDay() {
		DayOfWeek testDay = new DayOfWeek();
		testDay.setDay(5);
		assertEquals(5, testDay.getDay());
	}
	
	@Test
	public void canUpdateDay() {
		DayOfWeek testDay = new DayOfWeek();
		testDay.setDay(5);
		assertEquals(5, testDay.getDay());
		testDay.setDay(2);
		assertEquals(2, testDay.getDay());
	}
	
	@Test
	public void canSetWeekend() {
		DayOfWeek testDay = new DayOfWeek();
		testDay.setDay(0);
		assertEquals(0, testDay.getDay());
		testDay.setDay(6);
		assertEquals(6, testDay.getDay());
	}

}
