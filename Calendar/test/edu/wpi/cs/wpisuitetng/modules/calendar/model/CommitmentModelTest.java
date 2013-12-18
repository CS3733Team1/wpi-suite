package edu.wpi.cs.wpisuitetng.modules.calendar.model;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.calendar.model.commitment.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.commitment.CommitmentListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.event.EventListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.filter.FilteredCommitmentsListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.filter.FilteredEventsListModel;

public class CommitmentModelTest {
	
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
	/**
	 * Tests Adding Single Commitment
	 */
	public void testAddingCommitment(){
		CommitmentListModel model = CommitmentListModel.getCommitmentListModel();
		model.emptyModel();
		Commitment simple = new Commitment();
		model.addCommitment(simple);
		assertEquals(simple, model.getElement(0));
	}
	
	@Test
	/**
	 * Tests Adding Multiple Commitments
	 */
	public void testAddingMultipleCommitments(){
		CommitmentListModel model = CommitmentListModel.getCommitmentListModel();
		model.emptyModel();
		Commitment simple = new Commitment("first", new Date(), true);
		model.addCommitment(simple);
		simple = new Commitment("s", new Date(), true);
		model.addCommitment(simple);
		simple = new Commitment("t", new Date(), true);
		model.addCommitment(simple);
		simple = new Commitment("f", new Date(), true);
		model.addCommitment(simple);
		simple = new Commitment("d", new Date(), true);
		model.addCommitment(simple);
		simple = new Commitment("e", new Date(), true);
		model.addCommitment(simple);
		simple = new Commitment("p", new Date(), true);
		model.addCommitment(simple);
		simple = new Commitment("q", new Date(), true);
		model.addCommitment(simple);
		assertEquals(8, model.getSize());
	}
	
	@Test
	/**
	 * Tests Deleting single Commitment from List by Index
	 */
	public void testDeleteSingleCommitmentbyIndex(){
		CommitmentListModel model = CommitmentListModel.getCommitmentListModel();
		model.emptyModel();
		Commitment simple = new Commitment();
		model.addCommitment(simple);
		assertEquals(simple, model.getElement(0));
		model.removeCommitment(0);
		assertEquals(0, model.getSize());
	}
	
	@Test
	/**
	 * Tests Deleting Multiple Commitments from List by Index
	 */
	public void testDeleteMultipleCommitmentbyIndex(){
		CommitmentListModel model = CommitmentListModel.getCommitmentListModel();
		model.emptyModel();
		Commitment first = new Commitment("first", new Date(), true);
		model.addCommitment(first);
		Commitment second = new Commitment("second", new Date(), true);
		model.addCommitment(second);
		assertEquals(first, model.getElement(0));
		model.removeCommitment(0);
		assertEquals(second,model.getElement(0));
		model.removeCommitment(0);
		assertEquals(0, model.getSize());
	}
	
	@Test
	/**
	 * Tests Deleting single Commitment from List by Value
	 */
	public void testDeleteSingleCommitmentbyValue(){
		CommitmentListModel model = CommitmentListModel.getCommitmentListModel();
		model.emptyModel();
		Commitment simple = new Commitment();
		model.addCommitment(simple);
		assertEquals(simple, model.getElement(0));
		model.removeCommitment(simple);
		assertEquals(0, model.getSize());
	}
	
	@Test
	/**
	 * Tests Deleting Multiple Commitments from List by Value
	 */
	public void testDeleteMultipleCommitmentbyValue(){
		CommitmentListModel model = CommitmentListModel.getCommitmentListModel();
		model.emptyModel();
		Commitment first = new Commitment("first", new Date(), true);
		model.addCommitment(first);
		Commitment second = new Commitment("second", new Date(), true);
		model.addCommitment(second);
		assertEquals(first, model.getElement(0));
		model.removeCommitment(first);
		assertEquals(second,model.getElement(0));
		model.removeCommitment(second);
		assertEquals(0, model.getSize());
	}
	
	@Test
	/**
	 * Second Deletion First
	 */
	public void testDeleteSecondMultipleCommitmentbyValue(){
		CommitmentListModel model = CommitmentListModel.getCommitmentListModel();
		model.emptyModel();
		Commitment first = new Commitment("first", new Date(), true);
		model.addCommitment(first);
		Commitment second = new Commitment("second", new Date(), true);
		model.addCommitment(second);
		assertEquals(first, model.getElement(0));
		model.removeCommitment(second);
		assertEquals(first,model.getElement(0));
		model.removeCommitment(first);
		assertEquals(0,model.getSize());
	}
	
	@Test
	/**
	 * Test Update Commitment
	 */
	public void testUpdateCommitment(){
		CommitmentListModel model = CommitmentListModel.getCommitmentListModel();
		model.emptyModel();
		Commitment first = new Commitment("first", new Date(), true);
		Commitment second = new Commitment("second", new Date(), true);
		model.updateCommitment(first, second);
		assertEquals(1, model.getSize());
		assertEquals(second,model.getElement(0));
	}
}
