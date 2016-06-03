package com.entrepidea.java.ui.swing;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JWindow;

public class BorderlessWindow {
	public static void main(String[]args)  
	{  
	JButton button1=new JButton("MY BUTTON");  
	JWindow window=new JWindow();  
	  
	window.getContentPane().add(button1,BorderLayout.CENTER);  
	  
	window.setSize(400,400);  
	  
	window.setVisible(true);  
	}  
}
