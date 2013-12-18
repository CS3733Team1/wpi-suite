package edu.wpi.cs.wpisuitetng.modules.calendar.view.scheduledevent;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.List;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.commitment.CommitmentListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.event.EventListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.filter.FilteredCommitmentsListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.filter.FilteredEventsListModel;

public class LeaderTest {
	
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
