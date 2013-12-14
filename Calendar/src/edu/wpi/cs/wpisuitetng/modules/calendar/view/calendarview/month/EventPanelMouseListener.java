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

public class EventPanelMouseListener implements MouseListener{
	@Override
	public void mouseClicked(MouseEvent e) {
		EventPanel eventPanel = (EventPanel)e.getSource();
		if(eventPanel.getSelected()) eventPanel.setSelected(false);
		else eventPanel.setSelected(true);
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
