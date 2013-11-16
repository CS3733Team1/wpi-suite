package edu.wpi.cs.wpisuitetng.modules.calendar.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class Filter extends DeletableAbstractModel {
	
	private ArrayList<Category> categories;
	
	public Filter(ArrayList<Category> categories)
	{
		this.categories.addAll(categories);
	}
	
	public void addCategory(Category cat)
	{
		categories.add(cat);
	}
	
	public void removeCategory(Category cat)
	{
		categories.remove(cat);
	}
	
	public ArrayList<Category> getCategories()
	{
		return categories;
	}
	
	public ArrayList<Event> apply(Event[] inlist)
	{
		ArrayList<Event> outlist = new ArrayList<Event>();
		
		for(Event event: inlist)
		{
			for(Category cat: this.categories)
			{
				if(event.getCategory().equals(cat))
				{
					outlist.add(event);
					break;
				}
			}
		}
		
		return outlist;
	}
	
	public ArrayList<Commitment> apply(Commitment[] inlist)
	{
		ArrayList<Commitment> outlist = new ArrayList<Commitment>();
		
		for(Commitment commitment: inlist)
		{
			for(Category cat: this.categories)
			{
				if(commitment.getCategory().equals(cat))
				{
					outlist.add(commitment);
					break;
				}
			}
		}
		
		return outlist;
	}
	
	@Override
	public void save() {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete() {
		this.markForDeletion();
	}

	@Override
	public String toJSON() {
		//name, dueDate, description, category
		String str = new Gson().toJson(this, Filter.class);
		return str;
	}
	
	public static Filter[] fromJSONArray(String input)
	{
		final Gson parser = new Gson();
		return parser.fromJson(input, Filter[].class);
	}
	
	public static Filter fromJSON(String input)
	{
		final Gson parser = new Gson();
		return parser.fromJson(input, Filter.class);
	}

	@Override
	public Boolean identify(Object o) {
		// TODO Auto-generated method stub
		return null;
	}

}
