/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.view.filter;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.ListCellRenderer;
import javax.swing.border.MatteBorder;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Category;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.Filter;
import edu.wpi.cs.wpisuitetng.modules.calendar.model.FilterListModel;
import edu.wpi.cs.wpisuitetng.modules.calendar.view.utilities.CalendarUtils;

public class FilterListCellRenderer extends JPanel implements ListCellRenderer<Filter> {

	private JPanel colorSquares;
	private JLabel filterName;
	private JRadioButton appliedFilterRadio;

	public FilterListCellRenderer() {
		this.setLayout(new MigLayout("", "[]push[]", ""));
		filterName = new JLabel();
		colorSquares = new JPanel(new MigLayout("insets 0"));

		appliedFilterRadio = new JRadioButton();

		this.add(appliedFilterRadio, "split 2");
		this.add(filterName, "alignx left, wmax 130");
		this.add(colorSquares, "alignx right");

		this.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends Filter> list, Filter filter,
			int index, boolean isSelected, boolean cellHasFocus) {

		if(filter != null) {
			filterName.setText(filter.getName());
			colorSquares.removeAll();
			if(!filterName.getText().equals("Unfiltered")) {
				int i = 0;
				for(Category c: filter.getCategories()) {
					if(i < 2) {
						JPanel colorSquare = new JPanel();
						colorSquare.setPreferredSize(new Dimension(16, 16));
						colorSquare.setBorder(new MatteBorder(2, 2, 2, 2, Color.BLACK));
						colorSquare.setBackground(c.getColor());
						colorSquares.add(colorSquare);
					} else {
						colorSquares.add(new JLabel("..."));
						break;
					}
					i++;
				}
			}
		}

		if(filter.getSelected()) appliedFilterRadio.setSelected(true);
		else appliedFilterRadio.setSelected(false);

		if(cellHasFocus) {
			if(list.getModel() instanceof FilterListModel) {
				FilterListModel model = (FilterListModel)list.getModel();
				model.setActiveFilter(filter);
			}
		}

		if(isSelected) {
			this.setBackground(CalendarUtils.selectionColor);
			colorSquares.setBackground(CalendarUtils.selectionColor);
			appliedFilterRadio.setBackground(CalendarUtils.selectionColor);
		} else {
			this.setBackground(Color.WHITE);
			colorSquares.setBackground(Color.WHITE);
			appliedFilterRadio.setBackground(Color.WHITE);
		}

		return this;
	}
}
