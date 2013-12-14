package edu.wpi.cs.wpisuitetng.modules.calendar.view.filter;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Filter;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.FilterListModel;

public class FilterPanel extends JPanel implements MouseListener {
	FilterListPanel filterListPanel;

	private JButton addFilterButton, deleteFilterButton;

	public FilterPanel() {
		this.setLayout(new MigLayout("fill", "[grow, fill]", "[][grow, fill]"));

		try {
			addFilterButton = new JButton("<html>New<br/>Filter</html>",
					new ImageIcon(ImageIO.read(getClass().getResource("/images/add_filter.png"))));

			deleteFilterButton = new JButton("<html>Delete<br/>Filter</html>",
					new ImageIcon(ImageIO.read(getClass().getResource("/images/delete_filter.png"))));
		} catch (IOException e) {e.printStackTrace();}

		this.filterListPanel = new FilterListPanel();
		
		this.filterListPanel.getFilterList().addMouseListener(this);
		
		addFilterButton.setActionCommand("add");
		deleteFilterButton.setActionCommand("delete");
		
		JPanel p = new JPanel();
		p.add(addFilterButton);
		p.add(deleteFilterButton);
		
		this.add(p, "wrap");
		this.add(filterListPanel, "grow, push");
		//initially disables delete button so users don't think they can delete the default filter
		deleteFilterButton.setEnabled(false);
	}
	
	public void setAddFilterListener(ActionListener al) {
		addFilterButton.addActionListener(al);
	}
	
	public void setDeleteFilterListener(ActionListener al) {
		deleteFilterButton.addActionListener(al);
	}

	public List<Filter> getSelectedFilters() {
		return filterListPanel.getFilterList().getSelectedValuesList();
	}

	public void clearSelection() {
		filterListPanel.clearSelection();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		boolean deleteEnabled = true;
		for( Filter c: getSelectedFilters()){
			if(FilterListModel.getFilterListModel().isDefault(c))
			{
				deleteEnabled = false;
			}
		}
		deleteFilterButton.setEnabled(deleteEnabled);
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}
}
