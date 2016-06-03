package com.entrepidea.java.ui.swing;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Transparency;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class CharRenderCanvas extends JComponent {

	private int fontSize = 32;
	//font family candidates: "Verdana","Andalus"
	private Font m_dftFont = new Font("Algerian", Font.PLAIN,  fontSize); // common font object for display text
	private Robot robot = null;
	private FontMetrics fm; 
	int x = 0;
	int y = fontSize;
	private BufferedImage screenShot;
	
	public CharRenderCanvas(){
		 try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	protected void paintComponent(Graphics context) {
		 Graphics2D g= (Graphics2D) context;  
		 g.setColor(Color.BLACK);
		 g.setFont(m_dftFont);
		 
		 char[] data = {'A'};
		 int offset = 0;
		 int length = data.length;
		// int x=0; int y=32;
		 //Rectangle rect = g.getClipBounds();
		 //System.out.println(rect);
		 g.drawChars(data, offset, length, x, y);
		 GraphicsConfiguration config = g.getDeviceConfiguration();
		 //int transparency = Transparency.OPAQUE;
		 //screenShot = config.createCompatibleImage(400, 400, transparency);
		 screenShot = config.createCompatibleImage(400, 400);
		 //fm = g.getFontMetrics();
	}
	
	public int[] capture(){

		//System.out.println(fm.getDescent()+":"+fm.getAscent());
		// BufferedImage screenShot = robot.createScreenCapture(new Rectangle(x,0,32,32));
		 //BufferedImage screenShot = new BufferedImage(8,8,BufferedImage.TYPE_INT_RGB);
		 int w = screenShot.getWidth(null);
		 int h = screenShot.getHeight(null);
		 int[] pixelArray_genA = new int[w*h]; //array to hold pixel color data.
		 for(int i=0;i<pixelArray_genA.length;i++){
			 pixelArray_genA[i] = 0;
		 }
		 screenShot.getRGB(0, 0, w, h, pixelArray_genA, 0, w);
		 
		 File imageFile = new File("screenshot.png");
		 try {
			ImageIO.write(screenShot, "png", imageFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 return pixelArray_genA;
	}
	
	public void buildUI(Container container) {
        container.setLayout(new BoxLayout(container,
                                          BoxLayout.PAGE_AXIS));
        //FontRenderGrid grid = new FontRenderGrid();
        container.add(this);
        //Align the left edges of the components.
        setAlignmentX(Component.LEFT_ALIGNMENT);
        
       
	}
	
	private static void createAndShowUI(){
		JFrame frame = new JFrame("Demo to render the chars");
		frame.setSize(new Dimension(450,300));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final CharRenderCanvas cavas = new CharRenderCanvas();
		JPanel panel = new JPanel(new FlowLayout());
		JButton btn = new JButton("Test");
		btn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				int[] pixels = cavas.capture();
				System.out.println(pixels.length);
				for(int i=0;i<32;i++){
					for(int j=0;j<32;j++){
						System.out.printf("[%d,%d]=%1$#x", i,j,pixels[i*32+j]);
						//System.out.print("["+i+","+j+"]"+"=("+pixels[i*32+j]+")");
						System.out.print('\t');
					}
					System.out.println();
				}				
			}});
		
		panel.add(btn);
		frame.getContentPane().add(panel, BorderLayout.SOUTH);
		
		panel = new JPanel();
		cavas.buildUI(panel);
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		frame.setVisible(true);
	}
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				createAndShowUI();
			}
		});
	}
}
