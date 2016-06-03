package com.entrepidea.java.ui.swing.layout;

import java.awt.BorderLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;


public class TODOListEnterPanel extends JPanel {
	private static final long serialVersionUID = 0L;

	private JFrame frame;
	
	public TODOListEnterPanel(JFrame frame){
		this();
		this.frame = frame;
	}
   
    public TODOListEnterPanel() {
        // Creates a panel to hold all components
        //JPanel panel = new JPanel();
        setLayout(new GridBagLayout());
// Give the panel a border gap of 5 pixels
        setBorder(new EmptyBorder(new Insets(5, 5, 5, 5)));
        //getContentPane().add(BorderLayout.CENTER, panel);
        GridBagConstraints c = new GridBagConstraints();
// Define preferred sizes for input fields
        Dimension shortField = new Dimension(40, 20);
        Dimension mediumField = new Dimension(120, 20);
        Dimension longField = new Dimension(240, 20);
        Dimension hugeField = new Dimension(240, 80);
// Spacing between label and field
        EmptyBorder border = new EmptyBorder(new Insets(0, 0, 0, 10));
        EmptyBorder border1 = new EmptyBorder(new Insets(0, 20, 0, 10));
// Add space around all components to avoid clutter
        c.insets = new Insets(2, 2, 2, 2);
// Anchor all components WEST
        c.anchor = GridBagConstraints.WEST;
        JLabel lbl1 = new JLabel("Subject: ");
        lbl1.setBorder(border); // Add some space to the right
        add(lbl1, c);
        JTextField txt1 = new JTextField();
        txt1.setPreferredSize(longField);
        c.gridx = 1;
        c.weightx = 1.0; // Use all available horizontal space
        c.gridwidth = 3; // Spans across 3 columns
        c.fill = GridBagConstraints.HORIZONTAL; // Fills the 3 columns
        add(txt1, c);
        JLabel lbl2 = new JLabel("Description: ");
        lbl2.setBorder(border);
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 1; ;
        c.weightx = 0.0; // Do not use any extra horizontal space
        add(lbl2, c);
        JTextArea area1 = new JTextArea();
        JScrollPane scroll = new JScrollPane(area1);
        scroll.setPreferredSize(hugeField);
        c.gridx = 1;
        c.weightx = 1.0; // Use all available horizontal space
        c.weighty = 1.0; // Use all available vertical space
        c.gridwidth = 3; // Span across 3 columns
        c.gridheight = 2; // Span across 2 rows
        c.fill = GridBagConstraints.BOTH; // Fills the columns and rows
        add(scroll, c);
        JLabel lbl3 = new JLabel("Priority:");
        lbl3.setBorder(border);
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 0.0;
        c.weighty = 0.0;
        c.fill = GridBagConstraints.NONE;
        add(lbl3, c);
        JComboBox combo3 = new JComboBox();
        combo3.addItem("A");
        combo3.addItem("B");
        combo3.addItem("C");
        combo3.addItem("D");
        combo3.addItem("E");
        combo3.setPreferredSize(shortField);
        c.gridx = 1;
        add(combo3, c);
   
        JLabel lbl6 = new JLabel("Start Date:");
        lbl6.setBorder(border);
        c.gridx = 0;
        c.gridy = 4;
        add(lbl6, c);

        JTextField txt6 = new JTextField();
        txt6.setPreferredSize(mediumField);
        c.gridx = 1;
        c.gridwidth = 3;
        add(txt6, c);
 
        JLabel lbl8 = new JLabel("Due Date:");
        lbl8.setBorder(border);
        c.gridx = 0;
        c.gridy = 5;
        add(lbl8, c);
        JTextField txt8 = new JTextField();
        txt8.setPreferredSize(mediumField);
        c.gridx = 1;
        c.gridwidth = 3;
      
        add(txt8, c);
        JLabel lbl5 = new JLabel("Status:");
        lbl5.setBorder(border);
        c.gridx = 0;
        c.gridy = 6;
        add(lbl5, c);
 
      JTextField txt5 = new JTextField();
        txt5.setPreferredSize(longField);
        c.gridx = 1;
       // c.gridwidth = 3;
       // panel.add(radioPanel, c);
        add(txt5, c);
 
        JButton submitBtn = new JButton("Submit");
        c.gridx = 4;
        c.gridy = 0;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(submitBtn, c);
        JButton cancelBtn = new JButton("Cancel");
        c.gridy = 1;
        add(cancelBtn, c);
        JButton helpBtn = new JButton("Help");
        c.gridy = 2;
        c.anchor = GridBagConstraints.NORTH; // Anchor north
        add(helpBtn, c);
        
        setSize(new Dimension(550,200));
        
        
    }
   
    public static void main(String... args){
    	JFrame frame = new JFrame ("Hello there");
    	//frame.setSize(new Dimension(300,250));
    	/*JButton button = new JButton("test");
    	button.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			TODOListDlg dlg = new TODOListDlg();
    			dlg.setVisible(true);
    		}
    	});*/
    	//frame.getContentPane().add(button, BorderLayout.SOUTH);
    	frame.getContentPane().add(new TODOListEnterPanel(frame), BorderLayout.CENTER);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.pack();
    	frame.setVisible(true);
    }
}