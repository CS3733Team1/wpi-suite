package edu.wpi.cs.wpisuietng.modules.calendar;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.calendar.Calendar;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.CategoryListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.CommitmentListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Event;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.EventListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.commitment.CommitmentTabPanel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.event.EventTabPanel;

public class CalendarTest {
	
//	@Test
//	public void testCalendarInstance(){
//		EventListModel emodel = EventListModel.getEventListModel();
//		CommitmentListModel cmodel = CommitmentListModel.getCommitmentListModel();
//		emodel.emptyModel();
//		cmodel.emptyModel();
//		
//		Calendar testcal = new Calendar();
//		assertNotNull(testcal);
//		testcal = null;
//	}
	
	@Test
	public void testAddEventInstance(){
		CategoryListModel catListModel = CategoryListModel.getCategoryListModel();
		catListModel.setCategories(new Category[]{});
		Event event = new Event("new event", new Date(100, 12, 5), new Date(99, 6, 6));
		EventTabPanel testaddevent = new EventTabPanel(event);
		assertNotNull(testaddevent);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testAddCommitmentInstance(){
		CategoryListModel catListModel = CategoryListModel.getCategoryListModel();
		catListModel.setCategories(new Category[]{});
		Commitment commitment = new Commitment("new", new Date(113, 11, 11), true);
		CommitmentTabPanel testaddcommitment = new CommitmentTabPanel(commitment);
		assertNotNull(testaddcommitment);
	}
}
