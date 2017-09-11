package swing;

/*
 Swing Hacks Tips and Tools for Killer GUIs
 By Joshua Marinacci, Chris Adamson
 First Edition June 2005  
 Series: Hacks
 ISBN: 0-596-00907-0
 Pages: 542
 website: http://www.oreilly.com/catalog/swinghks/
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;

public class StatusBarSimulator {


	
	private static void createAndShowUI(){
		try {
			UIManager.setLookAndFeel(new WindowsLookAndFeel());
		} catch (Exception e) {}
		
		JFrame frame = new JFrame();
		Container contentPane = frame.getContentPane();
		contentPane.setLayout(new BorderLayout());

		//add status bar
		final StatusBar statusBar = new StatusBar();
		contentPane.add(statusBar, BorderLayout.SOUTH);

		//add top panel
		JPanel topPane = new JPanel();
		topPane.setLayout(new BoxLayout(topPane, BoxLayout.X_AXIS));
		topPane.add(Box.createHorizontalGlue());
		JButton btn=null;
		topPane.add(btn = new JButton("test"));
		btn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Task t = new Task(statusBar);
				t.addPropertyChangeListener(statusBar);
				t.execute();
			}
		});
		
		JMenuBar menuBar;
		JMenu menu;
		JMenuItem menuItem;

//		Create the menu bar.
		menuBar = new JMenuBar();

		menu = new JMenu("A Menu");
		menu.setMnemonic(KeyEvent.VK_A);
		menu.getAccessibleContext().setAccessibleDescription(
		        "The only menu in this program that has menu items");
		
		menu.addMouseMotionListener(new MouseMotionListener(){
			 public void 	mouseDragged(MouseEvent e){}
			 public void mouseMoved(MouseEvent e){
				 JMenuItem mi = (JMenuItem)e.getSource();
				 statusBar.setMenuItemText(mi.getText());
			 }
		});
		
		menuBar.add(menu);

		menuItem = new JMenuItem("A text-only menu item",
		                         KeyEvent.VK_T);
		
		menuItem.addMouseMotionListener(new MouseMotionListener(){
			 public void 	mouseDragged(MouseEvent e){}
			 public void mouseMoved(MouseEvent e){
				 JMenuItem mi = (JMenuItem)e.getSource();
				 System.out.println(mi.getText());
				 statusBar.setMenuItemText(mi.getText());
			 }
		});
		
		menu.add(menuItem);
		frame.setBounds(200, 200, 600, 200);
		frame.setTitle("Status bar simulator");
		
		frame.setJMenuBar(menuBar);
		
		contentPane.add(topPane, BorderLayout.NORTH);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);	
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				createAndShowUI();
			}
		});
	}
}



class StatusBar extends JPanel implements PropertyChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3742356654644060304L;
	private int value=0;
	private boolean bErase;
	private String menuText="";
	
	public StatusBar() {
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(10, 23));

		JPanel rightPanel = new JPanel(new BorderLayout());
		rightPanel.add(new JLabel(new AngledLinesWindowsCornerIcon()),
				BorderLayout.SOUTH);
		rightPanel.setOpaque(false);
		
		add(rightPanel, BorderLayout.EAST);
		setBackground(SystemColor.control);
		
	}
	
	public void setMenuItemText(String txt){
		menuText = txt;
		repaint();
	}

	public void setErase(boolean b){
		bErase = b;
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		int y = 0;
		g.setColor(new Color(156, 154, 140));
		g.drawLine(0, y, getWidth(), y);
		y++;
		g.setColor(new Color(196, 194, 183));
		g.drawLine(0, y, getWidth(), y);
		y++;
		g.setColor(new Color(218, 215, 201));
		g.drawLine(0, y, getWidth(), y);
		y++;
		g.setColor(new Color(233, 231, 217));
		g.drawLine(0, y, getWidth(), y);

		y = getHeight() - 3;
		g.setColor(new Color(233, 232, 218));
		g.drawLine(0, y, getWidth(), y);
		y++;
		g.setColor(new Color(233, 231, 216));
		g.drawLine(0, y, getWidth(), y);
		y = getHeight() - 1;
		g.setColor(new Color(221, 221, 220));
		g.drawLine(0, y, getWidth(), y);

		// progress bar
		g.setColor(Color.GREEN);
		g.fill3DRect(getWidth()-120, 6, value, getHeight()-12, true);
		
		if(bErase){
			g.setColor(SystemColor.control);
			g.fillRect(getWidth()-120, 6, value, getHeight()-12);
		}

		//menu text 
		g.setColor(Color.BLACK);
		g.drawString(menuText,2,15);

	}
	
	public void propertyChange(PropertyChangeEvent e){
		if("progress".equalsIgnoreCase(e.getPropertyName())){
			Integer value = (Integer)e.getNewValue();
			this.value = value;
			repaint();
		}		
	}
	
	public void clear(){
		bErase = true;
		repaint();
	}
}

class AngledLinesWindowsCornerIcon implements Icon {
	private static final Color WHITE_LINE_COLOR = new Color(255, 255, 255);

	private static final Color GRAY_LINE_COLOR = new Color(172, 168, 153);

	private static final int WIDTH = 13;

	private static final int HEIGHT = 13;

	public int getIconHeight() {
		return WIDTH;
	}

	public int getIconWidth() {
		return HEIGHT;
	}

	public void paintIcon(Component c, Graphics g, int x, int y) {

		g.setColor(WHITE_LINE_COLOR);
		g.drawLine(0, 12, 12, 0);
		g.drawLine(5, 12, 12, 5);
		g.drawLine(10, 12, 12, 10);

		g.setColor(GRAY_LINE_COLOR);
		g.drawLine(1, 12, 12, 1);
		g.drawLine(2, 12, 12, 2);
		g.drawLine(3, 12, 12, 3);

		g.drawLine(6, 12, 12, 6);
		g.drawLine(7, 12, 12, 7);
		g.drawLine(8, 12, 12, 8);

		g.drawLine(11, 12, 12, 11);
		g.drawLine(12, 12, 12, 12);

	}
}

class Task extends SwingWorker<Void, Void> {
	private StatusBar status=null;
	Task(StatusBar s){
		status = s;
		status.setErase(false);
	}
	@Override 
	public Void doInBackground(){
		for(int i=0;i<10;i++){
			try{
				Thread.sleep(1000);
				setProgress(100*(i+1)/10);
			}
			catch(InterruptedException e){}
		}
		return null;
	}
	
	@Override
	public void done(){
		status.clear();
	}
}
