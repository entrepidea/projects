package com.entrepidea.java.ui.swing;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 * @description: demo of the usage of repaint(). 
 * this method can be called from any thread and still work in EDT. 
 * it's used to refresh a display
 * 
 * */
public class RepaintTest extends JFrame {


	public static class SafePaintComponent extends JLabel{
		SafePaintComponent(){
			super("paint safely");
		}
		
		@Override
		public Dimension getPreferredSize(){
			return new Dimension(100,45);
		}
		
		@Override 
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			System.out.println(SwingUtilities.isEventDispatchThread());
		}
	} 
	public static void main(String[] args) {
		
		final SafePaintComponent s = new SafePaintComponent();
		JFrame frame = new RepaintTest();
		frame.add(s);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		
		Thread t = new Thread(new Runnable(){
			@Override
			public void run(){
				while(true){
				try{
					s.repaint();
					Thread.sleep(1000);
				}
				catch(Exception e){
					e.printStackTrace();
				}
				}
			}
		});
		t.start();
		
	}
}


