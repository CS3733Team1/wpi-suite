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

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.calendar.view.MainView;

/**
 * This class represents Filters for Events and Commitments based on Category. A Filter has:<br>
 * -A name<br>
 * -An ArrayList of Categories to allow<br>
 * -A boolean that keeps track of whether this Filter is active
 */
public class Filter extends DeletableAbstractModel {

	public String name;
	private ArrayList<Category> categories;
	private boolean isSelected;

	public Filter(){}

	public Filter(String name) {
		this.name = name;
		this.categories = new ArrayList<Category>();
		this.isSelected = false;
	}

	public Filter(String name, long uid) {
		this.name = name;
		this.categories = new ArrayList<Category>();
		this.isSelected = false;
		this.setUniqueID(uid);
	}

	public Filter(String name, List<Category> list) {
		this(name);
		for(Category cat: list) {
			addCategory(cat);
		}
	}

	/**
	 * Adds a Category to the list of allowed Categories for this Filter.
	 * @param cat The Category to be added. The Category is cloned inside of this method, so it
	 * can be passed directly.
	 */

	public void addCategory(Category cat) {
		Category dupedCat = cat.cloneFake();
		categories.add(dupedCat);
	}

	public String getName() {return this.name;}

	/**
	 * Removes a Category from the list of allowed Categories for this Filter.
	 * @param cat The Category to be removed. The Category is cloned inside of this method, so it
	 * can be passed directly.
	 */

	public void removeCategory(Category cat) {
		categories.remove(cat.cloneFake());
	}


	/**
	 * @return An ArrayList of the allowed Categories for this Filter.
	 */
	public ArrayList<Category> getCategories() {
		return categories;
	}

	/**
	 * Applies this Filter to a List of Events.
	 * @param inlist The List of Events to be filtered
	 * @return An ArrayList of Events with Categories that belong to this Filter's whitelist
	 */
	public List<Event> applyEventFilter(List<Event> inlist) {
		List<Event> outlist = new ArrayList<Event>();
		for(Event event: inlist) {
			for(Category cat: this.categories) {
				if(event.getCategory().equals(cat)) {
					outlist.add(event);
					break;
				}
			}
		}

		return filterTeamPersonal(outlist);
	}

	/**
	 * Applies this Filter to a List of Commitments.
	 * @param inlist The List of Commitments to be filtered
	 * @return An ArrayList of Commitments with Categories that belong to this Filter's whitelist
	 */
	public List<Commitment> applyCommitmentFilter(List<Commitment> inlist) {
		List<Commitment> outlist = new ArrayList<Commitment>();

		for(Commitment commitment: inlist) {
			for(Category cat: this.categories) {
				if(commitment.getCategory().equals(cat)) {
					outlist.add(commitment);
					break;
				}
			}
		}

		return filterTeamPersonal(outlist);
	}

	//This method attempts to filter a list of things based on
	// the status of the Team/Personal calendar checkbox's
	public static  <T extends DeletableAbstractModel> List<T> filterTeamPersonal(List<T> list) {
		List<T> removeList = new ArrayList<T>();
		if (MainView.getCurrentCalendarPanel() == null) {
			System.out.println("Current Calendar Panel null, not filtering based on Team/Personal");
			return list;
		}
		int state = -1;
		state = MainView.getCurrentCalendarPanel().getCalendarTabPanel().getTeamPersonalState();

		//3 = Both Checked
		//2 = Personal
		//1 = Team
		//0 = none

		switch (state) {
		default:
		case 3://both
			return list;
		case 2: //personal events
			for (T i : list) {
				if (i.isTeam)
					removeList.add(i);
			}
			break;
		case 1: //Team Events only
			for (T i : list) {
				if (!i.isTeam)
					removeList.add(i);
			}
			break;
		case 0: //none
			list.clear();
			return list;
		}
		list.removeAll(removeList);
		return list;
	}

	@Override
	public String toJSON() {
		//name, dueDate, description, category
		String str = new Gson().toJson(this, Filter.class);
		return str;
	}

	public static Filter[] fromJSONArray(String input) {
		final Gson parser = new Gson();
		return parser.fromJson(input, Filter[].class);
	}

	public static Filter fromJSON(String input) {
		final Gson parser = new Gson();
		return parser.fromJson(input, Filter.class);
	}

	public void setSelected(boolean b) {
		this.isSelected = b;
	}

	public boolean getSelected() {
		return this.isSelected;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (o == this)
			return true;
		if (!(o instanceof Filter))
			return false;

		Filter filterOther = (Filter)o;
		return this.getUniqueID() == filterOther.getUniqueID();
	}

	@Override
	public int hashCode() {
		return (int)this.getUniqueID();
	}
}
