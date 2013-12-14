/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.calendar.view.buttons;

import java.util.ArrayList;
import java.util.List;

public class TransparentButtonGroup {

	private List<TransparentToggleButton> buttons;

	public TransparentButtonGroup() {
		buttons = new ArrayList<TransparentToggleButton>();
	}

	public void add(TransparentToggleButton b) {
		buttons.add(b);
	}
	
	public void setSelectedButton(int index) {		
		for(TransparentToggleButton b: buttons) {
			if(b.isSelected()) {
				b.setSelected(false);
			}
		}
		
		buttons.get(index).setSelected(true);
	}
}
