package swing.components.button;


import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class TranslucnetButton extends JButton{
	
	private BufferedImage bi = null;
	
	public TranslucnetButton(){
		setOpaque(false);
	}
	
	@Override public Dimension getPreferredSize(){
		return new Dimension(100,50);
	}
	@Override
	public void paint(Graphics g){
		//create a compatible image
		if(bi==null || bi.getWidth()!=getWidth() || 
				bi.getHeight()!=bi.getHeight()){
			bi = getGraphicsConfiguration().createCompatibleImage(getWidth(), getHeight());
			bi.getGraphics().setClip(g.getClip());
			
		}
			
		super.paint(bi.getGraphics());
		
		Graphics2D g2d = (Graphics2D)g;
		//adjust the alpha composite 
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,.3f);
		g2d.setComposite(ac);
		g2d.drawImage(bi, 0, 0,null);
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run(){
				JFrame frame = new JFrame();
				JPanel panel = new JPanel();
				panel.add(new TranslucnetButton());
				frame.setSize(new Dimension(200,200));
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
				frame.getContentPane().add(panel);
			}
		});

	}

}
