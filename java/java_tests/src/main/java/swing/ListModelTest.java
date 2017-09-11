package swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

public class ListModelTest extends JPanel {

	/**
	 * @param args
	 */
	private JList list;
	
	ListModelTest(){
		list = new JList(new DefaultListModel());
		list.setPreferredSize(new Dimension(100,200));
		setLayout(new BorderLayout());
		add(new JScrollPane(list), BorderLayout.CENTER);
		JButton btn = new JButton("click me!");
		btn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				addElements();
			}
		});
		add(btn, BorderLayout.SOUTH);
	}
	
	public void addElements(){
		Thread t = new Thread(){
			public void run(){
				DefaultListModel model =(DefaultListModel)list.getModel();
				for(int i=0;i<10;i++){
					try{
						Thread.sleep(1000);
						model.add(i, "test"+i);
					}
					catch(InterruptedException e){};					
				}
			}
		};
		t.start();
	}
	
	
	private static void createAndShowUI(){
		JFrame frame = new JFrame("Testing of List Model");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new ListModelTest());
		frame.pack();
		frame.setVisible(true);
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				createAndShowUI();
			}
		});
	}
}


