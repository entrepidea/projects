package com.entrepidea.java.ui.swing;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class JFrameUtil extends JFrame {

	public JFrameUtil(String title){
		super(title);
		try{
			//UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			setSize(new Dimension(300,200));
		}
		catch(Exception ie){
			ie.printStackTrace();
		}
	}
	
	public static void centerWindow(JFrame f) {
		if (f == null) {
			return;
		}
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension dlgSize = f.getSize();
		if (dlgSize.height > screenSize.height) {
			dlgSize.height = screenSize.height;
		}
		if (dlgSize.width > screenSize.width) {
			dlgSize.width = screenSize.width;
		}
		f.setLocation((screenSize.width - dlgSize.width) / 2,
				(screenSize.height - dlgSize.height) / 2);
	}

	
	public static void createAndShowGUI() {
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        //Create and set up the window.
        JFrame frame = new JFrameUtil("TextDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
       // JComponent newContentPane = new Panel();//new Panel1();
        //newContentPane.setOpaque(true); //content panes must be opaque
        //frame.setContentPane(newContentPane);
        //Display the window.
//        frame.pack();
        centerWindow(frame);
        frame.setVisible(true);
    }
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				createAndShowGUI();
			}
		});
	}
}
