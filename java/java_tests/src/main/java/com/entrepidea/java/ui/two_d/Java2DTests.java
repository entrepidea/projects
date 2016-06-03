package com.entrepidea.java.ui.two_d;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

class Java2DPanel extends JComponent{
	
	private Map desktopHints = null;
	
	public Java2DPanel(){
		Toolkit tk = Toolkit.getDefaultToolkit();
		desktopHints = (Map)tk.getDesktopProperty("awt.font.desktophints");
	}
	
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(500,450);
	}
	
	@Override
	public void paintComponent(Graphics g){/*
		Graphics2D g2d = (Graphics2D)g;
		g2d.setBackground(Color.CYAN);
		g2d.clearRect(0, 0, getWidth(), getHeight());
		Font font = g2d.getFont().deriveFont(Font.BOLD | Font.ITALIC | Font.ROMAN_BASELINE, 24f);
		g2d.setFont(font);
		String str = "Hello, World!";
		g2d.drawString(str, 20, 20);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.drawString(str, 20, 50);
		
		if(desktopHints !=null){
			g2d.addRenderingHints(desktopHints);
			g2d.drawString(str, 20, 80);
		}
		
		g2d.setStroke(new BasicStroke(20f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
		g2d.drawLine(50, 150, 300, 300);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawLine(50, 200, 300, 350);
		
		//g2d.setColor(Color.GRAY.brighter());
		//g2d.fillRect(50, 370, 50, 50);
		g2d.rotate(Math.toRadians(45));
		g2d.setColor(Color.BLUE.darker());
		g2d.fillRect(50, 370, 50, 50);
		
		g2d.dispose();
		super.paintComponent(g);
		*/
		Graphics2D g2d  = (Graphics2D)g.create();
		Insets i = getInsets();
		
		g2d.setColor(Color.GRAY.brighter());
		g2d.fillRect(i.left, i.top, getWidth()-i.left-i.right, getHeight()-i.top-i.bottom);
		
		g.setColor(Color.RED);
		g.drawRoundRect(50, 50, 50, 50, 10, 10);
		g.draw3DRect(50, 120, 50, 50, true);
		g2d.dispose();
		
	}
}
public class Java2DTests {

	
	private static void createAndShowGUI(){
		JFrame frame = new JFrame("Java 2D tests");
		frame.getContentPane().add(new Java2DPanel());
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
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
