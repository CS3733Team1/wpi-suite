package edu.wpi.cs.wpisuitetng.modules.calendar.view.help;

public class HTMLDocument
{
	String title;
	String text;
	
	HTMLDocument(String text)
	{
		this.title = "";
		this.text = text;
	}
	
	HTMLDocument(String text, String title)
	{
		this.title = title;
		this.text = text;
	}
	
	public String getText()
	{
		return this.text;
	}
	
	public String getTitle()
	{
		return this.title;
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
	
	/**
	 * Returns the title of the document. This method is for the document tree's use.
	 */
	public String toString()
	{
		return this.title;
	}
}
