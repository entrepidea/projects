package swing.graphics;


import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

public class TranslucentImage extends JPanel {

	private BufferedImage bi = null;
	public TranslucentImage(){
		//setOpaque(false);
		if(bi==null){
			URL url = getClass().getResource("/images/java.jpeg");
			try {
				bi = ImageIO.read(url);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(bi.getWidth(),bi.getHeight());
	}
	
	@Override
	public void paintComponent(Graphics g){
		
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D)g;
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .7f);
		g2d.setComposite(ac);
		
		g2d.drawImage(bi, 0, 0, bi.getWidth(), bi.getHeight(), null);
		
	}
	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			
			@Override
			public void run(){
				JFrame frame = new JFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				JPanel panel = new TranslucentImage();
				JScrollPane sp = new JScrollPane(panel);
//				sp.setPreferredSize(new Dimension(200,100));
				frame.getContentPane().add(sp);
				frame.pack();
				frame.setVisible(true);
				//JFrameUtil.centerWindow(frame);
			}
		});

	}

}
