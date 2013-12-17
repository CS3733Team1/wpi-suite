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

import com.google.gson.Gson;
/**
 * Class that holds data for a Category. A Category has:<br>
 * -A name<br>
 * -A Color<br>
 * 
 * Privately, a Category also has a boolean (isReal) that says whether this is the real Category that
 * exists in the database.
 */
public class Category extends DeletableAbstractModel {
	private String name;
	private Color color;
	public boolean isReal = true;

	/**
	 * @return <b>true</b> if this Category is not a duplicate, <b>false</b> if it is
	 */
	public boolean getisReal() {
		return isReal;
	}
	/**
	 * Category constructor
	 */
	public Category(){}

	/**
	 * Category constructor
	 * @param name
	 * @param color
	 */
	public Category(String name, Color color) {
		this.name = name;
		this.color = color;
		this.isReal = true;
	}

	public Category(String name, Color color, long uid) {
		this.name = name;
		this.color = color;
		this.isReal = true;
		this.setUniqueID(uid);
	}

	/**
	 * @return the name of the Category
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the new name of the Category
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the Color associated with the Category
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param color the Color to associate with the Category
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
	}

	@Override
	public String toJSON() {
		//name, dueDate, description, category
		String str = new Gson().toJson(this, Category.class);
		return str;
	}

	/**
	 * Generates an array of Categories from a JSON String
	 * @param input JSON String containing multiple serialized Categories
	 * @return array of Categories
	 */
	public static Category[] fromJSONArray(String input) {
		final Gson parser = new Gson();
		return parser.fromJson(input, Category[].class);
	}

	/**
	 * Generates a single Category from a JSON String
	 * @param input JSON String representing one Category
	 * @return the resulting Category
	 */
	public static Category fromJSON(String input) {
		final Gson parser = new Gson();
		return parser.fromJson(input, Category.class);
	}

	/**
	 * Makes a fake copy of this. Should be used when constructing Events and Commitments with Categories
	 * to prevent duplicate returns from the database.
	 * @return A copy of this Category with isReal set to false
	 */
	public Category cloneFake() {
		Category cat = new Category(this.name, this.color, this.uniqueID);
		cat.isReal = false;
		return cat;
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof Category) {
			Category categoryOther = (Category)o;
			return this.getUniqueID() == categoryOther.getUniqueID();
		} else return false;
	}

	@Override
	public int hashCode() {
		return (int)this.getUniqueID();
	}
}
