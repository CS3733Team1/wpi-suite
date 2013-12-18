package edu.wpi.cs.wpisuitetng.modules.calendar.view.help;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.border.EtchedBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.TabSet;
import javax.swing.text.TabStop;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import net.miginfocom.swing.MigLayout;
import java.awt.Cursor;

/**
 * This class is the GUI for the ManReader. It handles display of the interface and handles user I/O operations.
 * 
 * External resources used include MiGLayout, Java Swing tutorials, and StackOverflow threads where noted.
 * @author trdesilva
 */
@SuppressWarnings("serial")
public class HelpWindow extends JPanel implements ActionListener, MouseListener, FocusListener, TreeSelectionListener, KeyListener
{
	JTextPane display;
	JScrollPane displayScroll;
	JTextField search;
	JTree docMenu;
	JScrollPane docMenuPane;
	JButton bGo, bBack, bForward, bHome;
	
	JPanel settings;
	JTextField filter;
	JButton bClear;
	
	ArrayList<HTMLDocument> docs;
	String include = "";
	String exclude = "";
	ArrayList<HTMLDocument> backlist;
	int backlistCurrent;
	XMLParser parser;

	/**
	 * Creates all of the GUI components and sets up their action listeners.
	 */
	public HelpWindow()
	{
		//set up main panel
		display = new JTextPane();
		display.setEditable(false);
		StyleContext sc = StyleContext.getDefaultStyleContext();
		TabSet tabs = new TabSet(new TabStop[] {new TabStop(20), new TabStop(20)}); //http://stackoverflow.com/questions/757692/how-do-you-set-the-tab-size-in-a-jeditorpane
		AttributeSet paraSet = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.TabSet, tabs);
		display.setParagraphAttributes(paraSet, false);
		
		displayScroll = new JScrollPane(display);
		//displayScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		displayScroll.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		displayScroll.setVisible(true);
		
		search = new JTextField();
		search.setText("Search...");
		search.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		search.addFocusListener(this);
		search.addKeyListener(this);
		
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Documents");
		docMenu = new JTree(top);
		docMenu.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		docMenu.setOpaque(true);
		docMenu.setShowsRootHandles(true);
		docMenu.addTreeSelectionListener(this);
		
		docMenuPane = new JScrollPane(docMenu);
		docMenuPane.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		docMenuPane.setVisible(true);
		
		bGo = new JButton("Go");
		bGo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		bGo.setToolTipText("Go");
		bGo.addMouseListener(this);
		bBack = new JButton("<");
		bBack.setToolTipText("Back");
		bBack.addMouseListener(this);
		bForward = new JButton(">");
		bForward.setToolTipText("Forward");
		bForward.addMouseListener(this);
		bHome = new JButton("Home");
		bHome.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		bHome.setToolTipText("Home");
		bHome.addMouseListener(this);
		
		this.setLayout(new MigLayout("fill", "[25%][51%][12%][12%]","[4%][4%][4%][88%]"));
		this.setMinimumSize(new Dimension(530, 600));
		
		this.add(displayScroll, "cell 1 1 1 3, grow");
		this.add(search, "cell 1 0, grow");
		this.add(docMenuPane, "cell 0 0 1 4, grow");
		this.add(bGo, "cell 2 0 2 1, grow");
		this.add(bBack, "cell 2 1, grow");
		this.add(bForward, "cell 3 1, grow");
		this.add(bHome, "cell 2 2 2 1, grow");
		
		//set up settings panel
		settings = new JPanel();
		settings.setLayout(new MigLayout("fill", "[100%]", "[14%][14%][72%]"));
		settings.setMinimumSize(new Dimension(180, 220));
		settings.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		
		filter = new JTextField();
		filter.setMinimumSize(new Dimension(160, 25));
		filter.setText("Exclude...");
		filter.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		filter.addFocusListener(this);
		filter.addKeyListener(this);
		
		bClear = new JButton("Clear");
		bClear.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		bClear.setMinimumSize(new Dimension(75,25));
		bClear.setToolTipText("Cancel filtering and clear fields");
		bClear.addMouseListener(this);
		
		settings.add(filter, "cell 0 0, grow");
		settings.add(bClear, "cell 0 1 1 1, grow");
		
