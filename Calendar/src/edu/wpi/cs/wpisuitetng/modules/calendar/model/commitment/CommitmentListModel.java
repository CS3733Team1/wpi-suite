/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.model.commitment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractListModel;

public class CommitmentListModel extends AbstractListModel<Commitment> { 

	/**
	 * This is a model for the commitment list. It contains all of the commitments to be
	 * displayed on the calendar. It extends AbstractListModel so that it can provide
	 * the model data to the JList component in the CalendarPanel.
	 * 
	 * @author Team TART
	 * 
	 */

	private static CommitmentListModel commitmentListModel;

	/** The list of commitments on the calendar */
	private List<Commitment> commitments;

	/**
	 * Constructs a new calendar with no commitments.
	 */
	private CommitmentListModel() {
		commitments = Collections.synchronizedList(new ArrayList<Commitment>());
	}

	static public synchronized CommitmentListModel getCommitmentListModel() {
		if (commitmentListModel == null)
			commitmentListModel = new CommitmentListModel();
		return commitmentListModel;
	}

	/**
	 * Adds the given commitment to the calendar
	 * 
	 * @param newCommitment
	 *            the new commitment to add
	 */
	public synchronized void addCommitment(Commitment newCommitment) {
		// Add the commitment
		this.commitments.add(newCommitment);
		Collections.sort(this.commitments);

		// Notify the model that it has changed so the GUI will be udpated
		this.fireIntervalAdded(this, 0, 0);
	}

	/**
	 * Adds the given array of commitments to the calendar
	 * 
	 * @param commitments
	 *            the array of commitments to add
	 */
	public synchronized void  addCommitments(Commitment[] commitments) {
		for (int i = 0; i < commitments.length; i++) {
			this.commitments.add(commitments[i]);
		}
		Collections.sort(this.commitments);

		this.fireContentsChanged(this, 0, this.commitments.size()-1);
	}

	/**
	 * Sets the list to mirror the array of commitments being passed in
	 * @param commitments The list of Commitments the list should mirror
	 */
	public synchronized void setCommitments(Commitment[] commitments) {
		this.commitments.clear();
		for (int i = 0; i < commitments.length; i++) {
			this.commitments.add(commitments[i]);
		}
		Collections.sort(this.commitments);
		this.fireIntervalAdded(this, 0, Math.max(getSize() - 1, 0));
	}

	/**
	 * Removes all commitments from this model
	 * 
	 * NOTE: One cannot simply construct a new instance of the model, because
	 * other classes in this module have references to it. Hence, we manually
	 * remove each commitment from the model.
	 */
	public synchronized void emptyModel() {
		int oldSize = getSize();
		commitments.removeAll(getList());
		this.fireIntervalRemoved(this, 0, Math.max(oldSize - 1, 0));
	}

	/**
	 * Returns the commitment at the given index. This method is called internally
	 * by the JList in CalendarPanel. Note this method returns elements in reverse
	 * order, so newest commitments are returned first.
	 * 
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	@Override
	public Commitment getElementAt(int index) {
		return commitments.get(commitments.size() - 1 - index);
	}

	public synchronized Commitment getElement(int index){
		return commitments.get(commitments.size() - 1 - index);
	}

	/**
	 * Removes a commitment based on an index
	 * @param index integer representing Commitment to be removed
	 */
	public synchronized void removeCommitment(int index) {
		this.commitments.remove(index);
		this.fireIntervalRemoved(this, 0, 0);
	}
	
	/**
	 * Removes the specified commitment from the list
	 * @param commitment Commitment to be removed
	 */
	public synchronized void removeCommitment(Commitment commitment) {
		this.commitments.remove(commitment);
		this.fireIntervalRemoved(this, 0, 0);
	}

	/**
	 * Returns the number of commitments in the model. Also used internally by the
	 * JList in CalendarPanel.
	 * 
	 * @see javax.swing.ListModel#getSize()
	 */
	@Override
	public int getSize() {
		return commitments.size();
	}

	/**
	 * Gets the list of commitments
	 * @return list of commitments
	 */
	public synchronized List<Commitment> getList(){
		return new ArrayList<Commitment>(commitments);
	}
	
	/**
	 * general update method, which signals to all the views that the model has changed
	 */
	public synchronized void update() {
		this.fireIntervalAdded(this, 0, this.getSize() > 0 ? this.getSize() -1 : 0);
	}

	/**
	 * Updates the listmodel with the new instance of the commitment
	 * @param oldCommitment the commitment to be removed from list
	 * @param newCommitment new updated commitment to be added to list
	 */
	public synchronized void updateCommitment(Commitment oldCommitment, Commitment newCommitment) {
		removeCommitment(oldCommitment);
		addCommitment(newCommitment);
		this.fireIntervalAdded(this, 0, 0);
	}
}
