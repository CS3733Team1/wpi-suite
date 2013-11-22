/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.view.commitment;

import javax.swing.JList;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;

public class CommitmentSubTabPanel extends JPanel {
	
	private CommitmentListPanel commitmentListPanel;
	
	public CommitmentSubTabPanel() {
		this.setLayout(new MigLayout("fill"));
		
		commitmentListPanel = new CommitmentListPanel();
		JPanel p = new JPanel(new MigLayout("fill"));
		p.add(commitmentListPanel, "grow, push");
		
		this.add(p, "grow, push");
	}
	
	public JList<Commitment> getCommitmentsList() {
		return commitmentListPanel.getCommitmentList();
	}
}
