package edu.wpi.cs.wpisuitetng.modules.calendar.view.scheduledevent;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.commitment.CommitmentListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.event.EventListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.filter.FilteredCommitmentsListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.filter.FilteredEventsListModel;

public class LeaderPanelTest {
	
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
	public void canCreateLeaderPanel() {
		assertNotNull(new LeaderPanel());
	}
	
	@Test
	public void canAddUser() {
		LeaderPanel testPanel = new LeaderPanel();
		testPanel.addUser("Peter");
		assertEquals("Peter", testPanel.getUser());
	}
	
	@Test
	public void canUpdateUser() {
		LeaderPanel testPanel = new LeaderPanel();
		testPanel.addUser("Peter");
		assertEquals("Peter", testPanel.getUser());
		testPanel.addUser("Shadi");
		assertEquals("Shadi", testPanel.getUser());
	}

	@Test
	public void canSetTimeFrame() {
		LeaderPanel testPanel = new LeaderPanel();
		testPanel.setTimeFrame(5, 5, 10);
	}
	
	@Test
	public void canReSize() {
		LeaderPanel testPanel = new LeaderPanel();
		testPanel.setTimeFrame(5, 5, 10);
		testPanel.reSize(5);
	}
}
