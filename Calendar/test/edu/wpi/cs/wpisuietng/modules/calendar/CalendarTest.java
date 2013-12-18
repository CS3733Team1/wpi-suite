package edu.wpi.cs.wpisuietng.modules.calendar;

import static org.junit.Assert.assertNotNull;

import java.awt.Color;
import java.util.Date;

import org.junit.After;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.calendar.Calendar;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.category.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.category.CategoryListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.commitment.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.commitment.CommitmentListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.event.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.event.EventListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.filter.FilteredCommitmentsListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.filter.FilteredEventsListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.commitment.CommitmentTabPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.event.EventTabPanel;

public class CalendarTest {
	
	@After
	public void testTearDown(){
		System.gc();
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
	public void testCalendarInstance(){
		EventListModel emodel = EventListModel.getEventListModel();
		CommitmentListModel cmodel = CommitmentListModel.getCommitmentListModel();
		emodel.emptyModel();
		cmodel.emptyModel();
		
		Calendar testcal = new Calendar();
		assertNotNull(testcal);
		testcal = null;
	}
	
	@Test
	public void testAddEventInstance(){
		CategoryListModel catListModel = CategoryListModel.getCategoryListModel();
		catListModel.setCategories(new Category[]{});
		Event event = new Event("new event", new Date(100, 12, 5), new Date(99, 6, 6), true, "", new Category("test", Color.BLACK));
		EventTabPanel testaddevent = new EventTabPanel(event);
		assertNotNull(testaddevent);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testAddCommitmentInstance(){
		CategoryListModel catListModel = CategoryListModel.getCategoryListModel();
		catListModel.setCategories(new Category[]{});
		Commitment commitment = new Commitment("new", new Date(113, 11, 11), true, "",  new Category("test", Color.BLACK), "New");
		CommitmentTabPanel testaddcommitment = new CommitmentTabPanel(commitment);
		assertNotNull(testaddcommitment);
	}
}
