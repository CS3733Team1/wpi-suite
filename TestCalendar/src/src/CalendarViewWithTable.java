package src;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.*;

public class CalendarViewWithTable extends JFrame
{    
    public CalendarViewWithTable()
    {
        setTitle( "Program" );
        setSize( 900, 900 ); // I want the JScrollPane to extend to 400 vertically

        String[] columnNames = {"Hour", "Event", "Nick^2"};
        Object[][] data = new Object[24][5];
        		
        for (int i = 0; i < 24; i++)
        {
        	data[i][0] = "Hour: " + i;
        	data[i][1] = "Event: " + i;
        	data[i][2] = "Your Mother";
        }//end for
    
        JTable dayView = new JTable(data, columnNames);
        dayView.setPreferredScrollableViewportSize(new Dimension(500, 70));
        dayView.setFillsViewportHeight(true);
        dayView.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        dayView.setSize(900, 900);
        
        
        JPanel dayViewContainer = new JPanel();
        dayViewContainer.add(dayView);
        dayViewContainer.setSize(900, 900);
        
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Day View", dayViewContainer);
        
        add(tabbedPane);
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }//end constructor
        /*JPanel topPanel = new JPanel();
        topPanel.setLayout( new BorderLayout() );
        getContentPane().add( topPanel );
        
        // Create the tab pages
        createPage1();

        tabbedPane = new JTabbedPane();
        tabbedPane.addTab( "Welcome", panel );
        
        topPanel.add( tabbedPane, BorderLayout.CENTER );   
    }//end constructor
    
    public void createPage1()
    {
    	panel = new JPanel();
    	panel.setLayout( new BorderLayout() );
    }//end createPage1*/
    public static void main( String args[] )
    {
      // Create an instance of the test application
      CalendarViewWithTable mainFrame  = new CalendarViewWithTable();
      mainFrame.setVisible( true );
    }//end main
}//end class
