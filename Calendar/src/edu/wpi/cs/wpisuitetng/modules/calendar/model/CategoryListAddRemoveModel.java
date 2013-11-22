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

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;

public class CategoryListAddRemoveModel extends DefaultComboBoxModel<Category> { 

	private List<Category> categories;

	public CategoryListAddRemoveModel() {
		this.categories = new ArrayList<Category>();
	}
	
	public CategoryListAddRemoveModel(List<Category> categories) {
		this.categories = new ArrayList<Category>();
		for(Category c: categories) this.categories.add(c);
	}

	public void addCategory(Category category) {
		categories.add(category);
		this.fireIntervalAdded(this, 0, 0);
	}

	public void addCategories(List<Category> categories) {
		for(Category c: categories) this.categories.add(c);
		this.fireIntervalAdded(this, 0, categories.size()-1);
	}
	
	@Override
	public Category getElementAt(int index) {
		return categories.get(index);
	}
	
	public void removeCategory(int index) {
		categories.remove(index);
		this.fireIntervalAdded(this, 0, 0);
	}

	public void removeCategory(Category category) {
		categories.remove(category);
		this.fireIntervalAdded(this, 0, 0);
	}
	
	public void removeCategories(List<Category> categories) {
		for(Category c: categories) this.categories.remove(c);
		this.fireIntervalRemoved(this, 0, 0);
	}

	@Override
	public int getSize() {
		return categories.size();
	}

	public List<Category> getList() {
		return categories; 
	}
}
