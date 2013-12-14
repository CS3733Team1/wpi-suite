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

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JToggleButton;

public class TransparentToggleButton extends JToggleButton implements MouseListener {
	
	public TransparentToggleButton(String string) {
		super(string);
		init();
	}
	
	public TransparentToggleButton(ImageIcon imageIcon) {
		super(imageIcon);
		init();
	}
	
	public TransparentToggleButton(String string, ImageIcon imageIcon) {
		super(string, imageIcon);
		init();
	}
	
	private void init() {
		this.setOpaque(false);
		this.setContentAreaFilled(false);
		this.setBorderPainted(false);
		this.setFocusable(false);
		
		this.addMouseListener(this);
	}
	
	@Override
	public void setSelected(boolean b) {
		if(b) {
			this.setOpaque(true);
			this.setContentAreaFilled(true);
			this.setBorderPainted(true);
		} else {
			this.setOpaque(false);
			this.setContentAreaFilled(false);
			this.setBorderPainted(false);
		}
		
		super.setSelected(b);
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {
		this.setOpaque(true);
		this.setContentAreaFilled(true);
		this.setBorderPainted(true);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(!this.isSelected()) {
			this.setOpaque(false);
			this.setContentAreaFilled(false);
			this.setBorderPainted(false);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}
}
