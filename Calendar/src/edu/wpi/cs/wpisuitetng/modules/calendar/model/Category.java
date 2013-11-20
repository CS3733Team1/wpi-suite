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
 * Model that holds data for a category
 */
public class Category extends DeletableAbstractModel {
	private String name;
	private Color color;
	
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
	}
	
	/**
	 * @return the name of the Category
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name of the Category
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
	 * Generates an array of Categories from a json String
	 * @param input json String representing an array of Categories
	 * @return array of Categories built from json String
	 */
	public static Category[] fromJSONArray(String input)
	{
		final Gson parser = new Gson();
		return parser.fromJson(input, Category[].class);
	}
	
	/**
	 * Generates a Category from a json String
	 * @param input json String representing a Category
	 * @return Category built from json String
	 */
	public static Category fromJSON(String input)
	{
		final Gson parser = new Gson();
		return parser.fromJson(input, Category.class);
	}
	
	@Override
	public Boolean identify(Object o) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Category) {
			Category c = (Category) o;
			return (c.getColor().equals(this.getColor()) && c.getName().equals(this.getName()));
		} else return false;
	}
}
