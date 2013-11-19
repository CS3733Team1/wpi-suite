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
