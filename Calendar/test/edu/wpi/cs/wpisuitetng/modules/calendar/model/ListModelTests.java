package edu.wpi.cs.wpisuitetng.modules.calendar.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class ListModelTests {

	@Test
	public void getCommitmentModel(){
		assertNotNull(CommitmentListModel.getCommitmentListModel());
	}
	
	@Test
	public void getCategoryModel(){
		assertNotNull(CategoryListModel.getCategoryListModel());
	}
	
	@Test
	public void getEventModel(){
		assertNotNull(EventListModel.getEventListModel());
	}
	
	@Test
	public void getFilterModel(){
		assertNotNull(FilterListModel.getFilterListModel());
	}
	
	@Test
	public void getFilterEventModel(){
		assertNotNull(FilteredEventsListModel.getFilteredEventsListModel());
	}
	
	@Test
	public void getFilterCommitmentModel(){
		assertNotNull(FilteredCommitmentsListModel.getFilteredCommitmentsListModel());
	}
}
