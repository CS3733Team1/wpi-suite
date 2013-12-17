package edu.wpi.cs.wpisuitetng.modules.calendar.view.help;
import java.awt.Dimension;
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
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.TabSet;
import javax.swing.text.TabStop;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import net.miginfocom.swing.MigLayout;

/**
 * This class is the GUI for the ManReader. It handles display of the interface and handles user I/O operations.
 * 
 * External resources used include MiGLayout, Java Swing tutorials, and StackOverflow threads where noted.
 * @author trdesilva
 */
@SuppressWarnings("serial")
public class HelpWindow extends JPanel implements ActionListener, MouseListener, FocusListener, TreeSelectionListener, ItemListener, KeyListener
{
	JTextPane display;
	JScrollPane displayScroll;
	JTextField search;
	JTree docMenu;
	JScrollPane docMenuPane;
	JButton bGo, bBack, bForward, bHome;
	
	JPanel settings;
	JTextField filter;
	JPanel checkPanel;
	JCheckBox bTopic, bSummary, bDetail;
	JButton bClear;
	
	ArrayList<HTMLDocument> docs;
	String include = "";
	String exclude = "";
	boolean lods[] = {true, true, true};
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
//		display.setMinimumSize(new Dimension(258, 543));
		display.setEditable(false);
		StyleContext sc = StyleContext.getDefaultStyleContext();
		TabSet tabs = new TabSet(new TabStop[] {new TabStop(20), new TabStop(20)}); //http://stackoverflow.com/questions/757692/how-do-you-set-the-tab-size-in-a-jeditorpane
		AttributeSet paraSet = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.TabSet, tabs);
		display.setParagraphAttributes(paraSet, false);
		
		displayScroll = new JScrollPane(display);
//		displayScroll.setMinimumSize(new Dimension(260, 545));
		displayScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		displayScroll.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		displayScroll.setVisible(true);
		
		search = new JTextField();
//		search.setMinimumSize(new Dimension(260, 25));
		search.setText("Search...");
		search.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		search.addFocusListener(this);
		search.addKeyListener(this);
		
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Documents");
		docMenu = new JTree(top);
//		docMenu.setMinimumSize(new Dimension(120, 500));
		docMenu.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		docMenu.setOpaque(true);
		docMenu.setShowsRootHandles(true);
		docMenu.addTreeSelectionListener(this);
		
		docMenuPane = new JScrollPane(docMenu);
//		docMenuPane.setMinimumSize(new Dimension(120, 580));
		docMenuPane.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		docMenuPane.setVisible(true);
		
		bGo = new JButton("Go");
//		bGo.setMinimumSize(new Dimension(50,25));
		bGo.setToolTipText("Go");
		bGo.addMouseListener(this);
		bBack = new JButton("<");
//		bBack.setMinimumSize(new Dimension(50,25));
		bBack.setToolTipText("Back");
		bBack.addMouseListener(this);
		bForward = new JButton(">");
//		bForward.setMinimumSize(new Dimension(50,25));
		bForward.setToolTipText("Forward");
		bForward.addMouseListener(this);
		bHome = new JButton("Home");
