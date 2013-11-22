/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.calendar.controller.commitment;

/**
 * This observer is called when a response is received from a request
 * to the server to update a commitment.
 */

public class UpdateCommitmentObserver 
{
	private final UpdateCommitmentController controller;
	
	/**
	 * Constructs the observer given an UpdateCommitmentController
	 * @param controller The controller used to update commitments
	 */
	public UpdateCommitmentObserver(UpdateCommitmentController controller) 
	{
		this.controller = controller;
	}

}
