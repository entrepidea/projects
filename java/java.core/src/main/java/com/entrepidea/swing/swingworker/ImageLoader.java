package com.entrepidea.jersey.swing.swingworker;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

/**
 * @description: 
 * The four methods in SwingWorker: doInBackground, publish, process,and done. 
 * The last two are automatically invoked from EDT.
 * SwingWorker<T,V> T is the intermediate value while V is the final value.
 * 
 * */
class ImageLoaderWorker extends SwingWorker<List<Image>, String>{

	private JTextArea log;
	private JPanel viewer;
	private String[] fileNames;
	
	public ImageLoaderWorker(JTextArea log, JPanel viewer, String... fileNames){
		this.log = log;
		this.viewer = viewer;
		this.fileNames = fileNames;
	}
	
	@Override
	protected void process(List<String> values){
		for(String val:values){
			log.append(val);
			log.append("\n");
		}
	}
	
	@Override
	public void done(){
		try {
			for(Image image:get()){
				if(image==null) continue;
				JLabel label = new JLabel(new ImageIcon(image));
				viewer.add(label);
				viewer.revalidate();
			}
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	protected List<Image> doInBackground() throws Exception {
		List<Image> images = new ArrayList<Image>();
		for(String fileName:fileNames){
			try{
				URL url = getClass().getResource(fileName);
				BufferedImage image = ImageIO.read(url);
				images.add(image);
				publish("loading "+fileName);
			}
			catch(IOException ioe){
				publish("error: "+ioe.getMessage());
			}
		}
		
		return images;
		
	}
	
}

public class ImageLoader {
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run(){
				final String[] fileNames= new String[]{
					     "/images/Bodie_small.png", "/images/Carmela_small.png",
		                   //"/images/Unknown.png", "/images/Denied.png",
		                   "/images/Death Valley_small.png", "/images/Lake_small.png","/images/java.jpeg"

				};
				
				final JTextArea log = new JTextArea(4,4);
				final JPanel panel = new JPanel();
				JButton start = new JButton("Start");
				start.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e){
						new ImageLoaderWorker(log, panel, fileNames).execute();
					}
				});
				
				JFrame frame = new JFrame();
				frame.getContentPane().add(new JScrollPane(log), BorderLayout.NORTH);
				frame.getContentPane().add(new JScrollPane(panel), BorderLayout.CENTER);
				frame.getContentPane().add(start,BorderLayout.SOUTH);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setSize(new Dimension(400,350));
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);
			}
		});

	}

}
