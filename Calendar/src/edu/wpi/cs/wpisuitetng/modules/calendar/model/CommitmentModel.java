

/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Chris Casola
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.model;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractListModel;
@SuppressWarnings("serial")
public class CommitmentModel extends AbstractListModel<Object> { 





/**
 * This is a model for the commitment list. It contains all of the commitments to be
 * displayed on the calendar. It extends AbstractListModel so that it can provide
 * the model data to the JList component in the CalendarPanel.
 * 
 * @author Thomas DeSilva, Zach Estep
 * 
 */

	private static CommitmentModel model;
	/** The list of commitments on the calendar */

	private List<Commitment> commitment;

	/**
	 * Constructs a new calendar with no commitments.
	 */
	private CommitmentModel() {
		commitment = new ArrayList<Commitment>();
	}
	
	static public CommitmentModel getCommitmentModel()
	{
		if (model == null)
			model = new CommitmentModel();
		return model;
	}
	/**
	 * Adds the given commitment to the calendar
	 * 
	 * @param newCommitment
	 *            the new commitment to add
	 */
	public void addCommitment(Commitment newCommitment) {
		// Add the commitment
		commitment.add(newCommitment);

		// Notify the model that it has changed so the GUI will be udpated
		this.fireIntervalAdded(this, 0, 0);
	}

	/**
	 * Adds the given array of commitments to the calendar
	 * 
	 * @param commitments
	 *            the array of commitments to add
	 */
	public void addCommitments(Commitment[] commitments) {
		for (int i = 0; i < commitments.length; i++) {
			this.commitment.add(commitments[i]);
		}
		this.fireIntervalAdded(this, 0, Math.max(getSize() - 1, 0));
	}

	/**
	 * Removes all commitments from this model
	 * 
	 * NOTE: One cannot simply construct a new instance of the model, because
	 * other classes in this module have references to it. Hence, we manually
	 * remove each commitment from the model.
	 */
	public void emptyModel() {
		int oldSize = getSize();
		Iterator<Commitment> iterator = commitment.iterator();
		while (iterator.hasNext()) {
			iterator.next();
			iterator.remove();
		}
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
	public Object getElementAt(int index) {
		return commitment.get(commitment.size() - 1 - index);
	}
	
	public Object getElement(int index){
		return commitment.get(commitment.size() - 1 - index);
	}
	
	public void removeCommitment(int index)
	{
		commitment.remove(index);
		this.fireIntervalAdded(this, 0, 0);
	}
	
	public void removeCommitment(Commitment commit)
	{
		commitment.remove(commit);
		this.fireIntervalAdded(this, 0, 0);
	}
	

	/**
	 * Returns the number of commitments in the model. Also used internally by the
	 * JList in CalendarPanel.
	 * 
	 * @see javax.swing.ListModel#getSize()
	 */
	@Override
	public int getSize() {
		return commitment.size();
	}
	
	static public List<Commitment> getList(){
		List<Commitment> temp = new ArrayList<Commitment>();
		temp.addAll(getCommitmentModel().commitment);
		return temp;
	}
}
