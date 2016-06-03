package com.entrepidea.java.ui.swing.layout;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class TODOListFrame extends JFrame {

	/**
	 * @param args
	 */
	private JTabbedPane TODOListPane = new JTabbedPane();   
	
	
	/**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
	TODOListFrame(String title){
		super(title);
	    //Create and set up the content pane.
        TODOListSummaryPanel pane1 = new TODOListSummaryPanel();
        pane1.setOpaque(true); //content panes must be opaque
        
        TODOListEnterPanel pane2 = new TODOListEnterPanel(this);
        pane2.setOpaque(true);
        
        TODOListPane.add("summary", pane1);
        TODOListPane.add("editing",pane2);
        
        getContentPane().add(TODOListPane, BorderLayout.CENTER);
        //setContentPane(newContentPane);

		
	}
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new TODOListFrame("Utility: TODO List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
	}

}
