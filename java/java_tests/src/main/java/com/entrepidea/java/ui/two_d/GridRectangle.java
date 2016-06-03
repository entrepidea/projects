package com.entrepidea.java.ui.two_d;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;

public class GridRectangle {

	private static class GridRectanglePanel extends JComponent implements MouseInputListener {
		
		public GridRectanglePanel(){
			
			addMouseListener(this);
			addMouseMotionListener(this);
			setBorder(BorderFactory.createEmptyBorder());
			setBackground(Color.WHITE);
			setOpaque(true);
		}
		
		@Override
		protected void paintComponent(Graphics g){
			if(isOpaque()){
				g.setColor(getBackground());
				g.fillRect(0,0,getWidth(),getHeight());
			}
			g.setColor(Color.GRAY);
			drawGrid(g, 20);
		}
		
		private void drawGrid(Graphics g, int space){
			Insets inset = getInsets();
			int firstX = inset.left;
			int firstY = inset.top;
			int lastX = getWidth() - inset.right;
			int lastY = getHeight() - inset.bottom;
			
			int x = firstX;
			while(x!=lastX){
				g.drawLine(x,firstY, x, lastY);
				x+=space;
			}
			int y = firstY;
			while(y!=lastY){
				g.drawLine(firstX,y,lastX,y );
				y+=space;
			}
			
		}
		
		@Override
		public void mouseClicked(MouseEvent e){
			System.out.println(e.getX()+","+e.getY());
		}
		
		@Override
		public void mouseEntered(MouseEvent e){}
		
		@Override
		public void mouseExited(MouseEvent e){}
		
		@Override
		public void mousePressed(MouseEvent e){}
		
		@Override
		public void mouseReleased(MouseEvent e){}

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	
	private static void createAndShowGUI(){
		JFrame frame = new JFrame("Grid panel");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JComponent com = new GridRectanglePanel();
		com.setPreferredSize(new Dimension(500,80));
		frame.getContentPane().add(com);
		frame.pack();
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run(){
				createAndShowGUI();
			}
			
		});
	}

}
