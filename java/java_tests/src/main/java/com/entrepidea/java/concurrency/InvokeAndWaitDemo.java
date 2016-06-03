package com.entrepidea.java.concurrency;

import java.awt.FlowLayout;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class InvokeAndWaitDemo extends Object {
  private static void print(String msg) {
    String name = Thread.currentThread().getName();
    System.out.println(name + ": " + msg);
  }

  public static void main(String[] args) {
    final JLabel label = new JLabel("--------");

    JPanel panel = new JPanel(new FlowLayout());
    panel.add(label);

    JFrame f = new JFrame("InvokeAndWaitDemo");
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.setContentPane(panel);
    f.setSize(300, 100);
    f.setVisible(true);

    try {
      print("sleeping for 3 seconds");
      Thread.sleep(3000);

      print("creating code block for event thread");
      Runnable setTextRun = new Runnable() {
        public void run() {
          print("about to do setText()");
          label.setText("New text!");
          try{
        	  print("sleep for 10 seconds.");
        	  Thread.sleep(10000);
          }
          catch(InterruptedException e){};
        }
      };
      print("about to invokeAndWait()");
      SwingUtilities.invokeAndWait(setTextRun);
      print("back from invokeAndWait()");
    } catch (InterruptedException ix) {
      print("interrupted while waiting on invokeAndWait()");
    } catch (InvocationTargetException x) {
      print("exception thrown from run()");
    }
  }
}
