package swing;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class JFileChooserTest extends JFrame {
	  public JFileChooserTest() {
		    JFileChooser fileChooser = new JFileChooser();
		    fileChooser.setDialogTitle("Choose a file");
		    this.getContentPane().add(fileChooser);
		    fileChooser.setVisible(true);
		  }

		  public static void main(String[] args) {
		    JFrame frame = new JFileChooserTest();
		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		    frame.pack();
		    frame.setVisible(true);
		  }
		}