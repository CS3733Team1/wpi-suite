/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.month;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CommitmentPanelMouseListener implements MouseListener{
	@Override
	public void mouseClicked(MouseEvent e) {
		CommitmentPanel commitmentPanel = (CommitmentPanel)e.getSource();
		if(commitmentPanel.getSelected()) commitmentPanel.setSelected(false);
		else commitmentPanel.setSelected(true);
	}
	
	// Unused
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
}