//		bHome.setMinimumSize(new Dimension(110,25));
		bHome.setToolTipText("Home");
		bHome.addMouseListener(this);
		
		this.setLayout(new MigLayout("fill", "[25%][51%][12%][12%]","[4%][4%][4%][88%]"));
		this.setMinimumSize(new Dimension(530, 600));
		//this.setPreferredSize(new Dimension(530, 600));
		
		this.add(displayScroll, "cell 1 1 1 3, grow");
		this.add(search, "cell 1 0, grow");
		this.add(docMenuPane, "cell 0 0 1 4, grow");
		this.add(bGo, "cell 2 0 2 1, grow");
		this.add(bBack, "cell 2 1, grow");
		this.add(bForward, "cell 3 1, grow");
		this.add(bHome, "cell 2 2 2 1, grow");
		
		//set up settings panel
		settings = new JPanel();
		settings.setLayout(new MigLayout("fill", "[100%]", "[14%][58%][14%][14%]"));
		settings.setMinimumSize(new Dimension(180, 220));
		settings.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		
		filter = new JTextField();
		filter.setMinimumSize(new Dimension(160, 25));
		filter.setText("Exclude...");
		filter.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		filter.addFocusListener(this);
		filter.addKeyListener(this);
		
		checkPanel = new JPanel();
		checkPanel.setLayout(new MigLayout("fill", "[100%]", "[33%][33%][33%]"));
		checkPanel.setMinimumSize(new Dimension(160, 75));
		checkPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		checkPanel.setVisible(true);
		
		bTopic = new JCheckBox();
		bTopic.setText("Topics");
		bTopic.setSelected(true);
		bTopic.setMinimumSize(new Dimension(110, 25));
		bTopic.addItemListener(this);
		bSummary = new JCheckBox();
		bSummary.setText("Summaries");
		bSummary.setSelected(true);
		bSummary.setMinimumSize(new Dimension(110, 25));
		bSummary.addItemListener(this);
		bDetail = new JCheckBox();
		bDetail.setText("Details");
		bDetail.setSelected(true);
		bDetail.setMinimumSize(new Dimension(110, 25));
		bDetail.addItemListener(this);
		
		checkPanel.add(bTopic, "cell 0 0, grow");
		checkPanel.add(bSummary, "cell 0 1, grow");
		checkPanel.add(bDetail, "cell 0 2, grow");
		
		bClear = new JButton("Clear");
		bClear.setMinimumSize(new Dimension(75,25));
		bClear.setToolTipText("Cancel filtering and clear fields");
		bClear.addMouseListener(this);
		
		settings.add(new JLabel("Show only..."), "cell 0 0, grow");
		settings.add(checkPanel, "cell 0 1, grow");
		settings.add(filter, "cell 0 2, grow");
		settings.add(bClear, "cell 0 3, grow");
		
		settings.setVisible(true);
		
		this.add(settings, "cell 2 3 2 1, grow");
		
		//set up backend
		docs = new ArrayList<HTMLDocument>();
		
		parser = XMLParser.getParser();
		
		findDocs();
		docs.add(new HTMLDocument("<html><body><h1>Welcome</h1><p>placeholder</p></body></html>", "Welcome"));
		display.setEditorKit(new HTMLEditorKit());
		if(docs.size() > 0)
		{
			display.setText(findTopLevelHTMLDocument("Welcome").getText());
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
	
	public static void go()
	{
		JFrame frame = new JFrame("ManReader");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		HelpWindow window = new HelpWindow();
		window.setOpaque(true);
		
		frame.setContentPane(window);
		frame.setMinimumSize(new Dimension(530, 600));
		frame.pack();
		frame.setVisible(true);
	}
	
	/**
	 * Adds an HTMLDocument from the specified XML file to the list of documents that can be displayed.
	 * @param file The filepath of an XML file to add
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
				System.out.println(s);
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
	String filterDocs(String include, String exclude, boolean[] lods)
	{
		String results = "";
		
		if(lods.length != 3)
		{
			System.err.println("not enough arguments to filterDocs, expected boolean[3]");
			return null;
		}
		
		for(HTMLDocument doc: docs)
		{
			if((doc.getText().contains(include) || include.equals(""))
			&& (lods[0] || lods[1] || lods[2])
			&& !doc.getTitle().contains("Welcome")
			&& (!doc.getText().contains(exclude) || exclude.equals(""))) //BUGBUG this is wrong, fix this
			{
				results += "<h1><u>" + doc.getTitle() + "</u></h1>";
				results += parser.unwrapHTML(doc.getText(), false) + "<br>";
			}
			else
			{
				continue;
			}
			/*
			if(lods[0]) //show topic-level-of-detail results
			{
				if((t.getChildHTMLDocument("Topic").findText(include) || include.equals("")) && !t.getChildHTMLDocument("Topic").findText(exclude))
					results += t.getChildHTMLDocument("Topic").sprintHTMLDocument();
			}
			
			if(lods[1]) //show summaries
			{
				if((t.getChildHTMLDocument("Summary").findText(include) || include.equals("")) && !t.getChildHTMLDocument("Summary").findText(exclude))
					results += t.getChildHTMLDocument("Summary").sprintHTMLDocument();
			}
			
			if(lods[2]) //show details
			{
				if((t.getChildHTMLDocument("Detail").findText(include) || include.equals("")) && !t.getChildHTMLDocument("Detail").findText(exclude))
					results += t.getChildHTMLDocument("Detail").sprintHTMLDocument();
			}
			*/
			results += "<br>";
		}
		
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
			/*
			for(HTMLDocument child: t.children)
			{
				node2 = new DefaultMutableTreeNode(child);
				node.add(node2);
			}
			*/
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
				updateBacklist(doc);
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
			
			display.setText(filterDocs(include, exclude, lods));
			
			if(display.getText().equals(""))
				display.setText("No matches found");
		}
		
		if(e.getSource().equals(bBack))
		{
			if(backlistCurrent > 0)
			{
				backlistCurrent--;
				display.setText(backlist.get(backlistCurrent).getText());
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
			
			bBack.setEnabled(false);
			bForward.setEnabled(false);
		}
		
		if(e.getSource().equals(bClear))
		{
			lods[0] = true;
			lods[1] = true;
			lods[2] = true;
			
			bTopic.setSelected(true);
			bSummary.setSelected(true);
			bDetail.setSelected(true);
			
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
	public void itemStateChanged(ItemEvent e)
	{
		if(e.getSource().equals(bTopic))
		{
			if(e.getStateChange() == ItemEvent.DESELECTED)
			{
				lods[0] = false;
			}
			else
			{
				lods[0] = true;
			}
		}
		
		if(e.getSource().equals(bSummary))
		{
			if(e.getStateChange() == ItemEvent.DESELECTED)
			{
				lods[1] = false;
			}
			else
			{
				lods[1] = true;
			}
		}
		
		if(e.getSource().equals(bDetail))
		{
			if(e.getStateChange() == ItemEvent.DESELECTED)
			{
				lods[2] = false;
			}
			else
			{
				lods[2] = true;
			}
		}
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
			
			display.setText(filterDocs(include, exclude, lods));
			
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
