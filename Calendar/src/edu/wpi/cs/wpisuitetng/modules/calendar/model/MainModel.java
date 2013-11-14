/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.model;

import java.util.List;

public class MainModel {

	//TODO: addCommitment in CalendarModel
	//TODO: commitmentModel in CalendarModel
	
	private CommitmentModel commitmentModel;
	private EventModel eventModel;
	/**
	 * 
	 */
	public MainModel()
	{
		commitmentModel = CommitmentModel.getCommitmentModel();
		eventModel = EventModel.getEventModel();
	}
	/**
	 * 
	 * @param newCommitment
	 */
	public void addCommitmentFromCalendar(Commitment newCommitment)
	{
		this.commitmentModel.addCommitment(newCommitment);
	}
	/**
	 * 
	 * @param commitmentLocation
	 * @return
	 */
	public String getCommitmentsFromCalendar(int commitmentLocation)
	{
		return  commitmentModel.getElementAt(commitmentLocation).toString();
	}
	/**
	 * 
	 * @return
	 */
	public static List<Commitment> getCommitmentModelList(){
		return CommitmentModel.getList();
	}
	/**
	 * 
	 * @return
	 */
	public CommitmentModel getCommitmentModel(){
		return commitmentModel;
	}
	
	
	public void removeCommitment(Commitment commit)
	{
		commitmentModel.removeCommitment(commit);
	}
	
	
	//EVENTS
	
	public void addEventFromCalendar(Event newEvent)
	{
		this.eventModel.addEvent(newEvent);
	}
	/**
	 * 
	 * @param commitmentLocation
	 * @return
	 */
	public String getEventsFromCalendar(int eventLocation)
	{
		return  commitmentModel.getElementAt(eventLocation).toString();
	}
	/**
	 * 
	 * @return
	 */
	public List<Event> getEventList(){
		return eventModel.getList();
	}
	/**
	 * 
	 * @return
	 */
	public EventModel getEventModel(){
		return eventModel;
	}
	
	
	public void removeEvent(Event event)
	{
		eventModel.removeEvent(event);
	}
	
}

