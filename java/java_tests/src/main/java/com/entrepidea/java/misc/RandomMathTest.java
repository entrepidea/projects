package com.entrepidea.java.misc;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class RandomMathTest extends JPanel {

	private static final long serialVersionUID = -878873092766355111L;

	class TimerLabel extends JLabel{
		private static final long serialVersionUID = 5954100799352574611L;
		private int initSec = 30;
		private String labelTxt = "00:"+new Integer(initSec);
		
		class CountDown extends TimerTask {

			@Override
			public void run() {
				updateLabel();
			}
			
		}
		  public TimerLabel() {
			  Timer timer =  new Timer();
				TimerTask task = new CountDown();
				long delay = 0;
				long period = 1000;		
				timer.schedule(task, delay, period);
		  }
		  
		  public void updateLabel(){
			  
			  if(initSec-->0){
				  if(initSec<10){
					  labelTxt = "00:0";
				  }
				  else{
					  labelTxt = "00:";
				  }
				  labelTxt += new Integer(initSec);
			  }
			  else{
				  labelTxt = "00:00";
				  initSec = 30;
				  clearTextField();
				  generateNewOps();
				  
			  }
			  setText(labelTxt);
		  }
		}
	
	private TimerLabel timer = new TimerLabel();
	private JTextField op1 = new JTextField(5);
	private JTextField op2 = new JTextField(5);
	private JTextField ret = new JTextField(5);
	
	public RandomMathTest(){
	    add(timer, BorderLayout.NORTH);
	    JPanel pCenter = new JPanel(new FlowLayout());
	    pCenter.add(op1);
	    pCenter.add(new JLabel("+"));
	    pCenter.add(op2);
	    pCenter.add(new JLabel("="));
	    pCenter.add(ret);
	    
	    add(pCenter, BorderLayout.CENTER);
	    
	    setSize(200, 60);
	    
	    generateNewOps();
	    
	}
	
	public void generateNewOps(){
		int  r =new Random().nextInt(100);
		op1.setText(new Integer(r).toString());
		
		 r = new Random().nextInt(100);
		 
		op2.setText(new Integer(r).toString());
	}
	
	public void clearTextField(){
		op1.setText("");
		op2.setText("");
		ret.setText("");
	}
	
	
	public static void createAndShowGUI(){
		JFrame frame = new JFrame("RandomMathTest");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        RandomMathTest newContentPane = new RandomMathTest();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
	}
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });

	}

}
