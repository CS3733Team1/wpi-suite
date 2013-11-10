package edu.wpi.cs.wpisuitetng.modules.calendar.model;


import java.util.ArrayList;
import java.util.List;

public class CalendarModel {

	//TODO: addCommitment in CalendarModel
	//TODO: commitmentModel in CalendarModel
	
	private CommitmentModel commitModel;
	
	public CalendarModel()
	{
		commitModel = new CommitmentModel();
	}
	
	public void addCommitmentFromCalendar(Commitment newCommitment)
	{
		this.commitModel.addCommitment(newCommitment);
	}
	
	public String getCommitmentsFromCalendar(int commitmentLocation)
	{
		return  commitModel.getElementAt(commitmentLocation).toString();
	}
	
	public List<Commitment> getList(){
		return commitModel.getList();
	}
	
	public CommitmentModel getcommitModel(){
		return commitModel;
	}
	
	
	public void removeCommitment(Commitment commit)
	{
		commitModel.removeCommitment(commit);
	}
	
}

