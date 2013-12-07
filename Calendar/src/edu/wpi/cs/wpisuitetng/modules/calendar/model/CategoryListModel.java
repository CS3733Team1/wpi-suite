/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team TART
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.calendar.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.swing.DefaultComboBoxModel;

public class CategoryListModel extends DefaultComboBoxModel<Category> { 

	/**
	 * This is a model for the commitment list. It contains all of the commitments to be
	 * displayed on the calendar. It extends AbstractListModel so that it can provide
	 * the model data to the JList component in the CalendarPanel.
	 * 
	 * @author Thomas DeSilva, Zach Estep
	 * 
	 */

	private static CategoryListModel categoryModel;

	/** The list of commitments on the calendar */
	private List<Category> categories;

	private final Category[] defaultCategories = {
			new Category("Uncategorized", Color.LIGHT_GRAY), 
			new Category("Important", Color.RED),
			new Category("Work", Color.BLUE),
			new Category("Home", Color.ORANGE),
			new Category("Holiday", Color.PINK)};

	/**
	 * Constructs a new calendar with no commitments.
	 */
	private CategoryListModel() {
		categories = Collections.synchronizedList(new ArrayList<Category>());
	}

	static public CategoryListModel getCategoryListModel() {
		if (categoryModel == null)
			categoryModel = new CategoryListModel();
		return categoryModel;
	}
	/**
	 * Adds the given commitment to the calendar
	 * 
	 * @param newCategory
	 *            the new category to add
	 */
	public void addCategory(Category newCategory) {
		// Add the category
		categories.add(newCategory);
		this.fireIntervalAdded(this, 0, 0);
	}

	/**
	 * Adds the given array of commitments to the calendar
	 * 
	 * @param commitments
	 *            the array of commitments to add
	 */
	public void addCategories(Category [] categories) {
		for (int i = 0; i < categories.length; i++) {
			this.categories.add(categories[i]);
		}
		this.fireIntervalAdded(this, 0, Math.max(getSize() - 1, 0));
	}

	// Used to see if a category is a default or not
	public boolean isDefault(Category category) {
		return Arrays.asList(defaultCategories).contains(category);
	}

	public void setCategories(Category[] categories) {
		this.emptyModel();

		//DEFAULT CATEGORIES		
		for(Category category: defaultCategories) this.categories.add(category);

		for (int i = 0; i < categories.length; i++) {
			if(!(this.categories.contains(categories[i]))) this.categories.add(categories[i]);
		}

		this.fireIntervalAdded(this, 0, Math.max(getSize() - 1, 0));
	}

	/**
	 * Removes all commitments from this model
	 * 
	 * NOTE: One cannot simply construct a new instance of the model, because
	 * other classes in this module have references to it. Hence, we manually
	 * remove each commitment from the model.
	 */
	public void emptyModel() {
		Iterator<Category> iterator = categories.iterator();
		while (iterator.hasNext()) {
			iterator.next();
			iterator.remove();
		}
		this.fireIntervalAdded(this, 0, Math.max(getSize() - 1, 0));
	}

	/**
	 * Returns the commitment at the given index. This method is called internally
	 * by the JList in CalendarPanel. Note this method returns elements in reverse
	 * order, so newest commitments are returned first.
	 * 
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	@Override
	public Category getElementAt(int index) {
		return categories.get(index);
	}

	public void removeCategory(Category cat) {
		categories.remove(cat);
		this.fireIntervalAdded(this, 0, 0);
	}

	/**
	 * Returns the number of commitments in the model. Also used internally by the
	 * JList in CalendarPanel.
	 * 
	 * @see javax.swing.ListModel#getSize()
	 */
	@Override
	public int getSize() {
		return categories.size();
	}

	public static List<Category> getList() {
		return categoryModel.categories;
	}

	public boolean isReserved(String categoryName) {
		for(Category category: categories) if(category.getName().equals(categoryName)) return true;
		return false;
	}
}
