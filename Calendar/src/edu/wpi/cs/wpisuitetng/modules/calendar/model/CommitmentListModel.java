package edu.wpi.cs.wpisuitetng.modules.calendar.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractListModel;

public class CommitmentListModel extends AbstractListModel<Commitment> {

	private List<Commitment> commitments;
	
	public CommitmentListModel() {
		commitments = new ArrayList<Commitment>();
	}
	
	public void addCommitment(Commitment commitment) {
		commitments.add(commitment);
		
		// Notify the model that it has changed so the GUI will be udpated
		this.fireIntervalAdded(this, 0, 0);
	}
	
	public void addCommitments(Commitment[] commitments) {
		for (int i = 0; i < commitments.length; i++) {
			this.commitments.add(commitments[i]);
		}
		this.fireIntervalAdded(this, 0, Math.max(getSize() - 1, 0));
	}
	
	/**
	 * Removes all messages from this model
	 * 
	 * NOTE: One cannot simply construct a new instance of the model, because
	 * other classes in this module have references to it. Hence, we manually
	 * remove each message from the model.
	 */
	public void emptyModel() {
		int oldSize = getSize();
		Iterator<Commitment> iterator = commitments.iterator();
		while (iterator.hasNext()) {
			iterator.next();
			iterator.remove();
		}
		this.fireIntervalRemoved(this, 0, Math.max(oldSize - 1, 0));
	}
	
	@Override
	public Commitment getElementAt(int index) {
		return this.commitments.get(commitments.size() - 1 - index);
	}

	@Override
	public int getSize() {
		return this.commitments.size();
	}
	
	public void removeCommitment(int index) {
		commitments.remove(index);
		this.fireIntervalRemoved(this, index, index);
	}
	
	public List<Commitment> getList(){
		return commitments;
	}
}