package edu.wpi.cs.wpisuitetng.modules.calendar.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class FilteredCommitmentsListModel extends AbstractListModel<Commitment> implements ListDataListener, FilterChangedListener {

	private static FilteredCommitmentsListModel filteredCommitmentsListModel;

	/** The list of filtered events on the calendar */
	private List<Commitment> filteredCommitments;
	
	private FilteredCommitmentsListModel() {
		filteredCommitments = new ArrayList<Commitment>();
		//filteredCommitments =  Collections.synchronizedList(new ArrayList<Commitment>());
		CommitmentListModel.getCommitmentListModel().addListDataListener(this);
		FilterListModel.getFilterListModel().addListDataListener(this);
		filterCommitments();
	}

	static public synchronized FilteredCommitmentsListModel getFilteredCommitmentsListModel() {
		if (filteredCommitmentsListModel == null)
			filteredCommitmentsListModel = new FilteredCommitmentsListModel();
		filteredCommitmentsListModel.filterCommitments();
		return filteredCommitmentsListModel;
	}
	private void filterCommitments() {
		List<Commitment> commitmentList = CommitmentListModel.getCommitmentListModel().getList();

		int removed = filteredCommitments.size();
		
		filteredCommitments.clear();
		
		this.fireIntervalRemoved(this, 0, Math.max(removed - 1, 0));
		
		for(Commitment c: FilterListModel.getFilterListModel().applyCommitmentFilter(commitmentList)) filteredCommitments.add(new Commitment(c));
		
		this.fireIntervalAdded(this, 0, Math.max(filteredCommitments.size() - 1, 0));
	}

	@Override
	public Commitment getElementAt(int index) {
		return filteredCommitments.get(filteredCommitments.size() - 1 - index);
	}

	@Override
	public int getSize() {
		return filteredCommitments.size();
	}

	// Used to update the filtered contents when there are changes to the EventListModel

	@Override
	public void contentsChanged(ListDataEvent e) {
		filterCommitments();
	}

	@Override
	public void intervalAdded(ListDataEvent e) {
		filterCommitments();
	}

	@Override
	public void intervalRemoved(ListDataEvent e) {
		filterCommitments();
	}
	
	@Override
	public void filterChanged() {
		filterCommitments();
	}

	public synchronized List<Commitment> getList() {
		filterCommitmens();
		return filteredCommitments;
	}
}
