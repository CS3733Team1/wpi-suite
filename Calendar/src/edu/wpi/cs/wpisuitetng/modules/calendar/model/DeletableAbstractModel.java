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

import java.util.Date;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

public abstract class DeletableAbstractModel extends AbstractModel{
	
	protected boolean MarkedForDeletion = false;
	
	public boolean isMarkedForDeletion()
	{
		return this.MarkedForDeletion;
	}
	public void markForDeletion()
	{
		this.MarkedForDeletion = true;
		System.out.println("AbsModel: deletion" + MarkedForDeletion);
	}

}
