package edu.wpi.cs.wpisuitetng.modules.calendar.view.help;

import java.util.ArrayList;

public class HTMLDocument
{
	String title;
	String text;
	ArrayList<String> childNames;
	
	HTMLDocument(String text)
	{
		this.title = XMLParser.getParser().getHTMLTitle(text);
		this.text = text;
		text.replace("\n", "");
		text.replace("\t", "");
		this.childNames = new ArrayList<String>();
		setChildNames();
		System.out.println(this.text);
	}
	
	HTMLDocument(String text, String title)
	{
		this.title = title;
		this.text = text;
		text.replace("\n", "");
		text.replace("\t", "");
		this.childNames = new ArrayList<String>();
		setChildNames();
		System.out.println(this.text);
	}
	
	public String getText()
	{
		return this.text;
	}
	
	public String getTitle()
	{
		return this.title;
	}
	
	public ArrayList<String> getChildNames()
	{
		return this.childNames;
	}
	
	public HTMLDocument setText(String text)
	{
		this.text = text;
		return this;
	}
	
	/**
	 * Sets the title according to the title in this.text.
	 * @return <b>this</b> after setting the new title
	 */
	public HTMLDocument setTitle()
	{
		this.title = XMLParser.getParser().getHTMLTitle(this.text);
		return this;
	}
	
	private void setChildNames()
	{
		String buffer = text;
		int start, end;
		
		while(buffer.contains("<h1>"))
		{
			start = buffer.indexOf("<h1>") + 4;
			end = buffer.indexOf("</h1>");
			
			childNames.add(buffer.substring(start, end));
			buffer = buffer.substring(end + 5);
		}
		
		
	}
	
	/**
	 * Returns the title of the document. This method is for the document tree's use.
	 */
	public String toString()
	{
		return this.title;
	}
}
