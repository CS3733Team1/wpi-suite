package edu.wpi.cs.wpisuitetng.modules.calendar.view.help;
import java.util.ArrayList;

/**
 * This class represents XML files. It provides methods for traversing their structure, as well as displaying them in exclusively
 * human-readable format.
 * @author trdesilva
 */
public class Tag
{
	public String name;
	public ArrayList<Tag> children;
	
	private String val;
	
	Tag()
	{
		this.name = null;
		this.val = null;
		
		children = new ArrayList<Tag>();
	}
	
	Tag(String name)
	{
		this.name = name;
		this.val = null;
		
		children = new ArrayList<Tag>();
	}
	
	Tag(String name, String val)
	{
		this.name = name;
		this.val = val;
		
		children = new ArrayList<Tag>();
	}
	
	public Tag setVal(String val)
	{
		this.val = val;
		return this;
	}
	
	public String getVal()
	{
		return val;
	}
	
	/**
	 * Finds a Tag in this Tag's children that has the given name.
	 * @param name Name to search for
	 * @return The first child Tag with the given name, or null if no child has that name
	 */
	public Tag getChildTag(String name)
	{
		for(Tag t: children)
		{
			if(t.name.equals(name))
				return t;
		}
		
		return null;
	}
	
	/**
	 * Recursively searches all child Tags for a Tag with the given name.
	 * @param name Name to search for
	 * @return The first child Tag with the given name, or null if no child has that name
	 */
	public Tag getDeepChildTag(String name)
	{
		for(Tag t: children)
		{
			if(t.name.equals(name))
				return t;
			else if(t.getDeepChildTag(name) != null)
				return t.getDeepChildTag(name);
		}
		
		return null;
	}
	
	/**
	 * Checks if this Tag or any children contain the given text.
	 * @param in Text to search for
	 * @return <b>true</b> if the given text exists in the names or values of any Tag in this Tag's hierarchy; <b>false</b> otherwise
	 */
	public boolean findText(String in)
	{
		if(!in.equals("") && in != null)
			return this.sprintTag().contains(in);
		else
			return false;
	}
	
	/**
	 * Recursively prints this Tag and all child Tags to a String, adding an additional level of tabification for each.
	 * @return A human-readable String representing this Tag
	 */
	public String sprintTag()
	{
		String str = this.name + "\n\t";
		for(Tag t: this.children)
		{
			str += t.sprintTag(1);
			str += "\t";
		}
		
		if(this.val != null)
		{
			str += this.val + "\n";
		}
		
		return str;
	}
	
	public String sprintTag(int tabs)
	{
		String str = this.name + "\n";
		for(int i = 0; i <= tabs; i++)
		{
			str += "\t";
		}
		for(Tag t: this.children)
		{
			str += t.sprintTag(tabs + 1);
			for(int i = 0; i <= tabs; i++)
			{
				str += "\t";
			}
		}
		
		if(this.val != null)
		{
			str += this.val + "\n";
		}
		else
		{
			str += "\n";
		}
		
		return str;
	}
	
	/**
	 * Returns the name of the Tag. This method is for the JTree in MainWindow to use.
	 */
	public String toString()
	{
		return this.name;
	}
}
