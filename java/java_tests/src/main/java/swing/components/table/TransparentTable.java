package swing.components.table;

import java.awt.BorderLayout;
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
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

@SuppressWarnings("serial")
public class TransparentTable extends JPanel {

	public TransparentTable(){
		
		JTable table = new JTable(40,5){
			{this.setOpaque(false);}
		};
		table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){{setOpaque(false);}});
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setPreferredScrollableViewportSize(new Dimension(300,250));
		JTableHeader th = table.getTableHeader();
		th.setDefaultRenderer(new DefaultTableCellRenderer(){{setOpaque(false);}});
		th.setOpaque(false);
		
		JScrollPane pane = new JScrollPane(table){
			BufferedImage bi = null;
			@Override
			public void paintComponent(Graphics g){
//				Graphics2D g2d = (Graphics2D)g;
//				g2d.setPaint(new GradientPaint(0,0,Color.BLUE, getWidth(), getHeight(),Color.RED));
//				g2d.fillRect(0, 0, getWidth(), getHeight());
				
				URL url = getClass().getResource("/images/IMG_5485.JPG");
				//bi = ImageIO.read(new File("/home/john/projects/java_workspace/ExxonDataProcessor/src/main/resources/images/IMG_5485.JPG"));
				try {
					bi = ImageIO.read(url);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Graphics2D g2d = (Graphics2D)g;
				g2d.drawImage(bi, 0, 0, bi.getWidth(),bi.getHeight(),this);
			}
		};
		pane.getViewport().setOpaque(false);
		pane.setColumnHeader(new JViewport());
		pane.getColumnHeader().setOpaque(false);
		setLayout(new BorderLayout());
		add(pane);
		
	}
	
	private static void createAndShowUI(){
		JFrame frame = new JFrame("Transparent Table");
		frame.getContentPane().add(new TransparentTable(), BorderLayout.CENTER);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run(){
				createAndShowUI();
			}
		});
	}

}
