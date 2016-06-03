package com.entrepidea.java.ui.swing;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * this is the template app that can be used as the stating point for any Swing based small app.
 * */
public class TemplatePanel extends JPanel {

	public TemplatePanel(){
		
	}
	
	
	private static void createAndShowUI(){
		JFrame frame = new JFrame("hello kitty");
		frame.getContentPane().add(new TemplatePanel());
		frame.setSize(new Dimension(400,300));
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.pack();
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run(){
				createAndShowUI();
			}
		});

	}

}
