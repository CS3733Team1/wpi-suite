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

public class Category extends DeletableAbstractModel {
	private String name;
	private Color color;
	
	public Category(){}
	
	public Category(String name, Color color) {
		this.name = name;
		this.color = color;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Color getColor() {
		return color;
	}

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
		this.markForDeletion();
	}
	@Override
	public String toJSON() {
		//name, dueDate, description, category
		String str = new Gson().toJson(this, Category.class);
		return str;
	}
	
	public static Category[] fromJSONArray(String input)
	{
		final Gson parser = new Gson();
		return parser.fromJson(input, Category[].class);
	}
	
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
}
