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

import java.util.UUID;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

public abstract class DeletableAbstractModel extends AbstractModel{
	
	//In the beginning it was described in Model.java
	//that models shall have a Unique Identifier int
	//and so it shall be 
	public long UniqueID = 0;//(int) UUID.randomUUID().getMostSignificantBits();
	
	protected int OwnerID=0;
	protected String OwnerName;
	protected boolean isTeam=true;
	
	public long getUniqueID()
	{
		return UniqueID;
	}
	public void setUniqueID(long id)
	{
		this.UniqueID=id;
	}
	public boolean getisTeam()
	{
		return isTeam;
	}
	public void setisTeam(boolean teamhuh)
	{
		this.isTeam=teamhuh;
	}
	public void setOwnerName(String own)
	{
		this.OwnerName=own;
	}
	public String getOwnerName()
	{
		return this.OwnerName;
	}
	public void setOwnerID(int id)
	{
		this.OwnerID=id;
	}
	public int getOwnerID()
	{
		return this.OwnerID;
	}
	protected boolean MarkedForDeletion = false;
	
	@Deprecated
	public boolean isMarkedForDeletion()
	{
		return this.MarkedForDeletion;
	}
	
	@Deprecated
	public void unmarkForDeletion()
	{
		this.MarkedForDeletion = false;
	}
	
	@Deprecated 
	public void markForDeletion()
	{
		this.MarkedForDeletion = true;
	}
}