		settings.setVisible(true);
		
		this.add(settings, "cell 2 3 2 1, grow");
		
		//set up backend
		docs = new ArrayList<HTMLDocument>();
		
		parser = XMLParser.getParser();
		
		findDocs();
		display.setEditorKit(new HTMLEditorKit());
		if(docs.size() > 0)
		{
			display.setText(findTopLevelHTMLDocument("Welcome").getText());
			display.setCaretPosition(0);
		}
		
		backlist = new ArrayList<HTMLDocument>();
		backlistCurrent = 0;
		backlist.add(docs.get(0));
		bBack.setEnabled(false);
		bForward.setEnabled(false);
		
		addDocsToTree(top);
		docMenu.setSelectionRow(1);
		docMenu.setExpandsSelectedPaths(true);
		
		this.setVisible(true);
	}
	
	/**
	 * Adds an HTMLDocument from the specified XML file to the list of documents that can be displayed.
	 * @param file The filepath of an HTML file to add
	 * @return The resulting HTMLDocument
	 */
	String addDoc(String file)
	{
		String absname = new File(new File("").getAbsoluteFile().getParent() + "/Calendar/help").getAbsoluteFile().getAbsolutePath();
		String html = fixHTMLImages(readHTML(absname + "/" + file));
		docs.add(new HTMLDocument(html, parser.getHTMLTitle(html)));
		return html;
	}
	
	/**
	 * Adds all HTML files in the current working directory to the list of documents.
	 */
	void findDocs()
	{
		System.out.println("findDocs()");
		File file = new File(new File("").getAbsoluteFile().getParent() + "/Calendar/help");
		for(String s: file.list())
		{
			System.out.println(s);
			if(s.length() > 5 && s.endsWith(".html"))
			{
				System.out.println("yes");
				addDoc(new File(s).getPath());
			}
		}
	}
	
	String readHTML(String path)
	{
		String output = "";
		File file = new File(path);
		Scanner s;
		try {
			s = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.err.println("file " + file.toString() + " not found");
			e.printStackTrace();
			return null;
		}
		
		while(s.hasNextLine())
		{
			output += s.nextLine();
		}
		
		s.close();
		return output;
	}
	
	String fixHTMLImages(String html)
	{
		String output = "";
		String buffer = html;
		String temp = "";
		String parent;
		int ind;
		ArrayList<String> imageNames = new ArrayList<String>();
		
		if(!html.contains("<img src=\""))
			return html;
		
		File file = new File(new File("").getAbsoluteFile().getParent() + "/Calendar/help");
		for(String s: file.list())
		{
			if(s.length() > 4 && s.substring(s.length() - 4).equals(".png")
			|| s.substring(s.length() - 4).equals(".jpg"))
			{
				imageNames.add(s);
			}
		}
		
		parent = file.getAbsolutePath();
		
		while(buffer.contains("<img src=\""))
		{
			ind = buffer.indexOf("<img src=\"");
			temp = buffer.substring(ind + 10, buffer.indexOf("\" ", ind));
			if(imageNames.contains(temp))
			{
				output += buffer.substring(0, ind + 10);
				output += "file:///" + parent + "/" + temp;
				output += buffer.substring(ind + 10 + temp.length(), ind + 10 + temp.length() + 2);
			}
			else
			{
				System.err.println("image file " + temp + " not found");
				output += buffer.substring(0, ind + 10 + temp.length() + 2);
			}
			
			buffer = buffer.substring(ind + 10 + temp.length() + 2);
		}
		
		output += buffer;
		//output.replaceAll("<img src=\"(.*)\" alt=\".*\" width=\"24\" height=\"24\">", "img src=\"" + new File("").getAbsoluteFile().getParent() + "/Calendar/help/.*png\"");
		return output;
	}
	
	/**
	 * Gives a String containing the printed versions of HTMLDocuments which fit the given search criteria.
	 * @param include A String that the HTMLDocuments must contain
	 * @param exclude A String that the HTMLDocuments cannot contain
	 * @param lods A 3-tuple that says which levels of detail to search
	 * @return Topic, Summary, and Detail HTMLDocuments which fit the criteria
	 */
	String filterDocs(String include, String exclude)
	{
		String results = "";
		String text;
		include = include.toLowerCase();
		exclude = exclude.toLowerCase();
		boolean found = false;
		
		for(HTMLDocument doc: docs)
		{
			text = doc.getText().toLowerCase();
			if((text.contains(include) && !include.equals(""))
			&& !doc.getTitle().contains("Welcome")
			&& (!text.contains(exclude) || exclude.equals("")))
			{
				results += "<h1><u>" + doc.getTitle() + "</u></h1>";
				results += parser.unwrapHTML(doc.getText(), false) + "<br>";
				found = true;
			}
			else
			{
				continue;
			}
			
			results += "<br>";
		}
		
		if(!found)
			results = "No results found.";
		
		return parser.wrapHTML(results, "Search Results");
	}
	
	/**
	 * Adds all documents in the list of documents to the selection menu.
	 * @param top The top node of the menu tree
	 */
	void addDocsToTree(DefaultMutableTreeNode top)
	{
		
		DefaultMutableTreeNode node, node2;
		
		for(HTMLDocument doc: docs)
		{
			node = new DefaultMutableTreeNode(doc.getTitle());
			
			for(String child: doc.getChildNames())
			{
				node2 = new DefaultMutableTreeNode(child);
				node.add(node2);
			}
			
			top.add(node);
		}
		
	}
	
	/**
	 * Updates the list of previously-visited documents.
	 * @param t A new document to add to the list
	 */
	void updateBacklist(HTMLDocument t)
	{
		if(!t.equals(backlist.get(backlistCurrent)))
		{
			if(backlistCurrent == backlist.size() - 1)
			{
				if(backlist.size() == 10)
				{
					backlist.remove(0);
				}
				backlist.add(t);
			}
			else
			{
				while(backlistCurrent != backlist.size() - 1)
				{
					backlist.remove(backlistCurrent + 1);
				}
				 backlist.add(t);
			}
			
			backlistCurrent = backlist.size() - 1;
			
			if(backlistCurrent == 0)
			{
				bBack.setEnabled(false);
			}
			else
			{
				bBack.setEnabled(true);
			}
			
			bForward.setEnabled(false); //update clears all forwards
		}
	}
	
	/**
	 * Finds a HTMLDocument in the top level of the list of documents with the given name.
	 * @param name The name to search for
	 * @return The first HTMLDocument in the top level of the tree with the given name
	 */
	HTMLDocument findTopLevelHTMLDocument(String name)
	{
		
		for(HTMLDocument doc: docs)
		{
			if(doc.getTitle().equals(name))
				return doc;
		}
		
		return null;
	}
	
	void scrollToHeading(String heading)
	{
		int prevPos = display.getCaretPosition();
		int newPos;
		int bottom;
		Rectangle textRect, visibleRect;
		String text = display.getText();
		String temp = "";
		
		//System.out.println("Before regex\n" + text);
		text = text.replaceAll("\\s*(</?[^/bui].*?>)\\s*", "$1");
		text = text.replaceAll(" +|\\n|\\r", " ");
		text = text.trim();
		//System.out.println("After regex\n" + text);
		newPos = text.indexOf("<h1>" + heading + "</h1>");
		
		if(text.contains("<title>"))
		{
			text = text.substring(0, text.indexOf("<title>")) + text.substring(text.indexOf("</title>") + 8);
			newPos = text.indexOf("<h1>" + heading + "</h1>");
		}

		//find all the tags and take them out because caret position is based on rendered text, not raw HTML
		for(int i = 0; i < newPos; i = text.indexOf("<"))
		{
			temp = text.substring(i, text.indexOf(">", i) + 1);
			temp = temp.replace("\\", "\\\\");
			if(temp.equals("<br>"))
				text = text.replaceFirst(temp, "\n");
			else
				text = text.replaceFirst(temp, "");
			
			newPos -= temp.length();
			//System.out.println("Text: " + text.substring(Math.max(0, newPos - 4000), Math.min(text.length(), newPos + 5000)) + "\nPos: " + newPos);
		}

		System.out.println(heading + " at " + newPos);
		
		if(newPos != -1)
		{
			display.setCaretPosition(Math.min(display.getDocument().getLength(), newPos));
		}
		
		try {
			textRect = display.modelToView(newPos);
		} catch (BadLocationException e) {
			textRect = display.getVisibleRect();
			System.err.println("bad rectangle pos");
		}
		visibleRect = display.getVisibleRect();
		textRect.setSize(visibleRect.getSize());
		
		display.scrollRectToVisible(textRect);
		displayScroll.getHorizontalScrollBar().setValue(0);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void focusGained(FocusEvent e)
	{
		if(e.getSource().equals(search))
		{
			if(search.getText().equals("Search..."))
			{
				search.setText("");
			}
		}
		
		if(e.getSource().equals(filter))
		{
			if(filter.getText().equals("Exclude..."))
			{
				filter.setText("");
			}
		}
	}

	@Override
	public void focusLost(FocusEvent e)
	{
		if(e.getSource().equals(search))
		{
			if(search.getText().equals(""))
			{
				search.setText("Search...");
			}
		}
		
		if(e.getSource().equals(filter))
		{
			if(filter.getText().equals(""))
			{
				filter.setText("Exclude...");
			}
		}
	}

	@Override
	public void valueChanged(TreeSelectionEvent e)
	{
		System.out.println("selection changed");
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
		
		if(node == null)
			return;
		if(node.getUserObject() instanceof String)
		{
			System.out.println((String) node.getUserObject());
			if(findTopLevelHTMLDocument((String) node.getUserObject()) != null)
			{
				HTMLDocument doc = findTopLevelHTMLDocument((String) node.getUserObject());
				System.out.println(doc.getTitle() + " selected");
				display.setText(doc.getText());
				display.setCaretPosition(0);
				updateBacklist(doc);
			}
			else if(!node.getUserObject().equals("Documents"))
			{
				HTMLDocument parent = findTopLevelHTMLDocument((String) ((DefaultMutableTreeNode) node.getParent()).getUserObject());
				display.setText(parent.getText());
				scrollToHeading((String) node.getUserObject());
				updateBacklist(parent);
				
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		if(e.getSource().equals(bGo))
		{
			if(!search.getText().equals("Search..."))
				include = search.getText();
			else
				include = "";
			
			if(!filter.getText().equals("Exclude..."))
				exclude = filter.getText();
			else
				exclude = "";
			
			display.setText(filterDocs(include, exclude));
			display.setCaretPosition(0);
			
			if(display.getText().equals(""))
				display.setText("No matches found");
		}
		
		if(e.getSource().equals(bBack))
		{
			if(backlistCurrent > 0)
			{
				backlistCurrent--;
				display.setText(backlist.get(backlistCurrent).getText());
				display.setCaretPosition(0);
				bForward.setEnabled(true);
				if(backlistCurrent == 0)
					bBack.setEnabled(false);
			}
		}
		
		if(e.getSource().equals(bForward))
		{
			if(backlistCurrent < backlist.size() - 1)
			{
				backlistCurrent++;
				display.setText(backlist.get(backlistCurrent).getText());
				display.setCaretPosition(0);
				bBack.setEnabled(true);
				if(backlistCurrent == backlist.size() - 1)
					bForward.setEnabled(false);
			}
		}
		
		if(e.getSource().equals(bHome))
		{
			backlist.clear();
			backlist.add(findTopLevelHTMLDocument("Welcome"));
			backlistCurrent = 0;
			
			display.setText(backlist.get(0).getText());
			display.setCaretPosition(0);
			
			bBack.setEnabled(false);
			bForward.setEnabled(false);
		}
		
		if(e.getSource().equals(bClear))
		{			
			filter.setText("Exclude...");
			exclude = "";
			search.setText("Search...");
			include = "";
			
			exclude = "";
		}
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
		{			
			if(!search.getText().equals("Search..."))
				include = search.getText();
			else
				include = "";
			
			if(!filter.getText().equals("Exclude..."))
				exclude = filter.getText();
			else
				exclude = "";
			
			display.setText(filterDocs(include, exclude));
			display.setCaretPosition(0);
			
			if(display.getText().equals(""))
				display.setText("No matches found");
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
