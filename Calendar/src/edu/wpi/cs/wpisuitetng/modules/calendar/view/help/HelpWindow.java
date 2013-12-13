package edu.wpi.cs.wpisuitetng.modules.calendar.view.help;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EtchedBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.TabSet;
import javax.swing.text.TabStop;
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
public class HelpWindow extends JPanel implements ActionListener, MouseListener, FocusListener, TreeSelectionListener, ItemListener
{
	JTextPane display;
	JScrollPane displayScroll;
	JTextPane search;
	JTree docMenu;
	JScrollPane docMenuPane;
	JButton bGo, bSettings, bBack, bForward, bHome;
	
	JPopupMenu settings;
	JTextPane filter;
	JPanel checkPanel;
	JCheckBox bTopic, bSummary, bDetail;
	JButton bApply, bCancel;
	
	ArrayList<Tag> docs;
	String include = "";
	String exclude = "";
	boolean lods[] = {true, true, true};
	ArrayList<Tag> backlist;
	int backlistCurrent;
	XMLParser parser;
	
	/*
	public static void main(String[] args)
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                go();
            }
        });
	}
	*/
	/**
	 * Creates all of the GUI components and sets up their action listeners.
	 */
	public HelpWindow()
	{
		//set up main panel
		display = new JTextPane();
		display.setPreferredSize(new Dimension(258, 543));
		display.setEditable(false);
		StyleContext sc = StyleContext.getDefaultStyleContext();
		TabSet tabs = new TabSet(new TabStop[] {new TabStop(20), new TabStop(20)}); //http://stackoverflow.com/questions/757692/how-do-you-set-the-tab-size-in-a-jeditorpane
		AttributeSet paraSet = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.TabSet, tabs);
		display.setParagraphAttributes(paraSet, false);
		
		displayScroll = new JScrollPane(display);
		displayScroll.setMinimumSize(new Dimension(260, 545));
		displayScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		displayScroll.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		displayScroll.setVisible(true);
		
		search = new JTextPane();
		search.setMinimumSize(new Dimension(260, 25));
		search.setMaximumSize(new Dimension(260, 25));
		search.setText("Search...");
		search.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		search.addFocusListener(this);
		
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Documents");
		docMenu = new JTree(top);
		docMenu.setMinimumSize(new Dimension(120, 500));
		docMenu.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		docMenu.setOpaque(true);
		docMenu.setShowsRootHandles(true);
		docMenu.addTreeSelectionListener(this);
		
		docMenuPane = new JScrollPane(docMenu);
		docMenuPane.setMinimumSize(new Dimension(120, 580));
		docMenuPane.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		docMenuPane.setVisible(true);
		
		bGo = new JButton("Go");
		bGo.setMinimumSize(new Dimension(50,25));
		bGo.setToolTipText("Go");
		bGo.addMouseListener(this);
		bSettings = new JButton("S");
		bSettings.setMinimumSize(new Dimension(50,25));
		bSettings.setToolTipText("Settings");
		bSettings.addMouseListener(this);
		bBack = new JButton("<");
		bBack.setMinimumSize(new Dimension(50,25));
		bBack.setToolTipText("Back");
		bBack.addMouseListener(this);
		bForward = new JButton(">");
		bForward.setMinimumSize(new Dimension(50,25));
		bForward.setToolTipText("Forward");
		bForward.addMouseListener(this);
		bHome = new JButton("Home");
		bHome.setMinimumSize(new Dimension(110,25));
		bHome.setToolTipText("Home");
		bHome.addMouseListener(this);
		
		this.setLayout(new MigLayout("fill", "10[120]10[260]10[50]10[50]10","10[25]10[25]10[25]10[475]10"));
		this.setMinimumSize(new Dimension(530, 600));
		this.setPreferredSize(new Dimension(530, 600));
		
		this.add(displayScroll, "cell 1 1 1 3");
		this.add(search, "cell 1 0");
		this.add(docMenuPane, "cell 0 0 1 4");
		this.add(bGo, "cell 2 0");
		this.add(bSettings, "cell 3 0");
		this.add(bBack, "cell 2 1");
		this.add(bForward, "cell 3 1");
		this.add(bHome, "cell 2 2 2 1");
		
		//set up settings panel
		settings = new JPopupMenu("Settings");
		settings.setLayout(new MigLayout("fill", "10[75]10[75]10", "10[25][105]10[25]10[25]10"));
		settings.setPreferredSize(new Dimension(180, 220));
		
		filter = new JTextPane();
		filter.setMinimumSize(new Dimension(160, 25));
		filter.setMaximumSize(new Dimension(160, 25));
		filter.setText("Exclude...");
		filter.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		filter.addFocusListener(this);
		
		checkPanel = new JPanel();
		checkPanel.setLayout(new MigLayout("fill", "[160]", "[35][35][35]"));
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
		
		checkPanel.add(bTopic, "cell 0 0");
		checkPanel.add(bSummary, "cell 0 1");
		checkPanel.add(bDetail, "cell 0 2");
		
		bApply = new JButton("Apply");
		bApply.setMinimumSize(new Dimension(75,25));
		bApply.setToolTipText("Apply these search filters");
		bApply.addMouseListener(this);
		bCancel = new JButton("Cancel");
		bCancel.setMinimumSize(new Dimension(75,25));
		bCancel.setToolTipText("Cancel filtering and close this menu");
		bCancel.addMouseListener(this);
		
		settings.add(new JLabel("Show only..."), "cell 0 0 2 1");
		settings.add(checkPanel, "cell 0 1 2 1");
		settings.add(filter, "cell 0 2 2 1");
		settings.add(bApply, "cell 0 3");
		settings.add(bCancel, "cell 1 3");
		
