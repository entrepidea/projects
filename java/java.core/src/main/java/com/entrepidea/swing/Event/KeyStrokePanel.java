package com.entrepidea.swing.Event;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

import javax.swing.JPanel;
import javax.swing.JFrame;

public class KeyStrokePanel extends JPanel implements KeyListener{
	
	private Point startPoint = new Point(0,0);
	private Point endPoint = new Point(0,0);
	
	public void keyPressed(KeyEvent evt){
		int code = evt.getKeyCode();
		int d = 0;
		
		if(evt.isShiftDown()){
			d = 5;
		}
		else{
			d = 1;
		}
		if(code == KeyEvent.VK_LEFT){
			add(-d,0);
		}
		if(code == KeyEvent.VK_RIGHT){
			add(d,0);
		}
		if(code == KeyEvent.VK_UP){
			add(0,-d);
		}
		if(code == KeyEvent.VK_DOWN){
			add(0,d);
		}
		
	}
	public void keyReleased(KeyEvent evt){
	}
	public void keyTyped(KeyEvent evt){
	}
	public boolean isFocusTraversable(){
		return true;
	}
	
	public void add(int x, int y){
		Graphics g = getGraphics();
		startPoint = endPoint;
		endPoint.x += x;
		endPoint.y += y;		
		g.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
	}
	
	public static void main(String[] args){
		JFrame f = new JFrame();		
		f.setTitle("Key Stroke");
		f.getContentPane().add(new KeyStrokePanel());
		f.setSize(300,200);
		f.setVisible(true);
		f.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
	}	
}