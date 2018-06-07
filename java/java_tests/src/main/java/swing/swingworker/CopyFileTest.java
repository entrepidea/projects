package swing.swingworker;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

/**
 * this is to test SwingWorker. SwingWorker is used to offload a lengthy job in a GUI application. 
 * Some disciplines of using SwingWorker are:
 * 
 * 1) Create the worker with some parameters
 * 2) Execute a single task (by implementing doInBackground())
 * 3) Update the GUI given the results of that task. (by implementing done()).
 * 
 * note: more need to test with SwingWorker...
 *
 * reference: https://forums.oracle.com/forums/thread.jspa?threadID=1140944&tstart=435
 * 
 * @author John
 *  
 * */

class CopyTask extends SwingWorker<Void, ByteBuffer>{

	private File srcFile;
	private File destFile;
	private long fileSize;
	private final int BUFF_SIZE;
	
	CopyTask(String src, String dest){
		this(new File(src),new File(dest));
	}
	
	CopyTask(File src, File dest){
		srcFile = src;
		destFile = dest;
		fileSize = srcFile.length();
		BUFF_SIZE = 1024;
	}
	
	private void copyFile() throws IOException{
		FileInputStream fin = new FileInputStream(srcFile);
		FileOutputStream fout = new FileOutputStream(destFile);
		FileChannel fcin = fin.getChannel();
		FileChannel fcout = fout.getChannel();
		ByteBuffer buffer = ByteBuffer.allocate(BUFF_SIZE);
		int retCode = 0;
		while(retCode!=-1){
			buffer.clear();
			retCode = fcin.read(buffer);
			buffer.flip();
			fcout.write(buffer);
			publish(buffer);
			
			currentSize += BUFF_SIZE;
			int pct = (int)(100*(((double)currentSize/fileSize)));
			
			setProgress(pct);
		}
		fcin.close();fcout.close();
		fin.close();fout.close();
		
		
		
	}
	
	@Override
	protected Void doInBackground() throws Exception {
		copyFile();
		return null;
	}	
	
	long currentSize=0;
	@Override
	public void process(List<ByteBuffer> chunk){
		//currentSize += BUFF_SIZE;
		//int pct = (int)(100*(((double)currentSize/fileSize)));
		
		//System.out.println("current size:="+currentSize+", file Size:="
			//	+fileSize+", current percnt:="+pct);
		
		//setProgress(pct);
	}
}

public class CopyFileTest extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JProgressBar progressBar = new JProgressBar(0,100);
	private JButton button = new JButton("click me");
	
	public CopyFileTest(){
		setLayout(new BorderLayout());
		add(progressBar, BorderLayout.NORTH);

		String src = "C:\\Users\\john\\Downloads\\The_Hangover\\The_Hangover.mkv";
		String dest = "C:\\tmp\\The_Hangover.mkv";
		final SwingWorker<Void, ByteBuffer> task = new CopyTask(src, dest);
		
		task.addPropertyChangeListener(new PropertyChangeListener(){

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if("progress".equals(evt.getPropertyName())){
					int value = (Integer)evt.getNewValue();
					progressBar.setValue(value);
				}
			}
		});
		button.addActionListener(new ActionListener(){
			@Override public void actionPerformed(ActionEvent evnt){
				task.execute();
			}
		});
		add(button, BorderLayout.SOUTH);
	}
	
	private static void createAndShowGUI(){
		JFrame frame = new JFrame("Copy File using Swingworker");
		frame.getContentPane().add(new CopyFileTest(), BorderLayout.CENTER);
		frame.setSize(new Dimension(200,100));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			@Override public void run(){
				createAndShowGUI();
			}
		});
	}

	//Instinet corp, phone interview, 06/16/14, Sanjiv, Hiring manager
	//TODO What's the advantage of Swingworker?
	//TODO How does a Swing application's frontend communicate with backend?
}
