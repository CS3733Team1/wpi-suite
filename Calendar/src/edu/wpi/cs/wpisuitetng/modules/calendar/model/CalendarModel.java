package edu.wpi.cs.wpisuitetng.modules.calendar.model;


import java.util.ArrayList;
import java.util.List;

public class CalendarModel {

	//TODO: addCommitment in CalendarModel
	//TODO: commitmentModel in CalendarModel
	
	private CommitmentModel commitModel;
	private EventModel eventModel;
	/**
	 * 
	 */
	public CalendarModel()
	{
		commitModel = CommitmentModel.getCommitmentModel();
		eventModel = new EventModel();
	}
	/**
	 * 
	 * @param newCommitment
	 */
	public void addCommitmentFromCalendar(Commitment newCommitment)
	{
		this.commitModel.addCommitment(newCommitment);
	}
	/**
	 * 
	 * @param commitmentLocation
	 * @return
	 */
	public String getCommitmentsFromCalendar(int commitmentLocation)
	{
		return  commitModel.getElementAt(commitmentLocation).toString();
	}
	/**
	 * 
	 * @return
	 */
	public List<Commitment> getList(){
		return commitModel.getList();
	}
	/**
	 * 
	 * @return
	 */
	public CommitmentModel getcommitModel(){
		return commitModel;
	}
	
	
	public void removeCommitment(Commitment commit)
	{
		commitModel.removeCommitment(commit);
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
		return  commitModel.getElementAt(eventLocation).toString();
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

