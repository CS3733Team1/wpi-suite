package edu.wpi.cs.wpisuitetng.modules.calendar.view.help;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * This class provides a method to convert a valid, simple XML file to JSON format, or to a Tag class. This version of the parser
 * does not support attributes or empty-element tags.
 * 
 * External resources used were documentation of default Java APIs, the Wikipedia page on XML (http://en.wikipedia.org/wiki/XML),
 * and a guide to regexes (http://www.regular-expressions.info/lookaround.html).
 * 
 * @author trdesilva
 */
public class XMLParser
{
	private static XMLParser parser;

	public static XMLParser getParser()
	{
		if(parser != null)
		{
			return parser;
		}
		else
			return new XMLParser();
	}

	XMLParser()
	{
		parser = this;
	}

	String parseToJSON(String xml)
	{
		String[] tokens = xml.split("(?=<)|(?<=>)");
		String str = "";
		Deque<String> arrname = new ArrayDeque<String>();
		String output = "";

		//System.out.println(tokens.length);

		for(int i = 0; i < tokens.length; i++)
		{
			str = tokens[i];

			if(str.length() < 2 ||
					str.charAt(1) == '?' ||
					str.substring(1,4).equals("!--"))
				continue;

			if(str.charAt(0) == '<' && str.charAt(1) != '/')
			{
				output += "{\"" + str.substring(1, str.length() - 1) + "\":";
				if(tokens[i + 1].charAt(0) == '<')
				{
					output += "[";
					arrname.push(str.substring(1, str.length() - 1));
				}
			}
			else if(str.charAt(0) == '<' && str.charAt(1) == '/')
			{
				if(str.substring(2, str.length() - 1).equals(arrname.peek()))
				{
					output += "]";
					arrname.pop();
				}

				output += "}";

				if(i + 1 < tokens.length && tokens[i + 1].charAt(1) != '/')
				{
					output += ",";
				}

			}
			else
			{
				output += "\"" + str + "\"";
			}


			//System.out.println(str);
		}

		output.replace("&lt;", "<");
		output.replace("&gt;", ">");
		output.replace("&amp;", "&");

		return output;
	}

	Tag JSONToTag(String json)
	{
		Tag tag = new Tag();
		Deque<Tag> stack = new ArrayDeque<Tag>();
		String[] tokens;
		Tag top;

		stack.push(tag);

		while(!stack.isEmpty())
		{
			top = stack.peek();
			if(json.charAt(0) == '{')
			{
				tokens = json.substring(2).split("\"", 2);
				//System.out.println("first: " + tokens[0] + " rest: " + tokens[1]);

				top.name = tokens[0];
				json = tokens[1];

				if(json.charAt(0) == ':')
				{
					json = json.substring(1);
					if(json.charAt(0) == '\"')
					{
						json = json.substring(1);
						tokens = json.split("\"", 2);
						//System.out.println("first: " + tokens[0] + " rest: " + tokens[1]);
						stack.pop().setVal(tokens[0]);
						json = tokens[1];
						continue;
					}
					else if(json.charAt(0) == '[')
					{
						json = json.substring(1);
						top.children.add(new Tag());
						stack.push(top.children.get(top.children.size() - 1));
						continue;
					}
				}
			}
			else if(json.charAt(0) == '}')
			{
				json = json.substring(1);
				if(json.charAt(0) == ']')
				{
					stack.pop();
					json = json.substring(1);
					continue;
				}
				else if(json.charAt(0) == ',')
				{
					json = json.substring(1);
					top.children.add(new Tag());
					stack.push(top.children.get(top.children.size() - 1));
					continue;
				}

			}
		}

		return tag;
	}

	Tag parseToTag(String xml)
	{
		return JSONToTag(parseToJSON(xml));
	}

	Tag readTagFromFile(String file)
	{
		Scanner s;
		String input = "";
		String iter;
		try
		{
			s = new Scanner(new File(file));
		}
		catch (FileNotFoundException e)
		{
			System.err.println("File " + file + " not found");
			e.printStackTrace();
			return null;
		}

		while(true) {
			try {
				iter = s.nextLine();
			} catch(NoSuchElementException e) {
				break;
			}
			//System.out.println(iter);
			input += iter;
		}

		//input.replaceAll("\n", "");
		input.replaceAll("\t", "");

		s.close();
		return parseToTag(input);
	}

	String getHTMLSection(String html, String headName, boolean getTitle)
	{
		String output = "";
		String buffer = html;
		int ind, depth;

		ind = buffer.indexOf(">" + headName + "</h");
		depth = getHeaderDepth(buffer.substring(ind - 1, ind));
		buffer = buffer.substring(ind - 3);
		output += buffer.substring(0, 4 + headName.length() + 5);
		buffer = buffer.substring(4 + headName.length() + 5);

		while(buffer.contains("<h"))
		{
			ind = buffer.indexOf("<h");
			output += buffer.substring(0, ind);
			buffer = buffer.substring(ind);

			if(getHeaderDepth(buffer.substring(ind, ind + 4)) <= depth) //a header with the same or lower depth than this one can't belong to this one
			{
				if(getTitle)
				{
					output = wrapHTML(output, getHTMLTitle(html));
				}
				else
				{
					output = wrapHTML(output);
				}
				return output;
			}
			else
			{
				ind = buffer.indexOf("</h");
				output += buffer.substring(0, ind + 5);
				buffer = buffer.substring(ind + 5);
			}
		}
		//this header is the last one at its level in the file, so the rest of the file belongs to this header
		ind = buffer.indexOf("</body>");
		output += buffer.substring(0, ind);

		if(getTitle)
		{
			output = wrapHTML(output, getHTMLTitle(html));
		}
		else
		{
			output = wrapHTML(output);
		}

		return output;
	}

	int getHeaderDepth(String header)
	{
		if(header.length() == 4 && header.startsWith("<h") && header.endsWith(">"))
			return Integer.parseInt(header.substring(2,3));
		else
			return 0;
	}

	String getHTMLTitle(String html)
	{
		String output = "";

		if(html.contains("<title>"))
		{
			output = html.substring(html.indexOf("<title>") + 7, html.indexOf("</title>"));
		}

		return output;
	}

	String wrapHTML(String html)
	{
		return "<!DOCTYPE html><html><body>" + html + "</body></html>";
	}

	String wrapHTML(String html, String title)
	{
		return "<!DOCTYPE html><html><body><title>" + title + "</title>" + html + "</body></html>";
	}

	String unwrapHTML(String html, boolean keepTitle)
	{
		if(keepTitle && html.contains("<title>"))
		{
			return html.substring(html.indexOf("<title>"), html.indexOf("</body>"));
		}
		else if(html.contains("<title>"))
		{
			return html.substring(html.indexOf("</title>") + 8, html.indexOf("</body>"));
		}
		else
		{
			return html.substring(html.indexOf("<body>") + 6, html.indexOf("</body>"));
		}
	}
}