		settings.setVisible(false);
		//set up backend
		docs = new ArrayList<Tag>();
		
		parser = XMLParser.getParser();
		
		//findDocs();
		docs.add(new Tag("Welcome", "placeholder"));
		if(docs.size() > 0)
		{
			display.setText(findTopLevelTag("Welcome").sprintTag());
		}
		
		backlist = new ArrayList<Tag>();
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
	 * Adds a Tag from the specified XML file to the list of documents that can be displayed.
	 * @param file The filepath of an XML file to add
	 * @return The resulting Tag
	 */
	Tag addDoc(String file)
	{
		docs.add(parser.readTagFromFile(file));
		return parser.readTagFromFile(file);
	}
	
	/**
	 * Adds all XML files in the current working directory to the list of documents.
	 */
	void findDocs()
	{
		File file = new File(".");
		for(String s: file.list())
		{
			if(s.length() > 4 && s.substring(s.length() - 4).equals(".xml"))
			{
				System.out.println(s);
				addDoc(new File(s).getPath());
			}
		}
	}
	
	/**
	 * Gives a String containing the printed versions of Tags which fit the given search criteria.
	 * @param include A String that the Tags must contain
	 * @param exclude A String that the Tags cannot contain
	 * @param lods A 3-tuple that says which levels of detail to search
	 * @return Topic, Summary, and Detail Tags which fit the criteria
	 */
	String filterDocs(String include, String exclude, boolean[] lods)
	{
		String results = "";
		
		if(lods.length != 3)
		{
			System.err.println("not enough arguments to filterDocs, expected boolean[3]");
			return null;
		}
		
		for(Tag t: docs)
		{
			if((t.findText(include) || include.equals("")) && (lods[0] || lods[1] || lods[2])
			&& !t.name.equals("Welcome"))
			{
				results += t.name + "\n";
			}
			else
			{
				continue;
			}
			
			if(lods[0]) //show topic-level-of-detail results
			{
				if((t.getChildTag("Topic").findText(include) || include.equals("")) && !t.getChildTag("Topic").findText(exclude))
					results += t.getChildTag("Topic").sprintTag();
			}
			
			if(lods[1]) //show summaries
			{
				if((t.getChildTag("Summary").findText(include) || include.equals("")) && !t.getChildTag("Summary").findText(exclude))
					results += t.getChildTag("Summary").sprintTag();
			}
			
			if(lods[2]) //show details
			{
				if((t.getChildTag("Detail").findText(include) || include.equals("")) && !t.getChildTag("Detail").findText(exclude))
					results += t.getChildTag("Detail").sprintTag();
			}
			
			results += "\n";
		}
		
		return results;
	}
	
	/**
	 * Adds all documents in the list of documents to the selection menu.
	 * @param top The top node of the menu tree
	 */
	void addDocsToTree(DefaultMutableTreeNode top)
	{
		DefaultMutableTreeNode node, node2;
		
		for(Tag t: docs)
		{
			node = new DefaultMutableTreeNode(t);
			for(Tag child: t.children)
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
	void updateBacklist(Tag t)
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
	 * Finds a Tag in the top level of the list of documents with the given name.
	 * @param name The name to search for
	 * @return The first Tag in the top level of the tree with the given name
	 */
	Tag findTopLevelTag(String name)
	{
		for(Tag t: docs)
		{
			if(t.name.equals(name))
				return t;
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
		if(node.getUserObject() instanceof Tag)
		{
			Tag t = (Tag) node.getUserObject();
			System.out.println(t.name + " selected");
			display.setText(t.sprintTag());
			updateBacklist(t);
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
			
			display.setText(filterDocs(include, exclude, lods));
			
			if(display.getText().equals(""))
				display.setText("No matches found");
		}
		
		if(e.getSource().equals(bSettings))
		{
			settings.show(this, bSettings.getX() - (settings.getWidth()) + bSettings.getWidth(), bSettings.getY());
			
			bTopic.setSelected(lods[0]);
			bSummary.setSelected(lods[1]);
			bDetail.setSelected(lods[2]);
			
			settings.setVisible(true);
		}
		
		if(e.getSource().equals(bBack))
		{
			if(backlistCurrent > 0)
			{
				backlistCurrent--;
				display.setText(backlist.get(backlistCurrent).sprintTag());
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
				display.setText(backlist.get(backlistCurrent).sprintTag());
				bBack.setEnabled(true);
				if(backlistCurrent == backlist.size() - 1)
					bForward.setEnabled(false);
			}
		}
		
		if(e.getSource().equals(bHome))
		{
			backlist.clear();
			backlist.add(findTopLevelTag("Welcome"));
			backlistCurrent = 0;
			
			display.setText(backlist.get(0).sprintTag());
			
			bBack.setEnabled(false);
			bForward.setEnabled(false);
		}
		
		if(e.getSource().equals(bApply))
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
		
		if(e.getSource().equals(bCancel))
		{
			lods[0] = true;
			lods[1] = true;
			lods[2] = true;
			
			bTopic.setSelected(true);
			bSummary.setSelected(true);
			bDetail.setSelected(true);
			
			filter.setText("Exclude...");
			exclude = "";
			
			settings.setVisible(false);
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
			if(e.getStateChange() == e.DESELECTED)
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
			if(e.getStateChange() == e.DESELECTED)
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
			if(e.getStateChange() == e.DESELECTED)
			{
				lods[2] = false;
			}
			else
			{
				lods[2] = true;
			}
		}
	}

}
