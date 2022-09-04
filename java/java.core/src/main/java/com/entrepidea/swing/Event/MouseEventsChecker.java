package com.entrepidea.jersey.swing.Event;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * @description: capture the mouse events and show the log in a text field
 * @author hai
 * @date 04/27/13
 * */
public class MouseEventsChecker extends JPanel {

	public MouseEventsChecker(){
		setLayout(new BorderLayout());
		final JTextField jf  =new JTextField(90);
		add(jf, BorderLayout.CENTER);
		JButton btn = new JButton("Click me");
		btn.addMouseListener(new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent evt){

		        int modifiers = evt.getModifiers();
		        if ((modifiers & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
		          System.out.println("Left button pressed.");
					SwingUtilities.invokeLater(new Runnable(){
						@Override
						public void run(){
							jf.setText("Left button pressed.");
						}
					});

		        }
		        if ((modifiers & InputEvent.BUTTON2_MASK) == InputEvent.BUTTON2_MASK) {
		          System.out.println("Middle button pressed.");
		          SwingUtilities.invokeLater(new Runnable(){
						@Override
						public void run(){
							jf.setText("Middle button pressed.");
						}
					});
		        }
		        if ((modifiers & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK) {
		          System.out.println("Right button pressed.");
		          SwingUtilities.invokeLater(new Runnable(){
						@Override
						public void run(){
							jf.setText("Right button pressed.");
						}
					});
		        }
		      
			}
//			public void mouseReleased(MouseEvent evt){
//				SwingUtilities.invokeLater(new Runnable(){
//					@Override
//					public void run(){
//						jf.setText("I am released");
//					}
//				});
//			}
		});
		add(btn,BorderLayout.SOUTH);
	}
	
	private static void createAndShowUI(){
		JFrame frame = new JFrame("Mouse click checker");
		frame.getContentPane().add(new MouseEventsChecker());
		frame.setSize(new Dimension(100,50));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
	}
	
	public static void main(String... args){
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run(){
				createAndShowUI();
			}
		});
	}
}
