package edu.wpi.cs.wpisuitetng.modules.calendar.model;

import java.util.Date;

public interface ISchedulable
{
	public String getName();
	public String getDescription();
	public Category getCategory();
	public Date getStartDate();
	public Date getEndDate();
}
