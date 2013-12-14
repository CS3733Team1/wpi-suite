package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.month;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.CalendarUtils;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.DateUtils;

public class CommitmentPanel extends EvComPanel {
	
	private Commitment commitment;
	
	private JLabel commitmentNameLabel;
	private JLabel commitmentTimeLabel;
	private List<Commitment> commitmentList;
	
	public CommitmentPanel(Commitment commitment) {
		super(false, false);
		this.commitment = commitment;
		
		this.backgroundColor = Color.white;
		this.selectedBackgroundColor = commitment.getCategory().getColor();
		this.textColor = CalendarUtils.titleNameColor;
		this.selectedTextColor = CalendarUtils.textColor(selectedBackgroundColor);
			
		this.setLayout(new MigLayout("insets 0, gap 0", "0[]push[]0", "0[]0"));
		
		commitmentNameLabel = new JLabel(commitment.getName());

		commitmentTimeLabel = new JLabel(DateUtils.timeToString(commitment.getDueDate()));
		commitmentTimeLabel.setFont(new Font(commitmentTimeLabel.getFont().getName(), Font.PLAIN, 8));
		
		this.setSelected(false);
		
		this.add(commitmentNameLabel, "gap left 5, wmin 0");
		this.add(commitmentTimeLabel, "gap right 5");
	}

	private void update() {
		if(this.isSelected) {
			this.setBackground(selectedBackgroundColor);
			commitmentNameLabel.setForeground(selectedTextColor);
			commitmentTimeLabel.setForeground(selectedTextColor);
		} else {
			this.setBackground(backgroundColor);
			commitmentNameLabel.setForeground(textColor);
			commitmentTimeLabel.setForeground(textColor);
		}
	}

	@Override
	public void setSelected(boolean isSelected) {		
		this.isSelected = isSelected;
		
		update();
	}
	
	public Commitment getCommitment() {return this.commitment;}
}
