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

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

public abstract class DeletableAbstractModel extends AbstractModel {
	//In the beginning it was described in Model.java
	//that models shall have a Unique Identifier int
	//and so it shall be
	protected long uniqueID = 0;
	
	protected int ownerID = 0;
	protected String ownerName;
	protected boolean isTeam = true;
	
	public DeletableAbstractModel()
	{
		
	}
	public DeletableAbstractModel(DeletableAbstractModel other)
	{
		this.uniqueID=other.uniqueID;
		this.ownerID = other.ownerID;
		this.isTeam = other.isTeam;
		this.ownerName=other.ownerName;
	}
	public long getUniqueID() {
		return uniqueID;
	}
	
	public void setUniqueID(long id) {
		this.uniqueID = id;
	}
	
	public boolean getisTeam() {
		return isTeam;
	}
	
	public void setisTeam(boolean teamhuh) {
		this.isTeam = teamhuh;
	}
	
	public void setOwnerName(String own) {
		this.ownerName = own;
	}
	
	public String getOwnerName() {
		return this.ownerName;
	}
	
	public void setOwnerID(int id) {
		this.ownerID = id;
	}
	
	public int getOwnerID() {
		return this.ownerID;
	}
	
	// Unused
	@Override
	public Boolean identify(Object o) {return null;}
	@Override
	public void save() {}
	@Override
	public void delete() {}
}
