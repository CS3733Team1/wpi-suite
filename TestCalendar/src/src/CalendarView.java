//package src;
//import java.awt.*;
//import javax.swing.*;
//import javax.swing.JList;
//import javax.swing.ListSelectionModel;
//
//public class CalendarView extends JFrame {
//
//    private     JTabbedPane tabbedPane;
//    private     JPanel      panel; // Page where I want JScrollPane intisialized
//    
//    private	JTable		table;
//    private JList checkBoxesJList;
//    
//    public CalendarView()
//    {
//        setTitle( "Program" );
//        setSize( 900, 900 ); // I want the JScrollPane to extend to 400 vertically
//
//
//        JPanel topPanel = new JPanel();
//        topPanel.setLayout( new BorderLayout() );
//        getContentPane().add( topPanel );
//
//        // Create the tab pages
//        createPage1();
//
//        tabbedPane = new JTabbedPane();
//        tabbedPane.addTab( "Welcome", panel );
//        topPanel.add( tabbedPane, BorderLayout.CENTER );    
//    }
//
//    public void createPage1()
//    {
//        panel = new JPanel();
//        panel.setLayout( new BorderLayout() );
//        
//        
//        
//        
//        ////////////////////////
//        JScrollPane scrollPanel1 = new JScrollPane();
//        scrollPanel1.setViewportView(checkBoxesJList);
//       
//        scrollPanel1.setPreferredSize(new Dimension(900,900));
//        ///////////////////////
//        
//     // Create columns names
//     		String columnNames[] = { "Column 1", "Column 2", "Column 3" };
//
//     		// Create some data
//     		String dataValues[][] =
//     		{
//     			{ "12", "234", "67" },
//     			{ "-123", "43", "853" },
//     			{ "93", "89.2", "109" },
//     			{ "279", "9033", "3092" }
//     		};
//
//     		// Create a new table instance
//     		table = new JTable( dataValues, columnNames );
//
//     		// Add the table to a scrolling pane
//     		scrollPane = new JScrollPane( table );
//     		topPanel.add( scrollPane, BorderLayout.CENTER );
//        
//        
//
//        panel.add(scrollPanel1,BorderLayout.CENTER);
//        
//     
//    }
//
//    public static void main( String args[] )
//    {
//        // Create an instance of the test application
//        CalendarView mainFrame  = new CalendarView();
//        mainFrame.setVisible( true );
//    }
//}