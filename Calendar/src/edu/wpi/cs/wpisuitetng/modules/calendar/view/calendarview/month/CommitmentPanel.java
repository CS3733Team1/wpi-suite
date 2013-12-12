package edu.wpi.cs.wpisuitetng.modules.calendar.view.calendarview.month;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Commitment;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.CalendarUtils;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utils.DateUtils;

public class CommitmentPanel extends EvComPanel {
	
	private Commitment commitment;
	
	private Color backgroundColor, selectedBackgroundColor;
	private Color textColor, selectedTextColor;
	
	private boolean isSelected;
	
	private JLabel commitmentNameLabel;
	private JLabel commitmentTimeLabel;
	
	public CommitmentPanel(Commitment commitment, Color backgroundColor) {
		super(false);
		this.commitment = commitment;
		
		this.backgroundColor = backgroundColor;
		this.selectedBackgroundColor = commitment.getCategory().getColor();
		this.textColor = CalendarUtils.titleNameColor;
		this.selectedTextColor = CalendarUtils.textColor(selectedBackgroundColor);
		
		this.isSelected = false;
			
		this.setLayout(new MigLayout("insets 0, gap 0", "0[]push[]0", "0[]0"));
		
		this.setBackground(backgroundColor);
		
		commitmentNameLabel = new JLabel(commitment.getName());
		commitmentNameLabel.setForeground(this.textColor);

		commitmentTimeLabel = new JLabel(DateUtils.timeToString(commitment.getDueDate()));
		commitmentTimeLabel.setFont(new Font(commitmentTimeLabel.getFont().getName(), Font.PLAIN, 8));
		commitmentTimeLabel.setForeground(this.textColor);
		
		this.add(commitmentNameLabel, "gap left 5, wmin 0");
		this.add(commitmentTimeLabel, "gap right 5");
	}
	
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
		
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
	
	public boolean getSelected()  {return this.isSelected;}
	
	public Commitment getCommitment() {return this.commitment;}
}
