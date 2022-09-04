package com.entrepidea.jersey.swing.components.table;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;


public class ComboBoxCellEditorTest  {

	private JTable table = null;
	private JPanel panel = null;
	public ComboBoxCellEditorTest(){
		table = new JTable(new DefaultTableModel(){
			private String[] cols = null;
			
			@Override
			public int getColumnCount(){
				if(cols==null){
					cols = new String[]{"id","fName","lName","gender","race","age","Marital status","political affiliate"};		
				}
				return cols.length;
				}
			@Override
			public int getRowCount(){return 30;}
		
			@Override
			public String getColumnName(int idex){
				return cols[idex];
			}
			
			
			@Override
			public Class<?> getColumnClass(int index){
				if (index==3){
					return JComboBox.class;
				}
				else{
					return super.getColumnClass(index);
				}
			}
			
		
			/*
			@Override
			public TableCellEditor getCellEditor(int row, int column){
				if(column == 3){
					JComboBox<String> cb = new JComboBox<String>();
					cb.removeAll();
					cb.addItem("M");
					cb.addItem("F");
					return new DefaultCellEditor(cb); 
				}
				else{
					return super.getCellEditor(row, column);
				}
			}*/
			
		});
	}
	
	public JScrollPane createTablePanel(){
		panel = new JPanel(new BorderLayout());
		panel.add(table, BorderLayout.CENTER);
		JScrollPane sp = new JScrollPane(panel);
		sp.setPreferredSize(new Dimension(400,300));
		return sp;
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run(){
				JFrame f = new JFrame();
				ComboBoxCellEditorTest cbcet = new ComboBoxCellEditorTest();
				JScrollPane pane = cbcet.createTablePanel();
				f.getContentPane().add(pane);
				f.pack();
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				f.setVisible(true);
			}
		});
	}

}
