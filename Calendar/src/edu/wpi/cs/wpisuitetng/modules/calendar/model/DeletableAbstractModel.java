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

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

public abstract class DeletableAbstractModel extends AbstractModel{
	
	//In the beginning it was described in Model.java
	//that models shall have a Unique Identifier int
	//and so it shall be 
	public int UniqueID = (int) UUID.randomUUID().getMostSignificantBits();
	
	public int getUniqueID()
	{
		return UniqueID;
	}
	@Deprecated
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
